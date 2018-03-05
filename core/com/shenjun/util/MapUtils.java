package com.shenjun.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jfree.util.Log;

/**
 * Map操作类
 * @author jbox_user
 *
 */
public class MapUtils {
	
	/**
	 * 转成JSON用的专用Map
	 * @param keys MapKey的数组
	 * @param values MapValue的数组
	 * @return
	 */
	public static Map<String,Object> arraysToJsonMap(String[] keys,Object[] values ){
		return (Map<String, Object>) arraysToMap(keys,values);
	}
	
	/**
	 * 将对应的数组转换成Map对象
	 * @param keys MapKey的数组
	 * @param values MapValue的数组
	 * @return
	 */
	public static Map<?,?> arraysToMap(Object[] keys,Object[] values ){
		
		Map<Object,Object> map = new LinkedHashMap<Object,Object>();
		for (Integer i = 0; i < keys.length; i++) {
			if (values[i] instanceof java.util.Date)
				map.put(keys[i],
						DateUtil.toStr(
								(java.util.Date) values[i])
								+ "");
			else
				map.put(keys[i], values[i]);
		}
		return map;
	}
	
	/**
	 * 将字符串的参数转为Map<String,String>对象【参数一定是偶数个】
	 * @param objects 【key , value , key ,value...】
	 * @return
	 */
	public static Map<String,String> instance(String... objects){
		@SuppressWarnings("unchecked")
		Map<String,String> instanceObj = (Map<String,String>)MapUtils.instanceObj(objects);
		return instanceObj;
	}
	
	/**
	 * 将对象参数转为Map<Object,Object>对象【参数一定是偶数个】
	 * @param objects 【key , value , key ,value...】
	 * @return
	 */
	public static Map instanceObj(Object... objects){
		return instanceObj(null,objects);
	}
	
	/**
	 * 将对象参数添加到指定的Map中，如果Map为Null将创建一个容器Map存为并返回
	 * Map<Object,Object>对象【参数一定是偶数个】
	 * @param objects 【key , value , key ,value...】
	 * @return
	 */
	public static Map<?,?> instanceObj(Map<Object,Object> map,Object... objects){
		if(map==null){
			map = new LinkedHashMap<Object, Object>();
		}
		
		if(objects.length%2==0){
			for(int i=0,len=objects.length;i<len;i=i+2){
				map.put(objects[i], objects[i+1]);
			}
		}else{
			Log.error("params is not even...");
		}
		return map;
	}
	
	/*public static void main(String[] args) {

		Map<String,String> map=(Map<String, String>) arraysToMap(new String[]{"a","b"}, new String[]{"a","b"});
		map.put("b", "d");
		System.out.println(map.get("b"));
	}*/
}
