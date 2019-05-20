package com.red5pro.server.api.superutils;


import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;

public class CustomFilenameGenerator implements IStreamFilenameGenerator {

	/** Path that will store recorded videos. */ 
	 public String recordPath = "streams/"; 
	 /** Path that contains VOD streams. */ 
	 public String playbackPath = "streams/"; 
	 /** Set if the path is absolute or relative */ 
	 public boolean resolvesAbsolutePath = false; 
	 
	 public String generateFilename(IScope scope, String name, GenerationType type) 
	 { 
	  return generateFilename(scope, name, null, type); 
	 }
	 
	 
	 
	 public String generateFilename(IScope scope, String name, String extension, GenerationType type) 
	 { 
		  String filename; 
		  
		  if (type == GenerationType.RECORD) 
		   filename = recordPath + name; 
		  else 
		   filename = playbackPath + name; 
		  
		  if (extension != null)
		  filename += extension; 
		  
		  return filename; 
	 } 
	 
	 public boolean resolvesToAbsolutePath() 
	 { 
		 return resolvesAbsolutePath; 
	 } 
	 
	 public void setRecordPath(String path)
	 { 
		 recordPath = path; 
	 }
	 
	 public void setPlaybackPath(String path) 
	 { 
		 playbackPath = path; 
	 } 
	
	 public void setAbsolutePath(Boolean absolute)
	 { 
		 resolvesAbsolutePath = absolute; 
	 }



	public boolean isResolvesAbsolutePath() {
		return resolvesAbsolutePath;
	}



	public void setResolvesAbsolutePath(boolean resolvesAbsolutePath) {
		this.resolvesAbsolutePath = resolvesAbsolutePath;
	}



	public String getRecordPath() {
		return recordPath;
	}



	public String getPlaybackPath() {
		return playbackPath;
	}

	 
}
