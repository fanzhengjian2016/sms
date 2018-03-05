package com.shenjun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 操作序列化文件类
 * @author: 沈军
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class SerializableUtil{
	private static Log log = LogFactory.getLog(SerializableUtil.class);
	
	/**
	 * 将implements java.io.Serializable的类保存到文件中
	 * @param obj implements java.io.Serializable 的CLass实例化对象
	 * @param path 已包含WebUtil.getClassesRoot()+？只需要写？号后台的路径
	 * @param SerializableName 序列化文件的名称
	 */
	public synchronized static void save(Object obj,String path,String SerializableName){
		try {
			String fileUrl =WebUtil.getClassesRoot()+path;
			File fileTemp =new File(fileUrl);
			fileTemp.mkdirs();
			
			File f= new File(fileUrl+SerializableName);
			f.createNewFile();
			
			log.info("正在序列化文件:"+f.getAbsoluteFile());
			
			//构建写入流
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(
							f
							)
					);
			os.writeObject(obj);
			os.flush();
			os.close();
			
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException "+e.getMessage(),e.getCause());
		} catch (IOException e) {
			log.error("IOException:"+e.getMessage(),e.getCause());
		} catch (Exception e){
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 将序列化的文件读取成对象类型
	 * @param path 已包含WebUtil.getClassesRoot()+？只需要写？号后台的路径
	 * @param fileName 序列化的文件名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized static <T> T get(String path,String fileName){
		Object obj = null;
		File sFile = null;
		try {
			String fileUrl =WebUtil.getClassesRoot()+path;
			
			sFile=new File(fileUrl+fileName);
			log.info("获取序列化文件:"+sFile.getAbsoluteFile());
			//构建输出流
			ObjectInputStream ois = 
			    new ObjectInputStream(new FileInputStream(
			    		sFile
			    		)
			    );

			obj = ois.readObject();
			
			ois.close();
			
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException "+e.getMessage(),e.getCause());
		} catch (IOException e) {
			log.error("IOException:"+e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException:"+e.getMessage());
		}catch (Exception e){
			log.error(e.getMessage());
		}
		return (T)obj;
	}
	
}
