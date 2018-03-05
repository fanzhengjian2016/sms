package com.shenjun.io.data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class ListDataSerializable<T extends Collection> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public T data;
	
	public ListDataSerializable(T data){
		this.data=data;
	} 
	
	public ListDataSerializable(){
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	} 

	
}
