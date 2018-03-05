package com.webservice.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.plugins.waterfee.WaterFreeServiceManager;
import com.plugins.waterfee.util.CryptoUtils;
import com.shenjun.annotation.ReturnType;
import com.shenjun.annotation.WebServiceDescription;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.system.SystemConfig;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;
import com.shenjun.web.thread.Lc;
@WebServiceDescription(description="水费服务接口")
public class WaterFree {
	
	protected static Log log = LogFactory.getLog(WaterFree.class);
	public final static String key="longshinetozhongshanwtzs";
	
	@SuppressWarnings("unused")
	private WaterFreeServiceManager waterFreeServiceManager;
	public static String DS_SMS_SERVER = SystemConfig.WEB_ROOT_URL+"/services/SmsServer?wsdl";
	
	
	/**
	 * 费用查询
	 * @return
	 */
	public String queryWaterFee(String json){
		
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("111_1---------------------------->>"+json+"----->>"+json2);
		if(waterFreeServiceManager==null){
			//log.debug("---------------------------->>null");
			waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		}
		String waterfee=waterFreeServiceManager.queryWaterFee(json2);
		log.debug("---------------------------->>"+waterfee);
		try {
			
			String result= CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), waterfee.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}
	/**
	 * wx费用查询
	 * @return
	 */
	public String queryWaterFeeWx(String json){
		
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("111_2---------------------------->>"+json+"----->>"+json2);
		if(waterFreeServiceManager==null){
			//log.debug("---------------------------->>null");
			waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		}
		String waterfee=waterFreeServiceManager.queryWaterFeeWx(json2);
		log.debug("---------------------------->>"+waterfee);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), waterfee.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * wx费用查询
	 * @return
	 */
	public String queryWaterFeeWX_MX(String json){
		
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("111_3---------------------------->>"+json+"----->>"+json2);
		if(waterFreeServiceManager==null){
			//log.debug("---------------------------->>null");
			waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		}
		String waterfee=waterFreeServiceManager.queryWaterFeeWX_MX(json2);
		log.debug("---------------------------->>"+waterfee);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), waterfee.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 支付水费
	 * @return
	 */
	public String payWaterFee(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("222---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String paywaterfee=waterFreeServiceManager.payWaterFee(json2);
		log.debug("---------------------------->>"+paywaterfee);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), paywaterfee.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 接收文本消息
	 * @return
	 */
	public String receiveFile(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("333---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String receivefile=waterFreeServiceManager.receiveFile(json2);
		log.debug("---------------------------->>"+receivefile);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), receivefile.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	
	/**
	 * 接收文本消息url方式
	 * @return
	 */
	public String receiveFileUrl(String str){
		String time=Lc.get("time");
		log.debug("333-----------------------time----->>"+time+"----->>");
		String json="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\""+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"\",\"extend\":\"\"},\"body\":{\"fileType\":\""+ReadProperties.getProperty("fileType")+"\",\"extend\":\"\",\"fileName\":\"DZ0003"+time+".TXT\",\"filePath\":\""+ReadProperties.getProperty("bingjiangftppath")+"\",\"acctOrgNo\":\""+ReadProperties.getProperty("acctOrgNo")+"\"}}";
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String receivefile=waterFreeServiceManager.receiveFile(json);
		log.debug("---------------------------->>"+receivefile);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), receivefile.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	
	/**
	 * 用户缴费协议查询
	 * 
	 */
	public String queryContact(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("444---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String queryContact=waterFreeServiceManager.queryContact(json2);
		log.debug("---------------------------->>"+queryContact);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), queryContact.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	
	/**
	 * 代扣协议维护(代扣签约，解约等)
	 */
	public String signContact(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("555---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String signContact=waterFreeServiceManager.signContact(json2);
		log.debug("---------------------------->>"+signContact);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), signContact.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	
	/**
	 * 
	 * 用户信息查询
	 * 
	 */
	public String queryUserInfo(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("666---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String queryUserInfo=waterFreeServiceManager.queryUserInfo(json2);
		log.debug("---------------------------->>"+queryUserInfo);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), queryUserInfo.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 绑定用户信息
	 * 
	 */
	public String bindUser(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("777---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean(waterFreeServiceManager.getClass());
		String bindUser=waterFreeServiceManager.bindUser(json2);
		log.debug("---------------------------->>"+bindUser);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), bindUser.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	
	/**
	 * 
	 * 用户账单查询
	 * 
	 */
	public String queryBill(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("888---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String queryBill=waterFreeServiceManager.queryBill(json2);
		log.debug("---------------------------->>"+queryBill);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), queryBill.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 余额查询
	 * @param json
	 * @return
	 */
	public String queryBalance(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("999---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String queryBalance=waterFreeServiceManager.queryBalance(json2);
		log.debug("---------------------------->>"+queryBalance);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), queryBalance.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 用户缴费记录查询
	 */
	public String queryPayFeeRecord(String json){
		String json2="{}";
		try {
			json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("10---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String queryPayFeeRecord=waterFreeServiceManager.queryPayFeeRecord(json2);
		log.debug("---------------------------->>"+queryPayFeeRecord);
		try {
			String result=  CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), queryPayFeeRecord.getBytes("utf-8")));
			log.debug("---------------------------->>加密返回内容:"+result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 
	 * 月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝
	 */
	public String uploadDZZDFile() {
			//log.debug("---------------------------->>null");
			waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
			String queryPayFeeRecord=waterFreeServiceManager.uploadDZZDFile();
			if ("success".equals(queryPayFeeRecord)) {
				log.debug("---------------------------->>月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝成功");
			}
		
		return  "{}";
		
	}
	
	/*
	public String getUserInfo(String aa){
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		//waterFreeServiceManager.getUserInfo();
		waterFreeServiceManager.getSomeUserFeeInfo();
		return "";
	}*/
	
	
	public String getTest(String json){
		log.info("param is :"+json);
		try {
			json=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(json)),"utf-8");
			log.info("decoce param is :"+json);
			
			json="do some thing:"+json;
			
			waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
			
			String queryPayFeeRecord=waterFreeServiceManager.uploadJFFile();
			
			return CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), json.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "{}";
	}
	
	
	public byte[] getByteTest(String json){
		
		return ("abcdefg:"+json).getBytes();
	}
	
	/*
	 * 
	 * 针对滨江收费系统，对接杭州银行，取消加密
	 */
	
	
	/**
	 * 费用查询
	 * @return
	 */
	public String queryWaterFeeBj(String json){
		
		String json2="{}";
		
		json2=json;
		log.debug("queryWaterFeeBj start---------------------------->>"+json+"----->>"+json2);
		if(waterFreeServiceManager==null){
			//log.debug("---------------------------->>null");
			waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		}
		String waterfee=waterFreeServiceManager.queryWaterFee(json2);
		log.debug("queryWaterFeeBj end---------------------------->>"+waterfee);
		try {
			return waterfee;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}
	
	
	
	/**
	 * 支付水费
	 * @return
	 */
	public String payWaterFeeBj(String json){
		String json2="{}";
		json2=json;
		log.debug("payWaterFeeBj start---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String paywaterfee=waterFreeServiceManager.payWaterFee(json2);
		log.debug("payWaterFeeBj end---------------------------->>"+paywaterfee);
		try {
			return paywaterfee;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * 接收文本消息
	 * @return
	 */
	public String receiveFileBj(String json){
		String json2="{}";
		json2=json;
		
		log.debug("receiveFileBj start---------------------------->>"+json+"----->>"+json2);
		waterFreeServiceManager= SpringUtil.getBean("waterFreeServiceManager",WaterFreeServiceManager.class);
		String receivefile=waterFreeServiceManager.receiveFile(json2);
		log.debug("receiveFileBj end---------------------------->>"+receivefile);
		try {
			return receivefile;
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return "{}";
	}
	
	

	
	
	
	public static void main(String[] args) {
	/*	String str="77+9Ze+/vXPvv70HAu+/vSMbFRHvv73vv71C77+977+9W2J1KO+/vSAa77+9dBQdaBPvv70RKe+/ve+/ve+/ve+/ve+/vQrvv71s77+977+9Ge+/vSgB77+927tdUu+/ve+/vXjvv70B77+9Ou+/vSrvv73vv71jM0RK77+977+9SO+/ve+/vXnvv70Aci/vv73vv73vv70E77+9G++/ve+/ve+/vQNOXOOEtADvv73vv73vv71VAe+/vWxq77+93ZUYMQxH77+9ctqDA++/vXRHa++/vT13CtKMQu+/ve+/vS8977+977+977+977+9e++/vQTvv716Au+/vW7vv73vv71pOwwDJCDvv73vv70eJu+/ve+/vSY1ZO+/vV5l77+9Ye+/ve+/ve+/vRlR77+977+9EwN777+9Yu+/vQrvv70477+9SO+/vT/vv73vv73vv73Zuy51dy3vv73vv73Wpu+/ve+/ve+/vUfvv73vv71o77+9Te+/ve+/vRHvv70c77+9Le+/ve+/vTM0Ju+/vVog77+9Ku+/ve+/vRsccO+/ve+/vW4WBWZu77+9F++/ve+/ve+/ve+/ve+/ve+/ve+/ve+/vW3vv73vv73vv73vv70j77+9Vkrvv70t77+977+9dGjvv71F77+92Y4n77+977+9UR3vv71nPhjntJ9zMO+/vSvvv73vv71RSO+/vQ==";
		try {
			String json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(str)),"utf-8");
			
			System.out.println(json2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//WaterFree wf = new WaterFree();
		//wf.uploadDZZDFile();
		
		
		//CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), json.getBytes("utf-8")));
		
		
		try {
			
			//加密过程
			String str="123456";
			//第一步
			byte[] barr = str.getBytes("utf-8");
			//第二步
			byte[]barr2=CryptoUtils.encodeECB(key.getBytes(),barr);
			//第三步
			String str2=CryptoUtils.encodeTo64(barr2);
			
			str2="kWWjc/UHAoVe8VTesOTVLqCnuqRvF+mafSnFRDU1MgF9WXvoBnEXbGJAGJmYtOG25umJonAosQ5qZzrVhxiqCBW8cFO6FEOPcCtamHz+d3j8G+VCf101Rkbg63HStnC+8uSvyzUdtSZNJAm0s+EnWHz/mTTAmSf1liPyxVROI8Py5K/LNR21JviCI+OsTcAygL9TbiVh+st2aMCKh4PQ0pOPGpb7KkXNRLtKWp2Qy2BXHROz1m3MpyEuJ2SYvvN0uw8O6nErd6moVKLmRGBatm67cDCfDayGl1EKCzT0dwIQtC4VPMb32NKrUt4sTQD/VWcyd/39Kwa+AuuHxEgIu67DgQGVL/v6St4Yv3TRoltXvT2bS5C0mr7ZYLwHBbFlIkFu9CzEsEA=";
		
			try {
				String json2=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(str2)),"utf-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
