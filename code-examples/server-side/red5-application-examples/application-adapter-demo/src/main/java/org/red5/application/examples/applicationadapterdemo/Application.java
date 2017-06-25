package org.red5.application.examples.applicationadapterdemo;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
import org.slf4j.Logger;

public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class);
	

	@Override
	public boolean appStart(IScope app) {
		log.info("appStart {} ", app);
		return super.appStart(app);
	}

	@Override
	public void appStop(IScope app) {
		log.info("appStop {} ", app);
		super.appStop(app);
	}

	@Override
	public boolean roomStart(IScope room) {
		log.info("roomStart {} ", room);
		return super.roomStart(room);
	}

	@Override
	public void roomStop(IScope room) {
		log.info("roomStop {} ", room);
		super.roomStop(room);
	}

	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("appConnect {} {} ", conn, params);
		return super.appConnect(conn, params);
	}

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		log.info("roomConnect {} {} ", conn, params);
		return super.roomConnect(conn, params);
	}

	@Override
	public void appDisconnect(IConnection conn) {
		log.info("appDisconnect {} ", conn);
		super.appDisconnect(conn);
	}

	@Override
	public void streamBroadcastClose(IBroadcastStream stream) {
		log.info("streamBroadcastClose {} ", stream);
		super.streamBroadcastClose(stream);
	}

	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.info("streamBroadcastStart {} ", stream);
		super.streamBroadcastStart(stream);
	}

	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		log.info("streamRecordStart {} ", stream);
		super.streamRecordStart(stream);
	}

	@Override
	public void streamSubscriberClose(ISubscriberStream stream) {
		log.info("streamSubscriberClose {} ", stream);
		super.streamSubscriberClose(stream);
	}

	@Override
	public void streamSubscriberStart(ISubscriberStream stream) {
		log.info("streamSubscriberStart {} ", stream);
		super.streamSubscriberStart(stream);
	}

	
	
	


}
