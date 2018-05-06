package daoke360com.task.analysislog.log_utils

import java.util.Base64

import com.alibaba.fastjson.JSON
import daoke360com.caseclass.IPRule
import daoke360com.common.GlobalContants
import daoke360com.utils.Utils
import org.apache.commons.lang.StringUtils
import test.haitaoGo.common.EventLogContants

import scala.collection.mutable

/**
  * Created by dell on 2018/5/4.
  */
object LogAnalysisUtils {
  /**
    * haitaoGo
    * 处理请求参数
    *
    * @param requestBody
    * @param eventLogMap
    */
  private def handlerRequestBody(requestBody: String, eventLogMap: mutable.HashMap[String, String]) = {

    //请求类型
    val tuple = requestBody.split("[/]")
    eventLogMap.put(EventLogContants.REQUEST_TYPE, tuple(0))

    //判段
    if (tuple(1).split("[?]")(1).split("[=]")(0).equals("bData")) {

      val str = requestBody.split("[=]")(1).split(" ")(0)
      if (Utils.validateBase64(str)) {

        val decoder = Base64.getUrlDecoder()
        val url = new String(decoder.decode(str))

        eventLogMap.put(EventLogContants.USER_BEHAVIOR, "bData")
        if (Utils.validateJson(url)) {

          val json = JSON.parseObject(url)


          //用户行为标识key
          eventLogMap.put(EventLogContants.USER_BEHAVIOR_KEY, json.get(EventLogContants.USER_BEHAVIOR_KEY).toString)

          //用户行为数据
          eventLogMap.put(EventLogContants.USER_BEHAVIOR_DATA, json.get(EventLogContants.USER_BEHAVIOR_DATA).toString)

          //用户行为数据解析
          val behData = json.get(EventLogContants.USER_BEHAVIOR_DATA)

          val ss = JSON.parseObject(behData.toString)


          val it = ss.entrySet().iterator()

          while (it.hasNext) {

            val kv = it.next().toString.split("[=]")

            eventLogMap.put(kv(0), kv(1))
          }

        }
      }
    }
  }

  /**
    *曲海涛  ip 归属地解析
    * @param eventLogMap
    * @param iprules
    * @return
    */
  private def handlerIP(eventLogMap: mutable.HashMap[String, String], iprules: Array[IPRule]) = {


    val ip = eventLogMap(EventLogContants.LOG_COLUMN_NAME_IP)

    val reginInfo = IPAnasisUtils.analysisIP(ip, iprules)

    eventLogMap.put(EventLogContants.LOG_COLUMN_NAME_COUNTRY, reginInfo.country)

    eventLogMap.put(EventLogContants.LOG_COLUMN_NAME_PROVINCE, reginInfo.province)

    eventLogMap.put(EventLogContants.LOG_COLUMN_NAME_CITY, reginInfo.city)

  }

  /**
    * haitaoGo-曲海涛
    * 对单条日志进行解析返回一个hashMap
    * @param logText
    * @param iprules
    * @return
    */
  def analysisLog(logText: String, iprules: Array[IPRule]) = {

    var eventLogMap: mutable.HashMap[String, String] = null

    if (StringUtils.isNotBlank(logText)) {

      val field = logText.split(GlobalContants.LOG_SPLIT_FLAG)

      if (field.length == 8 &&field(7).split("[;]").length==4) {

        eventLogMap = new mutable.HashMap[String, String]()

        //用户ip

        eventLogMap.put(EventLogContants.LOG_COLUMN_NAME_IP, field(0))
        //访问时间
        eventLogMap.put(EventLogContants.LOG_COLUMN_NAME_ACCESS_TIME, Utils.parseNginxTime2Long(field(3), "dd/MMM/yyyy:HH:mm:ss +0800").toString)


        val requestBody = field(4)


        //处理请求参数

        handlerRequestBody(requestBody, eventLogMap)

        //处理ip
        handlerIP(eventLogMap, iprules)

        //os_n 操作系统
        val kv = field(7).split("[;]")(2).split(" ")

        eventLogMap.put(EventLogContants.OPERATION_SYSTEM_NAME, kv(1))
        //os_v:操作系统版本
        eventLogMap.put(EventLogContants.OPERATION_SYSTEM_VERSION, kv(2))

        val modelNum = field(7).split("[/]")(2).split("[)]")(0)
        //手机型号
        eventLogMap.put(EventLogContants.MOBILE_PHONE_MODEL, modelNum)

      }

    }
    eventLogMap
  }
}
