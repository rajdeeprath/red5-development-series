package org.red5.streams.examples.liverecord;



import java.io.IOException;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.util.ScopeUtils;
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
	public void streamBroadcastClose(IBroadcastStream stream) {
		log.info("Stream broadcast close {}", stream);
	}

	
	
	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.info("Stream broadcast start {}", stream);
		
		ClientBroadcastStream bStream = (ClientBroadcastStream) stream;
		if(!bStream.isRecording()) {
			try 
			{
				bStream.saveAs(bStream.getPublishedName(), false);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	
	
	private void streamBroadcastStop(IBroadcastStream stream) {
		ClientBroadcastStream bStream = (ClientBroadcastStream) stream;
		if(bStream != null && bStream.isRecording()) {
			bStream.stopRecording();
		}
	}
	
	
	
	/**
	 * Stop record method for application level streams 
	 * @param streamName
	 */
	public void stopRecord(String streamName){
		IBroadcastStream stream = getBroadcastStream(appScope, streamName);
		streamBroadcastStop(stream);
	}
	
	
	
	/**
	 * Stop record method for sub-scope level streams
	 * 
	 * @param streamName
	 * @param scopePath : Scope path is relative to the application path & should not start with a slash (/) 
	 * @throws Exception 
	 */
	public void stopRecord(String streamName, String scopePath) throws Exception{
		IScope roomScope = getSubScope(appScope, scopePath);
		IBroadcastStream stream = getBroadcastStream(roomScope, streamName);
		streamBroadcastStop(stream);
	}
	
	
	
	
	/**
	 * Fetch the room scope using the parent scope and the room scope path
	 * 
	 * @param parent : The parent scope
	 * @param subScopePath : The room scope path
	 * @return
	 * @throws Exception
	 */
	private IScope getSubScope(IScope parent, String subScopePath) throws Exception {
        IScope roomScope = ScopeUtils.resolveScope(parent, subScopePath);
        if (roomScope == null)
            throw new Exception("Scope for path " + subScopePath + " could not be resolved.");
        return roomScope;
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
