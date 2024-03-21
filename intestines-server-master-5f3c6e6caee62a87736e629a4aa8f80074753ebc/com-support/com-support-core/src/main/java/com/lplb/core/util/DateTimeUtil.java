package com.lplb.core.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

	private static final String commonFormat = "yyyy-MM-dd HH:mm:ss";
	
	private static final String YYYYMMDD_FORMAT = "yyyy-MM-dd";

	private static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);
	/**
	 * 获取当前时间
	 * java.util.Date
	 * @return
	 */
	public static Date getNow() {
		Calendar calendar=Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 获取当前时间字符串
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowString() {
		SimpleDateFormat sdf = new SimpleDateFormat(commonFormat);
		return sdf.format(getNow());
	}

	/**
	 * 获取当前时间字符串(格式化)
	 * e.g yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowString(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(getNow());
	}

	/**
	 * 获取yyyy-MM-dd格式日期
	 * @return
	 */
	public static Date getNowDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD_FORMAT);
		String dateString = formatter.format(getNow());
		Date currentTime = null;
		try {
			currentTime = formatter.parse(dateString);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return currentTime;
	}

	/**
	 * 获取yyyy-MM-dd格式日期字符串
	 * @return
	 */
	public static String getNowDateString() {
		SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD_FORMAT);
		return formatter.format(getNow());
	}

	/**
	 * 获取yyyy-MM-dd格式日期字符串(格式化)
	 * @return
	 */
	public static String getNowDateString(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(getNow());
	}

	/**
	 * 获取格式日期字符串(格式化)
	 * @return
	 */
	public static String getString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	/**
	 * 获取当前时间戳(毫秒13位)
	 * @return Long
	 */
	public static Long getNowStamp() {
		return getNow().getTime();
	}

	/**
	 * 获取当前Unix时间戳(秒10位)
	 * @return Integer
	 */
	public static Integer getNowStampUnix() {
		return ((Long)(getNow().getTime()/1000)).intValue();
	}

	/**
	 * 时间戳转时间String
	 * @param stamp 13位或10位
	 * @return
	 */
	public static String stampToTimeString(Object stamp) {
		if(stamp == null) {
			return "";
		}
		String stampStr = stamp.toString();
		if(stampStr.length()!=13 && stampStr.length()!=10) {
			return "";
		}
		if(stampStr.length() == 10) {
			stampStr += "000";//补为13位
		}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(commonFormat);
        long lt = Long.parseLong(stampStr);
        Date date = new Date(lt);
        return  simpleDateFormat.format(date);
	}

	/**
	 * 时间戳转时间Date
	 * @param stamp 13位或10位
	 * @return
	 */
	public static Date stampToTime(Object stamp) {
		if(stamp == null) return null;
		String stampStr = stamp.toString();
		if(stampStr.length()!=13 && stampStr.length()!=10) return null;
		if(stampStr.length() == 10) stampStr += "000";//补为13位
        long lt = Long.parseLong(stampStr);
         return new Date(lt);
	}
	public static String stampToTimeString(Object stamp, String pattern) {
		if(stamp == null) return null;
		String stampStr = stamp.toString();
		if(stampStr.length()!=13 && stampStr.length()!=10) return null;
		if(stampStr.length() == 10) stampStr += "000";//补为13位
		long lt = Long.parseLong(stampStr);
		return format(new Date(lt),pattern);
	}

	/**
	 * 比较时间戳
	 * stampA - stampB >= val return true
	 * @param stampA
	 * @param stampB
	 * @param val
	 */
	public static boolean compareStamp(Integer stampA, Integer stampB, Integer val) {
		if(stampA==null) stampA = 0;
		if(stampB==null) stampB = 0;
		return stampA - stampB >= val;
	}

	/**
	 * 与当前时间戳比较
	 * @param stamp
	 * @param val
	 * @return
	 */
	public static boolean compareStamp(Integer stamp, Integer val) {
		if(stamp==null) stamp = 0;
		Integer stampNow = getNowStampUnix();
		return stampNow - stamp >= val;
	}

	public static Date getYYYYMMDD_Today() {
		return getNowDate();
	}

	public static java.sql.Date getJavaSQLDateNow() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	public static String DoFormatDate(Date dt_in, boolean ifShowTimePart) {
		if (ifShowTimePart)
			return (new SimpleDateFormat(commonFormat)).format(dt_in);
		else
			return (new SimpleDateFormat(YYYYMMDD_FORMAT)).format(dt_in);
	}


	/**
	 * Return short format datetime string.
	 *
	 * @param date java.util.Date
	 * @return short format datetime
	 */
	public static String shortFmt(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * Parse a datetime string.
	 *
	 * @param param datetime string, pattern: commonFormat.
	 * @return java.util.Date
	 */
	public static Date parse(String param) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(commonFormat);
		try {
			date = sdf.parse(param);
		} catch (ParseException ex) {
			date = new Date(System.currentTimeMillis());
			log.error(ex.getMessage());
		}
		return date;
	}


	/**
	 * 得到两个日期相差的小时数
	 */
	public static int subTime(String time1, String time2) {
		int result = 0;
		// "1979-02-20 08:00:00.000"
		Timestamp d1 = Timestamp.valueOf(time1);
		Timestamp d2 = Timestamp.valueOf(time2);
		result = (int) ((d2.getTime() - d1.getTime()) / 1000 / 60 / 60);
		return result;
	}

	/**
	 * 得到两个日期相差的小时数
	 */
	public static int subTime(Date time1, Date time2) {
		try {
			int result = 0;
			// "1979-02-20 08:00:00.000"
			result = (int) ((time1.getTime() - time2.getTime()) / 1000 / 60 / 60);
			return result;
		}catch (Exception e){
			log.error(e.getMessage());
			return 0;
		}
	}
	/**
	 * 取得本月第一天的日期
	 *
	 * @param DQRQ
	 * @return
	 */
	public static String getCurMonthFirstDay(String DQRQ) {
		String Year = DQRQ.substring(0, 4);
		String Month = DQRQ.substring(5, 7);
		String Day = "01";
		String strReturn = Year + "-" + Month + "-" + Day;
		return strReturn;
	}

	/**
	 * 取得本月第一天的日期
	 *
	 * @param DQRQ
	 * @return
	 */

	public static String getMonthFirstDay() {

		DateFormat df = new SimpleDateFormat(YYYYMMDD_FORMAT);
		Calendar calendar = Calendar.getInstance();
		Calendar cpcalendar = (Calendar) calendar.clone();
		cpcalendar.set(Calendar.DAY_OF_MONTH, 1);
		return df.format(new Date(cpcalendar.getTimeInMillis()));
	}


	/**
	 * 取得本月最后一天的日期
	 *
	 * @param DQRQ
	 * @return
	 */
	public static String getMonthEndDay() {

		DateFormat df = new SimpleDateFormat(YYYYMMDD_FORMAT);
		Calendar calendar = Calendar.getInstance();
		Calendar cpcalendar1 = (Calendar) calendar.clone();
		cpcalendar1.set(Calendar.DAY_OF_MONTH, 1);
		cpcalendar1.add(Calendar.MONTH, 1);
		cpcalendar1.add(Calendar.DATE, -1);

		return df.format(new Date(cpcalendar1.getTimeInMillis()));
	}

	/**
	 * 取得当前月的最大天数
	 *
	 *
	 * @return
	 */
	public static int getDaysOfMonth() {
		return getDaysOfMonth(getRightYear(), getRightMonth());
	}

	/**
	 * 取得一个月的最大天数
	 *
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		return (int) ((toLongTime(month == 12 ? (year + 1) : year, month == 12 ? 1 : (month + 1), 1)
				- toLongTime(year, month, 1)) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 取得下一个月的最大天数
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfNextMonth(int year, int month) {
		year = month == 12 ? year + 1 : year;
		month = month == 12 ? 1 : month + 1;
		return getDaysOfMonth(year, month);
	}

	/**
	 * 取得上个月的最大天数
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfProMonth(int year, int month) {
		year = month == 1 ? year - 1 : year;
		month = month == 1 ? 12 : month - 1;
		return getDaysOfMonth(year, month);
	}

	/**
	 * 取得当前的年 以整数形式返回， 例如：1999
	 *
	 * @return 返回当前年
	 */
	public static int getRightYear() {
		return getRightYear((Date) null);
	}

	/**
	 * 根据Date对象取得 年份 整数形式
	 *
	 * @param date java.util.Date 对象
	 * @return 返回 java.util.Date 的年份
	 */
	public static int getRightYear(Date date) {
		Calendar rightYear = Calendar.getInstance();
		if (date != null) {
			rightYear.setTime(date);
		}
		return rightYear.get(Calendar.YEAR);
	}

	/**
	 * Gets the RightMonth attribute of the DateUtil class
	 *
	 * @return The RightMonth value
	 */
	public static int getRightMonth() {
		return getRightMonth((Date) null);
	}

	/**
	 * Gets the RightMonth attribute of the DateUtil class
	 *
	 * @param date Description of Parameter
	 * @return The RightMonth value
	 */
	public static int getRightMonth(Date date) {
		Calendar rightMonth = Calendar.getInstance();
		if (date != null) {
			rightMonth.setTime(date);
		}
		return rightMonth.get(Calendar.MONTH) + 1;
	}

	/**
	 * 从给定的 year,mongth,day 得到时间的long值表示
	 *
	 * milliseconds after January 1, 1970 00:00:00 GMT).
	 *
	 * @param year  年
	 *
	 * @param month 月
	 *
	 * @param day   日
	 *
	 * @return 给定的 year,mongth,day 得到时间的long值表示
	 */
	public static long toLongTime(int year, int month, int day) {
		return toDate(year, month, day).getTime();
	}

	/**
	 * 从年月日得到一个Date对象
	 *
	 * @param year  年
	 *
	 * @param month 月
	 *
	 * @param day   日
	 *
	 * @return 得到的Date对象
	 */
	public static Date toDate(int year, int month, int day) {
		Calendar cld = Calendar.getInstance();
		if (cld == null) {
			cld = new GregorianCalendar();
		}
		cld.clear();
		cld.set(Calendar.YEAR, year);
		cld.set(Calendar.MONTH, month - 1);
		cld.set(Calendar.DAY_OF_MONTH, day);
		return cld.getTime();
	}

	/**
	 * 得到一个随机的字符串是(从时间取得)
	 *
	 * @return String
	 */
	public static String getUniqueID() {
		SimpleDateFormat dateform = new SimpleDateFormat("yyyyMMddHHmmhhSSS");// notice
																				// here

		GregorianCalendar calendar = new GregorianCalendar();
		String s = dateform.format(calendar.getTime());
		// String str = new Double(Math.random()).toString();
		// s = s + str.substring(2, str.length());
		return s;
	}

	public static Date getDateTimeOfDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return new Date(cal.getTimeInMillis());
	}

	public static Date StringToDate(String param) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_FORMAT);
		try {
			date = sdf.parse(param);
		} catch (ParseException ex) {
			log.error(ex.getMessage());
			return null;
		}
		return date;
	}

	public static Date StringToDate(String param, String format) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(param);
		} catch (ParseException ex) {
			log.error(ex.getMessage());
			return null;
		}
		return date;
	}


	public static String DateToString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_FORMAT);
		return sdf.format(date);
	}

	public static String TOStringNull(String cont) {
		if (ParamUtils.chkString(cont))
			return cont;
		return "";
	}

	public static String DateToStringAll(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(commonFormat);
		return sdf.format(date);
	}

	public static String NanoToString(String date) {
		return date.substring(0,10);
	}

	public static String DateToStringHHMM(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	public static String DateToStringHHMMSS(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(date);
	}

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static String getYyyyMMddStr(Date dt_in) {
		return (new SimpleDateFormat("yyyyMMdd")).format(dt_in);
	}

	public static String getYyMMddStr(Date dt_in) {
		return (new SimpleDateFormat("yyMMdd")).format(dt_in);
	}

	public static String getYyMMddStr() {
		return (new SimpleDateFormat("yyMMdd")).format(DateTimeUtil.getNow());
	}

	public static String getYyyy(Date dt_in) {
		return (new SimpleDateFormat("yyyy")).format(dt_in);
	}

	public static String getYy(Date dt_in) {
		return (new SimpleDateFormat("yy")).format(dt_in);
	}

	public static String getMMDD_str(Date date) {
		return (new SimpleDateFormat("MM/dd")).format(date);
	}

	public static String getYyyyMMStr(Date dt_in) {
		return (new SimpleDateFormat("yyyyMM")).format(dt_in);
	}

	public static Date getCurrentDayStart() {
		return parse(DateToString(getNowDate()) + " 00:00:00");
	}

	public static Date getCurrentDayend() {
		return parse(DateToString(getNowDate()) + " 23:59:59");
	}

	/**
	 * 使用用户格式格式化日期
	 *
	 * @param date    日期
	 * @param pattern 日期格式
	 * @author LLB
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	public static String shortFsm(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 计算两个时间相差的秒数
	 *
	 * @param startTime
	 * @param endTime
	 * @param format    时间格式
	 * @return 秒
	 * @author zang
	 */
	public static long dateDiffToSec(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long sec = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			sec = diff / ns;// 计算差多少秒
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return sec;
	}

	public static long dateDiffToSec(Date startTime, Date endTime) {
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long sec = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = endTime.getTime() - startTime.getTime();
			sec = diff / ns;// 计算差多少秒
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sec;
	}

	// 把yyyymmdd转成yyyy-MM-dd格式

	public static String format_YYYY_MM_DD(String str) {
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat(YYYYMMDD_FORMAT);
		String sfstr = "";
		try {
			if (ParamUtils.chkString(str)) {
				sfstr = sf2.format(sf1.parse(str));
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return sfstr;
	}

	// 把yyyy-MM-dd转成yyyymmdd格式

	public static String format_YYYYMMDD(String str) {
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat(YYYYMMDD_FORMAT);
		String sfstr = "";
		try {
			if (ParamUtils.chkString(str)) {
				sfstr = sf1.format(sf2.parse(str));
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return sfstr;
	}

	public static int getHours(Date date) {
		Calendar rightYear = Calendar.getInstance();
		if (date != null) {
			rightYear.setTime(date);
		}
		return rightYear.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar rightYear = Calendar.getInstance();
		if (date != null) {
			rightYear.setTime(date);
		}
		return rightYear.get(Calendar.MINUTE);
	}

	/**
	 * 得到几天后的时间
	 *
	 * @param d
	 * @param day
	 * @param format
	 * @return
	 */
	public static Date getDateAfter(Date d, int day, String format) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return getDateFormart(now.getTime(), format);
	}
	public static String getDateAfterToString(Date d, int day, String format) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return format(now.getTime(),format);
	}

	/**
	 * 传入的参数是java.util.Date格式
	 */
	public static Date getDateFormart(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		Date currentTime = null;
		try {
			currentTime = formatter.parse(dateString);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return currentTime;
	}

	/**
	 * 取当前日期，返回日期类型
	 *
	 * @return
	 */

	public static Date getDateTimeOfMinutes(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return new Date(cal.getTimeInMillis());
	}

	public static Date getDateTimeOfSeconds(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int getDaysBetween(Date smdate, Date bdate) {
		long between_days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_FORMAT);
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int getDaysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取两个时间的分钟数差
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static long getMinutesBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(commonFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		return (time2 - time1) / (1000 * 60);
	}

	/**
	 * 获取两个时间的分钟数差
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static long getMinutesBetween(Date smdate, Date bdate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		return (time2 - time1) / (1000 * 60);
	}

	/**
	 * 获取两个时间的秒数差
	 *
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static Long getSecondsBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(commonFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		Long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		Long time2 = cal.getTimeInMillis();
		return (time2 - time1) / 1000;
	}





	/**
	 * 获取这种格式的日期字符串 yyMMdd 2017-05-03--->170503
	 *
	 * @param date
	 * @return
	 */
	public static String DateToStringYY(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(date);
	}

	/**
	 * 根据日期获取星期几
	 *
	 * @param date 日期
	 * @return 星期几
	 */
	public static String getWeek(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[w];
	}

	/**
	 * 根据日期获取周几
	 *
	 * @param date 日期
	 * @return 周几
	 */
	public static String getWeek2(Date date) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[w];
	}
	/**
	 * 根据日期获取周几
	 *
	 * @param date 日期
	 * @return 周几
	 */
	public static String getWeek3(Date date) {
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDays[w];
	}

	/**
	 * 获取最近一周的年月日（包含今天）
	 * @return
	 */
	public static List<String> getNearWeek (Integer day) {
		ArrayList<String> week = new ArrayList<>();
		for (int i = day-1; i >= 0; i--) {
			week.add(getPastDate(i));
		}
		return week;
	}
	/**
	 * 获取过去第几天的日期
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD_FORMAT);
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取两个时间相差的天数小时分钟
	 * @return
	 */
	public static String getDHMByDate(String startTime,String endTime){
		Date from = DateTimeUtil.parse(startTime);
		Date to = DateTimeUtil.parse(endTime);
		String diff = "";
		long nd = 1000 * 24 * 60 * 60L;
		long nh = 1000 * 60 * 60L;
		long nm = 1000 * 60L;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long _diff = to.getTime()-from.getTime();
		if (_diff <= 0) {
			diff = "0天 0小时 0分";
			return diff;
		}
		// 计算差多少天
		long day = _diff / nd;
		// 计算差多少小时
		long hour = _diff % nd / nh;
		// 计算差多少分钟
		long min = _diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		if (day > 0) {
			diff += day + "天 ";
		}else{
			diff += "0天 ";
		}
		if (hour > 0) {
			diff += hour + "小时 ";
		}else{
			diff += "0小时 ";
		}
		if (min > 0) {
			diff += min + "分";
		}else{
			diff += "0分";
		}
		return diff;
	}

	/**
	 * 根据分钟数天数小时分钟
	 * @return
	 */
	public static String getDHMByMinute(int minute){
		Long time = minute*60*1000L;
		String diff = "";
		long nd = 1000 * 24 * 60 * 60L;
		long nh = 1000 * 60 * 60L;
		long nm = 1000 * 60L;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		if (time <= 0) {
			diff = "0天 0小时 0分";
			return diff;
		}
		// 计算差多少天
		long day = time / nd;
		// 计算差多少小时
		long hour = time % nd / nh;
		// 计算差多少分钟
		long min = time % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		if (day > 0) {
			diff += day + "天 ";
		}else{
			diff += "0天 ";
		}
		if (hour > 0) {
			diff += hour + "小时 ";
		}else{
			diff += "0小时 ";
		}
		if (min > 0) {
			diff += min + "分";
		}else{
			diff += "0分";
		}
		return diff;
	}

	/**
	 * 根据秒获取时分秒格式
	 * @param s
	 * @return
	 */
	public static String getHMSBySecondsType2(int s){
		String result = "";
		int hour = (s%(60*60*24))/(60*60);
		int min = (s%(60*60))/60;
		int seconds = s%60;
		if(hour!=0){
			result += (hour)+"小时";
		}else{
			result += "0小时";
		}
		if(min!=0){
			result += (min)+"分钟";
		}else{
			result += "0分钟";
		}
		if(seconds!=0){
			result += seconds+"秒";
		}else{
			result += "0秒";
		}
		return result;
	}

	public static Object getHMSBySeconds(int s) {
		String result = "";
		if (ObjectUtil.isEmpty(s)) {
			s = 0;
		}
		int hour = (s%(60*60*24))/(60*60);
		int min = (s%(60*60))/60;
		int seconds = s%60;
		if(hour!=0){
			result += (hour>=10?hour:"0"+hour)+":";
		}
		if(min!=0){
			result += (min>=10?min:"0"+min)+":";
		}else{
			result += "00:";
		}
		if(seconds!=0){
			result += (seconds>=10?seconds:"0"+seconds)+"";
		}else{
			result += "00";
		}
		return result;
	}
	public static Object getHMBySecondsOfTimeRank(int s) {
		String result = "";
		if (ObjectUtil.isEmpty(s)) {
			s = 0;
		}
		int hour = s/(60*60);
		int min = (s%(60*60))/60;
		if(hour!=0){
			result += (hour>=10?hour:"0"+hour)+"h";
		}else{
			result += "00h";
		}
		if(min!=0){
			result += (min>=10?min:"0"+min)+"m";
		}else{
			result += "00m";
		}
		return result;
	}

	/**
	 * 根据秒获取分钟或者小时（排行榜用）
	 * @param s
	 * @param type
	 * @return
	 */
	public static Object getHOrMByS(int s,String type) {
		if (ObjectUtil.isEmpty(s)) {
			s = 0;
		}
		int hour = s/(60*60);
		int min = (s%(60*60))/60;
		if(type.equals("1")){
			return hour;
		}else{
			return min;
		}
	}
	public static Object getHMBySecondsOfTimeRankType2(int s) {
		String result = "";
		if (ObjectUtil.isEmpty(s)) {
			s = 0;
		}
		int hour = s/(60*60);
		int min = (s%(60*60))/60;
		if(hour!=0){
			result += (hour>=10?hour:"0"+hour)+"小时";
		}else{
			result += "00小时";
		}
		if(min!=0){
			result += (min>=10?min:"0"+min)+"分";
		}else{
			result += "00分";
		}
		return result;
	}
	@SneakyThrows
	public static Date operationHour(String operDate,int number){
		SimpleDateFormat sdfFormat = new SimpleDateFormat(commonFormat);
		Date date = sdfFormat.parse(operDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY,number);
		date = cal.getTime();
		return date;
	}

	/**
	 * 获取两个时间的之间的日期
	 * @param startTime 开始时间
	 * @param endTime	结束时间
	 * @param minTime 最小时间
	 * @param maxTime 最大时间
	 * @return
	 */
	public static List<String> getDays(String startTime, String endTime,String minTime,String maxTime) {

		// 返回的日期集合
		List<String> result = new ArrayList<>();


		List<Date> days = new ArrayList<Date>();
		DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD_FORMAT);
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);
			Date mTime = dateFormat.parse(minTime);
			Date xTime = dateFormat.parse(maxTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(tempStart.getTime());
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
			for (Date day : days) {
				if((day.after(mTime)||day.equals(mTime))&&(day.before(xTime)||day.equals(xTime))){
					result.add(dateFormat.format(day));
				}
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		return result;
	}

	/**
	 * 将字幕时间转换为毫秒
	 * @param subtitleTime 字幕时间
	 * @param type 字幕类型 1=srt 2=vtt
	 * @return 毫秒数
	 */
	public static Integer subtitleTimeToMS(String subtitleTime,String type){
		String[] arrOne = subtitleTime.split(type.equals("1")?",":"\\.");
		int lastMS = Integer.parseInt(arrOne[1]);
		String[] arrTwo = arrOne[0].split(":");
		int hour = Integer.parseInt(arrTwo[0]);
		int minute = Integer.parseInt(arrTwo[1]);
		int second = Integer.parseInt(arrTwo[2]);
		return hour*60*60*1000+minute*60*1000+second*1000+lastMS;
	}

	/**
	 * 获取两个日期中间的日期 参数格式都是 yyyy-MM-dd
	 * @return
	 */
	public static List<String> getDaysByBetweenDate(String startDate,String endDate){
		// 返回的日期集合
		List<String> days = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD_FORMAT);
		try {
			Date start = dateFormat.parse(startDate);
			Date end = dateFormat.parse(endDate);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return days;
	}

	/**
	 * 获取本周的第一天
	 * @return String
	 * **/
	public static Date getWeekStart(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 0);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		return cal.getTime();
	}
	/**
	 * 获取本周的最后一天
	 * @return String
	 * **/
	public static Date getWeekEnd(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		cal.add(Calendar.DAY_OF_WEEK, 1);
		return cal.getTime();
	}

	/**
	 * 获取今天是 本周、本月、本年的第几天
	 * @param type 类型 1=周 2=月 3=年
	 */
	public static int getDayNumberForWeekMonthYear(String type){
		Map<String, Integer> mapInt = new LinkedHashMap<String, Integer>();
		Calendar calendar = Calendar.getInstance();
		Date today = new Date();
		calendar.setTime(today);// 此处可换为具体某一时间
		if("1".equals(type)){
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
			if (weekDay == 1) {
				weekDay = 7;
			} else {
				weekDay = weekDay - 1;
			}
			return weekDay;
		}else if("2".equals(type)){
			return calendar.get(Calendar.DAY_OF_MONTH);
		}else if("3".equals(type)){
			return calendar.get(Calendar.DAY_OF_YEAR);
		}else{
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println(getWeekStart());
		System.out.println(getWeekEnd());
	}
}
