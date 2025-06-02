package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;

public class OBJ_Medicine extends Entity{

	int value = 3;
	
	public OBJ_Medicine(Screen screen) {
		super(screen);
		type = typeConsumable;
		
		name = "Medicine";
		description = "[" +name+ "]\n"
					+ "An item that may be used to heal\nyou on difficult situations.\nIt's wise to always keep one.\n"
					+ "+"+value+" hp!";
		
		down1 = setup("/objects/medicine",screen.tileSize, screen.tileSize);
		
		collisionArea.x = (2 * screen.scale);
        collisionArea.y = (3 * screen.scale);
        collisionArea.width = (12 * screen.scale);
        collisionArea.height = (10 * screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
	}
	
	public void use(Entity entity) {
		screen.playSFX(1);
		screen.gameState = screen.dialogueState;
		screen.ui.currentSpeechLine = "You use the "+name+"!\n"
									+"You recovered "+value+" hp!";
		entity.hp += value;
		if(screen.player.hp > screen.player.maxHP) {
			screen.player.hp = screen.player.maxHP;
		}
	}
}