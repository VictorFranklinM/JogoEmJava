package object;

import entity.Entity;
import main.Screen;
public class OBJ_Key extends Entity{
	
	public static final String objName = "Key";
	
	Screen screen;
	
	public OBJ_Key(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeConsumable;
		stackable = true;
		
		name = objName;
		down1 = setup("/objects/key",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (4 * Screen.scale);
		collisionArea.y = (1 * Screen.scale);
		collisionArea.width = (7 * Screen.scale);
		collisionArea.height = (14 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		value = 2;
		description = "[" +name+ "]\n"
					+ "It's a key!\nMaybe you can unlock something\nlike a chest with it.";
		price = 3;
	}
	
	public boolean use(Entity entity) {
		screen.gameState = Screen.dialogueState;

		int objIndex = getDetected(entity, screen.obj, "Chest");

		if(objIndex != 999 && !((OBJ_Chest)screen.obj[Screen.currentMap][objIndex]).unlocked) {
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