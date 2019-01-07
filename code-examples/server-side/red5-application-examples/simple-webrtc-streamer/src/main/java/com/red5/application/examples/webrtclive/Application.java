package com.red5.application.examples.webrtclive;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	

	@Override
	public boolean appStart(IScope app) {
		WebSocketUtils.configureApplicationScopeWebSocket(app);
		log.info("Application started");
		return super.appStart(app);
	}



	@Override
	public boolean appConnect(IConnection arg0, Object[] arg1) {
		log.info("Client connecting");
		return super.appConnect(arg0, arg1);
	}



	@Override
	public void appDisconnect(IConnection arg0) {
		log.info("Client disconnecting");
		super.appDisconnect(arg0);
	}



	@Override
	public void appStop(IScope app) {
		WebSocketUtils.deConfigureApplicationScopeSocket(app);
		log.info("Application stopped");
		super.appStop(app);
	}
}
