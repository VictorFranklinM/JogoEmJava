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
		renderNPC();
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
		dialogues[1] = "I will teach you about this world";
		dialogues[2] = "Get magatamas so you can unlock new areas";
	}
	
	public void renderNPC() {
		up1 = setup("/npc/BF-Up-1");
		up2 = setup("/npc/BF-Up-2");
		up3 = setup("/npc/BF-Up-3");
		down1 = setup("/npc/BF-Down-1");
		down2 = setup("/npc/BF-Down-2");
		down3 = setup("/npc/BF-Down-3");
		left1 = setup("/npc/BF-Left-1");
		left2 = setup("/npc/BF-Left-2");
		left3 = setup("/npc/BF-Left-3");
		right1 = setup("/npc/BF-Right-1");
		right2 = setup("/npc/BF-Right-2");
		right3 = setup("/npc/BF-Right-3");

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