package com.commons.util;

import java.util.List;

import com.commons.db.AuthBean;
import com.shenjun.collection.SuperList;
import com.shenjun.web.thread.Lc;
/**
 * 获取授权信息
 * @author jbox_user
 *
 */
public class AuthUtil {
	@SuppressWarnings("unchecked")
	public static AuthBean getAuth(String authtype){
		@SuppressWarnings("unchecked")
		SuperList<Object[]> authList=Lc.getConn().createSuperSQLQuery("select authtype,paramsContent,remoteUser,connSign " +
				"from tb_sys_auth_msg where localuser=? and authtype=?", 
				Lc.getUser().getUserNo(),authtype);
		
		List<AuthBean> beans=authList.toClass(AuthBean.class);
		if(beans.size()>0){
			return beans.get(0);
		}
		return null;
	}
}
