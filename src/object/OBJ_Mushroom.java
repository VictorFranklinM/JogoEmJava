package object;

import entity.Entity;
import main.Screen;

public class OBJ_Mushroom extends Entity{
	
	public static final String objName = "Mushroom";
	
	Screen screen;
	
	public OBJ_Mushroom(Screen screen) {
		super(screen);
		this.screen = screen;
		type = typeConsumable;
		spriteNum = 2;
		stackable = true;
		
		name = objName;
		value = 1;
		description = "[" +name+ "]\n"
					+ "An item that may be used to heal\nyou. Not as useful as medicine, but\nit still can save your skin.\n"
					+ "+"+value+" hp!";
		price = 2;
		
		down1 = setup("/objects/mushroom",Screen.tileSize, Screen.tileSize);
		down2 = setup("/objects/mushrooms",Screen.tileSize, Screen.tileSize);
		
		collisionArea.x = 7*Screen.scale;
        collisionArea.y = 7*Screen.scale;
        collisionArea.width = 2*Screen.scale;
        collisionArea.height = 2*Screen.scale;

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
        
        setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You eat the "+name+"!\n"+"You recovered "+value+" hp!";
	}
	
	public boolean use(Entity entity) {
		screen.playSFX(1);
		startDialogue(this,0);
		entity.hp += value;
		if(screen.player.hp > screen.player.maxHP) {
			screen.player.hp = screen.player.maxHP;
		}
		return true;
	}
}