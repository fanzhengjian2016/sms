package com.shenjun.db.extractor;

import org.springframework.jdbc.core.PreparedStatementCreator;

public abstract class JboxPreparedStatementCreator implements PreparedStatementCreator {
	public Object[] params;
	
	@SuppressWarnings("unchecked")
	public <T> T getPrams(int i){
		return (T)this.params[i];
	}

	public JboxPreparedStatementCreator(Object...params){
		this.params=params;
	}
}
