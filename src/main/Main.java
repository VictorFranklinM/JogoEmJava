package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Main {
	
	public static JFrame window;
	
	public static void main(String[] args) {
		
		window = new JFrame();
		Screen screen = new Screen();
		
		window.add(screen);
		
		screen.config.loadConfig();
		
		new Main().setIcon();
		
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
	
	public void setIcon() {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("ui/icon.png"));
		window.setIconImage(icon.getImage());
	}
}
