package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;

public class OBJ_MagaRed extends Entity{

	
	public OBJ_MagaRed(Screen screen) {
		super(screen);
		type = typeMaga;
		
		name = "Fire  Magatama";
		
		attackValue = 5;
		defenseValue = 3;
		description = "[" +name+ "]\n"
					+ "A  Magatama  of  the  fire  attribute!\n+"
					+ attackValue + "  attack!\n+"
					+ defenseValue + "  defense!\n"
					+ "Let's  you  enter  the  ice  realm!\n"
					+ "Let's  you  use  fire  magic!";
		
		down1 = setup("/objects/redmaga",screen.tileSize, screen.tileSize);
		collisionArea.x = (2 * screen.scale);
        collisionArea.y = (2 * screen.scale);
        collisionArea.width = (12 * screen.scale);
        collisionArea.height = (12 * screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
	}
}