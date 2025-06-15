package object;

import entity.Entity;
import main.Screen;
public class OBJ_CacheCube extends Entity{
	
	public static final String objName = "Cache Cube";
	
	Screen screen;
	
	public OBJ_CacheCube(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typeObstacle;
		name = objName;
		
		down1 = setup("/objects/cacheCubeOn",Screen.tileSize, Screen.tileSize);
		down2 = setup("objects/cacheCube", Screen.tileSize, Screen.tileSize);
		
		collisionArea.x = (3 * Screen.scale);
        collisionArea.y = (3 * Screen.scale);
        collisionArea.width = (10 * Screen.scale);
        collisionArea.height = (10 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
        
	}
	
	public void interact() {
		screen.gameState = Screen.dialogueState;
		screen.ui.currentSpeechLine = "You opened the cache cube.";
	}
}