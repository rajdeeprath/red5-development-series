package org.red5.streams.examples.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.red5.server.api.IConnection;

public class Utils {
	
	
	static final String RTSPCONNECTION = "com.red5pro.server.stream.rtsp.RTSPMinaConnection"; 
	static final String RTMPCONNECTION = "org.red5.server.net.rtmp.RTMPMinaConnection"; 
	static final String RTCCONNECTION = "com.red5pro.webrtc.RTCConnection";
	
	
	
	public static boolean isRTSP(IConnection connection)
	{
		String connectionClassName = connection.getClass().getCanonicalName();
		return (connectionClassName.equalsIgnoreCase(RTSPCONNECTION));
	}
	
	
	public static Map<String, Object> getConnectionParameters(IConnection connection)
	{ 
		String connectionClassName = connection.getClass().getCanonicalName();
		
		if(connectionClassName.equalsIgnoreCase(RTSPCONNECTION))
		{
			Object[] params  = (Object[]) connection.getAttribute("connectParams");
			
			Map<String, Object> map = new HashMap<String, Object>();
			for(int i = 0; i<params.length;i++)
			{
				String[] param = String.valueOf(params[i]).split("=");
				map.put(param[0], param[1]);
			}
			
			return map;
		}
		else if(connectionClassName.equalsIgnoreCase(RTMPCONNECTION))
		{
			return getRTMPParametersMap(connection.getConnectParams());
		}
		else if(connectionClassName.equalsIgnoreCase(RTCCONNECTION))
		{
			return getRTCParametersMap(connection.getConnectParams());
		}
		
		return null;
		
	}

	
	
	
	private static Map<String, Object> getRTCParametersMap(Map<String, Object> content)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		Iterator<Entry<String, Object>> it = content.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> pair = it.next();
	        String key = pair.getKey();
	        Object value = pair.getValue();
	        
	        if(key.indexOf("?") == 0) key = key.replace("?", "");
	        map.put(key, value);
	    }
		
		return map;
		
	}
	
	
	
	private static Map<String, Object> getRTMPParametersMap(Map<String, Object> content)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Entry<String, Object>> it = content.entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry<String, Object> pair = it.next();
	        String key = pair.getKey();
	        Object value = pair.getValue();
	        
	        if(key.equals("queryString"))
	        {
	        	Map<String, Object> qmap = new HashMap<String, Object>();
	        	String[] parameters =  String.valueOf(value).split("&");
				for(int i = 0; i<parameters.length;i++)
				{
					String[] param = String.valueOf(parameters[i]).split("=");
					String name = param[0];
					if(name.indexOf("?") == 0) name = name.replace("?", "");
					String val = param[1];					
					qmap.put(name, val);
				}
				
				map.putAll(qmap);
	        }
	        
	        map.put(key, value);
	    }
		
		return map;
		
	}
}
