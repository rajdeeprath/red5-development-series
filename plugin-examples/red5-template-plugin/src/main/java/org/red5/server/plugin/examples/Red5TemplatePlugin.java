package org.red5.server.plugin.examples;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.plugin.Red5Plugin;
import org.slf4j.Logger;

public class Red5TemplatePlugin extends Red5Plugin {

    private Logger log = Red5LoggerFactory.getLogger(Red5TemplatePlugin.class);

    public static final String NAME = "red5-template-plugin";

    
    @Override
    public String getName() {
        return Red5TemplatePlugin.NAME;
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
