
package com.tcloudsoft.utils.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


public class DateUtils {

  /**
   * 日期格式 年 如2009
   */
  public static final String DATEFORMATYEAR = "yyyy";

  /**
   * 日期格式 年 月 如 2009-02
   */
  public static final String DATEFORMATMONTH = "yyyy-MM";

  /**
   * 日期格式 年 月 日 如2009-02-26
   */
  public static final String DATEFORMATDAY = "yyyy-MM-dd";

  public static final String DATEFORMATDAY2 = "yyyy/MM/dd";

  /**
   * 日期格式 年 月 日 时 如2009-02-26 15
   */
  public static final String DATEFORMATHOUR = "yyyy-MM-dd HH";

  /**
   * 日期格式 年 月 日 时 分 如2009-02-26 15:40
   */
  public static final String DATEFORMATMINUTE = "yyyy-MM-dd HH:mm";

  /**
   * 日期格式年 月 日 时 分 秒 如 2009-02-26 15:40:00
   */
  public static final String DATEFORMATSECOND = "yyyy-MM-dd HH:mm:ss";

  /**
   * 日期格式年 月 日 时 分 秒 毫秒 如2009-02-26 15:40:00 110
   */
  public static final String DATEFORMATMILLISECOND = "yyyy-MM-dd HH:mm:ss SSS";

  private static String timePattern = "HH:mm:ss";

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");



  /**
   * 获取 当前年、半年、季度、月、日、小时 开始结束时间
   */

  private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
  private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");;
  private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;


  /**
   * 返回日期 根据数据库类型 str_to_date to_date 精确到秒
   * 
   * @return
   */
  public static String str_to_date_second(String dataStr) {
    String ret = "";
    // if (Constant.DATABASETYPE.equalsIgnoreCase("oracle")) { //Oracle数据库
    // ret += " to_date('" + dataStr + "','yyyy-MM-dd hh24:mi:ss') ";
    // } else { //MySQL数据库
    ret += " str_to_date('" + dataStr + "','%Y-%m-%d %H:%i:%s') ";
    // }
    return ret;
  }

  /**
   * 返回日期 根据数据库类型 str_to_date to_date 精确到分
   * 
   * @return
   */
  public static String str_to_date_minute(String dataStr) {
    String ret = "";
    // if (Constant.DATABASETYPE.equalsIgnoreCase("oracle")) { //Oracle数据库
    // ret += " to_date('" + dataStr + "','yyyy-MM-dd hh24:mi') ";
    // } else { //MySQL数据库
    ret += " str_to_date('" + dataStr + "','%Y-%m-%d %H:%i') ";
    // }
    return ret;
  }

  /**
   * 按指定的格式，把Date转换成String 如date为null,返回null
   * 
   * @param date Date参数
   * @param format 日期格式
   * @return String
   */
  public static String format(Date date, String format) {
    if (date == null) {
      return null;
    }
    return new SimpleDateFormat(format).format(date);
  }

  /**
   * 按指定的格式，把string转换成Date 如string为空或null，返回null
   * 
   * @param string
   * @param format
   * @return
   * @throws ParseException
   */
  public static Date parase(String string, String format) throws ParseException {
    if (StringUtils.isEmpty(string)) {
      return null;
    }
    return new SimpleDateFormat(format).parse(string);
  }

  /**
   * 按年月日格式，把String转换成Date 如果String为空或者null，返回null
   * yyyy-MM-dd
   * @param dateString
   * @return
   * @throws ParseException
   */
  public static Date string2Date(String dateString) throws ParseException {
    Date dateTime = new Date();
    if (!StringUtils.isEmpty(dateString)) { // 如果string时间参数不是空
      final SimpleDateFormat df = new SimpleDateFormat(DATEFORMATDAY); // 年月日时间格式化
      Date date = null;
      date = df.parse(dateString); // String转换Date
      dateTime = new Date(date.getTime());
    }
    return dateTime;
  }

