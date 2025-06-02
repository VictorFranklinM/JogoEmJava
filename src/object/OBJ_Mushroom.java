package object;

import java.io.IOException;
import javax.imageio.ImageIO;

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
					+ "An item that may be used to heal\nyou.\nNot as useful as medicine, but it \nstill can save your skin.\n"
					+ "+"+value+" hp!";
		
		down1 = setup("/objects/mushroom",screen.tileSize, screen.tileSize);
		down2 = setup("/objects/mushrooms",screen.tileSize, screen.tileSize);
		
		collisionArea.x = (2 * screen.scale);
        collisionArea.y = (0 * screen.scale);
        collisionArea.width = (13 * screen.scale);
        collisionArea.height = (14 * screen.scale);

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