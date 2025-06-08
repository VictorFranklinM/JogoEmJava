package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class OBJ_HealthBar extends SuperObject{
	Screen screen;
	
	public OBJ_HealthBar(Screen screen) {
		this.screen = screen;
		
		name = "Health";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/blankbar.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/lowhpbar.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/semilowhpbar.png"));
			image4 = ImageIO.read(getClass().getResourceAsStream("/objects/midhpbar.png"));
			image5 = ImageIO.read(getClass().getResourceAsStream("/objects/semihighhp.png"));
			image6 = ImageIO.read(getClass().getResourceAsStream("/objects/fullhpbar.png"));
			this.performanceObj.scaleImage(image,Screen.tileSize,Screen.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Invalid image path.");
		}
	}
}

