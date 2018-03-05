package com.shenjun.timertask;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import com.shenjun.web.services.Service;
/**
 * 
 * @author shenjun
 * 任务调度类
 *
 */
public class JxCronTriggerFactoryBean extends CronTriggerFactoryBean{
	
	private Log log=LogFactory.getLog(JxCronTriggerFactoryBean.class);
	 
	private MethodInvokingJobDetailFactoryBean m=null;
	
	private ServicesSynchroTimerTask task = new ServicesSynchroTimerTask();
	
	private String beanName;
	
	public void setBeanName(String beanName) {
		this.beanName = beanName;
		super.setBeanName(beanName);
	}


	/**
	 * <property name="concurrent" value="false" />concurrent为true，则允许一个QuartzJob并发执行，否则就是顺序执行。
	 */
	public void afterPropertiesSet() throws ParseException {
		
		this.m = new MethodInvokingJobDetailFactoryBean();
		
		this.m.setConcurrent(false);
		
		this.m.setTargetObject(task);
		this.m.setTargetMethod("task");
		try {
			this.m.setBeanName(beanName);
			this.m.afterPropertiesSet();
		} catch (Exception e) {
			log.error("JxCronTriggerFactoryBean afterPropertiesSet error:"+e.getMessage(),e.getCause());
		} 
		this.setJobDetail(this.m.getObject());
		
		super.afterPropertiesSet();
	}


	public void setServices(Service[] service) {
		task.setService(service);
	}
	
	public void setService(Service service) {
		task.setService(new Service[]{service});
	}
}
