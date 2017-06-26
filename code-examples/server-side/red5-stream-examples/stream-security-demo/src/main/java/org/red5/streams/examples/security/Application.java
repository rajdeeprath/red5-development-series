package org.red5.streams.examples.security;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	

	@Override
	public boolean appStart(IScope app) {
		log.info("Application started {}", app);
		
		/* Register Security Handlers */
		
		this.registerStreamPublishSecurity(new PublishSecurity());
		this.registerStreamPlaybackSecurity(new PlaybackSecurity());
		
		return super.appStart(app);
	}



	@Override
	public void appStop(IScope app) {
		log.info("Application stopped {}", app);
		super.appStop(app);
	}



	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connecting {}", conn);
		
		if(Utils.isRTSP(conn)){
			conn.setAttribute("connectParams", params);
		}
		
		return super.appConnect(conn, params);
	}



	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnecting {}", conn);
		super.appDisconnect(conn);
	}
	
}
