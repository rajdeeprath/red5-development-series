package org.red5.connection.examples.messaging;



import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IPendingServiceCall;
import org.red5.server.api.service.IPendingServiceCallback;
import org.red5.server.api.service.IServiceCapableConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	
	private ExecutorService threadPoolExecutor;
	
	
	

	@Override
	public boolean appStart(IScope arg0) {
		
		log.info("Application start {}", arg0);
		
		threadPoolExecutor = Executors.newCachedThreadPool();
		
		return super.appStart(arg0);
	}
	
	
	
	
	@Override
	public void appStop(IScope arg0) {
		
		log.info("Application stop {}", arg0);
		
		threadPoolExecutor.shutdown();
		
		super.appStop(arg0);
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
	 * Invokes a client side method withotu arguments
	 * 
	 * @param conn
	 */
	private void serverToClient(IConnection conn, String method){
		
		try{
			IServiceCapableConnection invokeConn = (IServiceCapableConnection) conn;
			invokeConn.invoke(method);
		}catch(Exception e){
			log.error("An error occurred {}", e.getMessage());
		}
	}
	
	
	
	
	
	/**
	 * Invokes a client side method
	 * 
	 * @param conn
	 */
	private void serverToClient2(IConnection conn, String method, Object[] args){
		
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
	private void serverToClient3(IConnection conn, String method, Object[] args){
		
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
	 * Method Invoked from client
	 */
	protected void clientToServer(){
		log.info("Invoked from client at {}", Instant.now().toEpochMilli());
	}
	
	
	
	
	
	/**
	 * Method invoked from client requesting a callback on 'clientMethod1'
	 */
	protected void callClientMethodWithoutParams(){
		
		log.info("Client request for method call without params");
		
		IConnection connection = Red5.getConnectionLocal();
		
		threadPoolExecutor.execute(new Runnable(){

			@Override
			public void run() {
				serverToClient(connection, "clientMethod1");
			}			
		});		
	}
	
	
	
	
	
	/**
	 * Method invoked from client requesting a callback on 'clientMethod2'
	 */
	protected void callClientMethodWithParams(){
		
		log.info("Client request for method call with params");
		
		IConnection connection = Red5.getConnectionLocal();
		
		threadPoolExecutor.execute(new Runnable(){

			@Override
			public void run() {
				serverToClient2(connection, "clientMethod2", new Object[]{1, 2});
			}			
		});		
	}
	
	
	
	
	
	
	
	/**
	 * Method invoked from client requesting a callback on 'clientMethod3'
	 */
	protected void callClientMethodWithParamsForResult(){
		
		log.info("Client request for method call with params for result");
		
		IConnection connection = Red5.getConnectionLocal();
		
		threadPoolExecutor.execute(new Runnable(){

			@Override
			public void run() {
				serverToClient3(connection, "clientMethod3", new Object[]{1, 2});
			}			
		});		
	}
	
}
