package com.shenjun.io.data;

public interface SXmlBean {	
	/**
	 * 返回请求的bytes字符串
	 * @return
	 */
	public byte[] bytesSend();
	
	/**
	 * 接收请求的bytes
	 * @return
	 */
	public void bytesReceive();
}
