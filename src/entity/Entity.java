package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.PerformanceTool;
import main.Screen;
import main.Sound;

public abstract class Entity   {
	public Screen screen;
	Sound sound = new Sound();
	
	public int worldX, worldY;
	public boolean isMoving = false;
	
	public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3; // Sprites do personagem.
	public BufferedImage attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2;
	public BufferedImage image, image2, face;
	public String facing = "down"; // Direcao do personagem.
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle collisionArea =  new Rectangle();
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	public boolean collision = false;
	
	public boolean isInvincible = false;
	public int invincibilityTimer = 0;
	public int deathCounter = 0;
	
	public int spellCooldown = 60;
	
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	
	boolean hpBarOn = false;
	int hpBarCounter = 0;
	
	public int dialoguesQuantity = 20;
	public int actionLockCounter = 0; // Para movimenta��o dos NPC
	
	String dialogues[] = new String[dialoguesQuantity]; 
	int dialogueIndex = 0;
	
	public int type;
	public final int typePlayer = 0;
	public final int typeNpc = 1;
	public final int typeEnemy = 2;
	public final int typeMaga = 3;
	public final int typeConsumable = 4;
	public final int typePickupOnly = 5;
	
	// Status
	public String name;
	public int speed;
	public int maxHP;
	public int hp;
	public int maxMana;
	public int mana;
	public int level;
	public int strenght;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int macca;
	public Entity currentMagatama;
	public Projectile projectile;
	
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int inventorySize = 30;
	
	// Item status
	public int value;
	public int manaCost;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	
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
	
	public void setAction() {}
	
	public void use(Entity entity) {}
	
	public void update() {
		setAction();
		collision = false;
		screen.colCheck.checkTile(this);
		screen.colCheck.checkObject(this, false); // NPC nao pega objetos
		screen.colCheck.checkEntity(this, screen.npc); // Checa com outros NPCs
		screen.colCheck.checkEntity(this, screen.enemy);
		boolean contactPlayer = screen.colCheck.checkPlayer(this);
		
		if(this.type == typeEnemy && contactPlayer == true) {
			damagePlayer(attack);
		}
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
		if(isInvincible == true) {
	    	invincibilityTimer++;
	    	if(invincibilityTimer > 40) {
	    		isInvincible = false;
	    		invincibilityTimer = 0;
	    	}
		}
		if(spellCooldown > 0) {
		    	spellCooldown--;
		}
	}
	
	public void damagePlayer(int attack) {
		if(screen.player.isInvincible == false) {
			screen.playSFX(6);
			int damage = attack - screen.player.defense;
			if(damage < 0) {
				damage = 0;
			}
			screen.player.hp -= damage;
			screen.player.isInvincible = true;
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - screen.player.worldX + screen.player.screenX;
		int screenY = worldY - screen.player.worldY + screen.player.screenY;
		
		if(((worldX + Screen.tileSize) > (screen.player.worldX - screen.player.screenX))
			&& ((worldX - Screen.tileSize) < (screen.player.worldX + screen.player.screenX))
			&& ((worldY + Screen.tileSize) > (screen.player.worldY - screen.player.screenY))
			&& ((worldY - Screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
			
			switch(facing) {
			case "up":
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
				if(spriteNum == 3) {image = up3;}
				break;
				
			case "down":
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				if(spriteNum == 3) {image = down3;}
				break;
				
			case "left":
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				if(spriteNum == 3) {image = left3;}
				break;
				
			case "right":
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				if(spriteNum == 3) {image = right3;}
				break;
			}
			
			// Hp inimigo
			if(type == 2 && hpBarOn) {
				double hpScale = (double) Screen.tileSize/maxHP;
				double hpBarValue = hpScale*hp;
				
				Color greenGreen = new Color(64, 152, 94);
				Color darkGreen = new Color(4, 55, 59);
				Color blackGreen = new Color(10, 26, 47);
				
				g2.setColor(blackGreen);
				g2.fillRect(screenX-Screen.scale/2, screenY-18, Screen.tileSize+Screen.scale, 10 + Screen.scale);
				g2.setColor(darkGreen);
				g2.fillRect(screenX, screenY-16, Screen.tileSize, 10);
				g2.setColor(greenGreen);
				g2.fillRect(screenX, screenY-16, (int) hpBarValue, 10);
				
				hpBarCounter++;
				
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			if(isInvincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeSpriteOpacity(g2, 0.4f);
			}
			if(dying == true) {
				deathAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);
			changeSpriteOpacity(g2, 1f);
		}
		
		if(screen.key.isDebugging == true) {
		    g2.setColor(new Color(255, 0, 0, 150));

		    int collisionX = screenX + collisionArea.x;
		    int collisionY = screenY + collisionArea.y;
		    g2.fillRect(collisionX, collisionY, collisionArea.width, collisionArea.height);
		}
	}

	public void deathAnimation(Graphics2D g2) {
		deathCounter++;
		int frameDifference = 5;
		
		if(deathCounter <= frameDifference) {changeSpriteOpacity(g2, 0f);}
		if(deathCounter > frameDifference && deathCounter >= frameDifference*2) {changeSpriteOpacity(g2, 1f);}
		if(deathCounter > frameDifference*2 && deathCounter >= frameDifference*3) {changeSpriteOpacity(g2, 0f);}
		if(deathCounter > frameDifference*3 && deathCounter >= frameDifference*4) {changeSpriteOpacity(g2, 1f);}
		if(deathCounter > frameDifference*4 && deathCounter >= frameDifference*5) {changeSpriteOpacity(g2, 0f);}
		if(deathCounter > frameDifference*5 && deathCounter >= frameDifference*6) {changeSpriteOpacity(g2, 1f);}
		if(deathCounter > frameDifference*6 && deathCounter >= frameDifference*7) {changeSpriteOpacity(g2, 0f);}
		if(deathCounter > frameDifference*7 && deathCounter >= frameDifference*8) {changeSpriteOpacity(g2, 1f);}
		if(deathCounter > frameDifference*8) {
			alive = false;
		}
	}
	
	public void changeSpriteOpacity(Graphics2D g2, float opacity) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity));
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		PerformanceTool performancePlayer = new PerformanceTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = performancePlayer.scaleImage(image, width, height);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Sprite Path invalid for Player sprite");
		}
		return image;
	}


	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		screen.ui.currentSpeechLine = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(screen.player.facing) {
		case "up":
			facing = "down";
			break;
		case "down":
			facing = "up";
			break;
		case "left":
			facing = "right";
			break;
		case "right":
			facing = "left";
			break;
		}
	}
	
	public void damageReaction() {}
	
	public void checkDrop() {}
	
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i < Screen.objPerScreen; i++) {
			if(screen.obj[Screen.currentMap][i] == null) {
				screen.obj[Screen.currentMap][i] = droppedItem;
				screen.obj[Screen.currentMap][i].worldX = worldX;
				screen.obj[Screen.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	
	public int getParticleMaxHp() {
		int maxHP = 0;
		return maxHP;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxHp = generator.getParticleMaxHp();
		
		Particle p1 = new Particle(screen, generator, color, size, speed, maxHp, -2, -1);
		Particle p2 = new Particle(screen, generator, color, size, speed, maxHp, 2, 1);
		Particle p3 = new Particle(screen, generator, color, size, speed, maxHp, -2, 1);
		Particle p4 = new Particle(screen, generator, color, size, speed, maxHp, 2, -1);
		screen.particleList.add(p1);
		screen.particleList.add(p2);
		screen.particleList.add(p3);
		screen.particleList.add(p4);
	}
}
