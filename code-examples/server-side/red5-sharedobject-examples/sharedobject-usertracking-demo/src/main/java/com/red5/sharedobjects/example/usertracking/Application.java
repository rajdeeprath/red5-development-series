package com.red5.sharedobjects.example.usertracking;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.red5.server.exception.ClientRejectedException;
import org.red5.server.scope.WebScope;
import org.red5.server.util.ScopeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class Application extends MultiThreadedApplicationAdapter {

	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	private ISharedObject chatRoom = null;
	
	private IScope appScope;
	
	
	@Override
	public boolean appStart(IScope app) {
		log.info("Application started : {}", app);
		try 
		{
			this.appScope = app;
			
			/* Add connection listener */
			
			WebScope scope = (WebScope) app;
			IServer server = scope.getServer();
			server.addListener(connectionListener);
			
			
			/* Get shared object instance */
			chatRoom = getGameRoomSharedObject(app, "gameroom", false);
			
			
			/** Lock and unlock are needed only if you do not want any other thread to operate 
			 * on the shared object while you are operating on it. Unlock the shared object after you are done. */
			chatRoom.lock();
			chatRoom.acquire();
			chatRoom.addSharedObjectListener(roomListener);
			chatRoom.unlock();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return super.appStart(app);
	}
	
	
	
	
	/**
	 * Listen for successful connection to the application
	 */
	private IConnectionListener connectionListener = new IConnectionListener(){

		@Override
		public void notifyConnected(IConnection conn) {
			
			
			log.info("notifyConnected {}", conn);
			
			/* Not our scope then NVM */
			if(!isConnectionForScope(conn, appScope)){
				return;
			}
			
			String username = conn.getStringAttribute("username");
			
			if(username != null){
			
				List<String> users = (List<String>) chatRoom.getListAttribute("users");
				if(users == null) 
				{
					users = new ArrayList<String>();
				}
				else
				{
					if(users.contains(username))
					{
						log.warn(username + " already exists in the room");
						conn.close();
						return;
					}
				}
				
				
				users.add(username);
				chatRoom.setAttribute("users", users);
				chatRoom.setAttribute("lastUserConnect", Instant.now().toEpochMilli());
				chatRoom.setDirty(true);
			}
		}

		
		
		@Override
		public void notifyDisconnected(IConnection conn) {
			
			log.info("notifyDisconnected {}", conn);
			
			/* Not our scope then NVM */
			if(!isConnectionForScope(conn, appScope)){
				return;
			}
			
			String username = conn.getStringAttribute("username");
			
			if(username != null){
			
				List<String> users = (List<String>) chatRoom.getListAttribute("users");
				if(users == null) 
				{
					users = new ArrayList<String>();
				}
				
				
				users.remove(username);
				chatRoom.setAttribute("users", users);
				chatRoom.setDirty(true);
			}
		}
		
		
	};
	
	
	
	
	
	/**
	 * Checks to see if a connection is for a given scope
	 * 
	 * @param conn IConnection connection to test
	 * @param scope IScope scope to check for
	 * @return
	 */
	private boolean isConnectionForScope(IConnection conn, IScope scope){
		
		// While disconnecting scope may be null
		if(conn.getScope() != null)
		{
			return conn.getScope() == scope;
		}
		else
		{
			String rootPath = ScopeUtils.findApplication(scope).getPath();
			String scopeName = scope.getName();
			String scopePath = scope.getPath(); // /default/sharedobject-usertracking-demo
			String connectionPath = conn.getPath(); // sharedobject-usertracking-demo
			
			if((rootPath + "/" + connectionPath).equalsIgnoreCase(scopePath + "/" + scopeName)){
				return true;
			}
		}
		
		return false;
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
	 * Destroys the shared object created by the 'initSharedObject' call
	 * 
	 * @param scope
	 * @param name
	 * @param persistent
	 */
	private void destroySharedObject(){
	
		if(chatRoom != null)
		{
			chatRoom.removeSharedObjectListener(roomListener);
			chatRoom.release();
		}
		
		chatRoom = null;
	}

	
	
	
	
	/**
	 * Shared Object listener object for monitoring shared object events
	 */
	private ISharedObjectListener roomListener  = new ISharedObjectListener(){

		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectConnect(org.red5.server.api.so.ISharedObjectBase)
		 */
		@Override
		public void onSharedObjectConnect(ISharedObjectBase so) {
			log.info("Client connected to the shared object");
			
			IConnection conn = Red5.getConnectionLocal();
			
			if(conn.hasAttribute("username"))
			{
				String username = conn.getStringAttribute("username");
				
				Message notice = new Message();
				notice.sender = "Red5Server";
				notice.message = username + " : Joined the room";
				
				List<Object> args = new ArrayList<Object>();
				args.add(new Gson().toJson(notice));
				so.sendMessage("onChatMessage", args);
			}
			
			
		}

		
		/*
		 * (non-Javadoc)
		 * @see org.red5.server.api.so.ISharedObjectListener#onSharedObjectDisconnect(org.red5.server.api.so.ISharedObjectBase)
		 */
		@Override
		public void onSharedObjectDisconnect(ISharedObjectBase so) {
			log.info("Client disconnected from the shared object");
			
			
			IConnection conn = Red5.getConnectionLocal();
			
			if(conn.hasAttribute("username"))
			{
				String username = conn.getStringAttribute("username");
				
				Message notice = new Message();
				notice.sender = "Red5Server";
				notice.message = username + " : has left gameroom " + username;
				
				List<Object> args = new ArrayList<Object>();
				args.add(new Gson().toJson(notice));
				so.sendMessage("onChatMessage", args);
			}
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
		
		try 
		{
			String username = Utils.getUsernameParameter(conn, params);
			conn.setAttribute("username", username);
		} 
		catch (Exception e) 
		{
			throw new ClientRejectedException(e);
		}
		
		
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
