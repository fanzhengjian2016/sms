package com.shenjun.plugins.jxl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.shenjun.db.type.SqlType;
import com.shenjun.util.DateUtil;
import com.shenjun.util.SqlUtil;
import com.shenjun.util.StringUtil;

public class ExcelImp {
	
	private static Logger log = Logger.getLogger(ExcelImp.class);
	
	/**
	 * 将Excel转成对应的List数组，如果没有找到，返回List且长度为0
	 * @param file 文件
	 * @param startx 开始列读取索引
	 * @param starty 结果行读取索引
	 * @param x 列的读取长度
	 * @param y 行的读取长度
	 * @param types 如果为空，不进行编码，直接为String型，否则进行尝试转码
	 * @return
	 */
	public static List<Object[]> analyse(File file,int startx,int starty,int x,int y,Integer[] types){
		return analyse(file,0,startx,starty,x,y,types);
	}
	
	/**
	 * 将Excel转成对应的List数组，如果没有找到，返回List且长度为0
	 * @param file 文件
	 * @param startx 开始列读取索引
	 * @param starty 结果行读取索引
	 * @param x 列的读取长度
	 * @param y 行的读取长度
	 * @return
	 */
	public static List<Object[]> analyse(File file,int startx,int starty,int x,int y){
		return analyse(file,0,startx,starty,x,y,null);
	}
	
	/**
	 * 将Excel转成对应的List数组，如果没有找到，返回List且长度为0
	 * @param file 文件
	 * @param x 列的读取长度
	 * @param y 行的读取长度
	 * @param types 如果为空，不进行编码，直接为String型，否则进行尝试转码
	 * @return
	 */
	public static List<Object[]> analyse(File file,int x,int y,Integer[] types){
		return analyse(file,0,0,0,x,y,types);
	}
	
	/**
	 * 将Excel转成对应的List数组，如果没有找到，返回List且长度为0
	 * @param file 文件
	 * @param sheetName Sheet名称
	 * @param startx 开始列读取索引
	 * @param starty 结果行读取索引
	 * @param x 列的读取长度
	 * @param y 行的读取长度
	 * @param types 如果为空，不进行编码，直接为String型，否则进行尝试转码
	 * @return
	 */
	public static List<Object[]> analyse(File file,String sheetName,int startx,int starty,int x,int y,Integer[] types){
		try{
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(sheetName);
			return analyse(sheet,startx,starty,x,y,types);
		}catch(Exception e){
			log.error("ExcelImp analyse:"+e.getMessage(),e.getCause());
		}
		return null;
	}
	
	/**
	 * 将Excel转成对应的List数组，如果没有找到，返回List且长度为0
	 * @param file 文件
	 * @param sheetIndex 纸张索引
	 * @param startx 开始列读取索引
	 * @param starty 结果行读取索引
	 * @param x 列的读取长度
	 * @param y 行的读取长度
	 * @param types 如果为空，不进行编码，直接为String型，否则进行尝试转码
	 * @return
	 */
	public static List<Object[]> analyse(File file,int sheetIndex,int startx,int starty,int x,int y,Integer[] types){
		try{
			Workbook book = Workbook.getWorkbook(file);		
			Sheet sheet = book.getSheet(sheetIndex);
			return analyse(sheet,startx,starty,x,y,types);
		}catch(Exception e){
			log.error("ExcelImp analyse:"+e.getMessage(),e.getCause());
		}
		return null;
	}
	
	/**
	 * 将Excel转成对应的List数组，如果没有找到，返回List且长度为0
	 * @param Sheet 单元表对象
	 * @param startx 开始列读取索引
	 * @param starty 结果行读取索引
	 * @param x 列的读取长度
	 * @param y 行的读取长度
	 * @param types 如果为空，不进行编码，直接为String型，否则进行尝试转码
	 * @return
	 */
	public static List<Object[]> analyse(Sheet sheet,int startx,int starty,int x,int y,Integer[] types){
		List<Object[]> data = new ArrayList<Object[]>();
		try {
            int rowsCount=sheet.getRows();
            int columnsCount=sheet.getColumns();
            
            if(x!=-1){
            	columnsCount=x;
            }
            
            if(y!=-1){
            	rowsCount=y;
            }
            
            for(int i=startx;i<rowsCount;i++){
            	Object[] rows = new Object[x];
            	int typeIndex=0;
                for(int j=starty;j<columnsCount;j++){
                	Cell cell = sheet.getCell(j, i);
                    String result = cell.getContents();
                    if(types!=null&&types.length>typeIndex){
                    	Object o=SqlUtil.Csv(result, types[typeIndex]).getValue();
                    	rows[j]=o;
                    }else{
                    	rows[j]=result;
                    }
                    typeIndex++;
                }
                data.add(rows);
            }
		} catch (Exception e) {
			log.error("ExcelImp analyse:"+e.getMessage(),e.getCause());
		}
		return data;
	}
	
	public static void main(String[] args) {
		List<Object[]> data = ExcelImp.analyse(new File("C:/MyFile/temp/test.xls"),4, 3,new Integer[]{SqlType.VARCHAR,SqlType.VARCHAR,SqlType.VARCHAR,SqlType.DATE});
		for(Object[] rows : data){
			//System.out.println(rows[3].getClass());
			if(rows[3] instanceof java.util.Date){
				System.out.println("--"+DateUtil.toStr(
						(Date)rows[3],DateUtil.TIME
						));
			}
			System.out.println(StringUtil.join(rows, ","));
		}
	}
}
