package com.services.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.plugins.waterfee.WaterFreeServiceManager;
import com.plugins.waterfee.util.CryptoUtils;
import com.shenjun.enums.HttpRespEnum;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.util.DateConvertUtils;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WSUtil;
import com.shenjun.util.WebUtil;
import com.shenjun.web.services.Service;
import com.webservice.service.WaterFree;

public class DataSynch implements Service {

	private Log log=LogFactory.getLog(DataSynch.class);
	
	public final static String key="longshinetozhongshanwtzs";
	
	public DataSynch(){
		//log.debug("----------------------->> init DataSynch");
	}
	
	
	private WaterFreeServiceManager waterFreeServiceManager;
	private  WaterFree waterFree;
	
	public void setWaterFreeServiceManager(WaterFreeServiceManager waterFreeServiceManager) {
		log.debug("---------------------------->>set--"+waterFreeServiceManager);
		this.waterFreeServiceManager = waterFreeServiceManager;
	}
	
	public static String exec=ReadProperties.getProperty("exec");
	public static long circledate=DateConvertUtils.convertToDate(DateConvertUtils.getShortDate()).getTime()+Long.parseLong(ReadProperties.getProperty("exectime"));
	
	public int execute() {
		// TODO Auto-generated method stub
		log.debug("----------------------->>start");
		
		//打印  月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝
		try {
			if ("true".equals(ReadProperties.getProperty("uploadDZZDFile"))) {
				log.debug("--------------------->>定时任务  打印  月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝");
			//	waterFree.uploadDZZDFile();
				//http请求
			String  url=ReadProperties.getProperty("SmsTargetEPR");
			 /**
			WebUtil.httpSend(url, "uploadDZZDFile");*/
				//web请求 
				WSUtil.executeWs(url, null, null, "uploadDZZDFile");
			
			}
		} catch (Exception e) {
			log.debug("++++ >>定时任务  打印  月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝  报错：   "+e.toString());
		}
		
		
		try {
			if ("true".equals(ReadProperties.getProperty("bingjiangdz"))) {
				log.debug("--------------------->>定时任务  滨江对账方法");
				
				String  url=ReadProperties.getProperty("SmsTargetEPR");
				Calendar calendar = Calendar.getInstance();
				calendar.add(calendar.DAY_OF_YEAR, Integer.valueOf(ReadProperties.getProperty("reduceTime")));
				String time = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
				
				String ReDk="{\"head\":{\"version\":\"1.0.1\",\"source\":\"ALIPAY\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200011\",\"msgId\":\"C2E365C1B437D109D8DF2678E893F47D\",\"msgTime\":\""+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"\",\"extend\":\"\"},\"body\":{\"fileType\":\""+ReadProperties.getProperty("fileType")+"\",\"extend\":\"\",\"fileName\":\"DZ0003"+time+".TXT\",\"filePath\":\""+ReadProperties.getProperty("bingjiangftppath")+"\",\"acctOrgNo\":\""+ReadProperties.getProperty("acctOrgNo")+"\"}}";
				//web请求 
				WSUtil.executeWs(url, null, null, "receiveFile","json",CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), ReDk.getBytes("utf-8"))));
			}
		} catch (Exception e) {
			log.debug("++++ >>定时任务  滨江对账方法  报错：   "+e.toString());
		}
		
		
	/*	try {
			if ("uploadDZZDFile".equals("uploadDZZDFile")) {
				long num=new Date().getTime();
				DateFormat df=new SimpleDateFormat("dd");
				int  da=Integer.parseInt(df.format(num));
				Calendar cld = new GregorianCalendar();
				int days=cld.getActualMaximum(cld.DATE);
				System.out.println("days ="+(days-2)+" da="+da);
				if (da==(days-1)) {//倒数第二天执行定时任务
					System.out.println("days ="+days+" da="+da);
					waterFreeServiceManager.uploadDZZDFile();
					log.debug("--------------------->>定时任务  打印  月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝");
				}
				
			}
		} catch (Exception e) {
			log.debug("++++ >>定时任务  打印  月账单文本生成及上传(出账通知-电子账单文本)，通知支付宝  报错：   "+e.toString());
		}*/
		  
		  
		
		/*
		if("exec".equals(exec)){
			exec="noexec";
			waterFreeServiceManager.getUserInfo();
			log.debug("------------->>exec");
		}
		
		long num=new Date().getTime();
		if(num>circledate){
			log.debug("----------------------->>num>circledate");
			
			waterFreeServiceManager.getSomeUserFeeInfo();
			circledate=circledate+Long.parseLong(ReadProperties.getProperty("circletime"));
		}*/
		log.debug("----------------------->>end");
		return 0;
	}
	
	public static void main(String[] args) {
		
		long a1,a2,a3,a4,a5,a6;
		System.out.println(a1=DateConvertUtils.convertToDate("2014-09-01").getTime());
		System.out.println(a2=DateConvertUtils.convertToDate("2014-09-02").getTime());
		System.out.println(a3=DateConvertUtils.convertToDate("2014-09-03").getTime());
		System.out.println(a4=DateConvertUtils.convertToDate("2014-09-04").getTime());
		System.out.println(a5=DateConvertUtils.convertToDate("2014-09-04 01:30:00").getTime());
		System.out.println(a6=DateConvertUtils.convertToDate("2014-09-04 23:30:00").getTime());
		
		System.out.println(a6-a4);
		System.out.println(a5-a4);
		System.out.println(a4-a3);
		System.out.println(a3-a2);
		System.out.println(a2-a1);
		System.out.println(DateConvertUtils.convertToDate(DateConvertUtils.getShortDate()).getTime());
		System.out.println(DataSynch.class.getResource("/").getPath());
	}

}
