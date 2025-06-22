package main;

import entity.NPC_BlackFrost;
import entity.NPC_JackFrost;
import entity.NPC_Merchant;
import entity.NPC_Nadja;
import boss.Boss_Matador;
import data.Progress;
import enemy.En_Decarabia;
import enemy.En_Fomorian;
import enemy.En_Goblin;
import enemy.En_Katakirauwa;
import enemy.En_Placeholder;
import enemy.En_Slime;

public class NpcPlacer {

	Screen screen;
	
	public NpcPlacer(Screen screen) {
		this.screen = screen;
	}
	
	public void placeNPC() {
		int i = 0;
		int mapNum = 0;
		screen.npc[mapNum][i] = new NPC_JackFrost(screen);
		screen.npc[mapNum][i].worldX = 27 * Screen.tileSize;
		screen.npc[mapNum][i].worldY = 38 * Screen.tileSize;
		i++;
		screen.npc[mapNum][i] = new NPC_BlackFrost(screen);
		screen.npc[mapNum][i].worldX = 21 * Screen.tileSize;
		screen.npc[mapNum][i].worldY = 37 * Screen.tileSize;
		i++;
		
		i = 0;
		mapNum = 1;
		
		screen.npc[mapNum][i] = new NPC_Nadja(screen);
		screen.npc[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.npc[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		screen.npc[mapNum][i] = new NPC_Merchant(screen);
		screen.npc[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.npc[mapNum][i].worldY = 36 * Screen.tileSize;
		i++;
		
		i = 0;
		mapNum = 2;
		
		i = 0;
		mapNum = 3;
		
		i = 0;
		mapNum = 4;
		if(!Progress.finalCutsceneDone) {
			screen.npc[mapNum][i] = new NPC_JackFrost(screen);
			screen.npc[mapNum][i].speed = 0;
			screen.npc[mapNum][i].worldX = 131 * Screen.tileSize;
			screen.npc[mapNum][i].worldY = 131 * Screen.tileSize;
			i++;
		}
	}
	
	public void placeEnemy() {
		int i = 0;
		int mapNum = 0;
		screen.enemy[mapNum][i] = new En_Slime(screen);
		screen.enemy[mapNum][i].worldX = 20 * Screen.tileSize;
		screen.enemy[mapNum][i].worldY = 29 * Screen.tileSize;
		i++;
		screen.enemy[mapNum][i] = new En_Goblin(screen);
		screen.enemy[mapNum][i].worldX = 24 * Screen.tileSize;
		screen.enemy[mapNum][i].worldY = 32 * Screen.tileSize;
		i++;
		screen.enemy[mapNum][i] = new En_Fomorian(screen);
		screen.enemy[mapNum][i].worldX = 28 * Screen.tileSize;
		screen.enemy[mapNum][i].worldY = 26 * Screen.tileSize;
		i++;
		screen.enemy[mapNum][i] = new En_Decarabia(screen);
		screen.enemy[mapNum][i].worldX = 32 * Screen.tileSize;
		screen.enemy[mapNum][i].worldY = 29 * Screen.tileSize;
		i++;
		screen.enemy[mapNum][i] = new En_Katakirauwa(screen);
		screen.enemy[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.enemy[mapNum][i].worldY = 26 * Screen.tileSize;
		i++;
		screen.enemy[mapNum][i] = new En_Placeholder (screen);
		screen.enemy[mapNum][i].worldX = 42 * Screen.tileSize;
		screen.enemy[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		
		i = 0;
		mapNum = 1;
		
		i = 0;
		mapNum = 2;
		
		i = 0;
		mapNum = 3;
		
		if(!Progress.matadorDefeated) {
			screen.enemy[mapNum][i] = new Boss_Matador(screen);
			screen.enemy[mapNum][i].worldX = 131 * Screen.tileSize;
			screen.enemy[mapNum][i].worldY = 119 * Screen.tileSize;
			i++;
		}
	}
	
	public void clearEnemies() {
	    for (int map = 0; map < Screen.maxMap; map++) {
	        for (int i = 0; i < screen.enemy[map].length; i++) {
	            screen.enemy[map][i] = null;
	        }
	    }
	}
	
	public void clearNPCS() {
	    for (int map = 0; map < Screen.maxMap; map++) {
	        for (int i = 0; i < screen.npc[map].length; i++) {
	            screen.npc[map][i] = null;
	        }
	    }
	}
}
