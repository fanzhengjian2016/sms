package com.shenjun.web.thread;

import javax.servlet.http.HttpSession;

import nl.justobjects.pushlet.core.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.enums.SqlErrorEnum;
import com.shenjun.enums.UserType;
import com.shenjun.web.listens.SessionListen;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class User<T> {
	
	private static Log log=LogFactory.getLog(User.class);
	
	public static String USER_INFO="USER_INFO";
	
	private String ipAddr = "0.0.0.0";
	
	private String userNo = "00000";
	
	private String userName ="未登陆";
	
	private String deptId = "0";
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	private String deptName = "未知部门";
	
	private UserType userType= UserType.BaseUser;
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	private String sqlServerName;
	
	private String sign;
	
	private SqlErrorEnum error;
	
	private Session pushletSession;
	
	public Session getPushletSession() {
		return pushletSession;
	}

	public void setPushletSession(Session pushletSession) {
		this.pushletSession = pushletSession;
	}

	private T exp;
	
	public T getExp() {
		return exp;
	}

	public void setExp(T exp) {
		this.exp = exp;
	}

	public SqlErrorEnum getError() {
		return error;
	}

	public void setError(SqlErrorEnum error) {
		this.error = error;
	}


	public User(String ipAddr,String dataBaseName,String userNo ,String userName){
		this.ipAddr = ipAddr;
		this.sqlServerName = dataBaseName;
		this.userNo = userNo;
		this.userName = userName;
	}
	
	public User(){}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSqlServerName() {
		return sqlServerName;
	}

	public void setSqlServerName(String sqlServerName) {
		this.sqlServerName = sqlServerName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 通过用户编号获取一个用户对象
	 * @param userNo
	 * @return
	 */
	public static <T> User<T> getUser(String userNo){
		HttpSession session=SessionListen.getUserSesstionMap().get(userNo);
		@SuppressWarnings("unchecked")
		User<T> user = (User<T>)session.getAttribute(USER_INFO);
		if(user==null){
			log.error("获取用户:"+userNo+" is null,Session id:"+session.getId());
		}
		return user;
	}
}
