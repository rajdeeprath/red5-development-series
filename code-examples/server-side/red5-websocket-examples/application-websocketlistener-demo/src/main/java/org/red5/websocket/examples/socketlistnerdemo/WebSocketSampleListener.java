package org.red5.websocket.examples.socketlistnerdemo;

import java.util.concurrent.atomic.AtomicBoolean;

import org.red5.logging.Red5LoggerFactory;
import org.red5.net.websocket.WebSocketConnection;
import org.red5.net.websocket.listener.WebSocketDataListener;
import org.red5.net.websocket.model.WSMessage;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class WebSocketSampleListener extends WebSocketDataListener implements InitializingBean, ApplicationContextAware {
	
	
	private static Logger log = Red5LoggerFactory.getLogger(WebSocketSampleListener.class);
	
	private ApplicationContext applicationContext;
	
    private static AtomicBoolean initializing = new AtomicBoolean(false);

	private IScope appScope;
	
	
	
	public WebSocketSampleListener() {
	}
	
	
	public WebSocketSampleListener(IScope appScope) {
	     setAppScope(appScope);
	}
	
	
	
	@Override
	public void onWSMessage(WSMessage message) {
		log.info("onWSMessage called, for message type {}", message);
	}

	@Override
	public void onWSConnect(WebSocketConnection conn) {
		log.info("onWSConnect called, for connection {}", conn);
	}

	@Override
	public void onWSDisconnect(WebSocketConnection conn) {
		log.info("onWSDisconnect called, for connection {}", conn);
	}

	@Override
	public void stop() {
		log.info("listener stopping");
	}

	public IScope getAppScope() {
		return appScope;
	}

	public void setAppScope(IScope appScope) {
		this.appScope = appScope;
	}

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		
	}

	
	@Override
    public void afterPropertiesSet() throws Exception {
        if (appScope != null) {
            initialize();
        }
    }
	
	
	
	private void initialize() {
        if (initializing.compareAndSet(false, true)) {
        	
        	log.info("Initializing");
        	
            setProtocol("json");
            MultiThreadedApplicationAdapter adapter = (MultiThreadedApplicationAdapter) appScope.getHandler();
            
        }
    }

}