package object;

import entity.Entity;
import main.Screen;

public class OBJ_MagaRed extends Entity{

	Screen screen;
	
	public OBJ_MagaRed(Screen screen) {
		super(screen);
		type = typeMaga;
		
		Magic_Fireball fire = new Magic_Fireball(screen);
		
		name = "Fire Magatama";
		
		attackValue = 5;
		defenseValue = 3;
		description = "["+name+"]\n"
					+ "A Magatama of the fire attribute!\n+"
					+ attackValue + " physical attack!\n+"
					+ defenseValue + " defense!\n"
					+ "[Fire Spell]\n"+fire.attack+" attack!\n"+fire.manaCost+" MP!";
		
		down1 = setup("/objects/redmaga",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (2 * Screen.scale);
        collisionArea.y = (2 * Screen.scale);
        collisionArea.width = (12 * Screen.scale);
        collisionArea.height = (12 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
        knockBackPower = 3;
	}
}