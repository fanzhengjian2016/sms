package com.shenjun.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.xdb.XMLType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.collection.SuperList;
import com.shenjun.collection.SuperListFilter;
import com.shenjun.db.type.SqlColumnType;
import com.shenjun.db.type.SqlType;
import com.shenjun.db.type.SqlValue;

/**
 * 数据库工具类
 * @author shenjun
 */
public class SqlUtil {
	private static final Log log = LogFactory.getLog(SqlUtil.class);
	
	/**
	 * 将包含sqlvalue对象的参数转成value值
	 * @param values 包含sqlvalue对应的值
	 * @return
	 */
	public static Object[] toValue(Object...values){
		Object[] params = new Object[values.length];
		for(int i=0,len=params.length;i<len;i++){
			if(values[i] instanceof SqlValue){
				params[i]=((SqlValue)values[i]).getValue();
			}else{
				params[i]=values[i];
			}
		}
		return params;
	}
	
	/**
	 * 用于打印输出，将参数与Sql语句格式化输出
	 * @param sql
	 * @param values
	 * @return
	 */
	public static String toExecString(String sql , Object...values){
		StringBuilder info = new StringBuilder("Thread name:["+Thread.currentThread().getName()+"],sql:["+sql+"] params:[");
		int i =0;
		for(Object obj:values){
			i++;
			String v="";
			
			if(obj instanceof SqlValue){
				v = ((SqlValue)obj).getValue()+"";
			}else{
				v=obj+"";
			}
			
			info.append("{("+i+"):"+v+"},");
		}
		info.append("]");
		return info.toString();
	}
	
	/**
	 * 将Table的列转成List<Map<String,Object>>，主要用于变成JSON字符串使用
	 * @param sct
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> sqlColumnsToJson(SqlColumnType[] sct){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(SqlColumnType sc: sct){
			Map<String,Object> jb = new java.util.LinkedHashMap<String,Object>();
			jb.put("name", sc.getColumnName().toLowerCase());
			jb.put("text", sc.getLocalText());
			jb.put("displaysize", sc.getColumnDisplaySize());
			jb.put("type", sc.getColumnTypeName().toLowerCase());
			jb.put("typecode", sc.getColumnType());
			jb.put("sign", sc.getSign());
			list.add(jb);
		}
		
		return list;
	}
	
	/**
	 * 将ColumnName，ColumnName分割,(过时，请使用StringUtil.join)
	 * @param scts
	 * @return
	 */
	@Deprecated
	public static String sqlColumnTypeToText(SqlColumnType[] scts){
		StringBuilder sb= new StringBuilder();
		for(SqlColumnType sct : scts){
			sb.append(sct.getColumnName()+",");
		}
		return sb.toString();
	}
	
	/**
	 * ClobToString 将Clob类型转成字符串类型
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String ClobToString(Clob clob) throws SQLException,IOException {
		String reString = "";
		if(clob!=null){
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			while (s != null) {
				sb.append(s+"\n");
				s = br.readLine();
			}
			reString = sb.toString();
		}
		return reString;
	}
	
	/**
	 * 将字符型转成相应的数据库类型
	 * @param value
	 * @param sqlType 【如：SqlType.NUMERIC 请到SqlType对应值】
	 */
	public static SqlValue Csv(String value,Integer sqltype){
		SqlValue sv = null;
		if(sqltype==SqlType.NUMERIC){
			sv=new SqlValue(
					StringUtil.isNB(value)?
							new java.math.BigDecimal(value):null,SqlType.NUMERIC);
		}else if(sqltype==SqlType.INTEGER){
			sv=new SqlValue(
					StringUtil.isNB(value)?
							new java.lang.Integer(value):null,SqlType.INTEGER);
		}else if(sqltype==SqlType.DATE||sqltype==SqlType.TIMESTAMP){
			sv=new SqlValue(
					StringUtil.isNB(value)?
							new java.sql.Timestamp(
									DateUtil.toDate(value).getTime()):null,sqltype);
		}else if(sqltype==SqlType.XML){
			sv=new SqlValue(
					StringUtil.isNB(value)?
							value:null,SqlType.XML);
		}else{
			sv=new SqlValue(
					value, sqltype);
		}
		return sv;
	}
	
