package com.red5pro.services.cloud.platformname.component.test;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.services.cloud.platformname.component.SampleCloudController;
import com.red5pro.services.streammanager.exceptions.InstanceReadException;
import com.red5pro.services.streammanager.exceptions.InvalidLaunchConfigurationException;
import com.red5pro.services.streammanager.interfaces.IReadInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IRed5InstanceResponse;
import com.red5pro.services.streammanager.nodes.model.ReadInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.Red5Instance;

public class ReadInstanceTest {
	
	
	private static Logger logger = LoggerFactory.getLogger(ReadInstanceTest.class);
	
	private SampleCloudController controller;
	
	/**
	 * Instance identifier
	 */
	private String identifier;
	
	
	/**
	 * Platform instance identifier
	 */
	private String platformIdentifier;
	
	
	/**
	 * Availability zone of the instance 
	 */
	private String location;
	
	
	public ReadInstanceTest(SampleCloudController controller)
	{
		this.controller = controller;
	}
	
	
	
	public void run() throws IOException, InvalidLaunchConfigurationException, IllegalAccessException, JSONException, InstanceReadException
	{
		logger.info("Running read test at location " + getLocation() + " on " + platformIdentifier);
		
		IReadInstanceRequest request = new ReadInstanceRequest.Builder()
		.withAvailabilityZone(location)
		.withPlatformInstanceIdentifier(getPlatformIdentifier())
		.withInstanceIdentifier(getIdentifier())
		.withRequestTime(new Date())
		.build();

		IRed5InstanceResponse response = controller.getRed5Instance(request);
		Red5Instance instance = response.getInstance();
		logger.info(instance.toString());
	}
	
	
	

	public String getIdentifier() {
		return identifier;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}



	public String getPlatformIdentifier() {
		return platformIdentifier;
	}


	public void setPlatformIdentifier(String platformIdentifier) {
		this.platformIdentifier = platformIdentifier;
	}

}
