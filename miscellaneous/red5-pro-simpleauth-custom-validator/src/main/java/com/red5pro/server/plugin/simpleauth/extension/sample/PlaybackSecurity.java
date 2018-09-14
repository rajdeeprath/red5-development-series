package com.red5pro.server.plugin.simpleauth.extension.sample;

import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaybackSecurity implements IStreamPlaybackSecurity {
    
    private static Logger logger = LoggerFactory.getLogger(PlaybackSecurity.class);
    
    private CustomAuthValidator customAuthValidator;

    
    public PlaybackSecurity(CustomAuthValidator customAuthValidator) {
		this.customAuthValidator = customAuthValidator;
	}


	@Override
    public boolean isPlaybackAllowed(IScope scope, String name, int start, int length, boolean flushPlaylist) 
    {
		logger.info("Client attempting to playback stream...");
		
        return true;
    }


	/**
	 * Gets reference of custom validator main class
	 * @return
	 */
	public CustomAuthValidator getCustomAuthValidator() {
		return customAuthValidator;
	}


	
	/**
	 * Sets reference to custom validator main class
	 * 
	 * @param customAuthValidator
	 */
	public void setCustomAuthValidator(CustomAuthValidator customAuthValidator) {
		this.customAuthValidator = customAuthValidator;
	}

}
