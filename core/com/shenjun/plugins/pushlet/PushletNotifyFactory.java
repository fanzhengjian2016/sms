package com.shenjun.plugins.pushlet;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Session;

import org.apache.log4j.Logger;

public class PushletNotifyFactory {
	private static Logger log = Logger.getLogger(PushletNotify.class);
	
	private PushletNotify[] list;
	
	public void execute(Session session,Event anEvent){
		if(list!=null){
			log.debug("find PushletNotify count is "+this.list.length);
			
			for(int i=0,len=this.list.length;i<len;i++){
				this.list[i].notify(session,anEvent);
			}
		}else{
			log.info("PushletNotify  List is null ");
		}
		
	}

	public void setList(PushletNotify[] list) {
		this.list = list;
	}
}
