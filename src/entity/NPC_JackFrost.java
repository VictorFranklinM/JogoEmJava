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
		collisionArea.x = (4 * screen.scale);
		collisionArea.y = (8 * screen.scale);
		collisionArea.width = (8 * screen.scale);
		collisionArea.height = (7 * screen.scale);

		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}
	
	public void speak() {
		screen.ui.setFace(face);
		super.speak();
	}
	
	public void getImage() {
		face = setup("/npc/JF-Face",screen.tileSize, screen.tileSize);
		up1 = setup("/npc/JF-Up-1",screen.tileSize, screen.tileSize);
		up2 = setup("/npc/JF-Up-2",screen.tileSize, screen.tileSize);
		up3 = setup("/npc/JF-Up-3",screen.tileSize, screen.tileSize);
		down1 = setup("/npc/JF-Down-1",screen.tileSize, screen.tileSize);
		down2 = setup("/npc/JF-Down-2",screen.tileSize, screen.tileSize);
		down3 = setup("/npc/JF-Down-3",screen.tileSize, screen.tileSize);
		left1 = setup("/npc/JF-Left-1",screen.tileSize, screen.tileSize);
		left2 = setup("/npc/JF-Left-2",screen.tileSize, screen.tileSize);
		left3 = setup("/npc/JF-Left-3",screen.tileSize, screen.tileSize);
		right1 = setup("/npc/JF-Right-1",screen.tileSize, screen.tileSize);
		right2 = setup("/npc/JF-Right-2",screen.tileSize, screen.tileSize);
		right3 = setup("/npc/JF-Right-3",screen.tileSize, screen.tileSize);
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