package object;

import entity.Entity;
import main.Screen;

public class OBJ_ManaBottle extends Entity{
	
	Screen screen;
	
	public OBJ_ManaBottle(Screen screen) {
		super(screen);
		type = typeConsumable;
		
		name = "Mana Bottle";
		value = 2;
		description = "[" +name+ "]\n"
					+ "An item that may be used to\nrestore your mana on difficult\nsituations. It's wise to always keep\n one.\n"
					+ "+"+value+" mp!";
		price = 4;
		
		down1 = setup("/objects/manaBottle",Screen.tileSize, Screen.tileSize);
		
		collisionArea.x = (3 * Screen.scale);
        collisionArea.y = (3 * Screen.scale);
        collisionArea.width = (10 * Screen.scale);
        collisionArea.height = (10 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
	}
	
	public void use(Entity entity) {
		screen.playSFX(1);
		screen.gameState = Screen.dialogueState;
		screen.ui.currentSpeechLine = "You use the "+name+"!\n"
									+"You recovered "+value+" mp!";
				screen.player.mana += value;
		
		if(screen.player.mana > screen.player.maxMana) {
			screen.player.mana = screen.player.maxMana;
		}
	}
}