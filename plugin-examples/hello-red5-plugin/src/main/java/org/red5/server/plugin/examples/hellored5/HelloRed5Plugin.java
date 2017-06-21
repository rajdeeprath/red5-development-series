package org.red5.server.plugin.examples.hellored5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.listeners.IScopeListener;
import org.red5.server.api.scope.IBasicScope;
import org.red5.server.api.scope.IGlobalScope;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.scope.ScopeType;
import org.red5.server.plugin.Red5Plugin;
import org.red5.server.plugin.examples.hellored5.listener.AppEventMonitor;
import org.red5.server.plugin.examples.hellored5.security.PlaybackInterceptor;
import org.red5.server.plugin.examples.hellored5.security.PublishInterceptor;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public class HelloRed5Plugin extends Red5Plugin {

    private Logger log = Red5LoggerFactory.getLogger(HelloRed5Plugin.class);

    public static final String NAME = "hello-red5-plugin";
    
    private Properties configuration = new Properties();
    
    private Configuration defaultConfiguration;
    
    private final String configurationFile = "hello-red5-plugin.properties";

    
    @Override
    public String getName() {
        return HelloRed5Plugin.NAME;
    }
    

	@Override
	public void doStart() throws Exception {
		log.info(NAME + "doStart called");
		
		log.trace("Loading properties");
        Resource res = getConfResource(context, configurationFile);
        if (!res.exists()) 
        {
        	log.debug("Properties not found in conf, creating default configuration");
            
        	// Build default configuration
            configuration.put("simpleauth.default.active", "true");
            configuration.put("simpleauth.default.message", "Hello Red5");
            addConfResource(configuration, configurationFile, "HelloRed5 Properties\n");
        }
        else
        {
        	/******** Load configuration into local model *******/
        	
        	InputStream in = res.getInputStream();
            configuration.load(in);
            in.close();
        }
        
        
        

        boolean defaultActive = Boolean.parseBoolean(configuration.getProperty("hellored5.default.active", "false"));
        if (log.isDebugEnabled()) {
            log.debug("default active {}", defaultActive);
        }
        
        String defaultMessage = configuration.getProperty("hellored5.default.message", "Hello Red5");
        if (log.isDebugEnabled()) {
            log.debug("defaultMessage {}", defaultMessage);
        }
        
        
        /******** Store configuration into local model *******/
        
        defaultConfiguration = new Configuration();
        defaultConfiguration.setActive(defaultActive);
        defaultConfiguration.setMessage(defaultMessage);
        
        
        /******** Scan for applications *******/
        
        scanForApplications();
	}


	@Override
	public void doStop() throws Exception {
		log.info(NAME + "doStop called");
	}
	
	
	
	/**
     * Creates a new properties file.
     * 
     * @param props Properties to store
     * @param path 
     * @param comments 
     */
    private void addConfResource(Properties props, String path, String comments) {
        // red5 server conf directory property is set by red5 bootstrap
        String confDir = System.getProperty("red5.config_root");
        Path uri = Paths.get(String.format("%s/%s", confDir, path));
        OutputStream os = null;
        try {
            os = Files.newOutputStream(uri, new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING});
            log.debug("Creating configuration file {}", uri.toAbsolutePath());
            props.store(os, comments);
        } catch (IOException e) {
            log.warn("Exception adding conf resource: {}", uri, e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    
    

    /**
     * Looks for a specified local configuration resource.
     * 
     * @param context
     * @param path
     * @return Resource
     */
    private Resource getConfResource(ApplicationContext context, String path) {
        Resource res = context.getResource(String.format("classpath:/conf/%s", path));
        if (!res.exists()) {
            // red5 server conf directory property is set by red5 bootstrap
            String confDir = System.getProperty("red5.config_root");
            log.debug("Conf dir: {}", confDir);
            res = context.getResource(String.format("file:%s/%s", confDir, path));
        }
        return res;
    }
    
    
    
    /**
     * Scans server for applications 
     */
    private void scanForApplications() 
    {
        IScopeListener scopeListener = new IScopeListener() {
            @Override
            public void notifyScopeCreated(IScope scope) {
                if (scope.getType() == ScopeType.APPLICATION) {
                   log.info("Application started : {}", scope);
                   
                   /******** Accessing the scope handler for application *******/
                   MultiThreadedApplicationAdapter adapter = (MultiThreadedApplicationAdapter) scope.getHandler();
                   log.info("Application handler found : {}", adapter);
                   
                   
                   /******** Listening to application events *******/
                   adapter.addListener(new AppEventMonitor());
                   
                   
                   /******** Registering publish interceptor *******/
                   adapter.registerStreamPublishSecurity(new PublishInterceptor());
                   
                   /******** Registering playback interceptor *******/
                   adapter.registerStreamPlaybackSecurity(new PlaybackInterceptor());
                }
            }

            @Override
            public void notifyScopeRemoved(IScope scope) {
                if (scope.getType() == ScopeType.APPLICATION) {
                	log.info("Application stopped : {}", scope);
                }
            }
        };

        server.addListener(scopeListener);

        /**********************************************************************/

        log.debug("Setting handlers for apps that might have already started up");

        Iterator<IGlobalScope> inter = server.getGlobalScopes();
        while (inter.hasNext()) {
            IGlobalScope gscope = inter.next();
            Set<String> appSet = gscope.getBasicScopeNames(ScopeType.APPLICATION);
            Iterator<String> setInter = appSet.iterator();
            while (setInter.hasNext()) {
                String sApp = setInter.next();
                IBasicScope theApp = gscope.getBasicScope(ScopeType.APPLICATION, sApp);
                IScope issc = (IScope) theApp;
                log.info("Application found : {}", issc);
                
                /******** Accessing the scope handler for application *******/
                MultiThreadedApplicationAdapter adapter = (MultiThreadedApplicationAdapter) issc.getHandler();
                log.info("Application handler found : {}", adapter);
                
                
                /******** Listening to application events *******/
                adapter.addListener(new AppEventMonitor());
                
                
                /******** Registering publish interceptor *******/
                adapter.registerStreamPublishSecurity(new PublishInterceptor());
                
                /******** Registering playback interceptor *******/
                adapter.registerStreamPlaybackSecurity(new PlaybackInterceptor());
                                
            }
        }
    }


}
