package com.shenjun.plugins.spring;

import org.apache.log4j.Logger;

/**
 * Spring init load class
 * @author shenjun
 *
 */
public class InitExecuteFactory{
	private static Logger log = Logger.getLogger(InitExecuteFactory.class);
	
	private Compile[] initList;
	
	public void execute(){
		if(initList!=null){
			log.debug("find Compile count is "+this.initList.length);
			for(int i=0,len=this.initList.length;i<len;i++){
				this.initList[i].build();
			}
		}else{
			log.error("InitExecuteFactory  initList is null ");
		}
		
	}

	public void setInitList(Compile[] initList) {
		this.initList = initList;
	}
}
