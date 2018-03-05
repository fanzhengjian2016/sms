package com.shenjun.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.shenjun.util.WebUtil;

/**
 * @author: 沈军
 * @category 获取Web Root所在的地址
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class WebRootTag extends TagSupport {
	
	private Logger log = Logger.getLogger(WebRootTag.class);
	
	@SuppressWarnings("static-access")
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			HttpServletRequest request =(HttpServletRequest)this.pageContext.getRequest();
			/*HttpServletRequest request =(HttpServletRequest)this.pageContext.getRequest();
			String localAddr=request.getLocalAddr();
			if("0.0.0.0".equals(localAddr)){
				localAddr="127.0.0.1";
			}
			String webRoot = "http://"+localAddr+":"+request.getLocalPort()
						+request.getContextPath()+"/";*/
			String webRoot=WebUtil.getWebRoot(request);
			out.print(webRoot);
		} catch (IOException e) {
			log.error(e+"获取当前Web root地址出错.",e.getCause());
		}
		return this.EVAL_PAGE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
