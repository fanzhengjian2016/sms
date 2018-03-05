package com.shenjun.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.shenjun.util.JSONUtils;
import com.shenjun.util.RegexUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.util.XMLUtil;



public class AddrFilterLoadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(AddrFilterLoadServlet.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		Document doc=XMLUtil.read("xml/addrfilter.xml");
		
		List<Element> rules = doc.selectNodes("/addrfilter/rule");
		
		for(Element rule : rules){
			Map<String,String> ruleMap = new HashMap<String, String>();
			
			ruleMap.put("name", rule.attributeValue("name")+"");
			
			///////////////////ip/////////////////////////////////////////////////
			List<Element> expressions = rule.selectNodes("ip/expression");
			String[] expressionsStr = new String[expressions.size()];
			for(int i=0,len=expressions.size();i<len;i++){
				expressionsStr[i]="^("+(expressions.get(i).getText())+")$";
			}
			ruleMap.put("ip", StringUtil.join(expressionsStr, "|"));

			///////////////////addr//////////////////////////////////////////////////
			List<Element> urls = rule.selectNodes("urls/url");
			String[] urlsStr = new String[urls.size()];
			for(int i=0,len=urls.size();i<len;i++){
				urlsStr[i]="^("+(urls.get(i).getText())+")";
			}
			ruleMap.put("urls", StringUtil.join(urlsStr, "|"));
			
			///////////////////log/off//////////////////////////////////////////////////
			Element logOffs = (Element) rule.selectSingleNode("log/off");
			if(logOffs!=null){
				ruleMap.put("logoffs", logOffs.getText());
			}
			
			///////////////////log/on//////////////////////////////////////////////////
			Element logOns = (Element) rule.selectSingleNode("log/on");
			if(logOns!=null){
				ruleMap.put("logons", logOns.getText());
			}
			ruleList.add(ruleMap);
		}
		
		log.info("init addrfilter:"+JSONUtils.toJson(ruleList));
		
//		
//		//filter addr
//		List<Node> nodes= doc.selectNodes("/addrfilter/allows/url");
//		StringBuilder sb = new StringBuilder();
//		for(Node node :nodes){
//			sb.append("^("+node.getText()+")|");
//		}
//		if(sb.length()>0)
//			sb.deleteCharAt(sb.length()-1);
//		log.info("load allows ["+sb.toString()+"]");
//		filterMap.put("allows", sb.toString());
//		
//		//record display
//		List<Node> off_records= doc.selectNodes("/addrfilter/allows/record/off/postfix");
//		StringBuilder off_postfix = new StringBuilder();
//		for(Node node :off_records){
//			off_postfix.append("("+node.getText()+")$|");
//		}
//		if(off_postfix.length()>0)
//			off_postfix.deleteCharAt(off_postfix.length()-1);
//		
//		log.info("load allows off_postfix ["+off_postfix.toString()+"]");
//		
//		filterMap.put("off_postfix", off_postfix.toString());
//		
//		//record no display
//		List<Node> on_records= doc.selectNodes("/addrfilter/allows/record/on/postfix");
//		StringBuilder on_postfix = new StringBuilder();
//		for(Node node :on_records){
//			on_postfix.append("("+node.getText()+")$|");
//		}
//		if(on_postfix.length()>0)
//			on_postfix.deleteCharAt(on_postfix.length()-1);
//		
//		log.info("load allows on_postfix ["+on_postfix.toString()+"]");
//		
//		filterMap.put("on_postfix", on_postfix.toString());
//		
//		
//		//ip
//		List<Node> ip_records= doc.selectNodes("/addrfilter/allows/ip/expression");
//		StringBuilder ipstr = new StringBuilder();
//		for(Node node :ip_records){
//			ipstr.append("^("+node.getText()+")$|");
//		}
//		if(ipstr.length()>0)
//			ipstr.deleteCharAt(ipstr.length()-1);
//		
//		log.info("load allows ipstr ["+ipstr.toString()+"]");
//		
//		filterMap.put("ipstr", ipstr.toString());
	}
	
	private static  List<Map<String,String>> ruleList = new ArrayList<Map<String,String>>();
	
	public static boolean isAction(String url){
		if(RegexUtil.isFindStr("\\.action",url)){
			return true;
		}
		return false;
	}
	
	/**
	 * Allows pass
	 * @param url
	 * @return
	 */
	public static boolean isAllows(String url,Map<String,String> ruleMap){
		if(RegexUtil.isFindStr(ruleMap.get("urls"),url)){
			return true;
		}
		return false;
	}
	/**
	 * hidden 
	 * @param url
	 * @return
	 */
	public static boolean isOnPostfix(String url,Map<String,String> ruleMap){
		if(ruleMap.get("logons")!=null&&RegexUtil.isFindStr(ruleMap.get("logons"),url)&&!isAction(url)){
			return true;
		}
		return false;
	}
	/**
	 * display
	 * @param url
	 * @return
	 */
	public static boolean isOffPostfix(String url,Map<String,String> ruleMap){
		if(ruleMap.get("logoffs")!=null&&RegexUtil.isFindStr(ruleMap.get("logoffs"),url)&&!isAction(url)){
			return true;
		}
		return false;
	}
	
	/**
	 * 找到角色，通过，找不到，不通过
	 * @param ip
	 * @return
	 */
	public static Map<String,String> checkIp(String ip){
		for(Map<String,String> ruleMap : ruleList){
			if(RegexUtil.isFindStr(ruleMap.get("ip"),ip)){
				return ruleMap;
			}
		}
		return null;
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest reqquest,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(1001, "本页面不允许发送请求");
	}
	
	public static void main(String[] args) {
		
		System.out.println(RegexUtil.isFindStr("^(127\\.0\\.0.1)$","127.1.0.1"));
	}


}