  /**
   * yyyy/MM/dd
   * @param dateString
   * @return
   * @throws ParseException
   */
  public static Date string2Date2(String dateString) throws ParseException {
    Date dateTime = new Date();
    if (!StringUtils.isEmpty(dateString)) { // 如果string时间参数不是空
      final SimpleDateFormat df = new SimpleDateFormat(DATEFORMATDAY2); // 年月日时间格式化
      Date date = null;
      date = df.parse(dateString); // String转换Date
      dateTime = new Date(date.getTime());
    }
    return dateTime;
  }

  /**
   * 获取当前系统时间
   * 
   * @return
   */
  public static Date getSysDate() {
    Calendar calender = Calendar.getInstance();
    return calender.getTime();
  }

  /**
   * 取一天的开始时间 精确到秒 如果date为null，返回null
   * 
   * @param date
   * @return
   * @throws Exception
   */
  public static String getDayFirstSecond(Date date) {
    if (date == null) {
      return null;
    }
    String str = format(date, DATEFORMATDAY) + " 00:00:00";
    return str;
  }

  /**
   * 取一天的开始时间 精确到秒 如果string为""，返回null
   * 
   * @param
   * @return
   * @throws Exception
   */
  public static String getDayFirstSecond(String date) {
    if (date.equals("")) {
      return null;
    }
    String ret = "";
    try {
      ret = getDayFirstSecond(string2Date(date));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return ret;
  }

  /**
   * 取一天的结束时间 精确到秒 如果date为null，返回null
   * 
   * @param date
   * @return
   * @throws Exception
   */
  public static String getDayLastSecond(Date date) {
    if (date == null) {
      return null;
    }
    final String str = format(date, DATEFORMATDAY) + " 23:59:59";
    return str;
  }

  /**
   * 取一天的结束时间 精确到秒 如果string为""，返回null
   * 
   * @param
   * @return
   * @throws Exception
   */
  public static String getDayLastSecond(String date) {
    if (date.equals("")) {
      return null;
    }
    String ret = "";
    try {
      ret = getDayLastSecond(string2Date(date));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return ret;
  }

  /**
   * 取一天的开始时间 精确到毫秒
   * 
   * @param date
   * @return
   * @throws Exception
   */
  public static Date getDayFirstTime(Date date) throws Exception {
    if (date == null) {
      return null;
    }
    final String str = format(date, DATEFORMATDAY) + " 00:00:00 000";
    return parase(str, DATEFORMATMILLISECOND);
  }

  /**
   * 取一天的结束时间 精确到毫秒
   * 
   * @param date
   * @return
   * @throws Exception
   */
  public static Date getDayLastTime(Date date) throws Exception {
    if (date == null) {
      return null;
    }
    final String str = format(date, DATEFORMATDAY) + " 23:59:59 999";
    return parase(str, DATEFORMATMILLISECOND);
  }

  /**
   * 获取昨天的日期
   * 
   * @param strDate
   * @return
   * @throws ParseException
   * @throws Exception
   */
  public static Date getYestoday(String strDate) throws ParseException {
    if (null != strDate && strDate.length() > 0) {
      final GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(parase(strDate, DATEFORMATDAY));
      cal.add(Calendar.DAY_OF_MONTH, -1);
      final String str = format(cal.getTime(), DATEFORMATDAY);
      return parase(str, DATEFORMATDAY);
    }
    return null;
  }

  /**
   * 获取明天的日期
   * 
   * @param
   * @return
   * @throws ParseException
   * @throws Exception
   */
  public static Date getTomorrow() throws ParseException {
    final GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(getSysDate());
    cal.add(Calendar.DAY_OF_MONTH, 1);
    final String str = format(cal.getTime(), DATEFORMATDAY);
    return parase(str, DATEFORMATDAY);
  }

  /**
   * 获取指定日期下一天的日期
   * 
   * @param
   * @return
   * @throws ParseException
   * @throws Exception
   */
  public static Date getNextDay(Date someDate) throws ParseException {
    final Calendar ca = Calendar.getInstance();
    ca.setTime(someDate);
    ca.add(Calendar.DAY_OF_MONTH, 1);
    final String str = format(ca.getTime(), DATEFORMATDAY);
    return parase(str, DATEFORMATDAY);
  }

  /**
   * 根据当前日期返回本月的最后一天
   * 
   * @param someDate
   * @return
   */
  public static Date getLastDayOfMonth(Date someDate) {
    final Calendar ca = Calendar.getInstance();
    ca.setTime(someDate); // someDate 为你要获取的那个月的时间
    ca.set(Calendar.DAY_OF_MONTH, 1);
    ca.add(Calendar.MONTH, 1);
    ca.add(Calendar.DAY_OF_MONTH, -1);
    return ca.getTime();
  }

  /**
   * 得到本月最后一天的日期
   */
  public static Date getLastDayOfMonth(String dateStr) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Date date = sdf.parse(dateStr);
    Calendar ca = Calendar.getInstance();
    ca.setTime(date);
    ca.set(Calendar.DAY_OF_MONTH, 1);
    ca.add(Calendar.MONTH, 1);
    ca.add(Calendar.DAY_OF_MONTH, -1);
    return ca.getTime();
  }

  /**
   * 当前日期 yyyy-MM-dd
   * 
   * @return
   */
  public static String getToday() {
    Calendar ca = Calendar.getInstance();
    String str = format(ca.getTime(), DATEFORMATDAY);
    return str;
  }

  /**
   * 获取当前天 dd
   */
  public static String getNowDay(){
    Calendar ca = Calendar.getInstance();
    String day = format(ca.getTime(),"dd");
    return day;
  }

  /**
   * 获取当前日期  MM-dd HH:mm:ss
   */
  public static String getNowTime(){
    Calendar ca = Calendar.getInstance();
    String now = format(ca.getTime(),"MM-dd HH:mm:ss");
    return now;
  }

  /**
   * 当前日期上个月 yyyy-MM-dd
   * 
   * @return
   */
  public static String getLastMonthToday() {
    Calendar ca = Calendar.getInstance();
    ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
    String str = format(ca.getTime(), DATEFORMATDAY);
    return str;
  }

  /**
   * 当前日期上个星期yyyy-MM-dd
   * 
   * @return
   */
  public static String getLastWeekToday() {
    Calendar ca = Calendar.getInstance();
    ca.add(Calendar.DAY_OF_MONTH, -7);
    String str = format(ca.getTime(), DATEFORMATDAY);
    return str;
  }

  /**
   * 当前日期 yyyy-MM-dd HH:mm:ss
   * 
   * @return
   */
  public static String getTodayToSecond() {
    Calendar ca = Calendar.getInstance();
    String str = format(ca.getTime(), DATEFORMATSECOND);
    return str;
  }

  /**
   * 当前日期-月 yyyy-MM-dd HH:mm:ss
   * 
   * @return
   */
  public static String getLastMonthTodayToSecond() {
    Calendar ca = Calendar.getInstance();
    ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
    String str = format(ca.getTime(), DATEFORMATSECOND);
    return str;
  }

  /**
   * 当前日期-一周 yyyy-MM-dd HH:mm:ss
   * 
   * @return
   */
  public static String getLastWeekTodayToSecond() {
    Calendar ca = Calendar.getInstance();
    ca.add(Calendar.DAY_OF_MONTH, -7);
    String str = format(ca.getTime(), DATEFORMATSECOND);
    return str;
  }

  /**
   * 得到本月第一天的日期
   */
  public static Date getStartDayOfMonth(Date date) {
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.set(Calendar.DAY_OF_MONTH, 1);
    return cDay.getTime();
  }

  /**
   * 得到本月第一天的日期
   */
  public static Date getStartDayOfMonth(String dateStr) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Date date = sdf.parse(dateStr);
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.set(Calendar.DAY_OF_MONTH, 1);
    return cDay.getTime();
  }

