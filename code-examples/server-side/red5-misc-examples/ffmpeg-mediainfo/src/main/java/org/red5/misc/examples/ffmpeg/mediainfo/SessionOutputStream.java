package org.red5.misc.examples.ffmpeg.mediainfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.exec.LogOutputStream;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.IResultProvider;
import org.red5.misc.examples.ffmpeg.mediainfo.interfaces.SessionDataCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SessionOutputStream extends LogOutputStream implements IResultProvider {

	private static Logger logger = LoggerFactory.getLogger(SessionOutputStream.class);
	private SessionDataCallback callback;
	private static final int QUEUE_SIZE = 10;
	private final Queue<String> lines = new LinkedList<String>();
	private long lastOutputTime = 0;
	
	private MediaInfo resultObject;
	
	
	public MediaInfo getResultObject() {
		return resultObject;
	}

	public long getLastOutputTime() {
		return lastOutputTime;
	}

	public void setLastOutputTime(long lastOutputTime) {
		this.lastOutputTime = lastOutputTime;
	}

	public SessionOutputStream(){
		
	}
	
	public SessionOutputStream(SessionDataCallback callback){
		this.callback = callback;
	}
	
	@Override
	protected void processLine(String line, int level) {
		
		logger.info(line);
		
		lines.add(line);
		lastOutputTime = System.currentTimeMillis();
		
		if(this.callback != null){
		if(lines.size() == 1) {
			this.callback.onProcessStart(lastOutputTime);
		}
		
		processforMediaInfo(line);
		
		this.callback.onProcessData(line, lastOutputTime);
		}
	}
	
	
	
	private void processforMediaInfo(String line) {
		
		if(this.resultObject == null){
			this.resultObject = new MediaInfo();
		}
		
		Scanner scanner = new Scanner(line);
		
		if(line.contains("creation_time"))
		{
			// Find duration
	        Pattern creationTimePattern = Pattern.compile("(?<=creation_time   : )[^,]*");
	        String creationTime = scanner.findWithinHorizon(creationTimePattern, 0);
	        
	        if (creationTime == null)
	          throw new RuntimeException("Could not parse creation time.");
	        
	        //2013-07-26T18:36:04.000000Z
	        SimpleDateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	        try {
	        	Date date = dfmt.parse(creationTime);
				this.resultObject.setCreationTime(date);
			} catch (ParseException e) {
				throw new RuntimeException("Could not parse creation time string.");
			}
	        logger.debug("Creation time: {}", creationTime);
	        return;
		}
		
		if(line.contains("Duration"))
		{
			// Find duration
	        Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
	        String dur = scanner.findWithinHorizon(durPattern, 0);
	        if (dur == null)
	          throw new RuntimeException("Could not parse duration.");
	        String[] hms = dur.split(":");
	        double totalSecs = Integer.parseInt(hms[0]) * 3600
	                         + Integer.parseInt(hms[1]) *   60
	                         + Double.parseDouble(hms[2]);
	        
	        this.resultObject.setDuration(totalSecs);
	        logger.debug("Total duration: {} seconds.", totalSecs);
		}
		
		/*
		if(line.contains("bitrate"))
		{
			Pattern bitratePattern = Pattern.compile("(?<=bitrate: )[^,]*");
	        String bitrate = scanner.findWithinHorizon(bitratePattern, 0);
	        if (bitratePattern == null)
	          throw new RuntimeException("Could not parse bitrate.");
	        System.out.println("bitrate : " + bitrate + "");
		}
		*/

		
		if(line.contains("Stream #0"))
		{
			if(line.contains("Video:"))
			{
				Pattern vCodecPattern = Pattern.compile("(?<=Video: )[^,]*");
		        String vcodec = scanner.findWithinHorizon(vCodecPattern, 0);
		        if (vCodecPattern == null)
		          throw new RuntimeException("Could not parse video info.");
		        System.out.println("Video codec : " + vcodec + "");
		        
		        String[] vParts = line.split(",");
		        String cod = vParts[0].trim();
		        String fmt = vParts[1].trim();
		        String res = vParts[2].trim();
		        String bit = vParts[3].trim();
		        String fps = vParts[4].trim();
		        
		        String[] resParts =  res.split("x");
		        int width = Integer.parseInt(resParts[0]);
		        int height = Integer.parseInt(resParts[1]);
		        
		        this.resultObject.setVideoCodec(cod);
		        this.resultObject.setVideoWidth(width);
		        this.resultObject.setVideoHeight(height);
		        this.resultObject.setVideoBitrate(Integer.parseInt(bit.trim().split(" ")[0]));
		        this.resultObject.setFps(Float.parseFloat(fps.trim().split(" ")[0]));
		        
		        
		        logger.debug("fmt={}, res={}, bit={}, fps={}, width={}, height={}", fmt, res, bit, fps, width, height);
			}
			
			if(line.contains("Audio:"))
			{
				Pattern aCodecPattern = Pattern.compile("(?<=Audio: )[^,]*");
		        String aCodec = scanner.findWithinHorizon(aCodecPattern, 0);
		        if (aCodecPattern == null)
		          throw new RuntimeException("Could not parse audio info.");
		        System.out.println("Audio codec : " + aCodec + "");
		        
		        String[] aParts = line.split(",");
		        String cod = aParts[0].trim();
		        String sample = aParts[1].trim();
		        String channels = aParts[2].trim();
		        String bit = aParts[4].trim();
		        
		        this.resultObject.setAudioCodec(cod);
		        this.resultObject.setSampleRate(Integer.parseInt(sample.trim().split(" ")[0]));
		        this.resultObject.setAudioBitrate(Integer.parseInt(bit.trim().split(" ")[0]));
		        this.resultObject.setChannels((channels.trim().split(" ")[0]));
		        
		        logger.info("sample={}, channels={}, bit={}", sample, channels, bit);
			}
		}
		
		scanner = null;
		
	}

	/* Estimates if process is running by checking if there was output in last 5 seconds */
	public boolean isRunning()
	{
		long diff = System.currentTimeMillis() - lastOutputTime;
        long diffSeconds = diff / 1000 % 60;
        return (diffSeconds>5)?false:true;
	}
	
	@Override
	public void close() throws IOException {
		logger.info("closing output stream");
		lines.clear();
		super.close();
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		super.flush();
	}

	@Override
	public int getMessageLevel() {
		// TODO Auto-generated method stub
		return super.getMessageLevel();
	}

	@Override
	protected void processBuffer() {
		// TODO Auto-generated method stub
		super.processBuffer();
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return resultObject;
	}

}