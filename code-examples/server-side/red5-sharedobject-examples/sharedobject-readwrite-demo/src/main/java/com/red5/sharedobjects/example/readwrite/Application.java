package com.red5.sharedobjects.example.readwrite;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.red5.server.Server;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IAttributeStore;
import org.red5.server.api.IConnection;
import org.red5.server.api.IServer;
import org.red5.server.api.Red5;
import org.red5.server.api.listeners.IConnectionListener;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.so.ISharedObjectBase;
import org.red5.server.api.so.ISharedObjectListener;
import org.red5.server.scope.WebScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	private ISharedObject so = null;
	
	private IScope appScope;
	
	
	@Override
	public boolean appStart(IScope app) {
		log.info("Application started : {}", app);
		
		try 
		{
			this.appScope = app;
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	
			            	try 
			            	{
								runSharedObjectReadWriteTest();
							} 
			            	catch (Exception e) 
			            	{
								e.printStackTrace();
							}
			            }
			        }, 
			        15000 
			);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return super.appStart(app);
	}
	
	
	
	
	
	/**
	 * Main method which runs the read / write methods
	 * @throws Exception 
	 */
	private void runSharedObjectReadWriteTest() throws Exception
	{
		
		/* Get shared object instance */
		so = getGameRoomSharedObject(appScope, "mysharedobject", false);
		
		
		/** Lock and unlock are needed only if you do not want any other thread to operate 
		 * on the shared object while you are operating on it. Unlock the shared object after you are done. */
		so.lock();
		so.acquire();
		so.addSharedObjectListener(soListener);
		so.unlock();
		
		
		Thread.sleep(2000);
		
		/*  Write test #1 */
		writeSharedObjectData();
		
		
		Thread.sleep(2000);
		
		/*  Write test #2 */
		writeSharedObjectData2();
		
		
		Thread.sleep(2000);
		
		/* Read test #1 */
		readSharedObjectAttributes();
		
		
		Thread.sleep(2000);
		
		/* Read test #2 */
		readAllSharedObjectAttributes();
		
		
		Thread.sleep(2000);
		
		/* Clear sharedObject */
		clearSharedObjectData();
		
		
		Thread.sleep(2000);
		
		/* destroy sharedObject */
		destroySharedObject();
	}
	
	
	
	
	
	/**
	 * 
	 * @param scope
	 * @param name
	 * @param persistent
	 * @return
	 * @throws Exception
	 */
	private ISharedObject getGameRoomSharedObject(IScope scope, String name, boolean persistent) throws Exception{
	
		ISharedObject so = getSharedObject(scope, name, persistent);
		
		if(so == null){
			createSharedObject(scope, name, persistent);
			
			/* Check again */
			so = getSharedObject(scope, name, persistent);
			if(so == null) throw new Exception("Shared object was not created");
		}
		
		return so;
	}
	
	
	
	
	
	/**
	 * Triggers sync for each attribute write
	 */
	private void writeSharedObjectData(){
		
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		
		so.setAttribute("time", Instant.now().toEpochMilli());
		so.setAttribute("servername", "red5pro");
		so.setAttribute("capabilities", Red5.CAPABILITIES);
	}
	
	
	
	
	
	/**
	 * Triggers sync only once for all writes
	 */
	private void writeSharedObjectData2(){
		
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		
		so.beginUpdate();
		so.setAttribute("time", Instant.now().toEpochMilli());
		so.removeAttribute("servername");
		so.setAttribute("capabilities", Red5.CAPABILITIES);
		so.endUpdate();
		
	}
	
	
	
	
	
	/**
	 * Force an update notification dispatch
	 */
	private void forceDataSync(){
		
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		so.setDirty(true);
		
	}
	
	
	
	
	
	
	/**
	 * Reads attributes from SharedObject that we might have stored earlier
	 */
	private void readSharedObjectAttributes(){
		
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		
		if(so.hasAttribute("time")){
			log.info("Attr time  :  Value {}", so.getAttribute("time"));
		}
		
		if(so.hasAttribute("servername")){
			log.info("Attr servername  :  Value {}", so.getAttribute("servername"));
		}
		
		if(so.hasAttribute("capabilities")){
			log.info("Attr capabilities  :  Value {}", so.getAttribute("capabilities"));
		}
	}
	
	
	
	
	
	/**
	 * Reads all attributes from SharedObject
	 */
	private void readAllSharedObjectAttributes(){
		
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		Set<String> attrs = so.getAttributeNames();
		
		for(String attr : attrs){
			log.info("Attr {}  : value {}", attr, so.getAttribute(attr));
		}
	}
	
	
	
	
	
	
	/**
	 * Clears the ISharedObject of all its data
	 */
	private void clearSharedObjectData(){
		
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		so.clear();
	}

	
	
	
	
	/**
	 * Destroys the shared object created by the 'initSharedObject' call
	 * 
	 * @param scope
	 * @param name
	 * @param persistent
	 */
	private void destroySharedObject(){
	
		ISharedObject so = this.getSharedObject(appScope, "mysharedobject");
		
		if(so != null)
		{
			so.removeSharedObjectListener(soListener);
			so.close();
		}
		
		so = null;
	}

	
	
	
	
	/**
	 * Shared Object listener object for monitoring shared object events
	 */
	private ISharedObjectListener soListener  = new ISharedObjectListener(){

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
		
		conn.setAttribute("username", "guest-"+conn.getSessionId());
		
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
