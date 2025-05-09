package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3; // Sprites do personagem.
	public String facing; // Direção do personagem.
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle collisionArea;
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	
	public boolean collision = false;
}
