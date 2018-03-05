package com.shenjun.web.struts;

import com.shenjun.enums.ReturnEnum;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */
@Deprecated
public class ActionJspSupport extends DispatchActionSuper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute() throws Exception {
		this.returnEnum=ReturnEnum.JSP_CODE_NAME_TYPE;
		return super.execute();
	}
}
