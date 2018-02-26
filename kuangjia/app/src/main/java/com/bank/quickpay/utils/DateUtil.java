package com.bank.quickpay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @ClassName: DateUtil
 * @Description: 日期工具类
 *
 * 
 */
public class DateUtil {

	/**
	 * 字符串转换成日期转字符串
	 *
	 * @param str
	 * @return date
	 */
	public static String  StrToDateStr(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			if(str!=null){
				date = format.parse(str);
			}

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			if(date!=null){
				str = format1.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;


	}
	public static String  StrToDateStr(String str,String srcpattern,String topattern) {

		SimpleDateFormat format = new SimpleDateFormat(srcpattern);
		Date date = null;
		try {
			if(str!=null){
				date = format.parse(str);
			}

			SimpleDateFormat format1 = new SimpleDateFormat(topattern);
			if(date!=null){
				str = format1.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;


	}
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(date);
		return str;
	}
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToString(Date date) {
		String str =null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date!=null){
			 str = format.format(date);
		}
		
		return str;
	}
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToShortStr(Date date) {
//		"yyyy-MM-dd HH:mm:ss"
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	public static String DateToStrForICO(Date date) {
//		"yyyy-MM-dd HH:mm:ss"
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			if(str!=null){
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 字符串转换成日期yyyyMMddHHmm格式
	 * 
	 * @param yyyyMMddHHmm
	 * @return date
	 */
	public static Date StrToDate2(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = null;
		try {
			if(str!=null){
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 
	 * @Title: parseDate
	 * @Description: 把字符串解析为日期
	 * @param dateStr
	 * @param format
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String format) {

		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			date = (Date) df.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @Title: parseDate
	 * @Description: 把字符串解析为日期
	 * @param dateStr
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @Title: format
	 * @Description: 把日期格式化输出为字符串
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 
	 * @Title: formatDate
	 * @Description: 把日期解析为字符串
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @Title: getYear
	 * @Description: 返回当前年
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @Title: getMonth
	 * @Description: 返回当前月
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @Title: getDay
	 * @Description:返回当前日
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @Title: getHour
	 * @Description: 返回当前小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * @Title: getMinute
	 * @Description: 返回当前分钟
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 
	 * @Title: getSecond
	 * @Description: 返回当前秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 
	 * @Title: getMillis
	 * @Description: 返回当前毫秒
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 
	 * @Title: getTime
	 * @Description: 返回当前时分秒
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 
	 * @Title: getDateTime
	 * @Description: 返回当前年月日 时分秒
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * @Title: addDate
	 * @Description: 增加 几天后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: diffDate
	 * @Description: 两个日期相差几天   前者 减 后者
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static int diffDate(Date dateStart, Date dateEnd) {
		return (int) ((getMillis(dateStart) - getMillis(dateEnd)) / (24 * 3600 * 1000));
	}

	/**
	 * 
	 * @Title: getMonthBegin
	 * @Description: 返回某天所属月份的第一天
	 * @param strdate
	 * @return
	 */
	public static String getMonthBegin(String strdate) {
		Date date = parseDate(strdate);
		return format(date, "yyyy-MM") + "-01";
	}

	/**
	 * 
	 * @Title: getMonthEnd
	 * @Description: 返回某天所属月份的最后一天
	 * @param strdate
	 * @return
	 */
	public static String getMonthEnd(String strdate) {
		Date date = parseDate(getMonthBegin(strdate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 
	 * @Title: getGMT8Time
	 * @Description: 获取东八区时间
	 * @return
	 */
	public static Date getGMT8Time() {
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),
					Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			gmt8 = day.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			gmt8 = null;
		}
		return gmt8;
	}
}