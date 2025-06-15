package object;

import entity.Entity;
import main.Screen;
public class OBJ_Obelisk extends Entity{
	
	public static final String objName = "Obelisk";
	
	Screen screen;
	
	public OBJ_Obelisk(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typeObstacle;
		down1 = setup("/objects/obelisk",Screen.tileSize, Screen.tileSize*2);
		name = objName;
		
		collisionArea.x = (0 * Screen.scale);
        collisionArea.y = (0 * Screen.scale);
        collisionArea.width = (16*Screen.scale);
        collisionArea.height = (16*3 *Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        collision = true;
        
	}
	
	public void interact() {
		screen.gameState = Screen.dialogueState;
		if(screen.player.hasMaga > 0) {
			screen.playSFX(1);
			screen.ui.currentSpeechLine = "You are worthy.\nYou can pass.";
			for (int i = 0; i < screen.obj[Screen.currentMap].length; i++) {
	            if (screen.obj[Screen.currentMap][i] == this) {
	                screen.obj[Screen.currentMap][i] = null;
	                break;
	            }
	        }
		}
		else {
			screen.ui.currentSpeechLine = "You need the Force Magatama to pass.";
		}
		
		
	}
}