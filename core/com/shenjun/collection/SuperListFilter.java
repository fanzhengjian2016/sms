package com.shenjun.collection;
/**
 * @description 过滤值
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public abstract class SuperListFilter {
	
	public abstract Object doValue(Object o,int index,String columnName);
	
	private boolean enableDoValue = true;
	
	
	public boolean isEnableDoValue() {
		return enableDoValue;
	}

	public void setEnableDoValue(boolean enableDoValue) {
		this.enableDoValue = enableDoValue;
	}

	/**
	 * 数据库中总条数
	 */
	public Integer totalCount=0;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	/********************************************/
	private Long beginRIndex=0L;
	
	private Long endRIndex=1000000000000000000L;
	
	public Long getBeginRIndex() {
		return beginRIndex;
	}

	public void setBeginRIndex(Long beginRIndex) {
		this.beginRIndex = beginRIndex;
	}

	public Long getEndRIndex() {
		return endRIndex;
	}

	public void setEndRIndex(Long endRIndex) {
		this.endRIndex = endRIndex;
	}

	

	
	/**
	 * 设置Result取值范围,索引值，例如（1，2）从二行开始，从索引1的行开始，不包含2索引行，总共取值一行
	 * @param beginRNum
	 * @param endRNum
	 */
	public void setBeginEndNum(Long beginRIndex,Long endRIndex){
		this.beginRIndex = beginRIndex;
		this.endRIndex = endRIndex;
	}
}
