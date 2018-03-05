package com.shenjun.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 转成汉字拼音，通过Pinyin4j【出现少量转化错误纯属正常】
 * @author: 沈军
 * @category 转成汉字拼音
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class CnToSpell {
	
	/**
	 * 全拼
	 */
	public final static String FULL_SPELL="full_spell"; 
	
	/**
	 * 首字母
	 */
	public final static String FIRST_CHAR_SPELL="first_char_spell";
	
	
	private static final Log log = LogFactory.getLog(CnToSpell.class);
	
	/**
	 * 通过指定字符串与模式转为成拼音全拼与简写
	 * @param src 汉字字符
	 * @param mode 转换模式【CnToSpell.FULL_SPELL全拼,CnToSpell.FIRST_CHAR_SPELL简写】
	 * @return 全拼或者简写数组
	 */
	public static String[] convert(String src,String mode){
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		String[] t4 = new String[t1.length];
		
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符
				// System.out.println(t1[i]);
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					
					if(mode==FULL_SPELL){
						t4[i]=StringUtil.join(t2,"");
					}else{
						t4[i] = t2[0].charAt(0) + "";// 取出该汉字全拼的第一种读音并连接到字符串t4后
					}
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4[i] = Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			log.error("CnToSpell convert error:"+e.getMessage(),e);
		}
		return t4;
	}
	
	/**
	 * 直接转义成String字符串
	 * @param src 需要转换的汉字
	 * @param mode 转换模式【CnToSpell.FULL_SPELL全拼,CnToSpell.FIRST_CHAR_SPELL简写】
	 * @param separator 字之间的字符连接符
	 * @return 转换的字符串
	 */
	public static String convertString(String src,String mode,String separator){
		return StringUtil.join(
				convert(src,mode)
				,separator);
	}
	
	

	/*public static void main(String[] args) {
		String str = null;
		str = "坤";
		System.out.println("Spell=" + CnToSpell.convertString(str,FULL_SPELL,""));
	}*/
}
