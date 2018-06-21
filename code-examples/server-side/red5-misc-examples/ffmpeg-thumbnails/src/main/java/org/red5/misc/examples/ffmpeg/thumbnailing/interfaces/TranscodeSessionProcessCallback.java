package org.red5.misc.examples.ffmpeg.thumbnailing.interfaces;

public interface TranscodeSessionProcessCallback {

	public void onTranscodeProcessAdded(Process proc);
	public void onTranscodeProcessRemoved(Process proc);
}