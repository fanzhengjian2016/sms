package com.shenjun.web.struts.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.shenjun.annotation.ValidateType;
import com.shenjun.enums.SearchEnum;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.ClassUtil;
import com.shenjun.web.struts.DispatchActionSuper;
import com.shenjun.web.thread.LocalContent;

/**
 * @author: 沈军
 * @category 事物进行相关的拦截
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class TransactionInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(TransactionInterceptor.class);
	
	
	public void destroy() {
		
		log.debug("TransactionInterceptor destroy");
	}

	public void init() {
		log.debug("TransactionInterceptor init");
		
	}
	
	/**
	 * 对用户的权限进行相应的验证
	 */
	public void validateRole(ActionInvocation actionInvocation){
		String method = null;
		try {
			if((method=Struts2Util.get("method"))!=null){
				Method m=ClassUtil.getMethod(method, actionInvocation.getAction().getClass(), SearchEnum.NOT_DIS_UPPER_LOWER);
				if(m!=null){
					Annotation[] annotation = m.getAnnotations(); 
			        for(Annotation   a : annotation){
			        	if(a instanceof ValidateType)
			        		System.out.printf("验证代号:%s%n", ((ValidateType)a).ValidateChar()); 
			        }
				}
			}
		}catch (Exception e) {
			log.error("框架权限验证异常"+e,e.getCause());
		}
		
	}
	

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		log.debug("starting go to intercept.....thread ID("+Thread.currentThread().getName()+")");
		
		/**
		 * ************权限验证拦截体**************************************************
		 */
		validateRole(actionInvocation); //认证体系<元数据验证>
		/***************************************************************************/
		
		
		
		/**
		 * **************进入数据库连接,事物代码体**************************************************************
		 */
		String result = null;
		
		try {
			LocalContent.setError(null);
				
			result = actionInvocation.invoke();
			
			//log.debug("actionInvocation.invoke()->"+result);
			//System.out.println(ErrorLocal.getSp()+"-----------------------"+Thread.currentThread().getName());
			if(("error".equals(result)||LocalContent.getError()!=null)
					&&ActionContext.getContext()
					.get(DispatchActionSuper.ServletDispatcherAtt)==null){
				throw new Exception("program inner error for action");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("intercept rollback:"+e,e.getCause());
			LocalContent.setError(e.getMessage());
			return "error";
		}
		/******************************************************************************************/
		
		log.debug("end intercepted thread ID("+Thread.currentThread().getName()+")");
		
		return result;
	}

}
