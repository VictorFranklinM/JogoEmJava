package object;

import entity.Entity;
import main.Screen;

public class OBJ_MagaBlue extends Entity{

	
	public OBJ_MagaBlue(Screen screen) {
		super(screen);
		type = typeMaga;
		
		Magic_Icicle ice = new Magic_Icicle(screen);
		
		name = "Ice Magatama";
		
		attackValue = 3;
		defenseValue = 6;
		description = "[" +name+ "]\n"
					+ "A Magatama of the ice attribute!\n+"
					+ attackValue+" physical attack!\n+"
					+ defenseValue+" defense!\n"
					+ "[Ice Spell]\n"+ice.attack+" attack!\n"+ice.manaCost+" MP!";
		
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