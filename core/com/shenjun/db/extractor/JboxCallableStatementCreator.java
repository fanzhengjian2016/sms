package com.shenjun.db.extractor;

import org.springframework.jdbc.core.CallableStatementCreator;

public abstract class JboxCallableStatementCreator implements CallableStatementCreator {
	
	public Object[] params;
	
	@SuppressWarnings("unchecked")
	public <T> T getPrams(int i){
		return (T)this.params[i];
	}
	
	public JboxCallableStatementCreator(Object...params){
		this.params=params;
	}

}
