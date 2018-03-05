package com.shenjun.plugins.jxl;

import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BaseCreateList {
	/**
	 * 1.2版本的导出(主要不包含头)
	 * @param os
	 * @param ls
	 */
	public static void writerExcel(OutputStream os , List ls){
		writerExcels( os ,  ls,"");
	}
	
	public static void writerTExcel(OutputStream os , List ls,String name){
		writerExcels(os,ls,name);
	}
	
	public static void writerExcels(OutputStream os , List ls,String name){
		WritableWorkbook wwb = null;
		
		try {
			 /*
			  * 如果的JSP页面的话可以用ByteArrayOutputStream传入createWorkbook(args[0])
			  * 以便于下载
			  */
			wwb = Workbook.createWorkbook(os);
			WritableSheet wts = null;
			
			int k = 0;
			int len = ls.size();
			Object[] obj = null;
			
			if(len>0){
				obj=(Object[])ls.get(0);
				ls.remove(0);
			}
			len = ls.size();
			WritableFont wtfs = new WritableFont(WritableFont.COURIER , 14,WritableFont.BOLD,false);
			WritableCellFormat wtcfk = new WritableCellFormat(wtfs);
			wtcfk.setVerticalAlignment(VerticalAlignment.CENTRE);
			wtcfk.setAlignment(Alignment.CENTRE);
			
			WritableFont wtfs2 = new WritableFont(WritableFont.COURIER , 12,WritableFont.NO_BOLD,false);
			WritableCellFormat wtcfk2 = new WritableCellFormat(wtfs2);
			wtcfk2.setVerticalAlignment(VerticalAlignment.CENTRE);
			wtcfk2.setAlignment(Alignment.CENTRE);
			
			//字体是18,设置为斜体  true
			WritableFont wtf = new WritableFont(WritableFont.COURIER , 18,WritableFont.BOLD,false);
			//wtf.setUnderlineStyle(UnderlineStyle.DOUBLE);
			WritableCellFormat wtcf = new WritableCellFormat(wtf);
			wtcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wtcf.setAlignment(Alignment.CENTRE);
			
			for(int i = 0; i <len ; i++){
				if(i%5000==0){
					wts = wwb.createSheet("Sheet"+i, 0);//创建工作表
					if(name!=""){
						wts.setRowView(0, 760);
						wts.mergeCells(0, 0, ((Object[])(ls.get(i))).length-1,0);//mergeCells(m,n,p,q)作用是从(m,n)到(p,q)的单元格全部合并
						
						//Object[] obj = new Object[];
						
						Label lblCell = new Label(0,0,name,wtcf);
						wts.addCell(lblCell);
					}
					for (int j = 0; j < obj.length; j++) {
						Label lblCell = new Label(j,1,obj[j]+"",wtcfk);
						wts.setRowView(1, 400);
						wts.setColumnView(j, 23);
						wts.addCell(lblCell);
					}
					k=2;
				}
				
				Object[] lsc = (Object[])(ls.get(i));
				int le = lsc.length;
				for(int j = 0 ; j<le;j++){
					Label lblCell = new Label(j,k,lsc[j]+"",wtcfk2);//将单元格写入
					wts.setRowView(k, 400);
					wts.addCell(lblCell);
				}
				k++;
			}
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

