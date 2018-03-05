package com.plugins.mina.longshine;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class JxCodeFactory implements ProtocolCodecFactory  {
	
	private final Encoder encoder;
	
	private final Decoder decoder;
	
	public JxCodeFactory(){
    	this(Charset.forName("UTF-8"));
    }
	
	public JxCodeFactory(Charset charset){
		encoder = new Encoder();
	    decoder = new Decoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}
	

//	private final TextLineEncoder encoder;
//	private final TextLineDecoder decoder;
//    
//    public JxCodeFactory(){
//    	this(Charset.forName("UTF-8"));
//    }
//    
//    public JxCodeFactory(Charset charset){
//    	encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
//        decoder = new TextLineDecoder(charset, LineDelimiter.AUTO);
//    }
//    
//	@Override
//	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
//		// TODO Auto-generated method stub
//		return encoder;
//	}
//
//	@Override
//	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
//		// TODO Auto-generated method stub
//		return decoder;
//	}

}
