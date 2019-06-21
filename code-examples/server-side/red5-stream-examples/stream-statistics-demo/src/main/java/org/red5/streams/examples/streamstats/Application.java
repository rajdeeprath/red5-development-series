package org.red5.streams.examples.streamstats;



import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.statistics.IClientBroadcastStreamStatistics;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.util.ScopeUtils;
import org.red5.streams.examples.streamstats.model.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	
	private IScope appScope;
	
	private Gson gson;
	
	

	@Override
	public boolean appStart(IScope app) {
		this.appScope = app;
		this.gson = new Gson();
		
		return super.appStart(app);
	}
	
	
	
	
	/**
	 * Fetch stream statistics for a broadcast stream. 
	 *
	 * <p>
	 * NOTE: If using this example with Red5 pro, make sure to use `IProStream`
	 * instead of `ClientBroadcastStream`. You can access `IProStream` by including
	 * <a href="https://github.com/red5pro/red5pro-common">red5pro-common</a> dependency
	 * in the project.
	 * </p>
	 * 
	 * @param stream
	 * @return
	 */
	private Stats getStreamStatistics(IBroadcastStream stream) {
		
		ClientBroadcastStream cStream = (ClientBroadcastStream) stream;
		IClientBroadcastStreamStatistics obj = cStream.getStatistics();
		
		Stats stats = new Stats();
		stats.setName(obj.getPublishedName());
		stats.setBytesReceived(obj.getBytesReceived());
		stats.setActiveSubscribers(obj.getActiveSubscribers());
		stats.setTotalSubscribers(obj.getTotalSubscribers());
		stats.setMaxSubscribers(obj.getMaxSubscribers());
		
		log.info("Returning stream stats {} for stream {}", stats, stream.getPublishedName());
		
		return stats;
	}
	
	
	
	
	/**
	 * Fetch stream statistics for stream at application level
	 * 
	 * @param streamName
	 * @return
	 */
	public Object getStreamStatistics(String streamName){
		IBroadcastStream stream = this.getBroadcastStream(appScope, streamName);
		Stats stats = getStreamStatistics(stream);
		return gson.toJson(stats);
	}
	
	
	
	
	
	/**
	 * Fetch stream statistics for stream at sub-scope level
	 * 
	 * @param streamName
	 * @param scopePath
	 * @return
	 * @throws Exception
	 */
	public Object getStreamStatitics(String streamName, String scopePath) throws Exception{
		IScope room = getSubScope(appScope, scopePath);
		IBroadcastStream stream = this.getBroadcastStream(room, streamName); 
		Stats stats = getStreamStatistics(stream);
		return gson.toJson(stats);
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
}
