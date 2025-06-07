package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	Screen screen;
	
	public Config(Screen screen) {
		this.screen = screen;
	}
	
	public void saveConfig() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			// Music
			bw.write(String.valueOf(screen.music.volumeScale));
			bw.newLine();
			
			// SFX
			bw.write(String.valueOf(screen.sfx.volumeScale));
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			// Music
			String s = br.readLine();
			screen.music.volumeScale = Integer.parseInt(s);
			
			// SFX
			s = br.readLine();
			screen.sfx.volumeScale = Integer.parseInt(s);
			screen.tsm.updateVolume(screen.sfx);
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
