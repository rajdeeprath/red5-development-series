package org.red5.streams.examples.publishdetect;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IBroadcastScope;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IClientBroadcastStream;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.red5.server.api.stream.StreamState;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;


public class StreamPublishDetect implements IStreamPublishSecurity {
    
    private static final Logger logger = Red5LoggerFactory.getLogger(StreamPublishDetect.class, "stream-record");
    
    private IScope appScope;
    
    private ExecutorService executor;
    
    private MultiThreadedApplicationAdapter appAdapter;
    
    
    
    public StreamPublishDetect(IScope appScope){
        this.appScope = appScope;
        this.initialize();
    }
    
    
    
    private void initialize(){
        this.executor = Executors.newCachedThreadPool();
        
        this.appAdapter = (MultiThreadedApplicationAdapter) appScope.getHandler();
        
        if(this.appAdapter != null) {
            logger.info("Registering stream monitor for recording");
            this.appAdapter.registerStreamPublishSecurity(this);
        }
    }
    
    

    @Override
    public boolean isPublishAllowed(IScope scope, String name, String mode) {
        
    	logger.debug("Stream publish attempt  detected" + name +" at scope path "  + scope.getPath());
        
        if(mode.equalsIgnoreCase("live")){
            executor.execute(new BroadcastStreamChecker(scope, name));
        }
        return true;
    }

    

    public IScope getAppScope() {
        return appScope;
    }


    public void setAppScope(IScope appScope) {
        this.appScope = appScope;
    }
    
    
    public void destroy(){
        
        if(executor != null){
            executor.shutdown();
        }
        
    }
    
    
    
    
    
    
    private void streamBroadcastStart(IScope scope, IBroadcastStream stream){
        
    	logger.debug("Stream broadcast started");
    }
    
    
    
    private void streamBroadcastClose(IScope scope, IBroadcastStream stream){
        
        logger.debug("Stream broadcast stopped");
    }

    
    
    class BroadcastStreamChecker implements Runnable{
            
            IScope scope;
            IBroadcastScope bs;
            String name;
            ClientBroadcastStream stream;
            int numTries = 600;
            
            public BroadcastStreamChecker(IScope scope, String name){
                this.scope = scope;
                this.name = name;
            }
            
            
            private PropertyChangeListener streamChangeListener = new PropertyChangeListener(){
                
                @Override
                public void propertyChange(PropertyChangeEvent evt) 
                {
                    logger.info("Stream {} change: {}", name, evt); 
                    
                    String oldVal = String.valueOf(evt.getOldValue());
                    String newVal = String.valueOf(evt.getNewValue());
                    
                    if(oldVal.equalsIgnoreCase("STOPPED") && newVal.equalsIgnoreCase("CLOSED"))
                    {
                        if(stream != null)
                        {
                            logger.info("UnRegistering change listener: {}", name);
                            stream.removeStateChangeListener(streamChangeListener);
                        }
                    }
                    else if(oldVal.equalsIgnoreCase("STARTED") && newVal.equalsIgnoreCase("PUBLISHING"))
                    {
                        streamBroadcastStart(scope, stream);
                    }
                    else if(oldVal.equalsIgnoreCase("PUBLISHING") && newVal.equalsIgnoreCase("STOPPED"))
                    {
                        streamBroadcastClose(scope, stream);
                    }
                }
            };
            
            
            public void doCheck(){
                
                try
                {
                    if(numTries <= 0)
                    {
                        throw new Exception("Stream lookup exhausted. Could not find stream");
                    }
                    
                    
                    stream = (ClientBroadcastStream) appAdapter.getBroadcastStream(scope, name);
                    if(stream != null)
                    {
                        logger.debug("Registering change listener: {}", name);
                        stream.addStateChangeListener(streamChangeListener);
                        
                        if(stream.getState() == StreamState.PUBLISHING)
                        {
                            if(stream.getConnection() != null){
                                logger.info("Stream {} already active state: {}", name, stream.getState());
                                IConnection pubConnection  = Red5.getConnectionLocal();
                                if(pubConnection == null){
                                    Red5.setConnectionLocal(stream.getConnection());
                                }
                                
                                streamBroadcastStart(scope, stream);
                            }
                        }
                    }
                    else
                    {
                        Thread.sleep(10);
                        numTries --;
                        doCheck();
                    }
                }
                catch(Exception e)
                {
                    logger.error(e.getMessage());
                }
                
            }
    
    
            @Override
            public void run() {
                doCheck();
            }
            
        }
    }
