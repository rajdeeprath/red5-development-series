package com.red5.sharedobjects.example.listenerdemo;

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
	
	
	
	@Override
	public boolean appStart(IScope app) {
		log.info("Application started : {}", app);
		try 
		{
			sharedBucket = initSharedObject(app, "bucket", false);
			sharedBucket.addSharedObjectListener(bucketListener);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return super.appStart(app);
	}

	
	
	
	
	/**
	 * 
	 * @param scope
	 * @param name
	 * @param persistent
	 * @return
	 * @throws Exception
	 */
	private ISharedObject initSharedObject(IScope scope, String name, boolean persistent) throws Exception{
	
		ISharedObject so = getSharedObject(scope, name, persistent);
		
		if(so == null){
			createSharedObject(scope, name, persistent);
			
			/* Check again */
			so = getSharedObject(scope, name, persistent);
			if(so == null) throw new Exception("Shared object was not created");
		}
		
		if(!so.isAcquired())
		so.acquire();
		
		return so;
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
			sharedBucket.removeSharedObjectListener(bucketListener);
			sharedBucket.release();
		}
		
		sharedBucket = null;
	}

	
	
	
	
	/**
	 * Shared Object listener object for monitoring shared object events
	 */
	private ISharedObjectListener bucketListener  = new ISharedObjectListener(){

		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectConnect(org.red5.server.api.so.ISharedObjectBase)
		 */
		@Override
		public void onSharedObjectConnect(ISharedObjectBase so) {
			log.info("Client connected to the shared object");			
		}

		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectDisconnect(org.red5.server.api.so.ISharedObjectBase)
		 */
		@Override
		public void onSharedObjectDisconnect(ISharedObjectBase so) {
			log.info("Client disconnected from the shared object");
		}

		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectUpdate(org.red5.server.api.so.ISharedObjectBase, java.lang.String, java.lang.Object)
		 */
		@Override
		public void onSharedObjectUpdate(ISharedObjectBase so, String key, Object value) {
			log.info("Shared object property {} is updated", key);
		}

		
		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectUpdate(org.red5.server.api.so.ISharedObjectBase, org.red5.server.api.IAttributeStore)
		 */
		@Override
		public void onSharedObjectUpdate(ISharedObjectBase so, IAttributeStore values) {
			log.info("Shared object attribute store is updated");
		}

		
		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectUpdate(org.red5.server.api.so.ISharedObjectBase, java.util.Map)
		 */
		@Override
		public void onSharedObjectUpdate(ISharedObjectBase so, Map<String, Object> map) {
			log.info("Shared object multiple properties are updated");
			for (Map.Entry<String, Object> entry : map.entrySet())
			{
			    log.info(entry.getKey() + "/" + entry.getValue());
			}
		}

		
		
		
		/*
		 * 		(non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectDelete(org.red5.server.api.so.ISharedObjectBase, java.lang.String)
		 */
		@Override
		public void onSharedObjectDelete(ISharedObjectBase so, String key) {
			log.info("Property {} deleted from shared object", key);
		}
		
		
		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectClear(org.red5.server.api.so.ISharedObjectBase)
		 */
		@Override
		public void onSharedObjectClear(ISharedObjectBase so) {
			log.info("Shared object cleared");
		}

		
		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectSend(org.red5.server.api.so.ISharedObjectBase, java.lang.String, java.util.List)
		 */
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
