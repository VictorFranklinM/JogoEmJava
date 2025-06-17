package object;

import entity.Entity;
import main.Screen;
public class OBJ_SpecialKey extends Entity{
	
	public static final String objName = "Special Key";
	
	Screen screen;
	
	public OBJ_SpecialKey(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeConsumable;
		stackable = true;
		
		name = objName;
		down1 = setup("/objects/specialKey",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (4 * Screen.scale);
		collisionArea.y = (1 * Screen.scale);
		collisionArea.width = (7 * Screen.scale);
		collisionArea.height = (14 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		description = "[" +name+ "]\n"
					+ "It's a key!\nIt looks kinda different...\nMaybe you can use it to unlock\nan important door.";
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "There's nothing nearby to use this.";
		dialogues[1][0] = "You unlocked the door with the key!";
	}
	
	public boolean use(Entity entity) {

		int objIndex = getDetected(entity, screen.obj, "Door");

		if(objIndex != 999 && !((OBJ_Door)screen.obj[Screen.currentMap][objIndex]).unlocked) {
			startDialogue(this,1);
			screen.obj[Screen.currentMap][objIndex] = null;
			screen.playSFX(1);
			return true;
		}
		else {
			startDialogue(this,0); 
			return false;
		}
	}
}