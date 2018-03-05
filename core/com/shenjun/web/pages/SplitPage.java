package com.shenjun.web.pages;

import java.util.Collection;

/**
 * @author: 沈军
 * @category 分页的基类 {@link shenjunhappy@live.com }
 *  版权所有,严禁侵权.
 */
public class SplitPage<T> {

	public SplitPage() {

	}
	
	/**
	 * 搜索条件的数据
	 */
	private T searchData;

	/**
	 * 本页数据
	 */
	private Collection<?> data;

	/**
	 * 当前第几页
	 */
	private int currentPageNo = 0;

	/**
	 * 总页数
	 */
	private int totalPageCount = 0;

	/**
	 * 总记录数
	 */
	private int totalCount=0;

	/**
	 * 当前页第一条数据的位置,从0开始
	 */
	private int start=0;

	/**
	 * 每页的记录数
	 */
	private int pageSize=0;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage = true;

	/**
	 * 是否有上一页
	 */
	private boolean hasPreviousPage = true;

	public SplitPage(int start, int pageSize, int currentPageNo,
			int totalCount, Collection<?> data, String url, String param) {
		this.start = start;
		this.pageSize = pageSize;
		this.currentPageNo = currentPageNo;
		this.totalCount = totalCount;
		this.data = data;
		this.url = url;
		if (param != null && param != "") {
			this.param = param;
		}
		this.totalPageCount = this.totalCount / this.pageSize;
		int mod = this.totalCount % this.pageSize;
		if (mod > 0) {
			this.totalPageCount++;
		}
		if (this.totalPageCount == 0
				|| this.totalPageCount == this.currentPageNo) {
			this.hasNextPage = false;
		}
		if (1 == this.currentPageNo) {
			this.hasPreviousPage = false;
		}
	}

	public SplitPage(int start, int pageSize, int currentPageNo,
			int totalCount, Collection<?> data, String url) {
		this(start, pageSize, currentPageNo, totalCount, data, url, null);
	}

	public Collection<?> getData() {
		return this.data;
	}

	public final boolean isHasNextPage() {
		return hasNextPage;
	}

	public final boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final int getStart() {
		return start;
	}

	public final int getTotalCount() {
		return totalCount;
	}

	public final int getCurrentPageNo() {
		return currentPageNo;
	}

	public final int getTotalPageCount() {
		return totalPageCount;
	}

	private String url = ""; // 用于分页超链接要用的字符串参数

	private String param = Constants.SPLIT_PAGE_CURRENTPAGENO_PARAM;

	protected String getParamString() {
		String paramstr = "";
		int pos = url.lastIndexOf("?");
		int len = url.length();
		if (pos == -1) {
			paramstr = "?";
		} else if (pos < len) {
			paramstr = "&";
		}
		return paramstr;
	}

