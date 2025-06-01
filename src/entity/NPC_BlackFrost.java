package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.Screen;

public class NPC_BlackFrost extends Entity{
	
	private final int defaultSpeed = 2;
			
	public NPC_BlackFrost(Screen screen) {
		super(screen);
		facing = "down";
		speed = defaultSpeed;
		getImage();
		setTextLines();
		collisionArea = new Rectangle();
		collisionArea.x = (4 * screen.scale);
		collisionArea.y = (8 * screen.scale);
		collisionArea.width = (8 * screen.scale);
		collisionArea.height = (7 * screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}
	
	public void speak() {
		super.speak();
	}
	
	public void setTextLines() {
		dialogues[0] = "Welcome!";
		dialogues[1] = "I  will  teach  you  about  this  world";
		dialogues[2] = "Get  magatamas  so  you  can  unlock  new  areas";
	}
	
	public void getImage() {
		up1 = setup("/npc/BF-Up-1",screen.tileSize, screen.tileSize);
		up2 = setup("/npc/BF-Up-2",screen.tileSize, screen.tileSize);
		up3 = setup("/npc/BF-Up-3",screen.tileSize, screen.tileSize);
		down1 = setup("/npc/BF-Down-1",screen.tileSize, screen.tileSize);
		down2 = setup("/npc/BF-Down-2",screen.tileSize, screen.tileSize);
		down3 = setup("/npc/BF-Down-3",screen.tileSize, screen.tileSize);
		left1 = setup("/npc/BF-Left-1",screen.tileSize, screen.tileSize);
		left2 = setup("/npc/BF-Left-2",screen.tileSize, screen.tileSize);
		left3 = setup("/npc/BF-Left-3",screen.tileSize, screen.tileSize);
		right1 = setup("/npc/BF-Right-1",screen.tileSize, screen.tileSize);
		right2 = setup("/npc/BF-Right-2",screen.tileSize, screen.tileSize);
		right3 = setup("/npc/BF-Right-3",screen.tileSize, screen.tileSize);

	}
	

	
	@Override
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