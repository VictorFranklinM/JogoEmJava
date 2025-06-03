package object;

import entity.Entity;
import main.Screen;
public class OBJ_Chest extends Entity{
	
	public OBJ_Chest(Screen screen) {
		super(screen);
		
		name = "Chest";
		down1 = setup("/objects/chest",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (1 * Screen.scale);
		collisionArea.y = (3 * Screen.scale);
		collisionArea.width = (14 * Screen.scale);
		collisionArea.height = (13 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
	}
}