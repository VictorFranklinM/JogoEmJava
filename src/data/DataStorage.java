package data;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DataStorage implements Serializable{
	
	// Player Stats
	int hasMaga;
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
	int respawnX;
	int respawnY;
	int respawnMap;
	
	// Inventory
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	
	// Magatama
	int currentMagatamaSlot;
	String projectile;
	
	// Progress Defeated
	boolean matadorDefeated;
	boolean finalCutsceneDone;
	
	// Map Objects
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	boolean mapObjectCollision[][];
	String mapObjectLootNames[][];
	boolean mapObjectUnlocked[][];
	boolean mapObjectOpened[][];
	int mapDoorNum[][];
	int mapOpenDoorNum[][];
}