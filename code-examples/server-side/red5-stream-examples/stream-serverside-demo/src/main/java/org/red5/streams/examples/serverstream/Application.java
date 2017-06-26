package org.red5.streams.examples.serverstream;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IServerStream;
import org.red5.server.api.stream.support.SimplePlayItem;
import org.red5.server.api.stream.support.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	private String streamName = "tv";
	

	@Override
	public boolean appStart(IScope app) {
		
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		               initServerSidePlaylist(app, streamName);
		            }
		        }, 
		        10000 
		);
		
		return super.appStart(app);
	}
	
	
	
	
	/**
	 * initializes a server side stream
	 * 
	 * @param scope
	 * @param name
	 */
	private void initServerSidePlaylist(IScope scope, String name){
		
		IServerStream serverStream = StreamUtils.createServerStream(scope, name);
		
		String filename = "BladeRunner2049.flv"; // this should be in the streams directory
		SimplePlayItem item = SimplePlayItem.build(filename);
		serverStream.addItem(item);
		
		serverStream.setRewind(false);
		serverStream.start();	
	}
}
