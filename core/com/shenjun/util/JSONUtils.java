package com.shenjun.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.shenjun.plugins.jackson.JSON;
/**
 * JSON对象操作类
 * @author shenjun
 *
 */
public class JSONUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final XmlMapper xmlMapper = new XmlMapper();
	
	static{
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
	}

	private JSONUtils() {
		
	}
	/**
	 * List 转成带有Key JSON（过时，请使用listToListMap）
	 * @param list 数据部分
	 * @param columns 列名部分
	 * @return
	 */
	@Deprecated
	public static JSON<List<Map<String, Object>>> listToJson(List<Object[]> list,String[] columns){
		return new JSON<List<Map<String, Object>>>(listToListMap(list,columns));
	}
	
	/**
	 * 将List<Object[]> 转成 List<Map<String, Object>>
	 * @param list List<Object[]>
	 * @param columns map的KeyName
	 * @return
	 */
	public static List<Map<String, Object>> listToListMap(List<Object[]> list,String[] columns){
		List<Map<String, Object>> ls=new ArrayList<Map<String, Object>>();
		if(list!=null){
			for(Object[] rows : list){
				ls.add(MapUtils.arraysToJsonMap(columns, rows));
			}
		}
		return ls;
	}
	
	/**
	 * Object转成带有Key的JSON
	 * @param objects 数组部分
	 * @param columns 列名部分
	 * @return
	 */
	@Deprecated
	public static JSON<Map<String, Object>> objectjsToJson(Object[] objects,String[] columns){
		
		return new JSON<Map<String, Object>>(MapUtils.arraysToJsonMap(columns, objects));
	}

	/**
	 * 将对象转换成JSON字符串
	 * @param bean 可以是Bean|Map|List
	 * @return JSON字符串
	 */
	public static <T> String toJson(T bean) {
		StringWriter sw = new StringWriter();
		try {
			JsonGenerator gen = new JsonFactory().createGenerator(sw);
			mapper.writeValue(gen, bean);
			gen.close();
			return sw.toString();
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 自动构建Map 转成JSON字段串
	 * @param kvs 为一对一对的对象，如：key,value,key,value...对象的个数为偶数
	 * @return JSON字符串
	 */
	public static String toJson(Object... kvs){
		return JSONUtils.toJson(MapUtils.instanceObj(kvs));
	}

	/**
	 * 将JSON字符串转成对应的Class实例化对象
	 * @param json JSON字符串
	 * @param clzz 要转换对象的类型
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clzz) {

		T t = null;
		try {
			t = mapper.readValue(json, clzz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将JSON字符串转换成Map对象。
	 * @param json JSON字符串,请保持key是加了双引号的
	 * @return Map对象,默认为HashMap
	 */
	public static Map<?, ?> fromJson(String json) {
		try {
			return mapper.readValue(json, HashMap.class);
		} catch (JsonParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 将对象转成XML字符串
	 * @param List|bean|Map对象
	 * @return xml
	 * @throws IOException 
	 */
	public static <T> String toXml(T bean){
		
		StringWriter sw = new StringWriter();
		try {
			ToXmlGenerator gen = new XmlFactory().createGenerator(sw);
			xmlMapper.writeValue(gen, bean);
			gen.close();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sw.toString();
	}

	/*public static void main(String[] args) throws IOException {
		
		List array = new ArrayList();
		array.add(new Object[]{1,2,3});
		System.out.println("---"+JSONUtils.toJson(array));
		
		Map map = new LinkedHashMap();
		map.put("mm","dd");
		map.put("dd", "ssd");
		
		//org.codehaus.stax2.XMLStreamWriter2
		
		System.out.println(JSONUtils.toXml(map));
		System.out.println(JSONUtils.toXml(map));
		System.out.println(JSONUtils.toXml(map));
		
		
		String target="{'a':1,'b':[{'x':'5'}]}";
		Map a=JSONUtils.fromJson(target);
		List<Map> ls=(List)a.get("b");
		System.out.println(ls.get(0).get("x"));
		
		
		System.out.println(JSONUtils.toXml(a));
		
		List<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[]{"x",1,new Date()});
		data.add(new Object[]{"z","k",new Date()});
		
		String str=JSONUtils.listToJson(data, new String[]{"N1","N2","D1"}).toJSONString();
		System.out.println(str);
		
		String str2=JSONUtils.objectjsToJson(new Object[]{"z","k",new Date()}, new String[]{"N1","N2","D1"}).toJSONString();
		System.out.println(str2);
		
	}*/
	
	/**
	 * 	ObjectNode root = mapper.createObjectNode();
		ObjectNode s = mapper.createObjectNode();
		s.put("sss", "ddd");
		
		root.set("arraynode", s);
		
		List<ObjectNode> ls = new ArrayList<ObjectNode>();
		ls.add(root);
		
		System.out.println(JacksonFactory.toJson(ls));
	 */
}
