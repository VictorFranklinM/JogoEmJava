package object;

import entity.Entity;
import main.Screen;

public class OBJ_GreenMacca extends Entity {
	
	public static final String objName = "1 Macca";
	
	Screen screen;
	 
	public OBJ_GreenMacca(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typePickupOnly;
		name = objName;
		value = 1;
		down1 = setup("/objects/greenMacca",Screen.tileSize, Screen.tileSize);
		
		collisionArea.x = (2 * Screen.scale);
        collisionArea.y = (2 * Screen.scale);
        collisionArea.width = (12 * Screen.scale);
        collisionArea.height = (12 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
	}
	
	public boolean use(Entity entity) {
		screen.playSFX(0);
		screen.ui.addMessage("Macca +"+ value);
		screen.player.macca += value;
		return true;
	}
}