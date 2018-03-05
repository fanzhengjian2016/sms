package com.commons.util;

import org.dom4j.Document;

import com.shenjun.util.XMLUtil;

public class XmlFileManager {
	public static Document getJbTplDoc(){
		return XMLUtil.read("xml\\config\\base_template.xml");
	}
	
	public static Document getRendererDoc(){
		return XMLUtil.read("xml\\config\\renderer.xml");
	}
}