	/**
	 * 取得简单的分页字符信息
	 * 
	 * @return
	 */
	public String getSimplePageString() {
		String paramstr = getParamString();

		StringBuffer bufstr = new StringBuffer();
		int countPager=data==null?0:data.size();
		bufstr.append("本页<span class=\"cOrage\">" + countPager + "</span>条记录&nbsp;");
		bufstr.append("共<span class=\"cOrage\">" + this.getTotalCount() + "</span>条记录&nbsp;&nbsp;");
		bufstr.append("第<span class=\"cOrage\">" + this.getCurrentPageNo() + "</span>页&nbsp;&nbsp;");
		bufstr.append("共<span class=\"cOrage\">" + this.getTotalPageCount() + "</span>页&nbsp;&nbsp;");

		if (this.hasPreviousPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getFirstPage() + "&"+Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM+"="+pageSize+"&"+ Constants.RESTORE_PARAM
					+ "=true" + "\"><img src=\"../../images/main/page_home_19.gif\" width=\"53\" height=\"23\" border=\"0\" align=\"absmiddle\" /></A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getPrePage() + "&"+Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM+"="+pageSize+"&" + Constants.RESTORE_PARAM
					+ "=true" + "\"><img src=\"../../images/main/page_prev_21.gif\" width=\"62\" height=\"23\" border=\"0\" align=\"absmiddle\" /></A>&nbsp;&nbsp;");
		} else {
			bufstr.append("<img src=\"../../images/main/page_home_19.gif\" width=\"53\" height=\"23\" border=\"0\" align=\"absmiddle\" />&nbsp;&nbsp;");
			bufstr.append("<img src=\"../../images/main/page_prev_21.gif\" width=\"62\" height=\"23\" border=\"0\" align=\"absmiddle\" />&nbsp;&nbsp;");
		}
		if (this.hasNextPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getNextPage() + "&"+Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM+"="+pageSize+"&" + Constants.RESTORE_PARAM
					+ "=true" + "\"><img src=\"../../images/main/page_next_23.gif\" width=\"62\" height=\"23\" border=\"0\" align=\"absmiddle\" /></A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getLastPage() + "&"+Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM+"="+pageSize+"&" + Constants.RESTORE_PARAM
					+ "=true" + "\"><img src=\"../../images/main/page_end_25.gif\" width=\"53\" height=\"23\" border=\"0\" align=\"absmiddle\" /></A>&nbsp;&nbsp;");
		} else {
			bufstr.append("<img src=\"../../images/main/page_next_23.gif\" width=\"62\" height=\"23\" border=\"0\" align=\"absmiddle\" />&nbsp;&nbsp;");
			bufstr.append("<img src=\"../../images/main/page_end_25.gif\" width=\"53\" height=\"23\" border=\"0\" align=\"absmiddle\" />&nbsp;&nbsp;");
		}
		return bufstr.toString();
	}

	public String getPageString() {
		String paramstr = getParamString();

		StringBuffer bufstr = new StringBuffer();
		bufstr.append(this.getSimplePageString());
		bufstr.append("&nbsp;&nbsp;&nbsp;&nbsp;转到：");
		bufstr
				.append("<select id='_gotoPageCount' onchange=\"window.location.href='"
						+ url
						+ paramstr
						+ param
						+ "='+this.value+'&"
						+ Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM+"="+pageSize+"&"
						+ Constants.RESTORE_PARAM + "=true'\">");
		String select = "";
		for (int i = 1; i <= this.totalPageCount; i++) {
			if (i == this.currentPageNo) {
				select = " selected ";
			} else {
				select = "";
			}
			bufstr.append("<option " + select + " value=" + i + " >第" + i
					+ "页</option>");
		}
		bufstr.append("</select>");

		bufstr.append("&nbsp;&nbsp;&nbsp;&nbsp;");

		bufstr
				.append("<select id='_gotoPage' onchange=\"window.location.href='"
						+ url
						+ paramstr
						+ param
						+ "="
						+ this.getFirstPage()
						+ "&"
						+ Constants.RESTORE_PARAM
						+ "=true&"
						+ Constants.SPLIT_PAGE_CURRENTPAGESIZE_PARAM
						+ "='+this.value+''\">");
		select = "";
		for (int i = 1; i <= 5; i++) {
			if (this.pageSize == (Constants.DEFAULT_PAGE_SIZE * i)) {
				select = " selected ";
			} else {
				select = "";
			}
			bufstr.append("<option " + select + " value="
					+ Constants.DEFAULT_PAGE_SIZE * i + " >每页"
					+ (Constants.DEFAULT_PAGE_SIZE * i) + "条记录</option>");
		}
		if(100000 == this.pageSize){
			bufstr.append("<option selected value=100000 >显示全部记录</option>");
		}else{
			bufstr.append("<option  value=100000 >显示全部记录</option>");
		}
		bufstr.append("</select>");

		return bufstr.toString();
	}

	private final int getLastPage() {
		return this.totalPageCount;
	}

	private final int getNextPage() {
		if ((this.currentPageNo + 1) > this.totalPageCount) {
			return this.totalPageCount;
		}
		return this.currentPageNo + 1;
	}

	private final int getPrePage() {
		if ((this.currentPageNo - 1) < 1) {
			return 1;
		}
		return this.currentPageNo - 1;
	}

	private final int getFirstPage() {
		return 1;
	}

	public final String getUrl() {
		return url;
	}

	public String getParam() {
		return param;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public T getSearchData() {
		return searchData;
	}

	public void setSearchData(T searchData) {
		this.searchData = searchData;
	}

}
