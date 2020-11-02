package com.red5.application.examples.webrtclive;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	@Override
	public boolean appStart(IScope app) {
		
		/* 
		 * FOLLOWING code is optional when running the application inside Red5 Pro,
		 * and is only required when you run the application in Red5 Pro open source
		 * for WebSocket support.
		 * 
		 * SEE METHOD OF WebSocketUtils CLASS FOR EXACT CODE.
		 * 
		 * WebSocketUtils.configureApplicationScopeWebSocket(app);
		 */
		
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
		/* 
		 * FOLLOWING code is optional when running the application inside Red5 Pro,
		 * and is only required when you run the application in Red5 Pro open source
		 * for WebSocket support. 
		 * 
		 * SEE METHOD OF WebSocketUtils CLASS FOR EXACT CODE.
		 * 
		 * WebSocketUtils.deConfigureApplicationScopeSocket(app);
		 */
		
		log.info("Application stopped");
		super.appStop(app);
	}
	
	
	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.info("Stream broadcast start {}", stream);
		super.streamBroadcastClose(stream);
	}
	
	

	@Override
	public void streamBroadcastClose(IBroadcastStream stream) {
		log.info("Stream broadcast close {}", stream);
		super.streamBroadcastClose(stream);
	}



	@Override
	public void streamPublishStart(IBroadcastStream stream) {
		log.info("Stream publish start {}", stream);
		super.streamPublishStart(stream);
	}



	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		log.info("Stream record start {}", stream);
		super.streamRecordStart(stream);
	}

	@Override
	public void streamRecordStop(IBroadcastStream stream) {
		log.info("Stream record stop {}", stream);
		super.streamRecordStop(stream);
	}
	
	@Override
	public void streamSubscriberStart(ISubscriberStream stream) {
		log.info("Stream subscribe start {}", stream);
		super.streamSubscriberStart(stream);
	}

	@Override
	public void streamSubscriberClose(ISubscriberStream stream) {
		log.info("Stream subscribe close {}", stream);
		super.streamSubscriberClose(stream);
	}
}
