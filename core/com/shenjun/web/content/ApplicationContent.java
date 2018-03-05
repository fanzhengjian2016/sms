package com.shenjun.web.content;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.shenjun.util.XMLUtil;
import com.shenjun.web.listens.ContentListen;
/**
 * ApplicationContent Manager
 * @author shenjun
 *
 */
public class ApplicationContent {
	public static  Log log = LogFactory.getLog(ApplicationContent.class);

	@SuppressWarnings("unchecked")
	public static boolean buildAttrib() {
		// TODO Auto-generated method stub
		try {
			Document doc=XMLUtil.read("/xml/content.xml");
			List<Element> eles=doc.selectNodes("//content/application/attribute");
			for(Element ele : eles){
				String name=ele.attributeValue("name");
				String value=ele.getText();
				log.debug("load ServletContext name:"+name+"  value:"+value);
				ContentListen.getServletContext().setAttribute(name, value);
			}
		} catch (Exception e) {
			log.error("CompileXml:"+e.getMessage(),e.getCause());
			return false;
		}
		return true;
	}
}
