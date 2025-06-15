package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.Screen;
import object.OBJ_Chest;
import object.OBJ_Key;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;
import object.OBJ_Obelisk;

public class SaveLoad {
	
	Screen screen;
	
	public SaveLoad(Screen screen) {
		this.screen = screen;
	}
	public Entity getObject(String itemName) {
		Entity obj = null;
		
		switch(itemName) {
		case "Key": obj = new OBJ_Key(screen); break;
		case "Mana Bottle": obj = new OBJ_ManaBottle(screen); break;
		case "Medicine": obj = new OBJ_Medicine(screen); break;
		case "Mushroom": obj = new OBJ_Mushroom(screen); break;
		case "Force Magatama": obj = new OBJ_MagaGreen(screen); break;
		case "Ice Magatama": obj = new OBJ_MagaBlue(screen); break;
		case "Fire Magatama": obj = new OBJ_MagaRed(screen); break;
		case "Obelisk": obj = new OBJ_Obelisk(screen); break;
		case "Chest": obj = new OBJ_Chest(screen); break;
		}
		return obj;
	}
	public void save() {
		try{
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
			
			// Inventario
			for (int i = 0; i < screen.player.inventory.size(); i++) {
				datastorage.itemNames.add(screen.player.inventory.get(i).name);
				datastorage.itemAmounts.add(screen.player.inventory.get(i).amount);
			}
			
			// Magatama
			datastorage.currentMagatamaSlot = screen.player.getMagatamaSlot();
			
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
			System.out.println("Save Exception!");
		}
	}
	
	public void load() {
		try {
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
			
			// Inventario
			screen.player.inventory.clear();
			for(int i = 0; i < datastorage.itemNames.size(); i++) {
				Entity loadedItem = getObject(datastorage.itemNames.get(i));

				if (loadedItem != null) {
					screen.player.inventory.add(loadedItem);
					screen.player.inventory.get(i).amount = datastorage.itemAmounts.get(i);
				} else {
					System.out.println("Objeto não reconhecido: " + datastorage.itemNames.get(i));
					Entity placeholder = new OBJ_Mushroom(screen);
				    screen.player.inventory.add(placeholder);
				    screen.player.inventory.get(i).amount = datastorage.itemAmounts.get(i);
				}
			}
			
			// Magatama
			screen.player.currentMagatama = screen.player.inventory.get(datastorage.currentMagatamaSlot);
			screen.player.getAttack();
			screen.player.getDefense();
			screen.player.getAttackImage();
			
			// Objetos no mapa
			for(int mapNum = 0; mapNum < Screen.maxMap; mapNum++) {
				for(int i = 0; i < screen.obj[1].length; i++) {
					if(datastorage.mapObjectNames[mapNum][i].equals("N/A")) {
						screen.obj[mapNum][i] = null;
					}
					else {
						screen.obj[mapNum][i] = getObject(datastorage.mapObjectNames[mapNum][i]);
						screen.obj[mapNum][i].worldX = datastorage.mapObjectWorldX[mapNum][i];
						screen.obj[mapNum][i].worldY = datastorage.mapObjectWorldY[mapNum][i];
						if(datastorage.mapObjectLootNames != null) {
							screen.obj[mapNum][i].loot = getObject(datastorage.mapObjectLootNames[mapNum][i]);
						}
						screen.obj[mapNum][i].unlocked = datastorage.mapObjectUnlocked[mapNum][i];
						screen.obj[mapNum][i].opened = datastorage.mapObjectOpened[mapNum][i];
						if (screen.obj[mapNum][i].opened == true) {
							screen.obj[mapNum][i].down1 = screen.obj[mapNum][i].down2;
						}
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Load Exception!");
		}
	}
}
