package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.Screen;
import object.OBJ_GreenMacca;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;



public class En_Slime extends Entity{
	
	Screen screen;
	
	public En_Slime(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeEnemy;
		name = "Slime";
		elementalWeakness = "Ice";
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxHP = 2;
		hp = maxHP;
		attack = 2;
		defense = 0;
		exp = 2;
		
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
		up1 = setup("/enemies/GSlime-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/enemies/GSlime-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/enemies/GSlime-Up-2",Screen.tileSize, Screen.tileSize);
		down1 = setup("/enemies/GSlime-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/enemies/GSlime-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/enemies/GSlime-Down-2",Screen.tileSize, Screen.tileSize);
		left1 = setup("/enemies/GSlime-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/enemies/GSlime-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/enemies/GSlime-Left-2",Screen.tileSize, Screen.tileSize);
		right1 = setup("/enemies/GSlime-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/enemies/GSlime-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/enemies/GSlime-Right-2",Screen.tileSize, Screen.tileSize);

	}
	
	public void setAction() {
		followLogic();
		
		if(onPath) {
			loseAgro(screen.player, 15, 100);
		}
		else if(!onPath){
			getAgro(screen.player, 5, 50);
		}
	}
	
	public void damageReaction() {
		// Correr do jogador
		actionLockCounter = 0;
		facing = screen.player.facing;
		onPath = true;
		isFollowing = true;
	}
	
	public void checkDrop() {
		
		// Chance
		int i = new Random().nextInt(100)+1;
		
		// Set drops
		if(i < 50) {
			dropItem(new OBJ_GreenMacca(screen));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Medicine(screen));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_ManaBottle(screen));
		}
	}
}

