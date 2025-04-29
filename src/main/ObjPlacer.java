package main;

import object.OBJ_Magatama;

public class ObjPlacer {

	Screen sc;
	
	public ObjPlacer(Screen screen) {
		this.sc = screen;
	}
	
	public void placeObject() {
			sc.obj[0] = new OBJ_Magatama();
			sc.obj[0].worldX = 48 * sc.tileSize;
			sc.obj[0].worldY = 29 * sc.tileSize;
			
			sc.obj[1] = new OBJ_Magatama();
			sc.obj[1].worldX = 48 * sc.tileSize;
			sc.obj[1].worldY = 33 * sc.tileSize;
			
			sc.obj[2] = new OBJ_Magatama();
			sc.obj[2].worldX = 48 * sc.tileSize;
			sc.obj[2].worldY = 34 * sc.tileSize;
	}
}
