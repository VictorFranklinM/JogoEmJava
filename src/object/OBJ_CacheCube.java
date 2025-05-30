package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;
public class OBJ_CacheCube extends Entity{
	
	public OBJ_CacheCube(Screen screen) {
		super(screen);
		down1 = setup("/objects/cacheCube");
		name = "Cache Cube";
		
		
	}
}