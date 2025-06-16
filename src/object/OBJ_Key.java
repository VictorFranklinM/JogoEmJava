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
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "There's nothing nearby to use this.";
		dialogues[1][0] = "You unlocked the chest with the key!";
	}
	
	public boolean use(Entity entity) {

		int objIndex = getDetected(entity, screen.obj, "Chest");

		if(objIndex != 999 && !((OBJ_Chest)screen.obj[Screen.currentMap][objIndex]).unlocked) {
			startDialogue(this,1);
			((OBJ_Chest) screen.obj[Screen.currentMap][objIndex]).unlocked = true;
			screen.playSFX(1);
			return true;
		}
		else {
			startDialogue(this,0); 
			return false;
		}
	}

}