package com.shenjun.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 字符串处理类
 * @author: 沈军
 * @category 字符串处理类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class StringUtil {
	
	private static final Log log = LogFactory.getLog(StringUtil.class);
	
	/**
	 * String 转成指定的对象类型
	 * @param value
	 * @param type
	 * @return
	 */
	public static <T> T stringToValue(String value,Type type){
		Object obj = value;
		if(String.class==type){
			
		}else if(Date.class==type||java.util.Date.class==type){
			obj = DateUtil.toDate(obj+"");	
		}else if(Double.class==type){
			obj = Double.parseDouble(obj+"");
		}else if(Float.class==type){
			obj = Float.parseFloat(obj+"");
		}else if(Integer.class==type){
			obj = Integer.parseInt(obj+"");
		}else{
			log.error("找不到转换的类型格式：String to ["+type.toString()+"]");
		}
		return (T)obj;
	}
	
	/**
	 * 获取字符串的编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str){
		String[] encodes=new String[]{"iso8859-1","GB2312","GBK","UTF-8"};
		for(String encode :encodes){
			try{
				if(str.equals(new String(str.getBytes(encode), encode))) {
					return encode;
				}
			}catch (Exception e) {
				
			}
		}
		return null;
	}
	
	/**
	 * 合并数组成字符串【exp+obj[?]+exp+ch】
	 * @param objs
	 * @param ch 拼接连接符
	 * @param exp 对象两边修饰字符串
	 * @return
	 */
	public static String join(Object[] objs,String ch,String exp){
		StringBuilder sb = new StringBuilder();
		int len=objs.length;
		for(int i=0; i<len; i++ ){
			sb.append(exp+objs[i]+exp);
			if(i+1!=len){
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	/**
	 * 合并数组成字符串【obj[?]+ch】
	 * @param objs
	 * @param ch 拼接连接符
	 * @return
	 */
	public static String join(Object[] objs,String ch){
		return join(objs,ch,"");
	}
	
	/**
	 * 获取随机字符串代码
	 * @param length 所需字符长度
	 * */
	public static String getRandomNum(Integer length){
		Random randGen = null;
		char[] numbersAndLetters = null;
		if (length < 1) {
            return null;
        }
        if (randGen == null) {
               randGen = new Random();
               numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                  "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                 //numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
         //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);

	}
	
	/**
	 * 判断是否为空,空返回false,不为空返回True
	 * @param obj
	 * @return 空返回false,不为空返回True
	 */
	public static boolean isNB(Object...objects){
		for(Object obj:objects){
			if(!isNotBlankOrNull(obj)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 非NUll，但不匹配空
	 * @param objects
	 * @return
	 */
	public static boolean isNotNull(Object...objects){
		for(Object obj:objects){
			if(obj==null){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param obj 传入对象
	 * @param dft 默认对象
	 * @return 如果传入对象为空或者为NUll，将返回默认值。
	 */
	public static Object is(Object obj,Object dft){
		return isNB(obj)?obj:dft;
	}
	
	/**
	 * 
	 * @param str 传入对象
	 * @param dft 默认对象
	 * @return 如果传入对象为空或者为NUll，将返回默认值。
	 */
	public static String is(String str,String dft){
		return isNB(str)?str:dft;
	}
	
	/**
	 * 
	 * @param obj 传入对象
	 * @param dft 默认对象
	 * @return 如果传入对象为空或者为NUll，将返回默认值。
	 */
	public static float is(float obj,float dft){
		return isNB(obj)?obj:dft;
	}
	
	/**
	 * 
	 * @param obj 传入对象
	 * @param dft 默认对象
	 * @return 如果传入对象为空或者为NUll，将返回默认值。
	 */
	public static int is(int obj,int dft){
		return isNB(obj)?obj:dft;
	}
	
	/**
	 * 请求的时进行地址记录和处理
	 * @param httpServletRequest
	 * @param clientStr
	 * @param url
	 */
	@SuppressWarnings("unchecked")
	public static String processorAddr(HttpServletRequest httpServletRequest,String clientStr,String url){
		
		StringBuilder builder=new StringBuilder();
		builder.append("\nUser ID<"
					+clientStr
					+"> At<"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
					+">request IP:"+httpServletRequest.getRemoteAddr()
					+" Url:"+url+"\n");
		
		builder.append("{\n");
		
		int par = 1;
		Object value=null;
		String[] values=null;
		
		Map<String, String[]> paramMap= httpServletRequest.getParameterMap();
		Set<String> keySet=paramMap.keySet();
		
		for (Object key : keySet) {
			builder.append(" params "+par+":--<"+key.toString()+":");
			
			value=paramMap.get(key);
			if(value instanceof String[]){
				values=(String[])value;
				for (int i = 0; i < values.length; i++) {
					String val = values[i]==""?"Null":values[i];
					int len=val.length();
					if(len>200){
						val=val.substring(0,200)+"...(过多，长度为："+len+")";
					}
					if((values.length-1)==i){
						
						builder.append(val);
					}else{
						builder.append(values[i]+",");
					}
					
				}
			}else{
				builder.append((value==""?"Null":value));
			}
			builder.append(">\n");
			par++;
		}
		builder.append("}\n");
		
//		//指定显示信息
//		if(!(httpServletRequest.getParameter("shield")+"").equals("true")){
//			log.debug(builder.toString());
//			
//			//写入文件保存
//			WriteVisitingLog.setMsg(builder.toString());
//			
//			//显示当前线程
//			//log.debug("This beigin now Filter Thread name:("+Thread.currentThread().getName()+")");
//		}
		return builder.toString();
	}
	
	/**
	 * 转义为XML格式
	 * @param xmlValue
	 * @return
	 */
	public static String compileXml(String xmlValue){
		if(xmlValue==null)
			return null;
		
		xmlValue=xmlValue.replaceAll("&", "&amp;")
							.replaceAll("<", "&lt;")
								.replaceAll(">", "&gt;");
		return xmlValue;
	}
	
	/**
	 * 创建相当字符的数组，可用于？的创建
	 * @param ch
	 * @param len
	 * @return
	 */
	public static String[] createString(String ch,int len){
		String[] chs = new String[len];
		for(int i=0,slen=chs.length;i<slen;i++){
			chs[i]=ch;
		}
		return chs;
	}
	
	/**
	 * 写入文件
	 * @param path
	 * @param fileName
	 * @param text
	 */
	public static void toTextFile(String path,String fileName,String text){
		try {
			if(new File(path).mkdirs())
				log.info("成功创建文件夹:"+path);
			
			File file = new File(path+"/"+fileName);
			
			//FileWriter out = new FileWriter(file,true);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); 
			if(!file.exists())
				file.createNewFile();

			out.write(text);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("stringutil toTextFile "+e.getMessage(),e.getCause());
		}
	}
	
	/**
	 * 判断字符串是否为中文
	 * @param str 字符串
	 * @return 为中文为true，否则为false
	 */
	public boolean isChinese(String str) {
		boolean result = false;

		for (int i = 0; i < str.length(); i++) {
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result = true;
			}
		}
		return result;
	}
	
	
	/******************过时列表**********************************************************/
	/**
	 * 获取数组的第一个，【过时】
	 * @param obj
	 * @return
	 */
	@Deprecated
	public static Object getFirstObject(Object obj){
		return ((Object[])obj)[0]; 
	}
	
	/**
	 * 按指定的参数分隔字符串
	 * @param obj
	 * @param ch 拼接连接符
	 * @param exp 两边修饰
	 * @return
	 */
	@Deprecated
	public static String separatorString(char[] obj,String ch,String exp){
		StringBuilder sb = new StringBuilder();
		for(char oj : obj){
			sb.append(exp+(oj+"").trim()+exp+ch);
		}
		
		if(sb.length()>0)
			sb.replace(sb.length()-ch.length(), sb.length(), "");
		
		return sb.toString();
	}
	
	/**
	 * 按指定的参数分隔字符串
	 * @param obj
	 * @param ch
	 * @return
	 */
	@Deprecated
	public static String separatorString(char[] obj,String ch){
		StringBuilder sb = new StringBuilder();
		for(char oj : obj){
			sb.append((oj+"").trim()+ch);
		}
		
		if(sb.length()>0)
			sb.replace(sb.length()-ch.length(), sb.length(), "");
		
		return sb.toString();
	}
	
	/**
	 * 按指定的参数分隔字符串
	 * @param obj
	 * @param ch
	 * @param exp
	 * @return
	 */
	@Deprecated
	public static String separatorString(Object[] obj,String ch,String exp){
		StringBuilder sb = new StringBuilder();
		for(Object oj : obj){
			sb.append(exp+(oj+"").trim()+exp+ch);
		}
		
		if(sb.length()>0)
			sb.replace(sb.length()-ch.length(), sb.length(), "");
		
		return sb.toString();
	}
	
	/**
	 * 按指定的参数分隔字符串
	 * @param obj
	 * @param ch
	 * @return
	 */
	@Deprecated
	public static String separatorString(Object[] obj,String ch){
		StringBuilder sb = new StringBuilder();
		for(Object oj : obj){
			sb.append((oj+"").trim()+ch);
		}
		
		if(sb.length()>0)
			sb.replace(sb.length()-ch.length(), sb.length(), "");
		
		return sb.toString();
	}
	
	/**
	 * 判断是否为空,空返回false,不为空返回True
	 * @param obj
	 * @return
	 */
	@Deprecated
	public static boolean isNotBlankOrNull(Object obj){
		if(obj==null){
			return false;
		}
		
		if(obj instanceof Object[]){
			Object[] objs = (Object[])obj;
			if(objs.length>0){
				return true;
			}else{
				return false;
			}
		}
		
		if("".equals((obj+"").trim())){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param ch   ?
	 * @param len   0....10...
	 * @param separator ','
	 * @return
	 */
	@Deprecated
	public static String createSameChar(String ch,int len,String separator){
		String [] placeholder=StringUtil.createString(ch,len);
		return StringUtil.separatorString(placeholder, separator);
	}
}