  /**
   * 得到本月最后一天的日期
   */
  public static Date getEndDayOfMonth(Date date) {
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
    return cDay.getTime();
  }

  /**
   * 得到本月最后一天的日期
   */
  public static Date getEndDayOfMonth(String dateStr) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    Date date = sdf.parse(dateStr);
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
    return cDay.getTime();
  }

  /**
   * 得到下个月第一天的日期
   */
  public static Date getStartDayOfNextMonth(Date date) {
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.add(Calendar.MONTH, +1);
    cDay.set(Calendar.DAY_OF_MONTH, 1);
    return cDay.getTime();
  }

  /**
   * 得到下个月第一天的日期
   */
  public static Date getStartDayOfNextMonth(String dateStr) throws ParseException {
    Date date = parase(dateStr, DATEFORMATMONTH);
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.add(Calendar.MONTH, +1);
    cDay.set(Calendar.DAY_OF_MONTH, 1);
    return cDay.getTime();
  }

  /**
   * 获取指定日期所在周的周一
   */
  public static Date getMonday(Date date) {
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    cDay.set(Calendar.DAY_OF_WEEK, 2);// 老外将周日定位第一天，周一取第二天
    return cDay.getTime();
  }

  /**
   * 获取指定日期所在周日
   */
  public static Date getSunday(Date date) {
    Calendar cDay = Calendar.getInstance();
    cDay.setTime(date);
    if (Calendar.DAY_OF_WEEK == cDay.getFirstDayOfWeek()) { // 如果刚好是周日，直接返回
      return date;
    } else {// 如果不是周日，加一周计算
      cDay.add(Calendar.DAY_OF_YEAR, 7);
      cDay.set(Calendar.DAY_OF_WEEK, 1);
      return cDay.getTime();
    }
  }

  /**
   * 获取本年的第一天
   */
  public static Date getFirstDayOfYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    return calendar.getTime();
  }

  /**
   * 获取去年的今天
   */
  public static String getLastYearDay(Integer year) {
    SimpleDateFormat sdFormat = new SimpleDateFormat("MM-dd");
    System.out.println(String.valueOf(year)+sdFormat.format(new Date()));
    return String.valueOf(year)+"-"+sdFormat.format(new Date());
  }

  /**
   * 获取本年的第一天
   */
  public static Date getFirstDayOfYear(String dateStr) throws ParseException {
    Date date = parase(dateStr, DATEFORMATYEAR);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    return calendar.getTime();
  }

  /**
   * 获取本年的最后一天
   */
  public static Date getLastDayOfYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    return calendar.getTime();
  }

  /**
   * 获取本年的最后一天
   */
  public static Date getLastDayOfYear(String dateStr) throws ParseException {
    Date date = parase(dateStr, DATEFORMATYEAR);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    return calendar.getTime();
  }

  public static int getThisYear() throws ParseException {
    SimpleDateFormat df = new SimpleDateFormat(DATEFORMATYEAR);//设置日期格式
    return Integer.valueOf(df.format(new Date()));
  }



  /**
   * 获取下一年的第一天
   */
  public static Date getFirstDayOfNextYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, +1);
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    return calendar.getTime();
  }

  /**
   * 获取下一年的第一天
   */
  public static Date getFirstDayOfNextYear(String dateStr) throws ParseException {
    Date date = parase(dateStr, DATEFORMATYEAR);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, +1);
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    return calendar.getTime();
  }

  /**
   * 获取昨天的日期
   * 
   * @param
   * @return
   * @throws ParseException
   * @throws Exception
   */
  public static String getYestoday() throws ParseException {
    Calendar calendar = Calendar.getInstance();
    calendar.add(calendar.DATE, -1);
    calendar.set(calendar.HOUR_OF_DAY, 0);
    calendar.set(calendar.MINUTE, 0);
    return format(calendar.getTime(), DATEFORMATMINUTE);
  }

  /**
   * 获取当前时间分钟之前 精确到分钟
   */
  public static String getBeforMinutesSysDate(int minute) throws ParseException {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, -minute);
    return format(calendar.getTime(), DATEFORMATMINUTE);
  }

  /**
   * 获取当前时间分钟之前 精确到分钟
   */
  public static String getMinuteSysDate() throws ParseException {
    Calendar calendar = Calendar.getInstance();
    return format(calendar.getTime(), DATEFORMATMINUTE);
  }

  /**
   * 按年月日时分秒格式，把String转换成Date 如果String为空或者null，返回null
   * 
   * @param dateString
   * @return
   * @throws ParseException
   */
  public static Date string2DateSecond(String dateString) throws ParseException {
    Date dateTime = null;
    if (!StringUtils.isEmpty(dateString)) { // 如果string时间参数不是空
      final SimpleDateFormat df = new SimpleDateFormat(DATEFORMATSECOND); // 年月日时间格式化
      Date date = null;
      date = df.parse(dateString); // String转换Date
      dateTime = new Date(date.getTime());
    }
    return dateTime;
  }

  public static int diffDays(Date OneTime, Date OtherTime) {
    return DiffDays(getCalendar(OneTime), getCalendar(OtherTime));
  }

  /**
   * 获得两个时间的日期间隔 @return 获得两个日期间的日间隔，返回值为整型
   */
  public static int DiffDays(Calendar OneTime, Calendar OtherTime) {
    int nDays =
        (int) ((GetClearDate(OtherTime).getTimeInMillis() - GetClearDate(OneTime).getTimeInMillis())
            / 86400000);
    return nDays;
  }

  /**
   * 获得某一日期当天第一刻时间值
   */
  public static Calendar GetClearDate(Calendar aDate) {
    Calendar aDate2 = (Calendar) aDate.clone();
    aDate2.set(Calendar.HOUR_OF_DAY, 0);
    aDate2.set(Calendar.MINUTE, 0);
    aDate2.set(Calendar.SECOND, 0);
    aDate2.set(Calendar.MILLISECOND, 0);
    return aDate2;
  }

  public static Date convertStringToDateTime(String szDate) {
    Date Result = null;

    SimpleDateFormat dateformat = null;
    String szDateMask = DATEFORMATDAY + " " + timePattern;
    dateformat = new SimpleDateFormat(szDateMask);

    try {
      Result = dateformat.parse(szDate);
    } catch (ParseException pe) {
      pe.printStackTrace();
    }

    return Result;
  }

  /**
   * This method generates a string representation of a date/time in the format you specify on input
   * 
   * @param aMask the date pattern the string is in
   * @param strDate a string representation of a date
   * @return a converted Date object
   * @see SimpleDateFormat
   * @throws ParseException
   */
  public static final Date convertStringToDate(String aMask, String strDate) {
    SimpleDateFormat df = null;
    Date date = null;
    df = new SimpleDateFormat(aMask);

    try {
      date = df.parse(strDate);
    } catch (ParseException pe) {
      pe.printStackTrace();
    }

    return (date);
  }

  public static final Calendar getCalendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }


  /**
   * @param day
   * @return
   * @author: 谭斌
   * @description:从当前时间往前推指定天数的开始时间
   * @version 创建时间：2020年11月18日 下午3:09:18
   */
  public static Date getStartBeforeDay(Integer day) {
    Date now = new Date();
    Date startDate = org.apache.commons.lang3.time.DateUtils.addDays(now, -day);
    return startDate;
  }

  public static String getDateString(Date date, String format) {
    if (date != null) {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      String dateString = formatter.format(date);
      return dateString;
    }
    return null;
  }

  // 获取今年是哪一年
  public static Integer getNowYear() {
//    Date date = new Date();
//    GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
//    gc.setTime(date);
//    return Integer.valueOf(gc.get(1));
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    return Integer.valueOf(year);
  }

  /**
   * 将字符串时间格式转换成Date时间格式，参数String类型 比如字符串时间："2017-12-15 21:49:03" 转换后的date时间：Fri Dec 15 21:49:03 CST
   * 2017
   * 
   * @param datetime 类型为String
   * @return
   */
  public static Date StringToDate(String datetime) {
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    try {
      date = sdFormat.parse(datetime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date StringToDate2(String datetime) {
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    try {
      date = sdFormat.parse(datetime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date String2Date(String dateStr) {
    /* yyyy-MM-dd格式一定要与stringDate的格式一致 */
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  // 获取本年的开始时间
  public static Date getBeginDayOfYear() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, getNowYear());
    cal.set(Calendar.MONTH, Calendar.JANUARY);
    cal.set(Calendar.DATE, 1);
    return getDayStartTime(cal.getTime());
  }

  // 获取本年的结束时间
  public static Date getEndDayOfYear() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, getNowYear());
    cal.set(Calendar.MONTH, Calendar.DECEMBER);
    cal.set(Calendar.DATE, 31);
    return getDayEndTime(cal.getTime());
  }

  // 获取某个日期的结束时间
  public static Timestamp getDayEndTime(Date d) {
    Calendar calendar = Calendar.getInstance();
    if (null != d)
      calendar.setTime(d);
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return new Timestamp(calendar.getTimeInMillis());
  }

  // 获取某个日期的开始时间
  public static Timestamp getDayStartTime(Date d) {
    Calendar calendar = Calendar.getInstance();
    if (null != d)
      calendar.setTime(d);
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return new Timestamp(calendar.getTimeInMillis());
  }

  // 获取当天的开始时间
  public static Date getDayBegin() {
    Calendar cal = new GregorianCalendar();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  // 获取当天的结束时间
  public static Date getDayEnd() {
    Calendar cal = new GregorianCalendar();
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    return cal.getTime();
  }

  // 获取本周的开始时间
  @SuppressWarnings("unused")
  public static Date getBeginDayOfWeek() {
    Calendar calendar = Calendar.getInstance();
    int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); //获取周开始基准
    int current = calendar.get(Calendar.DAY_OF_WEEK); //获取当天周内天数
    calendar.add(Calendar.DAY_OF_WEEK, min-current+1); //当天-基准，获取周开始日期
    Date start = calendar.getTime();
    return start;
  }

  // 获取本周的结束时间
  public static Date getEndDayOfWeek() {
    Calendar calendar = Calendar.getInstance();
    int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); //获取周开始基准
    int current = calendar.get(Calendar.DAY_OF_WEEK); //获取当天周内天数
    calendar.add(Calendar.DAY_OF_WEEK, min-current+1); //当天-基准，获取周开始日期
    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_WEEK, 6); //开始+6，获取周结束日期
    Date end = calendar.getTime();
    return end;
  }

  // 获取本月的开始时间
  public static Date getBeginDayOfMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(getNowYear(), getNowMonth() - 1, 1);
    return getDayStartTime(calendar.getTime());
  }

  // 获取本月的结束时间
  public static Date getEndDayOfMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(getNowYear(), getNowMonth() - 1, 1);
    int day = calendar.getActualMaximum(5);
    calendar.set(getNowYear(), getNowMonth() - 1, day);
    return getDayEndTime(calendar.getTime());
  }

  // 获取本月是哪一月
  public static int getNowMonth() {
    Date date = new Date();
    GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
    gc.setTime(date);
    return gc.get(2) + 1;
  }







  public static String turnDayHourMinuteString(Double param) {
     BigDecimal decimal = new BigDecimal(param);
    decimal = decimal.setScale(0,BigDecimal.ROUND_HALF_UP);
    int minute = Integer.valueOf(decimal+"");


    //如果传入的分钟是0，默认24小时
    if (0 == minute) {
      return  "-小时";
    }
    //如果分钟小于60，默认返回分钟
    if (0 < minute && minute < 60) {
      return minute + "分钟";
    }
    //如果分钟小于24小时（1440分钟），返回小时和分钟
    if (60 <= minute && minute < 1440) {

      if (minute % 60 == 0) {
        int h = Integer.valueOf((minute / 60)+"");
        return h + "小时";
      } else {
        int h =Integer.valueOf((minute / 60)+"");
        int m = Integer.valueOf((minute % 60)+"");
        return h + "小时" + m + "分钟";
      }
    }
    //如果分钟大于1天
    if (minute >= 1440) {
      int d =Integer.valueOf((minute / 60 /24)+"");
      int h =Integer.valueOf((minute / 60 %24)+"");
      int m = Integer.valueOf((minute % 60 )+"");
      String s1 = null;
      if (d > 0) {
        s1 = d + "天";
      }
      //h如果计算大于等于1再展示，否则只展示天和分钟
      if (h >= 1) {
        s1 += h + "小时";
      }
      if (m > 0) {
        s1 += m + "分钟";
      }
      return s1;
    }
    return null;
  }

  //计算2个时间段之之间的日期
   public  static List<String> getDateList(Date start ,Date end,String format){
    if(start == null && end == null)
    {
      return new ArrayList<>();
    }
    if(end == null)
    {
      end = start;
    }
    if(start == null)
    {
      start = end;
    }
     List<String> betweenTime = new ArrayList<String>();
     try
     {
       SimpleDateFormat outformat = new SimpleDateFormat(format);
       Calendar sCalendar = Calendar.getInstance();
       sCalendar.setTime(start);
       int year = sCalendar.get(Calendar.YEAR);
       int month = sCalendar.get(Calendar.MONTH);
       int day = sCalendar.get(Calendar.DATE);
       sCalendar.set(year, month, day, 0, 0, 0);

       Calendar eCalendar = Calendar.getInstance();
       eCalendar.setTime(end);
       year = eCalendar.get(Calendar.YEAR);
       month = eCalendar.get(Calendar.MONTH);
       day = eCalendar.get(Calendar.DATE);
       eCalendar.set(year, month, day, 0, 0, 0);
       while (sCalendar.before(eCalendar))
       {
         betweenTime.add(outformat.format(sCalendar.getTime()));
         sCalendar.add(Calendar.DAY_OF_YEAR, 1);
       }
       betweenTime.add(outformat.format(eCalendar.getTime()));
     }
     catch(Exception e)
     {
       e.printStackTrace();
     }
     return betweenTime;
   }

  /**
   * 获取一个时间往前或往后推指定的时间
   * @param date 时间
   * @param i 类型 例如 Calendar.HOUR
   * @param d 需要滑动的时间
   * @return
   */
  public static Date dateRoll(Date date, int i, int d) {
    // 获取Calendar对象并以传进来的时间为准
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    // 将现在的时间滚动固定时长,转换为Date类型赋值
    calendar.add(i, d);
    // 转换为Date类型再赋值
    date = calendar.getTime();
    return date;
  }




  /**
   * 当前年的开始时间
   *
   * @return
   */
  public static Date getCurrentYearStartTime() {
    Calendar c = Calendar.getInstance();
    Date now = null;
    try {
      c.set(Calendar.MONTH, 0);
      c.set(Calendar.DATE, 1);
      now = shortSdf.parse(shortSdf.format(c.getTime()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }

  /**
   * 当前年的结束时间
   *
   * @return
   */
  public static Date getCurrentYearEndTime() {
    Calendar c = Calendar.getInstance();
    Date now = null;
    try {
      c.set(Calendar.MONTH, 11);
      c.set(Calendar.DATE, 31);
      now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }

  /**
   * 当前季度的开始时间
   *
   * @return
   */
  public static Date getCurrentQuarterStartTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 1;
    Date now = null;
    try {
      if (currentMonth >= 1 && currentMonth <= 3)
        c.set(Calendar.MONTH, 0);
      else if (currentMonth >= 4 && currentMonth <= 6)
        c.set(Calendar.MONTH, 3);
      else if (currentMonth >= 7 && currentMonth <= 9)
        c.set(Calendar.MONTH, 4);
      else if (currentMonth >= 10 && currentMonth <= 12)
        c.set(Calendar.MONTH, 9);
      c.set(Calendar.DATE, 1);
      now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }

  /**
   * 当前季度的结束时间
   *
   * @return
   */
  public static Date getCurrentQuarterEndTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 1;
    Date now = null;
    try {
      if (currentMonth >= 1 && currentMonth <= 3) {
        c.set(Calendar.MONTH, 2);
        c.set(Calendar.DATE, 31);
      } else if (currentMonth >= 4 && currentMonth <= 6) {
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DATE, 30);
      } else if (currentMonth >= 7 && currentMonth <= 9) {
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DATE, 30);
      } else if (currentMonth >= 10 && currentMonth <= 12) {
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DATE, 31);
      }
      now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }









  /**
   * LocalDate转Date
   * @param localDate
   * @return
   */
  public static Date localDate2Date(LocalDate localDate) {
    if(null == localDate) {
      return null;
    }
    ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
    return Date.from(zonedDateTime.toInstant());
  }

  /**
   * LocalDate转Date
   * @param year
   * @return
   */
  public static String getFirstTime(Integer year) {
    if(null == year) {
      return null;
    }
    return year + "-01-01 00:00:01";
  }

  /**
   * LocalDate转Date
   * @param year
   * @return
   */
  public static String getLastTime(Integer year) {
    if(null == year) {
      return null;
    }
    return year + "-12-31 23:59:59";
  }

}
