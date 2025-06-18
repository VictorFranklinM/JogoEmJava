package main;

import entity.Entity;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_IronDoor;
import object.OBJ_Key;
import object.OBJ_Lever;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;
import object.OBJ_Obelisk;
import object.OBJ_SpecialKey;
import object.OVR_Sparkle;

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
		screen.obj[mapNum][i] = new OBJ_Obelisk(screen);
		screen.obj[mapNum][i].worldX = 37 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 33 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Key(screen);
		screen.obj[mapNum][i].worldX = 30 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 37 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Key(screen);
		screen.obj[mapNum][i].worldX = 30 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 37 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Key(screen);
		screen.obj[mapNum][i].worldX = 30 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 37 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Chest(screen);
		screen.obj[mapNum][i].setLoot(new OBJ_ManaBottle(screen));
		screen.obj[mapNum][i].worldX = 29 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 31 * Screen.tileSize;
		i++;
		
		mapNum = 1;
		i = 0;
		
		screen.obj[mapNum][i] = new OBJ_MagaGreen(screen);
		screen.obj[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 32 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_SpecialKey(screen);
		screen.obj[mapNum][i].worldX = 17 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 32 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Door(screen);
		screen.obj[mapNum][i].worldX = 18 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 32 * Screen.tileSize;
		i++;
		screen.overlay[mapNum][i] = new OVR_Sparkle(screen);
		screen.overlay[mapNum][i].worldX = 17 * Screen.tileSize;
		screen.overlay[mapNum][i].worldY = 25 * Screen.tileSize;
		i++;
		
		mapNum = 2;
		i = 0;

		screen.obj[mapNum][i] = new OBJ_IronDoor(screen, 1);
		screen.obj[mapNum][i].worldX = 16 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 32 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_IronDoor(screen, 1);
		((OBJ_IronDoor) screen.obj[mapNum][i]).setOpenDoor();
		screen.obj[mapNum][i].worldX = 19 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 28 * Screen.tileSize;
		i++;
		screen.obj[mapNum][i] = new OBJ_Lever(screen, 1);
		screen.obj[mapNum][i].worldX = 16 * Screen.tileSize;
		screen.obj[mapNum][i].worldY = 24 * Screen.tileSize;
		i++;
	}
	public void getImage(){
		
	}
	public void setAction() {
		
	}
}
