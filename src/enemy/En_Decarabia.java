package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.Screen;

public class En_Decarabia extends Entity{
	
	
	public En_Decarabia(Screen screen) {
		super(screen);
		type = typeEnemy;
		name = "Decarabia";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxHP = 7;
		hp = maxHP;
		attack = 2;
		defense = 2;
		exp = 20;
		
		collisionArea = new Rectangle();
		collisionArea.x = (2 * Screen.scale);
		collisionArea.y = (5 * Screen.scale);
		collisionArea.width = (12 * Screen.scale);
		collisionArea.height = (10 * Screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
        
        getImage();
	}
	
	public void update() {
		super.update();
		int xDistance = Math.abs(worldX - screen.player.worldX);
		int yDistance = Math.abs(worldY - screen.player.worldY);
		int tileDistance = (xDistance + yDistance) / Screen.tileSize;
		if(onPath == false && tileDistance < 5) {
			int i = new Random().nextInt(100) + 1;
			if(i > 50) {
				onPath = true;
				isFollowing = true;
			}
		}
		if(onPath && tileDistance > 10) {
			onPath = false;
			isFollowing = false;
		}
	
	}
	
	public void getImage() {
		up1 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		up3 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		down1 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		down3 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		left1 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		left3 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		right1 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);
		right3 = setup("/enemies/Decarabia-Down-1",Screen.tileSize, Screen.tileSize);

	}
	
	public void setAction() {
		followLogic();
	}
	
	public void damageReaction() {
		// Correr do jogador
		actionLockCounter = 0;
		facing = screen.player.facing;
	}
}

