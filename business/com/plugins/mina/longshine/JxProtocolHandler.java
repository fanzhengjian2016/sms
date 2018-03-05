package com.plugins.mina.longshine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.plugins.waterfee.WaterFreeServiceManager;
import com.shenjun.collection.SuperList;
import com.shenjun.db.type.SqlType;
import com.shenjun.db.type.SqlValue;
import com.shenjun.enums.HttpRespEnum;
import com.shenjun.enums.SearchEnum;
import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.util.ClassUtil;
import com.shenjun.util.JSONUtils;
import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;
import com.shenjun.web.thread.Lc;

public class JxProtocolHandler extends IoHandlerAdapter {
	
	protected static Log log = LogFactory.getLog(JxProtocolHandler.class);
	
	private static Map<String,String> mapCode = null;
	private static final String execaddress=ReadProperties.getProperty("execaddress");
	private static final String execaddressurl=ReadProperties.getProperty("execaddressurl");
	
	public static String getMethod(String key){
		if(mapCode==null){
			mapCode=new HashMap<String,String>();
			
			mapCode.put("200001", "queryWaterFee");
			mapCode.put("200002", "payWaterFee");
			mapCode.put("200003", "queryUserInfo");
			mapCode.put("200004", "bindUser");
			mapCode.put("200005", "queryBill");
			mapCode.put("200006", "queryContact");
			mapCode.put("200014", "queryBalance");
			
			mapCode.put("200008", "queryPayFeeRecord");
			mapCode.put("200009", "signContact");
			//mapCode.put("200010", "queryWaterFee");
			mapCode.put("200011", "receiveFile");
			mapCode.put("200012", "queryPayWaterFee");
			
			mapCode.put("200013", "queryWaterFee");
			mapCode.put("200014", "queryWaterFee");
			mapCode.put("200015", "queryWaterFee");
			mapCode.put("200016", "queryWaterFee");
			mapCode.put("200017", "queryWaterFee");
			mapCode.put("200018", "queryWaterFee");
			
			mapCode.put("200019", "receiveFile");
		}
		
		return mapCode.get(key);
	}
	
