package main;

import java.awt.BasicStroke;
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
	private int dialogueBoxSize = 20;
	private int dialogueBoxSubcolorSize = 5;
	public String currentSpeechLine = "";
	
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
		if(screen.gameState==screen.dialogueState) {
    		drawDialogueScreen();
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
	
	public void drawDialogueScreen() {
		int x = screen.tileSize * 2;
		int y = screen.tileSize / 2;
		int width = screen.screenWidth - (screen.tileSize * 4);
		int height = screen.tileSize * 5;
		drawSubWindow(x, y, width, height);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
		x += screen.tileSize;
		y += screen.tileSize;
		g2.drawString(currentSpeechLine, x, y);
	}

	public void drawSubWindow(int x,int y,int width,int height) {
		Color c = new Color(0,0,0,250);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height,dialogueBoxSize,dialogueBoxSize);
		c = new Color(255,0,0);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x+5 , y+5, width-10, height-10, dialogueBoxSize - dialogueBoxSubcolorSize, dialogueBoxSize - dialogueBoxSubcolorSize);
		c = new Color(255,255,255);
		g2.setColor(c);
	}
	
	private int getCenteredX(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (screen.screenWidth/2) - (length);
		return x;
	}
}

