package org.red5.misc.examples.ffmpeg.thumbnailing.interfaces;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

public interface TranscodeSessionResultCallback {

	public void onTranscodeProcessComplete(int exitValue, long timestamp);
	public void onTranscodeProcessFailed(ExecuteException e, ExecuteWatchdog watchdog, long timestamp);
}