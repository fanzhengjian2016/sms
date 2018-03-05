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

/**
 * 水费处理页面
 * @author jbox_user
 *
 */
public class TestManager {
	protected static Log log = LogFactory.getLog(TestManager.class);
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
		TestManager w=new TestManager();
       
		//水费查询
	   //支付宝解密后的入参
       String one1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201203\",\"queryValue\":\"54114172\",\"busiType\":\"11\",\"bgnYm\":\"201203\",\"acctOrgNo\":\"00\"}}";
       
       
       //水费查询出参，需要加密给支付宝
       String one2="{\"head\":{\"version\":\"1.0.1\",\"source\":\"WTZHONGSHAN\",\"desIfno\":\"LONGSHINE\",\"servCode\":\"200001\",\"msgId\":\"dc5d3545-7307-439b-a482-785cbe5a0e7e\",\"msgTime\":\"20140709121758\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"9999\",\"rtnMsg\":\"\",\"consNo\":\"54114172\",\"consName\":\"羊\",\"addr\":\"\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\"00\",\"capitalNo\":\"\",\"consType\":\"\",\"prepayAmt\":\"0.00\",\"totalOweAmt\":\"63.70\",\"totalRcvblAmt\":\"63.70\",\"totalRcvedAmt\":\"63.70\",\"totalPenalty\":\"0\",\"recordCount\":\"1\",\"rcvblDet\":[{\"rcvblAmtId\":\"40281698\",\"consNo\":\"54114172\",\"consName\":\"羊\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\"00\",\"rcvblYm\":\"201404\",\"tPq\":\"26\",\"rcvblAmt\":\"63.70\",\"rcvedAmt\":\"0\",\"rcvblPenalty\":\"0\",\"oweAmt\":\"63.70\",\"extend\":\"\"}]}}";
       String one3="{\"head\":{\"version\":\"1.0.1\",\"source\":\"WTZHONGSHAN\",\"desIfno\":\"LONGSHINE\",\"servCode\":\"200001\",\"msgId\":\"66186717-408a-4587-b44e-8106c406884e\",\"msgTime\":\"20140709121632\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"9999\",\"rtnMsg\":\"\",\"consNo\":\"52508933\",\"consName\":\"陈\",\"addr\":\"\",\"orgNo\":\"\",\"orgName\":\"\",\"acctOrgNo\":\"00\",\"capitalNo\":\"\",\"consType\":\"\",\"prepayAmt\":\"0.00\",\"totalOweAmt\":\"0.00\",\"totalRcvblAmt\":\"0.00\",\"totalRcvedAmt\":\"0.00\",\"totalPenalty\":\"0.00\",\"recordCount\":\"0\",\"rcvblDet\":[]}}";
       
       //调用查询接口
       one2=w.startQueryWaterFee2(one1);
       
       //缴费入参
       String two1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200002\",\"msgId\":\"payshengyanhui0001\",\"msgTime\":\"20140708093609\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"rcvAmt\":\"164.15\",\"bankSerial\":\"2345678900\",\"rcvDet\":[{\"rcvblYm\":\"201404\",\"rcvblAmtId\":\"40271522\",\"consNo\":\"54118033\"}],\"chargeCnt\":\"1\",\"payMode\":\"\",\"capitalNo\":\"\",\"bankDate\":\"20140708\",\"acctOrgNo\":\"00\"}}";
       
       //缴费出参
       String two2="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200002\",\"msgId\":\"payshengyanhui0001\",\"msgTime\":\"20140708093609\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"2001\",\"rtnMsg\":\"\",\"extend\":\"\"}}";

       //缴费调用接口同水费查询
       
       
       //对账入参
       //需要提供ftp
       String three1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\"20140711170710\",\"extend\":\"\"},\"body\":{\"fileType\":\"\",\"extend\":\"\",\"fileName\":\"LONGSHINE_HAINING_DSDZ_20140709.txt\",\"filePath\":\"\",\"acctOrgNo\":\"00\"}}";
       //对账出参
       String three2="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\"20140711170710\",\"extend\":\"\"},\"body\":{\"rtnCode\":\"0009\",\"rtnMsg\":\"\",\"extend\":\"\"}}";

       //对账调用接口同水费查询，需要你们提供sftp服务器，用于你们存放对账文件；当你们调用对账接口，我们会去sftp取对账文件对账；
    

	    	
	}
	
	
	private static EndpointReference targetEPR = new EndpointReference("http://10.1.8.174:8080/Interactive/services/WaterFree?wsdl");
	
	
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
	
	
}
