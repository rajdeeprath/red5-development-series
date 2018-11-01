package com.red5pro.services.cloud.platformname.component.test;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.services.cloud.platformname.component.SampleCloudController;
import com.red5pro.services.streammanager.exceptions.InstanceReadException;
import com.red5pro.services.streammanager.exceptions.InvalidLaunchConfigurationException;
import com.red5pro.services.streammanager.interfaces.ICloudInstanceListResponse;
import com.red5pro.services.streammanager.nodes.model.CloudInstance;

public class ScanListInstancesTest {
	
	
	private static Logger logger = LoggerFactory.getLogger(ScanListInstancesTest.class);
	
	
	/**
	 * Controller
	 */
	private SampleCloudController controller;
	
	
	/**
	 * Node Name prefix that helps identify the node 
	 */
	private String namePrefix;
	
	
	public ScanListInstancesTest(SampleCloudController controller)
	{
		this.controller = controller;
	}
	
	
	
	public void run() throws IOException, InvalidLaunchConfigurationException, IllegalAccessException, JSONException, InstanceReadException
	{
		logger.info("Running scan test on subscription with namePrefix " + namePrefix);
		
		ICloudInstanceListResponse response = controller.scanListInstances(namePrefix);
		List<CloudInstance> instances = response.getInstances();
		for(CloudInstance instance : instances)
		{
			logger.info("Own instance found = " + instance.getPlatformIdentifier());
		}
	}



	public String getNamePrefix() {
		return namePrefix;
	}



	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	
	

}
