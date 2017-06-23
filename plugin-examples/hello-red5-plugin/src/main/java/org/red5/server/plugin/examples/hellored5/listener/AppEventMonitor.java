package org.red5.server.plugin.examples.hellored5.listener;

import org.red5.server.adapter.IApplication;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppEventMonitor implements IApplication {
	
    private Logger log = LoggerFactory.getLogger(AppEventMonitor.class);


	@Override
	public boolean appStart(IScope app) {
		log.info("appStart : {}", app);
		return true;
	}

	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("appConnect : {}", conn);
		return true;
	}

	@Override
	public boolean appJoin(IClient client, IScope app) {
		IConnection conn = Red5.getConnectionLocal();
		log.info("appJoin : {} at scope {}", conn, app);
		return true;
	}

	@Override
	public void appDisconnect(IConnection conn) {
		log.info("appDisconnect : {}", conn);
	}

	@Override
	public void appLeave(IClient client, IScope app) {
		IConnection conn = Red5.getConnectionLocal();
		log.info("appLeave : {} at scope {}", conn, app);
	}

	@Override
	public void appStop(IScope app) {
		log.info("appStop : {}", app);
	}

	@Override
	public boolean roomStart(IScope room) {
		log.info("roomStart : {}", room);
		return true;
	}

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		log.info("roomConnect : {}", conn);
		return true;
	}

	@Override
	public boolean roomJoin(IClient client, IScope room) {
		IConnection conn = Red5.getConnectionLocal();
		log.info("roomJoin : {} at scope {}", conn, room);
		return true;
	}

	@Override
	public void roomDisconnect(IConnection conn) {
		log.info("roomDisconnect : {}", conn);
	}

	@Override
	public void roomLeave(IClient client, IScope room) {
		IConnection conn = Red5.getConnectionLocal();
		log.info("roomLeave : {} at scope {}", conn, room);
	}

	@Override
	public void roomStop(IScope room) {
		log.info("roomStop : {}", room);
	}

}
