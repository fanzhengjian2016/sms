package com.shenjun.web.dispatcher;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.ServletRedirectResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.web.struts.DispatchActionSuper;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class ServletSuperRedirectResult extends ServletRedirectResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		super.doExecute(Struts2Util.getContext(DispatchActionSuper.ServletDispatcherAtt)+"", invocation);
	}
	
	/**
     * Sends the redirection.  Can be overridden to customize how the redirect is handled (i.e. to use a different
     * status code)
     *
     * @param response The response
     * @param finalLocation The location URI
     * @throws IOException
     */
    @Override
	protected void sendRedirect(HttpServletResponse response, String finalLocation) throws IOException {
        //if (SC_FOUND == statusCode) {
    	if(SC_NOT_FOUND== statusCode) {
            response.sendRedirect(finalLocation);
        } else {
            response.setStatus(statusCode);
            response.setHeader("Location", finalLocation);
            response.getWriter().write(finalLocation);
            response.getWriter().close();
        }
    }
	
	
}
