package entity;

import java.awt.Rectangle;

import main.Screen;

public class NPC_Nadja extends Entity{
	
	private final int defaultSpeed = 1;
			
	public NPC_Nadja(Screen screen) {
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
		face = setup("/npc/Nadja-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		// MUDAR
		dialogues[0] = "Hee-llo!\nMy name is Jack Frost!";
		dialogues[1] = "I wanna tell you something -ho!";
		dialogues[2] = "I heard that if you collect some magatamas, you\ncan go to another areas -ho!";
		dialogues[3] = "What is a Magatama you say?\nHow could I possibily hee-know!";
	}
	
	@Override
	public void setAction() {
		movementLogic();
	}
}