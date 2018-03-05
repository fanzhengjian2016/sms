package com.webservice.service;

import com.shenjun.annotation.WebServiceDescription;

@WebServiceDescription(description="版本服务")
public class JVersion {
	public String getVersion(){
		return "V1.1";
	}
	
	public String getOtherVersion(String sysName){
		return sysName+"[v.1]";
	}
}
