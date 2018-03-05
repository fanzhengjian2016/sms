package com.shenjun.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.system.VarUtils;
/**
 * webService 操作类
 * @author shenjun
 *
 */
public class WSUtil {
	
	private static final Log log = LogFactory.getLog(WSUtil.class);
	
	private static Map<String,EndpointReference> endPointRefMap = new HashMap<String, EndpointReference>();
	
	public static void main(String[] args) {
		OMElement res = WSUtil.execute2("http://127.0.0.1/mjbox/services/JVersion?wsdl", "getOtherVersion", "sysName","aa");
		System.out.println(res.getFirstElement().getText());
	}
	
	/**
	 * 通过指定相应参数，返回结果文本
	 * @param endPointRef endPointRef http://0.0.0.0/service/v?wsdl
	 * @param method method execute
	 * @param params Map<String,Object>||key,value,key,value
	 * @return
	 */
	public static String executeVal(String endPointRef,String method,Object...params){
		return executeWs(endPointRef,null,null,method,params).getFirstElement().getText();
	}
	
	/**
	 * 通过指定相应参数，调用远程的服务接口
	 * @param endPointRef endPointRef http://0.0.0.0/service/v?wsdl
	 * @param method method execute
	 * @param params Map<String,Object>||key,value,key,value
	 * @return
	 */
	public static OMElement execute2(String endPointRef,String method,Object...params){
		return executeWs(endPointRef,null,null,method,params);
	}
	
	/**
	 * 通过指定相应参数，调用远程的服务接口
	 * @param endPointRef http://0.0.0.0/service/v?wsdl
	 * @param actionName urn:execute
	 * @param nameSpace ->http://service.webservice.com
	 * @param method execute
	 * @param params Map<String,Object>
	 * @return
	 * @throws AxisFault
	 */
	public static OMElement executeWs(String endPointRef,String actionName,String nameSpace,String method,Object...params){
		ServiceClient sender = null;
		OMElement result=null;
		try{
			if(nameSpace==null){
				nameSpace="http://service.webservice.com";
			}
			if(actionName==null){
				actionName="urn:"+method;
			}
			
			if(endPointRefMap.get(endPointRef)==null){
				endPointRefMap.put(endPointRef, new EndpointReference(endPointRef));
			}
			
			//sender = SpringUtil.getBean("serviceClient", ServiceClient.class);
			//sender = new ServiceClient();
			sender = VarUtils.VarContent(ServiceClient.class,null,false);
			
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			//options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction(actionName);
			options.setTo(endPointRefMap.get(endPointRef));
			//options.setTo(new EndpointReference(endPointRef));
			//options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			
			sender.setOptions(options);
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = fac.createOMNamespace(nameSpace, "");
		    
		    OMElement data = fac.createOMElement(method, omNs);
		    
		    Map<String, Object> pramsMap= null;
		    
		    if(params!=null&&params.length==1){
		    	if(params[0]!=null&&params[0] instanceof Map){
		    		pramsMap = (Map<String, Object>) params[0];
		    	}
		    }else if(params!=null&&params.length%2==0){
		    	pramsMap=MapUtils.instanceObj(params);
		    }
			   
		    if(pramsMap!=null){
		    	for(Map.Entry<String, Object> entry : pramsMap.entrySet()){
			    	OMElement v = fac.createOMElement(entry.getKey(), omNs);
				    v.setText(entry.getValue()==null?"":(entry.getValue()+""));
				    data.addChild(v);
			    }
		    }
			
			result = sender.sendReceive(data);
			//sender.sendReceiveNonBlocking(elem, callback)
			//sender.cleanupTransport();
		}catch(Exception e){
			log.error("WSUtil.executeWs["+endPointRef+"] method["+method+"] error:"+e.getMessage(),e.getCause());
		}
		
		try {
			sender.cleanupTransport();
			VarUtils.VarContent(ServiceClient.class,sender,false);
			//sender.cleanup();
		} catch (AxisFault e) {
			VarUtils.VarContent(ServiceClient.class,sender,true);
			log.error("WSUtil.executeWs["+endPointRef+"] method["+method+"] cleanupTransport error:"+e.getMessage(),e.getCause());
		}
		
		return result;
	}
}
