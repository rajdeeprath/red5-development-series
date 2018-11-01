package com.red5pro.services.cloud.platformname.component;

import java.util.Arrays;
import java.util.List;

import com.red5pro.services.cloud.platformname.component.test.DestroyInstanceTest;
import com.red5pro.services.cloud.platformname.component.test.ScanListInstancesTest;
import com.red5pro.services.cloud.platformname.component.test.SpinInstanceTest;
import com.red5pro.services.cloud.platformname.component.test.StopInstanceTest;
import com.red5pro.services.cloud.platformname.component.test.SuggestLaunchLocationTest;

public class App {

	public static void main(String[] args) {
		
		/* Use location from your own cloud platform. This is availability zone and not region */
		String location = "us-east-1";
		
		/* Node name prefix -> found in streammanager red5-web.properties*/
		String namePrefix = "node";
		
		/* Mock instance identifier to test methods. It should have `node name prefix` in the begining of string*/
		String identifier = "node-eastus-a-11111";
	
		/* Mock platform instance identifier. Use id/name as defined by your platform to uniquely identify instance*/
		String platformIdentifier = "k3boef9r-eastus-vm";
		
		/* Available regions list. Use all regions that are targeted by your nodegroup */
		List<String> availableRegions = Arrays.asList("us-east-1");
		
		/* Zones used by Stream Manager in current cluster. Use a subset of total available zones to test. zones might be from one or more regions as targeted by your nodegroup. 
		 * Your controller shoudl know about all the available zones in a region and all the regions that it supports in prior. */
		List<String> assignedZones = Arrays.asList("us-east-1a", "us-east-1b", "us-east-1c");
		
		/* Last used zone. Use your cloud platform zone from a targeted region. */
		String lastUsedZone = "us-east-1a";
		
		/*Preferred region for next launch. Use your cloud platform region that is targeted via nodegroup*/
		String preferredRegion = "us-east-1";
		
		
		/*Preferred Instance type. Use a actual instance type from your launch configuration definition. Stream Manager will fetch instance type and pass it to the the controller, when it wants a suggestion.
		 * This may or may not be important for your evaluation of the next suitable availability zone. */
		String instanceType = "t2.micro";
		
		
		try
		{
			/* Initialize controller. In actual deployment instance is created via java beans in applicationContext.xml */ 
			SampleCloudController controller = new SampleCloudController();
			controller.setOperationTimeoutMilliseconds(30000);
			controller.initialize();
			
			
			/* Launch location test */
    		/*
    		SuggestLaunchLocationTest test0 = new SuggestLaunchLocationTest(controller);
    		test0.setAvailableRegions(availableRegions);
    		test0.setAssignedZones(assignedZones);
    		test0.setInstanceType(instanceType);
    		test0.setLastUsedZone(lastUsedZone);
    		test0.setPreferredRegion(preferredRegion);
    		test0.run();
    		*/
			
			
			/* Spin instance test */
			/*
			SpinInstanceTest test = new SpinInstanceTest(controller);
			test.setLocation(location); // simulated zone obtained through suggestLaunchLocation
			test.setIdentifier(identifier);
			test.setLaunchConfigPath("/resources/test.json");
			test.run();
			*/
			
			
    		
    		/* Read instance test */
    		/*
    		ReadInstanceTest test3 = new ReadInstanceTest(controller);
    		test3.setLocation(location);
    		test3.setIdentifier(identifier);
    		test3.setPlatformIdentifier(platformIdentifier);
    		test3.run();
    		*/
			
			
			/* Destroy instance test */
    		/*
    		DestroyInstanceTest test2 = new DestroyInstanceTest(controller);
    		test2.setLocation(location);
    		test2.setIdentifier(identifier);
    		test2.setPlatformIdentifier(platformIdentifier);
    		test2.run();
    		*/
			
			
			/* Stop instance test */
    		/*
    		StopInstanceTest test4 = new StopInstanceTest(controller);
    		test4.setLocation(location);
    		test4.setIdentifier(identifier);
    		test4.setPlatformIdentifier(platformIdentifier);
    		test4.run();
    		*/
			
			
			/* List Instances test - finds instances launched from current controller */
    		/*
    		ScanListInstancesTest test5 = new ScanListInstancesTest(controller);
    		test5.setNamePrefix(namePrefix);
    		test5.run();
    		*/
			
			
			/* Simulate long running parent  thread (streammanager) when debugging otherwise jvm will exit.
			 * To stop thread kill process in debugger. 
			 */
			Thread.sleep(900000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
