package org.red5.connection.examples.attributes;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	

	@Override
	public boolean appStart(IScope app) {
		return super.appStart(app);
	}
	



	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("Client connect {}", conn);
		
		storeAttributes(conn);
		
		/* Comment out the above call and uncomment the the method call below to see alternate way of storing attributes */
		//storeAttributes2(conn);
		
		logAttributeNames(conn);
		
		return super.appConnect(conn, params);
	}


	

	@Override
	public void appDisconnect(IConnection conn) {
		log.info("Client disconnect {}", conn);
		
		readAttributes(conn);
		
		/* Comment out the above call and uncomment the the method call below to see alternate way of reading attributes */
		// readAttributes2(conn);
		
		super.appDisconnect(conn);
	}

	
	
	/**
	 * Stores attributes on the connection object. 
	 * 
	 * @param conn
	 */
	private void storeAttributes(IConnection conn)
	{
		conn.setAttribute("string_attribute", "Red5");
		conn.setAttribute("int_attribute", 12345);
		conn.setAttribute("long_attribute", 1498388903991L);
		conn.setAttribute("boolean_attribute", true);
		conn.setAttribute("double_attribute", 2323456.45);
	}
	
	
	
	
	/**
	 * Stores attributes on the connection object. Uses a Map to set the values
	 * 
	 * @param conn
	 */
	@SuppressWarnings("unused")
	private void storeAttributes2(IConnection conn)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("string_attribute_key", "Red5");
		map.put("int_attribute_key", 1234);
		map.put("long_attribute_key", 1498388903991L);
		map.put("boolean_attribute_key", true);
		map.put("double_attribute_key", 2323456.45);
		
		conn.setAttributes(map);
	}
	
	
	
	/**
	 * Reads stored attributes from a IConnection object when attribute data type is 'known'
	 * 
	 * @param conn
	 */
	private void readAttributes(IConnection conn)
	{
		if(conn.hasAttribute("string_attribute")){
			log.info("string_attribute = " + conn.getStringAttribute("string_attribute")); 
		}			
		
		if(conn.hasAttribute("int_attribute")){
			log.info("int_attribute = " + conn.getIntAttribute("string_attribute")); 
		}
		
		if(conn.hasAttribute("long_attribute")){
			log.info("long_attribute = " + conn.getLongAttribute("string_attribute")); 
		}
		
		if(conn.hasAttribute("double_attribute")){
			log.info("double_attribute = " + conn.getDoubleAttribute("double_attribute")); 
		}
	}

	
	
	
	
	
	/**
	 * Reading connection attributes when attribute data type is 'not' known
	 * 
	 * @param conn
	 */
	@SuppressWarnings("unused")
	private void readAttributes2(IConnection conn)
	{
		if(conn.hasAttribute("string_attribute")){
			log.info("string_attribute = " + String.valueOf(conn.getAttribute("string_attribute"))); 
		}			
		
		if(conn.hasAttribute("int_attribute")){
			log.info("int_attribute = " + String.valueOf(conn.getAttribute("string_attribute"))); 
		}
		
		if(conn.hasAttribute("long_attribute")){
			log.info("long_attribute = " + String.valueOf(conn.getAttribute("string_attribute"))); 
		}
		
		if(conn.hasAttribute("double_attribute")){
			log.info("double_attribute = " + String.valueOf(conn.getAttribute("double_attribute"))); 
		}
	}
	
	
	
	
	
	private void logAttributeNames(IConnection conn){
		
		Set<String> attrs = conn.getAttributeNames();
		for(String attr : attrs){
			log.info("Attribute : {}", attr);
		}
	}
	
	
}
