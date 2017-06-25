package com.red5.example.template;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	
	
	
	@Override
	public boolean appStart(IScope app) {
		log.info("Application started : {}", app);
		return super.appStart(app);
	}







	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connect : {}",  conn);
		return super.appConnect(conn, params);
	}







	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnect : {}",  conn);
		super.appDisconnect(conn);
	}







	@Override
	public void appStop(IScope arg0) {
		log.info("Application stopped : {}", arg0);
		super.appStop(arg0);
	}
	


}
