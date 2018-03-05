package com.shenjun.plugins.rampart;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class WsServiceAuthHandler implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		/*WSPasswordCallback pCallback = (WSPasswordCallback) callbacks[0];
		
		// TODO Auto-generated method stub
		String id = pCallback.getIdentifier();
		
		String password = pCallback.getPassword();
		
		System.out.println("接收到WebService请求，userName[" + id + "],password[" + password + "]......");
		
		if (false){  
			System.out.println("验证用户失败，原因：您没有权限访问,用户名为空！");  
			throw new UnsupportedCallbackException(pCallback, "您没有权限访问,用户名为空！");
		}
		
		*//** 
		* 此处应该这样做：  
		* 1. 查询数据库，得到数据库中该用户名对应密码  
		* 2. 设置密码，wss4j会自动将你设置的密码与客户端传递的密码进行匹配  
		* 3. 如果相同，则放行，否则返回权限不足信息 
		*//*  
		pCallback.setPassword("123456");
		
		 
		* if (!PASSWORD.equals(password)) {
		* System.out.println("验证用户失败，原因：您没有权限访问,密码错误！"); throw new
		* UnsupportedCallbackException(pCallback, "您没有权限访问,密码错误！"); }
		  
		
		pCallback.setIdentifier("service");*/
	}

}
