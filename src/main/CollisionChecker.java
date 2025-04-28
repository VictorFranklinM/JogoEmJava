package main;

import entity.Entity;

public class CollisionChecker {
	
	Screen screen;

	public CollisionChecker(Screen sc) {
		this.screen = sc;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftX = entity.worldX + entity.collisionArea.x;
		int entityRightX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
		int entityTopY = entity.worldY + entity.collisionArea.y;
		int entityBottomY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;
		
		int entityLeftCol = entityLeftX / screen.tileSize;
		int entityRightCol = entityRightX / screen.tileSize;
		int entityTopRow = entityTopY / screen.tileSize;
		int entityBottomRow = entityBottomY / screen.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.facing) {
		case "up":
			entityTopRow = (entityTopY - entity.speed) / screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "down":
			entityBottomRow = (entityBottomY + entity.speed) / screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = screen.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "left":
			entityLeftCol = (entityLeftX - entity.speed) / screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "right":
			entityRightCol = (entityRightX + entity.speed) / screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
		}
	}
}
