package org.red5.misc.examples.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SampleHttpSevlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7731281558788266706L;
	
	
	
	@Autowired
	private Application application;
	
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}	

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(application != null){
			resp.getWriter().write("Application context path = " + application.getPath());
		}else{
			resp.getWriter().write("Error!. Could not resolve reference to application");
		}
		
		
	}
	
	
	
	

}
