package enemy;

import java.awt.Rectangle;

import entity.Entity;
import main.Screen;

public class En_Placeholder extends Entity{
	
	Screen screen;
	
	public En_Placeholder(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeEnemy;
		name = "Blank";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxHP = 3;
		hp = maxHP;
		attack = 2;
		defense = 1;
		exp = 5;
		
		collisionArea = new Rectangle();
		collisionArea.x = (2 * Screen.scale);
		collisionArea.y = (5 * Screen.scale);
		collisionArea.width = (12 * Screen.scale);
		collisionArea.height = (10 * Screen.scale);

		motion1Duration = 40;
		motion2Duration = 85;
		attackArea.width = Screen.tileSize*2;
		attackArea.height = Screen.tileSize;
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		attackAreaDefaultHeight = attackArea.height;
		attackAreaDefaultWidth = attackArea.width;
        
        getImage();
        getAttackImage();
	}
	
	public void getImage() {
		up1 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		up3 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		down1 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		down3 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		left1 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		left3 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		right1 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);
		right3 = setup("/enemies/Goblin-Down-1",Screen.tileSize, Screen.tileSize);

	}
	
	public void getAttackImage() {
		attackUp1 = setup("/player/AttackUp-1", Screen.tileSize, Screen.tileSize*2);
		attackUp2 = setup("/player/AttackUp-2", Screen.tileSize, Screen.tileSize*2);
		attackDown1 = setup("/player/AttackDown-1", Screen.tileSize, Screen.tileSize*2);
		attackDown2 = setup("/player/AttackDown-2", Screen.tileSize, Screen.tileSize*2);
		attackLeft1 = setup("/player/AttackLeft-1", Screen.tileSize*2, Screen.tileSize);
		attackLeft2 = setup("/player/AttackLeft-2", Screen.tileSize*2, Screen.tileSize);
		attackRight1 = setup("/player/AttackRight-1", Screen.tileSize*2, Screen.tileSize);
		attackRight2 = setup("/player/AttackRight-2", Screen.tileSize*2, Screen.tileSize);
	}
	
	public void setAction() {
		followLogic();
		
		if(onPath) {
			loseAgro(screen.player, 15, 100);
		}
		else if(!onPath){
			getAgro(screen.player, 5, 25);
		}
		if(!attacking) {
			useAttackEnemy(30, Screen.tileSize*3, Screen.tileSize);
		}
	}
	
	public void damageReaction() {
		// Correr do jogador
		actionLockCounter = 0;
		facing = screen.player.facing;
	}
}