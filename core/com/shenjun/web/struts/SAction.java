package com.shenjun.web.struts;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.shenjun.annotation.ReturnType;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.exception.RequestMethodErrorException;
import com.shenjun.io.data.JasperResultBean;
import com.shenjun.io.data.SXmlBean;
import com.shenjun.util.ClassUtil;
import com.shenjun.web.thread.Lc;
/**
 * 
 * @author shenjun
 * 2013年5月4日0:52:10 使用的最新方法，其它的已过时。
 *
 */
@ReturnType(ReturnEnum.AJAX_TYPE)
public class SAction extends DispatchActionSuper{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String InputStreamName="InputStreamName";
	
	public InputStream getInputStream(){
		return Lc.getContext(InputStreamName);
	}
	
	public void setInputStream(InputStream inputStream){ 
		Lc.setContext(InputStreamName, inputStream);
	}
	
	@SuppressWarnings("rawtypes")
	private boolean invalid(Class cls){
		List<Class> itfs = ClassUtil.findAllInterfaces(cls, 5);
		for(Class icls : itfs){
			if ("com.shenjun.io.data.SXmlBean".equals(icls.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * xml bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws RequestMethodErrorException
	 */
	@SuppressWarnings("rawtypes")
	protected Object sXmlInvoke() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, RequestMethodErrorException{
		Method method=getExecuteMethod();
		Class[] clss= method.getParameterTypes();
		if(clss!=null&&clss.length==1&&this.invalid(clss[0])){
			Class<?> cls=clss[0];SXmlBean sxb=null;
			try {
				sxb=(SXmlBean)cls.newInstance();
				sxb.bytesReceive();
			} catch (InstantiationException e) {
				log.error("构造CLS错误:"+e.getMessage(),e.getCause());
			}
			
			method.invoke(this, sxb);
			return sxb;
			
		}else{
			return super.invoke();
		}
	}
	
	/**
	 * exec jasper
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	protected Object sJasperInvoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method method=getExecuteMethod();
		
		JasperResultBean bean = new JasperResultBean();
		method.invoke(this, bean);

		return bean;
	}
	
	
	@Override
	protected Object invoke() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, RequestMethodErrorException{
		ReturnEnum reType=this.getRetrunAnnotation(this.getExecuteMethod().getAnnotations());
		Object reObj=null;
		ReturnEnum reCtype=this.getRetrunAnnotation(this.getClass().getAnnotations());
		if(reType!=null){
        	this.returnEnum=reType;
        }else if(reCtype!=null){
        	this.returnEnum=reCtype;
        }
		switch (this.returnEnum) {
		case XML_TYPE:
			reObj=this.sXmlInvoke();
			break;
		case JASPER_S:
			reObj=this.sJasperInvoke();
			break;
		default:
			reObj=super.invoke();
			break;
		}
		
		return reObj;
	}
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
}
