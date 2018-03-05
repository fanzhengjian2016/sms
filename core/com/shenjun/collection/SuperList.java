package com.shenjun.collection;

import java.util.List;
import java.util.Map;

import com.shenjun.db.type.SqlColumnType;
import com.shenjun.enums.ExtjsTypeEnum;
import com.shenjun.plugins.jackson.JSON;

/**
 *  expends list interface
 * @author shenjun
 *
 */
public interface SuperList<E> extends List<E> {
	/**
	 * this is db sql for columns data,is array
	 * @return
	 */
	public SqlColumnType[] getSqlColumnTypes();
	
	public int getIndexByName(String name);
	
	public void setSqlColumnTypes(SqlColumnType[] sct);
	
	public String toExtjs();
	
	public List toClass(Class cls);
	
	public List toClass(Class cls,String[] cols);
	
	public String toExtjs(String[] objs);
	
	public String toExtjs(ExtjsTypeEnum ete);
	
	public String toExtjs(ExtjsTypeEnum ete,String[] columns);
	
	public String toExtjs(ExtjsTypeEnum ete,String[] columns,String dataName,String totalName);
	
	/**
	 * 过时，请使用toJSONArrayString
	 * @return
	 */
	@Deprecated
	public JSON getListJSONArray();
	/**
	 * 返回[{columns:value,columns:value},{columns:value,columns:value}]
	 * @return
	 */
	public String toJSONArrayString();
	
	/**
	 * 过时，请使用toJSONArrayString
	 * @return
	 */
	@Deprecated
	public JSON getListJSONArray(String[] columns);
	/**
	 * 返回[{columns:value,columns:value},{columns:value,columns:value}]
	 * @return
	 */
	public String toJSONArrayString(String[] columns);
	
	/**
	 * 过时，请使用toFristRowJSONString
	 * @return
	 */
	@Deprecated
	public JSON getListJSONObject();
	
	/**
	 * 获取第一行成JSON
	 * 返回{columns:value,columns:value}
	 * @return
	 */
	public String toFristRowJSONString();
	
	/**
	 * 过时，请使用toFristRowJSONString
	 * @return
	 */
	@Deprecated
	public JSON getListJSONObject(String[] columns);
	
	/**
	 * 获取第一行成JSON
	 * 返回{columns:value,columns:value}
	 * @return
	 */
	public String toFristRowJSONString(String[] columns);
	
	/**
	 * 过时，请使用toSqlColumnJSONArray
	 * @return
	 */
	@Deprecated
	public JSON getSqlColumnJSONArray();
	
	/**
	 * 返回表的列头JSON字段串
	 * @return
	 */
	public String toSqlColumnJSONArrayString();
	
	
	public String getListArrayString();
	
	
	public String[] getColumnNames();
	
	public Map<String,SqlColumnType> getSqlColumnTypeMap();
	
	public <T> T getT(int index);
	
	/**
	 * 如果SuperList是从Result中获取，将可以获取总计数据在大小。否则为Data的Size
	 * @return
	 */
	public Integer getTotalCount();
	/**
	 * 设置过滤器
	 * @param listFilter
	 */
	public void setListFilter(SuperListFilter listFilter);
	
	/**
	 * 保存分割后的副本
	 * @param list
	 */
	public void setList(List<Object[]> list);
	
	/**
	 * 获取指定的对象，如果没有则返回NULL
	 * @param rowsIndex 
	 * @param cellIndex
	 * @return
	 */
	public <T> T get(int rowsIndex,int cellIndex);
	
	/**
	 * 根据行号与数据库中列名称获取值
	 * @param rowsIndex
	 * @param columnName
	 * @return
	 */
	public <T> T get(int rowsIndex,String columnName);
	
	/**
	 * 根据数据库列名获取第一行的数值
	 * @param columnName
	 * @return
	 */
	public <T> T get(String columnName); 
	
	
	
	
}
