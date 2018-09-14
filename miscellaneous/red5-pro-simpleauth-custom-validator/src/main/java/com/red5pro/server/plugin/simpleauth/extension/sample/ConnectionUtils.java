package com.red5pro.server.plugin.simpleauth.extension.sample;

import org.red5.server.api.IConnection;

public class ConnectionUtils {
    
    
    static String RTSPCONNECTION = "com.red5pro.server.stream.rtsp.RTSPMinaConnection";

    
    static String RTMPCONNECTION = "org.red5.server.net.rtmp.RTMPMinaConnection";   
    
    
    static String RTCCONNECTION = "com.red5pro.webrtc.RTCConnection";
    
    
    
    
    /**
     * Returns human readable string for a given IConnection type
     *  
     * @param connection
     * @return
     */
    public static String getConnectionType(IConnection connection)
    {
        String connectionClassName = connection.getClass().getCanonicalName();
        
        if(connectionClassName.equalsIgnoreCase(RTMPCONNECTION))
        {
            return "rtmp";
        }
        else if(connectionClassName.equalsIgnoreCase(RTSPCONNECTION))
        {
            return "rtsp";
        }
        else if(connectionClassName.equalsIgnoreCase(RTCCONNECTION))
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
        String connectionClassName = connection.getClass().getCanonicalName();
        return connectionClassName.equalsIgnoreCase(RTMPCONNECTION);
    }
    
    
    
    
    /**
     * Returns boolean true if connection is a RTSPMinaConnection object, false otherwise
     * 
     * @param connection
     * @return
     */
    public static boolean isRTSP(IConnection connection)
    {
        String connectionClassName = connection.getClass().getCanonicalName();
        return connectionClassName.equalsIgnoreCase(RTSPCONNECTION);
    }
    
    
    
    
    
    /**
     * Returns boolean true if connection is a RTCConnection object, false otherwise
     * 
     * @param connection
     * @return
     */
    public static boolean isRTC(IConnection connection)
    {
        String connectionClassName = connection.getClass().getCanonicalName();
        return connectionClassName.equalsIgnoreCase(RTCCONNECTION);
    }

}