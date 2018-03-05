package com.shenjun.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/*******************************************************************************
 * 日期处理类,此类已过时，请使用DateUtil类操作
 * @author 沈军
 * 
 */
@Deprecated
public class DateConvertUtils {
	private static Logger log = Logger.getLogger(DateConvertUtils.class);
	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";

	/**
	 *  "yyyy-MM-dd"
	 */
	public static final String SHORT_DATE = "yyyy-MM-dd";
	
	/**
	 *  "yyyyMMdd"
	 */
	public static final String SHORT_DATE_NO = "yyyyMMdd";
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
	
	

	/***************************************************************************
	 * 获得格式为yyyy-MM-dd的日期
	 * 
	 * @return
	 */
	public static String getShortDate() {
		return new SimpleDateFormat(SHORT_DATE)
				.format(new Date());
	}
	
	/*******************************************************
	 * 获得格式为 指定格式的时间
	 */
	public static String get(String Type) {
		return new SimpleDateFormat(Type)
				.format(new Date());
	}
	
	/***************************************************************************
	 * 获得格式为yyyy-MM-dd HH:mm:ss的日期
	 * 
	 * @return
	 */
	public static String getLongDate() {
		return new SimpleDateFormat(LONG_DATE)
				.format(new Date());
	}

	/***************************************************************************
	 * 获得当前的时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("获取系统毫秒数方法1："+Long.toString(new Date().getTime()));
        System.out.println("获取系统毫秒数方法2："+Long.toString(System.currentTimeMillis()));

	}
	
	/******************************过时的方法*******************************************************************/
	/**
	 * 传入Date和转换的字符串进行转换成String型的字符串
	 */
	@Deprecated
	public static String DateToString(Date date,String Type){
		
		if(date==null)return "";
		
		return new SimpleDateFormat(Type)
		.format(date);
	}
	
	/**
	 * 将指定格式的字符串转换成指定的日期
	 * @param dateString
	 * @param type
	 * @return
	 */
	@Deprecated
	public static Date convertToDate(String dateString, String type) {
		DateFormat df = null;
		Date dt = null;
		try {
			if (type.equals(LONG_DATE)) {
				df = new SimpleDateFormat(LONG_DATE);
				dt = df.parse(dateString);
			} else if (type.equals(SHORT_DATE)) {
				df = new SimpleDateFormat(SHORT_DATE);
				dt = df.parse(dateString);
			} else {
				log.error("当前日期格式不存在!");
			}
		} catch (Exception e) {
			log.error(e);
			log.error("日期格式错误，请检查你的时间格式");
		}
		return dt;
	}
	
	/**
	 * 将指定格式的字符串转换成指定的日期
	 * @param dateString
	 * @return
	 */
	@Deprecated
	public static Date convertToDate(String dateString) {
		DateFormat df = null;
		Date dt = null;
		try {
			if (dateString.indexOf(":")>=0) {
				df = new SimpleDateFormat(LONG_DATE);
				dt = df.parse(dateString);
			}else if (dateString.indexOf("-")>=0) {
				df = new SimpleDateFormat(SHORT_DATE);
				dt = df.parse(dateString);
			} else{
				df = new SimpleDateFormat(SHORT_DATE_NO);
				dt = df.parse(dateString);
			}
		} catch (Exception e) {
			log.error(e);
			log.error("日期格式错误，请检查你的时间格式");
		}
		return dt;
	}

}
