package com.shenjun.web.struts;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shenjun.annotation.FilterAnnotation;
import com.shenjun.annotation.ReturnType;
import com.shenjun.annotation.imp.Filter;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.enums.SearchEnum;
import com.shenjun.exception.RequestMethodErrorException;
import com.shenjun.io.data.JasperResultBean;
import com.shenjun.io.data.SXmlBean;
import com.shenjun.manager.CommonManager;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.ClassUtil;
import com.shenjun.util.RegexUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.web.dispatcher.Struts2DispatcherLoad;
import com.shenjun.web.thread.LocalContent;

/**
 * @author: 沈军
 * @category 定义一个可以通过传递method进行不同方法的访问的类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */

public class DispatchActionSuper extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	public  Log log = LogFactory.getLog(DispatchActionSuper.class);
	
	protected Class<? extends DispatchActionSuper> clazz = this.getClass();
	
	protected ReturnEnum returnEnum = ReturnEnum.JSP_CODE_NAME_TYPE;
	
	public final static String ServletDispatcherAtt ="ServletDispatcherAtt.url";
	
	@SuppressWarnings("unchecked")
	public <T> T get(Object obj){
		try {
			return (T)(obj);
		} catch (Exception e) {
			log.error(e+" case object error!!!",e.getCause());
			return null;
		}
		
	}
	
	/**
	 * object cast class
	 * @param <T>
	 * @param t
	 * @return
	 */
	public<T> T load(Class<T> cls){
		try {
			T obj =cls.newInstance();
			
			Field[] fields=cls.getDeclaredFields();
			
			for(Field field:fields){
				String v=Struts2Util.get(field.getName().toLowerCase());
				if(StringUtil.isNB(v)){
					ClassUtil.getMethod("set"+field.getName(), cls,SearchEnum.NOT_DIS_UPPER_LOWER)
							.invoke(obj, ClassUtil.caseValue(v, field.getType()));
				}
			}
			return obj;
		} catch (Exception e) {
			log.error(e+"castexception.",e.getCause());
		}
		return null;
	}
	
