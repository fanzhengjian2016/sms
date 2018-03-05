package com.shenjun.web.thread;

import java.util.Map;

import com.shenjun.manager.CommonManager;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.system.SystemConfig;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class Lc extends Struts2Util{
	
	/**
	 * 所有数据连接是否提交，不提交则回滚
	 * @param bool 
	 */
	public static void isCommit(boolean bool){
		 Map<String, CommonManager> cmap=LocalContent.getAllCm();
		 if(cmap!=null){
			 for(Map.Entry<String, CommonManager> ent:cmap.entrySet()){
				 if(ent.getValue().def!=null&&ent.getValue().status!=null){
					 if(bool){
						 ent.getValue().transactionManager.commit(ent.getValue().status);
					 }else{
						 ent.getValue().transactionManager.rollback(ent.getValue().status);
					 }
				 }
			 }
		 }
	}
	
	/**
	 * 获取默认的数据库连接
	 * @return 可操作数据库的工具类
	 */
	public static CommonManager getConn(){
		return getConn(SystemConfig.DEFAULT_DB);
	}
	
	/**
	 * 获取其它数据连接
	 * @param key 数据源标识名，可以从数据SqlServerMap.getSqlMap()获取所有的连接标识信息
	 * @return 可操作数据库的工具类
	 */
	public static CommonManager getConn(String key){
		return getCm(key,false);
	}
	/**
	 * 可以配置事务的连接
	 * @param key 数据源连接标识名
	 * @param isTransaction 是否带有事务
	 * @return
	 */
	public static CommonManager getConn(String key,boolean isTransaction){
		return getCm(key,isTransaction);
	}
	/**
	 * 获取默认的数据源连接
	 * @param isTransaction 是否带有事务
	 * @return
	 */
	public static CommonManager getConn(boolean isTransaction){
		return getCm(SystemConfig.DEFAULT_DB,isTransaction);
	}
}
