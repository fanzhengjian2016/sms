package com.shenjun.plugins.sftp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SftpLocal {
	
	private final static Log log = LogFactory.getLog(SftpLocal.class);
	
	public static final String SFTP = "sftp";
	
	private ChannelSftp channel;
	
	private Session session;
	
	private ConnectConfig connConfig;
	
	public SftpLocal(ConnectConfig connConfig){
		this.connConfig=connConfig;
		
		this.initConn();
	}
	
	/**
	 * 关闭通道
	 * 
	 * @throws Exception
	 */
	public void closeChannel() {
		if (null != channel) {
			try {
				channel.disconnect();
			} catch (Exception e) {
				log.error("关闭SFTP通道发生异常:", e);
			}
		}
		if (null != session) {
			try {
				session.disconnect();
			} catch (Exception e) {
				log.error("SFTP关闭 session异常:", e);
			}
		}
	}
	
	/**
	 * 释放资源
	 */
	public void sftpRelease() {
		
	}
	
	/**
	 * 是否已连接
	 * 
	 * @return
	 */
	private boolean isConnected() {
		return null != channel && channel.isConnected();
	}
	
	/**
	 * 初始化连接
	 * @return
	 */
	public int initConn(){
		if(isConnected()){
			return 0;
		}
		
		String host = connConfig.getHost();
		int port = connConfig.getPort();

		String userName = connConfig.getUserName();

		// 创建JSch对象
		JSch jsch = new JSch();
		
		//建立while 可以实现错误重新连接。
		try{
			// 添加私钥(信任登录方式)
			if (StringUtils.isNotBlank(connConfig.getPrivateKey())) {
				jsch.addIdentity(connConfig.getPrivateKey());
			}
			session = jsch.getSession(userName, host, port);
			if (log.isInfoEnabled()) {
				log.info(" JSCH Session created,sftpHost = " + host
						+ ", sftpUserName=" + userName);
			}
			// 设置密码
			if (StringUtils.isNotBlank(connConfig.getPassWord())) {
				session.setPassword(connConfig.getPassWord());
			}
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			// 设置超时
			session.setTimeout(connConfig.getTimeout());
			
			// 建立连接
			session.connect();
			if (log.isInfoEnabled()) {
				log.info("JSCH Session connected.sftpHost = " + host
						+ ", sftpUserName=" + userName);
			}else{
				log.info("JSCH Session connect fail.sftpHost = " + host
						+ ", sftpUserName=" + userName);
				
				return -1;
			}

			// 打开SFTP通道
			channel = (ChannelSftp) session.openChannel(SFTP);
			// 建立SFTP通道的连接
			channel.connect();
			if (log.isInfoEnabled()) {
				log.info("Connected successfully to sftpHost = " + host
						+ ", sftpUserName=" + userName);
			}else{
				log.info("Connect fail to sftpHost = " + host
						+ ", sftpUserName=" + userName);
				return -2;
			}
			
		}catch(JSchException jsch_ex){
			log.error("SftpLocal JSchException 初始化连接错误。"+jsch_ex.getMessage(),jsch_ex);
		}
		
		return 1;
	}
	
	/**
	 * 下载文件
	 * @param downDir 远程目录
	 * @param src 源文件名
	 * @param dst 保存在本地的目录与地址
	 * @throws Exception
	 */
	public void downFile(String downDir, String src, String dst)
			throws Exception {
		channel.cd(downDir);
		channel.get(src, dst);
	}
	
	/**
	 * 删除文件
	 * @param filePath 文件路径
	 * @throws SftpException
	 */
	public void deleteFile(String filePath) throws SftpException {
		channel.rm(filePath);
	}
	
	/**
	 * 
	 * @param src 源文件地址
	 * @param dst 目录地址
	 * @param mode 上传方法 如：ChannelSftp.OVERWRITE
	 */
	public int uploadFile(String src,String dst,int mode){
		try {
			channel.put(src, dst, mode);//ChannelSftp.OVERWRITE);
		} catch (SftpException e) {
			log.error("SFTP上传文件异常【src:"+src+",dst:"+dst+",mode:"+mode+",】:"+e.getMessage(), e);
			return -1;
		}
		return 0;
	}
	
	/**
	 * 显示文件路径
	 * @param dir 远程目录
	 * @return
	 * @throws SftpException
	 */
	public List<String> listFiles(String dir) throws SftpException {
		@SuppressWarnings("unchecked")
		Vector<LsEntry> files = channel.ls(dir);
		if (null != files) {
			List<String> fileNames = new ArrayList<String>();
			Iterator<LsEntry> iter = files.iterator();
			while (iter.hasNext()) {
				String fileName = iter.next().getFilename();
				if (StringUtils.equals(".", fileName)
						|| StringUtils.equals("..", fileName)) {
					continue;
				}
				fileNames.add(fileName);
			}
			return fileNames;
		}
		return null;
	}

}
