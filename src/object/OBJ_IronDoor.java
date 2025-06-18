package object;

import entity.Entity;
import main.Screen;
public class OBJ_IronDoor extends Entity{
	
	public static final String objName = "Iron Door";
	
	Screen screen;
	
	public OBJ_IronDoor(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typeObstacle;
		name = objName;
		down1 = setup("/objects/ironDoor",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (0 * Screen.scale);
		collisionArea.y = (0 * Screen.scale);
		collisionArea.width = (16 * Screen.scale);
		collisionArea.height = (16 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
		
		setDialogue();
		
	}
	
	public OBJ_IronDoor(Screen screen, int doorNum) {
		this(screen);
		this.doorNum = doorNum;		
	}
	
	public void setDialogue() {
		dialogues[0][0] = "This door looks too heavy to be opened with a key.\nMaybe there's a mechanism that can open it.";
	}
	
	@Override
	public void interact() {
		startDialogue(this,0);
		return;
	}
	
	public void setOpenDoor() {
		opened = true;
		spriteNum = 2;
		collisionArea.height = 0;
		collisionArea.width = 0;
		collision = false;
	}
}