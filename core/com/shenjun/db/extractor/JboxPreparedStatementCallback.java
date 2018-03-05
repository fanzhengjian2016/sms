package com.shenjun.db.extractor;

import org.springframework.jdbc.core.PreparedStatementCallback;

public abstract class JboxPreparedStatementCallback<T> implements PreparedStatementCallback<T>{
	public Object[] params;
	
	@SuppressWarnings("unchecked")
	public <T> T getPrams(int i){
		return (T)this.params[i];
	}
	
	public JboxPreparedStatementCallback(Object...params){
		this.params=params;
	}
}
