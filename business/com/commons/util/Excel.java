package com.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Excel {

    /**
     * 生成excel文件(文件标题栏与文件内容一定要对应)
     * @param os
     * @param title(excel文件标题栏)
     * @param lists(excel文件内容)
     * @throws IOException
     * @throws RowsExceededException
     * @throws WriteException
     */
 public static void writeExcel(OutputStream os, String[] title, List lists) throws IOException, RowsExceededException, WriteException  {
		// 创建可以写入的Excel工作薄(默认运行生成的文件在tomcat/bin下 )
	    WritableWorkbook wwb = Workbook.createWorkbook(os);
		// 生成工作表,(name:First Sheet,参数0表示这是第一页)
		WritableSheet sheet = wwb.createSheet("First Sheet", 0);
  
        // 开始写入第一行(即标题栏)
        for(int i=0; i<title.length; i++){
            // 用于写入文本内容到工作表中去
            Label label = null;
            // 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )      
            label = new Label(i, 0, title[i]);
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
        }

        // 开始写入内容
        for (int row=0; row<lists.size(); row++) {        
             // 获取一条(一行)记录
             List list = (List) lists.get(row);
             // 数据是文本时(用label写入到工作表中)
             for (int col=0; col<list.size(); col++) {                
               String listvalue = (String) list.get(col).toString(); 
               Label label = null;
               label = new Label(col, row+1, listvalue);
                     sheet.addCell(label);            
             }   
        } 
    
	     /*
	        生成一个保存数字的单元格,必须使用Number的完整包路径,否则有语法歧义,值为789.123
	    jxl.write.Number number = new jxl.write.Number(col, row, 555.12541);
	    sheet.addCell(number);
	  */
	
	        /*
	                           生成一个保存日期的单元格,必须使用DateTime的完整包路径,否则有语法歧义,值为new Date()
	          jxl.write.DateTime date = new jxl.write.DateTime(col, row, new java.util.Date());
	          sheet.addCell(date);
	         */
	
	        // 写入数据
		  wwb.write();
		  // 关闭文件
		  wwb.close();
		  // 关闭输出流
		  os.close();
	 }
 
	 /**
	  * 生成excel文件(文件标题栏与文件内容一定要对应)
	  * @param os
	  * @param title(excel文件标题栏)
	  * @param lists(excel文件内容)
	  * @throws IOException
	  * @throws RowsExceededException
	  * @throws WriteException
	  */
	public static void writeExcelObject(OutputStream os, String[] title, List lists) throws IOException, RowsExceededException, WriteException  {
		 WritableWorkbook wwb = Workbook.createWorkbook(os);
		   
		  int rownum=50000;
		  
		  int len=lists.size();
		  
		  int num=0;
		  
		  if(len%rownum==0){
			  num=len/rownum;
		  }else{
			  num=len/rownum+1;
		  }
 
       for(int k=0;k<num;k++){
       	
       	// 生成工作表,(name:First Sheet,参数0表示这是第一页)
   		WritableSheet sheet = wwb.createSheet("FirstSheet"+k, k);
     
           // 开始写入第一行(即标题栏)
           for (int i=0; i<title.length; i++) {
               // 用于写入文本内容到工作表中去
               Label label = null;
               // 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )      
               label = new Label(i, 0, title[i]);
               // 将定义好的单元格添加到工作表中
               sheet.addCell(label);
           }
           
           int tempnum1=k*rownum;
           int tempnum2=0;
           if((k+1)*rownum>len){
           	tempnum2=len;
           }else{
           	tempnum2=(k+1)*rownum;
           }

           // 开始写入内容
           for (int row=tempnum1; row<tempnum2; row++){        
                // 获取一条(一行)记录
        	     Object[]oo = (Object[]) lists.get(row);
                // 数据是文本时(用label写入到工作表中)
                for (int col=0; col<oo.length; col++) {                
                  String listvalue = oo[col]+""; 
                  Label label = null;
                  label = new Label(col, (row-tempnum1)+1, listvalue);
                        sheet.addCell(label);            
                }   
           } 
       	
       }
	 
	  
	
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		// 关闭输出流
		os.close();
	}
	public static void main(String[] args) throws IOException, RowsExceededException, WriteException{
		
		//组成list
		  //测试数字
		  List<Integer> list1 = new ArrayList<Integer>();
		  list1.add(1);
		  list1.add(2);
		  list1.add(3);

		  //测试日期
		  List<Date> list2 = new ArrayList<Date>();
		  list2.add(new Date());
		  list2.add(new Date());
		  list2.add(new Date());
		  
		  //测试英文
		  List<String> list3 = new ArrayList<String>();
		  list3.add("aeee");
		  list3.add("beee");
		  list3.add("crrr");
		  
		  //测试汉字
		  List<String> list4 = new ArrayList<String>();
		  list4.add("一ghgfh");
		  list4.add("二fghfgh");
		  list4.add("三fghgfd");
		  
		  List<List> lists = new ArrayList<List>();
		  lists.add(list1);
		  lists.add(list2);
		  lists.add(list3);
		  lists.add(list4);
		  
		  File f = new File("D://" + "hjheee" + ".xls");
		  f.createNewFile();
		  String title[] = {"编号rttrtrtrt","地trrrtrrt市","trrrrrrrr大学编号"};
		  Excel.writeExcel(new FileOutputStream(f), title, lists);
		  
		  String title2[] = {"编号1","编号2","编号3","编号4","编号5"};
		  Object[]a1={"A1","B1","C1","D1","G1"};
		  Object[]a2={"A1","B1","C1","D1","G3"};
		  Object[]a3={"A1","B1","C1","D1","G4"};
		  Object[]a4={"A1","B1","C1","D1","G5"};
		  Object[]a5={"A6","B6","C6","D6","G6"};
		  List lists2 = new ArrayList();
		  lists2.add(a1);
		  lists2.add(a1);
		  lists2.add(a1);
		  lists2.add(a1);
		  lists2.add(a1);
		  
		  Excel.writeExcelObject(new FileOutputStream(f), title2, lists2);
		  
		  

	}
}

