package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Screen;

public class Particle extends Entity{
	
	Entity generator;
	Color color;
	int size;
	int xDirection;
	int yDirection;

	public Particle(Screen screen, Entity generator, Color color, int size, int speed, int maxHP, int xDirection, int yDirection) {
		super(screen);
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxHP = maxHP;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
		
		hp = maxHP;
		int offset = (Screen.tileSize/2) - size/2;
		worldX = generator.worldX + offset;
		worldY = generator.worldY + offset;
	}
	
	public void update() {
		hp--;
		
		if(hp < maxHP/3) {
			yDirection++;
		}
		
		worldX += xDirection*speed;
		worldY += yDirection*speed;
		
		if(hp == 0) {
			alive = false;
		}
	}

	public void draw(Graphics2D g2) {
		int screenX = worldX - screen.player.worldX + screen.player.screenX;
		int screenY = worldY - screen.player.worldY + screen.player.screenY;
		
		g2.setColor(color);
		g2.fillRect(screenX, screenY, size, size);
	}
}
