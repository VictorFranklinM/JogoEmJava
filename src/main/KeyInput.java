package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	public boolean upHold, downHold, leftHold , rightHold, ePressed, enterPressed, shootKeyPressed, defenseKeyPressed;
	public boolean enterProcessed = false;
	public int endlag;
	private Screen screen;
	
	public boolean isDebugging = false;
	public boolean godModeOn = false;

	public KeyInput(Screen screen) {
		this.screen = screen;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//TITLE STATE
		if(screen.gameState == Screen.titleState) {
			titleState(code);
		}
		
		//PLAY STATE
		else if(screen.gameState == Screen.playState) {
			playState(code);
		}
		
		//DIALOGUE STATE
		else if(screen.gameState == Screen.dialogueState || screen.gameState == Screen.cutsceneState) {
			dialogueState(code);
		}
		//STATUS STATE
		else if(screen.gameState == Screen.statusState) {
			statusState(code);
		}
		//OPTIONS STATE
		else if(screen.gameState == Screen.optionsState) {
			optionsState(code);
		}
		//GAME OVER STATE
		else if(screen.gameState == Screen.gameOverState) {
			gameOverState(code);
		}
		//TRADE STATE
		else if(screen.gameState == Screen.tradeState) {
			tradeState(code);
		}
		//MAP STATE
		else if(screen.gameState == Screen.mapState) {
			mapState(code);
		}
	}
	
	public void tradeState(int code) {
		if(code == KeyEvent.VK_E) {
			ePressed = true;
		}
		
		if(screen.ui.subState == 0) {
			if(code == KeyEvent.VK_W) {
				screen.ui.commandNum--;
				if(screen.ui.commandNum < 0) {
					screen.ui.commandNum = 2;
				}
				screen.playSFX(8);
			}
			if(code == KeyEvent.VK_S) {
				screen.ui.commandNum++;
				if(screen.ui.commandNum > 2) {
					screen.ui.commandNum = 0;
				}
				screen.playSFX(8);
			}
		}
		if(screen.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ENTER) {
				screen.ui.subState = 0;
			}
		}
		if(screen.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ENTER) {
				screen.ui.subState = 0;
			}
		}
	}
	
	public void titleState(int code) {
		if(code == KeyEvent.VK_W) {
			screen.ui.commandNum--;
			if(screen.ui.commandNum < 0) {
				screen.ui.commandNum = 2;
			}
			screen.playSFX(8);
		}
		if (code == KeyEvent.VK_S) {
			screen.ui.commandNum++;
			if(screen.ui.commandNum > 2) {
				screen.ui.commandNum = 0;
			}
			screen.playSFX(8);
		}
	    if(code == KeyEvent.VK_ENTER) {
	    	if(screen.ui.commandNum == 0) {
	    		screen.gameState = Screen.playState;
	    		screen.playSFX(1);
	    		screen.stopMusic();
	    		screen.playMusic(2);
	    	}
	    	if(screen.ui.commandNum == 1) {
	    		screen.npcPlacer.clearEnemies();
	    		screen.npcPlacer.clearNPCS();
	    		screen.saveLoad.load();
	    		screen.npcPlacer.placeEnemy();
	    		screen.npcPlacer.placeNPC();
	    		screen.gameState = Screen.playState;
	    		screen.playSFX(1);
	    		screen.stopMusic();
	    		screen.playMusic(2);
	    	}
	    	if(screen.ui.commandNum == 2) {
	    		System.exit(0);
	    	}
	    }
	}
	   
	public void optionsState(int code) {
	    if(code == KeyEvent.VK_ESCAPE) {
	            screen.gameState = Screen.playState; 
	        }
	    
	    if(code == KeyEvent.VK_ENTER) {
	        enterPressed = true;
	    }

	    int maxCommandNum = 0;
	    switch(screen.ui.subState) {
	        case 0: maxCommandNum = 4; break;
	        case 2: maxCommandNum = 1; break;
	    }

	    if(code == KeyEvent.VK_W) {
	        screen.ui.commandNum--;
	        screen.playSFX(8);
	        if(screen.ui.commandNum < 0) {
	            screen.ui.commandNum = maxCommandNum;
	        }
	    }

	    if(code == KeyEvent.VK_S) {
	        screen.ui.commandNum++;
	        screen.playSFX(8);
	        if(screen.ui.commandNum > maxCommandNum) {
	            screen.ui.commandNum = 0;
	        }
	    }

	  
	    if(code == KeyEvent.VK_A) {
	    	if(screen.ui.subState == 0) {
	    		if(screen.ui.commandNum == 0 && screen.music.volumeScale > 0) {
	    			screen.music.volumeScale--;
	    			screen.music.checkVolume();
	    			screen.playSFX(8);
	    		}
	    		if(screen.ui.commandNum == 1 && screen.sfx.volumeScale > 0) {
	    			screen.sfx.volumeScale--;
	    			screen.tsm.updateVolume(screen.sfx);
	    			screen.playSFX(8);
	    		}
	        }
	    }

	    if(code == KeyEvent.VK_D) {
	    	if(screen.ui.subState == 0) {
	    		if(screen.ui.commandNum == 0 && screen.music.volumeScale < 5) {
	            screen.music.volumeScale++;
	            screen.music.checkVolume();
	            screen.playSFX(8);
	    		}
	    		if(screen.ui.commandNum == 1 && screen.sfx.volumeScale < 5) {
	    			screen.sfx.volumeScale++;
	    			screen.tsm.updateVolume(screen.sfx);
	    			screen.playSFX(8);
	    		}
	    	}
	        
	        
	    }
	}

	public void playState(int code) {
		if(code == KeyEvent.VK_W) {
			upHold = true;
		}
		if(code == KeyEvent.VK_A) {
			leftHold = true;
		}
		if(code == KeyEvent.VK_S) {
			downHold = true;
		}
		if(code == KeyEvent.VK_D) {
			rightHold = true;
		}
		if(code == KeyEvent.VK_E) {
			ePressed = true;
		}
		if(code == KeyEvent.VK_ENTER) {
			screen.gameState = Screen.statusState;
		}
		if(code == KeyEvent.VK_M) {
			screen.gameState = Screen.mapState;
		}
		if(code == KeyEvent.VK_T) {
			if(isDebugging == false) {
				isDebugging = true;
				screen.tileM.drawPath = true;
			}
			else {
				isDebugging = false;
				screen.tileM.drawPath = false;
			}
		}
		if(code == KeyEvent.VK_G) {
			if(godModeOn == false) {
				godModeOn = true;
			}
			else {
				godModeOn = false;
			}
		}
		if(code == KeyEvent.VK_R) {
			switch(Screen.currentMap) {
			case 0: screen.tileM.loadMap("/maps/world01.txt", 0);
			case 1: screen.tileM.loadMap("/maps/dungeon.txt", 1);
			case 2: screen.tileM.loadMap("/maps/dungeonB1.txt", 2);
			case 3: screen.tileM.loadMap("/maps/dungeonB2.txt", 3);
			}
			
		}
		if(code == KeyEvent.VK_F && screen.player.currentMagatama != null) {
			shootKeyPressed = true;
		}
		if(code == KeyEvent.VK_SPACE) {
			if(screen.player.endlag == 0) {
				defenseKeyPressed = true;
			}
		}
		if(code == KeyEvent.VK_ESCAPE) {
			screen.gameState = Screen.optionsState;
		}
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_E) {
			enterPressed = true;
		}
	}
	
	public void statusState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			screen.gameState = Screen.playState;
		}
		if(code == KeyEvent.VK_E) {
			screen.player.selectItem();
		}
		playerInventory(code);
	}
	
	public void mapState(int code) {
		
		if(code == KeyEvent.VK_M) {
			screen.gameState = Screen.playState;
		}
	}
	
	public void playerInventory(int code) {
		if(code == KeyEvent.VK_W) {
			if(screen.ui.playerSlotRow != 0) {
				screen.ui.playerSlotRow--;
				screen.playSFX(8);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(screen.ui.playerSlotCol != 0) {
				screen.ui.playerSlotCol--;
				screen.playSFX(8);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(screen.ui.playerSlotRow != 4) {
				screen.ui.playerSlotRow++;
				screen.playSFX(8);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(screen.ui.playerSlotCol != 5) {
				screen.ui.playerSlotCol++;
				screen.playSFX(8);
			}
		}
	}
	
	public void npcInventory(int code) {
		if(code == KeyEvent.VK_W) {
			if(screen.ui.npcSlotRow != 0) {
				screen.ui.npcSlotRow--;
				screen.playSFX(8);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(screen.ui.npcSlotCol != 0) {
				screen.ui.npcSlotCol--;
				screen.playSFX(8);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(screen.ui.npcSlotRow != 4) {
				screen.ui.npcSlotRow++;
				screen.playSFX(8);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(screen.ui.npcSlotCol != 5) {
				screen.ui.npcSlotCol++;
				screen.playSFX(8);
			}
		}
	}
	
	public void gameOverState(int code) {
		if(code == KeyEvent.VK_W) {
			screen.ui.commandNum--;
			if(screen.ui.commandNum < 0) {
				screen.ui.commandNum = 1;
			}
			screen.playSFX(8);
		}
		if(code == KeyEvent.VK_S) {
			screen.ui.commandNum++;
			if(screen.ui.commandNum > 1) {
				screen.ui.commandNum = 0;
			}
			screen.playSFX(8);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(screen.ui.commandNum == 0) {
				screen.gameState = Screen.playState;
				screen.resetGame(false);
			}
			else if(screen.ui.commandNum == 1) {
				screen.gameState = Screen.titleState;
				screen.resetGame(true);
			}
		}
	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upHold = false;
		}
		if(code == KeyEvent.VK_A) {
			leftHold = false;
		}
		if(code == KeyEvent.VK_S) {
			downHold = false;
		}
		if(code == KeyEvent.VK_D) {
			rightHold = false;
		}
		if(code == KeyEvent.VK_F && shootKeyPressed) {
			shootKeyPressed = false;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
			enterProcessed = false;
		}
		if(code == KeyEvent.VK_E) {
			ePressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			if(screen.player.endlag == 0) {
				screen.player.endlag = 20;
				defenseKeyPressed = false;

			}
			
		}
	}
		
}

