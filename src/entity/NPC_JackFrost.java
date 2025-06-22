package entity;

import java.awt.Rectangle;

import main.Screen;

public class NPC_JackFrost extends Entity{
	
	private final int defaultSpeed = 1;
	private int specialLine = 1;
	private int maxTextLines = 0; // CHANGE IF YOU CHANGE THE DEFAULT TEXT QUANTITY
	public boolean inCutscene = false;
			
	public NPC_JackFrost(Screen screen) {
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
	    
	    if (inCutscene) {
	        if (dialogues[specialLine] != null && dialogues[specialLine][0] != null) {
	            super.startDialogue(this, specialLine);
	        }
	        return;
	    }

	    if (dialogues[dialogueSet] != null && dialogues[dialogueSet][0] != null) {
	        super.startDialogue(this, dialogueSet);
	    }

	    dialogueSet++;
	    if (dialogueSet >= maxTextLines) {
	        dialogueSet = 0;
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
		dialogues[maxTextLines][0] = "Hee-llo!\nMy name is Jack Frost!";
		dialogues[maxTextLines][1] = "I wanna tell you something -ho!";
		dialogues[maxTextLines][2] = "I heard that if you collect some magatamas, you\ncan go to another areas -ho!";
		dialogues[maxTextLines][3] = "What is a Magatama you say?\nHow could I possibily hee-know!";
		
		dialogues[specialLine][0] = "Hee-llo, Hitoshura!";
		dialogues[specialLine][1] = "You are hee-re for Pixie, right? -ho!";
		dialogues[specialLine][2] = "I'm sorry to tell you this, but...";
		dialogues[specialLine][3] = "Your friend Pixie is in another dungeon! -ho!";
	}
	
	@Override
	public void setAction() {
		movementLogic(45);
	}
}