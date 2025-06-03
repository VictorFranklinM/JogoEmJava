package object;

import entity.Entity;
import main.Screen;
public class OBJ_CacheCube extends Entity{
	
	public OBJ_CacheCube(Screen screen) {
		super(screen);
		down1 = setup("/objects/cacheCube",Screen.tileSize, Screen.tileSize);
		name = "Cache Cube";
		
		collisionArea.x = (3 * Screen.scale);
        collisionArea.y = (3 * Screen.scale);
        collisionArea.width = (10 * Screen.scale);
        collisionArea.height = (10 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
        
	}
}