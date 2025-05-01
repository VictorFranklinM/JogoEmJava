package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_CacheCube extends SuperObject{
	
	public OBJ_CacheCube() {
		name = "Cache Cube";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/cacheCube.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}