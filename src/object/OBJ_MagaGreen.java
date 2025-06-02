package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;

public class OBJ_MagaGreen extends Entity{

	
	public OBJ_MagaGreen(Screen screen) {
		super(screen);
		type = typeMaga;
		
		name = "Force Magatama";
		
		attackValue = 1;
		defenseValue = 1;
		description = "[" +name+ "]\n"
					+ "A  Magatama of the force attribute!\n+"
					+ attackValue + " attack!\n+"
					+ defenseValue + " defense!\n"
					+ "Lets you enter th  fire realm!\n"
					+ "Lets you use force magic!";
		
		down1 = setup("/objects/greenmaga",screen.tileSize, screen.tileSize);
		collisionArea.x = (2 * screen.scale);
        collisionArea.y = (2 * screen.scale);
        collisionArea.width = (12 * screen.scale);
        collisionArea.height = (12 * screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
	}
}