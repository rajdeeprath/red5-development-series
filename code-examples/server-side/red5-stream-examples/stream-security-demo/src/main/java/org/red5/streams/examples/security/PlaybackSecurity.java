package org.red5.streams.examples.security;

import java.util.Map;

import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaybackSecurity implements IStreamPlaybackSecurity {

	private static Logger log = LoggerFactory.getLogger(PlaybackSecurity.class);
	
	private String expectedToken = "red5pro#publisher";
	
	@Override
	public boolean isPlaybackAllowed(IScope scope, String name, int start, int length, boolean flushPlaylist) 
	{
		log.info("playback request coming in for stream {}", name);
		
		IConnection requestingConnection = Red5.getConnectionLocal();
		Map<String, Object> params = Utils.getConnectionParameters(requestingConnection);
		
		if(params.containsKey("token"))
		{
			String token = String.valueOf(params.get("token"));
			if(token.equals(expectedToken))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	
	

}
