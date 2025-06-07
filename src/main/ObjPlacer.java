package main;

import entity.Entity;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;

public class ObjPlacer extends Entity {

	
	public ObjPlacer(Screen screen) {
		super(screen);
}
	
	public void placeObject() {
		int i = 0;
		int mapNum = 0;

		screen.obj[mapNum][i] = new OBJ_MagaRed(screen);
		screen.obj[mapNum][i].worldX = 18 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_MagaBlue(screen);
		screen.obj[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Medicine(screen);
		screen.obj[mapNum][i].worldX = 20 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Mushroom(screen);
		screen.obj[mapNum][i].worldX = 21 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_ManaBottle(screen);
		screen.obj[mapNum][i].worldX = 22 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 34 * Screen.tileSize;
		i++;
		
		mapNum = 1;
		i = 0;
		
		screen.obj[mapNum][i] = new OBJ_MagaGreen(screen);
		screen.obj[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 32 * Screen.tileSize;
		i++;
	}
	public void getImage(){
		
	}
	public void setAction() {
		
	}
}
