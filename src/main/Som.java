package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Som {
   public static final int tamanhoUrl = 30;
	Clip clip;
	URL urlsom[] = new URL[tamanhoUrl];
	
	public Som() {
		urlsom[0] = getClass().getResource("/sound/coin.wav");
		urlsom[1] = getClass().getResource("/sound/powerup.wav");	
		urlsom[2] = getClass().getResource("/sound/TrilhaPlaceholder.wav");
	}
	
	public void setFile(int urlescolhido) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(urlsom[urlescolhido]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception urlinvalido){
			System.out.println("Erro ao Importar Som, url inválido ou inexistente");
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
