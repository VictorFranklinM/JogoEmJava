package object;

import entity.Entity;
import main.Screen;

public class OBJ_Medicine extends Entity{
	
	public static final String objName = "Medicine";
	
	Screen screen;
	
	public OBJ_Medicine(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeConsumable;
		stackable = true;
		
		name = objName;
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
        
        setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You use the "+name+"!\n" +"You recovered "+value+" hp!";
	}
	
	public boolean use(Entity entity) {
		screen.playSFX(1);
		startDialogue(this,0);
		screen.player.hp += value;	
		
		if(screen.player.hp > screen.player.maxHP) {
			screen.player.hp = screen.player.maxHP;
		}
		return true;
	}
}