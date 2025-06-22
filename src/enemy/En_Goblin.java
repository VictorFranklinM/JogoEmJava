package enemy;

import java.awt.Rectangle;

import entity.Entity;
import main.Screen;
import object.Magic_Arrow;

public class En_Goblin extends Entity{
	
	Screen screen;
	
	public En_Goblin(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeEnemy;
		name = "Goblin";
		defaultSpeed = 3;
		speed = defaultSpeed;
		maxHP = 3;
		hp = maxHP;
		attack = 2;
		defense = 1;
		exp = 5;
		projectile = new Magic_Arrow(screen);
		
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
        up1 = setup("/enemies/goblin/Goblin-Up-1",Screen.tileSize, Screen.tileSize);
        up2 = setup("/enemies/goblin/Goblin-Up-2",Screen.tileSize, Screen.tileSize);
        up3 = setup("/enemies/goblin/Goblin-Up-3",Screen.tileSize, Screen.tileSize);
        down1 = setup("/enemies/goblin/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
        down2 = setup("/enemies/goblin/Goblin-Down-2",Screen.tileSize, Screen.tileSize);
        down3 = setup("/enemies/goblin/Goblin-Down-3",Screen.tileSize, Screen.tileSize);
        left1 = setup("/enemies/goblin/Goblin-Left-1",Screen.tileSize, Screen.tileSize);
        left2 = setup("/enemies/goblin/Goblin-Left-2",Screen.tileSize, Screen.tileSize);
        left3 = setup("/enemies/goblin/Goblin-Left-3",Screen.tileSize, Screen.tileSize);
        right1 = setup("/enemies/goblin/Goblin-Right-1",Screen.tileSize, Screen.tileSize);
        right2 = setup("/enemies/goblin/Goblin-Right-2",Screen.tileSize, Screen.tileSize);
        right3 = setup("/enemies/goblin/Goblin-Right-3",Screen.tileSize, Screen.tileSize);
    }
	
	public void setAction() {
		if(onPath) {
			followLogic();
			loseAgro(screen.player, 15, 100);
			useSpellEnemy(200, 90);
		}
		else if(!onPath){
			movementLogic(45);
			getAgro(screen.player, 5, 25);
		}
	}
	
	public void damageReaction() {
		// This code makes the enemy run from the player
		actionLockCounter = 0;
		facing = screen.player.facing;
	}
}

