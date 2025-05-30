package main;

import entity.NPC_BlackFrost;
import entity.NPC_JackFrost;
import monster.Mon_Matador;

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
	public void placeMonster() {
		screen.monster[0] = new Mon_Matador(screen);
		screen.monster[0].worldX = 52 * screen.tileSize;
		screen.monster[0].worldY = 33 * screen.tileSize;
		
		screen.monster[1] = new Mon_Matador(screen);
		screen.monster[1].worldX = 53 * screen.tileSize;
		screen.monster[1].worldY = 33 * screen.tileSize;

	}

}
