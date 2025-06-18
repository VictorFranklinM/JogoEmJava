package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.PerformanceTool;
import main.Screen;
import main.Sound;

public class Entity {
	public Screen screen;
	Sound sound = new Sound();
	
	public int worldX, worldY;
	public boolean isMoving = false;
	
	public BufferedImage up1, up2, up3, left1, left2, left3, right1, right2, right3, down1, down2, down3; // Sprites do personagem.
	public BufferedImage attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2;
	public BufferedImage guardUp, guardDown, guardLeft, guardRight;
	public BufferedImage image, image2, face;
	public String facing = "down"; // Direcao do personagem.
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle collisionArea =  new Rectangle();
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	public int attackAreaDefaultWidth, attackAreaDefaultHeight;
	public boolean collision = false;
	
	public boolean isInvincible = false;
	public boolean isTransparent = false;
	public int invincibilityTimer = 0;
	public int deathCounter = 0;
	
	public String elementalWeakness = "";
	public int spellCooldown = 60;
	
	public Entity attacker;
	public boolean attacking = false;
	public boolean guarding = false;
	public int guardCounter = 0;
	public boolean stunned = false;
	int stunnedCounter = 0;
	public boolean alive = true;
	public boolean dying = false;
	
	boolean hpBarOn = false;
	int hpBarCounter = 0;
	
	public boolean onPath = false;
	
	public String knockBackDirection;
	public boolean knockBack = false;
	int knockBackCounter = 0;
	
	// Chest & Door
	public Entity loot;
	public boolean opened = false;
	public boolean unlocked = false;
	public int doorNum = -1;
	public int openDoorNum = -1;
	
	//DIALOGUES STATUS
	public int dialoguesQuantity = 20;
	public int actionLockCounter = 0; // Para movimenta��o dos NPC
	
	public String dialogues[][] = new String[dialoguesQuantity][dialoguesQuantity]; 
	public int dialogueIndex = 0;
	public int dialogueSet = 0;
	
	public int type;
	public final int typePlayer = 0;
	public final int typeNpc = 1;
	public final int typeEnemy = 2;
	public final int typeMaga = 3;
	public final int typeConsumable = 4;
	public final int typePickupOnly = 5;
	public final int typeObstacle = 6;
	public final int typeOverlay = 7;
	
	// Status
	public String name;
	public int defaultSpeed;
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
	
	public int motion1Duration;
	public int motion2Duration;
	
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int inventorySize = 30;
	
	// Item status
	public int value;
	public int manaCost;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int knockBackPower = 0;
	public boolean stackable = false;
	public int amount = 1;
	public int price;

	public boolean isFollowing = false;
	public Entity(Screen screen) {
		this.screen = screen;
	}
	
	public int getLeftX() {
		return worldX + collisionArea.x;
	}
	
	public int getRightX() {
		return worldX + collisionArea.x + collisionArea.width;
	}
	
	public int getTopY() {
		return worldY + collisionArea.y;
	}
	
	public int getBottomY() {
		return worldY + collisionArea.y + collisionArea.height;
	}
	
	public int getCol() {
		return (worldX + collisionArea.x)/Screen.tileSize;
	}
	
