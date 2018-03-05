package com.shenjun.io;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.util.ReadProperties;

/**
 * @author: 沈军
 * @category 写访问日志
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class WriteVisitingLog {
	
	private static  Log log = LogFactory.getLog(WriteVisitingLog.class);
	
	public static void setMsg(String str){
		String url = ReadProperties.getProperty("LogUrl");
		
		try {
			if(new File(url).mkdirs())
				log.info("成功创建文件夹:"+url);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			File file = new File(url+"Vis("+sdf.format(new Date())+").log");
				
			FileWriter out = new FileWriter(file,true);
			
			if(!file.exists())
				file.createNewFile();

			out.append("************************************************************************\n");
			out.write(str); 
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
