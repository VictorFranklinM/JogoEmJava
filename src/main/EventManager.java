package main;

import entity.Entity;

public class EventManager {
	Screen screen;
	EventArea eventArea[][][];
	Entity eventMaster;
	
	int lastEventX, lastEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventManager(Screen screen) {
		this.screen = screen;
		eventMaster = new Entity(screen);
		
		eventArea = new EventArea[Screen.maxMap][Screen.maxWorldCol][Screen.maxWorldRow];
		int map = 0;
		int col = 0;
		int row = 0;
		
		while(map < Screen.maxMap && col < Screen.maxWorldCol && row < Screen.maxWorldRow) {
		
			eventArea[map][col][row] = new EventArea();
			eventArea[map][col][row].x = 7*Screen.scale;
			eventArea[map][col][row].y = 7*Screen.scale;
			eventArea[map][col][row].width = 2*Screen.scale;
			eventArea[map][col][row].height = 2*Screen.scale;
			eventArea[map][col][row].eventAreaDefaultX = eventArea[map][col][row].x;
			eventArea[map][col][row].eventAreaDefaultY = eventArea[map][col][row].y;
			
			col++;
			if(col == Screen.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == Screen.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		
		setDialogue();
	}
	
	public void setDialogue() {
		eventMaster.dialogues[0][0] = "You fall into a pit!";
		
		eventMaster.dialogues[1][0] = "You heal yourself!\n(The progress has been saved)";
		eventMaster.dialogues[1][1] = "Truly, a glimmer of hope in these trying times.";
		
		eventMaster.dialogues[2][0] = "You have been teleported!";
		
	}
	
	public void checkEvent() {
		int xDistance = Math.abs(screen.player.worldX - lastEventX);
		int yDistance = Math.abs(screen.player.worldY - lastEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if(distance > Screen.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			// In order to change the map music you need to write screen.stopMusic() inside the brackeys after switchMap(x, y, z)
			//	and after that, write screen.playMusic(x), samething when you go back to the world map.
			if(hit(0, 26, 18, "up") == true) {switchMap(1, 17, 37);} // Dungeon 
			else if(hit(1, 17, 37, "down") == true) {switchMap(0, 26, 18);} // Map
			else if(hit(1, 17, 25, "any") == true) {healingPoint(Screen.dialogueState);} // Save
			else if(hit(1, 17, 26, "any") == true) {healingPoint(Screen.dialogueState);} // Save
			else if(hit(1, 27, 35, "any") == true) {switchMap(2, 17, 26);} // Dungeon -> B1
			else if(hit(2, 17, 26, "any") == true) {switchMap(1, 27, 35);} // B1 -> Dungeon
			else if(hit(2, 18, 35, "any") == true) {switchMap(3, 11, 35);} // B1 -> B2
			else if(hit(3, 11, 35, "any") == true) {switchMap(2, 18, 35);} // B2 -> B1
		}
		
		
	}
	
	public boolean hit(int map, int col, int row, String direction) {
		boolean hit = false;
		
		if(map == Screen.currentMap) {
			screen.player.collisionArea.x = screen.player.worldX + screen.player.collisionArea.x;
			screen.player.collisionArea.y = screen.player.worldY + screen.player.collisionArea.y;
		
			eventArea[map][col][row].x = col*Screen.tileSize + eventArea[map][col][row].x;
			eventArea[map][col][row].y = row*Screen.tileSize + eventArea[map][col][row].y;
		
			if(screen.player.collisionArea.intersects(eventArea[map][col][row]) && eventArea[map][col][row].eventDone == false) {
				if(screen.player.facing.contentEquals(direction) || direction.contentEquals("any")) {
					hit = true;
				
					lastEventX = screen.player.worldX;
					lastEventY = screen.player.worldY;
				}
			}
		}
		
		screen.player.collisionArea.x = screen.player.collisionAreaDefaultX;
		screen.player.collisionArea.y = screen.player.collisionAreaDefaultY;
		
		eventArea[map][col][row].x = eventArea[map][col][row].eventAreaDefaultX;
		eventArea[map][col][row].y = eventArea[map][col][row].eventAreaDefaultY;
		
		return hit;
	}
	
	public void damagePit(int gameState) {
		screen.gameState = gameState;
		screen.playSFX(6);
		eventMaster.startDialogue(eventMaster,0);
		screen.player.hp -= 1;
		
		// eventArea[map][col][row].eventDone = true;
		// Code for one time only events.
		
		canTouchEvent = false;
	}
	
	public void healingPoint(int gameState) {
		if(screen.key.ePressed == true) {
			screen.player.canAttack = false;
			screen.gameState = gameState;
			screen.playSFX(1);
			eventMaster.startDialogue(eventMaster,1);
			screen.player.hp = screen.player.maxHP;
			screen.player.mana = screen.player.maxMana;
			screen.npcPlacer.placeEnemy();
			screen.saveLoad.save();
		}
	}
	
	public void teleportPoint(int gameState, int teleportedX, int teleportedY) {
		screen.gameState = gameState;
		screen.playSFX(7);
		eventMaster.startDialogue(eventMaster, 2);
		screen.player.worldX = Screen.tileSize*teleportedX;
		screen.player.worldY = Screen.tileSize*teleportedY;
	}
	
	public void switchMap(int map, int col, int row) {
		screen.gameState = Screen.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		screen.playSFX(11);
		for (int i = 0; i < screen.npc[Screen.currentMap].length; i++) {
		    if (screen.npc[Screen.currentMap][i] != null) {
		        screen.npc[Screen.currentMap][i].dialogueSet = -1;
		    }
		}
	}
}
