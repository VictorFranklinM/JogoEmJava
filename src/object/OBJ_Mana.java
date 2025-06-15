package object;

import entity.Entity;
import main.Screen;
public class OBJ_Mana extends Entity{
	
	public static final String objName = "Mana";
	
	public OBJ_Mana(Screen screen) {
		super(screen);
		image = setup("/ui/Mana_Full",Screen.tileSize, Screen.tileSize);
		image2 = setup("/ui/Mana_None",Screen.tileSize, Screen.tileSize);
		name = objName;
		
	}
}