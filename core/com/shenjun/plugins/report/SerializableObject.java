package com.shenjun.plugins.report;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: 沈军
 * @category 实现序列化的封装类
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class SerializableObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private List obj;
	
	private Map map;
	
	public SerializableObject(){
		
	}
	
	public SerializableObject(List obj , Map map){
		this.obj = obj;
		this.map = map;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public List getObj() {
		return obj;
	}

	public void setObj(List obj) {
		this.obj = obj;
	}
	
}
