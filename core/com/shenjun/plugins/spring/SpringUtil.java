package com.shenjun.plugins.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shenjun.web.listens.ContentListen;

/**
 * @author: 沈军
 * @category Spring的操作类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class SpringUtil {
	
	private static  Log log = LogFactory.getLog(SpringUtil.class);
	
	/**
	 * 获取Spring容器
	 * @return
	 */
	public static WebApplicationContext getContent(){
		return WebApplicationContextUtils.getWebApplicationContext(ContentListen.getServletContext());
	}
	
	/**
	 * 获得一个实体的Bean的对象(未过时。慎重使用类)
	 * @param beanName
	 * @return
	 */
	@Deprecated
	public static Object getBean(String beanName){
		return WebApplicationContextUtils.getWebApplicationContext(ContentListen.getServletContext()).getBean(beanName);
	}
	
	/**
	 * 获得一个实体的Bean的对象(未过时。慎重使用类)
	 * @param beanName
	 * @param cls
	 * @return
	 */
	@Deprecated
	public static <T> T getBean(String beanName,Class<T> cls){
		return (T)WebApplicationContextUtils.getWebApplicationContext(ContentListen.getServletContext()).getBean(beanName,cls);
	}
	
	/**
	 * 通过Class获取Bean，如果Bean不存在，这一个类会负责自动帮您加载，建议使用。
	 * @param cls
	 * @return
	 */
	public static <T> T  getBean(Class<T> cls){
		T bean=null;
		String name=cls.getName().toLowerCase().replace(".", "_");
		try{
			bean = SpringUtil.getBean(name, cls);
		}catch(Exception e){
			log.debug("SpringUtil getBean not extis :"+cls.getName());
		}
		
		if(bean==null){
			JxSpringAutoLoadXml.loadXmlContent("<bean id=\""+name+"\" class=\""+cls.getName()+"\"></bean>");
			bean = SpringUtil.getBean(name, cls);
		}
		return bean;
	}
}
