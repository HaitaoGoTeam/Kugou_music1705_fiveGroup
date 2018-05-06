package daoke360com.task.analysislog

import daoke360com.caseclass.IPRule
import daoke360com.common.GlobalContants
import daoke360com.task.analysislog.log_utils.LogAnalysisUtils
import daoke360com.utils.Utils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.{SparkConf, SparkContext, SparkException}
import test.haitaoGo.common.EventLogContants


/**
  * Created by dell on 2018/5/4.
  */
object AnalysisLogTask {


  //验证参数
  def processArgs(args: Array[String], sparkConf: SparkConf) = {

    if (args.length == 2 && args(0).equals(GlobalContants.TASK_PARAMS_FLAG) && Utils.validateInpuDate(args(1))) {

      sparkConf.set(GlobalContants.TASK_INPUT_PATH, args(1))


    } else {

      throw new SparkException(
        """
          |哈哈，你错了
          |
          |
          |
        """.stripMargin)

    }

  }

  /**
    * 曲海涛
    * 处理输入路径
    *
    * @param sparkConf
    */
  def processInputPath(sparkConf: SparkConf) = {

    val path = sparkConf.get(GlobalContants.TASK_INPUT_PATH)
    var fs: FileSystem = null

    try {
      //将字符串转换成long 类型的时间戳
      val Ltime = Utils.parseNginxTime2Long(sparkConf.get(GlobalContants.TASK_INPUT_PATH), "yyyy-MM-dd")

      //将时间戳转换为指定格式的日期
      val path = Utils.formatDate(Ltime, "yyyy/MM/dd")

      val inputpath = new Path(GlobalContants.LOG_DIR_PREFIX + path)

      fs = FileSystem.newInstance(new Configuration())
      if (fs.exists(inputpath)) {

        sparkConf.set(GlobalContants.TASK_INPUT_PATH, inputpath.toString + "/*.gz")

      } else {

        throw new Exception(
          """
            |哈哈，我找不到 这个路径
            |
            |
          """.stripMargin)
      }

    } catch {
      case e: Exception => throw e
    } finally {

      if (fs != null) {

        fs.close()
      }
    }

  }

  def main(args: Array[String]): Unit = {


    val sparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")


    val jobConf = new JobConf(new Configuration())
    //制定输出的类
    jobConf.setOutputFormat(classOf[TableOutputFormat])

    jobConf.set(TableOutputFormat.OUTPUT_TABLE, EventLogContants.HBASE_EVENT_LOG_TABLE)

    //验证参数
    processArgs(args, sparkConf)

    //处理输入路径
    processInputPath(sparkConf)


    val sc = new SparkContext(sparkConf)
    val a = 1
    val accumulable = sc.accumulator(a)
    /**
      * 加载ip 规则库
      * 将ip 进行广播
      */
    val IPRulesRdd = sc.textFile("/iprules/ip.data").map(ip => {

      val split = ip.split(GlobalContants.LOG_SPLIT_FLAG)

      IPRule(split(2).toLong, split(3).toLong, split(5), split(6), split(7))

    }).collect()

    //广播ip 规则库
    val ipRules = sc.broadcast(IPRulesRdd)


    /**
      * 曲海涛
      * 加载.zip 文件数据，就行解析清洗
      */
    val MapRdd = sc.textFile(sparkConf.get(GlobalContants.TASK_INPUT_PATH)).map(line => {
      LogAnalysisUtils.analysisLog(line, ipRules.value)
    }).filter(map => map != null).sample(false, 0.0001)


    /**
      * 将map 数据保存进hbase
      */
    val tupple = MapRdd.map(map => {
      accumulable.add(1)
      /**
        * 构建rowkey  时间戳  +  ip + 手机型号
        */
      //ip
      val ip = map(EventLogContants.LOG_COLUMN_NAME_IP)
      //时间
      val time = map(EventLogContants.LOG_COLUMN_NAME_ACCESS_TIME)
      //手机型号
      val phonenum = map(EventLogContants.MOBILE_PHONE_MODEL)

      //构建rowkey
      val rowKey = time + "_" + Math.abs((ip + phonenum).hashCode)


      //构建put 对象

      val put = new Put(rowKey.getBytes())


      map.foreach(t2 => {

        put.addColumn(EventLogContants.HBASE_EVENT_LOG_TABLE_FAMILY.getBytes(), t2._1.getBytes(), t2._2.getBytes())

      })

      (new ImmutableBytesWritable(), put)

    })

    //将结果保存到hbase 中 create 'kugou_log',''
    tupple.saveAsHadoopDataset(jobConf)
    println(accumulable.value)
    sc.stop()

  }
}
