package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.Screen;



public class En_Slime extends Entity{
	
	
	public En_Slime(Screen screen) {
		super(screen);
		type = typeEnemy;
		name = "slime";
		speed = 2;
		maxHP = 2;
		hp = maxHP;
		attack = 2;
		defense = 0;
		exp = 2;
		
		collisionArea = new Rectangle();
		collisionArea.x = (0 * screen.scale);
		collisionArea.y = (5 * screen.scale);
		collisionArea.width = (16 * screen.scale);
		collisionArea.height = (10 * screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
        
        getImage();
	}
	
	
	public void getImage() {
		up1 = setup("/enemies/GSlime-Up-1",screen.tileSize, screen.tileSize);
		up2 = setup("/enemies/GSlime-Up-2",screen.tileSize, screen.tileSize);
		up3 = setup("/enemies/GSlime-Up-2",screen.tileSize, screen.tileSize);
		down1 = setup("/enemies/GSlime-Down-1",screen.tileSize, screen.tileSize);
		down2 = setup("/enemies/GSlime-Down-2",screen.tileSize, screen.tileSize);
		down3 = setup("/enemies/GSlime-Down-2",screen.tileSize, screen.tileSize);
		left1 = setup("/enemies/GSlime-Left-1",screen.tileSize, screen.tileSize);
		left2 = setup("/enemies/GSlime-Left-2",screen.tileSize, screen.tileSize);
		left3 = setup("/enemies/GSlime-Left-2",screen.tileSize, screen.tileSize);
		right1 = setup("/enemies/GSlime-Right-1",screen.tileSize, screen.tileSize);
		right2 = setup("/enemies/GSlime-Right-2",screen.tileSize, screen.tileSize);
		right3 = setup("/enemies/GSlime-Right-2",screen.tileSize, screen.tileSize);

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

