package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_MagaGreen extends SuperObject{
	
	public OBJ_MagaGreen() {
		name = "Green Magatama";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/greenmaga.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}