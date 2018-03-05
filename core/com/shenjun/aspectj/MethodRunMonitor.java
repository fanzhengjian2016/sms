package com.shenjun.aspectj;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 性能监听
 * @author Administrator
 *
 */
@Aspect
public class MethodRunMonitor {
	
	private static Log logger=LogFactory.getLog(MethodRunMonitor.class);
	
	public static void main(String[] args) throws SQLException, InterruptedException {
		/*ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/*.xml");
		
		LocalContent.setConnLocal("mssql");
		CommonManager t =(CommonManager) ctx.getBean("db");*/
		//System.out.println(t.transactionManager);
//		int i=t.createExecSql(
//				"update xml_table set xmlfield=? where id=?",
//				SqlUtil.Csv("<xml><a>ccee</a></xml>",SqlType.XML), "111");
//		
//		System.out.println(i+"+++++++++++++++");
//		t.releaseConnection(t.getConnection());
		/*
		int i=0;
		while(true){i++;
		CommonManager t =(CommonManager) ctx.getBean("db");
		System.out.println(t.getJdbcTemplate()+"--------"+"->"+t);
		LocalContent.setConnLocal("mysql");
//		Connection conn = t.getConnection();
//		ResultSet rs = conn.prepareStatement("select 1").executeQuery();
//		rs.next();
//		System.out.println(rs.getString(1)+"----");
		//t.createSQLQuery("select 1");
		t.createSQLQuery("select 1");
		t.createSQLQuery("select 1");
		t.createSQLQuery("select 1");
		t.createSQLQuery("select 1");
		
		t.releaseConnection(t.getConnection());
		
//			DataSourceUtils.getConnection(t.getJdbcTemplate().getDataSource());
//			t.getJdbcTemplate().query("select 1", new ResultSetExtractor<Object>() {
//				
//				@Override
//				public Object extractData(ResultSet rs) throws SQLException,
//						DataAccessException {
//					return null;
//				}
//			});
			

			System.out.println("--------"+i);
			Thread.sleep(2000);
		}*/
		
	}
	
	@Pointcut("execution(public * com.shenjun.dao..*(..) )")
	public void service(){
	}
	
	
	@Pointcut("within(*com.shenjun.dao.JboxDaoSupport)")
	public void with(){
		
	}
	
	@Pointcut("bean(*Manager)")
	public void bean(){}
	
	/**
	 *运行耗时情况
	 * @param pjp
	 * @return 	 "service()&&@annotation(com.shenjun.annotation.AopAspectj)"
	 * @throws Throwable
	 */
	// 拦截对目标对象方法调用  
	//@Around("target(com.shenjun.dao.JboxDaoSupport)&&(!execution(public * com.shenjun.dao..set*(..)))&&(!execution(public * com.shenjun.dao..get*(..)))")
	@Around("execution(public * com.shenjun.dao.JboxDaoSupport.create*(..) )")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
		long start=System.currentTimeMillis();
		String method=pjp.getSignature().getName();
		
		logger.debug("方法("+method+")调用开始时间："+start+"毫秒.........................................");
		Object retVal=pjp.proceed();
		long end=System.currentTimeMillis();
		logger.debug("方法("+method+")调用结束时间："+end+"毫秒,运行耗时："+(end-start)+"毫秒...........................................");
		return retVal;
	}
}

