package main;

import java.awt.Graphics2D;

import boss.Boss_Matador;
import entity.Entity;
import entity.PlayerDummy;
import object.OBJ_IronDoor;

public class CutsceneManager {

	Screen screen;
	Graphics2D g2;
	public int sceneNum = noCutscene;
	public int scenePhase = noCutscene;
	
	//Scene Number
	public static final int noCutscene = -1;
	public final int matador = 0;
	
	public CutsceneManager(Screen screen) {
		this.screen = screen;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch (sceneNum) {
		case matador: sceneMatador(); break;
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
}
