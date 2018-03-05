package com.shenjun.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.shenjun.annotation.AopAspectj;
import com.shenjun.collection.SplitPageCollection;
import com.shenjun.collection.SuperArrayList;
import com.shenjun.collection.SuperList;
import com.shenjun.collection.SuperListFilter;
import com.shenjun.db.extractor.JboxCallableStatementCallback;
import com.shenjun.db.extractor.JboxCallableStatementCreator;
import com.shenjun.db.extractor.JboxPreparedStatementCallback;
import com.shenjun.db.extractor.JboxPreparedStatementCreator;
import com.shenjun.db.extractor.JboxResultSetExtractor;
import com.shenjun.db.type.SqlServer;
import com.shenjun.db.type.SqlServerMap;
import com.shenjun.db.type.SqlType;
import com.shenjun.db.type.SqlValue;
import com.shenjun.enums.InputType;
import com.shenjun.enums.SqlServerType;
import com.shenjun.exception.JException;
import com.shenjun.util.SerializableUtil;
import com.shenjun.util.SqlUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.LocalContent;


/**
 * 数据访问抽象类，提供基本的数据访问方法
 * 
 * @author 沈军
 * @version 1.0
 * 
 */
@AopAspectj
public class JboxDaoSupport extends JdbcDaoSupport{
	
	public  Log log = LogFactory.getLog(this.getClass());
	
	public void createTest(){
		
		System.out.println(Lc.getConnLocal());
	}
	
//	@Autowired
//	private SqlXmlHandler sqlXmlHandler;
	
//	@Autowired
//	private DefaultLobHandler lobHandler;
	
	private String key;
	
	public String getConnKey(){
		return key;
	}
	
	public void setConnKey(String key){
		this.key=key;
	}
	
	private Connection conn;

	@Deprecated
	public Connection getConnection() throws SQLException{
		if(conn==null){
			conn=this.getDataSource().getConnection();
		}
		return conn;
	}
	/**
	 * exist connection
	 * @return
	 */
	public boolean isConnn(){
		return conn==null?false:true;
	}
	
	
	@Override
	public void releaseConnection(Connection con) {
		super.releaseConnection(con);
		conn=null;
	}

	@Override
	protected DataSource getDataSource() {
		return super.getDataSource();
	}

	/**
	 * 获取数据库类型
	 * @return DataBaseType
	 */
	public SqlServerType getDBType(){
		SqlServer sqlServer = SqlServerMap.get(key);
		
		if(sqlServer==null){
			log.error("SupperDao<getDBType> error:key not find object("+key+")");
		} 
		
		return sqlServer.getSqlServerType();
	}
	
	
	/**
	 * 通过Hibernate的createSQLQuery创建本地Sql
	 * @param sql
	 * @param gird_id 标识ID
	 * @param is_search 是否查询
	 * @param firstResult 从多少条开始取数据
	 * @param maxResults 取多少条
	 * @param values 这一个参数可传可以不传(采用Jdk1.5新特性)
	 * @return List<Object> 
	 */
	@Deprecated
	public SplitPageCollection createSplitPageSQLQuery(String sql ,String gird_id,boolean is_search,Integer firstResult,Integer maxResults, Object...values ){
		SuperList<Object[]> ls = null;
		
		log.info(SqlUtil.toExecString(sql, values));
		
		String path="cache/search/"+LocalContent.getUser().getUserNo()+"/";
		String serName=gird_id+".dat";
		if(is_search){
			ls = createSQLQuery(sql,values);
			if(StringUtil.isNB(gird_id)){
				SerializableUtil.save(ls, path,serName);
			}
		}else{
			ls=SerializableUtil.get(path, serName);
		}
		Integer count=ls.size(),toindex=firstResult+maxResults;
		if(toindex>count)
			toindex=count;
		
		return new SplitPageCollection(ls.subList(firstResult,toindex),count,firstResult,ls.getSqlColumnTypes());
	}
	
	/**
	 * 通过Hibernate的createSQLQuery创建本地Sql（过时，请采用createSQLQuery）
	 * @param sql
	 * @param values 这一个参数可传可以不传(采用Jdk1.5新特性)
	 * @return SuperList<Object> 
	 */
	@Deprecated
	public SuperList<Object[]> createSuperSQLQuery(String sql , Object...values ){
		return this.createSQLQuery(sql,null,values);
	}
	
