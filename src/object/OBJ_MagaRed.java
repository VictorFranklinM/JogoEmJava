package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_MagaRed extends SuperObject{
	
	public OBJ_MagaRed() {
		name = "Red Magatama";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/redmaga.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}