package com.shenjun.web.dispatcher;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.dispatcher.StreamResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.shenjun.enums.BrowserType;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.WebUtil;
import com.shenjun.web.thread.Lc;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class StreamSuperResult extends StreamResult {
	
	 protected String contentType = "text/plain";
	 protected String contentDisposition = "inline";
	 protected String inputName = "inputStream";
	 protected int bufferSize = 1024;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void setMessage(String contentType,String fileName){
		setMessage(contentType,fileName,null);
	}
	
	public static void setMessage(String contentType,String fileName,InputStream inputStream){
		Lc.setContext("contentType", contentType);
		try {
			String eFileName="";
			if(WebUtil.getBrowser()==BrowserType.MSIE){
				eFileName=java.net.URLEncoder.encode(fileName, "UTF-8");
			}else{
				eFileName=new String((fileName).getBytes(WebUtil.getCharacterEncoding()),"iso8859-1");
			}
			Lc.setContext("contentDisposition", "attachment;filename=\""+eFileName+"\"");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(inputStream!=null)
			Lc.setContext("InputStreamName", inputStream);
	}

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		if(Struts2Util.getContext("contentType")!=null){
			this.contentType=String.valueOf(Struts2Util.getContext("contentType"));
		}
		if(Struts2Util.getContext("contentDisposition")!=null){
			this.contentDisposition=String.valueOf(Struts2Util.getContext("contentDisposition"));
		}
		if(Struts2Util.getContext("inputName")!=null){
			this.inputName=String.valueOf(Struts2Util.getContext("inputName"));
		}
		if(Struts2Util.getContext("bufferSize")!=null){
			this.bufferSize=Integer.valueOf(String.valueOf(Struts2Util.getContext("bufferSize")));
		}
		super.doExecute(finalLocation, invocation);
	}
	

}
