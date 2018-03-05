package com.plugins.mina;

import java.util.concurrent.Executors;

import org.apache.mina.filter.executor.ExecutorFilter;

public class JxExecutorFilter extends ExecutorFilter {
	public JxExecutorFilter(){
		//new OrderedThreadPoolExecutor();
		super(Executors.newCachedThreadPool());
		//super(new OrderedThreadPoolExecutor());
	}
}
