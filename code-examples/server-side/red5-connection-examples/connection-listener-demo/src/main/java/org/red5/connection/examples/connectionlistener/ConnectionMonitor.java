package org.red5.connection.examples.connectionlistener;

import org.red5.server.api.IConnection;
import org.red5.server.api.listeners.IConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionMonitor implements IConnectionListener {

	private static Logger log = LoggerFactory.getLogger(ConnectionMonitor.class);

	
	
	@Override
	public void notifyConnected(IConnection conn) {
		log.info("Client connected {} to scope {}", conn, conn.getScope());	
	}

	
	
	@Override
	public void notifyDisconnected(IConnection conn) {
		log.info("Client disconnected {} to scope {}", conn, conn.getScope());
	}

}
