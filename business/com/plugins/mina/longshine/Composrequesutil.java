package com.plugins.mina.longshine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Date;

import com.commons.number.CreateNum;
import com.commons.util.ByteDataBuffer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import com.commons.util.DeSplitDemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ReadFuture;
import org.exolab.castor.net.util.URIUtils;

import com.plugins.mina.ssl.SSLContextGenerator;
import com.shenjun.util.DateUtil;
import com.shenjun.util.JSONUtils;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;
import com.shenjun.web.thread.Lc;
import com.sun.mail.handlers.message_rfc822;


/**
 * 用户请求LongShine服务器类
 * @author bin
 *
 */
public class Composrequesutil {
	
	
	private static Log log=LogFactory.getLog(SSLContextGenerator.class);
	private final static String ip=ReadProperties.getProperty("longshine.socketip");
	private final static String port=ReadProperties.getProperty("longshine.socketport");
	private final static String ssl_key=ReadProperties.getProperty("longshine.sslkey");//客户端私钥密码   客户端信任证书密码 
	private final static String tclient_key=ReadProperties.getProperty("longshine.tclient");//客户端信任证书列表，即服务端证书
	private final static String kclient_key=ReadProperties.getProperty("longshine.kclient");//客户端私钥
	/**
	 * 给longshine发送 消息提醒
	 * @param msg 报文
	 * @param servCode   服务编码
	 *  @param classfunction  类的功能
	 * @return
	 */
	public  static  String sendMessage(String msg,String servCode,String classfunction) throws FileNotFoundException{
		  Socket  ss = crteatSockets();
		  Object bodyMap =null;
		  Object headMap=null;
		  try {
			//发送数据
			BufferedOutputStream bos = new BufferedOutputStream(ss.getOutputStream());
			bos.write(changbyte(msg,servCode));
			bos.flush();
			log.info("---发送数据--------"+msg);
			//收包
			final byte[] buf = new byte[36000];
			DeSplitDemo split = new DeSplitDemo();
			ss.getInputStream().read(buf);
			ByteDataBuffer bdb1 = new ByteDataBuffer(buf);
			String  tmpList = split.doCanProcess(bdb1);
			log.info("=========回执"+tmpList);
			java.util.Map<?, ?> map=JSONUtils.fromJson(tmpList);
			bodyMap =map.get("body");
			headMap=  map.get("head");
			log.info("bodyMap:"+JSONUtils.toJson(bodyMap)+"headMap:"+JSONUtils.toJson(headMap));
			inputtable(msg, tmpList, classfunction);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("---发送数据--------"+e.getMessage(),e.getCause());
		}finally {
			try {
				if (null!=ss.getInputStream()) {
					ss.getInputStream().close();
					ss.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			log.error("---关闭--------"+e.getMessage(),e.getCause());
			}
		}
			
	    return "success";
	}
	/**
	 * 初始化链接
	 * 
	 */
	public static Socket crteatSockets() {
		 String trustKey=WebUtil.getClassesRoot()+"ssl/longshine"+"/"+tclient_key+".keystore";
		 String kclient=WebUtil.getClassesRoot()+"ssl/longshine"+"/"+ kclient_key+".keystore";
			Socket ss = new Socket();
			try {
			SSLContext	ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(kclient), ssl_key.toCharArray());
			tks.load(new FileInputStream(trustKey), ssl_key.toCharArray());
			kmf.init(ks, ssl_key.toCharArray());
			tmf.init(tks);;
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			ss = ctx.getSocketFactory().createSocket(ip, Integer.valueOf(port));
			log.info("---初始化-------"+"ip:"+ip+",port:"+port );
			} catch (Exception e) {
				e.printStackTrace();
				log.error("---初始化-------"+e.getMessage(),e.getCause());
			}
		return ss;
	}
	
	/**
	 *  转换成字节流
	 *  @param msg 报文
	 * @param servCode   服务编码
	 *  
	 */
	public static byte[] changbyte(String  msg ,String servCode) throws Exception {
		ByteDataBuffer bdb = new ByteDataBuffer();
		bdb.setInBigEndian(false);
		bdb.writeInt8((byte) 0x68); // 开始字节
		int len = Integer.parseInt(getLen(msg, 4));
		// 用于计算数据域的长度是否大于4字节
		bdb.writeInt32(len); // 报文长度
		bdb.writeString(servCode, 6);// 服务编码
		bdb.writeBytes(msg.getBytes()); // 报文frame
		bdb.writeInt8((byte) 0x16); // 结束字节
		return bdb.getBytes();
	}
	
	/**
	 * 计算报文长度，不足四位左补零
	 * 
	 * @param text
	 *            报文信息
	 * @param needlen
	 *            报文长度规定的字符数
	 * @return
	 */
	public static String getLen(String text, int needlen) {
		if (text != null) {
			int len;
			try {
				len = text.getBytes("utf-8").length;
				System.out.println(len);
				String lenStr = String.valueOf(len);
				System.out.println(lenStr);
				StringBuffer sb = new StringBuffer(lenStr);
				System.out.println(sb.length());
				while (sb.length() < needlen) {
					sb.insert(0, "0");
				}
				return sb.toString();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				log.error("---计算报文长度--------"+e.getMessage(),e.getCause());
			}
		}
		return null;
	}
	/**
	 * 记录
	 * @param sent 发送的消息
	 * @param rec 接收的消息
	 * @param classname 操作的类功能详解
	 */
	public static void inputtable(String sent,String rec,String classname ){
		System.out.println("服务端收到的数据=" + rec);
		java.util.Map<?, ?> map=JSONUtils.fromJson(rec);
		String id = classname;
		String bodyMapjson="";
		String headMapjson="";
		try {
			Object bodyMap =map.get("body");
			Object headMap=  map.get("head");
			bodyMapjson="bodyMap:"+JSONUtils.toJson(bodyMap);
			headMapjson="headMap:"+JSONUtils.toJson(headMap);
			Lc.set("body", bodyMapjson);
			Lc.set("head", headMapjson);
		} catch (Exception e) {
			log.error("返回值没有body或者head");
		}
		try {
			int num = Lc.getConn().createExecSql("insert into funtionlog  (id,updatetime,sentmessage,recmessage,body,head) values (?,?,?,?,?,?)", id,DateUtil.getLongDate(),sent,rec,bodyMapjson,headMapjson);
			if (0<num) {
				log.info("成功存入数据库表funtionlog中");
			}
		} catch (Exception e) {
			log.error("存入数据库表funtionlog中出错");
			e.getMessage();
		}
	}
public static void main(String[] args) {
	  String  res="{\"body\":{\"fileType\":\"DZZD\",\"extend\":\"\",\"filePath\":\"/download\",\"fileName\":\"WTHNXXSHOUCHUANG_DZZD_20160907.txt\",\"acctOrgNo\":\"36420\"},\"head\":{\"source\":\"WTHNXXSHOUCHUANG\",\"extend\":\"\",\"msgId\":\"20411023048203984092380w42ghf\","
				+ "\"msgTime\":\"20160912092945\",\"desIfno\":\"LONGSHINE\",\"servCode\":\"100001\",\"version\":\"1.0.1\"}}";
	  try {
		  Composrequesutil.sendMessage(res,"100001","classfunction");
		} catch (FileNotFoundException e) {
			log.error("---计算报文长度--------"+e.getMessage(),e.getCause());
			e.printStackTrace();
		}
}
}
