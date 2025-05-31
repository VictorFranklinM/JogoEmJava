package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;
public class OBJ_Mana extends Entity{
	
	public OBJ_Mana(Screen screen) {
		super(screen);
		image = setup("/ui/Mana_Full",screen.tileSize, screen.tileSize);
		image2 = setup("/ui/Mana_None",screen.tileSize, screen.tileSize);
		name = "Mana";
		
	}
}