package com.shenjun.plugins.pushlet;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Session;

public interface PushletNotify {
	
	public void notify(Session session,Event anEvent);
}
