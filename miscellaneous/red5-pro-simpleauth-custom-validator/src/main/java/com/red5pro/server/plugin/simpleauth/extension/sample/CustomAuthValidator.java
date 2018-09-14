package com.red5pro.server.plugin.simpleauth.extension.sample;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IContext;
import org.red5.server.api.Red5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.server.plugin.simpleauth.interfaces.IAuthenticationValidator;

public class CustomAuthValidator implements IAuthenticationValidator {

	private static Logger logger = LoggerFactory.getLogger(CustomAuthValidator.class);
	
	private IContext context;    
    
    private MultiThreadedApplicationAdapter adapter;
    
	
	@Override
	public void initialize() 
	{
		
		logger.info("CustomAuthValidator initializing...");
		
		if(adapter != null){
			// Enable the line below to activate publish interceptor
            //adapter.registerStreamPublishSecurity(new PublishSecurity(this));
			
			// Enable the line below to activate playback interceptor
            //adapter.registerStreamPlaybackSecurity(new PlaybackSecurity(this));
        }else{
            logger.error("Something is wrong. i dont have access to application adapter");
        }
        
        logger.debug("adapter = {}", adapter);
        logger.debug("context = {}", context);
    }

	
	/**
	 * Return true or false to determine whether client can connect or not.
	 * Note : if you want to extract custom params its a good thing to check connection type first.
	 * If you want to do publish and playback validation, then make sure to return true in this method unconditionally. 
	 */
	@Override
	public boolean onConnectAuthenticate(String arg0, String arg1, Object[] arg2) {
		
		logger.info("Client attempting to connect...");
		
		IConnection conn = Red5.getConnectionLocal();
		
		if(ConnectionUtils.isRTC(conn))
		{
			// do something special
		}
		else if(ConnectionUtils.isRTMP(conn))
		{
			// do something special
		}
		else if(ConnectionUtils.isRTSP(conn))
		{
			// do something special
		}
		
		return true;
	}

	
	public MultiThreadedApplicationAdapter getAdapter() {
		return adapter;
	}

	
	public void setAdapter(MultiThreadedApplicationAdapter adapter) {
		this.adapter = adapter;
	}

	
	public IContext getContext() {
		return context;
	}

	public void setContext(IContext context) {
		this.context = context;
	}

}
