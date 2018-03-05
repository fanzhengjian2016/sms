package com.plugins.waterfee;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import nl.justobjects.pushlet.util.Sys;

import org.apache.axiom.attachments.utils.IOUtils;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import com.commons.util.Excel;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelSftp;
import com.plugins.mina.longshine.Message;
import com.plugins.mina.longshine.RequestUtil;
import com.plugins.waterfee.util.CryptoUtils;
import com.plugins.waterfee.util.FtpUtils;
import com.shenjun.collection.SuperArrayList;
import com.shenjun.collection.SuperList;
import com.shenjun.db.type.SqlType;
import com.shenjun.db.type.SqlValue;
import com.shenjun.enums.ExtjsTypeEnum;
import com.shenjun.enums.SqlServerType;
import com.shenjun.manager.CommonManager;
import com.shenjun.plugins.jackson.JSON;
import com.shenjun.util.DateConvertUtils;
import com.shenjun.util.DateUtil;
import com.shenjun.util.JSONUtils;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.SqlUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.Lc;
import com.webservice.service.WaterFree;
import com.plugins.mina.longshine.Composrequesutil;
/**
 * 水费处理页面
 * @author jbox_user
 *
 */
public class WaterFreeServiceManager {
	protected static Log log = LogFactory.getLog(WaterFreeServiceManager.class);
	
	private static final String manyConnections=ReadProperties.getProperty("manyConnections");
	private static final String execaddress=ReadProperties.getProperty("execaddress");
	private static final String fatherpath=ReadProperties.getProperty("fatherpath");
	
	private static final String bingjiangdz=ReadProperties.getProperty("bingjiangdz");
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
		WaterFreeServiceManager w=new WaterFreeServiceManager();
		
       
       String one1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201203\",\"queryValue\":\"54114172\",\"busiType\":\"11\",\"bgnYm\":\"201203\",\"acctOrgNo\":\"00\"}}";
       String one2="{\"head\":{\"version\":\"1.0.1\",\"source\":\"WTZHONGSHAN\",\"desIfno\":\"LONGSHINE\",\"servCode\":\"200001\",\"msgId\":\"dc5d3545-7307-439b-a482-785cbe5a0e7e\",\"msgTime\":\"20140709121758\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"9999\",\"rtnMsg\":\"\",\"consNo\":\"54114172\",\"consName\":\"羊\",\"addr\":\"\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\"00\",\"capitalNo\":\"\",\"consType\":\"\",\"prepayAmt\":\"0.00\",\"totalOweAmt\":\"63.70\",\"totalRcvblAmt\":\"63.70\",\"totalRcvedAmt\":\"63.70\",\"totalPenalty\":\"0\",\"recordCount\":\"1\",\"rcvblDet\":[{\"rcvblAmtId\":\"40281698\",\"consNo\":\"54114172\",\"consName\":\"羊\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\"00\",\"rcvblYm\":\"201404\",\"tPq\":\"26\",\"rcvblAmt\":\"63.70\",\"rcvedAmt\":\"0\",\"rcvblPenalty\":\"0\",\"oweAmt\":\"63.70\",\"extend\":\"\"}]}}";
       String one3="{\"head\":{\"version\":\"1.0.1\",\"source\":\"WTZHONGSHAN\",\"desIfno\":\"LONGSHINE\",\"servCode\":\"200001\",\"msgId\":\"66186717-408a-4587-b44e-8106c406884e\",\"msgTime\":\"20140709121632\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"9999\",\"rtnMsg\":\"\",\"consNo\":\"52508933\",\"consName\":\"陈\",\"addr\":\"\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\"00\",\"capitalNo\":\"\",\"consType\":\"\",\"prepayAmt\":\"0.00\",\"totalOweAmt\":\"0.00\",\"totalRcvblAmt\":\"0.00\",\"totalRcvedAmt\":\"0.00\",\"totalPenalty\":\"0.00\",\"recordCount\":\"0\",\"rcvblDet\":[]}}";
       //w.testsentssage();
       
       String two1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200002\",\"msgId\":\"payshengyanhui0001\",\"msgTime\":\"20140708093609\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"rcvAmt\":\"164.15\",\"bankSerial\":\"2345678900\",\"rcvDet\":[{\"rcvblYm\":\"201404\",\"rcvblAmtId\":\"40271522\",\"consNo\":\"54118033\"}],\"chargeCnt\":\"1\",\"payMode\":\"\",\"capitalNo\":\"\",\"bankDate\":\"20140708\",\"acctOrgNo\":\"00\"}}";
       String two2="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200002\",\"msgId\":\"payshengyanhui0001\",\"msgTime\":\"20140708093609\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"2001\",\"rtnMsg\":\"\",\"extend\":\"\"}}";

       
       String three1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\"20140711170710\",\"extend\":\"\"},\"body\":{\"fileType\":\"\",\"extend\":\"\",\"fileName\":\"LONGSHINE_HAINING_DSDZ_20140709.txt\",\"filePath\":\"\",\"acctOrgNo\":\"00\"}}";
       String three2="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\"20140711170710\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"0009\",\"rtnMsg\":\"\",\"extend\":\"\"}}";

//       System.out.println(w.createRequestXml(one1));
//       System.out.println(w.createRequestXml(one2));
//       System.out.println(w.createRequestXml(one3));
//       System.out.println(w.createRequestXml(two1));
//       System.out.println(w.createRequestXml(two2));
//       System.out.println(w.createRequestXml(three1));
//       System.out.println(w.createRequestXml(three2));
       
//       try{
//       Map m=JSONUtils.fromJson("{www}");
//       }catch(Exception e){
//    	   System.out.println(e.getMessage());
//       }
//       
	    
	    	
	    	String aa="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200001\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201203\",\"queryValue\":\"15524\",\"busiType\":\"11\",\"bgnYm\":\"201203\",\"acctOrgNo\":\"00\"}}";
	    	
	    	
	    	aa= "{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201108\",\"queryValue\":\"74643\",\"busiType\":\"11\",\"bgnYm\":\"201108\",\"acctOrgNo\":\"zs\"}}";
	    	
	    	//w.startQueryWaterFee2(aa);
	    	//System.out.println(ReadProperties.getProperty("SmsTargetEPR6"));
	    	
	    	//System.out.println(w.createRequestXml(aa));
	    	
	    	String bb="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"rcvAmt\":\"164.15\",\"bankSerial\":\"2345678900\",\"rcvDet\":[{\"rcvblYm\":\"201311\",\"rcvblAmtId\":\"40271522\",\"consNo\":\"91673\"}],\"chargeCnt\":\"1\",\"payMode\":\"\",\"capitalNo\":\"\",\"bankDate\":\"20140708\",\"acctOrgNo\":\"00\"}}";
	    	
	    	
	    	String cc="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"fileType\":\"txt\",\"extend\":\"\",\"fileName\":\"LONGSHINE_HAINING_DSDZ_20140709.txt\",\"filePath\":\"\\a\\\",\"acctOrgNo\":\"00\"}}";
	    	
	 //w.getUserInfo();
	    	//w.writeStr("sssss\r\nsssss\r\n","test.txt");
		
//		Map a=new HashMap();
//		a.put("aa", "1");
//		a.put("bb", "3");
//		System.out.println(JSONUtils.toJson(a));
		
		//readFileByLines("C:/file/LONGSHINE_HAINING_DSDZ_20140709.txt");
       
//		String ss="68.64$7$";
//		System.out.println(ss.split("\\$").length);
	    	
//	    	String str="01$77788$a$b$c$d$4$3$3$$rrr$#02$9988$ss$dd$ddd$33$37$7$344$$888$#03$rrrr9$ff$3$3$3$$8888$#";
//	    	String []arrStr=str.split("#");
//	    	System.out.println(arrStr[2]);
//	    	
//	    	String []arrStr2=arrStr[2].split("\\$");
//	    	System.out.println(arrStr2[6]);
	    	
	    	
	    	//System.out.println(w.getArrayToBody("aa",11,"bb",22));
	    	
	    	//System.out.println("ss=".split("=").length);
	    	
	    	//System.out.println(new WaterFreeServiceManager().startTest("hjh")) ;
	    	//System.out.println(new WaterFreeServiceManager().startQueryWaterFee("hjh")) ;
	    	//System.out.println(new WaterFreeServiceManager().startAllTest("hjh", "getTest")) ;
	    	
	    	//String queryUserinfoJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"queryType\":\"01\",\"queryValue\":\"1000\",\"extend\":\"\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(queryUserinfoJson));
	    	//w.startAllTest(queryUserinfoJson, "queryUserInfo");
	    	
	    	//String bindUserJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"consNo\":\"1000\",\"dealType\":\"1\",\"extend\":\"\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(bindUserJson));
	    	//w.startAllTest(bindUserJson, "bindUser");
	    	
	    	//String queryBillJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"queryType\":\"01\",\"queryValue\":\"1000\",\"bgnYm\":\"201501\",\"endYm\":\"201506\",\"extend\":\"\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(queryBillJson));
	    	//w.startAllTest(queryBillJson, "queryBill");
	    	
	    	//String queryContactJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"queryType\":\"01\",\"queryValue\":\"1000\",\"extend\":\"\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(queryContactJson));
	    	//w.startAllTest(queryContactJson, "queryContact");
	    	
	    	
	    	//String queryBalanceJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"queryType\":\"01\",\"queryValue\":\"1000\",\"extend\":\"\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(queryBalanceJson));
	    	//w.startAllTest(queryBalanceJson, "queryBalance");
	    	
	    	//String queryPayFeeRecordJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"queryType\":\"01\",\"queryValue\":\"1000\",\"bgnDate\":\"20150101\",\"endDate\":\"20150601\",\"extend\":\"\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(queryPayFeeRecordJson));
	    	//w.startAllTest(queryPayFeeRecordJson, "queryPayFeeRecord");
	    	
	    	
	    	//String signContactJson="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"100002\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"acctOrgNo\":\"xttt\",\"agreementNo\":\"9000\",\"consNo\":\"1000\",\"bankCode\":\"334\",\"bankAcctNo\":\"11101\",\"bankAcctName\":\"agricu\",\"dealType\":\"44\",\"phone\":\"13083992642\",\"email\":\"2828@qq.com\",\"certType\":\"s\",\"certNo\":\"77\",\"verifyCode\":\"0000\",\"extend\":\"\"}}";
	    	//System.out.println(w.createRequestXml(signContactJson));
	    	//w.startAllTest(signContactJson, "signContact");
	    	
	    	
	    	
	    	//String ReDk="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\"20140711170710\",\"extend\":\"\"},\"body\":{\"fileType\":\"REDK\",\"extend\":\"\",\"fileName\":\"LONGSHINE_REDKKK_xttt_20150716.txt\",\"filePath\":\"/a/\",\"acctOrgNo\":\"xttt\"}}";
	    	//System.out.println(w.createRequestXml(ReDk));
	    	//w.startAllTest(ReDk, "receiveFile");
	    	
	    	System.out.println("111|22|33|33|$333|22|33|33|$#111|22|33|33|$333|22|33|33|$#".split("\\#")[1]);
	    	
