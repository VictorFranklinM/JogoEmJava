package main;

import entity.NPC_BlackFrost;
import entity.NPC_JackFrost;

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
}
