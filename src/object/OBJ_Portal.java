package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;
public class OBJ_Portal extends Entity{
	
	public OBJ_Portal(Screen screen) {
		super(screen);
		down1 = setup("/objects/portal");
		name = "Portal";
		
		
		collisionArea.x = (0 * screen.scale);
		collisionArea.y = (0 * screen.scale);
		collisionArea.width = (16 * screen.scale);
		collisionArea.height = (16 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
	}
}