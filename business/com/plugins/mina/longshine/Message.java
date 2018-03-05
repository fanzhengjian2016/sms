package com.plugins.mina.longshine;
/**
 * 消息传输类
 * @author shenjun
 *
 */
public class Message {
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	private String data;
	
	public Message(String code,String data){
		this.code=code;
		this.data=data;
	}
}
