package com.shenjun.db.conn;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.db.type.SqlServerMap;
import com.shenjun.plugins.spring.JxSpringAutoLoadXml;
import com.shenjun.security.Encrypt;
import com.shenjun.system.SystemConfig;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.StringUtil;

public class JdbcConfigLoads {
	private static Log log = LogFactory.getLog(JdbcConfigLoads.class);
	
	private static final String encrypKey="hzscgdkj";
	
	/**
	 * 载入数据库程序Bean
	 * @param dbName
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @param description
	 * @return
	 */
	public static String makeDbXml(String dbName,String driver,String url,String username,String password,String description){
		StringBuilder sb = new StringBuilder();
		sb.append("<bean id=\""+dbName+"\" parent=\""+SystemConfig.DefaultConnType+"\">");
		sb.append("	<property name=\"driverClass\">");
		sb.append("		<value><![CDATA["+driver+"]]></value>");
		sb.append("	</property>");
		sb.append("	<property name=\"jdbcUrl\">");
		sb.append("		<value><![CDATA["+url+"]]></value>");
		sb.append("	</property>");
		sb.append("	<property name=\"user\">");
		sb.append("		<value><![CDATA["+username+"]]></value>");
		sb.append("	</property>");
		sb.append("	<property name=\"password\">");
		sb.append("		<value><![CDATA["+password+"]]></value>");
		sb.append("	</property>");
		sb.append("	<property name=\"description\">");
		sb.append("		<value><![CDATA["+description+"]]></value>");
		sb.append("	</property>");
		sb.append("</bean>");
		
		return sb.toString();
	}
	
	/**
	 * 重载配置文件连接
	 *  mssql.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
		mssql.url=jdbc:sqlserver://0.0.0.0:1433;databaseName=dbname
		mssql.username=sa
		mssql.password=password
		mssql.description=none
	 * @return
	 */
	public static boolean reloadCfgConn(){
		Map<String, String> map = ReadProperties.getProperties("jdbc");
		
		for(Map.Entry<String, String> entry:map.entrySet()){
			String dbSign=(entry.getKey()+"").substring(0, (entry.getKey()+"").indexOf("."));
			if(SqlServerMap.get(dbSign)==null){
				String driverClassName=map.get(dbSign+".driverClassName");
				String url=map.get(dbSign+".url");
				String username=map.get(dbSign+".username");
				String password=map.get(dbSign+".password");
				String description=map.get(dbSign+".description");
				String encryp=map.get(dbSign+".encryp");
				
				if(StringUtil.isNB(encryp,password)){
					if("AES".equals(encryp)){
						password=new String(Encrypt.decrypt(password.getBytes(), encrypKey.getBytes(), Encrypt.KEYGEN_AES, 128));
					}
				}
				
				if(StringUtil.isNotNull(driverClassName,url,username,password,description)){
					
					if(SqlServerMap.putSqlServer(dbSign, driverClassName,url,username, description)){
						log.debug("CFG正在载入数据库连接：dbName["+dbSign+"],url["+url+"]");
						JxSpringAutoLoadXml.loadXmlContent(
								makeDbXml(dbSign, driverClassName, url, username, password, description)
								);
					}
				}
			}
		}
		return true;
	}
}
