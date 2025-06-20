package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.Screen;

public class Magic_Arrow extends Projectile{
	
	public static final String objName = "Arrow";
	
	Screen screen;

	public Magic_Arrow(Screen screen) {
		super(screen);
		this.screen = screen;
		
		name = objName;
		speed = 6;
		maxHP = 100;
		hp = maxHP;
		manaCost = 1;
		attack = 2;
		knockBackPower = 1;
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
		up1 = setup("/spells/arrowUp", Screen.tileSize, Screen.tileSize);
		up2 = setup("/spells/arrowUp", Screen.tileSize, Screen.tileSize);
		up3 = setup("/spells/arrowUp", Screen.tileSize, Screen.tileSize);
		down1 = setup("/spells/arrowDown", Screen.tileSize, Screen.tileSize);
		down2 = setup("/spells/arrowDown", Screen.tileSize, Screen.tileSize);
		down3 = setup("/spells/arrowDown", Screen.tileSize, Screen.tileSize);
		left1 = setup("/spells/arrowLeft", Screen.tileSize, Screen.tileSize);
		left2 = setup("/spells/arrowLeft", Screen.tileSize, Screen.tileSize);
		left3 = setup("/spells/arrowLeft", Screen.tileSize, Screen.tileSize);
		right1 = setup("/spells/arrowRight", Screen.tileSize, Screen.tileSize);
		right2 = setup("/spells/arrowRight", Screen.tileSize, Screen.tileSize);
		right3 = setup("/spells/arrowRight", Screen.tileSize, Screen.tileSize);
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
		Color color = new Color(64, 152, 94);
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
