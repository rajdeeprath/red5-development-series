package com.red5pro.services.cloud.platformname.component.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red5pro.services.cloud.platformname.component.SampleCloudController;
import com.red5pro.services.streammanager.exceptions.LaunchLocationSuggestionException;
import com.red5pro.services.streammanager.nodes.model.LaunchSuggestion;

public class SuggestLaunchLocationTest {
	
	
    private static Logger logger = LoggerFactory.getLogger(SuggestLaunchLocationTest.class);
	
    /**
     * Controller
     */
    private SampleCloudController controller;
	
    
    /**
     * Available regions list
     */
	List<String> availableRegions;
	
	
	/**
	 * Zones used by Stream Manager in current cluster
	 */
	List<String> assignedZones;
	
	
	/**
	 * Last used zone
	 */
	String lastUsedZone;
	
	
	/**
	 * Preferred region for next launch
	 */
	String preferredRegion;
	
	
	/**
	 * Preferred Instance type
	 */
	String instanceType;
	
	
	
	public SuggestLaunchLocationTest(SampleCloudController controller)
	{
		this.controller = controller;
	}
	
	
	public void run() throws LaunchLocationSuggestionException 
	{
		LaunchSuggestion nextLaunchLocation = controller.suggestLaunchLocation(availableRegions, assignedZones, lastUsedZone, preferredRegion, instanceType);
		logger.info("Next launch location = {} ", nextLaunchLocation);
	}



	public List<String> getAvailableRegions() {
		return availableRegions;
	}



	public void setAvailableRegions(List<String> availableRegions) {
		this.availableRegions = availableRegions;
	}



	public List<String> getAssignedZones() {
		return assignedZones;
	}



	public void setAssignedZones(List<String> assignedZones) {
		this.assignedZones = assignedZones;
	}



	public String getLastUsedZone() {
		return lastUsedZone;
	}



	public void setLastUsedZone(String lastUsedZone) {
		this.lastUsedZone = lastUsedZone;
	}



	public String getPreferredRegion() {
		return preferredRegion;
	}



	public void setPreferredRegion(String preferredRegion) {
		this.preferredRegion = preferredRegion;
	}



	public String getInstanceType() {
		return instanceType;
	}



	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}
	
	
	
	

}
