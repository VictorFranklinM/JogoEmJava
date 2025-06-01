package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import entity.Entity;
import object.OBJ_HP;
import object.OBJ_Mana;

public class UI {
	Color brightGreen = new Color(185, 219, 149);
	Color greenGreen = new Color(64, 152, 94);
	Color darkGreen = new Color(26, 100, 78);
	Color darkerGreen = new Color(4, 55, 59);
	Color blackGreen = new Color(10, 26, 47);
	
	Screen screen;
	Graphics2D g2;
	
	private Font megaten;
	
	BufferedImage titleBG, hpFull, hpNone, manaFull, manaNone, face;
	
	private int dialogueBoxSize = 20;
	private int dialogueBoxSubcolorSize = 5;
	
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	
	public String currentSpeechLine = "";
	public int commandNum = 0;
	
	public int slotCol = 0;
	public int slotRow = 0;
	
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
		
		// Render HUD
		Entity hp = new OBJ_HP(screen);
		Entity mana = new OBJ_Mana(screen);
		
		hpFull = hp.image;
		hpNone = hp.image2;
		manaFull = mana.image;
		manaNone = mana.image2;
	}
	
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
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
			drawPlayerUI();
			drawMessage();
			screen.ui.setFace(null);
		}
		// PAUSE STATE
		if(screen.gameState == screen.pauseState) {
			drawPlayerUI();
			drawMenu();
		}
		//Dialogue State
		if(screen.gameState==screen.dialogueState) {
			screen.tsm.stopTileSound();
    		drawDialogueScreen();
    	}
		//Status State
		if(screen.gameState == screen.statusState) {
			screen.tsm.stopTileSound();
			drawStatusScreen();
			drawInventory();
		}
	}

	public void drawMessage() {
		int messageX = screen.tileSize/2 + screen.scale;
		int messageY = screen.screenHeight/2 - screen.screenHeight/6;
		
		for (int i = 0; i < message.size(); i++) {
		    if (message.get(i) != null) {

		        String text = message.get(i);
		        int x = messageX;
		        int y = messageY;

		        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

		        // Border
		        g2.setColor(blackGreen);
		        g2.drawString(text, x-2, y);
		        g2.drawString(text, x+2, y);
		        g2.drawString(text, x, y-2);
		        g2.drawString(text, x, y+2);

		        // Text
		        g2.setColor(brightGreen);
		        g2.drawString(text, x, y);

		        int counter = messageCounter.get(i) + 1;
		        messageCounter.set(i, counter);
		        messageY += 50;

		        if (counter > 120) {
		            message.remove(i);
		            messageCounter.remove(i);
		        }
		    }
		}
	}

	public void drawPlayerUI() {
		int x = screen.tileSize;
		int y = screen.tileSize/2;
		int count = 0;
		
		// Renderizar HP MAX.
		while(count < screen.player.maxHP) {
			g2.drawImage(hpNone, x, y, null);
			count++;
			x += (8*screen.scale);
		}
		
		// Reset
		x = screen.tileSize;
		y = screen.tileSize/2;
		count = 0;
		
		// Renderizar HP atual.
		while(count < screen.player.hp) {
			g2.drawImage(hpFull, x, y, null);
			count++;
			x += (8*screen.scale);
		}
		
		// Renderizar Mana.
		x = screen.tileSize/2 + screen.scale;
		y = (int) (screen.tileSize*1.5) - screen.scale;
		count = 0;
		while(count <screen.player.maxMana) {
			g2.drawImage(manaNone, x, y, null);
			count++;
			x += (8*screen.scale);
		}
		
		x = screen.tileSize/2 + screen.scale;
		y = (int) (screen.tileSize*1.5) - screen.scale;
		count = 0;
		
		// Renderizar Mana atual.
		while(count < screen.player.mana) {
			g2.drawImage(manaFull, x, y, null);
			count++;
			x += (8*screen.scale);
		}
	}
	
	public void drawTitleScreen() {
		// Title BG
		try {
			titleBG = ImageIO.read(getClass().getResourceAsStream("/ui/titleBG.png"));	
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Invalid image path.");
			}
		g2.drawImage(titleBG, 0, 0, screen.screenWidth, screen.screenHeight, null);
		
		//Título Principal
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
		String text = "Shin Megami Tensei:";
		int x = getCenteredX(text);
		int y = screen.tileSize*2;
		
		//SHADOW
		g2.setColor(greenGreen);
		g2.drawString(text, x+5, y+5);
		
		//MAIN COLOR
		g2.setColor(brightGreen);
		g2.drawString(text, x, y);
		
		// Sub-titulo
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,70F));
		text = "Persona VI";
		x = getCenteredX(text);
		y = (screen.tileSize*3);
		
		//Sombra
		g2.setColor(greenGreen);
		g2.drawString(text, x+5, y+5);
		
		//Texto
		g2.setColor(brightGreen);
		g2.drawString(text, x, y);
		
		//MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getCenteredX (text);
		y += screen.tileSize*4;
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
	
	public void drawDialogueScreen() {
		int x = screen.tileSize * 3;
		int y = screen.tileSize / 3;
		int width = screen.screenWidth - (screen.tileSize * 6);
		int height = screen.tileSize * 3;
		drawSubWindow(x, y, width, height);
		
		if (face != null) {
			int faceSize = (int) (screen.tileSize*1.2);
		    int faceX = (int) (x + width - faceSize*1.22);

		    drawSubWindow(faceX, y, faceSize+16, faceSize+16, 0);

		    // desenha a imagem do rosto
		    g2.drawImage(face, faceX+8, y+8, faceSize, faceSize, null);
		}


		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		x += screen.tileSize;
		y += screen.tileSize;
		
		for(String line : currentSpeechLine.split("\n")) {
			g2.drawString(line, x, y);
			y += screen.tileSize/1.5;
		}
	}
	
	public void drawStatusScreen() {
		final int frameX = screen.tileSize;
		final int frameY = screen.tileSize;
		final int frameWidth = screen.tileSize*7;
		final int frameHeight = screen.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		g2.setColor(brightGreen);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + screen.tileSize/3;
		int textY = frameY + screen.tileSize;
		final int lineHeight = (int) (screen.tileSize/1.2);
		
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Hp", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strenght", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next level", textX, textY);
		textY += lineHeight;
		g2.drawString("Macca", textX, textY);
		textY += lineHeight;
		g2.drawString("Magatama", textX, textY);
		
		int tailX = (frameX + frameWidth) - screen.tileSize/2;
		textY = frameY + screen.tileSize;
		String value;
		
		value = String.valueOf(screen.player.level);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.hp+"/"+screen.player.maxHP);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.mana+"/"+screen.player.maxMana);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.strenght);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.dexterity);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.attack);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.defense);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.exp);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.nextLevelExp - screen.player.exp);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(screen.player.macca);
		textX = getAlignToRightX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		if(screen.player.currentMagatama != null) {
			g2.drawImage(screen.player.currentMagatama.down1, (int) (tailX - screen.tileSize/1.5), (int) (textY - screen.tileSize/1.5), null);
		}
		
	}

	public void drawInventory() {
		// Frame
		final int frameX = screen.screenWidth - screen.tileSize*8;
		final int frameY = screen.tileSize;
		final int frameWidth = (int) (screen.tileSize*6.7);
		final int frameHeight = (int) (screen.tileSize*5.6);
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Slot
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		
		for(int i = 0; i < screen.player.inventorySize; i++) {
			g2.setColor(darkGreen);
			g2.drawRoundRect(slotX, slotY, screen.tileSize, screen.tileSize, 9, 9);
			slotX += screen.tileSize;
			if(i == 5 || i == 11 || i == 17 || i == 23) {
				slotX = slotXStart;
				slotY += screen.tileSize;
			}
		}
		
		slotX = slotXStart;
		slotY = slotYStart;
		
		// Draw Itens
		for(int i = 0; i < screen.player.inventory.size(); i++) {
			g2.drawImage(screen.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += screen.tileSize;
			if(i == 5 || i == 11 || i == 17 || i == 23) {
				slotX = slotXStart;
				slotY += screen.tileSize;
			}
		}
		
		// Draw Cursor
		int cursorX = slotXStart + (screen.tileSize * slotCol);
		int cursorY = slotYStart + (screen.tileSize * slotRow);
		int cursorWidth = screen.tileSize;
		int cursorHeight = screen.tileSize;
		
		g2.setColor(brightGreen);
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// Description Frame
		int dFrameX = frameX;
		int dFrameY = screen.tileSize+frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = screen.tileSize*10 - frameHeight;
		drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
		
		// Draw Description Text
		int textX = dFrameX+20;
		int textY = (int) (dFrameY+screen.tileSize*0.75);	
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot();
		
		if(itemIndex < screen.player.inventory.size()) {
			
			for(String line: screen.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 36;
			}
		}
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*6);
		return itemIndex;
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		drawSubWindow(x, y, width, height, 200);
	}
	
	public void drawSubWindow(int x, int y, int width, int height, int opacity) {
		Color hudBG = new Color(0,0,0,opacity);
		g2.setColor(hudBG);
		g2.fillRoundRect(x, y, width, height, dialogueBoxSize, dialogueBoxSize);
		
		g2.setColor(greenGreen);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x+5 , y+5, width-10, height-10, dialogueBoxSize - dialogueBoxSubcolorSize, dialogueBoxSize - dialogueBoxSubcolorSize);
		g2.setColor(brightGreen);
	}
	
	private int getCenteredX(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (screen.screenWidth/2) - (length/2);
		return x;
	}
	
	private int getAlignToRightX(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	
	public BufferedImage setFace(BufferedImage face) {
		this.face = face;
		return face;
	}
}

