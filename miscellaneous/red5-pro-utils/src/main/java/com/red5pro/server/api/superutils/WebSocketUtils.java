package com.red5pro.server.api.superutils;

import org.red5.net.websocket.WSConstants;
import org.red5.net.websocket.WebSocketPlugin;
import org.red5.net.websocket.WebSocketScope;
import org.red5.net.websocket.WebSocketScopeManager;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.plugin.PluginRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketUtils {
	
	private static Logger log = LoggerFactory.getLogger(WebSocketUtils.class);
	
	
	
    public static void configureApplicationScopeWebSocket(IScope scope) {

        // first get the websocket plugin
        WebSocketPlugin wsPlugin = ((WebSocketPlugin) PluginRegistry.getPlugin(WebSocketPlugin.NAME));
        // get the websocket scope manager for the red5 scope
        WebSocketScopeManager manager = wsPlugin.getManager(scope);

        if (manager == null) {
            // get the application adapter
            MultiThreadedApplicationAdapter app = (MultiThreadedApplicationAdapter) scope.getHandler();
            log.debug("Creating WebSocketScopeManager for {}", app);
            // set the application in the plugin to create a websocket scope manager for it
            wsPlugin.setApplication(app);
            // get the new manager
            manager = wsPlugin.getManager(scope);
        }

        // the websocket scope
        WebSocketScope wsScope = (WebSocketScope) scope.getAttribute(WSConstants.WS_SCOPE);

        // check to see if its already configured
        if (wsScope == null) {
            log.debug("Configuring application scope: {}", scope);
            // create a websocket scope for the application
            wsScope = new WebSocketScope(scope);
            // register the ws scope
            wsScope.register();
        }
    }
    
    
    
    
    public static void deConfigureApplicationScopeSocket(IScope scope)
    {
    	WebSocketScopeManager manager = ((WebSocketPlugin) PluginRegistry.getPlugin("WebSocketPlugin")).getManager(scope);
        manager.removeApplication(scope);
        manager.stop();
    }

}
