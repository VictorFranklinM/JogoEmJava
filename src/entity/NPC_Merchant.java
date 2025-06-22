package entity;

import java.awt.Rectangle;

import main.Screen;
import object.OBJ_Key;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;

public class NPC_Merchant extends Entity{
	
	private final int defaultSpeed = 0;
	//private int specialLine = num;
	//private int maxTextLines = num; // CHANGE IF YOU CHANGE THE DEFAULT TEXT QUANTITY		
			
	public NPC_Merchant(Screen screen) {
		super(screen);
		facing = "down";
		speed = defaultSpeed;
		getImage();
		setTextLines();
		setItems();
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
		screen.gameState = Screen.tradeState;
		screen.ui.npc = this;
		dialogueSet++;
		if (dialogues[dialogueSet][0] == null) {
			dialogueSet--;
		}
	}
	
	public void getImage() {
		face = setup("/npc/merchant/Merchant-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/merchant/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		dialogues[0][0] = "Ooh, well look who the nekomata dragged in!\nLooking for the hottest stuff in town? It's yours,\nmy friend...! If you have enough Macca, that is~";
		dialogues[1][0] = "Thanks for spending so much, handsome!\nCome again a lot~";
		dialogues[2][0] = "Oh noo!\nGuess you're too poor to buy that~";
		dialogues[3][0] = "Your inventory is full!\nYou know, you should reeeeally get someone\nsworn to carry your burdens...";
		dialogues[4][0] = "Oh, dear... You probably shouldn't go around\nselling sacred stuff like this...";
	}
	
	public void setItems() {
		inventory.add(new OBJ_ManaBottle(screen));
		inventory.add(new OBJ_Medicine(screen));
		inventory.add(new OBJ_Mushroom(screen));
		inventory.add(new OBJ_Key(screen));
	}
}