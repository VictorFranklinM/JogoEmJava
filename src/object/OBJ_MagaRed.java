package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class OBJ_MagaRed extends SuperObject{
	Screen screen;
	
	public OBJ_MagaRed(Screen screen) {
		this.screen = screen;
		
		name = "Magatama";
		
		collisionArea.x = (2 * screen.scale);
		collisionArea.y = (2 * screen.scale);
		collisionArea.width = (12 * screen.scale);
		collisionArea.height = (12 * screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/redmaga.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}