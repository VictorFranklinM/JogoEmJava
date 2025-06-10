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
	
	public Entity npc;
	
	public String currentSpeechLine = "";
	public int commandNum = 0;
	
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	
	int subState = 0;
	int counter = 0;
	
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
		if(screen.gameState == Screen.titleState) {
			screen.tsm.stopTileSound();
			drawTitleScreen();
			
		}
		// PLAY STATE
		if(screen.gameState == Screen.playState) {
			drawPlayerUI();
			drawMessage();
			screen.ui.setFace(null);
		}
		//Dialogue State
		if(screen.gameState==Screen.dialogueState) {
			screen.tsm.stopTileSound();
    		drawDialogueScreen();
    	}
		//Status State
		if(screen.gameState == Screen.statusState) {
			screen.tsm.stopTileSound();
			drawStatusScreen();
			drawInventory(screen.player, true);
		}
		//OPTIONS STATE
		if(screen.gameState == Screen.optionsState) {
			screen.tsm.stopTileSound();
			drawOptionsScreen();
			
		}
		//GAME OVER
		if(screen.gameState == Screen.gameOverState) {
			screen.tsm.stopTileSound();
			drawGameOverScreen();
					
		}
		//TRANSITION
		if(screen.gameState == Screen.transitionState) {
			screen.tsm.stopTileSound();
			drawTransition();
							
		}
		//TRADE
		if(screen.gameState == Screen.tradeState) {
			screen.tsm.stopTileSound();
			drawTradeScreen();
									
		}
		
	}
	
	public void drawTradeScreen() {
		switch(subState) {
		case 0: tradeSelect(); break;
		case 1: tradeBuy(); break;
		case 2: tradeSell(); break;
		}
		screen.key.ePressed = false;
	}
	
	public void tradeSelect() {
		drawDialogueScreen();
		
		int x = (int) (Screen.tileSize*15.25);
		int y = (int) (Screen.tileSize*3.3);
		int width = Screen.tileSize*3;
		int height = (int) (Screen.tileSize * 3.5);
		drawSubWindow(x, y, width, height);
		
		x += Screen.tileSize;
		y += Screen.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(screen.key.ePressed) {
				subState = 1;
			}
		}
		y += Screen.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(screen.key.ePressed) {
				subState = 2;
			}
		}
		x -= Screen.tileSize/3;
		y += Screen.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(screen.key.ePressed) {
				commandNum = 0;
				screen.gameState = Screen.dialogueState;
				currentSpeechLine = "Come again!";
			}
		}
	}
	
	public void tradeBuy() {
		drawInventory(screen.player, false);
		drawInventory(npc, true);

		int x = Screen.screenWidth - Screen.tileSize * 8;
		int y = (int)(Screen.tileSize * 6.6);
		int width = (int)(Screen.tileSize * 6.7);
		int height = (int)((Screen.tileSize * 4.4)/2);

		drawSubWindow(x, y, width/2, height);
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			g2.drawString("Price: " + npc.inventory.get(itemIndex).price, x + 24, y + 80);
		}
		else {
			g2.drawString("Price: ", x + 24, y + 80); // ou pode deixar em branco
		}

		x += width/2;
		drawSubWindow(x, y, width/2, height);
		g2.drawString("Macca: " + screen.player.macca, x + 24, y + 80);

		x -= width/2;
		y += height;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ENTER] Go Back", x + 24, y + 80);
		
		if (screen.key.ePressed) {
			if (itemIndex < npc.inventory.size()) {
				if(npc.inventory.get(itemIndex).price > screen.player.macca) {
					subState = 0;
					screen.gameState = Screen.dialogueState;
					currentSpeechLine = "Oh noo!\nGuess you're too poor to buy that -Ho!";
					drawDialogueScreen();
				}
				else {
					if(screen.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						screen.player.macca -= npc.inventory.get(itemIndex).price;
					}
					else {
						subState = 0;
						screen.gameState = Screen.dialogueState;
						currentSpeechLine = "You can't carry any more!";
					}
				}
			}	
				
			}
		}


	
	public void tradeSell() {
		drawInventory(screen.player, true);
		
		int x = Screen.tileSize * 2;
		int y = Screen.tileSize;
		int width = (int)(Screen.tileSize * 6.7);
		int height = (int)(Screen.tileSize * 5.6);

		drawSubWindow(x, y, width / 2, height);
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < screen.player.inventory.size()) {
			g2.drawString("Sell: " + screen.player.inventory.get(itemIndex).value, x + 24, y + 80);
		} else {
			g2.drawString("Sell: ", x + 24, y + 80);
		}

		x += width / 2;
		drawSubWindow(x, y, width / 2, height);
		g2.drawString("Macca: " + screen.player.macca, x + 24, y + 80);

		x -= width / 2;
		y += height;
		height = (int)(Screen.tileSize * 4.4);
		drawSubWindow(x, y, width, height);
		g2.drawString("[ENTER] Go Back", x + 24, y + 80);

		if (screen.key.ePressed) {
			if (itemIndex < screen.player.inventory.size()) {
				if (screen.player.inventory.get(itemIndex) == screen.player.currentMagatama ||
						screen.player.inventory.get(itemIndex).name.contains("Magatama")) {

					commandNum = 0;
					subState = 0;
					screen.gameState = Screen.dialogueState;
					currentSpeechLine = "You probably shouldn't go around selling\nsacred stuff...";
				} else {
					if(screen.player.inventory.get(itemIndex).amount > 1) {
						screen.player.inventory.get(itemIndex).amount--;
					}
					else {
						screen.player.inventory.remove(itemIndex);
					}
					screen.playSFX(0);
					
					screen.player.macca += screen.player.inventory.get(itemIndex).value;
					commandNum = 0;
				}
			}
		}
	}

	
	public void drawTransition() {
		int transitionTime = 40;
		counter++;
		g2.setColor(new Color(0, 0, 0, (255/transitionTime)*counter));
		g2.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);
		
		if(counter == transitionTime) {
			screen.gameState = Screen.playState;
			Screen.currentMap = screen.eventManager.tempMap;
			screen.player.worldX = screen.eventManager.tempCol*Screen.tileSize;
			screen.player.worldY = screen.eventManager.tempRow*Screen.tileSize;
			screen.eventManager.lastEventX = screen.player.worldX;
			screen.eventManager.lastEventY = screen.player.worldY;
		}
		while(screen.gameState == Screen.playState && counter != 0) {
			counter--;
			g2.setColor(new Color(0, 0, 0, (255/transitionTime)*counter));
			g2.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
		
		text = "You Died";
		
		// Shadow
		g2.setColor(greenGreen);
		x = getCenteredX(text);
		y = Screen.tileSize*2;
		g2.drawString(text, x, y);
		
		// Text
		g2.setColor(brightGreen);
		g2.drawString(text, x-4, y-4);
		
		y += Screen.tileSize*1.5;
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		g2.setColor(greenGreen);
		text = "You, who has fallen before fulfilling your destiny...\nBeyond lies the land to which all souls eventually return...\nWhere the souls of the dead await their next reincarnation...\nDo not be afraid...";
		for(String line : text.split("\n")) {
			g2.drawString(line, getCenteredX(line), y);
			y += Screen.tileSize/1.5;
		}
		
		y = Screen.tileSize*2;
		y += Screen.tileSize*1.5;
		g2.setColor(brightGreen);
		for(String line : text.split("\n")) {
			g2.drawString(line, getCenteredX(line)-2, y-2);
			y += Screen.tileSize/1.5;
		}
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
		g2.setColor(greenGreen);
		text = "Reincarnate";
		x = getCenteredX(text);
		y += Screen.tileSize*2;
		g2.drawString(text, x, y);
		
		g2.setColor(brightGreen);
		g2.drawString(text, x-2, y-2);
		
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		// Exit
		g2.setColor(greenGreen);
		text = "Give Up";
		x = getCenteredX(text);
		y += Screen.tileSize;
		g2.drawString(text, x, y);
		
		g2.setColor(brightGreen);
		g2.drawString(text, x-2, y-2);
		
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	
	public void drawMessage() {
		int messageX = Screen.tileSize/2 + Screen.scale;
		int messageY = Screen.screenHeight/2 - Screen.screenHeight/6;
		
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
		int x = Screen.tileSize;
		int y = Screen.tileSize/2;
		int count = 0;
		
		// Renderizar HP MAX.
		while(count < screen.player.maxHP) {
			g2.drawImage(hpNone, x, y, null);
			count++;
			x += (8*Screen.scale);
		}
		
		// Reset
		x = Screen.tileSize;
		y = Screen.tileSize/2;
		count = 0;
		
		// Renderizar HP atual.
		while(count < screen.player.hp) {
			g2.drawImage(hpFull, x, y, null);
			count++;
			x += (8*Screen.scale);
		}
		
		// Renderizar Mana.
		x = Screen.tileSize/2 + Screen.scale;
		y = (int) (Screen.tileSize*1.5) - Screen.scale;
		count = 0;
		while(count <screen.player.maxMana) {
			g2.drawImage(manaNone, x, y, null);
			count++;
			x += (8*Screen.scale);
		}
		
		x = Screen.tileSize/2 + Screen.scale;
		y = (int) (Screen.tileSize*1.5) - Screen.scale;
		count = 0;
		
		// Renderizar Mana atual.
		while(count < screen.player.mana) {
			g2.drawImage(manaFull, x, y, null);
			count++;
			x += (8*Screen.scale);
		}
		if(screen.player.currentMagatama != null) {
			x = Screen.tileSize/2 + Screen.scale;
			y = (int) (Screen.tileSize*2.5);
			drawSubWindow(x-1, y-1, Screen.tileSize+2, Screen.tileSize+2);
			g2.drawImage(screen.player.currentMagatama.down1, x, y, null);
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
		g2.drawImage(titleBG, 0, 0, Screen.screenWidth, Screen.screenHeight, null);
		
		//Título Principal
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
		String text = "Shin Megami Tensei:";
		int x = getCenteredX(text);
		int y = Screen.tileSize*2;
		
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
		y = (Screen.tileSize*3);
		
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
		y += Screen.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			 g2.drawString(">", x-(Screen.tileSize/2), y);
			 }
		 
		 
		 text = "LOAD GAME";
		 x = getCenteredX (text);
		 y += Screen.tileSize;
		 g2.drawString(text, x, y);
		 if(commandNum == 1) {
			 g2.drawString(">", x-(Screen.tileSize/2), y);
				 
			 }
		
		
		 text = "QUIT";
		 x = getCenteredX (text);
		 y += Screen.tileSize;
		 g2.drawString(text, x, y);
		 if(commandNum == 2) {
			 g2.drawString(">", x-(Screen.tileSize/2), y);
				 
			 }

	}
	
	public void drawDialogueScreen() {
		int x = Screen.tileSize * 3;
		int y = Screen.tileSize / 3;
		int width = Screen.screenWidth - (Screen.tileSize * 6);
		int height = Screen.tileSize * 3;
		drawSubWindow(x, y, width, height);
		
		if (face != null) {
			int faceSize = (int) (Screen.tileSize*1.2);
		    int faceX = (int) (x + width - faceSize*1.22);

		    drawSubWindow(faceX, y, faceSize+16, faceSize+16, 0);

		    // desenha a imagem do rosto
		    g2.drawImage(face, faceX+8, y+8, faceSize, faceSize, null);
		}


		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		x += Screen.tileSize;
		y += Screen.tileSize;
		
		for(String line : currentSpeechLine.split("\n")) {
			g2.drawString(line, x, y);
			y += Screen.tileSize/1.5;
		}
	}
	
	public void drawStatusScreen() {
		final int frameX = Screen.tileSize;
		final int frameY = Screen.tileSize;
		final int frameWidth = Screen.tileSize*7;
		final int frameHeight = Screen.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		g2.setColor(brightGreen);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + Screen.tileSize/3;
		int textY = frameY + Screen.tileSize;
		final int lineHeight = (int) (Screen.tileSize/1.2);
		
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
		
		int tailX = (frameX + frameWidth) - Screen.tileSize/2;
		textY = frameY + Screen.tileSize;
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
			g2.drawImage(screen.player.currentMagatama.down1, (int) (tailX - Screen.tileSize/1.5), (int) (textY - Screen.tileSize/1.5), null);
		}
		else {
			value = "None";
			textX = getAlignToRightX(value, tailX);
			g2.drawString(value, textX, textY);
			textY += lineHeight;
		}
	}

	public void drawInventory(Entity entity, boolean cursor) {
		int frameX;
		int frameY;
		int frameWidth;
		int frameHeight;
		
		int slotCol;
		int slotRow;
		
		if(entity == screen.player) {
			frameX = Screen.screenWidth - Screen.tileSize*8;
			frameY = Screen.tileSize;
			frameWidth = (int) (Screen.tileSize*6.7);
			frameHeight = (int) (Screen.tileSize*5.6);
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = Screen.tileSize;
			frameY = Screen.tileSize;
			frameWidth = (int) (Screen.tileSize*6.7);
			frameHeight = (int) (Screen.tileSize*5.6);
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		// Frame
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Slot
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		
		// Draw Itens
		for(int i = 0; i < entity.inventory.size(); i++) {
			
			//Equip Cursor
			if(entity.inventory.get(i) == entity.currentMagatama) {
				g2.setColor(greenGreen);
				g2.fillRoundRect(slotX, slotY, Screen.tileSize, Screen.tileSize, 9, 9);;
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			// DISPLAY AMOUNT
			if(entity == screen.player && entity.inventory.get(i).amount > 1) {
				
				g2.setFont(g2.getFont().deriveFont(32F));
				int amountX;
				int amountY;
				
				String s = "" +  entity.inventory.get(i).amount;
				 amountX = getAlignToRightX(s, slotX +65);
				 amountY = slotY + Screen.tileSize;
				 
				 // SHADOW
				g2.setColor(greenGreen);
				g2.drawString(s, amountX, amountY);
				//NUMBER
				g2.setColor(brightGreen);
				g2.drawString(s, amountX-2, amountY-2);
				
			}
			
			slotX += Screen.tileSize;
			if(i == 5 || i == 11 || i == 17 || i == 23) {
				slotX = slotXStart;
				slotY += Screen.tileSize;
			}
		}
		
		slotX = slotXStart;
		slotY = slotYStart;
		
		// Draw Slot Border
		for(int i = 0; i < entity.inventorySize; i++) {
			g2.setColor(darkGreen);
			g2.drawRoundRect(slotX, slotY, Screen.tileSize, Screen.tileSize, 9, 9);
			slotX += Screen.tileSize;
			if(i == 5 || i == 11 || i == 17 || i == 23) {
				slotX = slotXStart;
				slotY += Screen.tileSize;
			}
		}
		
		// Draw Cursor
		if(cursor) {
			int cursorX = slotXStart + (Screen.tileSize * slotCol);
			int cursorY = slotYStart + (Screen.tileSize * slotRow);
			int cursorWidth = Screen.tileSize;
			int cursorHeight = Screen.tileSize;
			
			g2.setColor(brightGreen);
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			// Description Frame
			int dFrameX = frameX;
			int dFrameY = Screen.tileSize+frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = Screen.tileSize*10 - frameHeight;
			
			// Draw Description Text
			int textX = dFrameX+20;
			int textY = (int) (dFrameY+Screen.tileSize*0.75);	
			g2.setFont(g2.getFont().deriveFont(27F));
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			
				for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 36;
				}
			}
		}
	}
	
	public void drawOptionsScreen () {
		g2.setColor(brightGreen);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		// SUB WINDOW
		int frameWidth = Screen.tileSize*8;
		int frameHeight = Screen.tileSize*10;
		int frameX = Screen.screenWidth/2 - frameWidth/2;
		int frameY = Screen.tileSize;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch (subState) {
		case 0: optionsTop (frameX, frameY); break;
		case 1: optionsControl(frameX, frameY); break;
		case 2: optionsEndGame(frameX, frameY); break;
		
		}
	}
	
	public void optionsTop (int frameX, int frameY) {
		
		int textX;
		int textY;
		
		//TITLE
		String text = "Options";
		textX = getCenteredX(text);
		textY = frameY + Screen.tileSize;
		g2.drawString(text, textX, textY);
		
		// MUSIC
		textX = frameX + Screen.tileSize;
		textY += Screen.tileSize*2;
		g2.drawString("Music", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-25, textY);
		}
		
		//SFX
		textY += Screen.tileSize;
		g2.drawString("SFX", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//CONTROL
		textY += Screen.tileSize;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX-25, textY);
			if(screen.key.enterPressed && !screen.key.enterProcessed) {
				subState = 1;
				commandNum = 0;
				screen.playSFX(1);
				screen.key.enterProcessed = true;
			}
		}
		//END GAME
		textY += Screen.tileSize;
		g2.drawString("End Game ", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(screen.key.enterPressed && !screen.key.enterProcessed) {
				subState = 2;
				commandNum = 0;
				screen.playSFX(1);
				screen.key.enterProcessed = true;
			}
		}
		//BACK
		textY += Screen.tileSize*3;
		g2.drawString("Back ", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(screen.key.enterPressed && !screen.key.enterProcessed) {
				screen.gameState = Screen.playState;
				commandNum = 0;
				screen.playSFX(1);
				screen.key.enterProcessed = true;
			}
		}
		
		//MUSIC VOLUME
		textX = frameX + Screen.tileSize*3;
		textY = (int) (frameY + Screen.tileSize*2.7);
		g2.drawRect(textX, textY, 250, 24);
		int volumeWidth = 50 * screen.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		
		//SE VOLUME
		textY += Screen.tileSize;
		g2.drawRect(textX, textY, 250, 24);
		volumeWidth = 50 * screen.sfx.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		screen.config.saveConfig();
	}
	
	public void optionsControl(int frameX, int frameY) {
		int textX;
		int textY;
		
		String text = "Controls";
		textX = getCenteredX(text);
		textY = frameY + Screen.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + Screen.tileSize;
		textY += Screen.tileSize*2;
		
		g2.drawString("Move", textX, textY); textY+=Screen.tileSize;
		g2.drawString("Select/Attack/Interact", textX, textY); textY+=Screen.tileSize;
		g2.drawString("Shoot Spell", textX, textY); textY+=Screen.tileSize;
		g2.drawString("Status/Inventory", textX, textY); textY+=Screen.tileSize;
		g2.drawString("Options", textX, textY); textY+=Screen.tileSize;
		
		textX = frameX + Screen.tileSize*6;
		textY = frameY + Screen.tileSize*3;
		
		g2.drawString("WASD", textX, textY); textY+=Screen.tileSize;
		g2.drawString("E", textX, textY); textY+=Screen.tileSize;
		g2.drawString("F", textX, textY); textY+=Screen.tileSize;
		g2.drawString("ENTER", textX, textY); textY+=Screen.tileSize;
		g2.drawString("ESC", textX, textY); textY+=Screen.tileSize;
		
		textX = frameX + Screen.tileSize;
		textY = frameY + Screen.tileSize*9;
		
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(screen.key.enterPressed && !screen.key.enterProcessed) {
				subState = 0;
				commandNum = 2;
				screen.playSFX(1);
				screen.key.enterProcessed = true;
			}
		}
	}
	
	public void optionsEndGame(int frameX, int frameY) {
		int textX = frameX + Screen.tileSize;;
		int textY = Screen.tileSize*2;;
		
		currentSpeechLine = "Quit the game and return to\nthe title screen?";
		for(String line: currentSpeechLine.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
			
		}
		
		String text = "Yes";
		textX = getCenteredX(text);
		textY += Screen.tileSize*2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(screen.key.enterPressed && !screen.key.enterProcessed) {
				subState = 0;
				screen.gameState = Screen.titleState;
				screen.playSFX(1);
				screen.key.enterProcessed = true;
				screen.stopMusic();
				screen.playMusic(4);
			}
		}
		
		text = "No";
		textX = getCenteredX(text);
		textY += Screen.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(screen.key.enterPressed && !screen.key.enterProcessed) {
				subState = 0;
				commandNum = 3;
				screen.playSFX(1);
				screen.key.enterProcessed = true;
			}
		}
	}

	public int getItemIndexOnSlot(int slotCol, int slotRow) {
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
		int x = (Screen.screenWidth/2) - (length/2);
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
