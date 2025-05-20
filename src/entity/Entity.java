package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Sound;

public class Entity {
	Sound sound = new Sound();
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3; // Sprites do personagem.
	public String facing; // Direção do personagem.
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle collisionArea;
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	
	public boolean collision = false;
	
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}

	public void playSFX(int i) {
		sound.setFile(i);
		sound.play();
	}
}

