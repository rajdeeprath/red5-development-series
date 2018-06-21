package org.red5.misc.examples.ffmpeg.thumbnailing;



import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionDataCallback;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionProcessCallback;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionResultCallback;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;


public class Application extends MultiThreadedApplicationAdapter {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	private String ffmpegPath = "N:\\ffmpeg\\bin\\ffmpeg.exe";
	
	private String protocol = "rtsp";
	
	private int port = 8554;
	
	private String thumbnailExtractCommand = "-y -vframes 1 %s.jpg";
	
	private String workingDirectory = "K:\\thumbs";
	
	private ExecutorService execservice = Executors.newCachedThreadPool();

	private IScope app;
	
	

	@Override
	public boolean appStart(IScope app) {
		this.app = app;
		
		
        try 
        {
        	Resource resource = app.getResource("WEB-INF");
        	if(!resource.getFile().exists()){
        		log.error("servere error : web-inf folder not found");
        	}
        	
			File thumbsDirectory = new File(resource.getFile().getParentFile().getAbsolutePath() + File.separator + "thumbs");
			if(!thumbsDirectory.exists()){
				thumbsDirectory.mkdirs();
			}
			
			this.workingDirectory = thumbsDirectory.getAbsolutePath();
			log.info("Working directory {}", this.workingDirectory);
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
		String input = buildInput(protocol, port, stream);
		String command = String.format(thumbnailExtractCommand, stream.getPublishedName());
		execservice.submit(new ThumbNailExtractor(ffmpegPath, input, command, workingDirectory));
		super.streamBroadcastStart(stream);
	}



	private String buildInput(String protocol, int port, IBroadcastStream stream) {
		return protocol + "://127.0.0.1:" + port + "/" + app.getName() + "/" + stream.getPublishedName();
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
	
	
	


	public String getProtocol() {
		return protocol;
	}



	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}



	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}



	public String getThumbnailExtractCommand() {
		return thumbnailExtractCommand;
	}



	public void setThumbnailExtractCommand(String thumbnailExtractCommand) {
		this.thumbnailExtractCommand = thumbnailExtractCommand;
	}



	public String getWorkingDirectory() {
		return workingDirectory;
	}



	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
}
