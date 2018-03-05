package com.shenjun.db.extractor;

import org.springframework.jdbc.core.CallableStatementCallback;

public abstract class JboxCallableStatementCallback<T> implements CallableStatementCallback<T>{
	public Object[] params;
	
	@SuppressWarnings("unchecked")
	public <T> T getPrams(int i){
		return (T)this.params[i];
	}

	public JboxCallableStatementCallback(Object...params){
		this.params=params;
	}
}
