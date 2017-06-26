package org.red5.connection.examples.stats;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IServer;
import org.red5.server.api.scope.IScope;
import org.red5.server.scope.WebScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	

	@Override
	public boolean appStart(IScope arg0) {
		log.info("Application start {}", arg0);	
		return super.appStart(arg0);
	}
	



	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connect {}", conn);		
		return super.appConnect(conn, params);
	}

	
	
	
	

	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnect {}", conn);
		logConnectionStatistics(conn);
		super.appDisconnect(conn);
	}
	
	
	
	
	
	/**
	 * logs out several important connection properties for a given IConnection object
	 * @param conn IConnection object
	 */
	private void logConnectionStatistics(IConnection conn) {
		
		log.info("Client connection Id : {}", conn.getSessionId());
		log.info("Client connection protocol : {}", conn.getProtocol());
		log.info("Client connection host : {}", conn.getHost());
		log.info("Client connection address : {}", conn.getRemoteAddress());
		log.info("Client connection last ping : {}", conn.getLastPingTime());
		log.info("Client connection path : {}", conn.getPath());
		log.info("Connection parameters passed by client : {}", conn.getConnectParams());
		log.info("Bytes read from client : {}", conn.getReadBytes());
		log.info("Bytes written to client : {}", conn.getWrittenBytes());
		log.info("Messages read from connection : {}", conn.getReadMessages());
		log.info("Messages written to connection : {}", conn.getWrittenMessages());
		log.info("Messages dropped : {}", conn.getDroppedMessages());
	}
	
}
