package com.flashvisions.server.plugin.debug.systeminfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.listeners.IScopeListener;
import org.red5.server.api.scope.IBasicScope;
import org.red5.server.api.scope.IGlobalScope;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.scope.ScopeType;
import org.red5.server.plugin.Red5Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public class SystemInfoPlugin extends Red5Plugin {

    private Logger log = LoggerFactory.getLogger(SystemInfoPlugin.class);

    public static final String NAME = "system-info-plugin";

    
    @Override
    public String getName() {
        return SystemInfoPlugin.NAME;
    }
    

	@Override
	public void doStart() throws Exception {
		log.info(NAME + "doStart called");
		
		Resource guarantor = context.getResource("classpath:/conf/red5.xml");
        
		File conf_directory = guarantor.getFile().getParentFile();
		
		
		/******** Scan for java version *******/
		
		log.info("==================== JVM INFO START ===================");	
		
		String javaVersion = System.getProperty("java.version");
	    log.info(String.format("Java Version = '%s'", javaVersion));
	    log.info("Current directory = {}", System.getProperty("user.dir"));
	    
	    
	    log.info("==================== JVM INFO END ===================");
		
		
		/******** Scan for classpaths *******/
		
		
		log.info("==================== CLASS PATHS START ===================");		
		
		ClassLoader c=getClass().getClassLoader();
		log.info("c={}", c);
		URLClassLoader u=(URLClassLoader)c;
		URL[] urls=u.getURLs();
		for (URL i : urls) {
		    log.info("url: {}", i);
		}		
		
		log.info("==================== CLASS PATHS END ===================");
		
		
		
		/******** Scan for system & environment variables *******/
		
		
		log.info("==================== ENV VARIABLES START ===================");
		
		StringBuilder sb = new StringBuilder(); 
		Map<String, String> env = System.getenv(); 
		for (String key : env.keySet()) { 
		 sb.append(key + ": " + env.get(key)  + "\n"); 
		} 

		log.info(sb.toString());
		
		log.info("==================== ENV VARIABLES END ===================");
		
		
        
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
            }
        }
    }


}
