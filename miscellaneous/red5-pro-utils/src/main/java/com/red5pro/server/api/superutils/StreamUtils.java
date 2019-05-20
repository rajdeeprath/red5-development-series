package com.red5pro.server.api.superutils;

import java.io.File;
import java.io.IOException;

import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;
import org.red5.server.api.stream.IStreamFilenameGenerator.GenerationType;
import org.red5.server.util.ScopeUtils;
import org.springframework.core.io.Resource;

public class StreamUtils {
	
	
	/**
	 * Provides the path to stream storage directory.
	 * 
	 * <b>Note</b> : Not tested for sub-scopes
	 * 
	 * @param scope The application scope
	 * @return Absolute path of stream storage directory
	 * @throws IOException
	 */
	public static String getStreamsDirectory(IScope scope) throws IOException
	{
		final IScope app = ScopeUtils.findApplication(scope);
		
		if(app != null)
		{
			Resource resource = app.getResource("WEB-INF");
			
	    	if(!resource.getFile().exists()){
	    		throw new IOException("SEVERE: WEB-INF folder not found");
	    	}
	    	
	    	File webinf = resource.getFile(); 
	    	File webapp = webinf.getParentFile();
	    	String webAppPath = webapp.getAbsolutePath().replace("\\", "/");
	    	
	    	if(scope.getContext().hasBean("streamFilenameGenerator"))
			{
				IStreamFilenameGenerator bean = (IStreamFilenameGenerator) scope.getContext().getBean("streamFilenameGenerator");
				String genname = bean.generateFilename(scope, "dummy.flv", GenerationType.PLAYBACK);
				String path = null;
				
				if(bean.resolvesToAbsolutePath())
				{
					path = genname.substring(0,genname.lastIndexOf("/"));
					return path;
							
				}
				else
				{
					path = genname.substring(0,genname.lastIndexOf("/"));
					
					final StringBuilder result = new StringBuilder();
			        final String prefix = path + "/";
			        while (scope != null && scope != app) {
			            result.insert(0, scope.getName() + "/");
			            scope = scope.getParent();
			        }
			        if (result.length() == 0) {
			            return String.format("%s%s%s", webAppPath, "/", prefix);
			        } else {
			            result.insert(0, prefix).append("/");
			            return result.toString();
			        }
				}
			}
			else
			{
				return getStreamDirectory(scope, webAppPath); 
			}
		}
		
		return null;
	}
	
	
	
	
	private static String getStreamDirectory(IScope scope, String webAppPath) {
        final StringBuilder result = new StringBuilder();
        final IScope app = ScopeUtils.findApplication(scope);
        final String prefix = "streams" + "/";
        while (scope != null && scope != app) {
            result.insert(0, scope.getName() + "/");
            scope = scope.getParent();
        }
        if (result.length() == 0) {
            return String.format("%s%s%s", webAppPath, "/", prefix);
        } else {
            result.insert(0, prefix).append("/");
            return result.toString();
        }
    }

}
