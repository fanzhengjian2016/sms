package com.shenjun.db.decorator;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shenjun.plugins.spring.SpringUtil;
import com.shenjun.util.StringUtil;
import com.shenjun.web.thread.Lc;

public class JxJdbcTemplate  extends JdbcTemplate{
	
	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		String ds=Lc.getConnLocal();
		if(!StringUtil.isNB(ds)){
			return super.getDataSource();
		}
		
		return (DataSource) SpringUtil.getBean(ds);
	}

}
