package com.shenjun.plugins.sftp;

import com.shenjun.util.StringUtil;

public class ConnectConfig {
	/**
	 * 连接的标识号
	 */
	private String sign="default";
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		//不能存储空值
		if(StringUtil.isNB(sign)){
			this.sign = sign;
		}
	}
	private String host;
	private int port=22;
	private String userName;
	private int timeout=1000*1000;
	private String passWord;
	private String PrivateKey;
	
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPrivateKey() {
		return PrivateKey;
	}
	public void setPrivateKey(String privateKey) {
		PrivateKey = privateKey;
	}
	
	
}
