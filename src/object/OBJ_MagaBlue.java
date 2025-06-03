package object;

import entity.Entity;
import main.Screen;

public class OBJ_MagaBlue extends Entity{

	
	public OBJ_MagaBlue(Screen screen) {
		super(screen);
		type = typeMaga;
		
		name = "Ice Magatama";
		
		attackValue = 3;
		defenseValue = 6;
		description = "[" +name+ "]\n"
					+ "A Magatama of the ice attribute!\n+"
					+ attackValue+" attack!\n+"
					+ defenseValue+" defense!\n"
					+ "Lets you enter the electric realm!\n"
					+ "Lets you use ice magic!";
		
		down1 = setup("/objects/bluemaga",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (2 * Screen.scale);
        collisionArea.y = (2 * Screen.scale);
        collisionArea.width = (12 * Screen.scale);
        collisionArea.height = (12 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
	}
}