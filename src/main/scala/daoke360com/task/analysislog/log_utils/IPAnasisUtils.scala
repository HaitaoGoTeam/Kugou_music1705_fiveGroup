package daoke360com.task.analysislog.log_utils

import daoke360com.caseclass.{IPRule, ReginInfo}

import scala.util.control.Breaks.{break, breakable}

/**
  * Created by dell on 2018/5/4.
  */
object IPAnasisUtils {
  /**
    * 杨欣田
    * 查找ip对应的地域信息
    * @param ip
    * @param ipRules
    * @return
    */
  def analysisIP(ip: String, ipRules: Array[IPRule]) = {
    val regionInfo = ReginInfo()
    //将ip转换成完整的十进制
    val numIP = ip2Long(ip)
    //通过二分查找法，查找出ip对应的地区索引
    val index = binnerySearch(numIP, ipRules)
    if (index != -1) {
      val iPRule = ipRules(index)
      regionInfo.country = iPRule.Country
      regionInfo.province = iPRule.provices
      regionInfo.city = iPRule.city
    }
    regionInfo
  }

  /**
    * 杨欣田
    * 通过二分查找法，查找出ip对应的地区索引
    * @param numIP
    * @param ipRules
    * @return ip对应的地区索引
    */
  private def binnerySearch(numIP: Long, ipRules: Array[IPRule]) = {
    var min = 0
    var max = ipRules.length - 1
    var index = -1
    breakable({
      while (min <= max) {
        var middle = (min + max) / 2
        val ipRule = ipRules(middle)
        if (numIP >= ipRule.startIp && numIP <= ipRule.endIp) {
          index = middle
          break()
        } else if (numIP < ipRule.startIp) {
          max = middle - 1
        } else if (numIP > ipRule.endIp) {
          min = middle + 1
        }
      }
    })
    index
  }

  /**
    * 将ip转换成完整的十进制
    * 杨欣田
    * @param ip 比如：1.0.1.0
    * @return 比如：16777472
    */
  private def ip2Long(ip: String): Long = {
    var numIP: Long = 0
    //Array(1,0,1,0)
    val fileds = ip.split("[.]")
    for (i <- 0 until (fileds.length)) {
      numIP = numIP << 8 | fileds(i).toLong
    }
    numIP
  }
}
