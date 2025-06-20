package entity;

import java.awt.Rectangle;

import main.Screen;

public class NPC_BlackFrost extends Entity{
	
	private final int defaultSpeed = 2;
	//private int specialLine = num;
	//private int maxTextLines = num; // CHANGE IF YOU CHANGE THE DEFAULT TEXT QUANTITY			
	public NPC_BlackFrost(Screen screen) {
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
	
	public void setTextLines() {
		dialogues[0][0] = "My name is Black Frost -hoo!";
		dialogues[0][1] = "Have you heard about the Magatamas?";
		dialogues[0][2] = "They look like spirals -ho!";
		dialogues[0][3] = "Some people are saying they can even let you\nuse magic-hoo!";
	}
	
	public void getImage() {
		face = setup("/npc/blackfrost/BF-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/blackfrost/BF-Up-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/blackfrost/BF-Up-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/blackfrost/BF-Up-3",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/blackfrost/BF-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/blackfrost/BF-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/blackfrost/BF-Down-3",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/blackfrost/BF-Left-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/blackfrost/BF-Left-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/blackfrost/BF-Left-3",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/blackfrost/BF-Right-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/blackfrost/BF-Right-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/blackfrost/BF-Right-3",Screen.tileSize, Screen.tileSize);

	}
	

	
	@Override
	public void setAction() {
		movementLogic(45);
	}
}