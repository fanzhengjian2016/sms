package com.shenjun.annotation.imp;
/**
 * 前台界面进行访问方法时的过滤接口，主要可用于前置的过滤或者权限检查之类的。
 * @author jbox_user
 *
 */
public interface Filter {
	/**
	 * 返回0 正确进入程序部分，否则进入重定向
	 * @param objects
	 * @return
	 */
	public int before(Object...objects);
	/**
	 * 
	 * @param objects
	 * @return
	 */
	public Object redirect(Object...objects);
	/**
	 * 
	 * @param objects
	 * @return
	 */
	public int after(Object...objects);
}
