package main;

import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;

public class ObjPlacer {

	Screen sc;
	
	public ObjPlacer(Screen screen) {
		this.sc = screen;
	}
	
	public void placeObject() {
			sc.obj[0] = new OBJ_MagaGreen();
			sc.obj[0].worldX = 48 * sc.tileSize;
			sc.obj[0].worldY = 29 * sc.tileSize;
			
			sc.obj[1] = new OBJ_MagaRed();
			sc.obj[1].worldX = 48 * sc.tileSize;
			sc.obj[1].worldY = 33 * sc.tileSize;
			
			sc.obj[2] = new OBJ_MagaBlue();
			sc.obj[2].worldX = 48 * sc.tileSize;
			sc.obj[2].worldY = 34 * sc.tileSize;
	}
}
