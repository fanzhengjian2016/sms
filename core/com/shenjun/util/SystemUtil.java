package com.shenjun.util;

import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
/**
 * 系统容器方法类
 * @author shenjun
 *
 */
public class SystemUtil {
	private static final Log log = LogFactory.getLog(SystemUtil.class);
	
	/**
	 * 获取本地Ip地址
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getLocalIp() throws UnknownHostException{
		InetAddress addr=InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();
		return ip;
	}
	/**
	 * 获取本地端口号
	 * @param actionContext
	 * @return
	 */
	public static int getLocalPort(){
		
		HttpServletRequest request=ServletActionContext.getRequest();
		return request.getServerPort();//.getLocalPort();
	}
}
