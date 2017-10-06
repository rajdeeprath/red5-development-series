package org.red5.streams.examples.recordname;

import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;

public class CustomFileNames implements IStreamFilenameGenerator {

	
	 private String defaultLocation = "streams/";
	 
	 
	 public String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}



	@Override
	public String generateFilename(IScope scope, String name, GenerationType type) {
		  
		String filename; 
		  IConnection conn = Red5.getConnectionLocal();
		  
		  if (type == GenerationType.RECORD) 
		  filename = MD5(name + System.currentTimeMillis() + conn.getSessionId());
		  else 
		  filename = name;
		  
		  return filename;
	}
	
	
	public String generateFilename(IScope scope, String name, String extension, GenerationType type) 
	 { 
		  String filename; 
		  IConnection conn = Red5.getConnectionLocal();
		  
		  if (type == GenerationType.RECORD) 
		  filename = MD5(name + System.currentTimeMillis() + conn.getSessionId());
		  else 
		  filename = name; 
		  
		  if (extension != null)
		  filename += extension; 
		  
		  return defaultLocation + filename; 
	 } 



	@Override
	public boolean resolvesToAbsolutePath() {
		// TODO Auto-generated method stub
		return false;
	}



	public String getDefaultLocation() {
		return defaultLocation;
	}



	public void setDefaultLocation(String defaultLocation) {
		this.defaultLocation = defaultLocation;
	}
}
