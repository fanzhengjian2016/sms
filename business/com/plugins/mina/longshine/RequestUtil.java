package com.plugins.mina.longshine;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.plugins.mina.ssl.SSLContextGenerator;
import com.shenjun.util.ByteUtil;
import com.shenjun.util.ReadProperties;
import com.sun.mail.handlers.message_rfc822;

/**
 * 用户请求LongShine服务器类
 * @author shenjun
 *
 */
public class RequestUtil {
	
	private static  Log log = LogFactory.getLog(RequestUtil.class);
	
	/*public final static String IP=ReadProperties.getProperty("longshine.socketip");
	public final static String PORT=ReadProperties.getProperty("longshine.socketport");
	public final static String SSL_KEY=ReadProperties.getProperty("longshine.sslkey");*/
	
	//新乡
	/*
	public final static String IP="219.150.220.46";
	public final static String PORT="8888";*/
	
	public final static String IP="111.204.143.230";
	public final static String PORT="8099";
	
	public final static String SSL_KEY=ReadProperties.getProperty("longshine.sslkey");
	
	public static Message sendMessage(Message msg){
        IoConnector connector = new NioSocketConnector();
        connector.getSessionConfig().setReadBufferSize(2048);
        connector.getSessionConfig().setUseReadOperation(true);
        
        Message res=null;
        
        SslFilter sslFilter = new SslFilter(SSLContextGenerator.getSslContext(SSL_KEY));
        sslFilter.setUseClientMode(true);
        connector.getFilterChain().addFirst("sslFilter", sslFilter);

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JxCodeFactory()));

        try{
	        ConnectFuture future = connector.connect(new InetSocketAddress(IP, Integer.valueOf(PORT))).awaitUninterruptibly();
	        
	       // IoSession aIoSession=future.getSession();
	        System.out.println("55555 ="+IP+"="+PORT);
	        //System.out.println("55555 ="+IP+"="+PORT+"="+"=="+aIoSession.getLocalAddress()+"=====55555555555555555555555 ==="+aIoSession.getWrittenMessages());	
	        log.info("===============session链接：ip="+IP+"; port:"+PORT);
	        
	        if (!future.isConnected())
	        {
	        	log.error("RequestUtil.sendMessage无法连接");
	            return null;
	        }
	        IoSession session = future.getSession();
	        // 发送
	        session.write(msg).awaitUninterruptibly(100);
	        // 接收
	        ReadFuture readFuture = session.read();
	        if (readFuture.awaitUninterruptibly(30, TimeUnit.SECONDS)) {
	        	res = (Message) readFuture.getMessage();
	        	log.info("===============回执：ip="+IP+"; port:"+PORT);
	        } else {
	            // 读超时
	        	log.error("RequestUtil.sendMessage 接收超时。");
	        }
	        session.close(true);
        }catch(Exception e){
        	log.error("sendMessage error:"+e.getMessage(), e.getCause());
        }
        connector.dispose();
        return res;
	}
	
	public static void main(String[] args) {
		
		  String one1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201203\",\"queryValue\":\"103354\",\"busiType\":\"11\",\"bgnYm\":\"201203\",\"acctOrgNo\":\"36475\"}}";
		//String aa="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200001\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201203\",\"queryValue\":\"2613013\",\"busiType\":\"11\",\"bgnYm\":\"201203\",\"acctOrgNo\":\"36420\"}}";
    	
		//System.out.println(Integer.valueOf("68", 16).byteValue());
		
		//System.out.println(ByteUtil.bytes2Hex(new byte[]{104}));
		
		Message res=RequestUtil.sendMessage(new Message("200001", one1));
		System.out.println("code:"+res.getCode()+",data:"+res.getData());
	}
	
	
}
