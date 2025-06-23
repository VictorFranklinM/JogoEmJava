package enemy;

import java.awt.Rectangle;

import entity.Entity;
import main.Screen;

public class En_Fomorian extends Entity{
	
	Screen screen;
	
	public En_Fomorian(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeEnemy;
		name = "Fomorian";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxHP = 15;
		hp = maxHP;
		attack = 7;
		defense = 7;
		exp = 70;
		
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
		up1 = setup("/enemies/fomorian/Fomorian-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/enemies/fomorian/Fomorian-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/enemies/fomorian/Fomorian-Up-3",Screen.tileSize, Screen.tileSize);
		down1 = setup("/enemies/fomorian/Fomorian-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/enemies/fomorian/Fomorian-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/enemies/fomorian/Fomorian-Down-3",Screen.tileSize, Screen.tileSize);
		left1 = setup("/enemies/fomorian/Fomorian-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/enemies/fomorian/Fomorian-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/enemies/fomorian/Fomorian-Left-3",Screen.tileSize, Screen.tileSize);
		right1 = setup("/enemies/fomorian/Fomorian-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/enemies/fomorian/Fomorian-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/enemies/fomorian/Fomorian-Right-3",Screen.tileSize, Screen.tileSize);

	}
	
	public void setAction() {
		if(onPath) {
			followLogic();
			loseAgro(screen.player, 15, 100);
		}
		else if(!onPath){
			movementLogic(45);
			getAgro(screen.player, 5, 25);
		}
	}
	
	public void damageReaction() {

	}
}