	public static String execMethod(String method,String data){
		Method m = ClassUtil.getMethod(method, WaterFreeServiceManager.class, SearchEnum.NOT_DIS_UPPER_LOWER);
		
		WaterFreeServiceManager wsm=SpringUtil.getBean(WaterFreeServiceManager.class);
		try {
			return m.invoke(wsm, data)+"";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("exec "+method+" is error;"+e.getMessage(), e.getCause());
			
		}
		
		return data;
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-----------------sessionCreated-----------------------");
		log.info("-----------------sessionCreated-----------------------");
		super.sessionCreated(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-----------------sessionClosed-----------------------");
		log.info("-----------------sessionClosed-----------------------");
		super.sessionClosed(session);
	}

	public void messageReceived(IoSession session, Object message)throws Exception {
		Message msg = (Message)message;
		System.out.println("---Mina服务器接收到---------|"+msg.getCode()+msg.getData()+"|----------------");
		log.info("socket入参:---Mina服务器接收到---------|"+msg.getCode()+","+msg.getData()+"|----------------");
		/*
		String servCode=msg.getCode();
		String recievemessage=msg.getData();*/
	
		String resultStr="";
		
		if("native".equals(execaddress)){
			resultStr=JxProtocolHandler.execMethod(JxProtocolHandler.getMethod(msg.getCode()),msg.getData());
		}else{
			//远程remote
			
			//如果是对账
			if("200011".equals(msg.getCode())||"200019".equals(msg.getCode())){
				//先下载到本地的sftp服务器
				JxProtocolHandler.execMethod(JxProtocolHandler.getMethod(msg.getCode()),msg.getData());
			}
			
			Object object=WebUtil.httpSend(execaddressurl+"/system/remote.action?method=getData", HttpRespEnum.JSON, null, new Object[]{
					"execmethod",JxProtocolHandler.getMethod(msg.getCode()),
					"execdata",msg.getData()
			});
			
			if(object==null){
				resultStr=msg.getData();
			}else{
				resultStr=JSONUtils.toJson(object);
			}
		}
		
		
		
		
		/*
		
		if("200001".equals(servCode)){
			//欠费查询
			resultStr=wsm.queryWaterFee(recievemessage);
			
		}else if("200002".equals(servCode)){
			//代收缴费
			resultStr=wsm.payWaterFee(recievemessage);
			
		}else if("200003".equals(servCode)){
			//用户信息查询
			resultStr=wsm.queryUserInfo(recievemessage);
			
		}else if("200004".equals(servCode)){
			//用户绑定通知
			resultStr=wsm.bindUser(recievemessage);
			
		}else if("200005".equals(servCode)){
			//账单实时查询
			resultStr=wsm.queryBill(recievemessage);
			
		}else if("200006".equals(servCode)){
			//用户缴费协议查询
			resultStr=wsm.queryContact(recievemessage);
			
		}else if("200014".equals(servCode)){
			//余额查询
			resultStr=wsm.queryBalance(recievemessage);
			
		}else if("200008".equals(servCode)){
			//缴费记录查询
			resultStr=wsm.queryPayFeeRecord(recievemessage);
		}else if("200009".equals(servCode)){
			//代扣协议维护
			
			resultStr=wsm.signContact(recievemessage);
		}else if("200010".equals(servCode)){
			//采集信息查询
			
			
		}else if("200011".equals(servCode)){
			//机构接收文本服务（对账）
			resultStr=wsm.receiveFile(recievemessage);
			
		}else if("200012".equals(servCode)){
			//缴费单销账结果查询
			resultStr=wsm.queryPayWaterFee(recievemessage);
			
		}else if("200013".equals(servCode)){
			//购电下发状态查询
			
			
		}else if("200015".equals(servCode)){
			//用户表号查询
			
			
		}else if("200016".equals(servCode)){
			//自抄表上传状态查询
			
			
		}else if("200017".equals(servCode)){
			//自抄表上传
			
			
		}else if("200018".equals(servCode)){
			//历史抄表记录查询
			
			
		}else if("200019".equals(servCode)){
			//机构接收文本服务（代扣）
			resultStr=wsm.receiveFile(recievemessage);
			
		}*/
		
		
	
		//String resultStr2=resultStr;//如果需要加密,在这边加密
		//session.write(resultStr2);
		log.info("socket出参:---Mina服务器接收到---------|"+msg.getCode()+","+resultStr+"|----------------");
		msg.setData(resultStr);
		session.write(msg);
		super.messageReceived(session, message);
	}
	
	public static void main(String[] args) {
		
		/*
		Object object=WebUtil.httpSend(execaddressurl+"/system/remote.action?method=getTestData", HttpRespEnum.JSON, null, new Object[]{
				"execmethod","queryUserInfo"
		});
		Map map=(Map)object;
		System.out.println(object+"--->>"+map.get("name"));*/
		
		
		String aa="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTHAINING\",\"servCode\":\"200001\",\"msgId\":\"helloshengyanhui0001\",\"msgTime\":\"20140708162509\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\"0\",\"endYm\":\"201203\",\"queryValue\":\"8507145\",\"busiType\":\"11\",\"bgnYm\":\"201203\",\"acctOrgNo\":\"zs\"}}";
    	
		Object object=WebUtil.httpSend(execaddressurl+"/system/remote.action?method=getData", HttpRespEnum.JSON, null, new Object[]{
				"execmethod","queryWaterFee",
				"execdata",aa
		});
		
		if(object==null){
			System.out.println("object is null");
			return ;
		}
		Map map=(Map)object;
		System.out.println(object+"--->>"+map.get("head"));
		System.out.println(JSONUtils.toJson(object));
		
		
		//Object[]arr=Lc.getConn().createCall("SpQueryMisClient", new Object[]{"10.1.8.156",0},new Integer[]{SqlType.INTEGER,SqlType.VARCHAR});
		//SuperList sl=Lc.getConn().createCall("SpQuerylist", new Object[]{"10.1.8.156",0});
		
	
	}
}
