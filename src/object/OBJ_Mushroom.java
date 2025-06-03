package object;

import entity.Entity;
import main.Screen;

public class OBJ_Mushroom extends Entity{

	int value = 1;
	
	public OBJ_Mushroom(Screen screen) {
		super(screen);
		type = typeConsumable;
		spriteNum = 2;
		
		name = "Mushroom";
		description = "[" +name+ "]\n"
					+ "An item that may be used to heal\nyou. Not as useful as medicine, but\nit still can save your skin.\n"
					+ "+"+value+" hp!";
		
		down1 = setup("/objects/mushroom",Screen.tileSize, Screen.tileSize);
		down2 = setup("/objects/mushrooms",Screen.tileSize, Screen.tileSize);
		
		collisionArea.x = (2 * Screen.scale);
        collisionArea.y = (0 * Screen.scale);
        collisionArea.width = (13 * Screen.scale);
        collisionArea.height = (14 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
	}
	
	public void use(Entity entity) {
		screen.playSFX(1);
		screen.gameState = screen.dialogueState;
		screen.ui.currentSpeechLine = "You eat the "+name+"!\n"
									+"You recovered "+value+" hp!";
		entity.hp += value;
		if(screen.player.hp > screen.player.maxHP) {
			screen.player.hp = screen.player.maxHP;
		}
	}
}