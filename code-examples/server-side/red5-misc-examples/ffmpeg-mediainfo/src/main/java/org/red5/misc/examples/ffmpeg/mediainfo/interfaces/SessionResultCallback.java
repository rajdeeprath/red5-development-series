package org.red5.misc.examples.ffmpeg.mediainfo.interfaces;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

public interface SessionResultCallback {

	public void onProcessComplete(int exitValue, long timestamp, Object data);

	public void onProcessFailed(ExecuteException e, ExecuteWatchdog watchdog, long timestamp);
}