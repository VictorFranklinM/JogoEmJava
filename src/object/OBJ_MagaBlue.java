package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_MagaBlue extends SuperObject{
	
	public OBJ_MagaBlue() {
		name = "Blue Magatama";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/bluemaga.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}