package org.red5.misc.examples.ffmpeg.mediainfo;

import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionProcessCallback;

public class SessionDestroyer extends ShutdownHookProcessDestroyer {

	private SessionProcessCallback callback;
	
	public SessionDestroyer(SessionProcessCallback callback){
		this.callback = callback;
	}
	
	
	@Override
	public boolean add(Process process) {
		// TODO Auto-generated method stub
		if(callback != null)
		callback.onProcessAdded(process);
		
		return super.add(process);
	}

	@Override
	public boolean isAddedAsShutdownHook() {
		// TODO Auto-generated method stub
		return super.isAddedAsShutdownHook();
	}

	@Override
	public boolean remove(Process process) {
		// TODO Auto-generated method stub
		if(callback != null)
		callback.onProcessRemoved(process);
		
		return super.remove(process);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

}