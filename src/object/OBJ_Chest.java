package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;
public class OBJ_Chest extends Entity{
	
	public OBJ_Chest(Screen screen) {
		super(screen);
		
		name = "Chest";
		down1 = setup("/objects/chest",screen.tileSize, screen.tileSize);
		collisionArea.x = (1 * screen.scale);
		collisionArea.y = (3 * screen.scale);
		collisionArea.width = (14 * screen.scale);
		collisionArea.height = (13 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
	}
}