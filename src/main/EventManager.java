package main;

public class EventManager {
	Screen screen;
	EventArea eventArea[][];
	
	int lastEventX, lastEventY;
	boolean canTouchEvent = true;
	
	public EventManager(Screen screen) {
		this.screen = screen;
		
		eventArea = new EventArea[Screen.maxWorldCol][Screen.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {
		
			eventArea[col][row] = new EventArea();
			eventArea[col][row].x = 23;
			eventArea[col][row].y  = 23;
			eventArea[col][row].width = 2;
			eventArea[col][row].height = 2;
			eventArea[col][row].eventAreaDefaultX = eventArea[col][row].x;
			eventArea[col][row].eventAreaDefaultY = eventArea[col][row].y;
			
			col++;
			if(col == Screen.maxWorldCol) {
				col = 0;
				row++;
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
			if(hit(46, 28, "any") == true) {damagePit(46, 28, screen.dialogueState);}
			if(hit(46, 27, "any") == true) {teleportPoint(46, 27, screen.dialogueState);}
			if(hit(46, 26, "any") == true) {healingPoint(46, 26, screen.dialogueState);}
		}
		
		
	}
	
	public boolean hit(int col, int row, String direction) {
		boolean hit = false;
		
		screen.player.collisionArea.x = screen.player.worldX + screen.player.collisionArea.x;
		screen.player.collisionArea.y = screen.player.worldY + screen.player.collisionArea.y;
		
		eventArea[col][row].x = col*Screen.tileSize + eventArea[col][row].x;
		eventArea[col][row].y = row*Screen.tileSize + eventArea[col][row].y;
		
		if(screen.player.collisionArea.intersects(eventArea[col][row]) && eventArea[col][row].eventDone == false) {
			if(screen.player.facing.contentEquals(direction) || direction.contentEquals("any")) {
				hit = true;
				
				lastEventX = screen.player.worldX;
				lastEventY = screen.player.worldY;
			}
		}
		
		screen.player.collisionArea.x = screen.player.collisionAreaDefaultX;
		screen.player.collisionArea.y = screen.player.collisionAreaDefaultY;
		
		eventArea[col][row].x = eventArea[col][row].eventAreaDefaultX;
		eventArea[col][row].y = eventArea[col][row].eventAreaDefaultY;
		
		return hit;
	}
	
	public void damagePit(int col, int row, int gameState) {
		screen.gameState = gameState;
		screen.playSFX(6);
		screen.ui.currentSpeechLine = "You fall into a pit!";
		screen.player.hp -= 1;
		
		canTouchEvent = false;
	}
	
	public void healingPoint(int col, int row, int gameState) {
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
	
	public void teleportPoint(int col, int row, int gameState) {
		screen.gameState = gameState;
		screen.playSFX(7);
		screen.ui.currentSpeechLine = "You have been teleported!";
		screen.player.worldX = Screen.tileSize*50;
		screen.player.worldY = Screen.tileSize*32;
	}
}
