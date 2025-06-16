package entity;

import java.awt.Rectangle;

import main.Screen;
import object.OBJ_Key;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;

public class NPC_Merchant extends Entity{
	
	private final int defaultSpeed = 0;
	//private int specialLine = numero;
	//private int maxTextLines = numero; // mudar se mudar a quantidade de texto acessado de forma normal
			
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
		face = setup("/npc/Merchant-Face",Screen.tileSize, Screen.tileSize);
		up1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		up2 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		up3 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down2 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		down3 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left2 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		left3 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right1 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right2 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
		right3 = setup("/npc/Merchant-Down-1",Screen.tileSize, Screen.tileSize);
	}
	
	public void setTextLines() {
		dialogues[0][0] = "Hee-lo! Lamp oil? Rope? Bombs? You want it?\nIt's yours, my friend! As long as you have\nenough Macca -Ho!";
		dialogues[1][0] = "Thank you for your patronage!";
		dialogues[2][0] = "Oh noo!\nGuess you're too poor to buy that -Ho!";
		dialogues[3][0] = "Your inventory is full!\nYou should re-Heeee-ally get someone sworn\nto carry your burdens...";
		dialogues[4][0] = "You probably shouldn't go around selling\nsacred stuff...";
	}
	
	public void setItems() {
		inventory.add(new OBJ_ManaBottle(screen));
		inventory.add(new OBJ_Medicine(screen));
		inventory.add(new OBJ_Mushroom(screen));
		inventory.add(new OBJ_Key(screen));
	}
}