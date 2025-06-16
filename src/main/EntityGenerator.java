package main;

import entity.Entity;
import entity.Projectile;
import object.Magic_Arrow;
import object.Magic_Fireball;
import object.Magic_Icicle;
import object.Magic_WindBlast;
import object.OBJ_CacheCube;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_GreenMacca;
import object.OBJ_HP;
import object.OBJ_Key;
import object.OBJ_MagaBlue;
import object.OBJ_MagaGreen;
import object.OBJ_MagaRed;
import object.OBJ_Mana;
import object.OBJ_ManaBottle;
import object.OBJ_Medicine;
import object.OBJ_Mushroom;
import object.OBJ_Obelisk;
import object.OBJ_Portal;
import object.OBJ_SpecialKey;
import object.OVR_Sparkle;

public class EntityGenerator {

	Screen screen;
	
	public EntityGenerator(Screen screen) {
		this.screen = screen;
	}
	
	public Entity getObject(String itemName) {
		Entity obj = null;
		
		switch(itemName) {
		case Magic_Arrow.objName: obj = new Magic_Arrow(screen); break;
		case Magic_Fireball.objName: obj = new Magic_Fireball(screen); break;
		case Magic_Icicle.objName: obj = new Magic_Icicle(screen); break;
		case Magic_WindBlast.objName: obj = new Magic_WindBlast(screen); break;
		case OBJ_CacheCube.objName: obj = new OBJ_CacheCube(screen); break;
		case OBJ_Chest.objName: obj = new OBJ_Chest(screen); break;
		case OBJ_Door.objName: obj = new OBJ_Door(screen); break;
		case OBJ_GreenMacca.objName: obj = new OBJ_GreenMacca(screen); break;
		case OBJ_HP.objName: obj = new OBJ_HP(screen); break;
		case OBJ_Key.objName: obj = new OBJ_Key(screen); break;
		case OBJ_MagaBlue.objName: obj = new OBJ_MagaBlue(screen); break;
		case OBJ_MagaGreen.objName: obj = new OBJ_MagaGreen(screen); break;
		case OBJ_MagaRed.objName: obj = new OBJ_MagaRed(screen); break;
		case OBJ_Mana.objName: obj = new OBJ_Mana(screen); break;
		case OBJ_ManaBottle.objName: obj = new OBJ_ManaBottle(screen); break;
		case OBJ_Medicine.objName: obj = new OBJ_Medicine(screen); break;
		case OBJ_Mushroom.objName: obj = new OBJ_Mushroom(screen); break;
		case OBJ_Obelisk.objName: obj = new OBJ_Obelisk(screen); break;
		case OBJ_Portal.objName: obj = new OBJ_Portal(screen); break;
		case OBJ_SpecialKey.objName: obj = new OBJ_SpecialKey(screen); break;
		case OVR_Sparkle.objName: obj = new OVR_Sparkle(screen); break;
		default : System.out.println("Object not found."); break;
		}
		return obj;
	}
	
	public Projectile getProjectile(String projName) {
		Projectile proj = null;
		
		switch(projName) {
		case Magic_Arrow.objName: proj = new Magic_Arrow(screen); break;
		case Magic_Fireball.objName: proj = new Magic_Fireball(screen); break;
		case Magic_Icicle.objName: proj = new Magic_Icicle(screen); break;
		case Magic_WindBlast.objName: proj = new Magic_WindBlast(screen); break;
		default : System.out.println("Projectile not found."); break;
		}
		return proj;
	}
}
