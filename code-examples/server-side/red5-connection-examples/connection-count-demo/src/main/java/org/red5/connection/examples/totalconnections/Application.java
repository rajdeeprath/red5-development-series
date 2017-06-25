package org.red5.connection.examples.totalconnections;



import java.util.Set;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	
	private IScope appScope;
	
	
	

	@Override
	public boolean appStart(IScope app) {
		this.appScope = app;
		return super.appStart(app);
	}
	



	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connect {}", conn);
		
		getTotalConnections(appScope);
		
		return super.appConnect(conn, params);
	}

	
	
	
	

	

	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
		
		log.info("Client connect to room {}", conn);
		
		getTotalConnections(conn.getScope());
		
		return super.roomConnect(conn, params);
	}




	@Override
	public void roomDisconnect(IConnection conn) {
		
		log.info("Client disconnect from room {}", conn);
		
		getTotalConnections(conn.getScope());
		
		super.roomDisconnect(conn);
	}




	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnect {}", conn);
		
		getTotalConnections(appScope);
		
		super.appDisconnect(conn);
	}

	
	
	
	private void getTotalConnections(IScope scope){
		
		int total = 0;
		Set<IConnection> connections = scope.getClientConnections();
		for(IConnection connection : connections){
			if(connection.isConnected()){
				total++;
			}
		}
		
		log.info("Total connections currently in scope {} = {}", scope.getPath(), total);
	}
	
	
}
