package main;

import entity.Entity;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;

public class ObjPlacer extends Entity {

	
	public ObjPlacer(Screen screen) {
		super(screen);
}
	
	public void placeObject() {
		screen.obj[0] = new OBJ_MagaGreen(screen);
		screen.obj[0].worldX = 48 * Screen.tileSize;
		screen.obj[0].worldY = 29 * Screen.tileSize;
			
		screen.obj[1] = new OBJ_MagaRed(screen);
		screen.obj[1].worldX = 48 * Screen.tileSize;
		screen.obj[1].worldY = 33 * Screen.tileSize;
			
		screen.obj[2] = new OBJ_MagaBlue(screen);
		screen.obj[2].worldX = 44 * Screen.tileSize;
		screen.obj[2].worldY = 29 * Screen.tileSize;
		
		screen.obj[3] = new OBJ_Medicine(screen);
		screen.obj[3].worldX = 42 * Screen.tileSize;
		screen.obj[3].worldY = 34 * Screen.tileSize;
		
		screen.obj[4] = new OBJ_Mushroom(screen);
		screen.obj[4].worldX = 45 * Screen.tileSize;
		screen.obj[4].worldY = 30 * Screen.tileSize;
	}
	public void getImage(){
		
	}
	public void setAction() {
		
	}
}
