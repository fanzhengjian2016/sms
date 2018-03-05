package com.shenjun.web.listens;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.shenjun.io.data.TableStateSerializable;
import com.shenjun.system.SystemConfig;
import com.shenjun.web.load.SqlServerLoad;

/**
 * @author: 沈军
 * @category 监听Web启动和注销的类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class ContentListen implements ServletContextListener{
	
	
	private static Logger log = Logger.getLogger(ContentListen.class.getName());
	
	@SuppressWarnings("unused")
	private static ServletContext servletContext;
	
	public static ServletContext getServletContext(){
		return servletContext;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * Web 启动调用的方法
	 */
	@SuppressWarnings("static-access")
	public void contextInitialized(ServletContextEvent event) {
		
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		
		try {
			log.info("-----------------------"+SystemConfig.SYSTEM_NAME+"Web service starting !!-------------------------------");	
			servletContext=event.getServletContext();
			
			
			/**
			 * *****************载入多数据库结构**************************************************
			 */
			log.info("loading multi-SqlServer seting.......");
			SqlServerLoad.builder sl=new SqlServerLoad.builder();
			sl.build().load();
			
			log.info("loading table state..........");
			TableStateSerializable.load();
		
		} catch (Exception e) {
			log.error("程序启动发生异常"+e.getMessage(),e.getCause());
		}	
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 * Web 销毁调用方法
	 */
	@SuppressWarnings("deprecation")
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.info("saving table state...............");
		TableStateSerializable.save();
		log.info(""+SystemConfig.SYSTEM_NAME+"Web service shutdown");
	}

}
