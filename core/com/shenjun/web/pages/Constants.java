package com.shenjun.web.pages;

import com.shenjun.util.ReadProperties;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class Constants {
	/**
	 * 查询列表视图中，查询条件进行恢复时的标致状态参数值名
	 */
	public static final String RESTORE_PARAM="_restore";
	
	public static final String SEARCH_PARAM="search";
	
	/**
	 * 系统默认的分页参数名称
	 */
	public static final String SPLIT_PAGE_CURRENTPAGENO_PARAM="currentPageNo";
	
	/**
	 * 系统默认的每页记录条数参数名称
	 */
	public static final String SPLIT_PAGE_CURRENTPAGESIZE_PARAM="pageSize";
	
	/**
	 *默认的分页数据尺寸 
	 */
	public static final int DEFAULT_PAGE_SIZE = new Integer(ReadProperties.getProperty("initPageNumber")) ;
}
