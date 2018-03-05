package com.shenjun.web.listens;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.io.WriteVisitingLog;
import com.shenjun.util.StringUtil;
import com.shenjun.util.WebUtil;
import com.shenjun.web.servlet.AddrFilterLoadServlet;
import com.shenjun.web.thread.LocalContent;
import com.shenjun.web.thread.User;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class TransmitFilter implements Filter{
	
	private static final Log log = LogFactory.getLog(TransmitFilter.class);
	
	public void destroy() {	
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
	
		/*******@author shenjun *********************************************************/
		/*******访问地址和参数的处理过程***********************************************/
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		HttpServletResponse httpServletResponse=(HttpServletResponse)response;
		
		String url = httpServletRequest.getServletPath()+(httpServletRequest.getQueryString()==null?
				 "":"?"+httpServletRequest.getQueryString());
		
		httpServletRequest.setCharacterEncoding("UTF-8");
		//httpServletResponse.setCharacterEncoding("UTF-8");
		
		//从线程里获取用户名
		HttpSession session=httpServletRequest.getSession();
		@SuppressWarnings("unused")
		String sessionId=session.getId();
		User<Object> user = (User<Object>)session.getAttribute(User.USER_INFO);
		
		if(user==null&&httpServletRequest.getParameter("JSESSIONID")!=null){
			sessionId=httpServletRequest.getParameter("JSESSIONID");
			user=SessionListen.getUser(httpServletRequest.getParameter("JSESSIONID"));
		}
		
		String clientStr = "Null";
		if(user!=null){
			clientStr = user.getUserNo()+"("+user.getSqlServerName()+")";
			
			if(User.getUser(user.getUserNo())==null){
				log.error("无法从Map中获取Session的对象，判断已失效----------");
				SessionListen.updateSession(user.getUserNo(), session);
			}
			
		}
		
		LocalContent.setUser(user);
		//地址处理
		String requestLog=StringUtil.processorAddr(httpServletRequest, clientStr, url);
		
		/****************************************************************************************/
		
		String ip=httpServletRequest.getRemoteAddr();
		
		
		/**
		 * ip filter
		 **********************************/
		 Map<String, String> ruleMap = AddrFilterLoadServlet.checkIp(ip);
		if(ruleMap==null){
			log.error("["+ip+"] Unlawful,Invalid request");
			throw new IOException("["+ip+"] Unlawful,Invalid request,非法IP，无法进入程序。");
		}
		
		/**
		 * 无记录拦截
		 */
		if(AddrFilterLoadServlet.isOffPostfix(url,ruleMap)){
			filterChain.doFilter(request, response);
			return;
		}
		
		String logStr=requestLog+"rule["+ruleMap.get("name")+"][session:"+httpServletRequest.getSession().getId()+"]\n";
		log.debug(logStr);
		WriteVisitingLog.setMsg(logStr);
		
		/**
		 * 二级有记录拦截
		 */
		if(AddrFilterLoadServlet.isOnPostfix(url,ruleMap)){
			filterChain.doFilter(request, response);
			return;
		}
		
		//String accept=httpServletRequest.getHeader("Accept");
		//log.info("Accept :"+accept);
		
		
		/*********************************/
		
		/*******@author shenjun *********************************************************/
		//(!RegexUtil.isFindStr("^(/system/login.action)",url))&&(!RegexUtil.isFindStr("^(/services)",url))
		/*********检测登录口Session*******************************************************/
		//&&!AddrFilterLoadServlet.isAllows(url)
		if(user==null&&!AddrFilterLoadServlet.isAllows(url,ruleMap)){
			String requestType = httpServletRequest.getHeader("X-Requested-With");
			log.info("X-Requested-With:"+requestType);
			
			if("XMLHttpRequest".equals(requestType)){
				log.info("test url error，return x-Json string[!login_timeout]");
				PrintWriter out= response.getWriter();
				//response.setCharacterEncoding("utf-8");
				response.setContentType("application/x-json");
				out.print("!login_timeout");
				out.flush();
			}else{
				String reurl=WebUtil.getWebRoot(httpServletRequest);
				log.info("test url error，Redirect to："+reurl);
				httpServletResponse.sendRedirect(reurl);
			}
		}
		else{
	        httpServletResponse.addHeader("pragma", "no-cache");
	        httpServletResponse.addHeader("cache-control", "no-cache");
	        httpServletResponse.addHeader("expires", "0");
	        
	        // 写入对象进行本地线程类副本
	        filterChain.doFilter(request, response);
		}
		/****************************************************************/
		
	}
	
	
	public void init(FilterConfig arg0) throws ServletException {
	}

}
