package com.shenjun.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author: 沈军
 * @category 显示Jsp的地址和相关信息
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class JspAddrTag extends TagSupport{
	
	private Logger log = Logger.getLogger(JspAddrTag.class);
	
	@SuppressWarnings("static-access")
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			HttpServletRequest request =(HttpServletRequest)this.pageContext.getRequest();
	
			out.println("<!--当前页面信息-->");
			out.println("<!--JSP地址:{"+request.getServletPath()+"}-->");
			out.println("<!--请求地址:{"+request.getQueryString()+"}-->");
		} catch (IOException e) {
			log.error(e+"获取当前JSP页面地址出错.",e.getCause());
		}
		return this.EVAL_PAGE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
