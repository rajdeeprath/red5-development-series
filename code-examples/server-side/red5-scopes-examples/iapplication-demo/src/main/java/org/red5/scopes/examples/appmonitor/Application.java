package org.red5.scopes.examples.appmonitor;



import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class);


	@Override
	public boolean appStart(IScope arg0) {
		this.addListener(new ApplicationMonitor());
		return super.appStart(arg0);
	}

	
	
	@Override
	public void appStop(IScope arg0) {
		// TODO Auto-generated method stub
		super.appStop(arg0);
	}
	


}
