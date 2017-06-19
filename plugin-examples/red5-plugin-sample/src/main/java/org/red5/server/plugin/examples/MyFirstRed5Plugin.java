package org.red5.server.plugin.examples;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.plugin.Red5Plugin;
import org.slf4j.Logger;

public class MyFirstRed5Plugin extends Red5Plugin {

    private Logger log = Red5LoggerFactory.getLogger(MyFirstRed5Plugin.class);

    public static final String NAME = "MyFirst-Red5-Plugin";

    
    @Override
    public String getName() {
        return MyFirstRed5Plugin.NAME;
    }
    
    
    @Override
	public void init() {
    	log.info(NAME + "init called");
	}


	@Override
	public void doStart() throws Exception {
		log.info(NAME + "doStart called");
	}


	@Override
	public void doStop() throws Exception {
		log.info(NAME + "doStop called");
	}

}
