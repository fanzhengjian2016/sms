package com.plugins.mina.longshine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.shenjun.util.ByteUtil;

public class Decoder implements ProtocolDecoder {
	
	//private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	protected static Log log = LogFactory.getLog(Decoder.class);
	
	public byte[] getByte(IoBuffer in,int len){
		byte[] b = new byte[len];
		in.get(b);
		return b;
	}
	
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("decode data:"+"-------"+new String(in.array()));
		log.info("decode data2:"+new String(in.array()));
		
		
		//in.position(in.position()-1);
		//in.limit();
		
		byte b68=in.get();
		
		log.info("decode b68十六进制显示:"+ByteUtil.bytes2Hex(new byte[]{b68})+",byte:"+b68);
		
		if(b68==Integer.valueOf("68", 16).byteValue()){
			byte[]barr_len=this.getByte(in, 4);
			int len = ByteUtil.byteToInt(barr_len);
			String code = new String(this.getByte(in, 6));
			String data = new String(this.getByte(in, len),"UTF-8");
			
			log.info("decode code:"+code+",data:"+data+",len:"+len+",barr_len十六进制:"+ByteUtil.bytes2Hex(barr_len));
			
			out.write(new Message(code, data));
			
			byte b16=in.get();
			log.info("decode b16十六进制显示:"+ByteUtil.bytes2Hex(new byte[]{b16})+",byte:"+b16);
			
			if(b16==Integer.valueOf("16", 16).byteValue()){
				this.finishDecode(session, out);
			}
		}else{
			byte[]barr_len=this.getByte(in, 4);
			int len = ByteUtil.byteToInt(barr_len);
			String code = new String(this.getByte(in, 6));
			String data = new String(this.getByte(in, len),"UTF-8");
			
			log.info("decode code:"+code+",data:"+data+",len:"+len+",barr_len十六进制:"+ByteUtil.bytes2Hex(barr_len));
			
			out.write(new Message(code, data));
			
			byte b16=in.get();
			log.info("decode b16十六进制显示:"+ByteUtil.bytes2Hex(new byte[]{b16})+",byte:"+b16);
			
			if(b16==Integer.valueOf("16", 16).byteValue()){
				this.finishDecode(session, out);
			}
		}
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub		
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub
	}
	
	
}
