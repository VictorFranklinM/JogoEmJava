package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import boss.Boss_Matador;
import data.Progress;
import entity.Entity;
import entity.NPC_JackFrost;
import entity.PlayerDummy;
import object.OBJ_IronDoor;

public class CutsceneManager {

	Screen screen;
	Graphics2D g2;
	public int sceneNum = noCutscene;
	public int scenePhase = noCutscene;
	int counter = 0;
	float alpha = 0f;
	int y;
	String endCredits;
	
	//Scene Number
	public static final int noCutscene = -1;
	public final int matador = 0;
	public final int ending = 1;
	
	public CutsceneManager(Screen screen) {
		this.screen = screen;
		endCredits = "Credits...\n"
				+ "Equipe nem lembro o nome. Hitoshura?\n"
				+ "\n\n\n\n\n\n\n\n\n\n"
				+ "Se fuerte, Pessoal...\n\n\n\n\n\n"
				+ "Thank you for Playing!";
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch (sceneNum) {
		case matador: sceneMatador(); break;
		case ending: sceneEnding(); break;
		default: break;
		}
	}
	
	public void sceneMatador() {
		if(scenePhase == 0) {
			screen.bossBattleOn = true;
			screen.tsm.stopTileSound();
			
			// Place iron doors.
			for(int i = 0; i < screen.obj[1].length; i++) {
				if(screen.obj[Screen.currentMap][i] == null) {
					screen.obj[Screen.currentMap][i] = new OBJ_IronDoor(screen);
					screen.obj[Screen.currentMap][i].worldX = 131 * Screen.tileSize;
					screen.obj[Screen.currentMap][i].worldY = 129 * Screen.tileSize;
					screen.obj[Screen.currentMap][i].temp = true;
					screen.playSFX(15);	
					break;	
				}
			}
			// Check vacant slot for dummy.
			for(int i = 0; i < screen.npc[1].length; i++) {
				if(screen.npc[Screen.currentMap][i] == null) {
					screen.npc[Screen.currentMap][i] = new PlayerDummy(screen);
					screen.npc[Screen.currentMap][i].worldX = screen.player.worldX;
					screen.npc[Screen.currentMap][i].worldY = screen.player.worldY;
					screen.npc[Screen.currentMap][i].facing = screen.player.facing;
					break;
				}
			}
			
			screen.player.drawing = false;
			screen.map.miniMapOn = false;
			scenePhase++;
		}
			
		if(scenePhase == 1) {
			screen.player.worldY-=2;
			if(screen.player.worldY < 121 * Screen.tileSize) {
				scenePhase++;
			}
		}
		if(scenePhase == 2) {
		    for(int i = 0; i < screen.enemy[1].length; i++) {
		        if(screen.enemy[Screen.currentMap][i] != null && screen.enemy[Screen.currentMap][i].name.equals(Boss_Matador.bossName)) {
		        	screen.enemy[Screen.currentMap][i].sleep = false;
		            screen.ui.npc = screen.enemy[Screen.currentMap][i];
		            screen.ui.setFace(screen.ui.npc.face);
		            screen.ui.isCutsceneDialogue = true;
					scenePhase++;
		            break;
		        }
		    }
		}
		if(scenePhase == 3) {
			screen.gameState = Screen.dialogueState;
		}
		if(scenePhase == 4) {
			Entity dummy = null;
			screen.player.worldY+=4;
			if(dummy == null) {
				for(int i = 0; i < screen.npc[1].length; i++) {
					if(screen.npc[Screen.currentMap][i] != null && screen.npc[Screen.currentMap][i].name.equals(PlayerDummy.npcName)) {
						dummy = screen.npc[Screen.currentMap][i];
						break;
					}
				}
			}
			if(screen.player.worldY > dummy.worldY) {
				scenePhase++;
			}
		}
		if(scenePhase == 5) {
			for(int i = 0; i < screen.npc[1].length; i++) {
				if(screen.npc[Screen.currentMap][i] != null && screen.npc[Screen.currentMap][i].name.equals(PlayerDummy.npcName)) {
					screen.player.worldX = screen.npc[Screen.currentMap][i].worldX;
					screen.player.worldY = screen.npc[Screen.currentMap][i].worldY;
					screen.player.spriteNum = 1;
					screen.npc[Screen.currentMap][i] = null;
					break;
				}
			}
			screen.player.drawing = true;
			sceneNum = noCutscene;
			scenePhase = noCutscene;
			screen.gameState = Screen.playState;
			
			screen.stopMusic();
			screen.playMusic(16);
		}
	}
	
