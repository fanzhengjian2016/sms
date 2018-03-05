package com.shenjun.web.pages;


/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class Search<T>{
	
	private T searchData;

	private String restore;

	private int currentPageNo = 1;
	

	private int pageSize = Constants.DEFAULT_PAGE_SIZE;

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getRestore() {
		return restore;
	}

	public void setRestore(String restore) {
		this.restore = restore;
	}

	public T getSearchData() {
		return searchData;
	}

	public void setSearchData(T searchData) {
		this.searchData = searchData;
	}

}