	/**
	 * 将常用的数据类型对象转换成Sql标准的SqlValue类型
	 * @param value
	 * @return
	 */
	public static SqlValue Csov(Object value){
		SqlValue sv = null;
		if(value instanceof java.math.BigDecimal){
			sv=new SqlValue(
					value, SqlType.NUMERIC);
		}else if(value instanceof java.lang.Integer){
			sv=new SqlValue(
					value, SqlType.INTEGER);
		}else if(value instanceof java.util.Date){
			sv=new SqlValue(
					StringUtil.isNB(value)?
							new java.sql.Timestamp(
									((java.util.Date)value).getTime()):null, SqlType.DATE);
		}else if(value instanceof SqlValue){
			sv=(SqlValue)value;
		}else{
			sv=new SqlValue(
					value, SqlType.VARCHAR);
		}
		return sv;
	}
	
	/**
	 * 制作Superlist【对象集大小不能超过一百万，由于内在消耗太大，否则跳出】
	 * @param sl 存储SL的容器
	 * @param rs 包含结果的对象集
	 * @param slf 用于过滤与提取参数的对象集，See：SuperListFilter
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static SuperList<Object[]> makeSuperList(SuperList<Object[]> sl,ResultSet rs,SuperListFilter slf) throws SQLException, IOException{
		ResultSetMetaData rmd = rs.getMetaData();
		Integer count=rmd.getColumnCount();
		
		SqlColumnType[] sct = new SqlColumnType[count];
		for (int i = 1; i < count+1; i++) {
			sct[i-1]=new SqlColumnType(
					rmd.getColumnName(i),
					rmd.getColumnType(i),
					rmd.getColumnTypeName(i),
					rmd.getColumnDisplaySize(i)
			);
		}
		sl.setSqlColumnTypes(sct);
		
		int rowsNum=0;
		int limit=0;
		
		while(rs.next()){
			/**
			 * for(int i=0;i<20;i++){
					if(i>=8&&i<12){
						
					}else{
						continue;
					}
					System.out.println(i);
				}
			 */
			if(slf!=null){
				if(rowsNum>=slf.getBeginRIndex()&&rowsNum<slf.getEndRIndex()){
					
				}else{
					rowsNum++;
					continue;
				}
			}
			
			rowsNum++;
			
			limit++;
			
			Object[] objs = new Object[count];
			for(int i = 0 ; i < count;i++ ){
				try{
					if(sct[i].getColumnType()==SqlType.CLOB){
						objs[i]=SqlUtil.ClobToString(rs.getClob(i+1));
					}else if(sct[i].getColumnType()==SqlType.DATE){
						objs[i]=rs.getTimestamp(i+1);
					}else if(sct[i].getColumnType()==SqlType.XML){
						if(rs.getObject(i+1)==null)
							objs[i]=null;
						else{
							oracle.sql.OPAQUE op=(oracle.sql.OPAQUE)(rs.getObject(i+1));
							objs[i]=XMLType.createXML(op).getStringVal();
						}
					}else{
						objs[i]=(rs.getObject(i+1));
					}
					if(slf!=null&&slf.isEnableDoValue()){
						objs[i]=slf.doValue(objs[i], i, sct[i+1].getColumnName());
					}
				}catch(Exception e){
					log.error("sqlutil makeSuperList "+e.getMessage(),e.getCause());
				}

			}
			sl.add(objs);
			
			if(limit>1000000){
				log.error(">>>>>>>>>您一次性取值超过百万行.请检查程序逻辑。>>>>>>>>>>>>>>>>");
			}
		}
		if(slf!=null){
			slf.setTotalCount(rowsNum);
			sl.setListFilter(slf);
			log.info("获取从索引"+slf.getBeginRIndex()+"到"+slf.getEndRIndex()+",总计："+rowsNum+"条。");
		}
		return sl;
	}
}
