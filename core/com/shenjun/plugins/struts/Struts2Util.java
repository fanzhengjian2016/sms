package com.shenjun.plugins.struts;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.shenjun.db.type.SqlValue;
import com.shenjun.util.RegexUtil;
import com.shenjun.util.SqlUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.web.listens.ContentListen;
import com.shenjun.web.thread.LocalContent;

/**
 * LC代表本线程操作类集合，可以从这一个对象得到参数、数据库连接、Sftp连接等大量信息
 * @author: 沈军
 * @category 常用类 重点类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class Struts2Util extends LocalContent{
	
	private static Log logger=LogFactory.getLog(Struts2Util.class);
	
	/**
	 * 根据正则表达式查找request 中Parameter对象数组
	 * @param regex
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> getMatchParameter(String regex){
		Map<String,Object> map =ActionContext.getContext().getParameters();
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		for(Map.Entry<String,Object> iterator: map.entrySet()){
			if(RegexUtil.isFindStr(regex, iterator.getKey())){
				resMap.put(iterator.getKey(), iterator.getValue());
			}
		}
		return resMap;
	}
	/**
	 * 获取请求中所有参数
	 * @return
	 */
	public static Map<String,Object> getAllParametes(){
		return ActionContext.getContext().getParameters();
	}
	
	/**
	 * 获取request getParameter对象(已过时，请使用get方法)
	 * @param map
	 * @param name
	 * @return String
	 */
	@Deprecated
	private static String getParameter(String name){
		if(ActionContext.getContext()==null){
			return null;
		}
		
		Object obj = ActionContext.getContext().getParameters().get(name);
		if(obj!=null){
			String[] objs = (String[])obj;
			return objs[0]==null?null:objs[0]+"";
		}else{
			return null;
		}
	}
	
	/**
	 * 获取request getParameter对象
	 * @param name
	 * @return String
	 */
	public static String get(String name){
		return getParameter(name);
	}
	
	/**
	 * 获取request getParameter对象
	 * @param name
	 * @param default
	 * @return String
	 */
	public static String get(String name,String def){
		if(StringUtil.isNB(getParameter(name))){
			return getParameter(name);
		}else{
			return def;
		}
	}
	
	/**
	 * 获取正确编码的中文，用于get请求时中文乱码的的解决
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getEncodeParam(String name) throws UnsupportedEncodingException{
		String str=get(name);
		return new String(str.getBytes(StringUtil.getEncoding(str)),"UTF-8");
	}
	
	
	/**
	 * 将参数转成指定类型(已过时，请使用get方法)
	 * @param <T>
	 * @param name
	 * @param sqlType
	 * @return
	 */
	@Deprecated
	private static SqlValue getParameter(String name,Integer sqlType){
		return SqlUtil.Csv(Struts2Util.getParameter(name.trim()), sqlType).getValue();
	}
	
	/**
	 * 将参数转成指定类型
	 * @param <T>
	 * @param name
	 * @param sqlType
	 * @return sqlvalue.getValue()
	 */
	public static <T> T get(String name,Integer sqlType){
		return getParameter(name,sqlType).getValue();
	}
	
	/**
	 * 将参数转成SqlValue类型
	 * @param <T>
	 * @param name
	 * @param sqlType
	 * @return SqlValue
	 */
	public static SqlValue getSvp(String name,Integer sqlType){
		return SqlUtil.Csv(Struts2Util.getParameter(name.trim()), sqlType);
	}

	/**
	 * 设置request getParameter对象
	 * @param map
	 * @param name
	 * @return String
	 */
	public static void set(String key,String value){
		ActionContext.getContext().getParameters().put(key, new String[]{value});
	}
	
	/**
	 * 设置request getParameter对象
	 * @param params
	 */
	public static void set(Map<String,Object> map){
		if(map==null){
			return;
		}
		for(Map.Entry<String,Object> entry : map.entrySet()){
			if(entry.getValue() instanceof Object[]){
				setParameters(entry.getKey(),(String[])entry.getValue());
			}else{
				set(entry.getKey(),entry.getValue()+"");
			}
		}
	}
	
	/**
	 * 设置Context的Map中的对象
	 * @param name
	 * @param Object
	 * @return String
	 */
	public static void setContext(String key,Object value){
		ActionContext.getContext().put(key, value);
	}
	/**
	 * 获取Context的Map中的对象
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getContext(String key){
		return (T)ActionContext.getContext().get(key);
	}
	/**
	 * 获取request getParameters 数组 对象
	 * @param map
	 * @param name
	 * @return String
	 */
	public static String[] getParameters(String name){
		Object obj = ActionContext.getContext().getParameters().get(name);
		if(obj!=null){
			String[] objs = (String[])obj;
			if(objs.length==1){
				if(!StringUtil.isNB(objs[0])){
					return null;
				}
			}
			
			return objs;
		}else{
			return null;
		}
	}
	/**
	 * 设置request getParameters 数组 对象
	 * @param name
	 * @param params
	 */
	public static void setParameters(String name,String[] params){
		ActionContext.getContext().getParameters().put(name, params);
	}
	
	/**
	 * 存入值入Request中
	 * @param name
	 * @param value
	 * 请使用set方法
	 */
	@Deprecated
	public static void setRequestContent(String name,Object value){
		ActionContext.getContext().put(name, value);
		ActionContext.getContext().getParameters().put(name, new String[]{value==null?null:value+""});
	}
	
	/**
	 * 存入值入Session中
	 * @param name
	 * @param value
	 */
	public static void setSessionContent(String name,Object value){
		ActionContext.getContext().getSession().put(name, value);
	}
	
	/**
	 * 获取Session中的值
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static <T> T getSessionContent(String name){
		return (T)(ActionContext.getContext().getSession().get(name));
	}
	
	/**
	 * 获取Session中的值
	 * @param name
	 */
	public static <T> T getSession(String name){
		return getSessionContent(name);
	}
	
	/**
	 * 获取系统变量
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSysContent(String name){
		return (T)(ContentListen.getServletContext().getAttribute(name));
	}
	
	/**
	 * 获取Session中的值
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionValue(HttpSession session,String name){
		try {
			return (T)(session.getAttribute(name));
		} catch (Exception e) {
			logger.info("message:getSessionValue method return value is null,not find you are value");
			return null;
		}
		
	}
	
	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public static HttpServletRequest getServletRequest(){
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获取HttpServletResponse对象
	 * @return
	 */
	public static HttpServletResponse getServletResponse(){
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 传递参数
	 *@return string
	 */
	@SuppressWarnings("unchecked")
	public static String getReuqestParmsUrl(String url){
		Map<String, String[]> paramMap= getServletRequest().getParameterMap();
		StringBuilder builder=new StringBuilder();
		for(Map.Entry<String, String[]> entry:paramMap.entrySet()){
			builder.append("&"+entry.getKey()+"=");
			if(entry.getValue() instanceof String[]){
				String [] values=entry.getValue();
				for (int i = 0,len=values.length; i < len; i++) {
					String val = values[i];
					if((len-1)==i){
						builder.append(val);
					}else{
						builder.append(values[i]+",");
					}
				}
			}else{
				builder.append(entry.getValue());
			}
		}
		if(url.lastIndexOf("?")>-1){
			return url+builder.toString();
		}
		return url+"?time=0"+builder.toString();
	}
	
	/**
	 * 将字符串编译成ISO8859-1类型的字符串
	 * @param code
	 * @return
	 */
	public static String URLEncode(String code){
		try {
			return URLEncoder.encode(code, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("cast url exception"+e,e.getCause());
		}
		return null;
	}
	
	/**
	 * 获取客户端IP地址
	 * @return
	 */
	public static String getRequestAddr(){
		String localAddr=Struts2Util.getServletRequest().getRemoteAddr();
		if("0.0.0.0".equals(localAddr)){
			localAddr="127.0.0.1";
		}
		if("0:0:0:0:0:0:0:1".equals(localAddr))
			localAddr="127.0.0.1";
		return localAddr;
	}
	/**
	 * 将字符串转成UTF-8编码的字符串
	 * @param code
	 * @return
	 */
	public static String URLDecode(String code){
		try {
			return URLDecoder.decode(code, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("cast url exception"+e,e.getCause());
		}
		return null;
	}
	
}
