package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.Screen;



public class En_Katakirauwa extends Entity{
	
	
	public En_Katakirauwa(Screen screen) {
		super(screen);
		type = typeEnemy;
		name = "Katakirauwa";
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxHP = 3;
		hp = maxHP;
		attack = 3;
		defense = 1;
		exp = 10;
		
		collisionArea = new Rectangle();
		collisionArea.x = (2 * Screen.scale);
		collisionArea.y = (5 * Screen.scale);
		collisionArea.width = (12 * Screen.scale);
		collisionArea.height = (10 * Screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
        
        getImage();
	}
	
	
	public void getImage() {
		up1 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		up3 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		down1 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		down3 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		left1 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		left3 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		right1 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);
		right3 = setup("/enemies/Katakirauwa-Down-1",Screen.tileSize, Screen.tileSize);

	}
	
	public void setAction() {
		actionLockCounter++;
		
		if(actionLockCounter == 45) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // escolhe um numero de 1�100
			
			if (i <= 25) {
				facing = "up";
			}
			if (i > 25 && i <= 50) {
				facing = "down";
			}
			if (i > 50 && i <= 75) {
				facing = "left";
			}
			if (i > 75 && i <= 100) {
				facing = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	
	public void damageReaction() {
		// Correr do jogador
		actionLockCounter = 0;
		facing = screen.player.facing;
	}
}

