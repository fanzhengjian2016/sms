package com.shenjun.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestMethodErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static  Log log = LogFactory.getLog(RequestMethodErrorException.class);
	
	public RequestMethodErrorException(String exceptionMessage){
		log.error("请求的方法参数不符合返回规则:"+exceptionMessage);
	}

}
