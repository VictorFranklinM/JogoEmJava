package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.Screen;

public class Magic_Icicle extends Projectile{
	
	public static final String objName = "Icicle";
	
	Screen screen;

	public Magic_Icicle(Screen screen) {
		super(screen);
		this.screen = screen;
		
		name = objName;
		elementalType = "Ice";
		speed = 6;
		maxHP = 100;
		hp = maxHP;
		manaCost = 1;
		knockBackPower = 2;
		attack = (int) (screen.player.dexterity*2.5);
		alive = false;
		getImage();
		
		collisionArea.x = (0 * Screen.scale);
        collisionArea.y = (0 * Screen.scale);
        collisionArea.width = (16 * Screen.scale);
        collisionArea.height = (16 * Screen.scale);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
		
	}
	
	public void getImage() {
		up1 = setup("/spells/Icicle-Up", Screen.tileSize, Screen.tileSize);
		up2 = setup("/spells/Icicle-Up", Screen.tileSize, Screen.tileSize);
		up3 = setup("/spells/Icicle-Up", Screen.tileSize, Screen.tileSize);
		down1 = setup("/spells/Icicle-Down", Screen.tileSize, Screen.tileSize);
		down2 = setup("/spells/Icicle-Down", Screen.tileSize, Screen.tileSize);
		down3 = setup("/spells/Icicle-Down", Screen.tileSize, Screen.tileSize);
		left1 = setup("/spells/Icicle-Left", Screen.tileSize, Screen.tileSize);
		left2 = setup("/spells/Icicle-Left", Screen.tileSize, Screen.tileSize);
		left3 = setup("/spells/Icicle-Left", Screen.tileSize, Screen.tileSize);
		right1 = setup("/spells/Icicle-Right", Screen.tileSize, Screen.tileSize);
		right2 = setup("/spells/Icicle-Right", Screen.tileSize, Screen.tileSize);
		right3 = setup("/spells/Icicle-Right", Screen.tileSize, Screen.tileSize);
		
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= manaCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Entity user) {
		user.mana -= manaCost;
	}

	public Color getParticleColor() {
		Color color = new Color(105, 180, 200);
		return color;
	}
	
	public int getParticleSize() {
		int size = 6;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxHp() {
		int maxHP = 20;
		return maxHP;
	}
}
