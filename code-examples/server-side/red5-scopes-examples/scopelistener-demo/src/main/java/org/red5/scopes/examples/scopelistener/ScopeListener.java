package org.red5.scopes.examples.scopelistener;

import org.red5.server.api.listeners.IScopeListener;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScopeListener implements IScopeListener {

	private static Logger log = LoggerFactory.getLogger(ScopeListener.class);

	
	@Override
	public void notifyScopeCreated(IScope arg0) {
		log.info("Scope created {}", arg0);
	}

	@Override
	public void notifyScopeRemoved(IScope arg0) {
		log.info("Scope removed {}", arg0);
	}

}
