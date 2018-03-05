package com.shenjun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 读取配置文件的类
 * @author: 沈军
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class ReadProperties {
	
	private static Map<String,Properties> propertiesMap=new HashMap<String,Properties>();
	
	private static Logger log = Logger.getLogger(ReadProperties.class.getName());
	
	/**
	 * 载入模板文件
	 * @param fileName
	 * @param isload 是否重新载入
	 * @return
	 */
	private static Properties getPropertieObj(String fileName,boolean isload){
		try{
		if(propertiesMap.get(fileName)==null||isload){
			
			Properties p = new Properties();
			p.load(new FileInputStream(getPropertieFile(fileName)));
			propertiesMap.put(fileName, p);
		}
		}catch(Exception e){
			log.error("设置配置文件载入出错（ReadProperties）："+e.getMessage(),e.getCause());
		}
		return propertiesMap.get(fileName);
	}
	
	/**
	 * 获取配置文件
	 * @param fileName 文件名在项目目录的【resources/config】或者【resources】不包含扩展名
	 * @return
	 */
	public static File getPropertieFile(String fileName){
		File f = new File(WebUtil.getClassesRoot()+"config/"+fileName + ".properties");
		if(!f.isFile()){
			f = new File(WebUtil.getClassesRoot()+fileName + ".properties");
		}
		return f;
	}
	
	/**
	 * 获取对应文件的所有的配置信息
	 * @param fileName  文件名在项目目录的【resources/config】或者【resources】不包含扩展名
	 * @return
	 */
	public static Map<String,String> getProperties(String fileName){
		Properties p=getPropertieObj(fileName,false);
		Map<String,String> map = new HashMap<String, String>();
		for(Map.Entry<Object, Object> entry: p.entrySet()){
			map.put(entry.getKey()+"", entry.getValue()+"");
		}
		return map;
	}
	
	/**
	 * 修改配置文件的Key或者Value
	 * @param fileName 配置文件名 文件名在项目目录的【resources/config】或者【resources】不包含扩展名
	 * @param key Key的值
	 * @param value 
	 * @return
	 */
	public static boolean setPropertie(String fileName,String key,String value){
		try {
			Properties p=getPropertieObj(fileName,false);
			p.put(key, value);
			FileOutputStream fos=new FileOutputStream(getPropertieFile(fileName));
			p.store(fos,null);
			fos.flush();
			fos.close();
			getPropertieObj(fileName,true);
		} catch (Exception e) {
			log.error("设置配置文件"+e.getMessage(),e.getCause());
			return false;
		}
		return true;
	}
	
	/**
	 * 获取对应文件名的Key值
	 * @param fileName 文件名在项目目录的【resources/config】或者【resources】不包含扩展名
	 * @param key
	 * @return
	 */
	public static String getProperty(String fileName,String key){
		  try{
			  return getPropertieObj(fileName,false).getProperty(key)+"";
		  }catch(Exception e){
			  log.error("读取配置"+fileName+"文件,key["+key+"]出错:"+e.getMessage(),e.getCause());
			  return "";
		  }
	}
	
	/**
	 * 获取config/system_config.properties对应的Key
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		return getProperty("system_config",key);
	}
}
