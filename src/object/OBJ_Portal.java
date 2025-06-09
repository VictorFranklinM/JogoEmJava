package object;

import entity.Entity;
import main.Screen;
public class OBJ_Portal extends Entity{
	
	Screen screen;
	
	public OBJ_Portal(Screen screen) {
		super(screen);
		down1 = setup("/objects/portal",Screen.tileSize, Screen.tileSize);
		name = "Portal";
		
		
		collisionArea.x = (0 * Screen.scale);
		collisionArea.y = (0 * Screen.scale);
		collisionArea.width = (16 * Screen.scale);
		collisionArea.height = (16 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
	}
}