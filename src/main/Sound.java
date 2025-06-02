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
		soundURL[2] = getClass().getResource("/sound/forest.wav");
		soundURL[3] = getClass().getResource("/sound/grass.wav");
		soundURL[4] = getClass().getResource("/sound/mainMenu.wav");
		soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
		soundURL[7] = getClass().getResource("/sound/punch.wav");
		soundURL[8] = getClass().getResource("/sound/cursor.wav");
		soundURL[9] = getClass().getResource("/sound/burning.wav");
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
        if (clip != null) {
            clip.start();
        }
    }
	
	public void stop() {
	    if (clip != null && clip.isRunning()) {
	        clip.stop();
	    }
	}
	
	private boolean looping = false;

	public void loop() {
	    if (clip != null) {
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	        looping = true;
	    }
	}

	public void stopLoop() {
	    if (clip != null && looping) {
	        clip.loop(0);
	        looping = false;
	    }
	}

	public boolean isLooping() {
	    return looping;
	}
	
	public boolean isPlaying() {
	    return clip != null && clip.isRunning();
	}
}
