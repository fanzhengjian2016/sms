package com.shenjun.timertask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.web.services.Service;



public class ServicesSynchroTimerTask{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Log log=LogFactory.getLog(ServicesSynchroTimerTask.class);
	
	@SuppressWarnings("unused")
	private Service[] service;
	
	
	
	/**
	 * 数据同步作业
	 *
	 */
	public void task(){
		//log.info("task:"+Thread.currentThread().getName());
		for(Service s:service){
			try{
				s.execute();
			}catch (Exception e) {
				log.error("task ERROR:"+e.getMessage(),e.getCause());
			}
		}
		
	}

	public void setService(Service[] service) {
		this.service = service;
	}
}