	    	System.out.println(ReadProperties.getProperty("aaa"));
	    	
	    	String str="31309500                 Z05396-0            2017042229.50       2017-0          0.00        0.00        ";
	    	String lsh=str.substring(0, 25);
	    	String hh=str.substring(25, 45);
	    	String jfrq=str.substring(45, 53);
	    	String ssje=str.substring(53, 65);
	    	
	    	String fyrq=str.substring(65, 71);
	    	String fyid=str.substring(71, 81);
	    	String sfbj=str.substring(81, 93);
	    	String znj=str.substring(93, 105);
	    	
	    	System.out.println(lsh.trim()+"---"+lsh.length());
	    	System.out.println(hh.trim()+"---"+hh.length());
	    	System.out.println(jfrq.trim()+"---"+jfrq.length());
	    	System.out.println(ssje.trim()+"---"+ssje.length());
	    	System.out.println(fyrq.trim()+"---"+fyrq.length());
	    	System.out.println(fyid.trim()+"---"+fyid.length());
	    	System.out.println(sfbj.trim()+"---"+sfbj.length());
	    	System.out.println(znj.trim()+"---"+znj.length());
	    	
	}
	
	
	public String createRequestXml(String jsonParam){
		log.info("进参："+jsonParam);
		try{
			String nodeName="root";
			Element node=DocumentHelper.createElement(nodeName);
			
			node.addAttribute("aa", "1");
			node.addAttribute("bb", "2");
			
//			jsonParam="{\"head\":{\"version\":\"v1.0\",\"source\":\"ss11\",\"desIfno\":\"66\",\"servCode\":\"code11\",\"msgId\":\"22222\",\"msgTime\":\"20110203\",\"extend\":\"ex11\"}," +
//	    		"\"body\":{\"rtnCode\":\"rtnCode\",\"rtnMsg\":\"rtnMsg\",\"consNo\":\"rtnMsg\",\"consName\":\"rtnMsg\",\"addr\":\"rtnMsg\",\"orgNo\":\"rtnMsg\",\"orgName\":\"rtnMsg\",\"acctOrgNo\":\"rtnMsg\",\"capitalNo\":\"rtnMsg\",\"consType\":\"rtnMsg\",\"prepayAmt\":\"rtnMsg\",\"totalOweAmt\":\"rtnMsg\",\"totalRcvblAmt\":\"rtnMsg\",\"totalRcvedAmt\":\"rtnMsg\",\"totalPenalty\":\"rtnMsg\",\"recordCount\":\"rtnMsg\"," +
//	    		"\"rcvblDet\":[{\"rcvblAmtId\":\"rtnMsg\",\"consNo\":\"rtnMsg\",\"consName\":\"rtnMsg\",\"orgNo\":\"rtnMsg\",\"orgName\":\"rtnMsg\",\"acctOrgNo\":\"rtnMsg\",\"rcvblYm\":\"rtnMsg\",\"tPq\":\"rtnMsg\",\"rcvblAmt\":\"rtnMsg\",\"rcvedAmt\":\"rtnMsg\",\"rcvblPenalty\":\"rtnMsg\",\"oweAmt\":\"rtnMsg\",\"extend\":\"rtnMsg\"},{\"rcvblAmtId\":\"rtnMsg\",\"consNo\":\"rtnMsg\",\"consName\":\"rtnMsg\",\"orgNo\":\"rtnMsg\",\"orgName\":\"rtnMsg\",\"acctOrgNo\":\"rtnMsg\",\"rcvblYm\":\"rtnMsg\",\"tPq\":\"rtnMsg\",\"rcvblAmt\":\"rtnMsg\",\"rcvedAmt\":\"rtnMsg\",\"rcvblPenalty\":\"rtnMsg\",\"oweAmt\":\"rtnMsg\",\"extend\":\"rtnMsg\"}]}}";
//	     
		
			Map m=JSONUtils.fromJson(jsonParam);
			
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			Element head=getE(jsonhead,"head",null);
			node.add(head);
			
			Element body=getE(jsonbody,"body",new String[]{"rcvblDet","rcvDet"});
			node.add(body);
		       
	        Document document = DocumentHelper.createDocument();
			document.add(node); 
			log.info("出参："+document.asXML());
			return document.asXML();
		}catch(Exception ex){
			log.error(ex.getMessage(), ex.getCause());
			return "<root></root>";
		}
		
	}
	
	public Element getE(Map jsono,String nodeName,String [] arrayname){
		Element head=DocumentHelper.createElement(nodeName);
		Iterator iter = jsono.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			
			if(arrayname!=null&&arrayname.length>0){
				int z=0;
				for(z=0;z<arrayname.length;z++){
					if((entry.getKey()+"").equals(arrayname[z])){
						
						boolean isarray="true".equals(ReadProperties.getProperty("array"));
						
						if("rcvDet".equals(entry.getKey()+"")&&!isarray){
							String v=(String)entry.getValue();
							String []varr=(v+"").split("\\$");
							//"rcvDet":"3663|333|333||$3663|333|333||$"
							if(varr.length>0){
								for(int i=0,len=varr.length;i<len;i++){
									String v2=varr[i];
									String []varr2=(v2+"").split("\\|");
									
									Map map=new HashMap();
									map.put("consNo", varr2[0]);
									map.put("rcvblAmtId", varr2[1]);
									map.put("rcvblYm", varr2[2]);
									map.put("extendDet", "");
									
									Element el=getE(map,arrayname[z],null);
									head.add(el);
								}
							}
							
							break;
						}else{
							//"rcvDet":[{"consNo":"8301178","rcvblAmtId":"10154904","rcvblYm":"201310"}]
							List jsonaa=(List)entry.getValue();
							
							for(int i=0;i<jsonaa.size();i++){
								Map oo=(Map)jsonaa.get(i);
								Element el=getE(oo,arrayname[z],null);
								head.add(el);
							}
							break;
						}
						
						
					}
				}
				
				if(z==arrayname.length){
					head.addAttribute(entry.getKey()+"", entry.getValue()+"");
				}
			}else{
				head.addAttribute(entry.getKey()+"", entry.getValue()+"");
			}
			//System.out.println(entry.getKey() + "=>" + entry.getValue());
		}
		return head;
	}
	
	/**
	 * 欠费查询
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryWaterFee(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			
			String version=jsonhead.get("version")+"";
			String source=jsonhead.get("source")+"";
			String desIfno=jsonhead.get("desIfno")+"";
			String servCode=jsonhead.get("servCode")+"";
			String msgId=jsonhead.get("msgId")+"";
			String msgTime=jsonhead.get("msgTime")+"";
			String extend=jsonhead.get("extend")+"";
			
			source="杭州国德科技";
			desIfno="支付宝";
			msgTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			//String misarea=getMisArea(acctOrgNo);
			//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall("SpBCQueryArrearageForWeb_HZ", new String[]{xml});
			
			SuperList<Object[]>sl2=cm.createCall("SpBCQueryArrearageForWeb_MX", new String[]{xml});
			
			Map map=null;
			if(sl!=null&&sl.size()>0){
				Object[]oo=sl.get(0);
				
				String consNo=oo[2]+"";
				
				SuperList<Object[]> l= new SuperArrayList<Object[]>();
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(oo, new String[]{"rtnCode","rtnMsg","consNo","consName","addr","orgNo","orgName","acctOrgNo","capitalNo","consType","prepayAmt","totalOweAmt","totalRcvblAmt","totalRcvedAmt","totalPenalty","recordCount"});
				
				Map mymap=jsonT.getJsonObject();
				if(sl2!=null&&sl2.size()>0){
					for(int i=0;i<sl2.size();i++){
						Object[]oo2=sl2.get(i);
						
						if(consNo.equals(oo2[1]+""))
						    l.add(oo2);
						
					}
				}
				
				JSON<List<Map<String, Object>>> jsonT2=JSONUtils.listToJson(l, new String[]{"rcvblAmtId","consNo","consName","orgNo","orgName","acctOrgNo","rcvblYm","tPq","rcvblAmt","rcvedAmt","rcvblPenalty","oweAmt","extend"});
				mymap.put("rcvblDet", jsonT2.getJsonObject());
				
				return getSuccessData(JSONUtils.toJson(jsonhead),jsonT.toJSONString());
				
			}else{
				return getFailData(JSONUtils.toJson(jsonhead),"1005","暂时无法缴费或超过限定缴费金额，请咨询事业单位",acctOrgNo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	/**
	 * 欠费查询
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryWaterFeeWx(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			
			String version=jsonhead.get("version")+"";
			String source=jsonhead.get("source")+"";
			String desIfno=jsonhead.get("desIfno")+"";
			String servCode=jsonhead.get("servCode")+"";
			String msgId=jsonhead.get("msgId")+"";
			String msgTime=jsonhead.get("msgTime")+"";
			String extend=jsonhead.get("extend")+"";
			
			source="杭州国德科技";
			desIfno="支付宝";
			msgTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			//String misarea=getMisArea(acctOrgNo);
			//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]> hz=cm.createCall("SpBCQueryArrearageForWeb_HZ", new String[]{xml});
			
			SuperList<Object[]> mx=cm.createCall("SpBCQueryArrearageForWeb_MX", new String[]{xml});
			
			Map map=null;
			if(hz!=null&&hz.size()>0){
				Object[] hz_data=hz.get(0);
				
				String consNo=hz_data[2]+"";
				
				SuperList<Object[]> mx_data= new SuperArrayList<Object[]>();
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(hz_data, new String[]{"rtnCode","rtnMsg","consNo","consName","addr","orgNo","orgName","acctOrgNo","capitalNo","consType","prepayAmt","totalOweAmt","totalRcvblAmt","totalRcvedAmt","totalPenalty","recordCount"});
				
				Map<String, Object> mymap=jsonT.getJsonObject();
				if(mx!=null&&mx.size()>0){
					for(int i=0;i<mx.size();i++){
						Object[] data=mx.get(i);
						
						if(consNo.equals(data[1]+""))
							mx_data.add(data);
						
					}
				}
				
				JSON<List<Map<String, Object>>> jsonT2=JSONUtils.listToJson(mx_data, new String[]{"rcvblAmtId","consNo","consName","orgNo","orgName","acctOrgNo","rcvblYm","tPq","rcvblAmt","rcvedAmt","rcvblPenalty","oweAmt","extend"});
				mymap.put("rcvblDet", jsonT2.getJsonObject());
				
				return getSuccessData(JSONUtils.toJson(jsonhead),jsonT.toJSONString());
				
			}else{
				return getFailData(JSONUtils.toJson(jsonhead),"1005","暂时无法缴费或超过限定缴费金额，请咨询事业单位",acctOrgNo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	/**
	 * wx欠费查询
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryWaterFeeWX_MX(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			
			String version=jsonhead.get("version")+"";
			String source=jsonhead.get("source")+"";
			String desIfno=jsonhead.get("desIfno")+"";
			String servCode=jsonhead.get("servCode")+"";
			String msgId=jsonhead.get("msgId")+"";
			String msgTime=jsonhead.get("msgTime")+"";
			String extend=jsonhead.get("extend")+"";
			
			source="杭州国德科技";
			desIfno="支付宝";
			msgTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			//String misarea=getMisArea(acctOrgNo);
			//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]> hz=cm.createCall("SpBCQueryArrearageForWeb_HZ", new String[]{xml});
			
			SuperList<Object[]> mx=cm.createCall("SpBCQueryArrearageForWeb_MX", new String[]{xml});
			
			Map map=null;
			if(hz!=null&&hz.size()>0){
				Object[] hz_data=hz.get(0);
				
				String consNo=hz_data[2]+"";
				
				SuperList<Object[]> mx_data= new SuperArrayList<Object[]>();
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(hz_data, new String[]{"rtnCode","rtnMsg","consNo","consName","addr","orgNo","orgName","metBodNum","caliber","acctOrgNo","capitalNo","consType","prepayAmt","totalOweAmt","totalRcvblAmt","totalRcvedAmt","totalPenalty","recordCount"});
				
				Map<String, Object> mymap=jsonT.getJsonObject();
				if(mx!=null&&mx.size()>0){
					for(int i=0;i<mx.size();i++){
						Object[] data=mx.get(i);
						
						if(consNo.equals(data[0]+""))
							mx_data.add(data);
						
					}
				}
				
				
				JSON<List<Map<String, Object>>> jsonT2=JSONUtils.listToJson(mx_data, new String[]{"consNo","rcvblAmtId","rcvblYm","perRead","tPq","basAmt","additiAmt","sewaAmt","cleAmt","rcvblPenalty","oweAmt","extend"});
				mymap.put("rcvblDet", jsonT2.getJsonObject());
				
				return getSuccessData(JSONUtils.toJson(jsonhead),jsonT.toJSONString());
				
			}else{
				return getFailData(JSONUtils.toJson(jsonhead),"1005","暂时无法缴费或超过限定缴费金额，请咨询事业单位",acctOrgNo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	/**
	 * 缴费
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String payWaterFee(String json){
		
		try{
			log.info("----------拿到json的数据:"+json);
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String version=jsonhead.get("version")+"";
			String source=jsonhead.get("source")+"";
			String desIfno=jsonhead.get("desIfno")+"";
			String servCode=jsonhead.get("servCode")+"";
			String msgId=jsonhead.get("msgId")+"";
			String msgTime=jsonhead.get("msgTime")+"";
			String extend=jsonhead.get("extend")+"";
			
			source="杭州国德科技";
			desIfno="支付宝";
			msgTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			//String misarea="guodemischargewx";
			//String misarea=getMisArea(acctOrgNo);
			//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			//if()
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200002")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall("SpBCCompWriteoffForWeb", new String[]{xml});
			Map map=null;
			if(sl!=null&&sl.size()>0){
				Object[]oo=sl.get(0);
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(oo, new String[]{"rtnCode","rtnMsg","extend"});
				return getSuccessData(JSONUtils.toJson(m.get("head")),jsonT.toJSONString());
			}else{
				return getSuccessData(JSONUtils.toJson(m.get("head")),getBody("2005","业务状态异常",""));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
		
	}
	
	
	/**
	 * 缴费结果查询
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryPayWaterFee(String json){
		
		try{
			log.info("----------拿到json的数据:"+json);
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String version=jsonhead.get("version")+"";
			String source=jsonhead.get("source")+"";
			String desIfno=jsonhead.get("desIfno")+"";
			String servCode=jsonhead.get("servCode")+"";
			String msgId=jsonhead.get("msgId")+"";
			String msgTime=jsonhead.get("msgTime")+"";
			String extend=jsonhead.get("extend")+"";
			
			source="杭州国德科技";
			desIfno="支付宝";
			msgTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200002")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCCompZdcxuQr", new String[]{xml});
			Map map=null;
			if(sl!=null&&sl.size()>0){
				Object[]oo=sl.get(0);
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(oo, new String[]{"rtnCode","rtnMsg","instSerial","extend"});
				return getSuccessData(JSONUtils.toJson(m.get("head")),jsonT.toJSONString());
			}else{
				return getSuccessData(JSONUtils.toJson(m.get("head")),getBody("2005","业务状态异常",""));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
		
	}
	
	
	/**
	 * 代扣缴费对账,代扣扣款反馈文本
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String receiveFile(String json){
		
		try{
			Map m=JSONUtils.fromJson(json);//考虑解析错误的提示  返回错误码
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String version=jsonhead.get("version")+"";
			String source=jsonhead.get("source")+"";
			String desIfno=jsonhead.get("desIfno")+"";
			String servCode=jsonhead.get("servCode")+"";
			String msgId=jsonhead.get("msgId")+"";
			String msgTime=jsonhead.get("msgTime")+"";
			String extend=jsonhead.get("extend")+"";
			
			source="杭州国德科技";
			desIfno="支付宝";
			msgTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String fileName=jsonbody.get("fileName")+"";
			String fileType=jsonbody.get("fileType")+"";
			String filePath=jsonbody.get("filePath")+"";
			String extend2=jsonbody.get("extend")+"";
			
			String misarea=getMisArea(acctOrgNo);
			
			
			//CommonManager cm=Lc.getConn(acctOrgNo);
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"100002")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			log.debug("---------->>"+fileName+"   "+filePath+"   "+fileType);
			//filePath="/home/zhongshan_test/";
			String url=ReadProperties.getProperty("filelangxin");
			String dst=url+fileName;
			
			 try{
				if(new File(url).mkdirs())
					log.info("成功创建文件夹:"+url);
			 }catch(Exception e) {
				e.printStackTrace();
			 }
			 
			try {
				
				//如果fatherpath不为空,则下载到fatherpath
				if(StringUtil.isNB(fatherpath)){
					filePath=fatherpath;
					FtpUtils.fatherDerectoryDown(filePath, fileName, dst);
				}else{
					FtpUtils.langxinDown(filePath, fileName, dst);
				}
				
			}catch(Exception e){
				log.error("文件不存在:"+e.getMessage(),e.getCause());
				return getSuccessData(JSONUtils.toJson(m.get("head")),getBody("4003","文件不存在",""));
			}
			
			String body="{}";
			
			
			if("DSDZ".equals(fileType)){
				//代收对账
				
				if("true".equals(bingjiangdz)){
					body = readFileByLines_bj(dst,fileName,misarea,acctOrgNo);
				}else if("native".equals(execaddress)){
					body = readFileByLines(dst,fileName,misarea,acctOrgNo);
				}
				
			}else if("REDK".equals(fileType)){
				//代扣扣款反馈文本
				if("native".equals(execaddress)){
					body = readFileByLines(dst,fileName,misarea,acctOrgNo);
				}
			}
			
			return getSuccessData(JSONUtils.toJson(m.get("head")),body);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	/**
	 * 
	 * 用户信息查询
	 * 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryUserInfo(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCQueryUserInfo", new String[]{xml});
			
			SuperList<Object[]>sl2=cm.createCall(misarea+"SpBCQueryUserParams", new String[]{xml});
			
			JSON<Map<String, Object>> jsonObject= JSONUtils.objectjsToJson(new Object[]{"9999","成功查询用户信息","0",""}, new String[]{"rtnCode","rtnMsg","consTotalCnt","extend"});
			
			Map mapObject=jsonObject.getJsonObject();
			
			if(sl!=null&&sl.size()>0){
				mapObject.put("consTotalCnt", sl.size()+"");
				
				JSON<List<Map<String, Object>>> jsonListObject=JSONUtils.listToJson(sl, new String[]{"consNo",
						"consName","addr","orgNo",
						"orgName","provinceCode","provinceName","cityCode",
						"cityName","countyCode","acctOrgNo","acctOrgName",
						"conTactName","mobile","certNo","consType"});
				
				List<Map<String, Object>> listMapObject=jsonListObject.getJsonObject();
				
				
				for(int i=0,len=listMapObject.size();i<len;i++){
					Map<String, Object> mapTemp=listMapObject.get(i);
					
					String consNo=mapTemp.get("consNo")+"";
					List<Object[]>listTemp=new ArrayList<Object[]>();
					
					if(sl2!=null&&sl2.size()>0){
						for(int j=0,len2=sl2.size();j<len2;j++){
							Object[]objectArrTemp=sl2.get(j);
							String consNoTemp=objectArrTemp[0]+"";
							
							if(consNoTemp.equals(consNo)){
								listTemp.add(objectArrTemp);
							}
						}
					}
					
					JSON<List<Map<String, Object>>> jsonListObjectTemp=JSONUtils.listToJson(listTemp, new String[]{"consNo",
							"params1","paramsN"});
					
					mapTemp.put("params", jsonListObjectTemp.getJsonObject());
				}
				
				mapObject.put("consInfo", jsonListObject.getJsonObject());
				
			}else{
				JSON<List<Map<String, Object>>> jsonListObject=JSONUtils.listToJson(sl, new String[]{"consNo",
						"consName","addr","orgNo",
						"orgName","provinceCode","provinceName","cityCode",
						"cityName","countyCode","acctOrgNo","acctOrgName",
						"conTactName","mobile","certNo","consType"});
				
				mapObject.put("consInfo", jsonListObject.getJsonObject());
				
				mapObject.put("rtnMsg", "没有数据或操作异常");
			}
			
			return getSuccessData(JSONUtils.toJson(jsonhead),jsonObject.toJSONString());
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	
	/**
	 * 绑定用户信息
	 * 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String bindUser(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200002")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCBindUser", new String[]{xml});
			
			Map map=null;
			if(sl!=null&&sl.size()>0){
				Object[]oo=sl.get(0);
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(oo, new String[]{"rtnCode","rtnMsg","extend"});
				return getSuccessData(JSONUtils.toJson(m.get("head")),jsonT.toJSONString());
			}else{
				return getSuccessData(JSONUtils.toJson(m.get("head")),getBody("2005","业务状态异常",""));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
		
	}
	
	/**
	 * 
	 * 用户账单查询
	 * 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryBill(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCQueryBill", new String[]{xml});
			
			SuperList<Object[]>sl2=cm.createCall(misarea+"SpBCQueryBill_fee", new String[]{xml});
			SuperList<Object[]>sl3=cm.createCall(misarea+"SpBCQueryBill_copy", new String[]{xml});
			SuperList<Object[]>sl4=cm.createCall(misarea+"SpBCQueryBill_floor", new String[]{xml});
			
			JSON<Map<String, Object>> jsonObject= JSONUtils.objectjsToJson(new Object[]{"9999","查询成功",""}, 
					new String[]{"rtnCode","rtnMsg","extend"});
			SuperList<Object[]> l= new SuperArrayList<Object[]>();
			Map map=jsonObject.getJsonObject();
			
			if(sl!=null&&sl.size()>0){
				
				for(int i=0,len=sl.size();i<len;i++){
					Object[]objectArrTemp=sl.get(i);
					String primaryKey=objectArrTemp[0]+"";
					
					String content=objectArrTemp[0]+"|"+objectArrTemp[1]+"|"+objectArrTemp[2]+"|"
								+objectArrTemp[3]+"|"+objectArrTemp[4]+"|"+objectArrTemp[5]+"|"
								+objectArrTemp[6]+"|"+objectArrTemp[7]+"|"+objectArrTemp[8]+"|"
								+objectArrTemp[9]+"|"+objectArrTemp[10]+"|"+objectArrTemp[11]+"|"
								+objectArrTemp[12]+"|"+objectArrTemp[13]+"|"+objectArrTemp[14]+"|"
								+objectArrTemp[15]+"|"+objectArrTemp[16]+"|"+objectArrTemp[17]+"|"+objectArrTemp[18]+"|";
					
					for(int j=0,len2=sl2.size();j<len2;j++){
						Object[]objectArr2Temp=sl2.get(j);
						if(primaryKey.equals(objectArr2Temp[0]+"")){
							content+="01$"+objectArr2Temp[1]+"$"+objectArr2Temp[2]+"$"+objectArr2Temp[3]
									+"$"+objectArr2Temp[4]+"$"+objectArr2Temp[5]+"$"+objectArr2Temp[6]
									+"$"+objectArr2Temp[7]+"$"+objectArr2Temp[8]+"$"+objectArr2Temp[9]+"$"+objectArr2Temp[10]+"$#";
						}
					}
					
					for(int j=0,len2=sl3.size();j<len2;j++){
						Object[]objectArr2Temp=sl3.get(j);
						if(primaryKey.equals(objectArr2Temp[0]+"")){
							content+="02$"+objectArr2Temp[1]+"$"+objectArr2Temp[2]+"$"+objectArr2Temp[3]
									+"$"+objectArr2Temp[4]+"$"+objectArr2Temp[5]+"$"+objectArr2Temp[6]
									+"$"+objectArr2Temp[7]+"$"+objectArr2Temp[8]+"$"+objectArr2Temp[9]+"$"+objectArr2Temp[10]+"$#";
						}
					}
					
					for(int j=0,len2=sl4.size();j<len2;j++){
						Object[]objectArr2Temp=sl4.get(j);
						if(primaryKey.equals(objectArr2Temp[0]+"")){
							content+="03$"+objectArr2Temp[1]+"$"+objectArr2Temp[2]+"$"+objectArr2Temp[3]
									+"$"+objectArr2Temp[4]+"$"+objectArr2Temp[5]+"$"+objectArr2Temp[6]
									+"$"+objectArr2Temp[7]+"$#";
						}
					}
					
					l.add(new Object[]{content});
				}
				JSON<List<Map<String, Object>>> jsonListObject=JSONUtils.listToJson(l, new String[]{"content1"});
				map.put("contents", jsonListObject.getJsonObject());
			}
			return getSuccessData(JSONUtils.toJson(jsonhead),jsonObject.toJSONString());
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	
	/**
	 * 
	 * 用户余额查询
	 * 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryBalance(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCQueryBalance", new String[]{xml});
			
			JSON<Map<String, Object>> jsonObject= JSONUtils.objectjsToJson(new Object[]{"2005","查询异常","","","",""}, 
					new String[]{"rtnCode","rtnMsg","acctOrgNo","consNo","consName","extend"});
			
			
			Map map=jsonObject.getJsonObject();
			
			SuperList<Object[]> l= new SuperArrayList<Object[]>();
			
			if(sl!=null&&sl.size()>0){
				for(int i=0;i<sl.size();i++){
					Object[]objectArrTemp=sl.get(i);
					if(i==0){
						map.put("rtnCode", objectArrTemp[0]+"");
						map.put("rtnMsg", objectArrTemp[1]+"");
						map.put("acctOrgNo", objectArrTemp[2]+"");
						map.put("consNo", objectArrTemp[3]+"");
						map.put("consName", objectArrTemp[4]+"");
						map.put("extend", objectArrTemp[5]+"");
					}
					
					l.add(new Object[]{objectArrTemp[6],objectArrTemp[7],objectArrTemp[8]});
				}
			}
			
			JSON<List<Map<String, Object>>> jsonListObject=JSONUtils.listToJson(l, 
					new String[]{"balType","balValue","getDate"});
			
			map.put("BAL_INFO", jsonListObject.getJsonObject());
			
			return getSuccessData(JSONUtils.toJson(jsonhead),jsonObject.toJSONString());
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	/**
	 * 
	 * 用户缴费记录查询
	 * 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryPayFeeRecord(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			
			
			//版本号  判断版本号是否正确
			if(!isRightVersion(jsonhead)){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0002","协议的版本号错误",acctOrgNo);
			}
			
			//判断是否是查询欠费
			if(!isRightServCode(jsonhead,"200001")){
				//return getFailData(version ,source ,desIfno ,servCode,msgId ,msgTime,extend ,"0008","业务未开通",acctOrgNo);
			}
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			//xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><root aa=\"1\" bb=\"2\"><head version=\"1.0.1\" source=\"LONGSHINE\" desIfno=\"WTHAINING\" servCode=\"200001\" msgId=\"helloshengyanhui0001\" msgTime=\"20140708162509\" extend=\"\"/><body extend=\"\" queryType=\"0\" endYm=\"201203\" queryValue=\"15524\" busiType=\"11\" bgnYm=\"201203\" acctOrgNo=\"00\"/></root>";
			//xml=xml.substring(importstr.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCQueryPayRecord", new String[]{xml});
			JSON<Map<String, Object>> jsonObject= JSONUtils.objectjsToJson(new Object[]{"9999","查询成功",""}, new String[]{"rtnCode","rtnMsg","extend"});
			
			SuperList<Object[]> l= new SuperArrayList<Object[]>();
			Map map=jsonObject.getJsonObject();
			
			if(sl!=null&&sl.size()>0){
				for(int i=0;i<sl.size();i++){
					String payInfo="";
					Object[]ObjectArrTemp=sl.get(i);
					payInfo+=ObjectArrTemp[0]+"|"+ObjectArrTemp[1]+"|"+ObjectArrTemp[2]+"|"
					+ObjectArrTemp[3]+"|"+ObjectArrTemp[4]+"|"+ObjectArrTemp[5]+"|"
					+ObjectArrTemp[6]+"|"+ObjectArrTemp[7]+"|"+ObjectArrTemp[8]+"|"
					+ObjectArrTemp[9]+"|"+ObjectArrTemp[10]+"|"+ObjectArrTemp[11]+"|"
					+ObjectArrTemp[12]+"|"+ObjectArrTemp[13]+"|"+ObjectArrTemp[14]+"|"+ObjectArrTemp[15]+"|";
					
					l.add(new Object[]{payInfo});
				}
			}
			
			JSON<List<Map<String, Object>>> jsonListObject=JSONUtils.listToJson(l, new String[]{"payInfo"});
			map.put("payInfos", jsonListObject.getJsonObject());
			
			return getSuccessData(JSONUtils.toJson(jsonhead),jsonObject.toJSONString());
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	/**
	 * 用户缴费协议查询
	 * 
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String queryContact(String json){
		
		try{
	        Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCQueryContact", new String[]{xml});
			
			JSON<Map<String, Object>> jsonObject= JSONUtils.objectjsToJson(new Object[]{"9999","查询成功",""}, 
					new String[]{"rtnCode","rtnMsg","extend"});
			Map map=jsonObject.getJsonObject();
			
			JSON<List<Map<String, Object>>> jsonListObject=JSONUtils.listToJson(sl, new String[]{"payMode",
					"payModeName","consNo","consName","agreementNo","bankCode",
					"bankName","bankAcctNo","bankAcctName","certType",
					"certNo","mobile","signDate","Extend"});
			
			map.put("payModeInfo", jsonListObject.getJsonObject());
			return getSuccessData(JSONUtils.toJson(jsonhead),jsonObject.toJSONString());
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	/**
	 * 代扣协议维护(代扣签约，解约等)
	 */
	public String signContact(String json){
		try{
			Map m=JSONUtils.fromJson(json);//考虑解析错误的提示  返回错误码
			Map jsonhead=(Map)m.get("head");
			Map jsonbody=(Map)m.get("body");
			
			String acctOrgNo=jsonbody.get("acctOrgNo")+"";
			String misarea=getMisArea(acctOrgNo);
			CommonManager cm=Lc.getConn();
			
			String xml=createRequestXml(json);
			xml=xml.substring(xml.indexOf(">")+1);
		
			SuperList<Object[]>sl=cm.createCall(misarea+"SpBCSignContact", new String[]{xml});
			
			Map map=null;
			if(sl!=null&&sl.size()>0){
				Object[]oo=sl.get(0);
				JSON<Map<String, Object>> jsonT= JSONUtils.objectjsToJson(oo, new String[]{"rtnCode","rtnMsg","agreementNo","extend"});
				return getSuccessData(JSONUtils.toJson(m.get("head")),jsonT.toJSONString());
			}else{
				return getSuccessData(JSONUtils.toJson(m.get("head")),getArrayToBody("rtnCode","2005","rtnMsg","业务状态异常","agreementNo","-1","extend",""));
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage(), ex.getCause());
			Map m=JSONUtils.fromJson(json);
			Map jsonhead=(Map)m.get("head");
			return getSuccessData(JSONUtils.toJson(jsonhead) ,getBody("0009","系统异常",""));
		}
	}
	
	/**
	 * 缴费记录通知文本生成及上传，通知支付宝
	 */
	
	public String uploadJFFile(){
		String ss=ReadProperties.getProperty("allarea");
		String[]areaarr=ss.split(",");
		CommonManager cm=Lc.getConn();
		log.debug("------------>>"+ss);
		
		String url=ReadProperties.getProperty("filezfb");
		String alluserinfofileaddress=ReadProperties.getProperty("alluserinfofileaddress");
		
		for(int i=0;i<areaarr.length;i++){
			SuperList<Object[]>sl=cm.createCall("SpBCQueryLastDayPayRecord", new String[]{areaarr[i]});
			if(sl!=null&&sl.size()>0){
				String filename="HZGD_JF_"+areaarr[i]+"_"+DateConvertUtils.DateToString(new Date(),"yyyyMMdd")+".txt";
				String filepath=url+filename;
				
				StringBuilder sb=new StringBuilder();
				
				String tempStr=""+sl.size()+"|";
				double numTemp=0;
				for(int j=0,len=sl.size();j<len;j++){
					String payInfo="";
					Object[]ObjectArrTemp=sl.get(j);
					payInfo+=ObjectArrTemp[0]+"|"+ObjectArrTemp[1]+"|"+ObjectArrTemp[2]+"|"
					+ObjectArrTemp[3]+"|"+ObjectArrTemp[4]+"|"+ObjectArrTemp[5]+"|"
					+ObjectArrTemp[6]+"|"+ObjectArrTemp[7]+"|"+ObjectArrTemp[8]+"|"
					+ObjectArrTemp[9]+"|"+ObjectArrTemp[10]+"|"+ObjectArrTemp[11]+"|"
					+ObjectArrTemp[12]+"|"+ObjectArrTemp[13]+"|"+ObjectArrTemp[14]+"|"+ObjectArrTemp[15]+"|\r\n";
					
					
					numTemp=numTemp+Double.parseDouble(""+ObjectArrTemp[7]);
					sb.append(payInfo);
				}
				tempStr+=numTemp+"|\r\n";
				writeStr(tempStr+sb.toString(),filename,url);
				
				try {
				    FtpUtils.zfbUploadQfUserinfo(filepath, alluserinfofileaddress+filename);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String noticepath="{\"head\":{\"version\":\"1.0.1\",\"source\":\"HZGD\"," +
						"\"desIfno\":\"LONGSHINE\",\"servCode\":\"200111\"," +
						"\"msgId\":\"51D5F637C5141C27260003A4EB486495\"," +
						"\"msgTime\":\""+time+"\",\"extend\":\"\"}," +
						"\"body\":{\"acctOrgNo\":\""+areaarr[i]+"\"," +
						"\"filePath\":\""+alluserinfofileaddress+"\"," +
						"\"fileType\":\"JF\",\"fileName\":\""+filename+"\",\"extend\":\"\" }}";
				Message res=RequestUtil.sendMessage(new Message("200111", noticepath));
			System.out.println("code:"+res.getCode()+",data:"+res.getData());
			}
		}
		
		
		
		
		
		return "";
	}
	
	/**
	 * 催费通知文本生成及上传，通知支付宝
	 */
	
	public String uploadCFTZFile(){
		String ss=ReadProperties.getProperty("allarea");
		String[]areaarr=ss.split(",");
		CommonManager cm=Lc.getConn();
		log.debug("------------>>"+ss);
		
		String url=ReadProperties.getProperty("filezfb");
		String alluserinfofileaddress=ReadProperties.getProperty("alluserinfofileaddress");
		
		for(int i=0;i<areaarr.length;i++){
			SuperList<Object[]>sl=cm.createCall("SpBCQueryCFRecord", new String[]{areaarr[i]});
			if(sl!=null&&sl.size()>0){
				String filename="HZGD_CFTZ_"+areaarr[i]+"_"+DateConvertUtils.DateToString(new Date(),"yyyyMMdd")+".txt";
				String filepath=url+filename;
				
				StringBuilder sb=new StringBuilder();
				//sb.append("BEGIN|"+allnum+"\r\n");
				
				sb.append(sl.size()+"|\r\n");
				for(int j=0,len=sl.size();j<len;j++){
				   Object[]oo=sl.get(j);
				   sb.append(oo[0]+"|"+oo[1]+"|"+oo[2]+"|"+oo[3]+"|"+oo[4]+"|"+oo[5]+"|"+oo[6]+"|"+oo[7]+"|\r\n");
				}
				//sb.append("END\r\n");
				writeStr(sb.toString(),filename,url);
				
				
				try {
				    FtpUtils.zfbUploadQfUserinfo(filepath, alluserinfofileaddress+filename);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//通知sslsocket或者webservice去取，按需推送
				String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String noticepath="{\"head\":{\"version\":\"1.0.1\",\"source\":\"HZGD\"," +
						"\"desIfno\":\"LONGSHINE\",\"servCode\":\"200111\"," +
						"\"msgId\":\"51D5F637C5141C27260003A4EB486495\"," +
						"\"msgTime\":\""+time+"\",\"extend\":\"\"}," +
						"\"body\":{\"acctOrgNo\":\""+areaarr[i]+"\"," +
						"\"filePath\":\""+alluserinfofileaddress+"\"," +
						"\"fileType\":\"CFTZ\",\"fileName\":\""+filename+"\",\"extend\":\"\" }}";
				Message res=RequestUtil.sendMessage(new Message("200111", noticepath));
				System.out.println("code:"+res.getCode()+",data:"+res.getData());
			}
		}
		
		return "";
	}
	
	
	/**
	 * 月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝
	 */
	
	public String uploadDZZDFile(){
		String ss=ReadProperties.getProperty("allarea");
		String[]areaarr=ss.split(",");
		CommonManager cm=Lc.getConn();
		log.debug("------------>>"+ss);
		
		String url=ReadProperties.getProperty("filezfb");
		String alluserinfofileaddress=ReadProperties.getProperty("alluserinfofileaddress");
		
		
		for(int i=0;i<areaarr.length;i++){
			SuperList<Object[]>sl=cm.createCall("SpBCQueryBillLastDay", new String[]{areaarr[i]});
			SuperList<Object[]>sl2=cm.createCall("SpBCQueryBill_feeLastDay", new String[]{areaarr[i]});
			SuperList<Object[]>sl3=cm.createCall("SpBCQueryBill_copyLastDay", new String[]{areaarr[i]});
			SuperList<Object[]>sl4=cm.createCall("SpBCQueryBill_floorLastDay", new String[]{areaarr[i]});
			String filename=ReadProperties.getProperty("filename")+DateConvertUtils.DateToString(new Date(),"yyyyMMdd")+".txt";
			String filepath=url+filename;
			StringBuilder sb=new StringBuilder();
			sb.append(sl.size()+"|\r\n");
			
			if(sl!=null&&sl.size()>0){
				for(int ii=0,len=sl.size();ii<len;ii++){
					Object[]objectArrTemp=sl.get(ii);
					String primaryKey=objectArrTemp[0]+"";
					String content=objectArrTemp[0]+"|"+objectArrTemp[1]+"|"+objectArrTemp[2]+"|"
								+objectArrTemp[3]+"|"+objectArrTemp[4]+"|"+objectArrTemp[5]+"|"
								+objectArrTemp[6]+"|"+objectArrTemp[7]+"|"+objectArrTemp[8]+"|"
								+objectArrTemp[9]+"|"+objectArrTemp[10]+"|"+objectArrTemp[11]+"|"
								+objectArrTemp[12]+"|"+objectArrTemp[13]+"|"+objectArrTemp[14]+"|"
								+objectArrTemp[15]+"|"+objectArrTemp[16]+"|"+objectArrTemp[17]+"|"+objectArrTemp[18]+"|";
					
					for(int j=0,len2=sl2.size();j<len2;j++){
						Object[]objectArr2Temp=sl2.get(j);
						if(primaryKey.equals(objectArr2Temp[0]+"")){
							content+="01$"+objectArr2Temp[1]+"$"+objectArr2Temp[2]+"$"+objectArr2Temp[3]
									+"$"+objectArr2Temp[4]+"$"+objectArr2Temp[5]+"$"+objectArr2Temp[6]
									+"$"+objectArr2Temp[7]+"$"+objectArr2Temp[8]+"$"+objectArr2Temp[9]+"$"+objectArr2Temp[10]+"$#";
						}
					}
					
					for(int j=0,len2=sl3.size();j<len2;j++){
						Object[]objectArr2Temp=sl3.get(j);
						if(primaryKey.equals(objectArr2Temp[0]+"")){
							content+="02$"+objectArr2Temp[1]+"$"+objectArr2Temp[2]+"$"+objectArr2Temp[3]
									+"$"+objectArr2Temp[4]+"$"+objectArr2Temp[5]+"$"+objectArr2Temp[6]
									+"$"+objectArr2Temp[7]+"$"+objectArr2Temp[8]+"$"+objectArr2Temp[9]+"$"+objectArr2Temp[10]+"$#";
						}
					}
					
					for(int j=0,len2=sl4.size();j<len2;j++){
						Object[]objectArr2Temp=sl4.get(j);
						if(primaryKey.equals(objectArr2Temp[0]+"")){
							content+="03$"+objectArr2Temp[1]+"$"+objectArr2Temp[2]+"$"+objectArr2Temp[3]
									+"$"+objectArr2Temp[4]+"$"+objectArr2Temp[5]+"$"+objectArr2Temp[6]
									+"$"+objectArr2Temp[7]+"$#";
						}
					}
					
					sb.append(content+"|\r\n");
				}
				
				writeStr(sb.toString(),filename,url);
				
				
				try {
				    FtpUtils.zfbUploadQfUserinfo(filepath, alluserinfofileaddress+filename);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝>上传ftb"+e.getMessage(),e.getCause());
				}
				
				String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String noticepath="{\"head\":{\"version\":\"1.0.1\",\"source\":\"WTHNXXSHOUCHUANG\"," +
						"\"desIfno\":\"ALIPAY\",\"servCode\":\"100001\"," +
						"\"msgId\":\"51D5F637C5141C27260003A4EB486495\"," +
						"\"msgTime\":\""+time+"\",\"extend\":\"\"}," +
						"\"body\":{\"acctOrgNo\":\""+areaarr[i]+"\"," +
						"\"filePath\":\""+alluserinfofileaddress+"\"," +
						"\"fileType\":\"DZZD\",\"fileName\":\""+filename+"\",\"extend\":\"\" }}";
				
			//	Message res=RequestUtil.sendMessage(new Message("100001", noticepath));
				String classfunction="月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝";
				String res = null;
				log.info(classfunction+":"+noticepath);
				if (Boolean.parseBoolean(ReadProperties.getProperty("sendMessagezfbbill"))) {
					try {
						res = Composrequesutil.sendMessage(noticepath, "100001", classfunction);
					} catch (FileNotFoundException e) {
						log.error("发生提醒出错"+e.getMessage(),e.getCause());	
					}
					
				}
				log.info("月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝 "+res);
			}
		}
		
		//通知sslsocket或者webservice去取，按需推送
		return "success";
	}
	
	
	
	/**
	 * 代扣文本生成及上传，通知支付宝
	 */
	
	public String uploadDkFile(){
		String ss=ReadProperties.getProperty("allarea");
		String[]areaarr=ss.split(",");
		CommonManager cm=Lc.getConn();
		log.debug("------------>>"+ss);
		
		String url=ReadProperties.getProperty("filezfb");
		String alluserinfofileaddress=ReadProperties.getProperty("alluserinfofileaddress");
		
		for(int i=0;i<areaarr.length;i++){
			SuperList<Object[]>sl=cm.createCall("SpBCQueryTotalDK", new String[]{areaarr[i]});
			SuperList<Object[]>sl2=cm.createCall("SpBCQueryDK", new String[]{areaarr[i]});
			
			if(sl!=null&&sl.size()>0){
				String filename="HZGD_DKKK_"+areaarr[i]+"_"+DateConvertUtils.DateToString(new Date(),"yyyyMMdd")+".txt";
				String filepath=url+filename;
				
				StringBuilder sb=new StringBuilder();
				
				
				Object[]oo=sl.get(0);
				sb.append(oo[0]+"|"+oo[1]+"|"+oo[2]+"|"+oo[3]+"|"+oo[4]+"|\r\n");
				
				
				for(int j=0,len=sl2.size();j<len;j++){
				   Object[]objectArr=sl2.get(j);
				   sb.append(objectArr[0]+"|"+objectArr[1]+"|"+objectArr[2]+"|"+objectArr[3]+"|"+objectArr[4]+"|"+objectArr[5]+"|"+objectArr[6]+"|"+objectArr[7]+"|"+objectArr[8]+"|"+objectArr[9]+"|"+objectArr[10]+"|\r\n");
				}
				
				writeStr(sb.toString(),filename,url);
				
				
				try {
				    FtpUtils.zfbUploadQfUserinfo(filepath, alluserinfofileaddress+filename);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String noticepath="{\"head\":{\"version\":\"1.0.1\",\"source\":\"HZGD\"," +
						"\"desIfno\":\"LONGSHINE\",\"servCode\":\"200111\"," +
						"\"msgId\":\"51D5F637C5141C27260003A4EB486495\"," +
						"\"msgTime\":\""+time+"\",\"extend\":\"\"}," +
						"\"body\":{\"acctOrgNo\":\""+areaarr[i]+"\"," +
						"\"filePath\":\""+alluserinfofileaddress+"\"," +
						"\"fileType\":\"DKKK\",\"fileName\":\""+filename+"\",\"extend\":\"\" }}";
				Message res=RequestUtil.sendMessage(new Message("200111", noticepath));
				System.out.println("code:"+res.getCode()+",data:"+res.getData());
			}
		}
		
		
		
		//通知sslsocket或者webservice去取，按需推送
		
		
		return "success";
	}
	
	
	/**
	 * 读取代扣扣款反馈文本
	 */
	
	//对账的方法
	public String readReDkFile(String dst,String fileName,String misarea,String acctOrgNo) {
        File file = new File(dst);
        BufferedReader reader = null;
        try {
        	
        	CommonManager cm=Lc.getConn();
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            int czpc=0;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                if(line==1){
                	String []temparr=tempString.split("\\|");
                	
                	cm.createExecSql("insert into "+misarea+"ReDkInfoTotal(请求总记录数 ,请求总金额,扣款成功总记录数 ,扣款成功总金额,发起日期 ,银行代码 ,在途标识,清算中心 )" +
                			"values(?,?,?,?,?,?,?,?)",temparr[0],SqlUtil.Csv(temparr[1],SqlType.NUMERIC),
                			temparr[2],SqlUtil.Csv(temparr[3],SqlType.NUMERIC),
                			SqlUtil.Csv(temparr[4],SqlType.TIMESTAMP),temparr[5],
                			temparr[6],acctOrgNo);

                	List<Object[]>ll=cm.createSQLQuery("select MAX(操作批次) from "+misarea+"ReDkInfoTotal");
                	if(ll!=null&&ll.size()>0){
                		Object[]oarrt=ll.get(0);
                		czpc=Integer.valueOf(oarrt[0]+"");
                	}
                }else{
                	String []temparr=tempString.split("\\|");
                	cm.createExecSql("insert into  "+misarea+"ReDkInfo(detId,consNo,acctOrgNo,payAcctNo," +
                			"pQ,rcvblAmtId,rcvblYm,rcvblAmt,rcvblPenalty,totalAmt,reqDay,resDay,debitAmt," +
                			"accountedFlag,billNo,extend,操作批次 ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                			temparr[0],temparr[1],temparr[2],temparr[3],SqlUtil.Csv(temparr[4],SqlType.NUMERIC),
                			temparr[5],temparr[6],SqlUtil.Csv(temparr[7],SqlType.NUMERIC),SqlUtil.Csv(temparr[8],SqlType.NUMERIC),
                			SqlUtil.Csv(temparr[9],SqlType.NUMERIC),SqlUtil.Csv(temparr[10],SqlType.TIMESTAMP),
                			SqlUtil.Csv(temparr[11],SqlType.TIMESTAMP),SqlUtil.Csv(temparr[12],SqlType.NUMERIC),
                			temparr[13],temparr[14],temparr[15],czpc);
                }
              
                line++;
                
            }
            reader.close();
            
            Object[]ss=cm.createCall(misarea+"SpBCQueryReDK", new Object[]{acctOrgNo,4,czpc}, new Integer[]{SqlType.VARCHAR,SqlType.VARCHAR,SqlType.VARCHAR});
            
            String s="";
            if(ss!=null&&ss.length>0){
            	for(int i=0;i<ss.length;i++){
            		s+=ss[i]+"   ";
            	}
            }
            log.debug("------------>>调用代扣反馈文本返回的信息："+s);
        } catch (IOException e) {
            e.printStackTrace();
            return getBody("4000","文件格式错误","");
        }finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return getBody("9999","接收文档成功","");
    }
		
	
	/**
	 * 获取所有用户水卡的信息记录
	 */
	
	public String getUserInfo(){
		
		/*
		zs=telekmis_zs
		bf=telekmis_zs
		df=telekmis_zs
		ds=telekmis_zs
		gz=telekmis_zs
		mz=telekmis_zs
		sw=telekmis_zs
		sx=telekmis_zs
		gk=telekmis_zs
		nt=telekmis_zs
		sl=zssl10
		hl=zshn11
		sj=telekmis_zs*/
				
				
		String ss=ReadProperties.getProperty("allarea");
		
		
		String[]areaarr=ss.split(",");
		
		log.debug("------------>>"+ss);
		
		String []title={"户号","户名","地址","手机号码","证件号码"};
		
		String datastr=DateConvertUtils.DateToString(new Date(),"yyyyMMdd");
		for(int i=0;i<areaarr.length;i++){
			String misarea=getMisArea(areaarr[i]);
			
			SuperList sl=Lc.getConn().createSuperSQLQuery("select a.户号," +
					"a.户名,a.地址,a.手机号码,b.证件号码 from "+misarea+"用户水卡 a " +
				    "left join "+misarea+"开户信息  b on a.帐户编号=b.帐户编号  " +
				    		"order by a.户号 asc");
			
			if(sl!=null&&sl.size()>0){
				try {
					
					String url=ReadProperties.getProperty("filezfb");
					String filename= "alluserinfo_" + datastr + "_" + areaarr[i] + ".xls";
					String filepath=url+filename;
					
					try {
						if(new File(url).mkdirs())
							log.info("成功创建文件夹:"+url);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					File f = new File(filepath);
					Excel.writeExcelObject(new FileOutputStream(f), title, sl);
					
					//上传文件
					String alluserinfofileaddress=ReadProperties.getProperty("alluserinfofileaddress");
					try {
					    FtpUtils.zfbUploadQfUserinfo(filepath, alluserinfofileaddress+filename);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
		}
		
		return "1000";
	}
	
	
	/**
	 * 获取欠费用户的信息记录
	 * @return
	 */
	public String getSomeUserFeeInfo(){
        String ss=ReadProperties.getProperty("allarea");
        String qftime=ReadProperties.getProperty("qftime");
        
        int rownum=Integer.parseInt(ReadProperties.getProperty("rownum"));
		
		String[]areaarr=ss.split(",");
		CommonManager cm=Lc.getConn();
		
		log.debug("------------>>"+ss);
		
		for(int i=0;i<areaarr.length;i++){
			String misarea=getMisArea(areaarr[i]);
			
			SuperList<Object[]>sl=cm.createCall(misarea+"SPBCbillcheck_zfb", new String[]{qftime});
			
			
			if(sl!=null&&sl.size()>0){
				
				int allnum=sl.size();
				
				int pagesize=(allnum%rownum==0)?(allnum/rownum):(allnum/rownum+1);
				
				for(int k=0;k<pagesize;k++){
					String pageno="";
					if(k<9){
						pageno="0"+(k+1);
					}else{
						pageno=""+(k+1);
					}
					String filename="ZFB_HZGD_"+DateConvertUtils.DateToString(new Date(),"yyyyMMdd")+"_02_"+pageno+"_"+areaarr[i]+".txt";
					StringBuilder sb=new StringBuilder();
					
					
					int tempnum=(k+1)*rownum>allnum?allnum:(k+1)*rownum;
					int filenum=tempnum-k*rownum;
					sb.append("BEGIN|"+filenum+"\r\n");
					for(int j=k*rownum;j<tempnum;j++){
					   Object[]oo=sl.get(j);
					   sb.append("00006688|"+oo[0]+"|"+oo[1]+"|"+oo[2]+"|"+oo[3]+"|"+oo[13]+"|"+oo[4]+"|extendField[mobile="+oo[5]+",shown="+oo[6]+",lastShown="+oo[7]+",recordDate="+oo[8]+",chgQty="+oo[9]+",curAm="+oo[10]+",totalOweAmt="+oo[12]+"]\r\n");
					}
					
					sb.append("END\r\n");
					//writeStr(sb.toString(),filename);
				}
				
			}else{
				String filename="ZFB_HZGD_"+DateConvertUtils.DateToString(new Date(),"yyyyMMdd")+"_02_01_"+areaarr[i]+".txt";
				StringBuilder sb=new StringBuilder();
				sb.append("BEGIN|"+0+"\r\n");
				sb.append("END\r\n");
				//writeStr(sb.toString(),filename);
			}
			
		}
		
		
		return "";
	}
	
	public String writeStr(String str,String filename,String fileDst){
		  FileOutputStream m = null;
		  OutputStreamWriter n = null;
		  BufferedWriter s = null;
		  String url=fileDst;
		  String sResult="0";
		  try {
			if(new File(url).mkdirs())
				log.info("成功创建文件夹:"+url);
		  }catch (Exception e) {
			e.printStackTrace();
			sResult="1";
			return sResult;
		  }
		  
		  try{
			   m = new FileOutputStream(url+filename);//文件输出流是用于将数据写入文件,如果没有这个文件则自动创造一个
			   n = new OutputStreamWriter(m);//OutputStreamWriter是字节流通向字符流的桥梁
			   s = new BufferedWriter(n);//将文本写入字符输出流
		       s.write(str);//写入字符
		  }catch (FileNotFoundException e) {
		     log.error("找不到文件");
		     sResult="1";
		     return sResult;
		  }catch (IOException a) {
			 log.error("写入数据失败");
			 sResult="1";
			 return sResult;
		  }finally{
		    try{
			    s.flush();
			    s.close();
			    n.close();
			    m.flush();
			    m.close();
		    }catch (IOException e) {
		        e.printStackTrace();
		        sResult="1";
		        return sResult;
		    }
		  }
		  return sResult;
	}
	
	/*****************************************判定报文的格式是否合法**************************************************************/
	
	public boolean isRightVersion(Map jsonhead){
		boolean flag=true;
		String version=jsonhead.get("version")+"";
		
		if(!("1.0.1".equals(version))){
			flag=false;
		}
		return flag;
	}
	
	public boolean isRightServCode(Map jsonhead,String servCode){
		boolean flag=true;
		String servCodeTemp=jsonhead.get("servCode")+"";
		
		if(!(servCode.equals(servCodeTemp))){
			flag=false;
		}
		return flag;
	}
	
	public String getFailData(String version ,String source ,String desIfno ,String servCode,
			String msgId ,String msgTime,String extend ,String rtnCode,String rtnMsg,String acctOrgNo){
		return "{\"head\":{\"version\":\""+version+"\",\"source\":\""+source+"\",\"desIfno\":\""+desIfno+"\",\"servCode\":\""+servCode+"\",\"msgId\":\""+msgId+"\",\"msgTime\":\""+msgTime+"\",\"extend\":\""+extend+"\"}," +
				"\"body\":{\"rtnCode\":\""+rtnCode+"\",\"rtnMsg\":\""+rtnMsg+"\",\"consNo\":\"\",\"consName\":\"\",\"addr\":\"\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\""+acctOrgNo+"\",\"capitalNo\":\"\",\"consType\":\"\"," +
						"\"prepayAmt\":\"0.00\",\"totalOweAmt\":\"0.00\",\"totalRcvblAmt\":\"0.00\",\"totalRcvedAmt\":\"0.00\",\"totalPenalty\":\"0\",\"recordCount\":\"0\",\"rcvblDet\":[]}}";
	}
	
	public String getFailData(String head,String rtnCode,String rtnMsg,String acctOrgNo){
		return "{\"head\":"+head+",\"body\":{\"rtnCode\":\""+rtnCode+"\",\"rtnMsg\":\""+rtnMsg+"\",\"consNo\":\"\",\"consName\":\"\",\"addr\":\"\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\""+acctOrgNo+"\",\"capitalNo\":\"\",\"consType\":\"\"," +
						"\"prepayAmt\":\"0.00\",\"totalOweAmt\":\"0.00\",\"totalRcvblAmt\":\"0.00\",\"totalRcvedAmt\":\"0.00\",\"totalPenalty\":\"0\",\"recordCount\":\"0\",\"rcvblDet\":[]}}";
	}
	
	
	public String getSuccessData(String version ,String source ,String desIfno ,String servCode,
			String msgId ,String msgTime,String extend ,String body){
		return "{\"head\":{\"version\":\""+version+"\",\"source\":\""+source+"\",\"desIfno\":\""+desIfno+"\",\"servCode\":\""+servCode+"\",\"msgId\":\""+msgId+"\",\"msgTime\":\""+msgTime+"\",\"extend\":\""+extend+"\"}," +
				"\"body\":"+body+"}";
	}
	
	public String getSuccessData(String head ,String body){
		return "{\"head\":"+head+",\"body\":"+body+"}";
	}
	
	public String getBody(String rtnCode,String rtnMsg,String extend){
		return "{\"rtnCode\":\""+rtnCode+"\",\"rtnMsg\":\""+rtnMsg+"\",\"extend\":\""+extend+"\"}";
	}
	
	
	public String getArrayToBody(Object...value){
		
		String jsonBody="{";
		if(value!=null){
			for(int i=0,len=value.length;i<len;i++){
				if(i%2==0){
					jsonBody=jsonBody+"\""+value[i]+"\":\""+value[i+1]+"\",";
				}
			}
		}
		
		if(jsonBody.length()>1){
			jsonBody=jsonBody.substring(0, jsonBody.length()-1);
		}
		
		jsonBody+="}";
		
		return jsonBody;
		//return "{\"rtnCode\":\""+rtnCode+"\",\"rtnMsg\":\""+rtnMsg+"\",\"extend\":\""+extend+"\"}";
	}
	
	//对账的方法
	public String readFileByLines(String dst,String fileName,String misarea,String acctOrgNo) {
        File file = new File(dst);
        BufferedReader reader = null;
        try {
        	
        	//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			boolean flags=(cm.getDBType()==SqlServerType.MSSQL);
			
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            int czpc=0;
            SuperList<Object[]> superList = new SuperArrayList<Object[]>();
            String sql = "";
            while ((tempString = reader.readLine()) != null) {
            	Object[] objects = new Object[9];
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                if(line==1){
                	String []temparr=tempString.split("\\|");
                	String totalMoney=temparr[0]+"";
                	String totalRecord=temparr[1]+"";
                	//004-->013
                	System.out.println(fileName+totalRecord+totalMoney+temparr.length);
                	cm.createExecSql("insert into "+misarea+"实时销帐倒入记录("+(flags?"":"操作批次,")+"清算单位,倒入类别, 总行代码, " +
                			"文件名,缴费日期, 倒入时间, 总笔数, 总金额, 水司笔数, 水司金额)" +
                			"values("+(flags?"":"ActionID_seq.nextval,")+"?,NULL,'013',?,NULL,"+(flags?"GETDATE()":"sysdate")+",?,?,NULL,NULL)",acctOrgNo,fileName,totalRecord,totalMoney);

                	List<Object[]>ll=cm.createSQLQuery("select MAX(操作批次) from "+misarea+"实时销帐倒入记录");
                	if(ll!=null&&ll.size()>0){
                		Object[]oarrt=ll.get(0);
                		czpc=Integer.valueOf(oarrt[0]+"");
                	}
                }else{
                	String []temparr=tempString.split("\\|");
                	String hh=temparr[0]+"";
                	String lsh=temparr[1]+"";
                	String jfrq=temparr[2]+"";
                	String je=temparr[3]+"";
                	String recordnum=temparr[4]+"";
                	String other=temparr[5]+"";
                	
                	
                	String someOrAll=ReadProperties.getProperty("someOrAll");
                	
                	String  workerid="";
                	workerid=ReadProperties.getProperty("workerid");
                	if(!StringUtil.isNB(workerid)){
                		workerid="88888";
                	}
                	//缴清模式
                	if("all".equals(someOrAll)){
                		String []otherarr=other.split("\\#");
                    	
                    	
                    	for(int i=0,len=otherarr.length;i<len;i++){
                    		String otherarrstr=otherarr[i];
                    		
                    		String []otherarrstrarr=otherarrstr.split("\\$");
                    		
                    		String hhtemp=otherarrstrarr[0];
                    		String ysbs=otherarrstrarr[1];
                        	String ysny=otherarrstrarr[2];
                        	
                        	objects[0] = acctOrgNo;
                        	objects[1] = czpc;
                        	objects[2] = lsh;
                        	objects[3] = recordnum;
                        	objects[4] = hh;
                        	objects[5] = jfrq;
                        	objects[6] = je;
                        	objects[7] = ysbs;
                        	objects[8] = ysny;
                        	superList.add(objects);
                        	if (sql.equals("")) {
								sql = "insert into  "+misarea+"IdChgComp013(清算单位,操作批次, 操作工号, 倒入序号,操作日期," +
	                        			" 更新结果, 进帐流水号, 记录数, 户号, 缴费日期,实收金额, 起始日期, 结束日期, 发票号码, " +
	                        			"应收标识, 应收年月) values(?,?,"+workerid+",NULL,"+(flags?"GETDATE()":"sysdate")+",NULL,?,?,?,?,?,NULL,NULL,NULL,?,?)";
							}
                        	/*cm.createExecSql("insert into  "+misarea+"IdChgComp013(清算单位,操作批次, 操作工号, 倒入序号,操作日期," +
                        			" 更新结果, 进帐流水号, 记录数, 户号, 缴费日期,实收金额, 起始日期, 结束日期, 发票号码, " +
                        			"应收标识, 应收年月) values(?,?,"+workerid+",NULL,"+(flags?"GETDATE()":"sysdate")+",NULL,?,?,?,?,?,NULL,NULL,NULL,?,?)",
                        			acctOrgNo,czpc,lsh,recordnum,hh,jfrq,je,ysbs,ysny);*/
                    	}
                	}else{
                		//自由缴模式
                    	String []otherarr=other.split("\\$");
                    	
                    	String ysbs=otherarr[1];
                    	String ysny=otherarr[2];
                    	
                    	objects[0] = acctOrgNo;
                    	objects[1] = czpc;
                    	objects[2] = lsh;
                    	objects[3] = recordnum;
                    	objects[4] = hh;
                    	objects[5] = jfrq;
                    	objects[6] = je;
                    	objects[7] = ysbs;
                    	objects[8] = ysny;
                    	superList.add(objects);
                    	if (sql.equals("")) {
							sql = "insert into  "+misarea+"IdChgComp013(清算单位,操作批次, 操作工号, 倒入序号,操作日期," +
	                    			" 更新结果, 进帐流水号, 记录数, 户号, 缴费日期,实收金额, 起始日期, 结束日期, 发票号码, " +
	                    			"应收标识, 应收年月) values(?,?,"+workerid+",NULL,"+(flags?"GETDATE()":"sysdate")+",NULL,?,?,?,?,?,NULL,NULL,NULL,?,?)";
						}
                    	/*cm.createExecSql("insert into  "+misarea+"IdChgComp013(清算单位,操作批次, 操作工号, 倒入序号,操作日期," +
                    			" 更新结果, 进帐流水号, 记录数, 户号, 缴费日期,实收金额, 起始日期, 结束日期, 发票号码, " +
                    			"应收标识, 应收年月) values(?,?,"+workerid+",NULL,"+(flags?"GETDATE()":"sysdate")+",NULL,?,?,?,?,?,NULL,NULL,NULL,?,?)",
                    			acctOrgNo,czpc,lsh,recordnum,hh,jfrq,je,ysbs,ysny);*/
                	}
                	
                }
              
                line++;
                
            }
            reader.close();
            //批量插入
            cm.createBatchSql(sql, superList);
            Object[]ss=cm.createCall(misarea+"SpChgComp013", new Object[]{acctOrgNo,4,czpc}, new Integer[]{SqlType.VARCHAR,SqlType.VARCHAR,SqlType.VARCHAR});
            
            String s="";
            if(ss!=null&&ss.length>0){
            	for(int i=0;i<ss.length;i++){
            		s+=ss[i]+"   ";
            	}
            }
            log.debug("------------>>调用对账返回的信息："+s);
        } catch (IOException e) {
            e.printStackTrace();
            return getBody("4000","文件格式错误","");
        }finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return getBody("9999","接收文档成功","");
    }
	
	
	//滨江对账的方法
	public String readFileByLines_bj(String dst,String fileName,String misarea,String acctOrgNo) {
        File file = new File(dst);
        BufferedReader reader = null;
        try {
        	
        	//CommonManager cm=Lc.getConn();
			CommonManager cm=null;
			//如果多个乡镇，分别建立连接时
			if("true".equals(manyConnections)){
				cm=Lc.getConn(acctOrgNo);
			}else{
				cm=Lc.getConn();
			}
			
			boolean flags=(cm.getDBType()==SqlServerType.MSSQL);
			
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            int czpc=0;
            SuperList<Object[]> superList = new SuperArrayList<Object[]>();
            String sql = "";
            while ((tempString = reader.readLine()) != null) {
            	Object[] objects = new Object[11];
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                if(line==1){
                	String totalMoney=tempString.substring(0,10).trim();
                	String totalRecord=tempString.substring(10,22).trim();
                	//004-->013
                	//System.out.println(fileName+totalRecord+totalMoney+2);
                	log.info(fileName+totalRecord+totalMoney+2);
                	cm.createExecSql("insert into "+misarea+"实时销帐倒入记录("+(flags?"":"操作批次,")+"清算单位,倒入类别, 总行代码, " +
                			"文件名,缴费日期, 倒入时间, 总笔数, 总金额, 水司笔数, 水司金额)" +
                			"values("+(flags?"":"ActionID_seq.nextval,")+"?,NULL,'013',?,NULL,"+(flags?"GETDATE()":"sysdate")+",?,?,NULL,NULL)",acctOrgNo,fileName,totalMoney,totalRecord);

                	List<Object[]>ll=cm.createSQLQuery("select MAX(操作批次) from "+misarea+"实时销帐倒入记录");
                	if(ll!=null&&ll.size()>0){
                		Object[]oarrt=ll.get(0);
                		czpc=Integer.valueOf(oarrt[0]+"");
                	}
                }else{
                	String str=tempString;
                	
                	String lsh=str.substring(0, 25);
        	    	String hh=str.substring(25, 45);
        	    	String jfrq=str.substring(45, 53);
        	    	String je=str.substring(53, 65);
        	    	
        	    	String fyrq=str.substring(65, 71);
        	    	String fyid=str.substring(71, 81);
        	    	String sfbj=str.substring(81, 93);
        	    	String znj=str.substring(93, 105);
        	    	String qdbz=str.substring(105, 106);
        	    	 lsh=lsh.trim();
        	    	 hh=hh.trim();
        	    	 jfrq=jfrq.trim();
        	    	 je=je.trim();
        	    	
        	    	 fyrq=fyrq.trim();
        	    	 fyid=fyid.trim();
        	    	 sfbj=sfbj.trim();
        	    	 znj=znj.trim();
        	    	 qdbz = qdbz.trim();
                	
                	String  workerid="";
                	workerid=ReadProperties.getProperty("workerid");
                	if(!StringUtil.isNB(workerid)){
                		workerid="88888";
                	}
                	
                	objects[0] = acctOrgNo;
                	objects[1] = czpc;
                	objects[2] = lsh;
                	objects[3] = hh;
                	objects[4] = jfrq;
                	objects[5] = je;
                	objects[6] = sfbj;
                	objects[7] = znj;
                	objects[8] = fyid;
                	objects[9] = fyrq;
                	objects[10] = qdbz;
    
                	superList.add(objects);
                	if (sql.equals("")) {
						sql = "insert into  "+misarea+"IdChgComp013(清算单位,操作批次, 操作工号, 倒入序号,操作日期," +
	                			" 更新结果, 进帐流水号, 记录数, 户号, 缴费日期,实收金额, 水费本金, 滞纳金," +
	                			"费用ID, 费用年月,渠道标志) values(?,?,"+workerid+",NULL,"+(flags?"GETDATE()":"sysdate")+",NULL,?,NULL,?,?,?,?,?,?,?,?)";
					}
                	
                	/*cm.createExecSql("insert into  "+misarea+"IdChgComp013(清算单位,操作批次, 操作工号, 倒入序号,操作日期," +
                			" 更新结果, 进帐流水号, 记录数, 户号, 缴费日期,实收金额, 水费本金, 滞纳金," +
                			"费用ID, 费用年月,渠道标志) values(?,?,"+workerid+",NULL,"+(flags?"GETDATE()":"sysdate")+",NULL,?,NULL,?,?,?,?,?,?,?,?)",
                			acctOrgNo,czpc,lsh,hh,jfrq,je,sfbj,znj,fyid,fyrq,qdbz);*/
                }
              
                line++;
                
            }
            reader.close();
            
            //批量插入
            cm.createBatchSql(sql, superList);
            Object[]ss=cm.createCall(misarea+"SpChgComp013", new Object[]{acctOrgNo,4,czpc}, new Integer[]{SqlType.VARCHAR,SqlType.VARCHAR,SqlType.VARCHAR});   
            String s="";
            if(ss!=null&&ss.length>0){
            	for(int i=0;i<ss.length;i++){
            		s+=ss[i]+"   ";
            	}
            }
            log.debug("------------>>调用对账返回的信息："+s);
        } catch (IOException e) {
            e.printStackTrace();
            return getBody("4000","文件格式错误","");
        }finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return getBody("9999","接收文档成功","");
    }
	
	
	public String getMisArea(String acctOrgNo){
		String ss=ReadProperties.getProperty(acctOrgNo);
		if(ss==null||"null".equals(ss)){
		 ss="";
			
		}
		return "";
	}
	
	
	public String getMisArea2(String acctOrgNo){
		String ss=ReadProperties.getProperty(acctOrgNo);
		return ss;
	}


	
	/******************************************Webservice Test***************************************************************/
	
	
	/**
	 * 欠费查询 webservice 调用测试
	 * @param xml
	 * @throws IOException 
	 */
	public String startQueryWaterFee(String json) throws IOException{
		String sResult="{}";
		try {
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:getByteTest");
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement("getByteTest", omNs);
		    OMElement v1 = fac.createOMElement("json", omNs);
		    v1.setText(json);
		    data.addChild(v1);
		    
		    OMElement result = sender.sendReceive(data);
		    
		    byte[] res=null;
		    
		    Iterator it = result.getChildElements();
			 while (it.hasNext()) {
				 OMElement el = (OMElement) it.next();
				 OMText binaryNode = (OMText) el.getFirstOMChild();
				 binaryNode.setOptimize(true); //必须加此句，否则会出现ContentID is null的异常!
				 DataHandler dh = (DataHandler) binaryNode.getDataHandler();
				 
				 InputStream is = dh.getInputStream();
				 res=IOUtils.getStreamAsByteArray(is);
			 }
		    
			sender.cleanup();sender.cleanupTransport();
			log.info("startByteTest 返回  s:"+new String(res));
			log.info("startByteTest 返回  result："+result.toString());
			sResult=new String(res);
			
		} catch (AxisFault e) {
			log.info("startSendThread ServiceException error :"+e.getMessage(),e.getCause());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	
	/**
	 * 欠费查询 webservice 调用测试
	 * @param xml
	 * @throws IOException 
	 */
	public String startQueryWaterFee2(String json) throws IOException{
		String sResult="{}";
		try {
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:queryWaterFee");
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement("queryWaterFee", omNs);
		    OMElement v1 = fac.createOMElement("json", omNs);
		    v1.setText(json);
		    data.addChild(v1);
		    
		    OMElement result = sender.sendReceive(data);
		    
		    byte[] res=null;
		    String s="";
		    
		    Iterator it = result.getChildElements();
			 while (it.hasNext()) {
				 OMElement el = (OMElement) it.next();
				 s=el.getText();
//				 OMText binaryNode = (OMText) el.getFirstOMChild();
//				 binaryNode.setOptimize(true); //必须加此句，否则会出现ContentID is null的异常!
//				 DataHandler dh = (DataHandler) binaryNode.getDataHandler();
//				 
//				 InputStream is = dh.getInputStream();
//				 res=IOUtils.getStreamAsByteArray(is);
			 }
		    
			sender.cleanup();sender.cleanupTransport();
			System.out.println("---"+s);
			log.info("startSendThread 返回："+result.toString());
			sResult=s;
			
		} catch (AxisFault e) {
			log.info("startSendThread ServiceException error :"+e.getMessage(),e.getCause());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	public String startTest(String json) throws IOException{
		String sResult="{}";
		try {
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:getTest");
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement("getTest", omNs);
		    OMElement v1 = fac.createOMElement("json", omNs);
		    
		    
		    try {
		    	json=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(WaterFree.key.getBytes(), (json+"").getBytes()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    v1.setText(json);
		    data.addChild(v1);
		    
		    OMElement result = sender.sendReceive(data);
		    
		    byte[] res=null;
		    String s="";
		    
		    Iterator it = result.getChildElements();
			 while (it.hasNext()) {
				 OMElement el = (OMElement) it.next();
				 s=el.getText();
//				 OMText binaryNode = (OMText) el.getFirstOMChild();
//				 binaryNode.setOptimize(true); //必须加此句，否则会出现ContentID is null的异常!
//				 DataHandler dh = (DataHandler) binaryNode.getDataHandler();
//				 
//				 InputStream is = dh.getInputStream();
//				 res=IOUtils.getStreamAsByteArray(is);
			 }
		    
			sender.cleanup();sender.cleanupTransport();
			log.info("startTest 返回 s：---"+s);
			log.info("startTest 返回 result："+result.toString());
			
			try {
				s=new String(CryptoUtils.decodeECB(WaterFree.key.getBytes(), CryptoUtils.decodeTo64(s)),"utf-8");
				log.info("startTest 返回 decode s："+s);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sResult=s;
			
		} catch (AxisFault e) {
			log.info("startSendThread ServiceException error :"+e.getMessage(),e.getCause());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	public String startAllTest(String json,String method) throws IOException{
		String sResult="{}";
		try {
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:"+method);
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement(method, omNs);
		    OMElement v1 = fac.createOMElement("json", omNs);
		    
		    
		    try {
		    	json=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(WaterFree.key.getBytes(), (json+"").getBytes()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    v1.setText(json);
		    data.addChild(v1);
		    
		    OMElement result = sender.sendReceive(data);
		    
		    byte[] res=null;
		    String s="";
		    
		    Iterator it = result.getChildElements();
			 while (it.hasNext()) {
				 OMElement el = (OMElement) it.next();
				 s=el.getText();
			 }
		    
			sender.cleanup();sender.cleanupTransport();
			log.info("startTest 返回 s：---"+s);
			log.info("startTest 返回 result："+result.toString());
			
			try {
				s=new String(CryptoUtils.decodeECB(WaterFree.key.getBytes(), CryptoUtils.decodeTo64(s)),"utf-8");
				log.info("startTest 返回 decode s："+s);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sResult=s;
			
		} catch (AxisFault e) {
			log.info("startSendThread ServiceException error :"+e.getMessage(),e.getCause());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	private static EndpointReference targetEPR = new EndpointReference(
		    ReadProperties.getProperty("SmsTargetEPR"));
	
	
	private static Socket createSocket(){
		try {
			String serverHost = "localhost"; //服务端地址
			int serverPort = 1224; //服务端监听端口
			String clientPrivateKey = "D:\\kclient.keystore";  //客户端私钥
			String clientKeyPassword = "123456";  //客户端私钥密码
			String trustKey = "D:\\tclient.keystore"; //客户端信任证书列表，即服务端证书
			String trustKeyPassword = "123456";  //客户端信任证书密码
			
			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			ks.load(new FileInputStream(clientPrivateKey), clientKeyPassword.toCharArray());
			tks.load(new FileInputStream(trustKey), trustKeyPassword.toCharArray());

			kmf.init(ks, clientKeyPassword.toCharArray());
			tmf.init(tks);

			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			return (SSLSocket) ctx.getSocketFactory().createSocket(serverHost, serverPort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String test(){
		Socket socket=createSocket();
		
		try {
			socket.getOutputStream();
			socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	public String testsentssage() {
		try {
			String noticepath="{\"head\":{\"version\":\"1.0.1\",\"source\":\"WTHNXXSHOUCHUANG\"," +
					"\"desIfno\":\"ALIPAY\",\"servCode\":\"100001\"," +
					"\"msgId\":\"51D5F637C5141C27260003A4EB486495\"," +
					"\"msgTime\":\"20160907\",\"extend\":\"\"}," +
					"\"body\":{\"acctOrgNo\":\"362330\"," +
					"\"filePath\":\"\\downlode\"," +
					"\"fileType\":\"DZZD\",\"fileName\":\"WTHNXXSHOUCHUANG\",\"extend\":\"\" }}";
			System.out.println("======================开始");
			Message res=RequestUtil.sendMessage(new Message("100001", noticepath));
		} catch (Exception e) {
			System.out.println("+++++======================================"+e.getMessage().toString());
			e.getMessage().toString();
		}
		System.out.println("======================完事");
		return "sussce";
	}
	
		
	
	
}
