package org.red5.misc.examples.ffmpeg.mediainfo;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.IResultProvider;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionResultCallback;

public class SessionResultHandler extends DefaultExecuteResultHandler {

	private ExecuteWatchdog watchdog;
	private SessionResultCallback callback;
	private long abortRequestTimestamp = 0;
	private IResultProvider resultProvider;
	
	public SessionResultHandler(ExecuteWatchdog watchdog){
		this.setWatchdog(watchdog);
	}
	
	public SessionResultHandler(ExecuteWatchdog watchdog, SessionResultCallback callback, IResultProvider resultProvider){
		this.setWatchdog(watchdog);
		this.setCallback(callback);
		this.setResultProvider(resultProvider);
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
		if(this.callback != null){
			Object result = this.resultProvider.getResult();
			this.callback.onProcessComplete(exitValue, System.currentTimeMillis(), result);
		}
		
		super.onProcessComplete(exitValue);
	}

	@Override
	public void onProcessFailed(ExecuteException e) {
		// TODO Auto-generated method stub
				
		if(this.callback != null) 
		this.callback.onProcessFailed(e, watchdog, System.currentTimeMillis());
		
		super.onProcessFailed(e);
	}
	
	public ExecuteWatchdog getWatchdog() {
		return watchdog;
	}
	

	public SessionResultCallback getCallback() {
		return callback;
	}

	public void setCallback(SessionResultCallback callback) {
		this.callback = callback;
	}

	public long getAbortRequestTimestamp() {
		return abortRequestTimestamp;
	}

	public void setAbortRequestTimestamp(long abortRequestTimestamp) {
		this.abortRequestTimestamp = abortRequestTimestamp;
	}

	public IResultProvider getResultProvider() {
		return resultProvider;
	}

	public void setResultProvider(IResultProvider resultProvider) {
		this.resultProvider = resultProvider;
	}

}