package com.shenjun.db.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shenjun.db.decorator.MultiDataSource;
import com.shenjun.plugins.spring.SpringUtil;

/**
 * @author: 沈军
 * @category jjj
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */
public class DbConnections {
	private static final Log log = LogFactory.getLog(DbConnections.class);
	
	public static Connection getConn(){
		Object dsObj = SpringUtil.getBean("dataSource");
		try {
			if(dsObj!=null){
				MultiDataSource mds = (MultiDataSource)dsObj;
				return mds.getConnection();
			}else
				return null;
		} catch (Exception e) {
			log.error("获取默认的原始连接出错:"+e,e.getCause());
		}
		return null;
	}
	
	public static void closeAll(Connection conn){
		try {
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			log.error("关闭数据库原始连接时出错(Connection conn):"+e,e.getCause());
		}
	}
	
	public static void closeAll(Connection conn,PreparedStatement ps){
		try {
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			log.error("关闭数据库原始连接时出错(Connection conn,PreparedStatement ps):"+e,e.getCause());
		}
	}
	
	public static void closeAll(Connection conn,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			log.error("关闭数据库原始连接时出错(Connection conn,PreparedStatement ps):"+e,e.getCause());
		}
	}
	
	public static void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			log.error("关闭数据库原始连接时出错(Connection conn,PreparedStatement ps,ResultSet rs):"+e,e.getCause());
		}
	}
	
	public static void closeAll(Connection conn,Statement ps,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			log.error("关闭数据库原始连接时出错(Connection conn,PreparedStatement ps,ResultSet rs):"+e,e.getCause());
		}
	}
}
