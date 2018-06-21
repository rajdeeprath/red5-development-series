package org.red5.misc.examples.ffmpeg.thumbnailing;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionResultCallback;

public class TranscodeSessionResultHandler extends DefaultExecuteResultHandler {

	private ExecuteWatchdog watchdog;
	private TranscodeSessionResultCallback callback;
	private long abortRequestTimestamp = 0;
	
	public TranscodeSessionResultHandler(ExecuteWatchdog watchdog){
		this.setWatchdog(watchdog);
	}
	
	public TranscodeSessionResultHandler(ExecuteWatchdog watchdog, TranscodeSessionResultCallback callback){
		this.setWatchdog(watchdog);
		this.setCallback(callback);
	}
	
	private void setWatchdog(ExecuteWatchdog watchdog) {
		this.watchdog = watchdog;
	}

	@Override
	public ExecuteException getException() {
		// TODO Auto-generated method stub
		return super.getException();
	}

	@Override
	public int getExitValue() {
		// TODO Auto-generated method stub
		return super.getExitValue();
	}

	@Override
	public boolean hasResult() {
		// TODO Auto-generated method stub
		return super.hasResult();
	}

	@Override
	public void onProcessComplete(int exitValue) {
		// TODO Auto-generated method stub
		if(this.callback != null) 
		this.callback.onTranscodeProcessComplete(exitValue, System.currentTimeMillis());
		
		super.onProcessComplete(exitValue);
	}

	@Override
	public void onProcessFailed(ExecuteException e) {
		// TODO Auto-generated method stub
				
		if(this.callback != null) 
		this.callback.onTranscodeProcessFailed(e, watchdog, System.currentTimeMillis());
		
		super.onProcessFailed(e);
	}
	
	public ExecuteWatchdog getWatchdog() {
		return watchdog;
	}
	

	public TranscodeSessionResultCallback getCallback() {
		return callback;
	}

	public void setCallback(TranscodeSessionResultCallback callback) {
		this.callback = callback;
	}

	public long getAbortRequestTimestamp() {
		return abortRequestTimestamp;
	}

	public void setAbortRequestTimestamp(long abortRequestTimestamp) {
		this.abortRequestTimestamp = abortRequestTimestamp;
	}

}