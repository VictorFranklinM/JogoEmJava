package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;

public class OBJ_MagaBlue extends Entity{
	
	
	public OBJ_MagaBlue(Screen screen) {
		super(screen);
		
		name = "Magatama";
		down1 = setup("/objects/bluemaga",screen.tileSize, screen.tileSize);
		collisionArea.x = (2 * screen.scale);
		collisionArea.y = (2 * screen.scale);
		collisionArea.width = (12 * screen.scale);
		collisionArea.height = (12 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
	}
}