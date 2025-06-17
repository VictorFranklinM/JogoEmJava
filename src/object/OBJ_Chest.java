package object;

import entity.Entity;
import main.Screen;
public class OBJ_Chest extends Entity{
	
	public static final String objName = "Chest";
	
	Screen screen;
	
	public OBJ_Chest(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typeObstacle;
		name = objName;
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
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You need a key to open the chest.";
		dialogues[1][0] = "You open the chest and find a " + loot.name + "!\nYou obtain the " + loot.name + "!";
		dialogues[2][0] = "You open the chest and find a " + loot.name + "!\n...But your inventory is already full!";
		dialogues[3][0] = "It's empty.";
	}
	
	@Override
	public void interact() {

	    if (!unlocked) {
	        startDialogue(this,0);
	        return;
	    }

	    if (!opened) {
	        screen.playSFX(1);
	        
	        if (screen.player.canObtainItem(loot) == false) {
	        	startDialogue(this,2);
	        } else {
	        	startDialogue(this,1);
	          
	            spriteNum = 2;
	            opened = true;
	        }
	    } else {
	    	startDialogue(this,3);
	    }
	}
}