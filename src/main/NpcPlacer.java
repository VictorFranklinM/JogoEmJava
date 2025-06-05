package main;

import entity.NPC_BlackFrost;
import entity.NPC_JackFrost;
import enemy.En_Decarabia;
import enemy.En_Fomorian;
import enemy.En_Goblin;
import enemy.En_Katakirauwa;
import enemy.En_Slime;

public class NpcPlacer {

	Screen screen;
	
	public NpcPlacer(Screen screen) {
		this.screen = screen;
	}
	
	public void placeNPC() {
		screen.npc[0] = new NPC_JackFrost(screen);
		screen.npc[0].worldX = 27 * Screen.tileSize;
		screen.npc[0].worldY = 38 * Screen.tileSize;
		
		screen.npc[1] = new NPC_BlackFrost(screen);
		screen.npc[1].worldX = 21 * Screen.tileSize;
		screen.npc[1].worldY = 37 * Screen.tileSize;
	}
	
	public void placeEnemy() {
		int i = 0;
		
		screen.enemy[i] = new En_Slime(screen);
		screen.enemy[i].worldX = 20 * Screen.tileSize;
		screen.enemy[i].worldY = 29 * Screen.tileSize;
		i++;
		screen.enemy[i] = new En_Goblin(screen);
		screen.enemy[i].worldX = 24 * Screen.tileSize;
		screen.enemy[i].worldY = 32 * Screen.tileSize;
		i++;
		screen.enemy[i] = new En_Fomorian(screen);
		screen.enemy[i].worldX = 28 * Screen.tileSize;
		screen.enemy[i].worldY = 26 * Screen.tileSize;
		i++;
		screen.enemy[i] = new En_Decarabia(screen);
		screen.enemy[i].worldX = 32 * Screen.tileSize;
		screen.enemy[i].worldY = 29 * Screen.tileSize;
		i++;
		screen.enemy[i] = new En_Katakirauwa(screen);
		screen.enemy[i].worldX = 19 * Screen.tileSize;
		screen.enemy[i].worldY = 26 * Screen.tileSize;
		i++;
	}

}
