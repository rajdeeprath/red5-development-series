package org.red5.streams.examples.callbacks;



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
	public void streamBroadcastClose(IBroadcastStream stream) {
		log.info("Stream broadcast close {}", stream);
	}	

	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.info("Stream broadcast start {}", stream);
	}

	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		log.info("Stream record start {}", stream);
	}

	@Override
	public void streamRecordStop(IBroadcastStream stream) {
		log.info("Stream record stop {}", stream);
	}
	
	@Override
	public void streamSubscriberStart(ISubscriberStream stream) {
		log.info("Stream subscribe start {}", stream);
	}

	@Override
	public void streamSubscriberClose(ISubscriberStream stream) {
		log.info("Stream subscribe close {}", stream);
	}

}
