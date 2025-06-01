package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;

public class OBJ_MagaBlue extends Entity{

	
	public OBJ_MagaBlue(Screen screen) {
		super(screen);
		type = typeMaga;
		
		name = "Ice  Magatama";
		
		attackValue = 3;
		defenseValue = 6;
		description = "[" +name+ "]\n"
					+ "A  Magatama  of  the  ice  attribute!\n+"
					+ attackValue + "  attack!\n+"
					+ defenseValue + "  defense!\n"
					+ "Let's  you  enter  the  electric  realm!\n"
					+ "Let's  you  use  ice  magic!";
		
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