package com.shenjun.io.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class ZipStreamTools {
	
	private static  Log log = LogFactory.getLog(ZipStreamTools.class);
	/**
	 * 压缩
	 * @param b
	 * @return
	 */
	public static byte[] textToGZip(byte[] b){
		byte[] b2 = null;
		try {
			//BufferedInputStream in = new BufferedInputStream( new FileInputStream(text));
			//System.out.println("压缩前:\n" + new String(b));
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			GZIPOutputStream out = new GZIPOutputStream(bout);
		    ObjectOutputStream oout = new ObjectOutputStream(out);
		    // 读取源文件
//		    b1 = new byte[in.available()];  
//		    in.read(b1,0,b1.length);
		    
//		    b1=text.getBytes();
		    
		    
		            
		    // 压缩
		    //out.write(b1,0, b1.length);
		    oout.writeObject(new String(b));
		    oout.flush();
		    oout.close();
		    // 读取压缩字节流
		    b2 = bout.toByteArray();
		      
		    //System.out.println("压缩后:\n" + new String(b2));
		    
		} catch (IOException e) {
			log.error("textToGZip "+e.getMessage(), e.getCause());
			return b;
		}
		return b2;
	}
	/**
	 * 解压
	 * @param b
	 * @return
	 */
	public static byte[] gZipToText(byte[] b){
	    ObjectInputStream in;
	    byte[] b2 =null;
		try {
			//System.out.println("解缩前:\n" + new String(b));
			in = new ObjectInputStream(new GZIPInputStream(
					  new ByteArrayInputStream(b)));
			b2 = ((String)in.readObject()).getBytes();
		    in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("gZipToText "+e.getMessage(), e.getCause());
			return b;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("gZipToText "+e.getMessage(), e.getCause());
		}
		//System.out.println("解缩后:\n" + new String(b2));
		return b2;
	}
	
	public static void main(String[] args) {
		
		String xml="<smart><data><arrays name='tasklistarray'><bean taskid='140' tasktype='1' taskdec='sdas' copier='27' bookno='00102' bookdec='解放路' dldate='2013-01-07 15:16:22.217' uldate='2013-01-15 00:00:00.0' usercount='1036' copycyc='单月抄表' /></arrays></data></smart>";
		byte[] s=ZipStreamTools.gZipToText(
				ZipStreamTools.textToGZip(xml.getBytes())
		);
		
		System.out.println(new String(ZipStreamTools.textToGZip(xml.getBytes())
				).length());
		System.out.println(new String(s).length());
	}
}
