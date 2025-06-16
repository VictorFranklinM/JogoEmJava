package object;

import entity.Entity;
import main.Screen;

public class OBJ_ManaBottle extends Entity{
	
	public static final String objName = "Mana Bottle";
	
	Screen screen;
	
	public OBJ_ManaBottle(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeConsumable;
		stackable = true;
		
		name = objName;
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
        
        setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You use the "+name+"!\n"+"You recovered "+value+" mp!";
	}
	
	public boolean use(Entity entity) {
		screen.playSFX(1);
		startDialogue(this,0);
		screen.player.mana += value;
		
		if(screen.player.mana > screen.player.maxMana) {
			screen.player.mana = screen.player.maxMana;
			
		}
		return true;
	}
}