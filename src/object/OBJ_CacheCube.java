package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;
public class OBJ_CacheCube extends Entity{
	
	public OBJ_CacheCube(Screen screen) {
		super(screen);
		down1 = setup("/objects/cacheCube");
		name = "Cache Cube";
		
		collisionArea.x = (3 * screen.scale);
        collisionArea.y = (3 * screen.scale);
        collisionArea.width = (10 * screen.scale);
        collisionArea.height = (10 * screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
	}
}