package com.red5pro.services.cloud.platformname.component.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.services.cloud.platformname.component.SampleCloudController;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationResponseHandler;
import com.red5pro.services.streammanager.interfaces.ILaunchConfigurationSchema;
import com.red5pro.services.streammanager.interfaces.INewInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.LaunchConfigurationSchema;
import com.red5pro.services.streammanager.nodes.model.NewInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.NodeRole;

public class SpinInstanceTest implements ITest {
	
	private Logger logger = LoggerFactory.getLogger(SampleCloudController.class);

	
	/**
	 * Instance of controller
	 */
	private SampleCloudController controller;
	
	/**
	 * Instance identifier (must be unique)
	 */
	private String identifier;
	
	/**
	 * Availability zone to use
	 */
	private String location;
	
	/**
	 * Path to your launch config json file
	 */
	private String launchConfigPath;
	
	
	/**
	 * Response handler
	 */
	private IInstanceOperationResponseHandler handler;
	
	
	public SpinInstanceTest(SampleCloudController controller)
	{
		this.controller = controller;
	}

	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		logger.info("Running spin test at location " + getLocation() + "using launch config!");
		
		InputStream is = new FileInputStream(launchConfigPath);
        String jsonTxt = IOUtils.toString(is, "UTF-8");
        JSONObject json = new JSONObject(jsonTxt);       
        ILaunchConfigurationSchema config = new LaunchConfigurationSchema.Builder()
											.fromJsonString(jsonTxt)
											.build();
        
        
		INewInstanceRequest request = new NewInstanceRequest.Builder()
		.usingImage(config.getImage())
		.usingMachineType(config.getLaunchConfigurationForTarget("origin").getInstanceType())
		.atAvailabilityZone(location)
		.withAssumedRole(NodeRole.ORIGIN)
		.withEstimatedConnectionCapacity(config.getLaunchConfigurationForTarget("origin").getConnectionCapacity())
		.withInstanceId(identifier)
		.usingMetadata(config.getMetadata())
		.usingProperties(config.getProperties())
		.withRequestTime(new Date())
		.build();
		
		
		controller.setRed5InstanceResponseHandler(handler);
		controller.spinNewInstance(request);
	}

	public SampleCloudController getController() {
		return controller;
	}

	public void setController(SampleCloudController controller) {
		this.controller = controller;
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

	public String getLaunchConfigPath() {
		return launchConfigPath;
	}

	public void setLaunchConfigPath(String launchConfigPath) {
		this.launchConfigPath = launchConfigPath;
	}

	public IInstanceOperationResponseHandler getHandler() {
		return handler;
	}

	public void setHandler(IInstanceOperationResponseHandler handler) {
		this.handler = handler;
	}
	
	
	
}
