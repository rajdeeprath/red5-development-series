package org.red5.server.plugin.examples;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.plugin.Red5Plugin;
import org.slf4j.Logger;

public class HelloRed5Plugin extends Red5Plugin {

    private Logger log = Red5LoggerFactory.getLogger(HelloRed5Plugin.class);

    public static final String NAME = "hello-red5-plugin";

    
    @Override
    public String getName() {
        return HelloRed5Plugin.NAME;
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
