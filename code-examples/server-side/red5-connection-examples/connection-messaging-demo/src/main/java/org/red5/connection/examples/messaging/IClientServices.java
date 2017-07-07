package org.red5.connection.examples.messaging;

public interface IClientServices {

	/**
	 * Invoked by client
	 * 
	 * @param args
	 */
	public abstract void clientToServer();

	/**
	 * Call a method named 'clientMethod1' on client without params. The client method signature should match appropriately
	 */
	public abstract void callClientMethodWithoutParams();

	/**
	 * Call a method named 'clientMethod2' on client with params. The client method signature should match appropriately
	 */
	public abstract void callClientMethodWithParams();

	/**
	 * Call a method named 'clientMethod3' on client with params and get result. The client method signature should match appropriately
	 */
	public abstract void callClientMethodWithParamsForResult();

	
	/**
	 * 
	 * @param args
	 */
	public void clientToServerWithParams(Object[] args);

	
	
	/**
	 * 
	 * @param map
	 */
	public void clientToServerWithParamsForMobileSDK(String map);

}