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
		
		// Temporary direction to check knockback.
		String facing = entity.facing;
		if(entity.knockBack) {
			facing = entity.knockBackDirection;
		}
		
		switch(facing) {
		case "up":
			entityTopRow = (entityTopY - entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[Screen.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[Screen.currentMap][entityRightCol][entityTopRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "down":
			entityBottomRow = (entityBottomY + entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[Screen.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = screen.tileM.mapTileNum[Screen.currentMap][entityRightCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "left":
			entityLeftCol = (entityLeftX - entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[Screen.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[Screen.currentMap][entityLeftCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
			
		case "right":
			entityRightCol = (entityRightX + entity.speed) / Screen.tileSize;
			tileNum1 = screen.tileM.mapTileNum[Screen.currentMap][entityRightCol][entityTopRow];
			tileNum2 = screen.tileM.mapTileNum[Screen.currentMap][entityRightCol][entityBottomRow];
			
			if((screen.tileM.tile[tileNum1].collision == true) || (screen.tileM.tile[tileNum2].collision == true)) {
				entity.collision = true;
			}
			break;
		}
	}
	
	public int checkObject (Entity entity, boolean player) {
		
		int index = 999; // (Pode ser qualquer numero maior que o tamanho do array de objetos).
		
		// Temporary direction to check knockback.
		String facing = entity.facing;
		if(entity.knockBack) {
			facing = entity.knockBackDirection;
		}
		
		for(int i = 0; i < Screen.objPerScreen; i++) {
			
			if(screen.obj[Screen.currentMap][i] != null) {
				entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
				entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
				
				screen.obj[Screen.currentMap][i].collisionArea.x = screen.obj[Screen.currentMap][i].worldX + screen.obj[Screen.currentMap][i].collisionArea.x;
				screen.obj[Screen.currentMap][i].collisionArea.y = screen.obj[Screen.currentMap][i].worldY + screen.obj[Screen.currentMap][i].collisionArea.y;
				
				switch(facing) {
				case "up":
					entity.collisionArea.y -= entity.speed;
					if(entity.collisionArea.intersects(screen.obj[Screen.currentMap][i].collisionArea)) {
						if(screen.obj[Screen.currentMap][i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "down":
					entity.collisionArea.y += entity.speed;
					if(entity.collisionArea.intersects(screen.obj[Screen.currentMap][i].collisionArea)) {
						if(screen.obj[Screen.currentMap][i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					
					break;
					
				case "left":
					entity.collisionArea.x -= entity.speed;
					if(entity.collisionArea.intersects(screen.obj[Screen.currentMap][i].collisionArea)) {
						if(screen.obj[Screen.currentMap][i].collision == true) {
							entity.collision = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "right":
					entity.collisionArea.x += entity.speed;
					if(entity.collisionArea.intersects(screen.obj[Screen.currentMap][i].collisionArea)) {
						if(screen.obj[Screen.currentMap][i].collision == true) {
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
				
				screen.obj[Screen.currentMap][i].collisionArea.x = screen.obj[Screen.currentMap][i].collisionAreaDefaultX;
				screen.obj[Screen.currentMap][i].collisionArea.y = screen.obj[Screen.currentMap][i].collisionAreaDefaultY;
			}
		}
		
		return index;
	}
	
	public int checkEntity (Entity entity, Entity[][] target) {
		int index = 999;
		
		// Temporary direction to check knockback.
		String facing = entity.facing;
		if(entity.knockBack) {
			facing = entity.knockBackDirection;
		}
		
		for(int i = 0; i < target[1].length; i++) {
	        if(target[Screen.currentMap][i] != null && target[Screen.currentMap][i] != entity && !target[Screen.currentMap][i].dying) {

	            entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
	            entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

	            target[Screen.currentMap][i].collisionArea.x = target[Screen.currentMap][i].worldX + target[Screen.currentMap][i].collisionArea.x;
	            target[Screen.currentMap][i].collisionArea.y = target[Screen.currentMap][i].worldY + target[Screen.currentMap][i].collisionArea.y;

	            switch(facing) {
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

	            if(entity.collisionArea.intersects(target[Screen.currentMap][i].collisionArea)) {
	                entity.collision = true;
	                index = i;
	            }
	            
	            entity.collisionArea.x = entity.collisionAreaDefaultX;
	            entity.collisionArea.y = entity.collisionAreaDefaultY;

	            target[Screen.currentMap][i].collisionArea.x = target[Screen.currentMap][i].collisionAreaDefaultX;
	            target[Screen.currentMap][i].collisionArea.y = target[Screen.currentMap][i].collisionAreaDefaultY;
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
	