	public void sceneEnding() {
	    if (scenePhase == 0) {
	        screen.map.miniMapOn = false;
	        screen.tsm.stopTileSound();

	        // Place dummy
	        for (int i = 0; i < screen.npc[1].length; i++) {
	            if (screen.npc[Screen.currentMap][i] == null) {
	                screen.npc[Screen.currentMap][i] = new PlayerDummy(screen);
	                screen.npc[Screen.currentMap][i].worldX = screen.player.worldX;
	                screen.npc[Screen.currentMap][i].worldY = screen.player.worldY;
	                screen.npc[Screen.currentMap][i].facing = screen.player.facing;
	                break;
	            }
	        }

	        screen.player.drawing = false;
	        scenePhase++;
	    }

	    if (scenePhase == 1) {
	        // Move camera
	        screen.player.worldY += 4;
	        if (screen.player.worldY >= 128 * Screen.tileSize) {
	            scenePhase++;
	        }
	    }

	    if (scenePhase == 2) {
	        for (int i = 0; i < screen.npc[Screen.currentMap].length; i++) {
	            if (screen.npc[Screen.currentMap][i] instanceof NPC_JackFrost jack) {
	                jack.inCutscene = true;
	                screen.ui.npc = jack;
	                screen.ui.isCutsceneDialogue = true;
	                jack.speak();
	                scenePhase++;
	                break;
	            }
	        }
	    }

	    if (scenePhase == 3) {
	        if (screen.gameState == Screen.playState) {
	            scenePhase++;
	        }
	    }

	    if (scenePhase == 4) {
	        screen.player.worldY -= 4;

	        Entity dummy = null;
	        for (int i = 0; i < screen.npc[Screen.currentMap].length; i++) {
	            if (screen.npc[Screen.currentMap][i] instanceof PlayerDummy) {
	                dummy = screen.npc[Screen.currentMap][i];
	                break;
	            }
	        }

	        if (dummy != null && screen.player.worldY <= dummy.worldY) {
	            scenePhase++;
	        }
	    }
	    
	    if (scenePhase == 5) {
	    	screen.stopMusic();
	    	screen.playSFX(17);
	    	scenePhase++;
	    }
	    
	    if (scenePhase == 6) {
	    	if(counterReached(300)) {
	    		scenePhase++;
	    	}	    	
	    }
	    
	    if (scenePhase == 7) {
	    	alpha += 0.005f;
	    	if(alpha > 1f) {
	    		alpha = 1f;
	    	}
	    	drawBlackBackground(alpha);
	    	
	    	if (alpha == 1f) {
	    		alpha = 0;
	    		scenePhase++;
	    	}
	    }
	    
	    if (scenePhase == 8) {
	    	drawBlackBackground(1f);
	    	alpha += 0.005f;
	    	if(alpha > 1f) {
	    		alpha = 1f;
	    	}
	    	String text = "After this fierce battle against the boss,\n"
	    			+ "I couldn't even find my friend?\n"
	    			+ "Fuck this shit, I'm out.";
	    	
	    	drawString(alpha, 55f, 200, text, 110);
	    	
	    	if(counterReached(700)) {
	    		screen.playMusic(18);
	    		scenePhase++;
	    	}
	    }
	    
	    if (scenePhase == 9) {
	    	drawBlackBackground(1f);
	    	
	    	drawString (1f, 80f, Screen.screenHeight/2, "Shin Megami Tensei:\nPersona VI", 80);
	    	
	    	if(counterReached(300)) {
	    		scenePhase++;
	    	}
	    }
	    
	    if(scenePhase == 10) {
	    	drawBlackBackground(1f);
	    	
	    	y = Screen.screenHeight/2;
	    	
	    	drawString(1f, 45f, y, endCredits, 50);
	    	
	    	if(counterReached(450)) {
	    		scenePhase++;
	    	}
	    }
	    
	    if(scenePhase == 11) {
	        drawBlackBackground(1f);
	        drawString(1f, 45f, y, endCredits, 50);

	        int totalLines = endCredits.split("\n").length;
	        int lastLineY = y + (totalLines - 1) * 50;
	        
	        if(lastLineY > Screen.screenHeight/2) {
	        	y--;
	        }

	        else if(lastLineY <= Screen.screenHeight/2) {
	        	if(counterReached(180)) {
		    		scenePhase++;
		    		alpha = 0;
		    	}
	        }
	    }
	    
	    if(scenePhase == 12) {
	    	drawBlackBackground(1);
	    	if(counterReached(180)) {
	    		scenePhase++;
	    	}
	    }

	    if(scenePhase == 13) {
	    	for (int i = 0; i < screen.npc[1].length; i++) {
	            if (screen.npc[Screen.currentMap][i] != null && screen.npc[Screen.currentMap][i] instanceof NPC_JackFrost jack) {
	                jack.inCutscene = false;
	                break;
	            }
	    	}
	    	for(int i = 0; i < screen.npc[1].length; i++) {
				if(screen.npc[Screen.currentMap][i] != null && screen.npc[Screen.currentMap][i] instanceof PlayerDummy) {
					screen.player.spriteNum = 1;
					screen.npc[Screen.currentMap][i] = null;
					break;
				}
			}
	    	screen.gameState = Screen.titleState;
	    	screen.player.drawing = true;
	        screen.map.miniMapOn = true;
	    	sceneNum = noCutscene;
	    	scenePhase = noCutscene;
	    	Progress.finalCutsceneDone = true;
	    	screen.resetGame(true);
	    }
	}
	
	public boolean counterReached(int target) {
		boolean counterReached = false;
		counter++;
		if (counter>target) {
			counterReached = true;
			counter = 0;
		}
		return counterReached;
	}
	
	public void drawBlackBackground(float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.setFont(screen.ui.megaten.deriveFont(fontSize));
		
		for(String line: text.split("\n")) {
			int x = getCenteredX(line);
			g2.drawString(line, x, y);
			y += lineHeight;
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	private int getCenteredX(String text) {
	    int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	    return (Screen.screenWidth / 2) - (length / 2);
	}
}