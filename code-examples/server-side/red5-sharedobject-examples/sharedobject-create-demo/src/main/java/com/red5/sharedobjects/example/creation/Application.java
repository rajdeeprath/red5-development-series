package com.red5.sharedobjects.example.creation;

import java.util.List;
import java.util.Map;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IAttributeStore;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.so.ISharedObjectBase;
import org.red5.server.api.so.ISharedObjectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	ISharedObject sharedBucket = null;
	
	IScope appScope;
	
	
	
	
	@Override
	public boolean appStart(IScope app) {
		log.info("Application started : {}", app);
		this.appScope  = app;
		
		try 
		{
			initSharedObject(appScope, "bucket", false);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return super.appStart(app);
	}

	
	
	
	
	/**
	 * Initializes shared object for reference 'sharedBucket'
	 * 
	 * @param scope
	 * @param name
	 * @param persistent
	 * @throws Exception
	 */
	private void initSharedObject(IScope scope, String name, boolean persistent) throws Exception{
	
		if(sharedBucket == null)
		{
			sharedBucket = this.getSharedObject(scope, name, persistent);
			
			if(sharedBucket == null){
				boolean created = this.createSharedObject(scope, name, persistent);
				if(!created) throw new Exception("Failed to create shared object");
			}
			
			sharedBucket.acquire();
			sharedBucket.addSharedObjectListener(bucketListener);
		}
	}

	
	
	
	
	/**
	 * Destroys the shared object created by the 'initSharedObject' call
	 * 
	 * @param scope
	 * @param name
	 * @param persistent
	 */
	private void destroySharedObject(){
	
		if(sharedBucket != null)
		{
			sharedBucket.release();
		}
		
		sharedBucket = null;
	}

	
	
	
	
	/**
	 * Shared Object listener object for monitoring shared object events
	 */
	private ISharedObjectListener bucketListener  = new ISharedObjectListener(){

		@Override
		public void onSharedObjectConnect(ISharedObjectBase so) {
			log.info("Client connected to the shared object");			
		}

		@Override
		public void onSharedObjectDisconnect(ISharedObjectBase so) {
			log.info("Client disconnected from the shared object");
		}

		@Override
		public void onSharedObjectUpdate(ISharedObjectBase so, String key, Object value) {
			log.info("Shared object property {} is updated", key);
		}

		@Override
		public void onSharedObjectUpdate(ISharedObjectBase so, IAttributeStore values) {
			log.info("Shared object attribute store is updated");
		}

		@Override
		public void onSharedObjectUpdate(ISharedObjectBase so, Map<String, Object> map) {
			log.info("Shared object multiple properties are updated");
			for (Map.Entry<String, Object> entry : map.entrySet())
			{
			    log.info(entry.getKey() + "/" + entry.getValue());
			}
		}

		@Override
		public void onSharedObjectDelete(ISharedObjectBase so, String key) {
			log.info("Property {} deleted from shared object", key);
		}

		@Override
		public void onSharedObjectClear(ISharedObjectBase so) {
			log.info("Shared object cleared");
		}

		@Override
		public void onSharedObjectSend(ISharedObjectBase so, String method, List<?> params) {
			log.info("Shared Object send called for method {}", method);
			
		}
		
	};

	
	
	
	
	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connect : {}",  conn);
		return super.appConnect(conn, params);
	}







	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnect : {}",  conn);
		super.appDisconnect(conn);
	}







	@Override
	public void appStop(IScope arg0) {
		log.info("Application stopped : {}", arg0);
		destroySharedObject();
		super.appStop(arg0);
	}
	


}
