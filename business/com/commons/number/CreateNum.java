package com.commons.number;

import com.shenjun.collection.SuperList;
import com.shenjun.db.type.SqlType;
import com.shenjun.db.type.SqlValue;
import com.shenjun.enums.SqlServerType;
import com.shenjun.manager.CommonManager;
import com.shenjun.number.SequenceClass;
import com.shenjun.util.SqlUtil;
import com.shenjun.web.thread.Lc;

/**
 * @description 
 * @author shenjun
 * {@link sj_enginner@sina.com}
 * 版权个人所有，严禁侵权。
 */

public class CreateNum {
	/**
	 * 
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param cm
	 * @return
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static synchronized String sqlCreateNumber(String pattern,String tableName,String columnName,CommonManager cm){
		SqlValue[] svs= cm.createSvCall("pd_create_num",
				SqlUtil.Csov(tableName),
				SqlUtil.Csov(columnName),
				SqlUtil.Csov(pattern),
				new SqlValue(SqlType.VARCHAR));
		String num=svs[3].getValue();
		return num;
	}
	
	/**
	 * 创建多个数组
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param cm
	 * @return
	 */
	@SuppressWarnings("unused")
	private static synchronized String[] sqlCreateNumbers(String pattern,String tableName,String columnName,int count,CommonManager cm){
		SuperList<Object[]> sl = Lc.getConn().createCall("pd_create_nums", tableName,columnName,pattern,10);
		if(sl!=null&&sl.size()>0){
			int len=sl.size();
			String[] nums = new String[len];
			for(int i=0;i<len;i++){
				nums[i]=sl.get(i)[0]+"";
			}
			return nums;
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static synchronized String javaCreateNumber(String pattern,String tableName,String columnName){
		String pat=pattern;
		
		pat=pat.replaceAll("\\[", "");
		pat=pat.replaceAll("\\]", "");
		
		
		String lengthStr="len";
		CommonManager cm=Lc.getConn();
		if(cm.getDBType()==SqlServerType.MYSQL){
			 lengthStr="length";
		}
		@SuppressWarnings("rawtypes")
		SuperList<Object[]> list = cm.createSQLQuery("select max("+columnName+") from "+tableName+" where "+lengthStr+"("+columnName+")= "+pat.length()+" GROUP BY "+columnName+"  order by "+columnName+" desc");
		return SequenceClass.getNumber(pattern, list.size()==0 || list.get(0, 0)==null?"":list.get(0, 0)+"");
	}
	
	/**
	 * return Custom number
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @return String
	 */
	public static synchronized String getCustomNumber(String pattern,String tableName,String columnName){
		return sqlCreateNumber(pattern,tableName,columnName,Lc.getConn());
	}
	
	/**
	 * 获取主键组
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param count
	 * @return
	 */
	public static synchronized String[] getCustomNumbers(String pattern,String tableName,String columnName,int count){
		return sqlCreateNumbers(pattern,tableName,columnName,count,Lc.getConn());
	}
	
	/**
	 *  获取主键
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param dbName 数据库标识名
	 * @return
	 */
	public static synchronized String getCustomNumber(String pattern,String tableName,String columnName,String dbName){
		return sqlCreateNumber(pattern,tableName,columnName,Lc.getConn(dbName));
	}
	
	/**
	 * 获取主键组
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param count 
	 * @param dbName 数据库标识名
	 * @return
	 */
	public static synchronized String[] getCustomNumbers(String pattern,String tableName,String columnName,int count,String dbName){
		return sqlCreateNumbers(pattern,tableName,columnName,count,Lc.getConn(dbName));
	}
	
	/**
	 * 获取主键
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param cm 数据库对象
	 * @return
	 */
	public static synchronized String getCustomNumber(String pattern,String tableName,String columnName,CommonManager cm){
		return sqlCreateNumber(pattern,tableName,columnName,cm);
	}
	
	/**
	 * 获取主键组
	 * @param pattern
	 * @param tableName
	 * @param columnName
	 * @param count 主键个数
	 * @param cm 数据库对象
	 * @return
	 */
	public static synchronized String[] getCustomNumbers(String pattern,String tableName,String columnName,int count,CommonManager cm){
		return sqlCreateNumbers(pattern,tableName,columnName,count,cm);
	}
	
	
	public static synchronized Integer getCustomNumber(String tablename,String idname){
		String sql="select max(cast("+idname+" as int)) from "+tablename;
		SuperList<Object[]> list = Lc.getConn().createSQLQuery(sql);
		if(null==list.get(0, 0)){
			return 1;
		}else{
			Integer bigid=Integer.parseInt(list.get(0, 0)+"");
			return bigid+1;
		}
		
	}
}
