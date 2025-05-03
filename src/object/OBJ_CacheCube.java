package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class OBJ_CacheCube extends SuperObject{
	Screen screen;
	
	public OBJ_CacheCube(Screen screen) {
		this.screen = screen;
		
		name = "Cache Cube";
		
		collisionArea.x = (3 * screen.scale);
		collisionArea.y = (3 * screen.scale);
		collisionArea.width = (10 * screen.scale);
		collisionArea.height = (10 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/cacheCube.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}