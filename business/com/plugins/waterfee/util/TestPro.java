package com.plugins.waterfee.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;


public class TestPro {
	
	private final static String key="longshinetozhongshanwtzs";
	
	/**
	 * 欠费查询 webservice 调用测试
	 * @throws IOException 
	 */
	public static String startQueryWaterFee2(String json) throws IOException{
		String sResult="{}";
		
		try {
			
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:queryWaterFeeWx");
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement("queryWaterFeeWx", omNs);
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
			//System.out.println("---:"+s);
			//System.out.println("startSendThread 返回--:"+result.toString());
			sResult=s;
			
		}catch (AxisFault e){
			e.printStackTrace();
			System.out.println("startSendThread ServiceException error :"+e.getMessage());
			sResult="{\"fail\":\"wwww\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("startSendThread ServiceException error :"+e.getMessage());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	/**
	 * 缴费 webservice 调用测试
	 * @throws IOException 
	 */
	public static String startPayWaterFee(String json) throws IOException{
		String sResult="{}";
		
		try {
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:payWaterFee");
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement("payWaterFee", omNs);
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
			//System.out.println("---:"+s);
			//System.out.println("startSendThread 返回--:"+result.toString());
			sResult=s;
			
		}catch (AxisFault e){
			e.printStackTrace();
			System.out.println("startSendThread ServiceException error :"+e.getMessage());
			sResult="{\"fail\":\"wwww\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("startSendThread ServiceException error :"+e.getMessage());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	
	/**
	 * 对账 webservice 调用测试
	 * @throws IOException 
	 */
	public static String startReceiveFile(String json) throws IOException{
		String sResult="{}";
		
		try {
			
			ServiceClient sender = new ServiceClient();
			Options options = new Options();
			options.setAction("urn:receiveFile");
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		    sender.setOptions(options);
		    
		    OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace("http://service.webservice.com", "");
		    
		    OMElement data = fac.createOMElement("receiveFile", omNs);
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
			//System.out.println("---:"+s);
			//System.out.println("startSendThread 返回--:"+result.toString());
			sResult=s;
			
		}catch (AxisFault e){
			e.printStackTrace();
			System.out.println("startSendThread ServiceException error :"+e.getMessage());
			sResult="{\"fail\":\"wwww\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("startSendThread ServiceException error :"+e.getMessage());
			sResult="{\"fail\":\"wwww\"}";
		}
		return sResult;
		
	}
	
	
	private static EndpointReference targetEPR = new EndpointReference("" +
			"http://127.0.0.1:8888/Interactive/services/WaterFree?wsdl");

	
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		
		/*
		String testjson="kWWjc/UHAoVe8VTesOTVLqCnuqRvF+mafSnFRDU1MgECzreRURQZwJqKUID0r8ipctqDA6l0R2tKGfHRG/fr1Svz6vYm5TOmUfJ/WDyMeAzoiQKMM23E6t561yxTN9sUhB92PaTw/tVGRpgW3e6HZ9UKc6pyrJRzJXu8d2X8aY0U+Kn7nEBRGWdrI9g1/NWqunGQQuhvuAZV+E9OspZTbAX85cTjZWd5DqsXLtWD822AaINZ/RlcXyGc0x9sCmdZRJOmrGFuW0zob/WfXI5xa7vpZWDOdy/pw5hutXNvHb/Zuy51dy2M8CgUU+uRAyQE8IAkmNBSTsiN76XUPqxjy2YfP/kQTJM1IzCZW7lk+i5QVWWH5FhmJ77ZYLwHBbFlUXkSj0FG2L8=";
		
		String testjson2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(testjson)),"utf-8");
		System.out.println(testjson2);
		
		
		String testjson3=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), testjson2.getBytes()));
		System.out.println(testjson3);
		*/
		//欠费查询请求参数
		String one1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201108\",\"queryValue\":\"74643\",\"busiType\":\"11\",\"bgnYm\":\"201108\",\"acctOrgNo\":\"cx\"}}";

		String one2=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), one1.getBytes()));
		String one3=startQueryWaterFee2(one2);
		
		String one4=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(one3)),"gb2312");
		System.out.println(one4);
		
		//缴费请求参数
		/*String one5="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"rcvAmt\":\"500.97\",\"bankSerial\":\"0909090911111111\",\"rcvDet\":[{\"rcvblYm\":\"201301\",\"rcvblAmtId\":\"10621363\",\"consNo\":\"23800\"}],\"chargeCnt\":\"1\",\"payMode\":\"\",\"capitalNo\":\"\",\"bankDate\":\"20140923 12:12:10\",\"acctOrgNo\":\"zs\"}}";

		String one6=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), one5.getBytes()));
		String one7=startPayWaterFee(one6);
		
		String one8=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(one7)),"gb2312");
		System.out.println(one8);
		
		
		//对账请求参数
		String one9="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"fileType\":\"DSDZ\",\"extend\":\"\",\"fileName\":\"LONGSHINE_ZHONGSHAN_DSDZ_20140929_zs.txt\",\"filePath\":\"/upload/\",\"acctOrgNo\":\"zs\"}}";

		String one10=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), one9.getBytes()));
		String one11=startReceiveFile(one10);
		
		String one12=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(one11)),"gb2312");
		System.out.println(one12);*/
	}

}
