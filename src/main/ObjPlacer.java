package main;

import entity.Entity;
import object.OBJ_CacheCube;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_Portal;

public class ObjPlacer extends Entity {

	
	public ObjPlacer(Screen screen) {
		super(screen);
}
	
	public void placeObject() {
		screen.obj[0] = new OBJ_MagaGreen(screen);
		screen.obj[0].worldX = 48 * screen.tileSize;
		screen.obj[0].worldY = 29 * screen.tileSize;
			
		screen.obj[1] = new OBJ_Portal(screen);
		screen.obj[1].worldX = 48 * screen.tileSize;
		screen.obj[1].worldY = 33 * screen.tileSize;
			
		screen.obj[2] = new OBJ_CacheCube(screen);
		screen.obj[2].worldX = 44 * screen.tileSize;
		screen.obj[2].worldY = 29 * screen.tileSize;
	}
}
