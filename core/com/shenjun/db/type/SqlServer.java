package com.shenjun.db.type;

import java.io.Serializable;

import com.shenjun.enums.SqlServerType;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class SqlServer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SqlServerType sqlServerType;
	
	private String description;
	
	private String key_name;
	
	private String className;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		this.setSqlServerType(className);
	}

	private String url;
	
	private String userName;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey_name() {
		return key_name;
	}

	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

	public SqlServerType getSqlServerType() {
		return sqlServerType;
	}

	public void setSqlServerType(String className) {
		if(className.indexOf("OracleDriver")>-1){
			this.sqlServerType=SqlServerType.ORACLE;
		}else if(className.indexOf("SQLServerDriver")>-1){
			this.sqlServerType=SqlServerType.MSSQL ;
		}else if(className.indexOf("mysql")>-1){
			this.sqlServerType=SqlServerType.MYSQL ;
		}
	}
	
	
}
