package daoke360com.common

/**
  * Created by 于汶洁 on 2018/5/3.
  */
object GlobalContants {

  /**
    * 日志分割标记
    */
  final val LOG_SPLIT_FLAG: String = "\\|"

  /**
    * 任务参数开始的标记
    */
  final val TASK_PARAMS_FLAG: String = "-d"

  /**
    * 任务运行日期
    */
  final val TASK_RUN_DATE = "task_run_date"

  /**
    * 任务id
    */
  final val TASK_RUN_ID = "task_run_id"

  /**
    * 日志存放根目录
    */
  final val LOG_DIR_PREFIX = "/logs/"
  /**
    * 任务的输入路径
    */
  final val TASK_INPUT_PATH: String = "task_input_path"

  /**
    * JDBC mysql 驱动
    */
  final val JDBC_DRIVER = "jdbc.driver"

  /**
    * 数据库连接池初始大小
    */
  final val JDBC_DATA_SOURCE_SIZE = "jdbc.datasource.size"
  /**
    * mysql url
    */
  final val JDBC_URL = "jdbc.url"
  /**
    * mysql 用户名
    */
  final val JDBC_USER = "jdbc.user"
  /**
    * mysql 密码
    */
  final val JDBC_PASSWORD = "jdbc.password"

  /**
    * 任务相关的常量
    */
  final val TASK_PARAM_START_DATE: String = "startDate"
  final val TASK_PARAM_END_DATE: String = "endDate"

  /**
    * session相关常量
    */
  val SESSION_ID = "sid"
  val SESSION_COUNTRY = "country"
  val SESSION_PROVINCE = "province"
  val SESSION_CITY = "city"

  /**
    * 默认值
    */
  final val DEFAULT_VALUE: String = "unknown"
  final val VALUE_OF_ALL: String = "all"
}
