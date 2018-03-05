package com.shenjun.db.type;

import java.util.List;

/**
 * 表类,包含了表的数据、列对象、表名、主键等
 * @author shenjun
 *
 */
public class Table {
	private String tableName;
	
	private List<SqlColumnType> columns;
	
	private List<Object[]> data;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SqlColumnType> getColumns() {
		return columns;
	}

	public void setColumns(List<SqlColumnType> columns) {
		this.columns = columns;
	}

	public List<Object[]> getData() {
		return data;
	}

	public void setData(List<Object[]> data) {
		this.data = data;
	}
}
