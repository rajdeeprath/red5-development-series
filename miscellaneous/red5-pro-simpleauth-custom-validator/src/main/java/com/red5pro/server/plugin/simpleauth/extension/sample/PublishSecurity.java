package com.red5pro.server.plugin.simpleauth.extension.sample;

import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishSecurity implements IStreamPublishSecurity {

    private static Logger logger = LoggerFactory.getLogger(PublishSecurity.class);
    
    private CustomAuthValidator customAuthValidator;

    
    public PublishSecurity(CustomAuthValidator customAuthValidator) {
		this.setCustomAuthValidator(customAuthValidator);
	}


    
	@Override
    public boolean isPublishAllowed(IScope scope, String name, String mode) 
    {
		logger.info("Client attempting to publish stream...");
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
