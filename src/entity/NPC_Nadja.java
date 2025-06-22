package entity;

import java.awt.Rectangle;

import main.Screen;

public class NPC_Nadja extends Entity{
	
	private final int defaultSpeed = 1;
	private int specialHPLine = 4;
	private int maxTextLines = 3;
			
	public NPC_Nadja(Screen screen) {
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
		dialogueSet++;
		if (screen.player.hp <= screen.player.maxHP / 3) {
			super.startDialogue(this, specialHPLine);
			return;
		}
		if (dialogues[dialogueSet][0] != null) {
			super.startDialogue(this, dialogueSet);
		}
		if (dialogueSet > maxTextLines || dialogues[dialogueSet][0] == null) {
			dialogueSet = maxTextLines;
		}
	}
	
	public void getImage() {
        face = setup("/npc/nadja/Nadja-Face",Screen.tileSize, Screen.tileSize);
        up1 = setup("/npc/nadja/Nadja-Up-1",Screen.tileSize, Screen.tileSize);
        up2 = setup("/npc/nadja/Nadja-Up-2",Screen.tileSize, Screen.tileSize);
        up3 = setup("/npc/nadja/Nadja-Up-3",Screen.tileSize, Screen.tileSize);
        down1 = setup("/npc/nadja/Nadja-Down-1",Screen.tileSize, Screen.tileSize);
        down2 = setup("/npc/nadja/Nadja-Down-2",Screen.tileSize, Screen.tileSize);
        down3 = setup("/npc/nadja/Nadja-Down-3",Screen.tileSize, Screen.tileSize);
        left1 = setup("/npc/nadja/Nadja-Left-1",Screen.tileSize, Screen.tileSize);
        left2 = setup("/npc/nadja/Nadja-Left-2",Screen.tileSize, Screen.tileSize);
        left3 = setup("/npc/nadja/Nadja-Left-3",Screen.tileSize, Screen.tileSize);
        right1 = setup("/npc/nadja/Nadja-Right-1",Screen.tileSize, Screen.tileSize);
        right2 = setup("/npc/nadja/Nadja-Right-2",Screen.tileSize, Screen.tileSize);
        right3 = setup("/npc/nadja/Nadja-Right-3",Screen.tileSize, Screen.tileSize);
    }
	
	public void setTextLines() {
		dialogues[0][0] = "Oh, you're cute! Nice to meet you, I'm Nadja!";
		dialogues[0][1] = "Go deeper inside if you need to heal your injuries\nand save your progress.";
		dialogues[0][3] = "Where? Oh, don't worry, you can't miss it!";
		dialogues[0][3] = "You could say it's a... glimmer of hope~";
		
		dialogues[1][0] = "*Yawn* It's so boring here... Boo.";
		dialogues[1][1] = "Oh? You're back! Here to see me? Hehe~";
		dialogues[1][2] = "Remember, you should always save your\nprogress.";
		dialogues[1][3] = "The other demons will come back when you do,\nbut you should never lose hope!";
		
		dialogues[2][0] = "Someone once told me my name is short for\nhope in their language.";
		dialogues[2][1] = "That time, I vowed to be that person's \"Hope\"!\nBut... I can't remember who it was...";
		
		dialogues[maxTextLines][0] = "*Humming* I should take care not to lose my\nnecklace...";
		
		dialogues[specialHPLine][0] = "Hey...! Oh no, you're all battered!";
		dialogues[specialHPLine][1] = "There's a place deeper inside where you can\nheal your injuries.";
		dialogues[specialHPLine][2] = "You could say it's a... glimmer of hope~";
	}
	
	@Override
	public void setAction() {
		movementLogic(45);
	}
}