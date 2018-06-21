package org.red5.misc.examples.ffmpeg.thumbnailing;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionDataCallback;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionProcessCallback;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionResultCallback;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbNailExtractor implements Runnable, TranscodeSessionProcessCallback, TranscodeSessionDataCallback, TranscodeSessionResultCallback {
	
	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	private String ffmpegPath = "N:\\ffmpeg\\bin\\ffmpeg.exe";
	
	private String ffmpegCommand;
	
	private String workingDirectory = "K:\\thumbs";
	
	private String input;
	
	
	public ThumbNailExtractor(String ffmpegPath, String input, String ffmpegCommand, String workingDirectory)
	{
		this.ffmpegPath = ffmpegPath;
		this.input = input;
		this.ffmpegCommand = ffmpegCommand;
		this.workingDirectory = workingDirectory;
				
	}
	


	@Override
	public void run() 
	{
		try
		{
			CommandLine commandLine = new CommandLine(ffmpegPath);
			commandLine.addArgument("-i");
			commandLine.addArgument(input);
			
			String[] commandParts = ffmpegCommand.split(" ");
			for(int i=0;i<commandParts.length;i++){
				commandLine.addArgument(commandParts[i]);
			}
			
			log.info("command = " + commandLine.toString());
			
			DefaultExecutor executor = new DefaultExecutor();
			ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
			
			TranscodeSessionOutputStream outstream = new TranscodeSessionOutputStream(this);
			TranscodeSessionResultHandler resultHandler = new TranscodeSessionResultHandler(watchdog, this);
			
			executor.setWorkingDirectory(new File(workingDirectory));
			executor.setStreamHandler(new PumpStreamHandler(outstream));
			executor.setProcessDestroyer(new TranscodeSessionDestroyer(this));
			executor.setWatchdog(watchdog);
			executor.setExitValue(0);
			
			Thread.sleep(5000);
			executor.execute(commandLine, resultHandler);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void execute(String streamname, IScope app)
	{
		
	}

	
	
	@Override
	public void onTranscodeProcessAdded(Process proc) {
		// TODO Auto-generated method stub
		log.info("onTranscodeProcessAdded");
	}



	@Override
	public void onTranscodeProcessRemoved(Process proc) {
		// TODO Auto-generated method stub
		log.info("onTranscodeProcessRemoved");
	}



	@Override
	public void onTranscodeProcessStart(long timestamp) {
		log.debug("onTranscodeProcessStart");
	}



	@Override
	public void onTranscodeProcessData(Object data, long timestamp) {
		log.debug("onTranscodeProcessData");
	}



	@Override
	public void onTranscodeProcessComplete(int exitValue, long timestamp) {
		log.info("onTranscodeProcessComplete exitValue: " + exitValue);
	}



	@Override
	public void onTranscodeProcessFailed(ExecuteException e, ExecuteWatchdog watchdog, long timestamp) {
		String cause = null;
		
		if(watchdog != null && watchdog.killedProcess()) cause = "FAILURE_BY_TIMEOUT";
		else cause = "GENERIC_FAILURE";
		
		log.warn("onTranscodeProcessFailed or Probable force abort cause: " + cause);
	}

}
