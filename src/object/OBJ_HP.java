package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class OBJ_HP extends SuperObject{
	Screen screen;
	
	public OBJ_HP(Screen screen) {
		this.screen = screen;
		
		name = "HP";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/ui/HP_Full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/ui/HP_None.png"));
			
			image = performanceObj.scaleImage(image, screen.tileSize, screen.tileSize);
			image2 = performanceObj.scaleImage(image2, screen.tileSize, screen.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Invalid image path.");
		}
	}
}