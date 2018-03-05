package com.shenjun.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.shenjun.db.conn.JdbcConfigLoads;
import com.shenjun.plugins.spring.InitExecuteFactory;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.web.content.ApplicationContent;

public class WebInitServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(WebInitServlet.class);
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		log.debug("load jdbc.properties file to spring...");
		JdbcConfigLoads.reloadCfgConn();
		
		log.debug("load ApplicationContent buildAttrib...");
		ApplicationContent.buildAttrib();
		
		
		log.debug("init system params...............");
		InitExecuteFactory ief=SpringUtil.getBean("initfactory", InitExecuteFactory.class);
		if(ief!=null){
			ief.execute();
		}else{
			log.error("spring config bean[initfactory] is null");
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest reqquest,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(1001, "本页面不允许发送请求");
	}

}
