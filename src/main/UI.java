package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_MagaGreen;

public class UI {

	Screen screen;
	private Font arial_40;
	private BufferedImage magaIMG;
	private boolean messageOn = false;
	private String message = "";
	private int messageCounter = 0;
	private int messageTimer = 0;
	
	public UI(Screen screen) {
		this.screen = screen;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		OBJ_MagaGreen maga = new OBJ_MagaGreen(screen);
		magaIMG = maga.image;
	}
	
	public void displayMessage(String message) {
		this.message = message;
		messageOn = true;
		messageCounter++;
	}
	
	public void drawn(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.black);
		g2.drawImage(magaIMG, screen.tileSize/2, screen.tileSize/2, screen.tileSize, screen.tileSize, null);
		g2.drawString("x "+ screen.player.hasMaga, (int) (screen.tileSize*1.5), (int) (screen.tileSize*1.2));
		
		// Message
		if(messageOn==true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, screen.tileSize/8, screen.screenHeight/3);
			
			if(messageCounter > 1) {
				messageTimer = 0;
				messageCounter = 1;
			}
			
			messageTimer++;
			
			if(messageTimer > 90) {
				messageTimer = 0;
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
}
