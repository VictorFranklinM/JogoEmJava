package object;

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
					+ "Lets you enter the fire realm!\n"
					+ "Lets you use force magic!";
		
		down1 = setup("/objects/greenmaga",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (2 * Screen.scale);
        collisionArea.y = (2 * Screen.scale);
        collisionArea.width = (12 * Screen.scale);
        collisionArea.height = (12 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
	}
}