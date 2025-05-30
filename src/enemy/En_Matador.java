package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.Screen;

public class En_Matador extends Entity{
	public En_Matador(Screen screen) {
		super(screen);
		type = 2;
		name = "Matador de muriçoca";
		speed = 2;
		maxHP = 2;
		hp = maxHP;
		collisionArea = new Rectangle();
		collisionArea.x = (4 * screen.scale);
		collisionArea.y = (8 * screen.scale);
		collisionArea.width = (8 * screen.scale);
		collisionArea.height = (7 * screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
        
        getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemies/matador");
		up2 = setup("/enemies/matador");
		up3 = setup("/enemies/matador");
		down1 = setup("/enemies/matador");
		down2 = setup("/enemies/matador");
		down3 = setup("/enemies/matador");
		left1 = setup("/enemies/matador");
		left2 = setup("/enemies/matador");
		left3 = setup("/enemies/matador");
		right1 = setup("/enemies/matador");
		right2 = setup("/enemies/matador");
		right3 = setup("/enemies/matador");

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
}

