package entity;

import java.awt.Rectangle;

import main.Screen;

public class NPC_Manikin extends Entity{
	
	private final int defaultSpeed = 1;
	//private int specialLine = num;
	//private int maxTextLines = num; // CHANGE IF YOU CHANGE THE DEFAULT TEXT QUANTITY		
			
	public NPC_Manikin(Screen screen) {
		super(screen);
		facing = "down";
		speed = defaultSpeed;
		dialogueSet = -1;
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
	
	@Override
	public void speak() {
		screen.ui.setFace(face);
		super.facePlayer();
		super.startDialogue(this, dialogueSet);
		dialogueSet++;
		if (dialogues[dialogueSet][0] == null) {
			dialogueSet--;
		}
	}
	
	public void getImage() {
		face = setup("/npc/manikin/Manikin-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/manikin/Manikin-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/manikin/Manikin-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/manikin/Manikin-Up-3",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/manikin/Manikin-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/manikin/Manikin-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/manikin/Manikin-Down-3",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/manikin/Manikin-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/manikin/Manikin-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/manikin/Manikin-Left-3",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/manikin/Manikin-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/manikin/Manikin-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/manikin/Manikin-Right-3",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		dialogues[0][0] = "Placeholder";
		dialogues[0][1] = "Placeholder";
		
		dialogues[1][0] = "Placeholder";
		dialogues[1][1] = "I'm just a placeholder.\nGo away please.";
	}
	
	@Override
	public void setAction() {
		movementLogic(45);
	}
}