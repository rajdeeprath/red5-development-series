package org.red5.misc.examples.ffmpeg.mediainfo;



import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionDataCallback;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionProcessCallback;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionResultCallback;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	private String ffmpegPath = "N:\\ffmpeg\\bin\\ffmpeg.exe";
	
	private String workingDirectory = "K:\\thumbs";
	
	private ExecutorService execservice = Executors.newCachedThreadPool();

	private IScope app;
	
	private static String INPUT_TEST_FLV = "20051210-w50s.flv";
	

	@Override
	public boolean appStart(IScope app) {
		this.app = app;
		
		
        try 
        {
        	Resource resource = app.getResource("WEB-INF");
        	if(!resource.getFile().exists()){
        		log.error("severe error : web-inf folder not found");
        	}
        	
			File streamsDirectory = new File(resource.getFile().getParentFile().getAbsolutePath() + File.separator + "streams");
			if(!streamsDirectory.exists()){
				streamsDirectory.mkdirs();
			}
			
			this.workingDirectory = streamsDirectory.getAbsolutePath();
			log.info("Working directory {}", this.workingDirectory);
			
			// Start file check in thread
			execservice.execute(new Runnable(){

				@Override
				public void run() {
					
					File sample = new File(streamsDirectory.getAbsolutePath() + File.separator + INPUT_TEST_FLV);
					new InfoGrabber(getFfmpegPath(), sample.getAbsolutePath(), "-hide_banner", workingDirectory).run();
				}
				
			});
			
			
		} 
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return super.appStart(app);
	}

	
	


	@Override
	public void appStop(IScope app) {
		execservice.shutdown();
		super.appStop(app);
	}





	@Override
	public void streamBroadcastClose(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamBroadcastClose(stream);
	}



	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamBroadcastStart(stream);
	}

	//execservice.submit(new ThumbNailExtractor(ffmpegPath, input, command, workingDirectory));
	

	private String buildInput(String protocol, int port, String fileName) {
		return protocol + "://127.0.0.1:" + port + "/" + app.getName() + "/" + fileName;
	}





	@Override
	public void streamPublishStart(IBroadcastStream stream) {
		super.streamPublishStart(stream);
	}



	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamRecordStart(stream);
	}



	@Override
	public void streamRecordStop(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamRecordStop(stream);
	}



	public String getFfmpegPath() {
		return ffmpegPath;
	}



	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}
	
	



	public String getWorkingDirectory() {
		return workingDirectory;
	}



	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
}
