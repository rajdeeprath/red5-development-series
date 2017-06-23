package org.red5.server.plugin.examples.hellored5.security;

import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishInterceptor implements IStreamPublishSecurity {

    private Logger log = LoggerFactory.getLogger(PublishInterceptor.class);

	
	@Override
	public boolean isPublishAllowed(IScope scope, String name, String mode) {
		log.info("publish attempt detected");
		return true;
	}

}
