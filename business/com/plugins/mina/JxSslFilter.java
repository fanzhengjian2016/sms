package com.plugins.mina;

import javax.net.ssl.SSLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;

import com.plugins.mina.longshine.Decoder;
import com.plugins.mina.ssl.SSLContextGenerator;

public class JxSslFilter extends SslFilter {
	
	protected static Log log = LogFactory.getLog(JxSslFilter.class);
	
	@Override
	public void exceptionCaught(NextFilter nextFilter, IoSession session,
			Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(nextFilter, session, cause);
	}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws SSLException {
		// TODO Auto-generated method stub
		super.messageReceived(nextFilter, session, message);
		System.out.println("------------JxSslFilter--messageReceived------------------");
		log.info("------------JxSslFilter--messageReceived------------------");
	}

	public JxSslFilter() {
		//super(SSLContextGenerator.getSslContext());
		super(SSLContextGenerator.getSingleSslContext());
		//super(SSLContextGenerator.getSingleSslContext());
	}
}
