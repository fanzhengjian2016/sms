package com.shenjun.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.shenjun.plugins.jackson.JSON;

/**
 * xml 操作类
 * @author 沈军
 * 版权个人所有,严禁侵权
 */
public class XMLUtil {
	@SuppressWarnings("unused")
	private static Log  log = LogFactory.getLog(XMLUtil.class);
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void eachWriteJson(Element element,List json){
		Iterator iter = element.elementIterator();
		while(iter.hasNext()){
			Element recordEle = (Element) iter.next();
			Map jsonobj = new LinkedHashMap();
			List<Attribute> attrs = recordEle.attributes();
			for(Attribute attr : attrs){
				jsonobj.put(attr.getName(), attr.getValue());
			}

			List cjson=new ArrayList();
			jsonobj.put("children", cjson);
			json.add(jsonobj);
			eachWriteJson(recordEle,cjson);
		}
	}
	/**
	 * 将Doc文件转成JSON
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSON xmlToJson(Document doc){
		Element element = doc.getRootElement();
		List jsonarr = new ArrayList();
		eachWriteJson(element,jsonarr);
		
		
		List<Attribute> attrs = element.attributes();
		Map jsonobj = new LinkedHashMap();
		for(Attribute attr : attrs){
			jsonobj.put(attr.getName(), attr.getValue());
		}
		jsonobj.put("children", jsonarr);
		jsonobj.put("expanded",true);
		List root = new ArrayList();
		root.add(jsonobj);
		
		return new JSON(root);
	}
	
	/**
	 * 读取 绝对位置 XML
	 * @param path WebUtil.getClassesRoot()+path
	 * @return
	 */
	@Deprecated
	public static Document readXml(String path){
		SAXReader xmlRead = new SAXReader();
		Document dom=null;
		try {
			dom = xmlRead.read(new File(path));
		} catch (DocumentException e) {
			log.error("readXml Error"+e,e.getCause());
		}
		return dom;
	}
	
	/**
	 * 相对位置
	 * @param path WebUtil.getClassesRoot()+path
	 * @return
	 */
	public static Document read(String path){
		return read(new File(WebUtil.getClassesRoot()+path));
	}
	
	/**
	 * 指定文件
	 * @param file
	 * @return
	 */
	public static Document read(File file){
		SAXReader xmlRead = new SAXReader();
		Document dom=null;
		try {
			dom = xmlRead.read(file);
		} catch (DocumentException e) {
			log.error("readXml DocumentException Error"+e,e.getCause());
		}
		return dom;
	}
	
	/**
	 * 文本转XML
	 * @param xmlStr
	 * @return
	 */
	public static Document StringToXml(String xmlStr){
		Document document=null;
		try {
			document = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			log.error(e.getMessage()+"[xmlutil method StringToXml]",e.getCause());
		} 
		return document;
	}
	/**
	 * 转换XML格式为正常Text格式
	 * @param content
	 * @return
	 */
	@Deprecated
	public static String wrapXmlContent(String content){
	    StringBuffer appender = new StringBuffer("");

	    if ((content != null) && (!content.trim().isEmpty())) {
	      appender = new StringBuffer(content.length());

	      for (int i = 0; i < content.length(); i++) {
	        char ch = content.charAt(i);
	        if ((ch == '\t') || (ch == '\n') || (ch == '\r') || 
	          ((ch >= ' ') && (ch <= 55295)) || 
	          ((ch >= 57344) && (ch <= 65533)) || (
	          (ch >= 65536) && (ch <= 1114111))) {
	          appender.append(ch);
	        }
	      }
	    }
	    String result = appender.toString();

	    return "<![CDATA[" + result.replaceAll("]]>", "]]<") + "]]>";
	}
	
	/**
	 * XML转成List的对象
	 * @param doc
	 * @return
	 */
	public static List<String[]> xmlToList(Document doc){
		List<Element> rows = doc.getRootElement().elements();
		List<String[]> data = new ArrayList<String[]>();
		for(Element el : rows){
			List<Element> cells = el.elements();
			//System.out.println(cells.size());
			String[] rowsString = new String[cells.size()];
			for(int i=0,len=cells.size();i<len;i++){
				//System.out.println(cell.getText());
				rowsString[i]=(((Element)(cells.get(i))).getText()+"");
			}
			data.add(rowsString);
		}
		return data;
	}
	
	/*public static void main(String[] args) {
		String xml="<data><rows><cell>userno</cell><cell>loginname</cell><cell>username</cell><cell>pwd</cell><cell>sex</cell><cell>old</cell><cell>deptno</cell><cell>phone</cell><cell>email</cell></rows><rows><cell>10001</cell><cell>mhshan</cell><cell>单明慧</cell><cell>77777</cell><cell>0</cell><cell>1991-01-21 00:00:00</cell><cell>D0001</cell><cell></cell><cell>guode@qq.com</cell></rows><rows><cell>10002</cell><cell>bubble</cell><cell>bubble</cell><cell>bubble</cell><cell>1</cell><cell>2014-09-24 00:00:00</cell><cell>D0001</cell><cell>88888888</cell><cell>guode@qq.com</cell></rows><rows><cell>10003</cell><cell>hjhooooo</cell><cell>洪建华</cell><cell>77777</cell><cell>1</cell><cell>2014-09-11 00:00:00</cell><cell>D0002</cell><cell>7777777</cell><cell>guode@qq.com</cell></rows><rows><cell>10004</cell><cell>uiuijk</cell><cell>很快了解</cell><cell>77777</cell><cell></cell><cell>2014-09-10 00:00:00</cell><cell>D0001</cell><cell>7777777</cell><cell>guode@qq.com</cell></rows><rows><cell>10005</cell><cell>testuu</cell><cell>测试</cell><cell>77777</cell><cell>1</cell><cell>2014-09-10 00:00:00</cell><cell>D0001</cell><cell>7777777</cell><cell>guode@qq.com</cell></rows><rows><cell>10006</cell><cell>linlin</cell><cell>林林</cell><cell>77777</cell><cell>1</cell><cell>1991-01-21 00:00:00</cell><cell>D0001</cell><cell>78459632</cell><cell>guode@qq.com</cell></rows></data>";
		XMLUtil.xmlToList(XMLUtil.StringToXml(xml));
	}*/
}
