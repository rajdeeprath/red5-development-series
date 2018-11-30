package org.red5.misc.examples.ffmpeg.mediainfo;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InfoGrabber extractor  = new InfoGrabber("J:\\ffmpeg\\bin\\ffprobe.exe", "J:\\media\\20051210-w50s.flv", "-hide_banner", "J:\\media");
		extractor.run();
	}

}
