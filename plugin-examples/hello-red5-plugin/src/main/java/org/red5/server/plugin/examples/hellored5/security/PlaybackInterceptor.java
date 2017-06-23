package org.red5.server.plugin.examples.hellored5.security;

import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaybackInterceptor implements IStreamPlaybackSecurity {

    private Logger log = LoggerFactory.getLogger(PlaybackInterceptor.class);

    
	@Override
	public boolean isPlaybackAllowed(IScope scope, String name, int start, int length, boolean flushPlaylist) {
		log.info("Playback attempt detected");		
		return true;
	}

}
