package entity;

import main.Screen;

public class Projectile extends Entity{
	Entity user;
	
	public Projectile(Screen screen) {
		super(screen);
	}
	
	public void set(int worldX, int worldY, String facing, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.facing = facing;
		this.alive = alive;
		this.user = user;
		this.hp = this.maxHP;
		
	}
	
	public void update() {
		
		switch(facing) {
			case "up": worldY -= speed; break;
			case "down":worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right":worldX += speed; break;
		}
		
		hp--;
		if(hp <= 0) {
			alive = false;
		}
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 3;
			}
			else if(spriteNum == 3) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}

}
