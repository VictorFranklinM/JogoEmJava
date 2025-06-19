package boss;

import java.awt.Rectangle;

import entity.Entity;
import main.Screen;

public class Boss_Matador extends Entity{
	
	Screen screen;
	int size = 2; // 16x16 = 1, 32x32 = 2, 64x64 = 4 
	
	public static final String bossName = "Matador";
	
	public Boss_Matador(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeEnemy;
		name = bossName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxHP = 20; // Change later
		hp = maxHP;
		attack = 1; // Change later
		defense = 1; // Change later
		exp = 5; // Change later
		knockBackPower = 5;
		isBoss = true;
		
		collisionArea = new Rectangle();
		collisionArea.x = (5 * Screen.scale);
		collisionArea.y = (2 * Screen.scale);
		collisionArea.width = (19 * Screen.scale);
		collisionArea.height = (30 * Screen.scale);

		motion1Duration = 40;
		motion2Duration = 100;
		attackArea.width = (int) (Screen.tileSize*1.5); // Probably will change
		attackArea.height = (int) (Screen.tileSize*1.5); // Probably will change
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		attackAreaDefaultHeight = attackArea.height;
		attackAreaDefaultWidth = attackArea.width;
        
        getImage();
        getAttackImage();
	}
	
	public void getImage() {
		
		if(!enraged) {
			up1 = setup("/bosses/matador/Matador-Up-1",Screen.tileSize*size, Screen.tileSize*size);
			up2 = setup("/bosses/matador/Matador-Up-2",Screen.tileSize*size, Screen.tileSize*size);
			up3 = setup("/bosses/matador/Matador-Up-3",Screen.tileSize*size, Screen.tileSize*size);
			down1 = setup("/bosses/matador/Matador-Down-1",Screen.tileSize*size, Screen.tileSize*size);
			down2 = setup("/bosses/matador/Matador-Down-2",Screen.tileSize*size, Screen.tileSize*size);
			down3 = setup("/bosses/matador/Matador-Down-3",Screen.tileSize*size, Screen.tileSize*size);
			left1 = setup("/bosses/matador/Matador-Left-1",Screen.tileSize*size, Screen.tileSize*size);
			left2 = setup("/bosses/matador/Matador-Left-2",Screen.tileSize*size, Screen.tileSize*size);
			left3 = setup("/bosses/matador/Matador-Left-3",Screen.tileSize*size, Screen.tileSize*size);
			right1 = setup("/bosses/matador/Matador-Right-1",Screen.tileSize*size, Screen.tileSize*size);
			right2 = setup("/bosses/matador/Matador-Right-2",Screen.tileSize*size, Screen.tileSize*size);
			right3 = setup("/bosses/matador/Matador-Right-3",Screen.tileSize*size, Screen.tileSize*size);
		}
		/*
		else if(enraged) {
			up1 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			up2 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			up3 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			down1 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			down2 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			down3 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			left1 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			left2 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			left3 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			right1 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			right2 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
			right3 = setup("/bosses/matador/Matador-Down-1-Phase2",Screen.tileSize*size, Screen.tileSize*size);
		}
		*/
	}
	
	public void getAttackImage() {
		if(!enraged) {
			attackUp1 = setup("/bosses/matador/Matador-AttackUp-1", Screen.tileSize*size, Screen.tileSize*size*2);
			attackUp2 = setup("/bosses/matador/Matador-AttackUp-2", Screen.tileSize*size, Screen.tileSize*size*2);
			attackDown1 = setup("/bosses/matador/Matador-AttackDown-1", Screen.tileSize*size, Screen.tileSize*size*2);
			attackDown2 = setup("/bosses/matador/Matador-AttackDown-2", Screen.tileSize*size, Screen.tileSize*size*2);
			attackLeft1 = setup("/bosses/matador/Matador-AttackLeft-1", Screen.tileSize*size*2, Screen.tileSize*size);
			attackLeft2 = setup("/bosses/matador/Matador-AttackLeft-2", Screen.tileSize*size*2, Screen.tileSize*size);
			attackRight1 = setup("/bosses/matador/Matador-AttackRight-1", Screen.tileSize*size*2, Screen.tileSize*size);
			attackRight2 = setup("/bosses/matador/Matador-AttackRight-2", Screen.tileSize*size*2, Screen.tileSize*size);
		}
		/*
		else if(enraged) {
			attackUp1 = setup("/bosses/matador/Matador-AttackDown-1-Phase2", Screen.tileSize*size, Screen.tileSize*size*2);
			attackUp2 = setup("/bosses/matador/Matador-AttackDown-2-Phase2", Screen.tileSize*size, Screen.tileSize*size*2);
			attackDown1 = setup("/bosses/matador/Matador-AttackDown-1-Phase2", Screen.tileSize*size, Screen.tileSize*size*2);
			attackDown2 = setup("/bosses/matador/Matador-AttackDown-2-Phase2", Screen.tileSize*size, Screen.tileSize*size*2);
			attackLeft1 = setup("/bosses/matador/Matador-AttackDown-1-Phase2", Screen.tileSize*size*2, Screen.tileSize*size);
			attackLeft2 = setup("/bosses/matador/Matador-AttackDown-2-Phase2", Screen.tileSize*size*2, Screen.tileSize*size);
			attackRight1 = setup("/bosses/matador/Matador-AttackDown-1-Phase2", Screen.tileSize*size*2, Screen.tileSize*size);
			attackRight2 = setup("/bosses/matador/Matador-AttackDown-2-Phase2", Screen.tileSize*size*2, Screen.tileSize*size);
		}
		*/
	}
	
	public void setAction() {
		
		/*
		if(!enraged && hp < maxHP/2) {
		 
			enraged = true;
			getImage();
			getAttackImage();
			defaultSpeed += 2;
			speed = defaultSpeed;
			attack += 5;
		}
		*/
		
		if(getTileDistance(screen.player) < 15 || onPath) {
			onPath = true;
			bossMovement(60);
		}
		else {
			movementLogic(120);
		}
		if(!attacking) {
		    useAttackEnemy(60, (int) (Screen.tileSize*2.5), (int) (Screen.tileSize*0.8)); // Probably will change
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
	}
}