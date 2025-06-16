package object;

import entity.Entity;
import main.Screen;
public class OBJ_Door extends Entity{
	
	public static final String objName = "Door";
	
	Screen screen;
	
	public OBJ_Door(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typeObstacle;
		name = objName;
		down1 = setup("/objects/door",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (0 * Screen.scale);
		collisionArea.y = (0 * Screen.scale);
		collisionArea.width = (16 * Screen.scale);
		collisionArea.height = (16 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
		
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You need a key to open the door.";
	}
	
	@Override
	public void interact() {

	    if (!unlocked) {
	        startDialogue(this,0);
	        return;
	    }
	}

}