package com.shenjun.plugins.jackson;

import com.shenjun.util.JSONUtils;
/**
 * 
 * @author jbox_user
 *
 * @param <T>
 */
public class JSON<T> {
	private T jsonObject;
	
	public JSON(T t){
		this.jsonObject=t;
	}
	/**
	 * 获取JSON对象中的值
	 * @param t
	 */
	public T getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(T jsonObject) {
		this.jsonObject = jsonObject;
	}
	/**
	 * 生成JSON字段串
	 * @return
	 */
	public String toJSONString(){
		return JSONUtils.toJson(this.getJsonObject());
	}
	
/*	public List<Map<String,Object>> getListData(){
		if (this.jsonObject instanceof List) {
			List<Map<String,Object>> map = (List) this.jsonObject ;
			return map;
		}
		return null;
	}*/
	
	/*public Map<String,Object> getMapData(){
		if (this.jsonObject instanceof Map) {
			Map<String,Object> map = (Map) this.jsonObject ;
			return map;
		}
		return null;
	}*/
	
}
