package object;

import entity.Entity;
import main.Screen;
public class OBJ_Chest extends Entity{
	
	Screen screen;
	
	public OBJ_Chest(Screen screen) {
		super(screen);
		this.screen = screen;
		
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
	
	public void setLoot(Entity loot) {
		this.loot = loot;
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

	        if (screen.player.canObtainItem(loot) == false) {
	            sb.append("\n...But your inventory is already full!");
	        } else {
	            sb.append("\nYou obtain the " + loot.name + "!");
	          
	            spriteNum = 2;
	            opened = true;
	        }

	        screen.ui.currentSpeechLine = sb.toString();
	    } else {
	        screen.ui.currentSpeechLine = "It's empty.";
	    }
	}

}