package com.red5pro.services.cloud.platformname.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.red5pro.services.streammanager.exceptions.InstanceCreateException;
import com.red5pro.services.streammanager.exceptions.InstanceDeleteException;
import com.red5pro.services.streammanager.exceptions.InstanceReadException;
import com.red5pro.services.streammanager.exceptions.InstanceResetException;
import com.red5pro.services.streammanager.exceptions.InstanceStopException;
import com.red5pro.services.streammanager.exceptions.InstanceUpdateException;
import com.red5pro.services.streammanager.exceptions.LaunchLocationSuggestionException;
import com.red5pro.services.streammanager.interfaces.ICloudInstanceListResponse;
import com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController;
import com.red5pro.services.streammanager.interfaces.IDeleteInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IDeleteInstanceResponse;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationErrorResponse;
import com.red5pro.services.streammanager.interfaces.IInstanceOperationResponseHandler;
import com.red5pro.services.streammanager.interfaces.INewInstanceRequest;
import com.red5pro.services.streammanager.interfaces.INewInstanceResponse;
import com.red5pro.services.streammanager.interfaces.IReadInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IRed5InstanceResponse;
import com.red5pro.services.streammanager.interfaces.IResetInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IResetInstanceResponse;
import com.red5pro.services.streammanager.interfaces.IStopInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IStopInstanceResponse;
import com.red5pro.services.streammanager.interfaces.IUpdateInstanceRequest;
import com.red5pro.services.streammanager.interfaces.IUpdateInstanceResponse;
import com.red5pro.services.streammanager.nodes.model.CloudInstance;
import com.red5pro.services.streammanager.nodes.model.CloudInstanceListResponse;
import com.red5pro.services.streammanager.nodes.model.DeleteInstanceResponse;
import com.red5pro.services.streammanager.nodes.model.InstanceMetaDataItem;
import com.red5pro.services.streammanager.nodes.model.InstanceOperationErrorResponse;
import com.red5pro.services.streammanager.nodes.model.LaunchSuggestion;
import com.red5pro.services.streammanager.nodes.model.NewInstanceResponse;
import com.red5pro.services.streammanager.nodes.model.NodeState;
import com.red5pro.services.streammanager.nodes.model.ReadInstanceRequest;
import com.red5pro.services.streammanager.nodes.model.Red5Instance;
import com.red5pro.services.streammanager.nodes.model.Red5InstanceResponse;
import com.red5pro.services.streammanager.nodes.model.StopInstanceResponse;
import com.red5pro.services.streammanager.nodes.model.UpdateInstanceResponse;



public class SampleCloudController implements ICloudPlatformInstanceController {
	
	private Logger logger = LoggerFactory.getLogger(SampleCloudController.class);
	
	
	private static String platform = "Sample Cloud";
	
	
	private long operationTimeoutMilliseconds = 120000;

	
	private IInstanceOperationResponseHandler responseHandler;
	
	
	private boolean apiAvailable = true;


