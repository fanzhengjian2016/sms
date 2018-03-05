package com.commons.db;
/**
 * authtype,paramsContent,remoteUser,connSign
 * @author jbox_user
 *
 */
public class AuthBean {
	private String authType;
	
	private String paramsContent;
	
	private String remoteUser;
	
	private String connSign;
	
	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getParamsContent() {
		return paramsContent;
	}

	public void setParamsContent(String paramsContent) {
		this.paramsContent = paramsContent;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	public String getConnSign() {
		return connSign;
	}

	public void setConnSign(String connSign) {
		this.connSign = connSign;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getLocalUser() {
		return localUser;
	}

	public void setLocalUser(String localUser) {
		this.localUser = localUser;
	}

	private String authNo;
	
	private String authCode;
	
	private String localUser;
	
}
