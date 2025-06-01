package main;

import entity.NPC_BlackFrost;
import entity.NPC_JackFrost;
import enemy.En_Slime;

public class NpcPlacer {

	Screen screen;
	
	public NpcPlacer(Screen screen) {
		this.screen = screen;
	}
	
	public void placeNPC() {
		screen.npc[0] = new NPC_JackFrost(screen);
		screen.npc[0].worldX = 50 * screen.tileSize;
		screen.npc[0].worldY = 33 * screen.tileSize;
		
		screen.npc[1] = new NPC_BlackFrost(screen);
		screen.npc[1].worldX = 51 * screen.tileSize;
		screen.npc[1].worldY = 33 * screen.tileSize;
	}
	public void placeEnemy() {
		screen.enemy[0] = new En_Slime(screen);
		screen.enemy[0].worldX = 52 * screen.tileSize;
		screen.enemy[0].worldY = 33 * screen.tileSize;
		
		screen.enemy[1] = new En_Slime(screen);
		screen.enemy[1].worldX = 53 * screen.tileSize;
		screen.enemy[1].worldY = 33 * screen.tileSize;

	}

}
