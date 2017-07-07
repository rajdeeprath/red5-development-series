package org.red5.connection.examples.messaging;

import java.util.HashMap;
import java.util.Map;

public class Red5ProUtils {
	
	public static Map<String, Object> fromString(String data)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String[] pairs = data.split(";");
		
		for(int i=0;i<pairs.length;i++){
			String nameValuePair = pairs[i];
			String[] pair = nameValuePair.split("=");
			map.put(pair[0], pair[1]);
		}
		
		return map;
	}

}
