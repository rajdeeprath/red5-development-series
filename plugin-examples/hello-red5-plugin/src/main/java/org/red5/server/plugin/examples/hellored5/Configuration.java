package org.red5.server.plugin.examples.hellored5;


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