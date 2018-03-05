package com.web.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.plugins.waterfee.WaterFreeServiceManager;
import com.plugins.waterfee.util.CryptoUtils;
import com.shenjun.annotation.FilterAnnotation;
import com.shenjun.annotation.ReturnType;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.JSONUtils;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;
import com.shenjun.web.struts.SAction;
import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.LocalContent;
import com.shenjun.web.thread.User;

@ReturnType(ReturnEnum.AJAX_TYPE)
public class LoginAction extends SAction{
	
	@SuppressWarnings("unused")
	private WaterFreeServiceManager waterFreeServiceManager;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@ReturnType(ReturnEnum.JSP_REDIRECT_TYPE)
	@FilterAnnotation("clsfilter")
	public String checkUser(){
		if(this.login()){
			return "/jbox_ext/index.jsp";
		}
		return "/login.jsp";
	}
	
	/**
	 * json 方法检查用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkUserJson(){
		if(this.login()){
			Map user = new LinkedHashMap();
			user.put("success", true);
			user.put("userno", Lc.getUser().getUserNo());
			user.put("deptid", Lc.getUser().getDeptId());
			user.put("username", Lc.getUser().getUserName());
			user.put("sysname", Lc.getSysContent("touch_sys_name"));
			log.debug(JSONUtils.toJson(user));
			
			return JSONUtils.toJson(user);
		}else{
			String res="{success:false,msg:''}";
			log.debug(res);
			return res;
		}
		
	}

	
	public String getUserInfo(){
		log.info("用户：user:["+LocalContent.getUser().getUserNo()+"]");
		//log.info("----->>"+WaterFreeServiceManager.queryWaterFee(""));
		return "{user:111}";
	}
	
	///////////////////////////////////private/////////////////////////////////////////////////////////////////
	private boolean login(){
		String userno= Struts2Util.get("userno");
		List<Object[]> ls=Lc.getConn().createSQLQuery("select userno,username,deptno from tb_sys_user where loginName=? and pwd=?", 
				userno,Struts2Util.get("pwd"));
		if(ls.size()>0){
			User<Object> user = new User<Object>();
			user.setUserNo(ls.get(0)[0]+"");
			user.setUserName(ls.get(0)[1]+"");
			user.setDeptId(ls.get(0)[2]+"");
			user.setIpAddr(Struts2Util.getRequestAddr()); 
			Struts2Util.setSessionContent(User.USER_INFO, user);
			
			log.info("USER-AGENT("+user.getUserNo()+")->"+Lc.getServletRequest().getHeader("USER-AGENT"));
			log.info("USER-CHAR("+user.getUserNo()+")->"+WebUtil.getCharacterEncoding());
			log.info("USER-DB("+user.getUserNo()+")->"+Lc.getConn().getDBType());
			return true;
		}else{
			log.info("USER-AGENT(login"+userno+")->"+Lc.getServletRequest().getHeader("USER-AGENT"));
			log.info("USER-CHAR(login"+userno+")->"+WebUtil.getCharacterEncoding());
			Struts2Util.setRequestContent("error", "用户名或密码错误"); 
			return false;
		}
	}
}
