package com.webservice.service;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.annotation.WebServiceDescription;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */
@WebServiceDescription(description="天气情况")
public class Weather {
	
	private  Log log = LogFactory.getLog(Weather.class);
	
	private static EndpointReference targetEPR = new EndpointReference(
     "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");
	
	/**
	 * 获取天气情况
	 * 31119 2057
	 * @return
	 */
	public String getWeather(String cityName){
		String res="没有您要找的天气";
		try { 
			 ServiceClient sender = new ServiceClient();
			 Options options = new Options();
			 options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			 options.setAction("http://WebXml.com.cn/getWeatherbyCityName");
			 options.setTo(targetEPR);
			 //options.setProperty(propertyKey, property)
	        
			 // enabling MTOM in the client side
			 // options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
			 options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		     sender.setOptions(options);
		     
		     OMFactory fac = OMAbstractFactory.getOMFactory();
		     OMNamespace omNs = fac.createOMNamespace("http://WebXml.com.cn/", "");
		     OMElement data = fac.createOMElement("getWeatherbyCityName", omNs);
		     OMElement inner = fac.createOMElement("theCityName", omNs);
		     inner.setText(cityName);
		     data.addChild(inner);
		     
		     
			 OMElement result = sender.sendReceive(data);
			 
			 res=result.toString();
			 
			 sender.cleanup();sender.cleanupTransport();
			 
		} catch (AxisFault e) {
			log.error("获取<"+cityName+">天气错误："+e.getMessage(),e.getCause());
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		System.out.println(new Weather().getWeather("杭州"));
	}
}
