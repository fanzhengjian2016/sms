package com.shenjun.db.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 数据库对象操作类
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class SqlServerMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Map<String,SqlServer> sqlMap= new HashMap<String, SqlServer>();
	
//	private static boolean status=true;
	
	/**
	 * get SqlServer 
	 * @param key
	 * @return
	 */
	public static SqlServer get(String key){
		return sqlMap.get(key);
	}
	
	
	/**
	 * get sqlMap
	 */
	public static Map<String,SqlServer> getSqlMap(){
		return sqlMap;
	}
	
	
//	/**
//	 * set sqlMap值
//	 * @param sqlMap
//	 */
//	public final static void setSqlMap(Map<String,SqlServer> sqlMap){
//		if(status){
//			SqlServerMap.sqlMap=sqlMap;
//			status=false;
//		}else{
//			new Exception("不能重复的赋值.");
//		}
//	}
	/**
	 * 存储数据库对象组
	 * @param keyName
	 * @param className
	 * @param description
	 * @return 成功true,失败false
	 */
	public static boolean putSqlServer(String keyName,String className,String url,String userName,String description){
//		if(sqlMap!=null&&sqlMap.get(keyName)==null){
//			SqlServer ss = new SqlServer();
//			ss.setKey_name(keyName);
//			ss.setSqlServerType(className);
//			ss.setDescription(description);
//			sqlMap.put(ss.getKey_name(), ss);
//			return true;
//		}
//		return false;
		SqlServer ss = new SqlServer();
		ss.setKey_name(keyName);
		ss.setClassName(className);
		ss.setDescription(description);
		ss.setUrl(url);
		ss.setUserName(userName);
		sqlMap.put(ss.getKey_name(), ss);
		return true;
	}

}
