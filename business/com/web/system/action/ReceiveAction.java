package com.web.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ResponseBody;

import com.plugins.waterfee.WaterFreeServiceManager;
import com.plugins.waterfee.util.CryptoUtils;
import com.shenjun.annotation.FilterAnnotation;
import com.shenjun.annotation.ReturnType;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.JSONUtils;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;
import com.shenjun.web.struts.SAction;
import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.LocalContent;
import com.shenjun.web.thread.User;

@ReturnType(ReturnEnum.AJAX_TYPE)
public class ReceiveAction extends SAction{
	
	@SuppressWarnings("unused")
	private WaterFreeServiceManager waterFreeServiceManager;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 接收文本消息url方式
	 * @return
	 */
	@ReturnType(ReturnEnum.JSP_REDIRECT_TYPE)
	public String receiveFileUrl(){
		String time=Lc.get("time");
		log.debug("333-----------------------time----->>"+time+"----->>");
		String json="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\""+
		new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"\",\"extend\":\"\"},\"body\":{\"fileType\":\""+ReadProperties.getProperty("fileType")+"\",\"extend\":\"\",\"fileName\":\""+
		time+"\",\"filePath\":\""+ReadProperties.getProperty("bingjiangftppath")+"\",\"acctOrgNo\":\""+ReadProperties.getProperty("acctOrgNo")+"\"}}";
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String receivefile=waterFreeServiceManager.receiveFile(json);
		try {
			return "/succeed.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/error.jsp";
	}

}
