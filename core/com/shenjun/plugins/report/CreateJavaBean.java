package com.shenjun.plugins.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.util.ReadProperties;
import com.shenjun.util.WebUtil;

/**
 * @author: 沈军
 * @category 自动创建Java类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class CreateJavaBean  {
	
	
	private static Log log = LogFactory.getLog(CreateJavaBean.class);
	
	public synchronized static File createFile(Object[] objType , Object[] objName ){
		File file = null;
		try{
			String fileUrl =WebUtil.getClassesRoot()+"net/shenjun/temp/reportbean/";
			File fileTemp =new File(fileUrl);
			fileTemp.mkdirs();
			file = File.createTempFile("ReportBean", ".java", fileTemp);
			
		    String filename = file.getName();
	        String classname = getClassName(filename);
	        
	        PrintWriter out = new PrintWriter(new FileOutputStream(file));
	        //代码生成部分
			StringBuilder code = new StringBuilder();
			code.append("package net.shenjun.temp.reportbean;\n ");
			code.append("import java.io.Serializable;\n");
			code.append("public class "+classname+" implements Serializable {\n");
			code.append("private static final long serialVersionUID = 1L;\n");
			for (int i = 0; i < objType.length ; i++) {
				
				String type = objType[i].getClass().toString().split(" ")[1];
				
				code.append("\tprivate "+type+" "+objName[i]+";\n");
				
				code.append("\tpublic void set"+objName[i].toString().substring(0,1).toUpperCase()+objName[i].toString().substring(1));
				code.append("("+type+" "+objName[i].toString()+"){this."+objName[i].toString()+"="+objName[i].toString()+";}\n");
				
				code.append("\tpublic "+type+" get"+objName[i].toString().substring(0,1).toUpperCase()+objName[i].toString().substring(1));
				code.append("(){return this."+objName[i].toString()+";}\n");
			}
			code.append("}");
			
			out.write(code.toString());
	  
	        out.flush();
	        out.close();
	        String[] args = new String[]{fileTemp.getAbsolutePath()+"/"+filename};
	        int status = 0;//Main.compile(args);

	        log.debug("\njava文件创建在:"+fileTemp.getAbsolutePath()+"\\"+filename+"\n"+"是否成功(0->成功,1->失败):"+status+"\n代码如下:\n"+code.toString()+"\n");
		}catch(Exception ex){
			log.error("创建Java文件出错:"+ex.getMessage());
		}
        return file;
	}
	
	private static String getClassName(String filename) {
	       return filename.substring(0, filename.length() - 5);
	}
	
		public static void main(String[] args) throws Exception {
		Object[] objType = new Object[]{"ddd","ddd","ddd"};
		Object[] objName = new Object[]{"name","sex","address"};
		createFile(objType, objName);	
		
	}
}

