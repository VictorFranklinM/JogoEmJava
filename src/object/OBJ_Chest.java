package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class OBJ_Chest extends SuperObject{
	Screen screen;
	
	public OBJ_Chest(Screen screen) {
		this.screen = screen;
		
		name = "Chest";
		
		collisionArea.x = (1 * screen.scale);
		collisionArea.y = (3 * screen.scale);
		collisionArea.width = (14 * screen.scale);
		collisionArea.height = (13 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Invalid image path.");
		}
		collision = true;
	}
}