	/**
	 * 提供普通查询与Mssql存储过程的执行
	 * @param sql
	 * @param slf
	 * @param prepare true 普通Sql false存储过程（mssqlserver）
	 * @param values
	 * @return
	 */
	private SuperList<Object[]> createMsSqlCall(String sql ,SuperListFilter slf, boolean prepare, Object...values){
		log.info(SqlUtil.toExecString(sql, values));
		
		if(prepare){
			return this.getJdbcTemplate().query(sql, new  JboxResultSetExtractor<SuperList<Object[]>>(slf) {
				
				@Override
				public SuperList<Object[]> extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					SuperListFilter slf=this.getPrams(0);
					SuperList<Object[]> sl = new SuperArrayList<Object[]>();
					try {
						SqlUtil.makeSuperList(sl, rs,slf);
					} catch (IOException e) {
						log.error("createMsSqlCall error:"+e.getMessage(),e.getCause());
						LocalContent.setError(e.getMessage());
					}
					return sl;
				}
			}, SqlUtil.toValue(values));
		}else{
			return this.getJdbcTemplate().execute(new JboxCallableStatementCreator(sql,values) {
				
				@Override
				public CallableStatement createCallableStatement(Connection con)
						throws SQLException {
					String sql=(String) this.getPrams(0);
					CallableStatement cs = con.prepareCall(sql);
					
					Object[] values=this.getPrams(1);
					for (int i = 0; i < values.length; i++) {
						if(values[i] instanceof SqlValue){
							SqlValue sv=(SqlValue)(values[i]);
							cs.setObject(i+1, sv.getValue(),sv.getSqlType());
						}else if(values[i] instanceof Date){
							cs.setDate(i+1, new java.sql.Date(((Date)(values[i])).getTime()));
						}else{
							cs.setObject(i+1, values[i]);
						}
					}
					return cs;
				}
			}, new JboxCallableStatementCallback<SuperList<Object[]>>(slf) {

				@Override
				public SuperList<Object[]> doInCallableStatement(CallableStatement cs)
						throws SQLException, DataAccessException {
					cs.execute();
					
					ResultSet rs = cs.getResultSet();
					if(rs==null){
						for(int i=0,len=1000;i<len;i++){
							if(rs == null && cs.getMoreResults()){
								rs = cs.getResultSet();
								break;
							}
						}
					}
					if(rs==null){
						try {
							throw new com.shenjun.exception.JException("get ResultSet is null..", this.getClass());
						} catch (JException e) {
							log.error("createMsSqlCall error:"+e.getMessage(),e.getCause());
							LocalContent.setError(e.getMessage());
						}
					}
					SuperListFilter slf=this.getPrams(0);
					SuperList<Object[]> sl = new SuperArrayList<Object[]>();
					try {
						SqlUtil.makeSuperList(sl, rs,slf);
					} catch (IOException e) {
						log.error("createMsSqlCall error:"+e.getMessage(),e.getCause());
						LocalContent.setError(e.getMessage());
					}
					return sl;
				}
			});
		}
	}
	
	/**
	 * 通过的createSQLQuery创建本地Sql
	 * @param sql
	 * @param SuperListFilter 过滤参数值使用
	 * @param values 这一个参数可传可以不传(采用Jdk1.5新特性)
	 * @return SuperList<Object> 
	 */
	public SuperList<Object[]> createSQLQuery(String sql ,SuperListFilter slf, Object...values ){
		return createMsSqlCall(sql,slf,true,values);
	}
	
	/**
	 * 通过Hibernate的createSQLQuery创建本地Sql
	 * @param sql
	 * @param values 这一个参数可传可以不传(采用Jdk1.5新特性)
	 * @return List<Object> 
	 */
	@AopAspectj
	public SuperList<Object[]> createSQLQuery(String sql , Object...values ){
		return this.createSQLQuery(sql,null,values);
	}
	
	
	/**
	 * 通过Hibernate的createSQLQuery创建本地Sql
	 * @param sql
	 * @param values 这一个参数可传可以不传(采用Jdk1.5新特性)
	 * @return Int(影响的行数)
	 */
	public int createExecSql(String sql , Object...values ){
		log.info(SqlUtil.toExecString(sql, values));
		return this.getJdbcTemplate().update(sql, SqlUtil.toValue(values));
	}
	
	/**
	 * 执行批量语句
	 * @param sql
	 * @param ls<纯Object对象，可以使用SqlValue对象>
	 * @return
	 */
	public int[] createBatchSvSql(String sql,List<Object[]> ls){
		
		List<Object[]> params = new ArrayList<Object[]>();
		for(Object[] objects:ls){
			params.add(SqlUtil.toValue(objects));
		}
		
		return createBatchSql(sql,params);
	}
	
	/**
	 * 执行批量语句
	 * @param sql
	 * @param ls<纯Object对象，不可以使用SqlValue对象>
	 * @return
	 */
	public int[] createBatchSql(String sql,List<Object[]> ls){
		return this.getJdbcTemplate().batchUpdate(sql, ls);
	}
	
	
	/**
	 * 执行本地SQl语句进行执行
	 * @param sql
	 * @param values
	 * @return 返回是否成功
	 */
	public boolean createSql(String sql,SqlValue...values){
		log.info(SqlUtil.toExecString(sql, (Object[])values));
		this.getJdbcTemplate().execute(new JboxPreparedStatementCreator(sql) {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				// TODO Auto-generated method stub
				return con.prepareStatement((String) this.getPrams(0));
			}
		},new JboxPreparedStatementCallback<Boolean>(sql,values) {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				SqlValue[] values=this.getPrams(1);
				for (int i = 0,len=values.length; i < len; i++) {
					ps.setObject(i+1, values[i].getValue(), values[i].getSqlType());	
				}
				//如果第一个结果是 ResultSet 对象，则返回 true；如果第一个结果是更新计数或者没有结果，则返回 false 
				return ps.execute((String) this.getPrams(0));
			}
		});
		
		return true;
	}
	/**
	 * 执行存储过程,返回游标式的表结构
	 * @param callSql
	 * @param inputObj
	 * @return
	 */
	public SuperList<Object[]> createCall(String callSql , Object... params){
		return this.createCall(callSql,null,params);
	}
	
	/**
	 * 执行存储过程,oracle返回游标式的表结构,mssql返回表
	 * @param callSql
	 * @param inputObj
	 * @param SuperListFilter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SuperList<Object[]> createCall(String callSql ,SuperListFilter slf, Object... params){
		log.info(SqlUtil.toExecString(callSql, params));
		if(this.getDBType()==SqlServerType.ORACLE){
			return (SuperList<Object[]>)(
					(
						this.createCall(
								callSql, params, 
								new Integer[]{SqlType.CURSOR}
						)
					)[0]
			);
		}else if(this.getDBType()==SqlServerType.MSSQL||this.getDBType()==SqlServerType.MYSQL){
			StringBuilder builder = new StringBuilder();
			builder.append("{call  ");
			builder.append(callSql+"");
			
			if(params!=null&&params.length>0){
				builder.append(" (");
			}
			
			for(int i=0;i<params.length;i++){
				builder.append("?,");
			}
			
			if(params!=null&&params.length>0){
				builder.deleteCharAt(builder.toString().length()-1);
				builder.append(" )");
			}
			builder.append("}");
			return this.createMsSqlCall(builder.toString(), null, false, params);
		}
		return null;
	}
	
	/**
	 * 这是一个执行存储过程
	 * 返回参数在Sqlvalue里存储
	 * @param callSql
	 * @param sv
	 */
	public SqlValue[] createSvCall(String callName,SqlValue... sv){
		return createSvCall(callName,null,sv);
	}
	/**
	 * 这是一个执行存储过程 返回参数在Sqlvalue里存储
	 * @param callName
	 * @param slf 过滤对象
	 * @param sv
	 * @return
	 */
	public SqlValue[] createSvCall(String callName,SuperListFilter slf,SqlValue... sv){
		StringBuilder sql = new StringBuilder("{call "+callName);
		if(sv.length==0){
			sql.append(" }");
		}else{
			sql.append(" (");
			for(int i=0,len=sv.length;i<len;i++){
				sql.append("?,");
			}
			sql.deleteCharAt(sql.toString().length()-1);
			sql.append(")}");
		}
		
		log.info("call ->"+sql.toString());
		
		return this.getJdbcTemplate().execute(new JboxCallableStatementCreator(sql.toString()) {
			@Override
			public CallableStatement createCallableStatement(Connection con)
					throws SQLException {
				// TODO Auto-generated method stub
				return con.prepareCall((String) this.getPrams(0));
			}
		},new JboxCallableStatementCallback<SqlValue[]>(sql.toString(),sv,slf){

			@Override
			public SqlValue[] doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				SqlValue[] sv=this.getPrams(1);
				for(int i=0,len=sv.length;i<len;i++){
					if(sv[i].getInputType() == InputType.IN){
						cs.setObject(i+1, sv[i].getValue());
					}else{
						cs.registerOutParameter(i+1, sv[i].getSqlType());
					}
					sv[i].setIndex(i+1);
				}
				
				cs.execute();
				
				for(int i=0,len=sv.length;i<len;i++){
					if(sv[i].getInputType() == InputType.OUT){
						if(sv[i].getSqlType() == SqlType.CURSOR){
							SuperList<Object[]> sl = new SuperArrayList<Object[]>();
							try {
								SqlUtil.makeSuperList(sl, (ResultSet)(cs.getObject(i+1)), (SuperListFilter)this.getPrams(2));
							} catch (IOException e) {
								log.error("createSvCall程序运行错误:"+e,e.getCause());
								LocalContent.setError(e.getMessage());
							}
							sv[i].setValue(sl);
						}else{
							sv[i].setValue(cs.getObject(i+1));
						}
					}
				}
				return sv;
			}
		});
	}
	
	/**
	 * 这是一个执行存储过程,返回相关的参数
	 * @param callSql 执行一个存储过储的名字
	 * @param inputObj 输入参数的数组
	 * @param types 输入返回的结果的集的Int型数组
	 * @return 返回数据库执行结果的数组
	 */
	@Deprecated
	public Object[] createCall(String callName , Object [] inputObj, Integer[] types){
		SqlValue[] sv = new SqlValue[inputObj.length+types.length];
		for(int i=0,len=inputObj.length;i<len;i++){
			sv[i]=SqlUtil.Csov(inputObj[i]);
		}
		for(int i=0,len=types.length;i<len;i++){
			sv[i+inputObj.length]=new SqlValue(types[i]);
		}
		this.createSvCall(callName, sv);
		
		List<Object> obj = new ArrayList<Object>();
		for(int i=0,len=sv.length;i<len;i++){
			if(sv[i].getInputType()==InputType.OUT){
				obj.add(sv[i].getValue());
			}
		}
		return obj.toArray();
		
	}
}
