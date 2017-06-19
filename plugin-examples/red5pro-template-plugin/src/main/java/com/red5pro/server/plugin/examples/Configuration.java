package com.red5pro.server.plugin.examples;


public class Configuration {
	   
	
	private boolean active;
	
	
	public Configuration()
	{
		
	}
	
	
	
	
	public Configuration(boolean active)
	{
		this.active = active;
	}
	
	
	

	public boolean isActive() {
		return active;
	}




	public void setActive(boolean active) {
		this.active = active;
	}
	
	

	
}