//	/**
//	 * 对返回类型进行验证
//	 */
//	protected ReturnEnum validateMethodType(){
//		try {
//			Annotation[] annotation = this.getExecuteMethod().getAnnotations(); 
//	        for(Annotation   a : annotation){
//	        	if(a instanceof ReturnType )
//		        	return ((ReturnType)a).value();
//	        } 
//		} catch (Exception e) {
//			log.error("返回Method类型验证异常"+e,e.getCause());
//		}
//		return null;
//	}
	
	/**
	 * 对返回类型进行验证
	 */
	protected ReturnEnum getRetrunAnnotation(Annotation[] annotation){
		try {
	        for(Annotation   a : annotation){
	        	if(a instanceof ReturnType )
		        	return ((ReturnType)a).value();
	        } 
		} catch (Exception e) {
			log.error("返回Class类型验证异常"+e,e.getCause());
		}
		return null;
	}
	
	
	
	/**
	 * 
	 *
	 */
	public CommonManager getCommonManager(){
		
		return null;
	}
	
	protected Method getExecuteMethod(){
		String methodStr = Struts2Util.get("method");
		if(methodStr==null){
			log.error("method is null");
		}
		Method method = ClassUtil.getMethod(methodStr, clazz, SearchEnum.NOT_DIS_UPPER_LOWER);
		return method;
	}
	
	protected Object invoke() throws IllegalArgumentException, IllegalAccessException,RequestMethodErrorException,InvocationTargetException{
		return getExecuteMethod().invoke(this);
	}
	
	
	/**
	 * 过滤器验证
	 */
	protected Filter getFilterImp(){
		try {
			Annotation[] ann=(Annotation[])ArrayUtils.addAll(this.getClass().getAnnotations(), this.getExecuteMethod().getAnnotations());
			
	        for(Annotation   a : ann){
	        	if(a instanceof FilterAnnotation ){
	        		String filterName=((FilterAnnotation) a).value();
	        		Filter f = SpringUtil.getBean(filterName, Filter.class);
	        		
	        		if(f==null){
	        			log.error("method getFilterImp is null");
	        			continue;
	        		}
	        		return f;
	        	}
	        } 
		} catch (Exception e) {
			log.error("返回Class类型验证异常"+e,e.getCause());
		}
		return null;
	} 
	
	@Override
	public String execute() throws Exception {
		String returnStr=null;
		try {
			
			Filter filter = this.getFilterImp();
			boolean is_pass = true;
			
			Object returnValue=null;
	        //log.debug("线程ID("+Thread.currentThread().getName()+"),execute返回的字符串为:"+returnValue);
	        ReturnEnum reType=null;
			
			if(filter!=null){
				if(filter.before()!=0){
					returnValue=filter.redirect();
					is_pass=false;
				}
			}
			
	        if(is_pass){
	        	returnValue=invoke();
	        	reType=this.getRetrunAnnotation(this.getExecuteMethod().getAnnotations());
	        }else{
	        	reType=this.getRetrunAnnotation(
	        			ClassUtil.getMethod("redirect", filter.getClass(), SearchEnum.NOT_DIS_UPPER_LOWER)
	        			.getAnnotations());
	        }
	        
	        if(filter!=null){
	        	filter.after();
	        }
	        
	        
	        
	        HttpServletResponse response = ServletActionContext.getResponse();
	        
	        if(reType!=null){
	        	this.returnEnum=reType;
	        }
	        
	        switch (this.returnEnum) {
			case AJAX_TYPE://普通的Ajax返回
				PrintWriter out= response.getWriter();
				//response.setCharacterEncoding("utf-8");
				response.setContentType("application/x-json");
				out.print(returnValue);
				out.flush();
				break;
			case FILE_TYPE:
				
				break;
			case XML_TYPE://返回纯XML内容
				ServletOutputStream sout =response.getOutputStream();
				//response.setCharacterEncoding("utf-8");
				response.setContentType("application/xml");
				
				if(returnValue instanceof SXmlBean){
					sout.write(((SXmlBean)returnValue).bytesSend());
				}else if(returnValue instanceof String){
					sout.write(((String) returnValue).getBytes("UTF-8"));
				}else if(returnValue instanceof byte[]){
					sout.write((byte[])returnValue);
				}
				sout.flush();
				
				break;
			case JSP_FORWARD_TYPE:
				ActionContext.getContext().put(ServletDispatcherAtt, returnValue);
				returnStr = Struts2DispatcherLoad.FORWORDNAME;
				
				break;
			case JASPER_S:
				ActionContext.getContext().put(ServletDispatcherAtt, returnValue);
				returnStr = Struts2DispatcherLoad.SJASPER;
				
				if(returnValue instanceof JasperResultBean){
					ActionContext.getContext().put(JasperResultBean.JASPER_RESULT_BEAN, returnValue);
				}else{
					log.error("return value is error,not JasperResultBean..................");
				}
				
				break;
			case JSP_REDIRECT_TYPE:
				ActionContext.getContext().put(ServletDispatcherAtt, returnValue);
				returnStr = Struts2DispatcherLoad.REDIRECT;
				break;
			case JSP_STREAM_TYPE:
				ActionContext.getContext().put(ServletDispatcherAtt, returnValue);
				returnStr = Struts2DispatcherLoad.STREAM;
				break;
			case JSP_CODE_NAME_TYPE:
				if((returnValue+"").endsWith(".jsp")){
					ActionContext.getContext().put(ServletDispatcherAtt, returnValue);
					returnStr = Struts2DispatcherLoad.AUTOSTINGVALUE;
				}else if(RegexUtil.isFindStr("^[a-zA-Z]+$", (returnValue+""))){
					returnStr = (returnValue+"");
				}else{
					ActionContext.getContext().put(ServletDispatcherAtt, returnValue);
					returnStr = Struts2DispatcherLoad.REDIRECT;
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error("("+Thread.currentThread().getName()+")execute error."+e.getMessage(), e.getCause());
			LocalContent.setError(e.getMessage());
			throw e;
		}
		return returnStr;
	}
}
