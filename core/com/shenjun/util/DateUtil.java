package com.shenjun.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Date操作类
 * @author shenjun
 *
 */
public class DateUtil{
	private static Logger log = Logger.getLogger(DateUtil.class);
	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * "yyyyMMdd HH:mm:ss"
	 */
	public static final String LONG_DATE2 = "yyyyMMdd HH:mm:ss";

	/**
	 *  "yyyy-MM-dd"
	 */
	public static final String SHORT_DATE = "yyyy-MM-dd";
	
	/**
	 *  "yyyyMMdd"
	 */
	public static final String SHORT_DATE2 = "yyyyMMdd";
	
	/**
	 * HH:mm:ss
	 */
	public static final String TIME ="HH:mm:ss";
	
	/**
	 * "yyyy"
	 */
	public static final String YEAR_DATE = "yyyy";
	
	/**
	 * "MM"
	 */
	public static final String MOUTH_DATE = "MM";
	
	/**
	 * "dd"
	 */
	public static final String DAY_DATE = "dd";
	
	/**
	 * 
	 * @param date
	 * @param formatString DateUtil里的提供常用格式字符串
	 * @return
	 */
	public static String toStr(Date date,String formatSting){
		if(date==null)return "";
		
		return new SimpleDateFormat(formatSting).format(date);
	}
	/**
	 * 获取当前时间的指定格式字符串
	 * @param formatString  DateUtil里的提供常用格式字符串
	 */
	public static String get(String formatSting){
		return new SimpleDateFormat(formatSting).format(java.util.Calendar.getInstance().getTime()); 
	}
	
	/**
	 * 通过时间 毫秒数获取时间
	 * the milliseconds since January 1, 1970, 00:00:00 GMT.
	 * @param time 1970来的毫秒数
	 * @return
	 */
	public static Date get(long time){
		return new Date(time);
	}
	
	/**
	 * 转换成 yyyy-MM-dd HH:mm:ss
	 * @param date Date对象
	 * @return 
	 */
	public static String toStr(Date date){
		return toStr(date,DateUtil.LONG_DATE);
	}
	
	/**
	 * 获得格式为yyyy-MM-dd的日期字符串
	 * @return
	 */
	public static String getShortDate() {
		return get(DateUtil.SHORT_DATE);
	}
	/**
	 * 转换成格式为yyyy-MM-dd的日期字符串
	 * @param date
	 * @return
	 */
	public static String toShortDate(Date date){
		return toStr(date,DateUtil.SHORT_DATE);
	}
	
	/**
	 * 获得格式为yyyy-MM-dd HH:mm:ss的日期字符串
	 * @return
	 */
	public static String getLongDate() {
		return get(DateUtil.LONG_DATE);
	}

	/**
	 * 获得当前的时间 HH:mm:ss
	 * @return
	 */
	public static String getNowTime() {
		return get(DateUtil.TIME);
	}
	/**
	 * 转换成格式为HH:mm:ss的日期字符串
	 * @param date
	 * @return
	 */
	public static String toTime(Date date){
		return toStr(date,DateUtil.TIME);
	}
	/**
	 * 将日期字符串转换成日期
	 * @param dateString 日期字符串
	 * @param formatString DateUtil里的提供常用格式字符串
	 * @return
	 */
	public static Date toDate(String dateString,String formatString){
		try {
			return new SimpleDateFormat(formatString).parse(dateString);
		} catch (ParseException e) {
			log.error("DateUtil->toDate error:"+e.getMessage(), e.getCause());
		}
		return null;
	}
	
	/**
	 * 将日期字符串转换成日期(转换格式进行尝试识别)
	 * @param dateString 日期字符串
	 * @return
	 */
	public static Date toDate(String dateString){
		String formatString="";
		if(StringUtil.isNB(dateString)){
			if(RegexUtil.isFindStr("^\\d+[-]\\d+[-]\\d+\\s\\d+[:]\\d+[:]\\d+$", dateString)){
				formatString=LONG_DATE;
			}else if(RegexUtil.isFindStr("^\\d+\\s\\d+[:]\\d+[:]\\d+$", dateString)){
				formatString=DateUtil.LONG_DATE2;
			}else if(RegexUtil.isFindStr("^\\d+[-]\\d+[-]\\d+$", dateString)){
				formatString=DateUtil.SHORT_DATE;
			}else if(RegexUtil.isFindStr("^\\d+\\d+\\d+$", dateString)){
				formatString=DateUtil.SHORT_DATE2;
			}else if(RegexUtil.isFindStr("^\\d+[:]\\d+[:]\\d+$", dateString)){
				formatString=DateUtil.TIME;
			}else{
				formatString=LONG_DATE;
			}
		}
		return toDate(dateString,formatString);
	}
	
	public static void main(String[] args) {
		String str="2010-01-01 20:22:33.022222";
		System.out.println(DateUtil.toDate(str));
	}
}
