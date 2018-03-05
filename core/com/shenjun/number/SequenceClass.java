package com.shenjun.number;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.shenjun.util.RegexUtil;

/**
 * 序号管理类
 * @author 沈军
 *
 */
public class SequenceClass {
	
	@SuppressWarnings("unused")
	private static List<String> seqlist = new ArrayList<String>();
	
	public String getSequence(){
		
		return null;
	}
	
	/**
	 * 获取指定规则的序号
	 * @param pattern 替换规则
	 * @param source 增加的前一个序号
	 * @return 
	 * egg SF[YY][MM][DD][000]
	 */
	public static String getNumber(String pattern,String source){
		//日期处理
		Calendar now=Calendar.getInstance();
		String year=now.get(Calendar.YEAR)+"";
		String month="0"+(now.get(Calendar.MONTH)+1);
		String date="0"+(now.get(Calendar.DATE));
		
		//替换日期
		pattern=pattern.replaceAll("\\[YY\\]", year.substring(2));
		pattern=pattern.replaceAll("\\[YYYY\\]", (year+""));
		pattern=pattern.replaceAll("\\[MM\\]",month.substring(month.length()-2,month.length()));
		pattern=pattern.replaceAll("\\[DD\\]",date.substring(date.length()-2,date.length()));
		
		
		
		//序号处理
		List<String> ls =RegexUtil.getSpecifyRegex("\\[[0-9]+\\]", pattern);
		if(source==null)
			source="";
		StringBuilder sb = new StringBuilder(source);
		boolean bool = false;
		if(sb.length()>0){
		String sbr=pattern;
			for(String pattStr:ls){
				sb.delete(pattern.indexOf("["), pattern.indexOf("]"));
				sbr=sbr.replace(pattStr, "");
			}
			
			if(sbr.equals(sb.toString())){
				bool=true;
			}
		}
		for(String pattStr:ls){
			
			int startIndex=pattern.lastIndexOf(pattStr);

			String sourceNumber=pattStr.replace("[", "").replace("]", "");
			if(!"".equals(source)&&bool)
				sourceNumber=source.substring(startIndex,startIndex+pattStr.length()-2);
			
			String pattStrClean=pattStr.replace("[", "").replace("]", "");
			
			String addNumberStr=pattStrClean+(Integer.parseInt(sourceNumber)+1);
			
			pattern=pattern.replaceAll("\\["+pattStrClean+"\\]",
					addNumberStr.substring(addNumberStr.length()-pattStrClean.length(),addNumberStr.length())
			);
			
		}
		return pattern;
	}
	
	public static void main(String[] args) {
		System.out.println(
				 SequenceClass.getNumber("LLL[YY][MM][DD][0000]", null)
		);
	}
	
}
