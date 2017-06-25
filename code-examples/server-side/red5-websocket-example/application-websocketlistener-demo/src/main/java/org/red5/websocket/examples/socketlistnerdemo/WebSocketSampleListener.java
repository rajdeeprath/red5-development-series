package org.red5.websocket.examples.socketlistnerdemo;

import org.red5.logging.Red5LoggerFactory;
import org.red5.net.websocket.WebSocketConnection;
import org.red5.net.websocket.listener.WebSocketDataListener;
import org.red5.net.websocket.model.WSMessage;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;

public class WebSocketSampleListener extends WebSocketDataListener {
	
	
	private static Logger log = Red5LoggerFactory.getLogger(WebSocketSampleListener.class);

	private IScope appScope;
	
	{
        setProtocol("subprotocol");
    }
	
	
	
	@Override
	public void onWSMessage(WSMessage message) {
		log.info("onWSMessage called, for message type {}", message.getMessageType().name());
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

}
