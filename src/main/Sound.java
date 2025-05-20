package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
   public static final int sizeURL = 30;
	Clip clip;
	URL soundURL[] = new URL[sizeURL];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/coin.wav");
		soundURL[1] = getClass().getResource("/sound/powerup.wav");	
		soundURL[2] = getClass().getResource("/sound/TrilhaPlaceholder.wav");
	}
	
	public void setFile(int url) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[url]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			System.out.println("Invalid music path.");
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