	public int getRow() {
		return (worldY + collisionArea.y)/Screen.tileSize;
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
	
	public void resetCounter() {
		spriteCounter = 0;
		actionLockCounter = 0;
		invincibilityTimer = 0;
		deathCounter = 0;
		knockBackCounter = 0;
		guardCounter = 0;
	}
	
	public void setLoot (Entity loot) {} // CHEST LOOT
	
	public void setAction() {}
	
	public boolean use(Entity entity) {return false;}
	
	public void checkCollision() {
		collision = false;
		screen.colCheck.checkTile(this);
		screen.colCheck.checkObject(this, false); // NPC nao pega objetos
		screen.colCheck.checkEntity(this, screen.npc); // Checa com outros NPCs
		screen.colCheck.checkEntity(this, screen.enemy);
		boolean contactPlayer = screen.colCheck.checkPlayer(this);
		
		if(this.type == typeEnemy && contactPlayer == true) {
			damagePlayer(attack);
		}
	
	}
	
	public void update() {
		
		if(knockBack == true) {
			
			checkCollision();
			if (collision == true) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			else if(collision == false) {
				switch(knockBackDirection) {
				case "up": worldY -= speed;
				break;
				case "down": worldY += speed;
				break;
				case "left": worldX -= speed;
				break;
				case "right": worldX += speed;
				break;
				}
			}
			
			knockBackCounter++;
			if(knockBackCounter == 10) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			
		}
		else if(attacking) {
			attack();
		}
		else {
			setAction();
			checkCollision();
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
	    	if(invincibilityTimer > 30) {
	    		isInvincible = false;
	    		invincibilityTimer = 0;
	    	}
		}
		if(spellCooldown > 0) {
		    	spellCooldown--;
		}
		if(stunned) {
			stunnedCounter++;
			if(stunnedCounter > 60) {
				stunned = false;
				stunnedCounter = 0;
			}
		}
	}
	
	public String getOppositeDirection(String facing) {
		String oppositeDirection = "";
		switch(facing) {
		case "up": oppositeDirection = "down"; break;
		case "down": oppositeDirection = "up"; break;
		case "left": oppositeDirection = "right"; break;
		case "right": oppositeDirection = "left"; break;
		}
		
		return oppositeDirection;
	}
	
	public void attack() {
	    spriteCounter++;

	    if (spriteCounter <= motion1Duration) {
	        spriteNum = 1;
	    }

	    if (spriteCounter > motion1Duration && spriteCounter <= motion2Duration) {
	        spriteNum = 2;

	        int currentWorldX = worldX;
	        int currentWorldY = worldY;
	        int collisionAreaWidth = collisionArea.width;
	        int collisionAreaHeight = collisionArea.height;

	        ajustAttackArea();

	        if (type == typeEnemy) {
	            if (screen.colCheck.checkPlayer(this)) {
	                damagePlayer(attack);
	            }
	        } else if (type == typePlayer) {
	            int enemyIndex = screen.colCheck.checkEntity(this, screen.enemy);
	            int knockBack;
	            if(currentMagatama == null) {
	            	knockBack = 2;
	            }
	            else {
	            	knockBack = currentMagatama.knockBackPower;
	            }
	            screen.player.damageEnemy(enemyIndex, this, attack, knockBack);

	            int projectileIndex = screen.colCheck.checkEntity(this, screen.projectile);
	            screen.player.damageProjectile(projectileIndex);
	        }

	        worldX = currentWorldX;
	        worldY = currentWorldY;
	        collisionArea.width = collisionAreaWidth;
	        collisionArea.height = collisionAreaHeight;
	    }

	    if (spriteCounter > motion2Duration) {
	        spriteNum = 1;
	        spriteCounter = 0;
	        attacking = false;
	    }
	}
	
	public void ajustAttackArea() {
	    int atkW = attackAreaDefaultWidth;
	    int atkH = attackAreaDefaultHeight;

	    switch (facing) {
	        case "up":
	            attackArea.width = atkH;
	            attackArea.height = atkW;
	            worldY -= attackArea.height;
	            break;
	        case "down":
	            attackArea.width = atkH;
	            attackArea.height = atkW;
	            worldY += collisionArea.y + collisionArea.height;
	            break;
	        case "left":
	            attackArea.width = atkW;
	            attackArea.height = atkH;
	            worldX -= attackArea.width;
	            break;
	        case "right":
	            attackArea.width = atkW;
	            attackArea.height = atkH;
	            worldX += collisionArea.x + collisionArea.width;
	            break;
	    }

	    collisionArea.width = attackArea.width;
	    collisionArea.height = attackArea.height;
	}
	
	public void damagePlayer(int attack) {
		if(screen.player.isInvincible == false) {
			int damage = attack - screen.player.defense;
			
			String guardDirection = getOppositeDirection(facing);
			// Guard
			if(screen.player.guarding && screen.player.facing.equals(guardDirection)) {
				//Parry
				if(screen.player.guardCounter < 10) {
					damage = 0;
					screen.playSFX(13);
					setKnockBack(this, screen.player, knockBackPower);
					stunned = true;
					spriteCounter = -60;
				}
				else if(screen.player.guardCounter > 10){
					damage /= 2;
					screen.playSFX(12);
				}
				if(damage < 0) {
					damage = 0;
				}
			}
			// Damage
			else {
				screen.playSFX(6);
				if(damage < 1) {
					damage = 1;
				}
			}
			
			if(damage > 0 ) {
				screen.player.isTransparent = true;
				setKnockBack(screen.player, this, knockBackPower);
			}

			screen.player.hp -= damage;
			screen.player.isInvincible = true;
		}
	}
	
	public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
		this.attacker = attacker;
		target.knockBackDirection = attacker.facing;
		target.speed += knockBackPower;
		target.knockBack = true;
		
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - screen.player.worldX + screen.player.screenX;
		int screenY = worldY - screen.player.worldY + screen.player.screenY;
		
		if(((worldX + Screen.tileSize) > (screen.player.worldX - screen.player.screenX))
			&& ((worldX - Screen.tileSize) < (screen.player.worldX + screen.player.screenX))
			&& ((worldY + Screen.tileSize) > (screen.player.worldY - screen.player.screenY))
			&& ((worldY - Screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
			
			int tempScreenX = screenX;
			int tempScreenY = screenY;
			
			switch(facing) {
			case "up":
				if(attacking == false) {
					if(spriteNum == 1) {image = up1;}
					if(spriteNum == 2) {image = up2;}
					if(spriteNum == 3) {image = up3;}
				}
				if(attacking == true) {
					tempScreenY = screenY - Screen.tileSize;
					if(spriteNum == 1) {image = attackUp1;}
					if(spriteNum == 2) {image = attackUp2;}
				}
				break;
			case "down":
				if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				if(spriteNum == 3) {image = down3;}
				}
				if(attacking == true) {
					if(spriteNum == 1) {image = attackDown1;}
					if(spriteNum == 2) {image = attackDown2;}
				}
				break;
				
			case "left":
				if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				if(spriteNum == 3) {image = left3;}
				}
				if(attacking == true) {
					tempScreenX = screenX - Screen.tileSize;
					if(spriteNum == 1) {image = attackLeft1;}
					if(spriteNum == 2) {image = attackLeft2;}
				}
				break;
				
			case "right":
				if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				if(spriteNum == 3) {image = right3;}
				}
				if(attacking == true) {
					if(spriteNum == 1) {image = attackRight1;}
					if(spriteNum == 2) {image = attackRight2;}
				}
				break;
			
			}
	        if(screen.key.isDebugging == true) {
	        	g2.setColor(new Color(255, 255, 0, 150)); // Amarelo para a hitbox de ataque
	        	int attackScreenX = screenX;
	        	int attackScreenY = screenY;
	                
	        	switch(facing) {
	        	case "up": 
	        		attackScreenY = screenY - attackArea.height; 
	        		break;
	        	case "down": 
	        		attackScreenY = screenY + Screen.tileSize;
	        		break;
	        	case "left": 
	        		attackScreenX = screenX - attackArea.width;
	        		break;
	        	case "right":
	        		attackScreenX = screenX + Screen.tileSize;
	        		break;
	        	}
	                
	        	g2.fillRect(attackScreenX, attackScreenY, attackArea.width, attackArea.height);
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
			
			g2.drawImage(image, tempScreenX, tempScreenY, null);
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

	public void speak() {}
	
	public void facePlayer() {
		
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
	
	public void startDialogue(Entity entity, int setNum) {
		screen.gameState = Screen.dialogueState;
		screen.ui.npc = entity;
		dialogueSet = setNum;
		dialogueIndex = 0;
	}
	
	public void interact() {
		
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
	
	public void searchPath(int goalCol,int goalRow) {
		
		int startCol = (worldX + collisionArea.x) / Screen.tileSize;
		int startRow = (worldY + collisionArea.y) / Screen.tileSize;
		screen.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
		
		if(screen.pFinder.search() == true)  {
			
			//NEXT WORLDY AND WORLD X ITS IS GOING TO MOVE
			int nextX = screen.pFinder.pathList.get(0).col * Screen.tileSize;
			int nextY = screen.pFinder.pathList.get(0).row * Screen.tileSize;
			
			//ENTITY COLLISION AREA POSITIONS
			int enLeftX = worldX + collisionArea.x;
			int enRightX = worldX + collisionArea.x + collisionArea.width;
			int enTopY = worldY + collisionArea.y;
			int enBottomY = worldY + collisionArea.y + collisionArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + Screen.tileSize ) {
				facing = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + Screen.tileSize) {
				facing = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + Screen.tileSize) {
				//LEFT OR RIGHT
				if(enLeftX > nextX) {
					facing = "left";
				}
				if(enLeftX < nextX) {
					facing = "right";
				}
				
			}
			else if(enTopY > nextY && enLeftX > nextX) {
				//UP OR LEFT
				facing = "up";
				checkCollision();
				if(collision == true) {
					facing = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				//UP OR RIGHT
				facing = "up";
				checkCollision();
				if(collision == true) {
					facing = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				//DOWN OR LEFT
				facing = "down";
				checkCollision();
				if(collision == true) {
					facing = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				//DOWN OR RIGHT
				facing = "down";
				checkCollision();
				if(collision == true) {
					facing = "right";
				}
			}
			
			
			// CHECK IF IT REACHES THE GOAL
			if(isFollowing == false) {
				int nextCol = screen.pFinder.pathList.get(0).col;
				int nextRow = screen.pFinder.pathList.get(0).row;
				if(nextCol == goalCol && nextRow == goalRow) {
				onPath = false;
				}
			}
	}		
				
		
		
	}
	
	public void movementLogic() {
		actionLockCounter++;
		
		if(actionLockCounter == 45) {
			Random random = new Random();
			int i = random.nextInt(100)+1; // escolhe um numero de 1�100
		
			if (i <= 25) {
				facing = "up";
			}
			if (i > 25 && i <= 50) {
				facing = "down";
			}
			if (i > 50 && i <= 75) {
				facing = "left";
			}
			if (i > 75 && i <= 100) {
				facing = "right";
			}
		
			actionLockCounter = 0;
		}
	}
	
	public void followLogic() {
		if(onPath == true) {
			
			int goalCol = (screen.player.worldX + screen.player.collisionArea.x) / Screen.tileSize;
			int goalRow = (screen.player.worldY + screen.player.collisionArea.y) / Screen.tileSize;
			searchPath(goalCol,goalRow);
		
		}else{
			movementLogic();
		
		}
	}
	
	public void destinedMovement(int col,int row) {
		if(onPath == true) {
			int goalCol = col;
			int goalRow = row;
			searchPath(goalCol,goalRow);
		}else {
			movementLogic();
		}
		
	}
	
	public int getDetected(Entity user, Entity target[][], String targetName) {
		int index = 999;
		
		// Check the surrounding object
		int nextWorldX = user.worldX + Screen.tileSize / 2;
		int nextWorldY = user.worldY + Screen.tileSize / 2;
		
		switch(user.facing) {
	    case "up": nextWorldY -= Screen.tileSize; break;
	    case "down": nextWorldY += Screen.tileSize; break;
	    case "left": nextWorldX -= Screen.tileSize; break;
	    case "right": nextWorldX += Screen.tileSize; break;
		}
		
		int col = nextWorldX/Screen.tileSize;
		int row = nextWorldY/Screen.tileSize;
		
		
		for (int i = 0; i < target[Screen.currentMap].length; i++) {
			if(target[Screen.currentMap][i] != null) {
				if(target[Screen.currentMap][i].getCol() == col && target[Screen.currentMap][i].getRow() == row && target[Screen.currentMap][i].name.equals(targetName) ) {
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(worldX - target.worldX);
		return xDistance;
	}
	
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(worldY - target.worldY);
		return yDistance;
	}
	
	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) + getYDistance(target)) / Screen.tileSize;
		return tileDistance;
	}
	
	public void getAgro(Entity target, int distance, int rate) {
		if(getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if(i==0) {
				onPath = true;
				isFollowing = true;
			}
		}
	}
	
	public void loseAgro(Entity target, int distance, int rate) {
		if(getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if(i==0) {
				onPath = false;
				isFollowing = false;
			}
		}
	}
	
	public void useAttackEnemy(int rate, int straightDistance, int sidesDistance) {
		boolean targetInRange = false;
		int xDis = getXDistance(screen.player);
		int yDis = getYDistance(screen.player);
		
		switch(facing) {
		case "up":
			if(screen.player.worldY < worldY && yDis < straightDistance && xDis < sidesDistance) {
				targetInRange = true;
			}
			break;
		case "down":
			if(screen.player.worldY > worldY && yDis < straightDistance && xDis < sidesDistance) {
				targetInRange = true;
			}
			break;
		case "left":
			if(screen.player.worldX < worldX && xDis < straightDistance && yDis < sidesDistance) {
				targetInRange = true;
			}
			break;
		case "right":
			if(screen.player.worldX > worldX && xDis < straightDistance && yDis < sidesDistance) {
				targetInRange = true;
			}
			break;
		}
		
		if(targetInRange) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				spellCooldown = 90;
			}
		}
	}
	
	public void useSpellEnemy(int rate, int cooldown) {
		int i = new Random().nextInt(rate);
		if(i == 0 && !projectile.alive && spellCooldown == 0) {
			projectile.set(worldX, worldY, facing, true, this);
				
			// CHECK VACANCY
			for(int j = 0; j <screen.projectile[1].length; j++)  {
				if(screen.projectile[Screen.currentMap][j] == null) {
					screen.projectile[Screen.currentMap][j] = projectile;
					break;
				}
			}
		spellCooldown = cooldown;
		}
	}
}
	
