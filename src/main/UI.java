package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.OBJ_MagaGreen;

public class UI {

	Screen screen;
	Graphics2D g2;
	
	private Font megaten;
	
	private boolean messageOn = false;
	private String message = "";
	private int messageCounter = 0;
	private int messageTimer = 0;
	
	private int dialogueBoxSize = 20;
	private int dialogueBoxSubcolorSize = 5;
	
	public String currentSpeechLine = "";
	public int commandNum = 0;
	
	public UI(Screen screen) {
		this.screen = screen;
		
		InputStream is = getClass().getResourceAsStream("/font/SMT-Devil-Survivor.ttf");
		try {
			megaten = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
			System.out.println("Invalid font format.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Invalid font path.");
		}
	}
	
	public void displayMessage(String message) {
		this.message = message;
		messageOn = true;
		messageCounter++;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(megaten);
		g2.setColor(Color.black);
		
		//TITLE STATE
		if(screen.gameState == screen.titleState) {
			drawTitleScreen();
			
		}
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
	public void drawTitleScreen() {
		
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, screen.screenWidth, screen.screenHeight);
		
		//Título Principal
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
		String text = "Shin Megami Tensei:";
		int x = getCenteredX(text);
		int y = screen.tileSize*2;
		
		//SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		//MAIN COLOR
		g2.setColor (Color.white);
		g2.drawString(text, x, y);
		
		// Sub-titulo
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,70F));
		text = "Persona VI";
		x = getCenteredX(text);
		y = (screen.tileSize*3);
		
		//Sombra
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		//Texto
		g2.setColor (Color.white);
		g2.drawString(text, x, y);
	
		// HITOSHURA IMAGEM
		x = screen.screenWidth/2 - screen.tileSize;
		y += screen.tileSize;
		g2.drawImage(screen.player.down2, x, y, screen.tileSize*2, screen.tileSize*2, null);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getCenteredX (text);
		y += screen.tileSize*3;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			 g2.drawString(".", x-(screen.tileSize/2), y);
			 }
		 
		 
		 text = "LOAD GAME";
		 x = getCenteredX (text);
		 y += screen.tileSize;
		 g2.drawString(text, x, y);
		 if(commandNum == 1) {
			 g2.drawString(".", x-(screen.tileSize/2), y);
				 
			 }
		
		
		 text = "QUIT";
		 x = getCenteredX (text);
		 y += screen.tileSize;
		 g2.drawString(text, x, y);
		 if(commandNum == 2) {
			 g2.drawString(".", x-(screen.tileSize/2), y);
				 
			 }
		
	
	
	}
	private void drawMenu() {
		String text = "PAUSED";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100F));
		int x = getCenteredX(text);
		int y = screen.tileSize*2;
		g2.drawString(text, x, y);
	}
	
	private void drawMessage() {
		int x = screen.tileSize/2;
		int y = screen.screenHeight/2;
		
		int framesPerMessage = 90; // 1 segundo = 60 frames.
		
		if(messageOn==true) {
			g2.setFont(g2.getFont().deriveFont(60F));
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
		int x = screen.tileSize * 3;
		int y = screen.tileSize / 3;
		int width = screen.screenWidth - (screen.tileSize * 6);
		int height = screen.tileSize * 3;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		x += screen.tileSize;
		y += screen.tileSize;
		
		for(String line : currentSpeechLine.split("\n")) {
			g2.drawString(line, x, y);
			y += screen.tileSize/1.5;
		}
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0,0,0,200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, dialogueBoxSize, dialogueBoxSize);
		
		c = new Color(255,0,0);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x+5 , y+5, width-10, height-10, dialogueBoxSize - dialogueBoxSubcolorSize, dialogueBoxSize - dialogueBoxSubcolorSize);
		c = new Color(255,255,255);
		g2.setColor(c);
	}
	
	private int getCenteredX(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (screen.screenWidth/2) - (length/2);
		return x;
	}
}

