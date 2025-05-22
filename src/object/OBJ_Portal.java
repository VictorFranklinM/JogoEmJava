package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class OBJ_Portal extends SuperObject{
	Screen screen;
	
	public OBJ_Portal(Screen screen) {
		this.screen = screen;
		
		name = "Portal";
		
		collisionArea.x = (0 * screen.scale);
		collisionArea.y = (0 * screen.scale);
		collisionArea.width = (16 * screen.scale);
		collisionArea.height = (16 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/portal.png"));
			this.performanceObj.scaleImage(image, screen.tileSize, screen.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Invalid image path.");
		}
		collision = true;
	}
}