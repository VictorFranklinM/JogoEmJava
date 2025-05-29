package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;
public class OBJ_HP extends Entity{
	
	public OBJ_HP(Screen screen) {
		super(screen);
		image = setup("/ui/HP_Full");
		image2 = setup("/ui/HP_None");
		name = "HP";
		
	}
}