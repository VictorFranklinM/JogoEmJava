package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Magatama extends SuperObject{
	
	public OBJ_Magatama() {
		name = "Magatama";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/magatama.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
