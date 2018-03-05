package com.shenjun.web.listens;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;

import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.User;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class SessionListen implements HttpSessionAttributeListener {
	
	private static Map<String, HttpSession> UserMap = new HashMap<String, HttpSession>();
	private static Map<String,String> SessionIdMap = new HashMap<String, String>();
	
	private void removeUser(String userno){
		SessionIdMap.remove(UserMap.get(userno).getId());
		UserMap.remove(userno);
		
	}
	
	private void addUser(String userno,HttpSession session){
		updateSession(userno,session);
	}
	
	private static Logger log = Logger.getLogger(SessionListen.class.getName());
	
	/**
	 * 加入时 Session
	 */
	public void attributeAdded(HttpSessionBindingEvent event) {
		log.info("attributeAdded Session:"+event.getName());
		if(event.getName().equals(User.USER_INFO)){
			User ut =(User)event.getValue();
			if(UserMap.get(ut.getUserNo())!=null){
				log.info("Force "+ut.getUserNo()+" login out。");
				UserMap.get(ut.getUserNo()).invalidate();
			}
			this.addUser(ut.getUserNo(),event.getSession());
		}
			
	}
	
	/**
	 * 移除Session时
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		log.info("attributeRemoved Session:"+event.getName());
		if(event.getName().equals(User.USER_INFO)){
			User ut =(User)event.getValue();
			if(event.getSession()==UserMap.get(ut.getUserNo())){
				this.removeUser(ut.getUserNo());
				log.info("attributeRemoved Session HttpSessionBindingEvent remove:"+event.getName());
			}else{
				log.info("attributeRemoved Session HttpSessionBindingEvent not remove:"+event.getName());
			}
		}
	}

	/**
	 * 替换Session时
	 */
	public void attributeReplaced(HttpSessionBindingEvent event) {
		log.info("Session attributeReplaced:"+event.getName());
		if(event.getName().equals(User.USER_INFO)){
			User ut =(User)event.getValue();
			this.addUser(ut.getUserNo(),event.getSession());
		}
	}
	
	public static  Map<String, HttpSession> getUserSesstionMap(){
		return SessionListen.UserMap;
	}
	
	public static User getUser(String sessionid){
		User user = (User) UserMap.get(SessionIdMap.get(sessionid)).getAttribute(User.USER_INFO);
		if(user!=null){
			log.info("成功获取User:"+user.getUserNo()+",sessionid:"+sessionid);
		}
		return user;
	}
	
	/**
	 * update Session
	 * @param userno
	 * @param session
	 */
	public static synchronized void updateSession(String userno,HttpSession session){
		SessionIdMap.put(session.getId(),userno);
		UserMap.put(userno, session);
	}
}
