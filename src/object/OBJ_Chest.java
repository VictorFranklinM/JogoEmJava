package object;

import entity.Entity;
import main.Screen;
public class OBJ_Chest extends Entity{
	
	Screen screen;
	Entity loot;
	boolean opened = false;
	public boolean unlocked = false;
	
	public OBJ_Chest(Screen screen, Entity loot) {
		super(screen);
		this.screen = screen;
		this.loot = loot;
		
		type = typeObstacle;
		name = "Chest";
		down1 = setup("/objects/chest",Screen.tileSize, Screen.tileSize);
		down2 = setup("/objects/chestOpen",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (1 * Screen.scale);
		collisionArea.y = (3 * Screen.scale);
		collisionArea.width = (14 * Screen.scale);
		collisionArea.height = (13 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
	}
	
	@Override
	public void interact() {
	    screen.gameState = Screen.dialogueState;

	    if (!unlocked) {
	        screen.ui.currentSpeechLine = "You need a key to open the chest.";
	        return;
	    }

	    if (!opened) {
	        screen.playSFX(1);
	        StringBuilder sb = new StringBuilder();
	        sb.append("You open the chest and find a " + loot.name + "!");

	        if (screen.player.inventory.size() == screen.player.inventorySize) {
	            sb.append("\n...But your inventory is already full!");
	        } else {
	            sb.append("\nYou obtain the " + loot.name + "!");
	            screen.player.inventory.add(loot);
	            spriteNum = 2;
	            opened = true;
	        }

	        screen.ui.currentSpeechLine = sb.toString();
	    } else {
	        screen.ui.currentSpeechLine = "It's empty.";
	    }
	}

}