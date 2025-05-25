package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_MagaGreen;

public class UI {

	Screen screen;
	Graphics2D g2;
	
	private Font arial_40;
	
	private boolean messageOn = false;
	private String message = "";
	private int messageCounter = 0;
	private int messageTimer = 0;
	
	public UI(Screen screen) {
		this.screen = screen;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
	}
	
	public void displayMessage(String message) {
		this.message = message;
		messageOn = true;
		messageCounter++;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.black);
		
		// PLAY STATE
		if(screen.gameState == screen.playState) {
			drawMessage();
		}
		// PAUSE STATE
		if(screen.gameState == screen.pauseState) {
			drawMenu();
		}
	}
	
	private void drawMenu() {
		String text = "PAUSED";
		int x = getCenteredX(text);
		int y = (int) (screen.tileSize*1.5);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		g2.drawString(text, x, y);
	}
	
	private void drawMessage() {
		int x = screen.tileSize/2;
		int y = screen.screenHeight/2;
		
		int framesPerMessage = 90; // 1 segundo = 60 frames.
		
		if(messageOn==true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, x, y);
			
			if(messageCounter > 1) {
				messageTimer = 0;
				messageCounter = 1;
			}
			
			messageTimer++;
			
			if(messageTimer > framesPerMessage) {
				messageTimer = 0;
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
	private int getCenteredX(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (screen.screenWidth/2) - (length);
		return x;
	}
}

