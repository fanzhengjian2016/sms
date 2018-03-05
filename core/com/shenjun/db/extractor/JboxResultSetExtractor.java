package com.shenjun.db.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class JboxResultSetExtractor<T> implements ResultSetExtractor<T> {
	
	public Object[] params;
	
	@SuppressWarnings("unchecked")
	public <T> T getPrams(int i){
		return (T)this.params[i];
	}

	public JboxResultSetExtractor(Object...params){
		this.params=params;
	}
}
