package com.shenjun.web.listens.util;

import javax.servlet.ServletContextEvent;

import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

public class Log4jSuperConfigListener extends Log4jConfigListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Log4jWebConfigurer.initLogging(event.getServletContext());
	}
}
