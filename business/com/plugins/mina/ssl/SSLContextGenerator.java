package com.plugins.mina.ssl;

import java.io.File;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.filter.ssl.KeyStoreFactory;
import org.apache.mina.filter.ssl.SslContextFactory;

import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;

public class SSLContextGenerator {
	
	private static SSLContext ssl = null;
	
	private final static String SSL_DEFAULT_NAME=ReadProperties.getProperty("ssl","ssl.defaultname");
	
	private static  Log log = LogFactory.getLog(SSLContextGenerator.class);
	
	public static SSLContext getSslContext(String name){
		if(ssl==null){
			ssl=SSLContextGenerator.initSslContext(name);
		}
		return ssl;
	}
	
	public static SSLContext getSslContext(){
		return SSLContextGenerator.getSslContext(SSL_DEFAULT_NAME);
	}
	
	public static SSLContext getSingleSslContext(String name){
		return SSLContextGenerator.initSslContext(name);
	}
	
	public static SSLContext getSingleSslContext(){
		return getSingleSslContext(SSL_DEFAULT_NAME);
	}
	
    /**
     * 这个方法，通过keystore和truststore文件返回一个SSLContext对象
     * 
     * @return
     */
    private static SSLContext initSslContext(String name) {
        SSLContext sslContext = null;
        try {
        	String keystorepath=WebUtil.getClassesRoot()+ReadProperties.getProperty("ssl", name+".keystore");
        	String trustStorepath=WebUtil.getClassesRoot()+ReadProperties.getProperty("ssl", name+".truststore");
        	
        	String keystorepwd=ReadProperties.getProperty("ssl", name+".keystorepwd");
        	String truststorepwd=ReadProperties.getProperty("ssl", name+".truststorepwd");
        	
        	log.info("load ssl("+name+") keystore:"+keystorepath+",truststore:"+trustStorepath);
        	
            /*
             * 提供keystore的存放目录，读取keystore的文件内容
             */
            File keyStoreFile = new File(keystorepath);//("C:/Users/Lambert/ssl/server.keystore");

            /*
             * 提供truststore的存放目录，读取truststore的文件内容
             */
            File trustStoreFile = new File(trustStorepath);//("C:/Users/Lambert/ssl/client.truststore");
            

            if (keyStoreFile.exists() && trustStoreFile.exists()) {
                final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
                log.info("Url is: " + keyStoreFile.getAbsolutePath());
                keyStoreFactory.setDataFile(keyStoreFile);
                keyStoreFactory.setPassword(keystorepwd);

                final KeyStoreFactory trustStoreFactory = new KeyStoreFactory();
                trustStoreFactory.setDataFile(trustStoreFile);
                trustStoreFactory.setPassword(truststorepwd);

                final SslContextFactory sslContextFactory = new SslContextFactory();
                final KeyStore keyStore = keyStoreFactory.newInstance();
                sslContextFactory.setKeyManagerFactoryKeyStore(keyStore);
                
                sslContextFactory.setKeyManagerFactoryAlgorithm("SunX509");
                sslContextFactory.setTrustManagerFactoryAlgorithm("SunX509");

                final KeyStore trustStore = trustStoreFactory.newInstance();
                sslContextFactory.setTrustManagerFactoryKeyStore(trustStore);
                sslContextFactory.setKeyManagerFactoryKeyStorePassword(keystorepwd);
                sslContext = sslContextFactory.newInstance();
                
                
                
                log.info("SSL provider is: "+ sslContext.getProvider()+",Protocol:"+sslContext.getProtocol());
            } else {
                log.info("Keystore or Truststore file does not exist");
            }
        } catch (Exception ex) {
            log.error("SSLContextGenerator error:"+ex.getMessage(),ex.getCause());
        }
        return sslContext;
    }
    
    public static void main(String[] args) {
    	//System.out.println(ReadProperties.getProperty("SystemName"));
		SSLContextGenerator.getSslContext();
	}
}