package main;

import javax.swing.JFrame;
public class Main {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		Screen screen = new Screen();
		
		window.add(screen);
		
		screen.config.loadConfig();
		
		window.setTitle("Shin Megami Tensei: Persona VI");
		window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
		
		screen.setupGame();
	
		screen.startGameThread();
		
	}
}
