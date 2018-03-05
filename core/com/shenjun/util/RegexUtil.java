package com.shenjun.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过正则表达式进行字符串的操作与查询类
 * @author shenjun
 *
 */
public class RegexUtil {
	
	/**
	 * 通过正则表达式查询第一个字符串
	 * @param regex 正则表达式
	 * @param str
	 * @return
	 */
	public static String getFrist(String regex , String str){
		Matcher m =  Pattern.compile(regex).matcher(str);
		if(m.find()){
			return m.group();
		}
		return null;
	}
	
	/**]
	 * 查询有指定的正则表达式的内容是否存在
	 * @param regex 正则表达式
	 * @param str
	 * @return 存在返回true不存在返回false
	 */
	public static boolean isFindStr(String regex , String str){
		Matcher m =  Pattern.compile(regex).matcher(str);
		return m.find();
	}
	
	/**
	 * 获取指定的正则表达式内容
	 * @param regex 正则表达式
	 * @param str
	 * @return 返回一个List<String>的正则表达式列表
	 */
	public static List<String> getSpecifyRegex(String regex , String str){
		List<String> arr = new ArrayList<String>();
		Matcher m =  Pattern.compile(regex).matcher(str);
		while(m.find()){
			arr.add(m.group());
		}
		return arr;
	}
	
	/*public static void main(String[] args) {
		
		//List<String> ls = RegexUtil.getSpecifyRegex("[:]{1}[(\u4e00-\u9fa5|0-9)]+", "  select b.描述 as 结算方式,sum(金额) as 水费金额,  sum(滞纳金) as 滞纳金,  sum(a.CHGWATER) AS 水量,count(a.费用ID) as 费用笔数  from 费用信息 a,结算方式 b  where 销帐日期 between :销帐起始日期 and :销帐 结束日期  and (销帐方式='YS' or exists(select * from 票据打印 pj,打印记录 dy  where pj.打印ID = dy.打印ID  and dy.费用ID = a.费用ID  and pj.发票号码 is not null  and pj.作废日期 is null  and pj.票据类型='SJ'))  and a.结算方式 = b.结算方式  group by b.描述  order by b.描述");
		List<String> ls = RegexUtil.getSpecifyRegex("com\\.web\\.[a-z]+\\.action", "com.web.system.action");
		//System.out.println(RegexUtil.isFindStr("com.web.[a-z]+.action", "com.web.system.action"));
		System.out.println(RegexUtil.isFindStr("^.*$", "在一起"));
		
		for (String str:ls)
			System.out.println(str);
	}*/
}
