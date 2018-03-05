package com.shenjun.system;

import com.shenjun.db.type.DBConnType;


/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class SystemConfig {
	/**
	 * 系统名称
	 */
	public static final String SYSTEM_NAME=com.shenjun.util.ReadProperties.getProperty("SystemName");
	/**
	 * 本地文件首目录名
	 */
	public static final String FILE_ROOT=com.shenjun.util.ReadProperties.getProperty("FileRoot");
	/**
	 * 报表文件目录名
	 */
	public static final String REPROT_FILE_ROOT=com.shenjun.util.ReadProperties.getProperty("ReportFileRoot");
	/**
	 * 本项目的WebRoot的访问名eg:http://127.0.0.1/mjbox
	 */
	public static final String WEB_ROOT_URL=com.shenjun.util.ReadProperties.getProperty("WebRootUrl");
	
	/**
	 * 数据库连接类型
	 */
	public static final String DefaultConnType=com.shenjun.util.ReadProperties.getProperty("DefaultConnType");
	
	/**
	 * 数据库连接方式
	 * enum DBConnType{c3p0,dbcp}
	 */
	public static final DBConnType DB_CONN_TYPE=(
			"c3p0".equals(DefaultConnType)?DBConnType.C3P0:DBConnType.DBCP
					);
	/**
	 * 默认的数据库连接
	 */
	public static final String DEFAULT_DB=com.shenjun.util.ReadProperties.getProperty("DefaultSqlServerKey");
	
	
	
}
