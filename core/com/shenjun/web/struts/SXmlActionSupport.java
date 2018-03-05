package com.shenjun.web.struts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.shenjun.enums.ReturnEnum;
import com.shenjun.exception.RequestMethodErrorException;
import com.shenjun.io.data.SXmlBean;

@Deprecated
public class SXmlActionSupport extends DispatchActionSuper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean invalid(Class<?> cls) throws RequestMethodErrorException{
		Class<?>[] itfs=cls.getInterfaces();
		boolean res=false;
		if(itfs!=null){
			for(Class<?> icls : itfs){
				if ("com.shenjun.io.data.SXmlBean".equals(icls.getName())) {
					return true;
				}
			}
		}
		if(cls.getSuperclass()!=null){
			return this.invalid(cls.getSuperclass());
		}else{
			return res;
		}
	}
	
	protected Object sXmlInvoke() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, RequestMethodErrorException{
		Method method=getExecuteMethod();
		Class<?>[] clss= method.getParameterTypes();
		if(!this.invalid(clss[0])){
			throw new RequestMethodErrorException("invoke not found com.shenjun.io.data.SXmlBean interface.");
		}
		if(clss!=null&&clss.length==1){
			Class<?> cls=clss[0];SXmlBean sxb=null;
			try {
				sxb=(SXmlBean)cls.newInstance();
				sxb.bytesReceive();
			} catch (InstantiationException e) {
				log.error("构造CLS错误:"+e.getMessage(),e.getCause());
			}
			
			method.invoke(this, sxb);
			return sxb;
			
		}
		throw new RequestMethodErrorException("SXmlActionSupport invoke");
	}
	
	@Override
	protected Object invoke() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, RequestMethodErrorException{
		if(this.getRetrunAnnotation(this.getExecuteMethod().getAnnotations())==null){
			return this.sXmlInvoke();
		}else{
			return super.invoke();
		}
	}
	
	@Override
	public String execute() throws Exception {
		//xml
		this.returnEnum=ReturnEnum.XML_TYPE;
		return super.execute();
	}

}
