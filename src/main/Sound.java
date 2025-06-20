package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
   public static final int sizeURL = 30;
	Clip clip;
	URL soundURL[] = new URL[sizeURL];
	FloatControl fc;
	int volumeScale = 3;
	float volume;

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
		soundURL[10] = getClass().getResource("/sound/gameOver.wav");
		soundURL[11] = getClass().getResource("/sound/stairs.wav");
		soundURL[12] = getClass().getResource("/sound/blocked.wav");
		soundURL[13] = getClass().getResource("/sound/parry.wav");
		soundURL[14] = getClass().getResource("/sound/speech.wav");
		soundURL[15] = getClass().getResource("/sound/dooropen.wav");
		soundURL[16] = getClass().getResource("/sound/bossBattle.wav");
	}
	
	public void setFile(int url) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[url]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
			
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
	public void checkVolume() {
		
		switch (volumeScale) {
		case 0: volume = -80F; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f;break;
		case 3: volume = -5F; break;
		case 4: volume = 1f; break;
		case 5: volume = 6F;  break;
		}
		fc.setValue(volume);
	}
}