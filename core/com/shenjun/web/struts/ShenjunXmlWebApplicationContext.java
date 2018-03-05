package com.shenjun.web.struts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * @author: 沈军
 * @category 加入Spring的BeanDefinition读取功能，方便WEB开发时随时查看配置信息
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class ShenjunXmlWebApplicationContext extends XmlWebApplicationContext {

	//private static Logger log = Logger.getLogger(ShenjunXmlWebApplicationContext.class.getName());
	
	
	public BeanDefinition getBeanDefinition(String beanName) {
		return getBeanFactory().getBeanDefinition(beanName);
	}
	protected List<BeanDefinition> getBeanDefinitions(){
		String[] beanNames = this.getBeanDefinitionNames();
		List<BeanDefinition> beanDefinitions=new ArrayList<BeanDefinition>();
		for (String beanName : beanNames) {
			beanDefinitions.add(this.getBeanDefinition(beanName));
		}
		return beanDefinitions;
	}
}	

