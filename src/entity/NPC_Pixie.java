package entity;

import java.awt.Rectangle;

import main.Screen;

public class NPC_Pixie extends Entity{
	
	private final int defaultSpeed = 0;
	//private int specialLine = num;
	//private int maxTextLines = num; // CHANGE IF YOU CHANGE THE DEFAULT TEXT QUANTITY		
			
	public NPC_Pixie(Screen screen) {
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
		face = setup("/npc/jackfrost/JF-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/jackfrost/JF-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/jackfrost/JF-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/jackfrost/JF-Up-3",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/jackfrost/JF-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/jackfrost/JF-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/jackfrost/JF-Down-3",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/jackfrost/JF-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/jackfrost/JF-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/jackfrost/JF-Left-3",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/jackfrost/JF-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/jackfrost/JF-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/jackfrost/JF-Right-3",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		dialogues[0][0] = "Hitoshura!";
		dialogues[0][1] = "I knew you were coming!";
		dialogues[0][2] = "Thank you!";
		dialogues[0][3] = "But... I'm not here anymore...";
	}
	
//	@Override
//	public void setAction() {
//		movementLogic(45);
//	}
}