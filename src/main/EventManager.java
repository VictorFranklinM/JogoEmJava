package main;

public class EventManager {
	Screen screen;
	EventArea eventArea[][][];
	
	int lastEventX, lastEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventManager(Screen screen) {
		this.screen = screen;
		
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
	}
	
	public void checkEvent() {
		int xDistance = Math.abs(screen.player.worldX - lastEventX);
		int yDistance = Math.abs(screen.player.worldY - lastEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if(distance > Screen.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			if(hit(0, 46, 28, "any") == true) {damagePit(Screen.dialogueState);}
			// else if(hit(0, 46, 27, "any") == true) {teleportPoint(screen.dialogueState, 50, 40);}
			// else if(hit(0, 46, 26, "any") == true) {healingPoint(screen.dialogueState);}
			else if(hit(0, 26, 18, "up") == true) {switchMap(1, 17, 37);}
			else if(hit(1, 17, 37, "down") == true) {switchMap(0, 26, 18);}
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
		screen.ui.currentSpeechLine = "You fall into a pit!";
		screen.player.hp -= 1;
		
		// eventArea[map][col][row].eventDone = true;
		// Para eventos que só ocorrem 1 vez.
		
		canTouchEvent = false;
	}
	
	public void healingPoint(int gameState) {
		if(screen.key.ePressed == true) {
			screen.player.canAttack = false;
			screen.gameState = gameState;
			screen.playSFX(1);
			screen.ui.currentSpeechLine = "You heal yourself!";
			screen.player.hp = screen.player.maxHP;
			screen.player.mana = screen.player.maxMana;
			screen.npcPlacer.placeEnemy();
		}
	}
	
	public void teleportPoint(int gameState, int teleportedX, int teleportedY) {
		screen.gameState = gameState;
		screen.playSFX(7);
		screen.ui.currentSpeechLine = "You have been teleported!";
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
	}
}
