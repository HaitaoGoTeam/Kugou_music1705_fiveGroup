package daoke360com.caseclass

import daoke360com.common.GlobalContants

/**
  * Created by dell on 2018/3/30.
  */
/**
  * ip规则类
  *
  * @param startIp
  * @param endIp
  * @param Country
  * @param provices
  * @param city
  */
case class IPRule(startIp: Long, endIp: Long, Country: String, provices: String, city: String) extends Serializable



case class ReginInfo(var country: String = GlobalContants.DEFAULT_VALUE,
                     var province: String = GlobalContants.DEFAULT_VALUE,
                     var city: String = GlobalContants.DEFAULT_VALUE) extends Serializable