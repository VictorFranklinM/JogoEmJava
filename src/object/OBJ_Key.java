package object;

import entity.Entity;
import main.Screen;
public class OBJ_Key extends Entity{
	
	Screen screen;
	
	public OBJ_Key(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeConsumable;
		
		name = "Key";
		down1 = setup("/objects/key",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (4 * Screen.scale);
		collisionArea.y = (1 * Screen.scale);
		collisionArea.width = (7 * Screen.scale);
		collisionArea.height = (14 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}
	
	public boolean use(Entity entity) {
		screen.gameState = Screen.dialogueState;

		int objIndex = getDetected(entity, screen.obj, "Chest");

		if(objIndex != 999 && screen.obj[Screen.currentMap][objIndex].spriteNum == 1) {
			screen.ui.currentSpeechLine = "You unlocked the chest with the key!";
			((OBJ_Chest) screen.obj[Screen.currentMap][objIndex]).unlocked = true;
			screen.playSFX(1);
			return true;
		}
		else {
			screen.ui.currentSpeechLine = "There's nothing nearby to use this.";
			return false;
		}
	}

}