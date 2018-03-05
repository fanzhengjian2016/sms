package com.web.system.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.plugins.waterfee.util.CryptoUtils;
import com.plugins.waterfee.util.TestPro;
import com.shenjun.annotation.ReturnType;
import com.shenjun.enums.ReturnEnum;
import com.shenjun.web.struts.SAction;
import com.shenjun.web.thread.Lc;

@ReturnType(ReturnEnum.AJAX_TYPE)
public class TestAction extends SAction{

	private static final long serialVersionUID = 1L;
	private final static String key="longshinetozhongshanwtzs";
	public String query() throws Exception{
		String retult="";
		TestPro testpro=new TestPro();
		String select =Lc.get("select");
		switch (Integer.parseInt(select)) {
		case 1:
			String queryType=Lc.get("queryType");
			String time=Lc.get("queryTime");
			String queryValue=Lc.get("queryValue");
			//欠费查询请求参数
			String one1="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"queryType\":\""+queryType+"\",\"endYm\":\""+time+"\",\"queryValue\":\""+queryValue+"\",\"busiType\":\"11\",\"bgnYm\":\""+time+"\",\"acctOrgNo\":\"zs\"}}";
			String one2=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), one1.getBytes()));
			String one3=testpro.startQueryWaterFee2(one2);
			
			retult=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(one3)),"gb2312");
			System.out.println(retult);
			break;
		case 2:
			//交易金额
			String rcvAmt=Lc.get("rcvAmt");
			//户号
			String consNo=Lc.get("consNo");
			//应收年月
			String rcvblYm=Lc.get("rcvblYm");
			//应收标识
			String rcvblAmtId=Lc.get("rcvblAmtId");
			//缴费请求参数
			String one5="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"extend\":\"\",\"rcvAmt\":\""+rcvAmt+"\",\"bankSerial\":\"090909091111"+((int)(Math.random()*9000+1000))+"\",\"rcvDet\":[{\"rcvblYm\":\""+rcvblYm+"\",\"rcvblAmtId\":\""+rcvblAmtId+"\",\"consNo\":\""+consNo+"\"}],\"chargeCnt\":\"1\",\"payMode\":\"WX\",\"capitalNo\":\"\",\"bankDate\":\""+new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date())+"\",\"acctOrgNo\":\"zs\"}}";

			String one6=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), one5.getBytes()));
			String one7=testpro.startPayWaterFee(one6);
			
			retult=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(one7)),"gb2312");
			System.out.println(retult);
			break;
		case 3:
			String fileType =Lc.get("fileType");
			String one9="{\"head\":{\"version\":\"1.0.1\",\"source\":\"LONGSHINE\",\"desIfno\":\"WTZHONGSHAN\",\"servCode\":\"200001\",\"msgId\":\"7eaf45cd8224ce33bb7016930b1a3533\",\"msgTime\":\"20110617204209\",\"extend\":\"\"},\"body\":{\"fileType\":\""+fileType+"\",\"extend\":\"\",\"fileName\":\"LONGSHINE_ZHONGSHAN_DSDZ_20140929_zs.txt\",\"filePath\":\"/upload/\",\"acctOrgNo\":\"zs\"}}";

			String one10=CryptoUtils.encodeTo64(CryptoUtils.encodeECB(key.getBytes(), one9.getBytes()));
			String one11=testpro.startReceiveFile(one10);
			
			retult=new String(CryptoUtils.decodeECB(key.getBytes(), CryptoUtils.decodeTo64(one11)),"gb2312");
			System.out.println(retult);
			break;

		default:
			break;
		}
		return retult;
	}
}
