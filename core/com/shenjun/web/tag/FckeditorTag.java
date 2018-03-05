package com.shenjun.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author: 沈军
 * @category 获取Web Root所在的地址
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class FckeditorTag extends TagSupport {
	
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(FckeditorTag.class);
	
	@SuppressWarnings("static-access")
	@Override
	public int doStartTag() throws JspException {
		//JspWriter out = this.pageContext.getOut();
/*		try {
			HttpServletRequest request =(HttpServletRequest)this.pageContext.getRequest();
			FCKeditor fckEditor = new FCKeditor(request, "EditorDefault");
			fckEditor.setBasePath("/plugins/fckeditor");
			fckEditor.setHeight(this.height);
			fckEditor.setWidth(this.width);
			//fckEditor.setConfig("ImageBrowserURL", "/J2EE/userfiles");
			fckEditor.setValue(value);
			out.println(fckEditor);
		} catch (IOException e) {
			log.error(e+"获取当前Web root地址出错.",e.getCause());
		}*/
		return this.EVAL_PAGE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	private String width;
	
	private String height;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
