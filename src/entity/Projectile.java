package entity;

import main.Screen;

public class Projectile extends Entity{
	Entity user;
	private int map = Screen.currentMap;
	
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
		this.map = Screen.currentMap;
		
	}
	
	public void update() {
		if(map != Screen.currentMap) {
			alive = false;
			return;
		}
		if(user == screen.player && alive) {
	        int enemyIndex = screen.colCheck.checkEntity(this, screen.enemy);
	        if(enemyIndex != 999) {
	            screen.player.damageEnemy(enemyIndex, attack);
	            generateParticle(user.projectile, screen.enemy[Screen.currentMap][enemyIndex]);
	            alive = false;
				return;
			}
		}
		if(user != screen.player) {
			boolean contactPlayer = screen.colCheck.checkPlayer(this);
			if(!screen.player.isInvincible && contactPlayer) {
				damagePlayer(attack);
				generateParticle(user.projectile, screen.player);
				alive = false;
				return;
			}
		}
		
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

	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		return haveResource;
	}
	
	public void subtractResource(Entity user) {}
	
}
