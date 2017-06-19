package org.red5.scopes.examples.appmonitor;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;

public class ApplicationMonitor implements IApplication {
	
	private static Logger log = Red5LoggerFactory.getLogger(ApplicationMonitor.class);


	@Override
	public boolean appStart(IScope app) {
		log.info("appStart "  +app.toString());
		return true;
	}

	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		
		log.info("appConnect : Scope "  +conn.getScope().toString());
		return true;
	}

	@Override
	public boolean appJoin(IClient client, IScope app) {
		
		log.info("appJoin : Scope "  +app.toString());
		return true;
	}

	@Override
	public void appDisconnect(IConnection conn) {
		
		log.info("appDisconnect : Scope "  +conn.getScope().toString());
	}

	@Override
	public void appLeave(IClient client, IScope app) {
		
		log.info("appDisconnect : Scope "  + app.toString());
	}

	@Override
	public void appStop(IScope app) {
		
		log.info("appStop : Scope "  + app.toString());
	}

	@Override
	public boolean roomStart(IScope room) {
		
		log.info("roomStart : Scope "  + room.toString());
		return true;
	}

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		
		log.info("roomConnect : Scope "  + conn.getScope().toString());
		return true;
	}

	@Override
	public boolean roomJoin(IClient client, IScope room) {
		
		log.info("roomJoin : Scope "  + room.toString());
		return true;
	}

	@Override
	public void roomDisconnect(IConnection conn) {
		
		log.info("roomDisconnect : Scope "  + conn.getScope().toString());
	}

	@Override
	public void roomLeave(IClient client, IScope room) {
		
		log.info("roomLeave : Scope "  + room.toString());
	}

	@Override
	public void roomStop(IScope room) {
		
		log.info("roomLeave : Scope "  + room.toString());
	}

}
