package com.shenjun.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.shenjun.util.WebUtil;

/**
 * 处理XML请求的类
 * @author Administrator
 *
 */
public class XmlServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Log log = LogFactory.getLog(HttpServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("forwarding xml file.........."+request.getRequestURI());
		PrintWriter out = response.getWriter();
		try {
			
			response.setContentType("text/xml; charset=GBK");
			//response.setContentType("msxml-document; charset=GBK");

			String filePath=request.getRequestURI();
			filePath=filePath.substring(
					request.getContextPath().length()
			);
			log.info("请求XML文件:"+filePath);
			
			if(filePath.indexOf(".")==-1){
				filePath+=".xml";
			}
			
			String path=WebUtil.getClassesRoot()+filePath;
			SAXReader reader = new SAXReader();
		    Document document= reader.read(new File(path));
		    out.write(document.asXML());
		    out.flush();out.close();
		} catch (Exception e) {
			log.error("获取XML文件出错."+e,e.getCause());
			out.flush();out.close();
		}
	}

}
