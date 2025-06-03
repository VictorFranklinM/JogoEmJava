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
		
		int entityLeftCol = entityLeftX / Screen.tileSize;
		int entityRightCol = entityRightX / Screen.tileSize;
		int entityTopRow = entityTopY / Screen.tileSize;
		int entityBottomRow = entityBottomY / Screen.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.facing) {
		case "up":
			entityTopRow = (entityTopY - entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "down":
			entityBottomRow = (entityBottomY + entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = screen.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "left":
			entityLeftCol = (entityLeftX - entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "right":
			entityRightCol = (entityRightX + entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
		}
	}
	
	public int checkObject (Entity entity, boolean player) {
		
		int index = 999; // (Pode ser qualquer numero maior que o tamanho do array de objetos).
		
		for(int i = 0; i < screen.obj.length; i++) {
			
			if(screen.obj[i] != null) {
				entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
				entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
				
				screen.obj[i].collisionArea.x = screen.obj[i].worldX + screen.obj[i].collisionArea.x;
				screen.obj[i].collisionArea.y = screen.obj[i].worldY + screen.obj[i].collisionArea.y;
				
				switch(entity.facing) {
				case "up":
					entity.collisionArea.y -= entity.speed;
					if(entity.collisionArea.intersects(screen.obj[i].collisionArea)) {
						if(screen.obj[i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "down":
					entity.collisionArea.y += entity.speed;
					if(entity.collisionArea.intersects(screen.obj[i].collisionArea)) {
						if(screen.obj[i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					
					break;
					
				case "left":
					entity.collisionArea.x -= entity.speed;
					if(entity.collisionArea.intersects(screen.obj[i].collisionArea)) {
						if(screen.obj[i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "right":
					entity.collisionArea.x += entity.speed;
					if(entity.collisionArea.intersects(screen.obj[i].collisionArea)) {
						if(screen.obj[i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				}
				entity.collisionArea.x = entity.collisionAreaDefaultX;
				entity.collisionArea.y = entity.collisionAreaDefaultY;
				
				screen.obj[i].collisionArea.x = screen.obj[i].collisionAreaDefaultX;
				screen.obj[i].collisionArea.y = screen.obj[i].collisionAreaDefaultY;
			}
		}
		
		return index;
	}
	
	// Colisao de NPC e inimigos
	public int checkEntity (Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
	        if(target[i] != null && target[i] != entity && !target[i].dying) {

	            entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
	            entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

	            target[i].collisionArea.x = target[i].worldX + target[i].collisionArea.x;
	            target[i].collisionArea.y = target[i].worldY + target[i].collisionArea.y;

	            switch(entity.facing) {
				case "up":
					entity.collisionArea.y -= entity.speed;
					
					break;
					
				case "down":
					entity.collisionArea.y += entity.speed;
					
					
					break;
					
				case "left":
					entity.collisionArea.x -= entity.speed;
					
					break;
					
				case "right":
					entity.collisionArea.x += entity.speed;
				
					break;
					
				}

	            if(entity.collisionArea.intersects(target[i].collisionArea)) {
	                entity.collision = true;
	                index = i;
	            }
	            
	            entity.collisionArea.x = entity.collisionAreaDefaultX;
	            entity.collisionArea.y = entity.collisionAreaDefaultY;

	            target[i].collisionArea.x = target[i].collisionAreaDefaultX;
	            target[i].collisionArea.y = target[i].collisionAreaDefaultY;
	        }
	    }
	    return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;

	            entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
	            entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

	            screen.player.collisionArea.x = screen.player.worldX + screen.player.collisionArea.x;
	            screen.player.collisionArea.y = screen.player.worldY + screen.player.collisionArea.y;

	            switch(entity.facing) {
				case "up":
					entity.collisionArea.y -= entity.speed;
					
					break;
					
				case "down":
					entity.collisionArea.y += entity.speed;
					
					
					break;
					
				case "left":
					entity.collisionArea.x -= entity.speed;
					
					break;
					
				case "right":
					entity.collisionArea.x += entity.speed;
				
					break;
					
				}

	            if(entity.collisionArea.intersects(screen.player.collisionArea)) {
	                entity.collision = true;
	                contactPlayer = true;
	            }
	            
	            entity.collisionArea.x = entity.collisionAreaDefaultX;
	            entity.collisionArea.y = entity.collisionAreaDefaultY;

	            screen.player.collisionArea.x = screen.player.collisionAreaDefaultX;
	            screen.player.collisionArea.y = screen.player.collisionAreaDefaultY;
	            
				return contactPlayer;
	        }
		
		}
	