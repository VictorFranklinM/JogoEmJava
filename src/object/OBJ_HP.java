package object;

import entity.Entity;
import main.Screen;
public class OBJ_HP extends Entity{
	
	public static final String objName = "HP";
	
	public OBJ_HP(Screen screen) {
		super(screen);
		image = setup("/ui/HP_Full",Screen.tileSize, Screen.tileSize);
		image2 = setup("/ui/HP_None",Screen.tileSize, Screen.tileSize);
		name = objName;
		
	}
}