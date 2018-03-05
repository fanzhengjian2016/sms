package com.shenjun.web.tag;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.shenjun.util.DateUtil;
/**
 * 
 * @author 沈军
 * 版权个人所有
 * 日期操作类
 */
public class DateTimeTag  extends TagSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pattern = DateUtil.LONG_DATE;
	
	private Date date=null;
	
	private Integer field=null;
	
	public void setField(Integer field) {
		this.field = field;
	}

	private Integer var=null;
	
	public void setVar(Integer var) {
		this.var = var;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void initVal(){
		pattern = DateUtil.LONG_DATE;
		date=null;
		field=null;
		var=null;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		if(date==null){
			Calendar c = java.util.Calendar.getInstance();
			if(field==null){
				field=Calendar.HOUR;
			}
			
			if(var!=null){
				c.add(field, var);
			}
			
			date=c.getTime();
		}
		
		String dateStr=DateUtil.toStr(date, pattern);
		
		JspWriter out = this.pageContext.getOut();
		try {
			out.print(dateStr);
		} catch (IOException e) {
			log.error("DateTimeTag error"+e.getMessage(),e.getCause());
		}finally{
			initVal();
		}
		return TagSupport.EVAL_PAGE;
	}

	private Logger log = Logger.getLogger(DateTimeTag.class);
	

}
