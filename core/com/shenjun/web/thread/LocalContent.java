package com.shenjun.web.thread;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.shenjun.db.type.SqlServerMap;
import com.shenjun.manager.CommonManager;
import com.shenjun.plugins.sftp.ConnectConfig;
import com.shenjun.plugins.sftp.SftpLocal;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.system.SystemConfig;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class LocalContent {
	
	private static final String defaultCm="db";
	
	public static String defaultDb= SystemConfig.DEFAULT_DB;
	
	private static final Log log = LogFactory.getLog(LocalContent.class);
	
	private static ThreadLocal<User<Object>> localUser = new ThreadLocal<User<Object>>();

	public static void setUser(User<Object> user) {
		localUser.set(user);
	}

	public static User<Object> getUser() {
		User<Object> u=localUser.get();
		return u==null?new User<Object>():u;
	}
	
	
	
	/**
	 * 本地Session记录线程
	 */
	private static ThreadLocal<Map<String, CommonManager>> cmLocal = new ThreadLocal<Map<String, CommonManager>>();

	protected static void setCm(String key ,CommonManager cm) {
		if(cmLocal.get()==null){
			Map<String, CommonManager> cmap=new HashMap<String, CommonManager>();
			cmap.put(key, cm);
			cmLocal.set(cmap);
		}else{
			cmLocal.get().put(key, cm);
		}
		
	}
	
	public static Map<String, CommonManager> getAllCm(){
		return cmLocal.get();
	}

	protected static CommonManager getCm(String key,boolean isTransaction) {
		Map<String, CommonManager> map=((Map<String, CommonManager>)cmLocal.get());
		if("".equals(key)||key==null){
			key=SystemConfig.DEFAULT_DB;
		}else if(SqlServerMap.get(key)==null){
			key=SystemConfig.DEFAULT_DB;
		}
		CommonManager cm = null;
		if(map==null||map.get(key)==null){
			//HibernateSuperDaoSupport hs=SpringUtil.getBean(key, HibernateSuperDaoSupport.class);
			try{
				LocalContent.setConnLocal(key);
				cm = (CommonManager) SpringUtil.getBean(defaultCm);
				
//				JdbcTemplate jdbcTemplate =SpringUtil.getBean("jdbcTemplate", JdbcTemplate.class);
//				cm.setJdbcTemplate(jdbcTemplate);
				
				cm.setConnKey(key);
					
				if(isTransaction){
					//org.springframework.transaction.jta.JtaTransactionManager
					cm.transactionManager=SpringUtil.getBean("transactionManager",DataSourceTransactionManager.class);
					cm.transactionManager.setDataSource(cm.getJdbcTemplate().getDataSource());
					cm.def = new DefaultTransactionDefinition();
					cm.def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
					cm.status = cm.transactionManager.getTransaction(cm.def);
				}
				
				LocalContent.setCm(key,cm);
				
				log.debug("new Lc.getConn("+key+") " +
						"Thread:("+Thread.currentThread().getName()+") " +
								"conn sign:"+key+"," +
										"isTransaction:"+isTransaction+",ex:"+cm);//+",conn:"+cm.getConnection());
				
				map=((Map<String, CommonManager>)cmLocal.get());
				
			}catch(Exception e){
				log.error("("+Thread.currentThread().getName()+")获取数据库连接错误，信息:"+e.getMessage(),e.getCause());
			}
		}else{
			cm =map.get(key);
			try {
				LocalContent.setConnLocal(key);
//				cm.setLocalSession(cm.getHsds(LocalContent.class).getLocalSession()
//						, key);
				log.debug("exist Lc.getConn("+key+") " +
						"Thread:("+Thread.currentThread().getName()+") " +
								"conn sign:"+key+"," +
										"isTransaction:"+isTransaction+",ex:"+cm);//+",conn:"+cm.getConnection());
			} catch (Exception e) {
				log.error("("+Thread.currentThread().getName()+")获取数据库连接错误，信息:"+e.getMessage(),e.getCause());
			}
		}
		return cm;
	}

	/**
	 * 数据库连接记录本地线程
	 */
	private static ThreadLocal<String> connLocal = new ThreadLocal<String>();

	public static void setConnLocal(String key) {
		connLocal.set(key);
	}

	public static String getConnLocal() {
		return (String)connLocal.get();
	}
	
	/**
	 * 错误信息记录本地线程
	 */
	private static ThreadLocal<String> errorLocal = new ThreadLocal<String>();

	public static void setError(String error) {
		if(error!=null){
			log.error("线程("+Thread.currentThread().getId()+")。error:"+error);
			errorLocal.set(errorLocal.get()+"/"+error);
		}else{
			errorLocal.set(null);
		}
	}

	public static String getError() {
		return (String)errorLocal.get();
	}
	
	
	/*************************************************************/
	private static ThreadLocal<Map<String,SftpLocal>> sfpt = new ThreadLocal<Map<String,SftpLocal>>();
	
	public static SftpLocal getSftpCm(ConnectConfig connConfig){
		String key =connConfig.getSign();
		
		if(sfpt.get()==null){
			sfpt.set(new HashMap<String, SftpLocal>());
		}
		
		if(sfpt.get().get(key)==null){
			sfpt.get().put(connConfig.getSign(), new SftpLocal(connConfig));
		}
		SftpLocal sftpObj= sfpt.get().get(key);
		sftpObj.initConn();
		return sftpObj;
	}
	
	public static Map<String,SftpLocal> getAllSfptCm(){
		return sfpt.get();
	}
	
}
