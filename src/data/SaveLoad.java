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
			
			// Stats do Player
			datastorage.level = screen.player.level;
			datastorage.maxHP = screen.player.maxHP;
			datastorage.hp = screen.player.hp;
			datastorage.maxMana = screen.player.maxMana;
			datastorage.strenght = screen.player.strenght;
			datastorage.dexterity = screen.player.dexterity;
			datastorage.exp = screen.player.exp;
			datastorage.nextLevelExp = screen.player.nextLevelExp;
			datastorage.macca = screen.player.macca;
			datastorage.worldX = screen.player.worldX;
			datastorage.worldY = screen.player.worldY;
			datastorage.currentMap = Screen.currentMap;
			
			// Inventario
			for (int i = 0; i < screen.player.inventory.size(); i++) {
				datastorage.itemNames.add(screen.player.inventory.get(i).name);
				datastorage.itemAmounts.add(screen.player.inventory.get(i).amount);
			}
			
			// Magatama
			if(screen.player.currentMagatama != null) {
				datastorage.currentMagatamaSlot = screen.player.getMagatamaSlot();
			}
			else {
				datastorage.currentMagatamaSlot = screen.player.inventorySize+1;
			}
			
			// Objetos no mapa
			datastorage.mapObjectNames = new String[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectWorldX = new int[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectWorldY = new int[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectLootNames = new String[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectOpened = new boolean[Screen.maxMap][screen.obj[1].length];
			datastorage.mapObjectUnlocked = new boolean[Screen.maxMap][screen.obj[1].length];
			
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
						datastorage.mapObjectUnlocked[mapNum][i] = screen.obj[mapNum][i].unlocked;
						datastorage.mapObjectOpened[mapNum][i] = screen.obj[mapNum][i].opened;
					}
				}
			}
			
			//Escrever o arquivo de DataStorage
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
			
			//Ler o arquivo de DataStorage
			DataStorage datastorage = (DataStorage)oinpstr.readObject();
			
			// Stats do Player
			screen.player.level = datastorage.level;
			screen.player.maxHP = datastorage.hp;
			screen.player.hp = datastorage.hp;
			screen.player.maxMana = datastorage.maxMana;
			screen.player.strenght = datastorage.strenght;
			screen.player.dexterity = datastorage.dexterity;
			screen.player.exp = datastorage.exp;
			screen.player.nextLevelExp = datastorage.nextLevelExp;
			screen.player.macca = datastorage.macca;
			screen.player.worldX = datastorage.worldX;
			screen.player.worldY = datastorage.worldY;
			Screen.currentMap = datastorage.currentMap;
			
			// Inventario
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
				screen.player.getAttack();
				screen.player.getDefense();
			}
			
			// Objetos no mapa
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
							screen.obj[mapNum][i].loot = screen.eGenerator.getObject(datastorage.mapObjectLootNames[mapNum][i]);
						}
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
