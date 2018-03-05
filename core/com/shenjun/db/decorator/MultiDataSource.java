package com.shenjun.db.decorator;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.shenjun.system.SystemConfig;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.Lc;
import com.shenjun.web.thread.LocalContent;

/**
 * @author: 沈军
 * @category
 * {@link shenjunhappy@live.com }
 * 版权所有,严禁侵权.
 */

public class MultiDataSource implements DataSource,ApplicationContextAware {

	private static final Log log = LogFactory.getLog(MultiDataSource.class);
	private ApplicationContext applicationContext = null;
	private DataSource dataSource = null;
	
	private Map<String,DataSource> mutiDs = new HashMap<String,DataSource>();

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return getDataSource().getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		getDataSource().setLogWriter(arg0);
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		getDataSource().setLoginTimeout(arg0);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public DataSource getDataSource(String dataSourceName){
		log.debug("dataSourceName:("+dataSourceName+") Thread name:("+Thread.currentThread().getName()+")->");
		if(this.mutiDs.get(dataSourceName)==null){
			this.mutiDs.put(dataSourceName, (DataSource)this.applicationContext.getBean(dataSourceName));
		}
		return this.mutiDs.get(dataSourceName);
	}
	
	public void setDataSource(DataSource dataSource) {
		if(!StringUtil.isNB(Lc.getConnLocal())){
			Lc.setConnLocal(SystemConfig.DEFAULT_DB);
		}
		this.mutiDs.put(Lc.getConnLocal(), dataSource);
		this.dataSource = dataSource;
	}

	public DataSource getDataSource(){
		String beanName=LocalContent.getConnLocal();
		return getDataSource(beanName);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}