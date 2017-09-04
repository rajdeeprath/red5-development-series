package org.red5.streams.examples.publishdetect;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);

	
	@Override
	public boolean appStart(IScope app) {
		this.registerStreamPublishSecurity(new StreamPublishDetect(app));
		return super.appStart(app);
	}

	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		return super.appConnect(conn, params);
	}

	@Override
	public void appDisconnect(IConnection conn) {
		super.appDisconnect(conn);
	}
}
