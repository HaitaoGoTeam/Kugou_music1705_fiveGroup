package test.haitaoGo.common

/**
  * Created by 于汶洁 on 2018/5/3.
  */
object EventLogContants {

  /**
    * 保存日志的表
    */
  final val HBASE_EVENT_LOG_TABLE: String = "kugou_log"

  /**
    * 列族
    */
  final val HBASE_EVENT_LOG_TABLE_FAMILY: String = "log"

  /**
    * 用户ip
    */
  final val LOG_COLUMN_NAME_IP: String = "ip"
  /**
    * 日志时间
    */
  final val LOG_COLUMN_NAME_ACCESS_TIME = "access_time"
  /**
    * 国家
    */
  final val LOG_COLUMN_NAME_COUNTRY = "country"
  /**
    * 省份
    */
  final val LOG_COLUMN_NAME_PROVINCE = "province"
  /**
    * 城市
    */
  final val LOG_COLUMN_NAME_CITY = "city"

  /**
    * 用户唯一标识
    */
  final val LOG_COLUMN_NAME_UUID = "uid"

  /**
    * 用户会话标识
    */
  final val LOG_COLUMN_NAME_SID = "sid"


  /**
    * 事件名称
    */
  final val LOG_COLUMN_NAME_EVENT_NAME = "en"

  /**
    * 操作系统名称
    */
  final val OPERATION_SYSTEM_NAME = "os_n"

  /**
    * 操作系统版本
    */
  final val OPERATION_SYSTEM_VERSION = "os_v"

  /**
    * 专辑id
    */
  final val ALBUM_ID = "albumId"

  /**
    * 节目id
    */
  final val PROGRAM_ID = "programId"

  /**
    * 播放时长
    */
  final val PLAY_TIME = "playTime"

  /**
    * 行为标识key
    */
  final val USER_BEHAVIOR_KEY = "behaviorKey"

  /**
    * 用户行为数据
    */
  final val USER_BEHAVIOR_DATA = "behaviorData"

  /**
    * 手机型号
    */
  final val MOBILE_PHONE_MODEL = "modelNum"

  /**
    * 请求方式
    */
  final val REQUEST_TYPE = "request_type"

  /**
    * 主播id
    */
  final val HOST_ID = "anchorId"

  /**
    * app区域信息
    */
  final val APP_REGIONAL_INFORMATION = "zongKey"

  /**
    * 用户行为标识
    */
  final val USER_BEHAVIOR = "behavior"


}
