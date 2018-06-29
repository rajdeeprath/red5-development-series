package org.red5.misc.examples.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB 
				maxFileSize=1024*1024*50,      	// 50 MB
				maxRequestSize=1024*1024*100)   	// 100 MB
public class FileUploadServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(FileUploadServlet.class);

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5563512641545597892L;
	
	
	@Autowired
	private Application application;
	
	
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}	
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		try
		{
			log.debug("Receiving upload");
			
			Part filePart = request.getPart("myupload");
			
			// IMPORTANT!! - make sure to add additional security checks / token / signature to ensure that upload is from authentic source 
			
		    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		    InputStream fileContent = filePart.getInputStream();
		    
		    
		    Resource resource = application.getContext().getResource("WEB-INF");
		    File webInf = resource.getFile();
		    if(!webInf.exists()){
		    	throw new Exception("WEB-INF could not be located");
		    }
		    
		    File uploads = new File(webInf + File.separator + application.getUploadDir());
		    if(!uploads.exists()){
		    	uploads.mkdirs();
		    }

		    // Add better check and validation logic here for security 
		    File file = new File(uploads, fileName);
		    if(file.exists()){
		    	throw new Exception("file by name " + fileName + " already exists");
		    }
		    
		    Files.copy(fileContent, file.toPath());
		    
		    log.debug("Upload saved");
		    
		    PrintWriter out = response.getWriter();
		    out.println("File uploaded successfully!!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	

}
