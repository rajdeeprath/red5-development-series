package org.red5.websocket.examples.socketlistnerdemo;

import org.red5.logging.Red5LoggerFactory;
import org.red5.net.websocket.WebSocketPlugin;
import org.red5.net.websocket.WebSocketScope;
import org.red5.net.websocket.WebSocketScopeManager;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.plugin.PluginRegistry;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Application extends MultiThreadedApplicationAdapter implements ApplicationContextAware  {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class);
	
	private ApplicationContext applicationContext;
	
	
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }	
	
	
	
	@Override
    public boolean appStart(IScope scope) 
	 {
        log.info("Application starting");
        WebSocketUtils.configureApplicationScopeWebSocket(scope);
        return super.appStart(scope);
    }

	 
	 
	 
	 @Override
    public void appStop(IScope scope) {
        log.info("Application stopping");
        WebSocketUtils.deConfigureApplicationScopeSocket(scope);
        super.appStop(scope);
    }

}