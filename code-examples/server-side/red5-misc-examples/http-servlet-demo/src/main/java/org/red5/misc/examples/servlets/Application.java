package org.red5.misc.examples.servlets;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	

	@Override
	public boolean appStart(IScope app) {
		return super.appStart(app);
	}
}
