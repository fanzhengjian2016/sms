package com.shenjun.plugins.spring;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;

/**
 * 动态构建XMLBean
 * @author shenjun
 * 版权个人所有
 */
public class JxSpringAutoLoadXml{
	
	private static Log log = LogFactory.getLog(JxSpringAutoLoadXml.class);
	
	private static XmlBeanDefinitionReader beanDefinitionReader;
	/**
	 * 动态载入
	 * <bean id="compileXml" class="com.commons.compile.CompileXml"></bean>
	 * @param xmlBean
	 * @return
	 */
	public static int loadXmlContent(String xmlBean){
		ApplicationContext applicationContext = SpringUtil.getContent();
		beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)
				applicationContext.getAutowireCapableBeanFactory());
		
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(applicationContext));
        
        log.debug("开始载入Spring XML："+JxSpringAutoLoadXml.convertSpringXml(xmlBean));
        
        ByteArrayResource r=null;
		try {
			r = new ByteArrayResource(JxSpringAutoLoadXml.convertSpringXml(xmlBean).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("JxSpringAutoLoadXml.loadXmlContent UnsupportedEncodingException: "+e.getMessage(), e.getCause());
		}
        beanDefinitionReader.loadBeanDefinitions(r);
		return 0;
	}
	/**
	 *
	 * @param xml
	 * @return
	 */
	private static String convertSpringXml(String xml){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\" ");
//		sb.append("xmlns:context=\"http://www.springframework.org/schema/context\"");
//		sb.append("xmlns:mvc=\"http://www.springframework.org/schema/mvc\"");
//		sb.append("xmlns:p=\"http://www.springframework.org/schema/p\"");
		sb.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
//		sb.append("xmlns:tx=\"http://www.springframework.org/schema/tx\"");
		sb.append("xsi:schemaLocation=\"");
		sb.append("http://www.springframework.org/schema/beans ");
		sb.append("http://www.springframework.org/schema/beans/spring-beans-3.0.xsd ");
//		sb.append("    http://www.springframework.org/schema/context");
//		sb.append("    http://www.springframework.org/schema/context/spring-context-3.0.xsd");
//		sb.append("    http://www.springframework.org/schema/mvc");
//		sb.append("    http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd");
//		sb.append("    http://www.springframework.org/schema/tx");
//		sb.append("    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd");
		sb.append("\" >");
		sb.append(xml);
		sb.append("</beans>");
		return sb.toString();
	}
	
	/**
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:context="http://www.springframework.org/schema/context"
xmlns:mvc="http://www.springframework.org/schema/mvc"
xmlns:p="http://www.springframework.org/schema/p"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:tx="http://www.springframework.org/schema/tx"
xsi:schemaLocation="
    http://www.springframework.org/schema/beans     
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-3.0.xsd
    http://www.springframework.org/schema/mvc
    http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
    ">
</beans>
	 */
}
