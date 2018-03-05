package com.shenjun.db.type;

import java.io.Serializable;
import java.lang.reflect.Type;


public class SqlColumnType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnName="none";
	private Integer columnType=SqlType.VARCHAR;
	private String columnTypeName="varchar";
	private Integer columnDisplaySize=100;
	private String tableName="";
	private String localText="";
	private String params="";
	private String sign;
	@SuppressWarnings("unused")
	private Type javaType;
	
	public Type getJavaType() {
		if(this.columnType == SqlType.VARCHAR){
			return String.class;
		}else if(this.columnType == SqlType.DATE||this.columnType == SqlType.TIMESTAMP){
			return java.util.Date.class;
		}else if(this.columnType == SqlType.INTEGER){
			return Integer.class;
		}else if(this.columnType == SqlType.NUMERIC){
			return Float.class;
		}else{
			return Object.class;
		}
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTableName() {
		return (tableName+"").toLowerCase();
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getLocalText() {
		return localText;
	}

	public void setLocalText(String localText) {
		this.localText = localText;
	}

	public SqlColumnType(){}
	
	public SqlColumnType(String columnName,Integer columnType,String columnTypeName,Integer columnDisplaySize){
		this.columnName=columnName;
		this.columnType=columnType;
		this.columnTypeName=columnTypeName;
		this.columnDisplaySize=columnDisplaySize;
		this.setLocalText((columnName+"").toLowerCase());
	}
	
	public SqlColumnType(String columnName){
		this.columnName=columnName;
		this.setLocalText((columnName+"").toLowerCase());
	}
	
	public Integer getColumnDisplaySize() {
		return columnDisplaySize;
	}
	public void setColumnDisplaySize(Integer columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}
	public String getColumnName() {
		return (columnName+"").toLowerCase();
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
		this.setLocalText(columnName);
	}
	public Integer getColumnType() {
		return columnType;
	}
	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
}
