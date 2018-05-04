package daoke360com.utils

import java.text.SimpleDateFormat
import java.util.{Base64, Locale}
import java.util.regex.Pattern

import com.alibaba.fastjson.JSON

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


  /**
    * 曲海涛
    * 验证日期是否是 yyyy-MM-dd  这种格式
    *
    * @param inputDate
    * @return
    */
  def validateInpuDate(inputDate: String) = {
    val reg = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$"
    val pattern = Pattern.compile(reg)
    pattern.matcher(inputDate).matches()

  }

  /**
    * 曲海涛
    * 验证是否是数字
    */
  def validateNumber(inputNumber: String) = {

    val reg = "\\d+"
    val pattern = Pattern.compile(reg)
    pattern.matcher(inputNumber).matches()
  }

  /**
    * 曲海涛
    * 验证 数据是否为 json 格式的数据
    * @param json
    * @return
    */
  def validateJson(json: String) = {

    try{
      JSON.parseObject(json)
      true
    }catch {

      case e: Exception =>{
        false
      }
        false
    }
  }

  /**
    * 曲海涛
    * 判断是否是hbase64 加密数据
    * @param base64
    * @return
    */
  def validateBase64(base64:String)={

    try{
      var url = new String(Base64.getUrlDecoder().decode(base64))
      true
    }catch {
      case e:Exception=>{
        false
      }
    }

  }


}
