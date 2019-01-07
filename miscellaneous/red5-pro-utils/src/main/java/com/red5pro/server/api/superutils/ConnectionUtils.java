package com.red5pro.server.api.superutils;

import org.red5.server.api.IConnection;
import org.red5.server.net.rtmp.RTMPMinaConnection;

import com.red5pro.server.stream.rtsp.IRTSPConnection;
import com.red5pro.server.stream.webrtc.IWebRTCConnection;

public class ConnectionUtils {
	
	
	
	
	/**
	 * Returns human readable string for a given IConnection type
	 *  
	 * @param connection
	 * @return
	 */
	public static String getConnectionType(IConnection connection)
	{
		if(connection instanceof RTMPMinaConnection)
		{
			return "rtmp";
		}
		else if(connection instanceof IRTSPConnection)
		{
			return "rtsp";
		}
		else if(connection instanceof IWebRTCConnection)
		{
			return "rtc";
		}
		
		return null;
	}
	
	
	
	
	/**
	 * Returns boolean true if connection is a RTMPMinaConnection object, false otherwise
	 * 
	 * @param connection
	 * @return
	 */
	public static boolean isRTMP(IConnection connection)
	{
		return (connection instanceof RTMPMinaConnection);
	}
	
	
	
	
	/**
	 * Returns boolean true if connection is a RTSPMinaConnection object, false otherwise
	 * 
	 * @param connection
	 * @return
	 */
	public static boolean isRTSP(IConnection connection)
	{
		return (connection instanceof IRTSPConnection);
	}
	
	
	
	
	
	/**
	 * Returns boolean true if connection is a RTCConnection object, false otherwise
	 * 
	 * @param connection
	 * @return
	 */
	public static boolean isRTC(IConnection connection)
	{
		return (connection instanceof IWebRTCConnection);
	}

}
