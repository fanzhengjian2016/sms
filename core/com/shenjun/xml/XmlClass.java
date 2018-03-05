package com.shenjun.xml;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * xml 构建类
 * @author 沈军
 *
 */
public class XmlClass {
	
	private final static String nodeName="node";
	
	private Log  log = LogFactory.getLog(this.getClass());
	
	/**
	 * 获取文档
	 * @param docList List表
	 * @param nodeIndex 节点ID
	 * @param parentIndex 父节点ID
	 * @param attributes 属性列表
	 * @return
	 */
	public Document toXml(List<Object[]> docList,Integer nodeIndex,Integer parentIndex,String[] attributes){
		return this.toXml(docList,"0",nodeIndex, parentIndex, attributes);
	}
	
	/**
	 * 获取文档
	 * @param docList List表
	 * @param nodeIndex 节点ID
	 * @param parentIndex 父节点ID
	 * @param attributes 属性列表
	 * @param filePath 文件路径
	 * @return
	 */
	public Document toXml(List<Object[]> docList,Integer nodeIndex,Integer parentIndex,String[] attributes,String filePath){
		return this.toXml(docList, "0", nodeIndex, parentIndex, attributes, filePath);
	}
	
	/**
	 * 写入文档
	 * @param docList List表
	 * @param rootId 头ID
	 * @param nodeIndex 节点ID
	 * @param parentIndex 父节点ID
	 * @param attributes 属性列表
	 * @param filePath 文件路径
	 * @return Document
	 */
	public Document toXml(List<Object[]> docList,String rootId,Integer nodeIndex,Integer parentIndex,String[] attributes,String filePath){
		Document document=this.toXml(docList,rootId,nodeIndex, parentIndex, attributes);
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(filePath), OutputFormat.createPrettyPrint());
			writer.write(document);
		    writer.close();
		}catch(Exception e){
			log.error("写入权限XML"+e,e.getCause());
			return null;
		}
		
		return document;
	}
	/**
	 * 构建文档
	 * @param docList List表
	 * @param rootId 头ID
	 * @param nodeIndex 节点ID
	 * @param parentIndex 父节点ID
	 * @param attributes 属性列表
	 * @return
	 */
	public Document toXml(List<Object[]> docList,String rootId,Integer nodeIndex,Integer parentIndex,String[] attributes){
		Map<String,List<Element>> map = new HashMap<String, List<Element>>();
		List<Element>ls=null;
		for(Object[] nodeObj:docList){
			Element node=DocumentHelper.createElement(nodeName);
			for(int i=0,len=attributes.length;i<len;i++){
				node.addAttribute(attributes[i]+"", nodeObj[i]+"");
			}
			ls=map.get(nodeObj[parentIndex]+"");
			if(null==ls){
				ls=new ArrayList<Element>();ls.add(node);
				map.put(nodeObj[parentIndex]+"",ls);
			}else{
				ls.add(node);
				map.put(nodeObj[parentIndex]+"",ls);
			}
		}
		
		Document document = DocumentHelper.createDocument();
		
		Element rootNode=map.get(rootId).get(0);
		document.add(rootNode);

		this.eachWriteXml(rootNode, map, attributes[nodeIndex]);
		return document;
	}
	
	/**
	 * 文档迭代
	 * @param root
	 * @param map
	 * @param nodeId
	 */
	private void eachWriteXml(Element root,Map<String,List<Element>> map,String nodeId){
		String key=root.attribute(nodeId).getValue();
		List<Element> els=map.get(key);
		if(els!=null)
		for(Element el : els){
			root.add(el);
			this.eachWriteXml(el,map,nodeId);
		}
	}
	
	
	/**
	 * 测试用例
	 * @param args
	 */
	public static void main(String[] args) {
		List<Object[]> ls = new ArrayList<Object[]>();
		ls.add(new Object[]{1,0,"cdfs1"});
		ls.add(new Object[]{2,1,"cdfs2"});
		ls.add(new Object[]{3,1,"cdfs3"});
		ls.add(new Object[]{4,2,"cdfs4"});
		ls.add(new Object[]{5,7,"cdfs5"});
		ls.add(new Object[]{6,1,"cdfs6"});
		ls.add(new Object[]{7,2,"cdfs7"});
		ls.add(new Object[]{8,5,"cdfs8"});
		ls.add(new Object[]{9,8,"cdfs9"});
		
		
		Document document =new XmlClass().toXml(ls,0,1,new String[]{"id","parentid","name"});
		System.out.println(document.asXML());
		
	}
}
