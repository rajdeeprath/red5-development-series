package org.red5.scopes.examples.scopelistener;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IServer;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
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
		server.addListener(new ScopeListener());
		
		return super.appStart(arg0);
	}

	
	
	@Override
	public void appStop(IScope arg0) {
		super.appStop(arg0);
	}



	@Override
	public void streamBroadcastClose(IBroadcastStream arg0) {
		IConnection broadcaster = Red5.getConnectionLocal();
		log.info("Connection {}", broadcaster);
	}



	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		IConnection broadcaster = Red5.getConnectionLocal();
		log.info("Connection {}", broadcaster);
	}



	@Override
	public void streamSubscriberClose(ISubscriberStream stream) {
		// TODO Auto-generated method stub
		super.streamSubscriberClose(stream);
	}



	@Override
	public void streamSubscriberStart(ISubscriberStream stream) {
		// TODO Auto-generated method stub
		super.streamSubscriberStart(stream);
	}
	

	
}
