package com.shenjun.web.struts;

import com.shenjun.enums.ReturnEnum;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
@Deprecated
public class JsonDispatchActionSuper extends DispatchActionSuper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute() throws Exception {
		this.returnEnum=ReturnEnum.AJAX_TYPE;
		return super.execute();
	}

}
