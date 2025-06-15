package data;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DataStorage implements Serializable{
	
	//Stats do Player
	int level;
	int maxHP;
	int hp;
	int maxMana;
	int mana;
	int strenght;
	int dexterity;
	int exp;
	int nextLevelExp;
	int macca;
	int worldX;
	int worldY;
	int currentMap;
	
	// Inventario
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	
	// Magatama
	int currentMagatamaSlot;
	
	// Objetos no mapa
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][];
	boolean mapObjectUnlocked[][];
	boolean mapObjectOpened[][];
}