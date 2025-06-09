package object;

import entity.Entity;
import main.Screen;

public class OBJ_Medicine extends Entity{
	
	Screen screen;
	
	public OBJ_Medicine(Screen screen) {
		super(screen);
		type = typeConsumable;
		
		name = "Medicine";
		value = 3;
		description = "[" +name+ "]\n"
					+ "An item that may be used to heal\nyou on difficult situations.\nIt's wise to always keep one.\n"
					+ "+"+value+" hp!";
		price = 5;
		
		down1 = setup("/objects/medicine",Screen.tileSize, Screen.tileSize);
		
		collisionArea.x = (2 * Screen.scale);
        collisionArea.y = (3 * Screen.scale);
        collisionArea.width = (12 * Screen.scale);
        collisionArea.height = (10 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
	}
	
	public void use(Entity entity) {
		screen.playSFX(1);
		screen.gameState = Screen.dialogueState;
		screen.ui.currentSpeechLine = "You use the "+name+"!\n"
									+"You recovered "+value+" hp!";
				screen.player.hp += value;
		
		if(screen.player.hp > screen.player.maxHP) {
			screen.player.hp = screen.player.maxHP;
		}
	}
}