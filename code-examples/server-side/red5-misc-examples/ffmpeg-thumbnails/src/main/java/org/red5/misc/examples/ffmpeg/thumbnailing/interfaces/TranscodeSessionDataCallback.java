package org.red5.misc.examples.ffmpeg.thumbnailing.interfaces;


public interface TranscodeSessionDataCallback {

	public void onTranscodeProcessStart(long timestamp);
	public void onTranscodeProcessData(Object data, long timestamp);
}