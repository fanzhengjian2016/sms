package com.shenjun.aspectj;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shenjun.dao.JboxDaoSupport;
import com.shenjun.manager.CommonManager;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.LocalContent;

@Aspect
public class SettingConnMonitor {
	private static Log log=LogFactory.getLog(SettingConnMonitor.class);
	
	
	@Around("execution(public * com.shenjun.dao.JboxDaoSupport.create*(..) )")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
		
		JboxDaoSupport j = (JboxDaoSupport)pjp.getTarget();
		String conn=j.getConnKey();
		if(StringUtil.isNB(conn)){
			Lc.setConnLocal(conn);
			log.debug("SettingConnMonitor set conn db key:"+conn);
		}
		return pjp.proceed();
	}
	
	public static void main(String[] args) throws SQLException, InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/*.xml");
		
		LocalContent.setConnLocal("mssql");
		CommonManager t =(CommonManager) ctx.getBean("db");
		t.setConnKey("mssql");
		t.createTest();
	}
}
