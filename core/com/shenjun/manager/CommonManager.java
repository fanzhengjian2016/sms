package com.shenjun.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.shenjun.collection.SuperList;
import com.shenjun.dao.JboxDaoSupport;
import com.shenjun.db.type.SqlColumnType;
import com.shenjun.db.type.SqlValue;
import com.shenjun.plugins.struts.Struts2Util;
import com.shenjun.util.SqlUtil;
import com.shenjun.web.thread.Lc;

/**
 * 常用的数据库操作类
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class CommonManager extends JboxDaoSupport{
	
    public DataSourceTransactionManager transactionManager;

	public DefaultTransactionDefinition def;
	
	public TransactionStatus status;
	
	
	/**
	 * 自动创建指定表的插入语句
	 * @param tableName 表名，值从LC中获取
	 * @return
	 */
	public Integer toArryInsertSql(String tableName){
		return this.toArryInsertSql(tableName,null,null);
	}
	
	/**
	 * 自动创建指定表的插入语句
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public Integer toArryInsertSql(String tableName,String[] columns){
		return this.toArryInsertSql(tableName,columns,null);
	}
	
	/**
	 * 自动创建指定表的插入语句
	 * @param tableName
	 * @param params
	 * @return
	 */
	public Integer toArryInsertSql(String tableName,Map<String,Object> params){
		List<String> ls = new ArrayList<String>();
		for(Map.Entry<String,Object> entry : params.entrySet()){
			ls.add(entry.getKey());
		}
		return this.toArryInsertSql(tableName,ls.toArray(new String[]{}),null);
	}
	
	/**
	 * 自动创建指定表的插入语句。值从Lc.get中获取
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public Integer toArryInsertSql(String tableName,String[] columns,Map<String,Object> params){
		SuperList<Object[]> sl=this.createSQLQuery("select * from "+tableName+" where 1=2");
		
		if(columns==null){
			columns=sl.getColumnNames();
		}
		
		Map<String,SqlColumnType> sctMap = sl.getSqlColumnTypeMap();
		
		List<SqlValue> ls=new ArrayList<SqlValue>();
		StringBuilder sbsql=new StringBuilder("insert into "+tableName+"(");
		StringBuilder fliedbuf = new StringBuilder();
		StringBuilder paramsbuf = new StringBuilder();
		for(String col:columns){
			
			SqlValue obj=null;
			if(params!=null){
				if(params.get(col) instanceof SqlValue){
					obj=(SqlValue) params.get(col);
				}else{
					obj=SqlUtil.Csov(params.get(col));
				}
			}else{
				obj=Lc.getSvp(col,sctMap.get(col.trim()).getColumnType());
			}
			if(obj!=null){
				ls.add(obj);
				fliedbuf.append(col+",");
				paramsbuf.append("?,");
			}
		}
		if(fliedbuf.length()>0)
			fliedbuf.replace(fliedbuf.length()-1, fliedbuf.length(), "");
		
		sbsql.append(fliedbuf.toString()+") values(");

		paramsbuf.replace(paramsbuf.length()-1, paramsbuf.length(), ")");
		return this.createExecSql(sbsql.toString()+paramsbuf.toString(),ls.toArray(new Object[]{}));
	}
	
	/**
	 * 自动创建指定表的更新语句
	 * @param tableName
	 * @param params
	 * @param afterCond
	 * @param values
	 * @return
	 */
	public Integer toArryUpdateSql(String tableName,Map<String,Object> params,String afterCond,SqlValue...values){
		List<String> ls = new ArrayList<String>();
		for(Map.Entry<String,Object> entry : params.entrySet()){
			ls.add(entry.getKey());
		}
		return toArryUpdateSql(tableName,ls.toArray(new String[]{}),params,afterCond,values);
	}
	
	/**
	 * 自动创建指定表的更新语句
	 * @param tableName
	 * @param columns
	 * @param afterCond
	 * @param values
	 * @return
	 */
	public Integer toArryUpdateSql(String tableName,String afterCond,SqlValue...values){
		return toArryUpdateSql(tableName,null,null,afterCond,values);
	}
	
	/**
	 *  自动创建指定表的更新语句
	 * @param tableName
	 * @param columns
	 * @param afterCond
	 * @param values
	 * @return
	 */
	public Integer toArryUpdateSql(String tableName,String[] columns,String afterCond,SqlValue...values){
		return toArryUpdateSql(tableName,columns,null,afterCond,values);
	}
	
	/**
	 * 自动创建指定表的更新语句。值从Lc.get中获取
	 * @param tableName
	 * @param columns
	 * @param afterCond
	 * @return
	 */
	public Integer toArryUpdateSql(String tableName,String[] columns,Map<String,Object> params,String afterCond,SqlValue...values){
		SuperList<Object[]> sl=this.createSQLQuery("select * from "+tableName+" where 1=2");
		
		if(columns==null){
			columns=sl.getColumnNames();
		}
		Map<String,SqlColumnType> sctMap = sl.getSqlColumnTypeMap();

		List<SqlValue> ls=new ArrayList<SqlValue>();
		StringBuilder sbsql=new StringBuilder("update "+tableName+" set  ");
		for(String column:columns){
			if(sctMap.get(column)==null)
				continue;
			
			SqlValue sv=null;
			
			if(params!=null){
				if(params.get(column) instanceof SqlValue){
					sv=(SqlValue) params.get(column);
				}else{
					sv=SqlUtil.Csov(params.get(column));
				}
			}else{
				sv=Struts2Util.getSvp(column,sctMap.get(column).getColumnType());
			}
			
			ls.add(sv);
			sbsql.append(column+"=?,");
		}
		
		for (int i = 0,len =values.length; i < len ; i++) {
			ls.add(values[i]);
		}
		
		sbsql.replace(sbsql.length()-1, sbsql.length(), " where 1=1 ");
		return this.createExecSql(sbsql.toString()+afterCond,ls.toArray(new Object[]{}));
	}
}
