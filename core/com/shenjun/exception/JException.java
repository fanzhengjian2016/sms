package com.shenjun.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static  Log log = LogFactory.getLog(JException.class);
	
	public JException(String exceptionMessage,Class cls){
		log.error("cls"+cls+":"+exceptionMessage);
	}

}
