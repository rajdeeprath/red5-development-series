package org.red5.misc.examples.ffmpeg.mediainfo.interfaces;

public interface SessionProcessCallback {

	public void onProcessAdded(Process proc);

	public void onProcessRemoved(Process proc);
}