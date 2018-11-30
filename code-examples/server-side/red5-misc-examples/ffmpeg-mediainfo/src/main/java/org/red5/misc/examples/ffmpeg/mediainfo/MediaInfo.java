package org.red5.misc.examples.ffmpeg.mediainfo;

import java.util.Date;

public class MediaInfo {
	
	String name;
	
	String type;
	
	Date creationTime;
	
	double duration;
	
	int videoBitrate;
	
	int audioBitrate;
	
	String videoCodec;
	
	String audioCodec;
	
	int sampleRate;
	
	String channels;
	
	int videoWidth;
	
	int videoHeight;
	
	float fps;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getVideoBitrate() {
		return videoBitrate;
	}

	public void setVideoBitrate(int videoBitrate) {
		this.videoBitrate = videoBitrate;
	}

	public int getAudioBitrate() {
		return audioBitrate;
	}

	public void setAudioBitrate(int audioBitrate) {
		this.audioBitrate = audioBitrate;
	}

	public String getVideoCodec() {
		return videoCodec;
	}

	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}

	public String getAudioCodec() {
		return audioCodec;
	}

	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public int getVideoWidth() {
		return videoWidth;
	}

	public void setVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
	}

	public int getVideoHeight() {
		return videoHeight;
	}

	public void setVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
	}

	public float getFps() {
		return fps;
	}

	public void setFps(float fps) {
		this.fps = fps;
	}

	@Override
	public String toString() {
		return "MediaInfo [name=" + name + ", type=" + type + ", creationTime=" + creationTime + ", duration="
				+ duration + ", videoBitrate=" + videoBitrate + ", audioBitrate=" + audioBitrate + ", videoCodec="
				+ videoCodec + ", audioCodec=" + audioCodec + ", sampleRate=" + sampleRate + ", channels=" + channels
				+ ", videoWidth=" + videoWidth + ", videoHeight=" + videoHeight + ", fps=" + fps + "]";
	}

	
	

}
