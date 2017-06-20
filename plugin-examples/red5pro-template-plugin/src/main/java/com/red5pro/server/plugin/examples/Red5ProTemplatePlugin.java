package com.red5pro.server.plugin.examples;

import java.io.IOException;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.red5pro.plugin.Red5ProPlugin;

public class Red5ProTemplatePlugin extends Red5ProPlugin {

    private Logger log = Red5LoggerFactory.getLogger(Red5ProTemplatePlugin.class);

    public static final String NAME = "red5pro-template-plugin";

    
    @Override
    public String getName() {
        return Red5ProTemplatePlugin.NAME;
    }


	@Override
	public void activateCluster() {
		// TODO Auto-generated method stub
		log.info(NAME + "activateCluster called");
	}


	@Override
	public void doStartProPlugin(FileSystemXmlApplicationContext arg0)
			throws IOException {
		// TODO Auto-generated method stub
		log.info(NAME + "doStartProPlugin called");
	}


	@Override
	public void doStopProPlugin() throws Exception {
		// TODO Auto-generated method stub
		log.info(NAME + "doStopProPlugin called");
	}


	@Override
	public void setValidatedLicense(String arg0) {
		// TODO Auto-generated method stub
		log.info(NAME + "setValidatedLicense called");
	}

}
