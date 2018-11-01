package com.red5pro.services.cloud.platformname.component.test;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.services.cloud.platformname.component.SampleCloudController;
import com.red5pro.services.streammanager.exceptions.InstanceDeleteException;
import com.red5pro.services.streammanager.exceptions.InvalidLaunchConfigurationException;
import com.red5pro.services.streammanager.interfaces.IDeleteInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationErrorResponse;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationResponseHandler;
import com.red5pro.services.streammanager.interfaces.IRed5InstanceResponse;
import com.red5pro.services.streammanager.nodes.model.DeleteInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.TerminateReason;

public class DestroyInstanceTest {
	
    private static Logger logger = LoggerFactory.getLogger(DestroyInstanceTest.class);
	
    /**
     * controller instance
     */
	private SampleCloudController controller;
	
	/**
	 * instance identifier
	 */
	private String identifier;
	
	
	/**
	 * platform instance identifier
	 */
	private String platformIdentifier;
	
	
	/**
	 * availability zone of instance
	 */
	private String location;
	
	
	public DestroyInstanceTest(SampleCloudController controller)
	{
		this.controller = controller;
	}
	
	
	public void run() throws IOException, InvalidLaunchConfigurationException, IllegalAccessException, JSONException, InstanceDeleteException
	{
		logger.info("Running delete test at location " + getLocation() + " on " + platformIdentifier);
		
		IDeleteInstanceRequest request = new DeleteInstanceRequest.Builder()
		.withAvailabilityZone(location)
		.withInstanceIdentifier(identifier)
		.withPlatformInstanceIdentifier(platformIdentifier)
		.withReason(TerminateReason.SCALEDOWN)
		.withRequestTime(new Date())
		.build();
		
		
		controller.setRed5InstanceResponseHandler(handler);
		controller.destroyInstance(request);
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
			logger.info("Instance deleted " + response.getInstance().toString());
		}

		@Override
		public void onInstanceDeleteFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			logger.info("Instance deletion failed " + response.getError());
			
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
			
			
		}

		@Override
		public void onInstanceStopFailed(
				IInstanceOperationErrorResponse response) {
			// TODO Auto-generated method stub
			
		}
    	
    };

}
