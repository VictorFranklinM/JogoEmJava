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
		collisionArea.x = (4 * Screen.scale);
		collisionArea.y = (8 * Screen.scale);
		collisionArea.width = (8 * Screen.scale);
		collisionArea.height = (7 * Screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}
	
	public void speak() {
		screen.ui.setFace(face);
		super.speak();
	}
	
	public void setTextLines() {
		dialogues[0] = "My name is Black Frost -hoo!";
		dialogues[1] = "Have you heard about the Magatamas?";
		dialogues[2] = "They look like spirals -ho!";
		dialogues[3] = "Some people are saying they can even let you\nuse magic-hoo!";
	}
	
	public void getImage() {
		face = setup("/npc/BF-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/BF-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/BF-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/BF-Up-3",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/BF-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/BF-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/BF-Down-3",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/BF-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/BF-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/BF-Left-3",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/BF-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/BF-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/BF-Right-3",Screen.tileSize, Screen.tileSize);

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