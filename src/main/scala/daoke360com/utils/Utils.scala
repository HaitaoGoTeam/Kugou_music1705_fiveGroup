package daoke360com.utils

import java.text.SimpleDateFormat
import java.util.Locale

/**
  * Created by dell on 2018/5/4.
  */
object Utils {


  /**
    * 张迎尊  times时间戳--》指定格式时间
    * @param longTime
    * @param pattern
    * @return 格式时间
    */
  def formatDate(longTime: Long, pattern: String) = {
    val sdf = new SimpleDateFormat(pattern)
    val calendar = sdf.getCalendar
    calendar.setTimeInMillis(longTime)
    sdf.format(calendar.getTime)
  }
  /**
    * 张迎尊  指定格式时间--》时间戳
    * eg:dd/MMM/yyyy:HH:mm:ss +0800-->time戳
    * @param nginxTime
    * @param pattern
    * @return 格式时间
    */
  def parseNginxTime2Long(nginxTime: String,pattern:String ) = {
    val sdf = new SimpleDateFormat(pattern, Locale.ENGLISH)
    val date = sdf.parse(nginxTime)
    date.getTime
  }





}
