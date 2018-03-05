package com.shenjun.plugins.sftp;

import java.util.List;

import com.jcraft.jsch.ChannelSftp;

public class SftpTest {
	public static void main(String[] args) throws Exception {
		ConnectConfig c=new ConnectConfig();
		c.setHost("192.168.1.2");
		c.setUserName("Administrator");
		c.setPassWord("guodedc");
		
		
		
		SftpLocal sftpUtils = new SftpLocal(c);
		
		sftpUtils.uploadFile("C:/MyFile/disk/b/a.txt", "/a/sftp.txt", ChannelSftp.OVERWRITE);
		
		List<String> dirs = sftpUtils.listFiles("/a/");
		for(String dir:dirs){
			System.out.println(dir);
		}
		
		sftpUtils.downFile("/", "Server.txt", "C:/MyFile/disk/b/a.txt");
		
		sftpUtils.closeChannel();
	}
}
