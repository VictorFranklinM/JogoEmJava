package main;

import object.OBJ_CacheCube;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_Portal;

public class ObjPlacer {

	Screen sc;
	
	public ObjPlacer(Screen screen) {
		this.sc = screen;
	}
	
	public void placeObject() {
			sc.obj[0] = new OBJ_MagaGreen();
			sc.obj[0].worldX = 48 * sc.tileSize;
			sc.obj[0].worldY = 29 * sc.tileSize;
			
			sc.obj[1] = new OBJ_Portal();
			sc.obj[1].worldX = 48 * sc.tileSize;
			sc.obj[1].worldY = 33 * sc.tileSize;
			
			sc.obj[2] = new OBJ_CacheCube();
			sc.obj[2].worldX = 44 * sc.tileSize;
			sc.obj[2].worldY = 29 * sc.tileSize;
	}
}
