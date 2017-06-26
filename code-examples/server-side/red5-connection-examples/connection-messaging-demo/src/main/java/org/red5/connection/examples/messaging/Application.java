package org.red5.connection.examples.messaging;



import java.time.Instant;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	

	@Override
	public boolean appStart(IScope arg0) {
		log.info("Application start {}", arg0);	
		return super.appStart(arg0);
	}
	



	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connect {}", conn);		
		return super.appConnect(conn, params);
	}

	
	
	
	

	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnect {}", conn);
		super.appDisconnect(conn);
	}
	
	
	
	
	
	/**
	 * Invokes a client side method
	 * 
	 * @param conn
	 */
	@SuppressWarnings("unused")
	private void serverToClient(IConnection conn, String method, Object[] args){
		
		try{
			IServiceCapableConnection invokeConn = (IServiceCapableConnection) conn;
			invokeConn.invoke(method, args);
		}catch(Exception e){
			log.error("An error occurred {}", e.getMessage());
		}
	}
	
	
	
	
	/**
	 * Invokes a client side method for result
	 * 
	 * @param conn
	 */
	@SuppressWarnings("unused")
	private void serverToClient2(IConnection conn, String method, Object[] args){
		
		try
		{
			IServiceCapableConnection invokeConn = (IServiceCapableConnection) conn;
			invokeConn.invoke(method, args, new IPendingServiceCallback(){

				@Override
				public void resultReceived(IPendingServiceCall call) {
					Object result = call.getResult();
					log.info("Result {}", String.valueOf(result));
				}		
			});
		}
		catch(Exception e)
		{
			log.error("An error occurred {}", e.getMessage());
		}
	}
	
	
	
	
	/**
	 * Invoked by client
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	private void clientToServer(Object[] args){
		log.info("Invoked from client at {} with params {}", Instant.now().toEpochMilli(), args);
	}
	
}
