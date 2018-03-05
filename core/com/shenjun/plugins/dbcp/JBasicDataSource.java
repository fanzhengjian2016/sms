package com.shenjun.plugins.dbcp;

import org.apache.commons.dbcp.BasicDataSource;

public class JBasicDataSource extends  BasicDataSource{
	
	/**
     * The fully qualified Java class name of the JDBC driver to be used.
     */
    protected String driverClass = null;
    
    /**
     * <p>Sets the jdbc driver class name.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param driverClassName the class name of the jdbc driver
     */
    public synchronized void setDriverClass(String driverClass) {
       this.setDriverClassName(driverClass);
    }
    
    protected String jdbcUrl = null;
    
    public synchronized void setJdbcUrl(String jdbcUrl) {
        this.setUrl(jdbcUrl);
    }
    
    protected String user = null;
    
    public synchronized void setUser(String user) {
        this.setUsername(user);
    }
    
    protected String description = null;
    
    public synchronized void setDescription(String description) {
        this.description=description;
    }
    
    
    

}
