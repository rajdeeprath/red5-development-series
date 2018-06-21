package org.red5.misc.examples.ffmpeg.thumbnailing;

import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.red5.misc.examples.ffmpeg.thumbnailing.interfaces.TranscodeSessionProcessCallback;

public class TranscodeSessionDestroyer extends ShutdownHookProcessDestroyer {

	private TranscodeSessionProcessCallback callback;
	
	public TranscodeSessionDestroyer(TranscodeSessionProcessCallback callback){
		this.callback = callback;
	}
	
	
	@Override
	public boolean add(Process process) {
		// TODO Auto-generated method stub
		if(callback != null)
		callback.onTranscodeProcessAdded(process);
		
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
		callback.onTranscodeProcessRemoved(process);
		
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