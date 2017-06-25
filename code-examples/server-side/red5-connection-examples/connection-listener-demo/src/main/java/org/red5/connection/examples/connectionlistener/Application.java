package org.red5.connection.examples.connectionlistener;



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
		
		
		WebScope ws = (WebScope) arg0;
		IServer server = ws.getServer();
		log.info("server {}", server);
		server.addListener(new ConnectionMonitor());
		
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
		super.appDisconnect(conn);
	}
	
}
