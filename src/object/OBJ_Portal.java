package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Portal extends SuperObject{
	
	public OBJ_Portal() {
		name = "Portal";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/portal.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}