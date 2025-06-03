package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.Screen;

public class NPC_JackFrost extends Entity{
	
	private final int defaultSpeed = 1;
			
	public NPC_JackFrost(Screen screen) {
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
	
	public void getImage() {
		face = setup("/npc/JF-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/JF-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/JF-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/JF-Up-3",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/JF-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/JF-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/JF-Down-3",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/JF-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/JF-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/JF-Left-3",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/JF-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/JF-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/JF-Right-3",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		dialogues[0] = "Hee-llo!\nMy name is Jack Frost!";
		dialogues[1] = "I wanna tell you something -ho!";
		dialogues[2] = "I heard that if you collect some magatamas, you\ncan go to another areas -ho!";
		dialogues[3] = "What is a Magatama you say?\nHow could I possibily hee-know!";
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