package object;

import entity.Projectile;
import main.Screen;

public class Magic_Fireball extends Projectile{
	
	Screen screen;

	public Magic_Fireball(Screen screen) {
		super(screen);
		this.screen = screen;
		
		name = "Fireball";
		speed = 5;
		maxHP = 100;
		hp = maxHP;
		manaCost = 1;
		attack = 2;
		alive = false;
		getImage();
		
	}
	
	public void getImage() {
		up1 = setup("/spells/FireBall-Up", screen.tileSize, screen.tileSize);
		up2 = setup("/spells/FireBall-Up", screen.tileSize, screen.tileSize);
		up3 = setup("/spells/FireBall-Up", screen.tileSize, screen.tileSize);
		down1 = setup("/spells/FireBall-Down", screen.tileSize, screen.tileSize);
		down2 = setup("/spells/FireBall-Down", screen.tileSize, screen.tileSize);
		down3 = setup("/spells/FireBall-Down", screen.tileSize, screen.tileSize);
		left1 = setup("/spells/FireBall-Left", screen.tileSize, screen.tileSize);
		left2 = setup("/spells/FireBall-Left", screen.tileSize, screen.tileSize);
		left3 = setup("/spells/FireBall-Left", screen.tileSize, screen.tileSize);
		right1 = setup("/spells/FireBall-Right", screen.tileSize, screen.tileSize);
		right2 = setup("/spells/FireBall-Right", screen.tileSize, screen.tileSize);
		right3 = setup("/spells/FireBall-Right", screen.tileSize, screen.tileSize);
	}

}
