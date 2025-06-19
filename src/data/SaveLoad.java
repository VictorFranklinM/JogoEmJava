package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.Screen;
import object.OBJ_Portal;

public class SaveLoad {
	
	Screen screen;
	
	public SaveLoad(Screen screen) {
		this.screen = screen;
	}
	
	public void save() {
		try{
			@SuppressWarnings("resource")
			ObjectOutputStream ooutstr = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage datastorage = new DataStorage();
			
			// Player Stats
			datastorage.level = screen.player.level;
			datastorage.maxHP = screen.player.maxHP;
			datastorage.hp = screen.player.hp;
			datastorage.maxMana = screen.player.maxMana;
			datastorage.mana = screen.player.mana;
			datastorage.strenght = screen.player.strenght;
			datastorage.dexterity = screen.player.dexterity;
			datastorage.exp = screen.player.exp;
			datastorage.nextLevelExp = screen.player.nextLevelExp;
			datastorage.macca = screen.player.macca;
			datastorage.worldX = screen.player.worldX;
			datastorage.worldY = screen.player.worldY;
			datastorage.currentMap = Screen.currentMap;
			datastorage.respawnMap = screen.player.respawnMap;
			datastorage.respawnX = screen.player.respawnX;
			datastorage.respawnY = screen.player.respawnY;
			datastorage.hasMaga = screen.player.hasMaga;
			
			// Inventory
			for (int i = 0; i < screen.player.inventory.size(); i++) {
				datastorage.itemNames.add(screen.player.inventory.get(i).name);
				datastorage.itemAmounts.add(screen.player.inventory.get(i).amount);
			}
			
			// Magatama
			if(screen.player.currentMagatama != null) {
				datastorage.currentMagatamaSlot = screen.player.getMagatamaSlot();
				datastorage.projectile = screen.player.projectile.name;
			}
			else {
				datastorage.currentMagatamaSlot = screen.player.inventorySize+1;
			}
			
			// Map Objects
			datastorage.mapObjectNames = new String[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectWorldX = new int[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectWorldY = new int[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectCollision = new boolean[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectLootNames = new String[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectOpened = new boolean[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectUnlocked = new boolean[Screen.maxMap][screen.obj[1].length];
			datastorage.mapDoorNum = new int[Screen.maxMap][screen.obj[1].length];
			datastorage.mapOpenDoorNum = new int[Screen.maxMap][screen.obj[1].length];
			
			for(int mapNum = 0; mapNum < Screen.maxMap; mapNum++) {
				for(int i = 0; i < screen.obj[1].length; i++) {
					if(screen.obj[mapNum][i] == null) {
						datastorage.mapObjectNames[mapNum][i] = "N/A";
					}
					else {
						datastorage.mapObjectNames[mapNum][i] = screen.obj[mapNum][i].name;
						datastorage.mapObjectWorldX[mapNum][i] = screen.obj[mapNum][i].worldX;
						datastorage.mapObjectWorldY[mapNum][i] = screen.obj[mapNum][i].worldY;
						if(screen.obj[mapNum][i].loot != null) {
							datastorage.mapObjectLootNames[mapNum][i] = screen.obj[mapNum][i].loot.name;
						}
						datastorage.mapObjectCollision[mapNum][i] = screen.obj[mapNum][i].collision;
						datastorage.mapDoorNum[mapNum][i] = screen.obj[mapNum][i].doorNum;
						datastorage.mapOpenDoorNum[mapNum][i] = screen.obj[mapNum][i].openDoorNum;
						datastorage.mapObjectUnlocked[mapNum][i] = screen.obj[mapNum][i].unlocked;
						datastorage.mapObjectOpened[mapNum][i] = screen.obj[mapNum][i].opened;
					}
				}
			}
			
			// Write DataStorage Object
			ooutstr.writeObject(datastorage);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Save Exception!");
		}
	}
	
	public void load() {
		try {
			@SuppressWarnings("resource")
			ObjectInputStream oinpstr = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			// Read DataStorage File
			DataStorage datastorage = (DataStorage)oinpstr.readObject();
			
			// Player Stats
			screen.player.level = datastorage.level;
			screen.player.maxHP = datastorage.maxHP;
			screen.player.hp = datastorage.hp;
			screen.player.maxMana = datastorage.maxMana;
			screen.player.mana = datastorage.mana;
			screen.player.strenght = datastorage.strenght;
			screen.player.dexterity = datastorage.dexterity;
			screen.player.exp = datastorage.exp;
			screen.player.nextLevelExp = datastorage.nextLevelExp;
			screen.player.macca = datastorage.macca;
			screen.player.worldX = datastorage.worldX;
			screen.player.worldY = datastorage.worldY;
			screen.player.hasMaga = datastorage.hasMaga;
			Screen.currentMap = datastorage.currentMap;
			screen.player.respawnMap = datastorage.respawnMap;
			screen.player.respawnX = datastorage.respawnX;
			screen.player.respawnY = datastorage.respawnY;
			
			// Inventory
			screen.player.inventory.clear();
			for(int i = 0; i < datastorage.itemNames.size(); i++) {
				Entity loadedItem = screen.eGenerator.getObject(datastorage.itemNames.get(i));

				if (loadedItem != null) {
					screen.player.inventory.add(loadedItem);
					screen.player.inventory.get(i).amount = datastorage.itemAmounts.get(i);
				} else {
					System.out.println("Objeto não reconhecido: " + datastorage.itemNames.get(i));
					Entity placeholder = new OBJ_Portal(screen);
				    screen.player.inventory.add(placeholder);
				    screen.player.inventory.get(i).amount = datastorage.itemAmounts.get(i);
				}
			}
			
			// Magatama
			if(datastorage.currentMagatamaSlot != screen.player.inventorySize+1) {
				screen.player.currentMagatama = screen.player.inventory.get(datastorage.currentMagatamaSlot);
				screen.player.projectile = screen.eGenerator.getProjectile(datastorage.projectile);
				screen.player.getAttack();
				screen.player.getDefense();
			}
			
			// Map Objects
			for(int mapNum = 0; mapNum < Screen.maxMap; mapNum++) {
				for(int i = 0; i < screen.obj[1].length; i++) {
					if(datastorage.mapObjectNames[mapNum][i].equals("N/A")) {
						screen.obj[mapNum][i] = null;
					}
					else {
						screen.obj[mapNum][i] = screen.eGenerator.getObject(datastorage.mapObjectNames[mapNum][i]);
						screen.obj[mapNum][i].worldX = datastorage.mapObjectWorldX[mapNum][i];
						screen.obj[mapNum][i].worldY = datastorage.mapObjectWorldY[mapNum][i];
						if(datastorage.mapObjectLootNames[mapNum][i] != null) {
							screen.obj[mapNum][i].setLoot(screen.eGenerator.getObject(datastorage.mapObjectLootNames[mapNum][i]));
						}
						screen.obj[mapNum][i].collision = datastorage.mapObjectCollision[mapNum][i];
						screen.obj[mapNum][i].openDoorNum = datastorage.mapOpenDoorNum[mapNum][i];
						screen.obj[mapNum][i].doorNum = datastorage.mapDoorNum[mapNum][i];
						screen.obj[mapNum][i].unlocked = datastorage.mapObjectUnlocked[mapNum][i];
						screen.obj[mapNum][i].opened = datastorage.mapObjectOpened[mapNum][i];
						if (screen.obj[mapNum][i].opened == true) {
							screen.obj[mapNum][i].spriteNum = 2;
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Load Exception!");
		}
	}
}
