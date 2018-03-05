package com.commons.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.annotation.ReturnType;
import com.shenjun.annotation.imp.Filter;
import com.shenjun.enums.ReturnEnum;

public class ClsFilter implements Filter{
	
	public  Log log = LogFactory.getLog(ClsFilter.class);

	public int before(Object... objects) {
		// TODO Auto-generated method stub
		log.debug("ClsFilter before");
		return 1;
	}
	
	@ReturnType(ReturnEnum.JSP_CODE_NAME_TYPE)
	public Object redirect(Object... objects) {
		// TODO Auto-generated method stub
		log.debug("ClsFilter redirect");
		return "/index.jsp";
	}

	public int after(Object... objects) {
		// TODO Auto-generated method stub
		log.debug("ClsFilter after");
		return 0;
	}

}
