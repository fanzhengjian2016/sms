package com.plugins.mina.longshine;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.shenjun.util.ByteUtil;

public class Encoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		Message msg =(Message)message;
		
		byte[] data=(msg.getData()+"").getBytes("UTF-8");
		IoBuffer buff = IoBuffer.allocate(1+4+6+data.length+1);
		buff.put(Integer.valueOf("68", 16).byteValue());
		buff.put(ByteUtil.intToByte(data.length));
		buff.put(msg.getCode().getBytes());
		buff.put(data);
		buff.put(Integer.valueOf("16", 16).byteValue());
		buff.flip();
		out.write(buff);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		System.out.println("kk:"+ByteUtil.bytes2Hex(new byte[]{-128,-127,100,127,(byte)255}));
		System.out.println("kk:"+new Integer(255).byteValue());
		System.out.println("kk:"+ByteUtil.bytes2Hex(ByteUtil.intToByte(258)));
		System.out.println("kk:"+ByteUtil.bytes2Hex(ByteUtil.intToByte(-128)));
		System.out.println("kk:"+ByteUtil.byteToInt(ByteUtil.intToByte(-1)));
		System.out.println("kk:"+ByteUtil.byteToInt(ByteUtil.hex2byte(ByteUtil.bytes2Hex(ByteUtil.intToByte(-128)))));
	}
}
