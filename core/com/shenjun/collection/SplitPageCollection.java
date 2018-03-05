package com.shenjun.collection;

import java.util.List;

import com.shenjun.db.type.SqlColumnType;
import com.shenjun.plugins.jackson.JSON;
import com.shenjun.util.JSONUtils;

/**
 * 分页工具类
 * @author 沈军
 *
 * @param <T>
 */
public class SplitPageCollection{
	public SqlColumnType[] sct;
	
	public SqlColumnType[] getSqlColumnTypes() {
		return this.sct;
	}

	public void setSqlColumnTypes(SqlColumnType[] sct) {
		this.sct=sct;
	}
	
	public String[] getColumnNames(){
		String []columns=new String[this.getSqlColumnTypes().length];
		
		for(int i=0,len=this.getSqlColumnTypes().length;i<len;i++){
			columns[i]=this.getSqlColumnTypes()[i].getColumnName().toLowerCase();
		}
		return columns;
	}

	public JSON getListJSONArray() {
		return JSONUtils.listToJson((List<Object[]>)this.getData(), this.getColumnNames());
	}
	
	public SplitPageCollection(Object data,Integer totalCount,Integer currentPageNo,SqlColumnType[] sct){
		this.data=data;
		this.totalCount=totalCount;
		this.currentPageNo=currentPageNo;
		this.sct=sct;
	}
	/**
	 * 本页数据
	 */
	private Object data;
	
	/**
	 * 当前第几页
	 */
	private Integer currentPageNo = 0;

	/**
	 * 总页数
	 */
	private Integer totalPageCount = 0;
	
	/**
	 * 当前页条数
	 */
	private Integer currentPageCount = 0;

	/**
	 * 总记录数
	 */
	private Integer totalCount=0;

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public <T> T getData() {
		return (T)data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public Integer getCurrentPageCount() {
		return currentPageCount;
	}

	public void setCurrentPageCount(Integer currentPageCount) {
		this.currentPageCount = currentPageCount;
	}

	
	
}
