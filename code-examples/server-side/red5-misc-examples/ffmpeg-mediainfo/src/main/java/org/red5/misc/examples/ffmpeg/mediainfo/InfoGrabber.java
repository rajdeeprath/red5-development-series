package org.red5.misc.examples.ffmpeg.mediainfo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionDataCallback;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionProcessCallback;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionResultCallback;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfoGrabber implements Runnable, SessionProcessCallback, SessionDataCallback, SessionResultCallback {
	
	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	private String ffmpegPath = "N:\\ffmpeg\\bin\\ffprobe.exe";
	
	private String ffmpegCommand;
	
	private String workingDirectory = "K:\\thumbs";
	
	private String input;
	
	
	public InfoGrabber(String ffmpegPath, String input, String ffmpegCommand, String workingDirectory)
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
			CommandLine commandLine;
			File bash = null;
			
			if(!SystemUtils.IS_OS_WINDOWS)
        	{
            	// commented this as it is not working on windows
                // look for bash shell
				bash = new File("/bin/bash");
                if (bash.exists() && bash.canExecute()) {
                    log.debug("Bash was found and is executable");
                } else {
                    throw new Exception("Shell not available");
                }
        	}
			
			File ffmpeg = new File(ffmpegPath);
            
            if (ffmpeg.exists() && ffmpeg.canExecute() && !ffmpeg.isDirectory()) 
            {
                log.debug("FFMpeg was found and is executable");
            } 
            else if (ffmpeg.isDirectory()) 
            {
                throw new Exception(String.format("FFMpeg path is pointing at a directory, verify path: %s", ffmpegPath));
            } 
            else 
            {
                throw new Exception(String.format("FFMpeg not available, verify path: %s", ffmpegPath));
            }
            
           
            if(!SystemUtils.IS_OS_WINDOWS)
            {
            	commandLine = new CommandLine(bash);
            	commandLine.addArgument("-c");
            	
            	String ffmpegCmdline = buildLinuxCommand(input, ffmpegCommand);
            	commandLine.addArgument(String.format("%s %s", ffmpeg.getAbsolutePath(), ffmpegCmdline), false);
            }
            else
            {
            	commandLine = new CommandLine(ffmpegPath);
            	commandLine.addArgument("-i");
    			commandLine.addArgument(input);
    			
    			String[] commandParts = ffmpegCommand.split(" ");
    			for(int i=0;i<commandParts.length;i++){
    				commandLine.addArgument(commandParts[i]);
    			}
            }
			
			log.debug("command = " + commandLine.toString());			
			
			DefaultExecutor executor = new DefaultExecutor();
			ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
			
			SessionOutputStream outstream = new SessionOutputStream(this);
			SessionResultHandler resultHandler = new SessionResultHandler(watchdog, this, outstream);
			
			executor.setWorkingDirectory(new File(workingDirectory));
			executor.setStreamHandler(new PumpStreamHandler(outstream));
			executor.setProcessDestroyer(new SessionDestroyer(this));
			executor.setWatchdog(watchdog);
			executor.setExitValue(0);
			
			executor.execute(commandLine, resultHandler);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	private String buildLinuxCommand(String input, String ffmpegCommand) {
		return "-i" + " " + input + " " + ffmpegCommand;
	}



	@Override
	public void onProcessAdded(Process proc) {
		// TODO Auto-generated method stub
		log.debug("onProcessAdded");
	}



	@Override
	public void onProcessRemoved(Process proc) {
		// TODO Auto-generated method stub
		log.debug("onProcessRemoved");
	}



	@Override
	public void onProcessStart(long timestamp) {
		log.debug("onProcessStart");
	}



	@Override
	public void onProcessData(Object data, long timestamp) {
		log.debug("onProcessData");
	}



	@Override
	public void onProcessComplete(int exitValue, long timestamp, Object data) {
		
		log.debug("onProcessComplete exitValue: " + exitValue);
		
		MediaInfo result = (MediaInfo) data;
		File source = new File(input);
		if(source.isFile()){
			result.setName(source.getName());
			result.setType(FilenameUtils.getExtension(source.getName()));
		}
		
		log.info("Result {}", result.toString());
	}



	@Override
	public void onProcessFailed(ExecuteException e, ExecuteWatchdog watchdog, long timestamp) {
		String cause = null;
		
		if(watchdog != null && watchdog.killedProcess()) cause = "FAILURE_BY_TIMEOUT";
		else cause = "GENERIC_FAILURE";
		
		log.warn("onProcessFailed or Probable force abort cause: " + cause);
	}

}
