package com.shenjun.db.type;

import com.shenjun.enums.InputType;

/**
 * 
 * @author shenjun
 *
 */
public class SqlValue {
	private Object value;
	
	private int SqlType;
	
	private int index;
	
	private InputType inputType = InputType.IN;
	
	public SqlValue(int SqlType){
		this.inputType=InputType.OUT;
		this.SqlType=SqlType;
	}
	
	public SqlValue(Object value,int SqlType){
		this.value=value;
		this.SqlType=SqlType;
	}
	public int getSqlType() {
		return SqlType;
	}
	public void setSqlType(int sqlType) {
		SqlType = sqlType;
	}
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T getValue() {
		return (T)value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public InputType getInputType() {
		return inputType;
	}
	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}
}
