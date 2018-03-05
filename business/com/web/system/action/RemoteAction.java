package com.web.system.action;



import com.plugins.mina.longshine.JxProtocolHandler;
import com.shenjun.annotation.ReturnType;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.web.struts.SAction;
import com.shenjun.web.thread.Lc;

@ReturnType(ReturnEnum.AJAX_TYPE)
public class RemoteAction extends SAction{
	
	public String getData(){
		return JxProtocolHandler.execMethod(
				Lc.get("execmethod"),
				Lc.get("execdata"));
	}
	
	
	public String getTestData(){
		return "{\"name\":\"hjh\",\"pass\":\"123\",\"method\":\""+Lc.get("execmethod")+"\"}";
	}
	
}
