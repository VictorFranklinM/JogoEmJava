package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.PerformanceTool;
import main.Screen;
import main.Sound;

public abstract class Entity {
	Screen screen;
	Sound sound = new Sound();
	public int worldX, worldY;
	public int speed;
	public boolean isMoving = false;
	
	public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3; // Sprites do personagem.
	public String facing; // Direcao do personagem.
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle collisionArea;
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	
	public boolean collision = false;
	
	public int actionLockCounter = 0; // Para movimentação dos NPC
	
	public Entity(Screen screen) {
		this.screen = screen;
	}
	
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
	
	public void setAction() {
		
	}
	
	public void update() {
		setAction();
		collision = false;
		screen.colCheck.checkTile(this);
		screen.colCheck.checkObject(this, false); // NPC nao pega objetos
		screen.colCheck.checkEntity(this, screen.npc); // Checa com outros NPCs
		screen.colCheck.checkEntity(this, new Entity[]{screen.player}); // Checa com o player
		if(collision == false) {
			switch(facing) {
			case "up": worldY -= speed;
			break;
			case "down": worldY += speed;
			break;
			case "left": worldX -= speed;
			break;
			case "right": worldX += speed;
			break;
			}
			
			if(collision == true) {
	    		isMoving = false;
	    		spriteNum = 2;
	    	} else {
	    		spriteCounter++;
	    	}
	    	
	    	if(spriteCounter > 6) {
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
	
	public void draw(Graphics2D g2, Screen screen) {
		BufferedImage image = null;
		int screenX = worldX - screen.player.worldX + screen.player.screenX;
		int screenY = worldY - screen.player.worldY + screen.player.screenY;
		
		if(((worldX + screen.tileSize) > (screen.player.worldX - screen.player.screenX))
			&& ((worldX - screen.tileSize) < (screen.player.worldX + screen.player.screenX))
			&& ((worldY + screen.tileSize) > (screen.player.worldY - screen.player.screenY))
			&& ((worldY - screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
			
			switch(facing) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				if(spriteNum == 3) {
					image = up3;
				}
				break;
				
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
				if(spriteNum == 3) {
					image = down3;
				}
				break;
				
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				if(spriteNum == 3) {
					image = left3;
				}
				break;
				
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				if(spriteNum == 3) {
					image = right3;
				}
				break;
			}			
			g2.drawImage(image, screenX, screenY, screen.tileSize, screen.tileSize, null);
		}
	}

	public BufferedImage setup(String imagePath) {
		PerformanceTool performancePlayer = new PerformanceTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = performancePlayer.scaleImage(image, screen.tileSize, screen.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("sprite Path invalid for Player sprite");
		}
		return image;
	}

}

