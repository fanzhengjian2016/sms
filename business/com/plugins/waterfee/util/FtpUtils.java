package com.plugins.waterfee.util;

import com.jcraft.jsch.ChannelSftp;
import com.shenjun.plugins.sftp.ConnectConfig;
import com.shenjun.util.ReadProperties;
import com.shenjun.web.thread.Lc;

public class FtpUtils {
	public static void langxinDown(String downDir,String source,String dst) throws Exception{
	
		ConnectConfig c=new ConnectConfig();
		//c.setHost("218.244.146.167");
		//c.setUserName("zhongshan_test");
		//c.setPassWord("Zhongshan!!test");
		
		String langxinip=ReadProperties.getProperty("langxinip").trim();
		String langxinuser=ReadProperties.getProperty("langxinuser").trim();
		String langxinpass=ReadProperties.getProperty("langxinpass").trim();
		String langxinport=ReadProperties.getProperty("langxinport").trim();
		
		c.setHost(langxinip);
		c.setUserName(langxinuser);
		c.setPassWord(langxinpass);
		c.setPort(Integer.valueOf(langxinport));
		
		Lc.getSftpCm(c).downFile(downDir, source, dst);
	}
	
	
	public static void fatherDerectoryDown(String downDir,String source,String dst) throws Exception{
		
		ConnectConfig c=new ConnectConfig();
		//c.setHost("218.244.146.167");
		//c.setUserName("zhongshan_test");
		//c.setPassWord("Zhongshan!!test");
		
		String fatherip=ReadProperties.getProperty("fatherip").trim();
		String fatheruser=ReadProperties.getProperty("fatheruser").trim();
		String fatherpass=ReadProperties.getProperty("fatherpass").trim();
		String fatherport=ReadProperties.getProperty("fatherport").trim();
		
		c.setHost(fatherip);
		c.setUserName(fatheruser);
		c.setPassWord(fatherpass);
		c.setPort(Integer.valueOf(fatherport));
		
		Lc.getSftpCm(c).downFile(downDir, source, dst);
	}
	
	
	
	
	public static void zfbUploadQfUserinfo(String source,String dst) throws Exception{

		ConnectConfig c=new ConnectConfig();
		//c.setHost("sftp.alipay.com");
		//c.setUserName("zssgs");
		//c.setPassWord("TITE1Z");
		
		String zfbip=ReadProperties.getProperty("zfbip").trim();
		String zfbuser=ReadProperties.getProperty("zfbuser").trim();
		String zfbpass=ReadProperties.getProperty("zfbpass").trim();
		String zfbport=ReadProperties.getProperty("zfbport").trim();
		
		c.setHost(zfbip);
		c.setUserName(zfbuser);
		c.setPassWord(zfbpass);
		c.setPort(Integer.valueOf(zfbport));
		
		Lc.getSftpCm(c).uploadFile(source, dst, ChannelSftp.OVERWRITE);
	}
	
	public static void zfbUploadAllUserInfo(String source,String dst) throws Exception{

		ConnectConfig c=new ConnectConfig();
		//c.setHost("sftp.alipay.com");
		//c.setUserName("zssgs");
		//c.setPassWord("TITE1Z");
		
		String zfbip=ReadProperties.getProperty("zfbip2").trim();
		String zfbuser=ReadProperties.getProperty("zfbuser2").trim();
		String zfbpass=ReadProperties.getProperty("zfbpass2").trim();
		String zfbport2=ReadProperties.getProperty("zfbport2").trim();
		
		c.setHost(zfbip);
		c.setUserName(zfbuser);
		c.setPassWord(zfbpass);
		c.setPort(Integer.valueOf(zfbport2));
		
		Lc.getSftpCm(c).uploadFile(source, dst, ChannelSftp.OVERWRITE);
	}
}
