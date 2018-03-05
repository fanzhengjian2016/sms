package com.shenjun.util;

import java.util.ArrayList;
import java.util.List;

import com.shenjun.db.type.SqlType;
import com.shenjun.db.type.SqlValue;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.web.thread.Lc;

/**
 * 数据库字符串操作类
 * SqlString sql = new SqlString("select * from table where 1=1");
 * sql.append(" and cname=? ", "LL");
 * Object[] params=sql.toSqlParams();
 * String sqls=sql.toSqlStr()
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */
public class SqlString{
	/**
	 * Sql字符
	 */
	private StringBuilder sqlstr;
	
	/**
	 * 参数
	 */
	public List<Object> paramsLs=new ArrayList<Object>();
	
	/**
	 * 转出成字符串
	 * @return
	 */
	public String toSqlStr(){
		return this.sqlstr.toString();
	}
	/**
	 * 转出数据库参数
	 * @return
	 */
	public Object[] toSqlParams(){
		return this.paramsLs.toArray();
	}
	/**
	 * 转出数据库参数
	 * @return
	 */
	public SqlValue[] toSqlValueParams(){
		int len=this.paramsLs.size();
		SqlValue[] sv = new SqlValue[len];
		for(int i=0;i<len;i++){
			Object obj = this.paramsLs.get(i);
			sv[i]=SqlUtil.Csov(obj);
		}
		return sv;
	}
	
	/**
	 * 追求条件
	 * @param str 字段串
	 * @param params 解析?的参数
	 * @param 是否强行通过
	 */
	public SqlString append(String sql,Object params,boolean falg){
		if(falg||StringUtil.isNB(params)){
			sqlstr.append(" "+sql);
			paramsLs.add(params);
		}
		return this;
	}
	/**
	 * 
	 * @param str 字段串
	 * @param rname 请求的参数名
	 * @param sqlType 数据库类型
	 * @return
	 */
	public SqlString append(String sql,String rname,Integer sqlType){
		if(StringUtil.isNB(Lc.get(rname))){
			SqlValue sv=Struts2Util.getSvp(rname, sqlType);
			return append(sql,sv,true);
		}
		return this;
	}
	/**
	 * 
	 * @param str 字段串
	 * @param rname 请求的参数名
	 * @return
	 */
	public SqlString append(String sql,String rname){
		return this.append(sql, rname,SqlType.VARCHAR);
	}
	
	public SqlString(){
		sqlstr=new StringBuilder();
	}
	/**
	 * 带参数的构造
	 * @param sqlstr
	 */
	public SqlString(String...strings){
		this.sqlstr=new StringBuilder(StringUtil.separatorString(strings, " "));
	}
	
	/**
	 * 追加Sql字符串
	 * @param sql
	 * @return
	 */
	public SqlString append(String sql){
		this.sqlstr.append(sql);
		return this;
	}
	
	public String toString(){
		return this.toSqlStr();
	}
	
	/**
	 * 测试用例
	 * @param args
	 */
/*	public static void main(String[] args) {
//		SqlString sql = new SqlString("select * from table where 1=1");
//		sql.append(" and cname=? ", "LL");
//		@SuppressWarnings("unused")
//		String sqls=sql.toSqlStr();
//		@SuppressWarnings("unused")
//		Object[] params=sql.toSqlParams();
		
		String[] s =new String[]{"11","22"};
		System.out.println(StringUtil.separatorString(s, " "));
	}*/
	
	
}
