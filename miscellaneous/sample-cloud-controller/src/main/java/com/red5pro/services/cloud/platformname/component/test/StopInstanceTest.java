package com.red5pro.services.cloud.platformname.component.test;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.services.cloud.platformname.component.SampleCloudController;
import com.red5pro.services.streammanager.exceptions.InstanceStopException;
import com.red5pro.services.streammanager.exceptions.InvalidLaunchConfigurationException;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationErrorResponse;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationResponseHandler;
import com.red5pro.services.streammanager.interfaces.IRed5InstanceResponse;
import com.red5pro.services.streammanager.interfaces.IStopInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.StopInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.TerminateReason;

public class StopInstanceTest {
	
    private static Logger logger = LoggerFactory.getLogger(StopInstanceTest.class);
	
    /**
     * Controller
     */
    private SampleCloudController controller;
	
    
    /**
     * Instance identifier
     */
	private String identifier;
	
	
	/**
	 * Platform Instance identifier
	 */
	private String platformIdentifier;
	
	
	/**
	 * Availability zone of instance
	 */
	private String location;
	
	
	
	public StopInstanceTest(SampleCloudController controller)
	{
		this.controller = controller;
	}
	
	
	public void run() throws IOException, InvalidLaunchConfigurationException, IllegalAccessException, JSONException, InstanceStopException
	{
		logger.info("Running stop test at location " + getLocation() + " on " + platformIdentifier);
		
		IStopInstanceRequest request = new StopInstanceRequest.Builder()
		.withAvailabilityZone(getLocation())
		.withInstanceIdentifier(getIdentifier())
		.withPlatformInstanceIdentifier(getPlatformIdentifier())
		.withReason(TerminateReason.UNKNOWN)
		.withRequestTime(new Date())
		.build();

		controller.setRed5InstanceResponseHandler(handler);
		controller.stopInstance(request);
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




	IInstanceOperationResponseHandler handler = new IInstanceOperationResponseHandler()
	{

		@Override
		public void onInstanceCreateSuccess(
				IRed5InstanceResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInstanceCreateFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onInstanceDeleted(IRed5InstanceResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInstanceDeleteFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onInstanceUpdated(IRed5InstanceResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInstanceUpdateFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInstanceResetSuccess(
				IRed5InstanceResponse response) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onInstanceResetFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onInstanceStopSuccess(IRed5InstanceResponse response) {
			// TODO Auto-generated method stub
			
			logger.info("Instance stopped " + response.getInstance().toString());
		}

		@Override
		public void onInstanceStopFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			
			logger.info("Instance stop failed " + response.getError());
			
		}
    	
    };

}
