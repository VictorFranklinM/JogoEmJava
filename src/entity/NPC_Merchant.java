package entity;

import java.awt.Rectangle;

import main.Screen;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;

public class NPC_Merchant extends Entity{
	
	private final int defaultSpeed = 0;
			
	public NPC_Merchant(Screen screen) {
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
		face = setup("/npc/Merchant-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/Merchant-Down-2",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		// MUDAR
		dialogues[0] = "Hee-llo!\nMy name is Jack Frost!";
		dialogues[1] = "I wanna tell you something -ho!";
		dialogues[2] = "I heard that if you collect some magatamas, you\ncan go to another areas -ho!";
		dialogues[3] = "What is a Magatama you say?\nHow could I possibily hee-know!";
	}
	
	public void setItems() {
		inventory.add(new OBJ_ManaBottle(screen));
		inventory.add(new OBJ_Medicine(screen));
		inventory.add(new OBJ_Mushroom(screen));
	}
}