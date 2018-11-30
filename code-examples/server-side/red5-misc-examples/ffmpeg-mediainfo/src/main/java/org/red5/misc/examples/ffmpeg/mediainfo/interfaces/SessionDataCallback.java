package org.red5.misc.examples.ffmpeg.mediainfo.interfaces;

public interface SessionDataCallback {

	public void onProcessStart(long timestamp);

	public void onProcessData(Object data, long timestamp);
}