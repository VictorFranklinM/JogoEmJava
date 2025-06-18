package object;

import entity.Entity;
import main.Screen;
public class OBJ_Lever extends Entity{
	
	public static final String objName = "Lever";
	
	Screen screen;
	
	public OBJ_Lever(Screen screen) {
		super(screen);
		this.screen = screen;
		
		type = typeObstacle;
		name = objName;
		down1 = setup("/objects/leverUp",Screen.tileSize, Screen.tileSize);
		down2 = setup("/objects/leverDown",Screen.tileSize, Screen.tileSize);
		collisionArea.x = (0 * Screen.scale);
		collisionArea.y = (0 * Screen.scale);
		collisionArea.width = (16 * Screen.scale);
		collisionArea.height = (16 * Screen.scale);
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		collision = true;
		
		setDialogue();
	}
	
	public OBJ_Lever(Screen screen, int openDoorNum) {
		this(screen);
		this.openDoorNum = openDoorNum;	
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You pull the lever down.";
		dialogues[1][0] = "You pull the lever up.";
	}
	
	@Override
	public void interact() {

	    if (!opened) {
	        startDialogue(this,0);
	        spriteNum = 2;
	        opened = true;
	    }

	    else if (opened) {
	        startDialogue(this,1);
	        spriteNum = 1;
	        opened = false;
	    }
	    
	    changeDoorState(this.openDoorNum);
	}
	
	public void changeDoorState(int openDoorNum) {
		for(int i = 0; i < screen.obj[1].length; i++) {
			if(screen.obj[Screen.currentMap][i] != null && screen.obj[Screen.currentMap][i].doorNum == openDoorNum) {
				screen.obj[Screen.currentMap][i].opened = !screen.obj[Screen.currentMap][i].opened;
				screen.playSFX(1);
				if(screen.obj[Screen.currentMap][i].opened) {
					screen.obj[Screen.currentMap][i].spriteNum = 2;
					screen.obj[Screen.currentMap][i].collisionArea.height = 0;
					screen.obj[Screen.currentMap][i].collisionArea.width = 0;
					screen.obj[Screen.currentMap][i].collision = false;
				}
				else if(!screen.obj[Screen.currentMap][i].opened) {
					screen.obj[Screen.currentMap][i].spriteNum = 1;
					screen.obj[Screen.currentMap][i].collisionArea.height = 16 * Screen.scale;
					screen.obj[Screen.currentMap][i].collisionArea.width = 16 * Screen.scale;
					screen.obj[Screen.currentMap][i].collision = true;
				}
			}
		}
	}
}