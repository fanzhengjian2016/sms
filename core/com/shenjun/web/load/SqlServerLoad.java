package com.shenjun.web.load;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.shenjun.db.type.SqlServer;
import com.shenjun.db.type.SqlServerMap;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;
import com.shenjun.util.XMLUtil;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class SqlServerLoad{
	protected SqlServerLoad(){
	}
	
	private static Logger log = Logger.getLogger(SqlServerLoad.class.getName());
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void load(){
		
		Map<String,SqlServer> sqlMap = SqlServerMap.getSqlMap();
		File file = new File(WebUtil.getClassesRoot()+"spring/application-context-connection.xml");
		String name="";
		if(file.exists()){
			Document doc= XMLUtil.read(file);
			List<Element> ls=doc.selectNodes("//*[name()='bean'][@parent='abstractDataSource']");
			if(ls==null||ls.size()==0){
				ls=doc.selectNodes("//*[name()='bean']");
			}
			
			for(Element el : ls){
				
				SqlServer ss = new SqlServer();
				ss.setKey_name((el.attributeValue("id")+"").trim());
				
				String className=(el.selectSingleNode("*[name()='property'][@name='driverClass']")
						.getStringValue()+"").trim();
				ss.setClassName(ReadProperties.getProperty("jdbc",
						className.substring(2,className.length()-1)
				));
				
				String jdbcUrl=(el.selectSingleNode("*[name()='property'][@name='jdbcUrl']")
						.getStringValue()+"").trim();
				ss.setUrl(ReadProperties.getProperty("jdbc",
						jdbcUrl.substring(2,jdbcUrl.length()-1)
				));
				
				String user=(el.selectSingleNode("*[name()='property'][@name='user']")
						.getStringValue()+"").trim();
				ss.setUserName(ReadProperties.getProperty("jdbc",
						user.substring(2,user.length()-1)
				));
				
				//System.out.println(el.selectNodes("*[name()='property'][@name='description']").size());
				
				String ds=(el.selectSingleNode("*[name()='property'][@name='description']")
								.getStringValue()+"").trim();
				
				String description=ReadProperties.getProperty(
						"jdbc",ds.substring(2,ds.length()-1)
						);
				
				//ss.setName(description);
				ss.setDescription(description);
				name+=("["+ss.getKey_name()+":"+description+"]");
				sqlMap.put(ss.getKey_name(), ss);
			}
			log.debug("loading in SqlServer,success in "+ls.size()+" set "+name);
		}else{
			log.debug("loading in SqlServer,success in "+0+" set "+name);
		}
		
	}
	
	public static class builder{
		public SqlServerLoad build(){
			return new SqlServerLoad();
		}
	}
	
	public static void main(String[] args) {
		SqlServerLoad.builder sl=new SqlServerLoad.builder();
		sl.build().load();
	}
}