	private String[] regions;
	/*
	 * AWS Example: private String[] regions = new String[] { "us-gov-west-1", "us-east-1", "us-east-2", "us-west-1", "us-west-2", "eu-west-1", "eu-west-2", "eu-west-3", "eu-central-1", "ap-south-1", "ap-southeast-1", "ap-southeast-2", "ap-northeast-1", "ap-northeast-2", "sa-east-1", "ca-central-1" };
	 */
	
	
	private String[] availabilityZones;
	/*
	 * AWS Example: private String[] availabilityZones = new String[] { "us-gov-west-1a", "us-gov-west-1b", "us-gov-west-1c", "us-east-1a", "us-east-1b", "us-east-1c", "us-east-1d", "us-east-1e", "us-east-1f", "us-east-2a", "us-east-2b", "us-east-2c", "us-west-1a", "us-west-1b", "us-west-2a", "us-west-2b", "us-west-2c", "eu-west-1a", "eu-west-1b", "eu-west-1c", "eu-west-2a", "eu-west-2b", "eu-west-2c", "eu-west-3a", "eu-west-3b", "eu-west-3c", "eu-central-1a", "eu-central-1b", "eu-central-1c", "ap-south-1a", "ap-south-1b", "ap-southeast-1a", "ap-southeast-1b", "ap-southeast-1c", "ap-southeast-2a", "ap-southeast-2b", "ap-southeast-2c", "ap-northeast-1a", "ap-northeast-1b", "ap-northeast-1c", "ap-northeast-1d", "ap-northeast-2a", "ap-northeast-2c", "sa-east-1a", "sa-east-1b", "sa-east-1c", "ca-central-1a", "ca-central-1b" };
	 */
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#initialize()
	 */
	@Override
	public void initialize() 
	{
		// Initialize your controller. use this method to load any data / auth keys / policies / additional configs etc, that you need for your
		// controller. Place code within try-catch block
		
	}
	
	
	
	
	@Override
	public LaunchSuggestion suggestLaunchLocation(List<String> availableRegions, List<String> assignedZones, String lastUsedZone, String preferredRegion,
            String instanceType) throws LaunchLocationSuggestionException {
		
		// Stream manager core will request for the new suitable availability zone for a new instance via this method. It will provide a list of regions to select from (based on nodegroup),, a list of already used zones, the last used zone and the preferred region. 
		// the method simply needs to return a `LaunchSuggestion` object  that wraps the zone to use
		
		try
		{
			String suggestedZone = null;// determine a zone to use. Dont return null !!
			
			logger.info("Recommending zone " + suggestedZone + " for your region");
	        return new LaunchSuggestion(suggestedZone);
		}
		catch (Exception e) 
		{
            String eMsg = "Unable to find suitable availability zone. Cause : " + e.getMessage();
            logger.warn(eMsg);
            throw new LaunchLocationSuggestionException(eMsg);
        }
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#spinNewInstance(com.red5pro.services.streammanager.interfaces.INewInstanceRequest)
	 */
	@Override
	public INewInstanceResponse spinNewInstance(INewInstanceRequest req)	throws InstanceCreateException 
	{
		try 
		{
            logger.info("Launch started " + new Date().toString());
            
            String availabilityZone = req.getAvailabilityZone();
            
            String region; // lookup region for your availability zone
            
            /// construct your launch request object
            
            // Make request to your platform to initiate a new instance
            Object response = null; // store received a response object
            // to identify your request, its state and probably an `id` to track the request such as an instance `id`.
            
            // Start a thread to monitor your instance state / request status asynchronously without blocking the request thread
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new PostLaunchChecker(req, response));
            executorService.shutdown();
            
            // Return intermediate response to stream manager thread to acknowledge  a successful request
            String responseIdentifier = null; // A arbitrary `string` id related to the request. This can be the request id or a controller generated id
            return new NewInstanceResponse.Builder().withResponseTime(new Date()).withResponseToken(responseIdentifier)
                    .build(); 
		}
		catch(Exception e)
		{
			logger.error("Error creating instance " + e.getMessage());
			throw new InstanceCreateException(e.getMessage());
		}
	}
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#destroyInstance(com.red5pro.services.streammanager.interfaces.IDeleteInstanceRequest)
	 */
	@Override
	public IDeleteInstanceResponse destroyInstance(IDeleteInstanceRequest req)	throws InstanceDeleteException 
	{
		String availabilityZone = req.getAvailabilityZone();
		
		try
		{
			String region; // look up region for your availability zone
			
			// read the instance from platform to confirmt hat it can be deleted
			IRed5InstanceResponse response = getRed5Instance(new ReadInstanceRequest.Builder().withInstanceIdentifier(req.getInstanceIdentifier())
                    .withPlatformInstanceIdentifier(req.getPlatformInstanceIdentifier()).withAvailabilityZone(availabilityZone).withRequestTime(new Date())
                    .build());
			
			logger.info("================== Deleting Instance " + req.getInstanceIdentifier() + " ==================");
			
			
			// Make request to your platform to delete instance 
			Object deleteResponse = null; // store received a response object
            
			
			// Start a thread to delete monitor deletion asynchronously without blocking the request thread
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(new PostDeleteChecker(req, response, deleteResponse));
			executorService.shutdown();
			
			// Return intermediate response to stream manager thread to acknowledge  a successful request
			return new DeleteInstanceResponse.Builder().withResponseTime(new Date()).withResponseToken(String.valueOf(System.currentTimeMillis())).build();
		}
		catch (Exception e) 
		{
            logger.error("Error deleting instance " + e.getMessage());
            throw new InstanceDeleteException(e.getMessage());
        }
	}
	

	

	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#destroyCloudInstance(com.red5pro.services.streammanager.nodes.model.CloudInstance)
	 */
	@Override
	public void destroyCloudInstance(CloudInstance instance)	throws InstanceDeleteException 
	{
		try {
			String availabilityZone = instance.getAvailabilityZone();

			String region; // lookup region for your availability zone
			
			logger.info("================== Deleting Unwanted Instance " + instance.getPlatformIdentifier() + " ==================");

			// Request platform to terminate this instance. Tracking the process is not important since only `unwanted` / `zombie` instances are terminated using this method.
			
		} 
		catch (Exception e) 
		{
			logger.error("Error deleting instance " + instance.getPlatformIdentifier() + " " + e.getMessage());
			throw new InstanceDeleteException(e.getMessage());
		}
	}

	
	
	

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getAvailabilityZones()
	 */
	@Override
	public String[] getAvailabilityZones() 
	{
		return availabilityZones;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getOperationTimeoutMilliseconds()
	 */
	@Override
	public long getOperationTimeoutMilliseconds() 
	{
		return operationTimeoutMilliseconds;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getPlatform()
	 */
	@Override
	public String getPlatform() 
	{
		return platform;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getRed5Instance(com.red5pro.services.streammanager.interfaces.IReadInstanceRequest)
	 */
	@Override
	public IRed5InstanceResponse getRed5Instance(IReadInstanceRequest req)	throws InstanceReadException 
	{
		Red5Instance red5Instance = null;

		try {
			String availabilityZone = req.getAvailabilityZone();
			String platformIdentifier = req.getPlatformInstanceIdentifier();

			String region = null; // lookup region for the availability zone
			if (region == null)
				throw new Exception("Could not find a valid region for the given availability zone");

			Object instance = null;
			/*
			 * Try to read instance from platform using `platformIdentifier` and store the read instance object into `instance`. then convert `your instance object` into a 
			 * `Red5Instance` object that Stream Manager understands
			 */
			
			red5Instance = vendorInstanceToRed5Instance(req, instance);

			if (red5Instance == null){
				throw new InstanceReadException("Could not read instance details");
			}

			return new Red5InstanceResponse.Builder().withInstance(red5Instance).withResponseTime(new Date()).build();
		} catch (Exception e) {
			throw new InstanceReadException("Unable to read instance with identifier " + " " + req.getInstanceIdentifier() + " cause: " + e.getMessage());
		}
	}
	
	
	
	
	private Red5Instance vendorInstanceToRed5Instance(IReadInstanceRequest request, Object instance) {

        try {
            logger.debug(new Gson().toJson(instance));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Red5Instance red5Instance = new Red5Instance();
        red5Instance.setIdentifier(request.getInstanceIdentifier());
        
        // Determine the `platfoirmIdentifier` of the machine from ` your instance object` and store it
        String platformIdentifier = null;
        /* AWS Example : platformIdentifier = instance.getInstanceId() */
        red5Instance.setPlatformIdentifier(platformIdentifier);
        
        
        NodeState red5InstanceState = null;
        String instanceState; // <- get your instance state and store it

        // Translate your instance state into Red5 instance state
        
        /* AWS Example
        if (instanceState.getCode() == AWSInstanceStatus.PROVISIONING)
            cloudInstanceState = NodeState.PENDING;
        else if (instanceState.getCode() == AWSInstanceStatus.RUNNING)
            cloudInstanceState = NodeState.RUNNING;
        else if (instanceState.getCode() == AWSInstanceStatus.STOPPING)
            cloudInstanceState = NodeState.STOPPING;
        else if (instanceState.getCode() == AWSInstanceStatus.SHUTTING_DOWN)
            cloudInstanceState = NodeState.TERMINATING;
        else if (instanceState.getCode() == AWSInstanceStatus.TERMINATED)
            cloudInstanceState = NodeState.DISPOSED;
        */
        
        red5Instance.setState(red5InstanceState);

        Date creationTime = null;
        /* AWS Example : creationTime = awsInstance.getLaunchTime(); */
        red5Instance.setLaunchTime(creationTime);

        String availabilityZone = null; // <-Determine the availability zone of the node and store it
        /* AWS Example : availabilityZone = instance.getPlacement().getAvailabilityZone() */
        red5Instance.setAvailabilityZone(availabilityZone);

        String publicIP = null; // <-Determine the public ip address of the node and store it
        /* AWS Example : publicIP = instance.getPublicIpAddress(); */
        red5Instance.setPublicIpAddress(publicIP);

        String dns = null; // <-Determine the public dns of the node and store it (optional)
        /* AWS Example : dns = instance.getPublicDnsName() */
        red5Instance.setHostname(dns);

        return red5Instance;
    }

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getRed5InstanceResponseHandler()
	 */
	@Override
	public IInstanceOperationResponseHandler getRed5InstanceResponseHandler() 
	{
		return responseHandler;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getRegionforZone(java.lang.String)
	 */
	@Override
	public String getRegionforZone(String arg0) throws Exception 
	{
		// look up region for a specified availability zone and return the region string
		return null;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#getRegions()
	 */
	@Override
	public String[] getRegions() 
	{
		return regions;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#hasInstance(com.red5pro.services.streammanager.interfaces.IReadInstanceRequest)
	 */
	@Override
	public boolean hasInstance(IReadInstanceRequest req) throws IOException 
	{
		boolean found = false;

		try {
			String platformIdentifier = req.getPlatformInstanceIdentifier();
			if (platformIdentifier == null)
				throw new Exception("Invalid lookup request.");

			String availabilityZone = req.getAvailabilityZone();

			String region; // lookup region for your availability zone

			// use the platformIdentifier to check if you have an instance on the platform
			// if found then return true;
			found = true;			
		} 
		catch (Exception e) 
		{
			logger.warn("Unable to find instance for id " + req.getInstanceIdentifier() + ".Cause : " + e.getMessage());
			found = false;
		}

		return found;
	}
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#stopInstance(com.red5pro.services.streammanager.interfaces.IStopInstanceRequest)
	 */
	@Override
	public IStopInstanceResponse stopInstance(IStopInstanceRequest req)	 throws InstanceStopException 
	{
		String availabilityZone = req.getAvailabilityZone();
		
		try
		{
			String region; // look up region for your availability zone
			
			// read the instance from platform to confirm that it can be stopped
			IRed5InstanceResponse response = getRed5Instance(new ReadInstanceRequest.Builder().withInstanceIdentifier(req.getInstanceIdentifier())
                    .withPlatformInstanceIdentifier(req.getPlatformInstanceIdentifier()).withAvailabilityZone(availabilityZone).withRequestTime(new Date())
                    .build());
			
			logger.info("================== Stopping Instance " + req.getInstanceIdentifier() + " ==================");
			
			
			// Make request to your platform to delete instance 
			Object stopResponse = null; // store received a response object
            
			
			// Start a thread to delete monitor deletion asynchronously without blocking the request thread
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.execute(new PostStopChecker(req, response, stopResponse));
			executorService.shutdown();
			
			// Return intermediate response to stream manager thread to acknowledge  a successful request
			return new StopInstanceResponse.Builder().withResponseTime(new Date()).withResponseToken(String.valueOf(System.currentTimeMillis())).build();
		}
		catch (Exception e) 
		{
			logger.error("Error stopping instance " + req.getInstanceIdentifier() + " " + e.getMessage());
			throw new InstanceStopException(e.getMessage());
        }
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#resetInstance(com.red5pro.services.streammanager.interfaces.IResetInstanceRequest)
	 */
	@Override
	public IResetInstanceResponse resetInstance(IResetInstanceRequest arg0)	throws InstanceResetException 
	{
		// not required as not implemented at core. Method has no use yet.
		return null;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#scanListInstances(java.lang.String)
	 */
	@Override
	public ICloudInstanceListResponse scanListInstances(String namePrefix)	throws InstanceReadException 
	{
		// implement scanning your platform for unwanted instances here. Unwanted /zombie instances are those that were launched by 
		// stream manager but are not there in / lost from database due to communication issues, api problems etc
		
		try 
		{
            List<String> processedRegions = new ArrayList<String>();
            List<CloudInstance> cloudInstances = new ArrayList<CloudInstance>();
            String[] zones = getAvailabilityZones();
            
            for (String zone : zones) 
            {
            	try
            	{
	            	String region = null; //lookup region for your availability zone. should not be null
	
	                if (processedRegions.contains(region)) {
	                    continue;
	                }
	                
	                // collect region to mark it as processed
	                processedRegions.add(region);
	                
	                /* fetch all running instances in this region that belong to this streammanager
	                   use instance name / metadata to identify that the node belongs to this stream manager.
	                  the `instanceIdentifier` that was provided during spinNewInstance contained the `namePrefix` 
	                  in the starting of the string. Use substring checking to see if the `namePrefix` is present in the `instanceIdentifier` or 
	                  in metadata (if it was stored there).
	                  
	                  If the `namePrefix` is found in the string then the node belongs to current stream manager and a candidate for processing.
	                  */
	                Object instance = null; // read your instance object from your platform
	                boolean belongsToMe = false; // check if instance belongs to this stream manager
	                if (belongsToMe) 
	                {
	                	// convert `your instance object` to a `CloudInstance` object
	                	CloudInstance cloudInstance = vendorInstanceToCloudInstance(instance);
		                if (cloudInstance == null)
		                {
		                	throw new InstanceReadException("Could not read instance details");
		                }
		                
		                // add CloudInstance to list
		                cloudInstances.add(cloudInstance);
	                }
	                
            	}
            	catch (Exception e) 
        		{
                    logger.debug("Error in scanning for one or more instance(s)" + e.getMessage() + " zone = " + zone);
                    continue;
                }
            }
            
            // return list of collected lost instances 
            return new CloudInstanceListResponse.Builder().withInstance(cloudInstances).withResponseTime(new Date()).build();
		}
		catch (Exception e) 
		{
            throw new InstanceReadException("Unable to read one or more instance(s). Cause: " + e.getMessage());
        }
	}
	
	
	
	
	
	private CloudInstance vendorInstanceToCloudInstance(Object instance) {

        CloudInstance cloudInstance = new CloudInstance();
        
        // Determine the `platfoirmIdentifier` of the machine from ` your instance object` and store it  
        //cloudInstance.setPlatformIdentifier(platformIdentifier);

        NodeState cloudInstanceState = null;
        String instanceState; // <- get your instance state and store it

        // Translate your instance state into Red5 instance state
        
        /* AWS Example
        if (instanceState.getCode() == AWSInstanceStatus.PROVISIONING)
            cloudInstanceState = NodeState.PENDING;
        else if (instanceState.getCode() == AWSInstanceStatus.RUNNING)
            cloudInstanceState = NodeState.RUNNING;
        else if (instanceState.getCode() == AWSInstanceStatus.STOPPING)
            cloudInstanceState = NodeState.STOPPING;
        else if (instanceState.getCode() == AWSInstanceStatus.SHUTTING_DOWN)
            cloudInstanceState = NodeState.TERMINATING;
        else if (instanceState.getCode() == AWSInstanceStatus.TERMINATED)
            cloudInstanceState = NodeState.DISPOSED;
        */
        
        cloudInstance.setState(cloudInstanceState);

        Date creationTime = null; // get instance creation time as a java Date Object
        /* AWS Example : creationTime = instance.getLaunchTime(); */
        cloudInstance.setLaunchTime(creationTime);
        

        String availabilityZone = null; // <-Determine the availability zone of the node and store it
        /* AWS Example : availabilityZone = instance.getPlacement().getAvailabilityZone() */
        cloudInstance.setAvailabilityZone(availabilityZone);
        
        
        String publicIP = null; // <-Determine the public ip address of the node and store it
        /* AWS Example : publicIP = instance.getPublicIpAddress(); */
        cloudInstance.setPublicIpAddress(publicIP);

        
        String dns = null; // <-Determine the public dns of the node and store it (optional)
        /* AWS Example : dns = instance.getPublicDnsName() */
        cloudInstance.setHostname(dns);


        return cloudInstance;
    }
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#isApiBridgeAvailable()
	 */
	@Override
	public boolean isApiBridgeAvailable() 
	{
		return apiAvailable;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#setOperationTimeoutMilliseconds(long)
	 */
	@Override
	public void setOperationTimeoutMilliseconds(long arg0) 
	{
		this.operationTimeoutMilliseconds = arg0;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#setRed5InstanceResponseHandler(com.red5pro.services.streammanager.interfaces.IInstanceOperationResponseHandler)
	 */
	@Override
	public void setRed5InstanceResponseHandler(IInstanceOperationResponseHandler arg0) 
	{
		this.responseHandler = arg0;
	}

	

	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.red5pro.services.streammanager.interfaces.ICloudPlatformInstanceController#updateInstanceMetaData(com.red5pro.services.streammanager.interfaces.IUpdateInstanceRequest)
	 */
	@Override
	public IUpdateInstanceResponse updateInstanceMetaData(IUpdateInstanceRequest req) throws InstanceUpdateException 
	{
		// not required as not implemented at core. Method has no use yet explicitly.
		
		try {
			String availabilityZone = req.getAvailabilityZone();

			String region = null; // lookup region for availability zone


			// Read instance to update metadata
			IRed5InstanceResponse response = getRed5Instance(new ReadInstanceRequest.Builder().withInstanceIdentifier(req.getInstanceIdentifier())
					.withPlatformInstanceIdentifier(req.getPlatformInstanceIdentifier()).withAvailabilityZone(availabilityZone).withRequestTime(new Date())
					.build());

			logger.info("================== Updating Instance data " + response.getInstance().getIdentifier() + " ==================");

			// get `platformIdentifier`
			String platformIdentifier = req.getPlatformInstanceIdentifier();

			List<InstanceMetaDataItem> newInstanceMetadata = new ArrayList<InstanceMetaDataItem>();

			/* Update metadata from request */
			newInstanceMetadata.add(new InstanceMetaDataItem("identifier", req.getInstanceIdentifier()));
			newInstanceMetadata.add(new InstanceMetaDataItem("role", req.getRole().name().toLowerCase()));
			newInstanceMetadata.add(new InstanceMetaDataItem("connectionCapacity", String.valueOf(req.getEstimatedConnectionCapacity())));

			ExecutorService taggerService = Executors.newSingleThreadExecutor();
			taggerService.execute(new MetaDataTagger(newInstanceMetadata, req.getMetadata(), platformIdentifier));
			taggerService.shutdown();

			return new UpdateInstanceResponse.Builder().withResponseTime(new Date()).withResponseToken(String.valueOf(System.currentTimeMillis())).build();
		} 
		catch (Exception e) 
		{
			logger.error("Error updating instance data " + e.getMessage());
			throw new InstanceUpdateException(e.getMessage());
		}
	}



	@Override
	public CompletableFuture<IRed5InstanceResponse> getRed5InstanceAsync(IReadInstanceRequest arg0) {
		// not required as not implemented at core. Method has no use yet.
		return null;
	}



	@Override
	public CompletableFuture<Boolean> hasInstanceAsync(IReadInstanceRequest arg0) throws IOException {
		// not required as not implemented at core. Method has no use yet.
		return null;
	}



	@Override
	public CompletableFuture<ICloudInstanceListResponse> scanListInstancesAsync(String arg0) {
		// not required as not implemented at core. Method has no use yet.
		return null;
	}


	
	
	
	class PostLaunchChecker implements Runnable {
		
		INewInstanceRequest req;
		Object response;
		
		public PostLaunchChecker(INewInstanceRequest req, Object response)
		{
			this.req = req;
			this.response = response;
		}

		@Override
		public void run() 
		{
			String platformIdentifier = null; // should be obtained from response. this uniquely identifies your instance on the platform (`id` or `name`)
			
			try 
			{
                logger.info("Waiting for operation completion...");
                
                // Poll your platform for instance state using the identifier (platform identifier) provided in platform response periodically
                // There should be a time limit (`operationTimeoutMilliseconds`) within which the instance state should be returned as `running` otherwise the request needs to be declared as a `failure`.
                boolean operationSuccess = false;
                
                
                if(operationSuccess)
                {
                	logger.info("Launch operation success!");

                	/* Instances need to store metadata for identification. You can use your platform API  to update the basic metadata on instances once it is accessible. */
                	List<InstanceMetaDataItem> newInstanceMetadata = new ArrayList<InstanceMetaDataItem>();
        			newInstanceMetadata.add(new InstanceMetaDataItem("identifier", req.getInstanceIdentifier()));
        			newInstanceMetadata.add(new InstanceMetaDataItem("role", req.getRole().name().toLowerCase()));
        			newInstanceMetadata.add(new InstanceMetaDataItem("connectionCapacity", String.valueOf(req.getEstimatedConnectionCapacity())));
        			
        			// do logic to store initial required metadata on your instance here ->

                	
                	IRed5InstanceResponse response = getRed5Instance(new ReadInstanceRequest.Builder()
                			.withInstanceIdentifier(req.getInstanceIdentifier()).withPlatformInstanceIdentifier(platformIdentifier)
                			.withAvailabilityZone(req.getAvailabilityZone()).withRequestTime(new Date()).build());
                			responseHandler.onInstanceCreateSuccess(response);
                }
                else
                {
                	logger.info("Launch operation failed!");
                	
                	IInstanceOperationErrorResponse erresponse = new InstanceOperationErrorResponse.Builder().withResponseTime(new Date())
                			.withErrorMessage("Instance not found or failed to initialize.")
                			.forInstanceIdentifier(req.getInstanceIdentifier()).forPlatformInstanceIdentifier(platformIdentifier).build();

                			responseHandler.onInstanceCreateFailed(erresponse);
                }
			}
			catch(Exception e)
			{	
				logger.error("Launch operation error! " + e.getMessage());

				IInstanceOperationErrorResponse erresponse = new InstanceOperationErrorResponse.Builder().withResponseTime(new Date())
				.withErrorMessage(e.getMessage()).forInstanceIdentifier(req.getInstanceIdentifier()).forPlatformInstanceIdentifier(platformIdentifier)
				.atAvailabilityZone(req.getAvailabilityZone()).build();

				responseHandler.onInstanceCreateFailed(erresponse);
			}
		}
		
	}

	
	
	
	class PostDeleteChecker implements Runnable {

		Object deleteResponse;

		IDeleteInstanceRequest request;

		IRed5InstanceResponse response;

		public PostDeleteChecker(IDeleteInstanceRequest request, IRed5InstanceResponse response, Object deleteResponse) {
			this.request = request;
			this.response = response;
			this.deleteResponse = deleteResponse;
		}

		@Override
		public void run() {
			String instanceId = null;

			try {
				logger.info("Waiting for operation completion...");

				// Poll your platform for instance state using the identifier (platform identifier) provided in platform response periodically
                // There should be a time limit (`operationTimeoutMilliseconds`) within which the instance state should be returned as `terminated` (or whatever your platform specifies) otherwise the request needs to be declared as a `failure`.
                boolean operationSuccess = false;

				if (operationSuccess) 
				{
					logger.info("Delete operation Success!");
					responseHandler.onInstanceDeleted(response);
				} 
				else 
				{
					String reason = "unknown"; // construct a delete error message reason string if your platform supports
					logger.error("Delete operation Error! " + reason);
					IInstanceOperationErrorResponse erresponse = new InstanceOperationErrorResponse.Builder().withResponseTime(new Date())
							.withErrorMessage(reason).forInstanceIdentifier(request.getInstanceIdentifier())
							.forPlatformInstanceIdentifier(request.getPlatformInstanceIdentifier()).atAvailabilityZone(request.getAvailabilityZone()).build();

					responseHandler.onInstanceDeleteFailed(erresponse);

				}
			} catch (Exception e) {
				logger.error("Delete operation Error! " + e.getMessage());

				IInstanceOperationErrorResponse erresponse = new InstanceOperationErrorResponse.Builder().withResponseTime(new Date())
						.withErrorMessage(e.toString()).forInstanceIdentifier(request.getInstanceIdentifier())
						.forPlatformInstanceIdentifier(request.getPlatformInstanceIdentifier()).atAvailabilityZone(request.getAvailabilityZone()).build();

				responseHandler.onInstanceDeleteFailed(erresponse);
			}
		}

	}
	
	
	
	
	class PostStopChecker implements Runnable {

		Object stopResponse;

		IStopInstanceRequest request;

		IRed5InstanceResponse response;

		public PostStopChecker(IStopInstanceRequest request, IRed5InstanceResponse response, Object stopResponse) {
			this.request = request;
			this.response = response;
			this.stopResponse = stopResponse;
		}

		@Override
		public void run() {
			String instanceId = null;

			try {
				logger.info("Waiting for operation completion...");

				// Poll your platform for instance state using the identifier (platform identifier) provided in platform response periodically
                // There should be a time limit (`operationTimeoutMilliseconds`) within which the instance state should be returned as `stopped` (or whatever your platform specifies) otherwise the request needs to be declared as a `failure`.
                boolean operationSuccess = false;

				if (operationSuccess) 
				{
					logger.info("Stop operation Success!");
					responseHandler.onInstanceStopSuccess(response);
				} 
				else 
				{
					String reason = "unknown"; // construct a delete error message reason string if your platform supports
					logger.error("Stop operation Error! " + reason);
					IInstanceOperationErrorResponse erresponse = new InstanceOperationErrorResponse.Builder().withResponseTime(new Date())
							.withErrorMessage(reason).forInstanceIdentifier(request.getInstanceIdentifier())
							.forPlatformInstanceIdentifier(request.getPlatformInstanceIdentifier()).atAvailabilityZone(request.getAvailabilityZone()).build();

					responseHandler.onInstanceStopFailed(erresponse);

				}
			} catch (Exception e) {
				logger.error("Stop operation Error! " + e.getMessage());

				IInstanceOperationErrorResponse erresponse = new InstanceOperationErrorResponse.Builder().withResponseTime(new Date())
						.withErrorMessage(e.toString()).forInstanceIdentifier(request.getInstanceIdentifier())
						.forPlatformInstanceIdentifier(request.getPlatformInstanceIdentifier()).atAvailabilityZone(request.getAvailabilityZone()).build();

				responseHandler.onInstanceStopFailed(erresponse);
			}
		}
	}
	
	
	
	
	class MetaDataTagger implements Runnable {

		List<InstanceMetaDataItem> specified;

		List<InstanceMetaDataItem> newInstanceMetadata;

		String target;

		public MetaDataTagger(List<InstanceMetaDataItem> newInstanceMetadata, List<InstanceMetaDataItem> specified, String target) {
			this.specified = specified;
			this.newInstanceMetadata = newInstanceMetadata;
			this.target = target;
		}

		@Override
		public void run() {

			/* Add any pre configured metadata */
			if (specified != null) {
				for (InstanceMetaDataItem data : specified) {
					newInstanceMetadata.add(data);
				}
			}

			/* Add new meta-data */
			/*
			newInstanceMetadata.add(new InstanceMetaDataItem("identifier", request.getInstanceIdentifier()));
			newInstanceMetadata.add(new InstanceMetaDataItem("role", request.getRole().name().toLowerCase()));
			newInstanceMetadata.add(new InstanceMetaDataItem("connectionCapacity",
					String.valueOf(request.getEstimatedConnectionCapacity())));
			*/

			// store metadata on your platform for the instance
		}

	}
}
