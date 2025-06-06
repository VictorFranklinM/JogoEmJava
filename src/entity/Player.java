package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.KeyInput;
import main.Screen;
import object.Magic_Fireball;
import object.Magic_Icicle;
import object.Magic_WindBlast;

public class Player extends Entity{
	Screen screen;
	
	KeyInput key;
	
	public final int screenX;
	public final int screenY;
	private final int defaultSpeed = 5;
	
	public int hasMaga = 0;
	
	public boolean isMoving = false;
	public boolean canAttack = true;
	
	private int standingCounter = 0;
	
	public Player(Screen screen, KeyInput keyInput) {
		super(screen);
		this.screen = screen;
		
		this.key = keyInput;
		
		/* Como o personagem e renderizado a partir do pixel superior esquerdo, subtraimos meio tile de X e Y para que ele
		 * seja renderizado corretamente no meio da tela. */
		screenX = (Screen.screenWidth / 2) - (Screen.tileSize/2);
		screenY = (Screen.screenHeight / 2) - (Screen.tileSize/2);
		
		collisionArea = new Rectangle();
		collisionArea.x = (4 * Screen.scale); // X do retângulo (comeca no canto esquerdo).
		collisionArea.y = (8 * Screen.scale); // Y (comeca no canto superior).
		collisionArea.width = (8 * Screen.scale); // Largura do retângulo.
		collisionArea.height = (7 * Screen.scale); // Altura.
		
		attackArea.width = (int) (Screen.tileSize/1.5);
		attackArea.height = (int) (Screen.tileSize/1.5);
		
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		setDefaultValues();
		getImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
		// World X e Y sao onde o personagem do player aparecera no mapa inicialmente.
		worldX = Screen.tileSize * 25; // LEMBRAR DE MUDAR SETDEFAULTPOSITIONS()
		worldY = Screen.tileSize * 37;
		speed = defaultSpeed;
		facing = "down";
		
		// Status
		maxHP = 3;
		hp = maxHP;
		maxMana = 3;
		mana = maxMana;
		level = 1;
		strenght = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		macca = 0;
		
		if(currentMagatama != null) {
			attack = getAttack();
			defense = getDefense();
		}
		else {
			attack = strenght;
			defense = dexterity;
		}
	}
	
	public void setDefaultPositions() {
		worldX = Screen.tileSize * 25; // MUDAR NA VERSÃO FINAL DO JOGO
		worldY = Screen.tileSize * 37;
		facing = "down";
	}
	
	public void restoreHpAndMana() {
		hp = maxHP;
		mana = maxMana;
		isInvincible = false;
	}
	
	public void setItems() {
		inventory.clear();
	}
	
	public void getImage() {
		up1 = setup("/player/Up-1", Screen.tileSize, Screen.tileSize);
		up2 = setup("/player/Up-2", Screen.tileSize, Screen.tileSize);
		up3 = setup("/player/Up-3", Screen.tileSize, Screen.tileSize);
		down1 = setup("/player/Down-1", Screen.tileSize, Screen.tileSize);
		down2 = setup("/player/Down-2", Screen.tileSize, Screen.tileSize);
		down3 = setup("/player/Down-3", Screen.tileSize, Screen.tileSize);
		left1 = setup("/player/Left-1", Screen.tileSize, Screen.tileSize);
		left2 = setup("/player/Left-2", Screen.tileSize, Screen.tileSize);
		left3 = setup("/player/Left-3", Screen.tileSize, Screen.tileSize);
		right1 = setup("/player/Right-1", Screen.tileSize, Screen.tileSize);
		right2 = setup("/player/Right-2", Screen.tileSize, Screen.tileSize);
		right3 = setup("/player/Right-3", Screen.tileSize, Screen.tileSize);

	}	
	
	public void getPlayerAttackImage() {
		
		attackUp1 = setup("/player/AttackUp-1", Screen.tileSize, Screen.tileSize*2);
		attackUp2 = setup("/player/AttackUp-2", Screen.tileSize, Screen.tileSize*2);
		attackDown1 = setup("/player/AttackDown-1", Screen.tileSize, Screen.tileSize*2);
		attackDown2 = setup("/player/AttackDown-2", Screen.tileSize, Screen.tileSize*2);
		attackLeft1 = setup("/player/AttackLeft-1", Screen.tileSize*2, Screen.tileSize);
		attackLeft2 = setup("/player/AttackLeft-2", Screen.tileSize*2, Screen.tileSize);
		attackRight1 = setup("/player/AttackRight-1", Screen.tileSize*2, Screen.tileSize);
		attackRight2 = setup("/player/AttackRight-2", Screen.tileSize*2, Screen.tileSize);
	}
	
	public void update() {
		if (attacking == true) {
			attack();
		}
		else if (key.upHold || key.downHold || key.leftHold || key.rightHold || key.ePressed) {
			isMoving = true;

	        if(key.upHold) {
	            facing = "up";
	        }
	        if(key.downHold) {
	            facing = "down";
	        }
	        if(key.leftHold) {
	            facing = "left";
	        }
	        if(key.rightHold) {
	            facing = "right";
	        }
	    	
	    	collision = false;
	    	screen.colCheck.checkTile(this);
	    	
	    	int objIndex = screen.colCheck.checkObject(this, true);
	    	if(objIndex < Screen.objPerScreen) {
	    		pickUpObject(objIndex);
	    	}
	    	
	    	int npcIndex = screen.colCheck.checkEntity(this, screen.npc);
	    	interactNPC(npcIndex);
	    	
	    	int enemyIndex = screen.colCheck.checkEntity(this,screen.enemy);
	    	contactEnemy(enemyIndex);
	    	
	    	screen.eventManager.checkEvent();
	    	
	    	
	    	
	    	if(collision == false && key.ePressed == false) {
	    		switch(facing) {
	    		case "up":
		    		// Checa se o personagem esta movendo na diagonal e recalcula o vetor de velocidade.
		    		if(key.leftHold) {
		    			speed--;
		    			worldX -=speed;

		    			
		    		} else if (key.rightHold) {
		    			speed--;
		    			worldX +=speed;
		    		}
		    		
		    		worldY -= speed;
	    			break;
	    			
	    		case "down":
		    		// Checa se o personagem esta movendo na diagonal e recalcula o vetor de velocidade.
		    		if(key.leftHold) {
		    			speed--;
		    			worldX -=speed;
		    			
		    		} else if (key.rightHold) {
		    			speed--;
		    			worldX +=speed;

		    		}
		    		
		    		worldY += speed;
	    			break;
	    			
	    		case "left":
		    		// Checa se o personagem esta movendo na diagonal e recalcula o vetor de velocidade.
		    		if(key.upHold) {
		    			speed--;
		    			worldY -=speed;
		    			
		    		} else if (key.downHold) {
		    			speed--;
		    			worldY +=speed;

		    		}
		    		
		    		worldX -= speed;
	    			break;
	    			
	    		case "right":
		    		// Checa se o personagem esta movendo na diagonal e recalcula o vetor de velocidade.
	    			if(key.upHold) {
	    				speed--;
		    			worldY -=speed;

		    			
		    		} else if (key.downHold) {
		    			speed--;
		    			worldY +=speed;

		    		}
	    			
		    		worldX += speed;
	    			break;
	    		}
	    		
	    		speed = defaultSpeed;
	    	
	    	}
	    	
	    	if(key.ePressed && canAttack) {
	    		screen.playSFX(7);
	    		attacking = true;
	    		spriteCounter = 0;
	    	}
	    	
	    	canAttack = true;
	    	screen.key.ePressed = false;
	    	
	    	if(collision == true) {
	    		isMoving = false;
	    		spriteNum = 1;
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
		else if(!attacking && !key.upHold && !key.downHold && !key.leftHold && !key.rightHold){
			isMoving = false;
			standingCounter++;
			if(standingCounter == 15) {
				spriteNum = 1;
				standingCounter = 0;
			}
		}
		
		if(screen.key.shootKeyPressed && !projectile.alive && spellCooldown == 0 && projectile.haveResource(this)) {
			
			projectile.set(worldX, worldY, facing, true, this);
			projectile.subtractResource(this);
			screen.spellList.add(projectile);
			spellCooldown = 60;
			screen.playSFX(9);
		}
	    
	    if(isInvincible == true) {
	    	invincibilityTimer++;
	    	if(invincibilityTimer > 60) {
	    		isInvincible = false;
	    		invincibilityTimer = 0;
	    	}
	    }
	    if(spellCooldown > 0) {
	    	spellCooldown--;
	    }
	    
	    if(hp <= 0) {
			screen.stopMusic();
			screen.playMusic(10);
	    	screen.gameState = screen.gameOverState;
	    }
	}
	
	public void attack() {
		spriteCounter++;
		
		if(spriteCounter <= 5) {
	        spriteNum = 1;
	    }
		
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
		int currenWorldX = worldX;
		int currenWorldY = worldY;
		int collisionAreaWidth = collisionArea.width;
		int collisionAreaHeight = collisionArea.height;
		
		switch(facing){
		case "up": worldY -= attackArea.height; break;
		case "down": worldY += attackArea.height; break;
		case "left": worldX -= attackArea.width; break;
		case "right": worldX += attackArea.width; break;
		}
		
		collisionArea.width = attackArea.width;
		collisionArea.height = attackArea.height;
		
		int enemyIndex = screen.colCheck.checkEntity(this, screen.enemy);
		damageEnemy(enemyIndex, attack);

		worldX = currenWorldX;
		worldY = currenWorldY;
		collisionArea.width = collisionAreaWidth;
		collisionArea.height = collisionAreaHeight;

		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int index) {
		if(!screen.obj[Screen.currentMap][index].collision) {
			pickUpObjectNoCol(index);
		}
		else if(screen.obj[Screen.currentMap][index].collision) {
			pickUpObjectWithCol(index);
		}
	}
			
	public void pickUpObjectNoCol(int index) {
		if(index != 999) {
			if(screen.obj[Screen.currentMap][index].type == typePickupOnly) {
				screen.obj[Screen.currentMap][index].use(this);
				screen.obj[Screen.currentMap][index] = null;
			}
			else {
				String text;

				if(inventory.size() != inventorySize) {
					inventory.add(screen.obj[Screen.currentMap][index]);
					screen.playSFX(1);
					text = "Got " + screen.obj[Screen.currentMap][index].name + "!";
					screen.obj[Screen.currentMap][index] = null;
				}
				else {
					text = "Your inventory is full!";
				}
				screen.ui.addMessage(text);
			}
		}
	}
			
	public void pickUpObjectWithCol(int index) {
		if(index != 999 && screen.key.ePressed) {
			screen.gameState = screen.dialogueState;
			canAttack = false;
			
			if(inventory.size() != inventorySize) {
				if(screen.obj[Screen.currentMap][index].name.contains("Magatama")) {
					hasMaga++;
					System.out.println(hasMaga);
				}
				inventory.add(screen.obj[Screen.currentMap][index]);
				screen.playSFX(1);
				screen.ui.currentSpeechLine = "Got " + screen.obj[Screen.currentMap][index].name + "!";
				screen.obj[Screen.currentMap][index] = null;
			}
			else {
				screen.ui.currentSpeechLine = "Your inventory is full!";
			}
		}
	}
	
	public void interactNPC (int i) {
		if(screen.key.ePressed == true) {
			if (i != 999) {
				canAttack = false;
				screen.gameState = screen.dialogueState;
				screen.npc[Screen.currentMap][i].speak();
			}
		}	
	}
	
	private void contactEnemy(int i) {
		if(i != 999) {
			
			if(isInvincible == false && screen.enemy[Screen.currentMap][i].dying == false) {
				
				playSFX(6);
				
				int damage = screen.enemy[Screen.currentMap][i].attack - screen.player.defense;
				if(damage < 0) {
					damage = 0;
				}
				hp -= damage;
				isInvincible = true;
			}
		}
	}
	
	public void damageEnemy(int i, int attack) {
		if(i != 999) {
			
			if(!screen.enemy[Screen.currentMap][i].isInvincible) {
				
				playSFX(5);
				
				int damage = attack - screen.enemy[Screen.currentMap][i].defense;
				if(damage < 0) {
					damage = 0;
				}
				screen.enemy[Screen.currentMap][i].hp -= damage;
				screen.ui.addMessage(damage+" damage to "+screen.enemy[Screen.currentMap][i].name+"!");
				
				screen.enemy[Screen.currentMap][i].isInvincible = true;
				screen.enemy[Screen.currentMap][i].damageReaction();
				
				if (screen.enemy[Screen.currentMap][i].hp <= 0) {
					screen.enemy[Screen.currentMap][i].hp = 0;
					screen.enemy[Screen.currentMap][i].dying = true;
					screen.ui.addMessage("Killed the "+screen.enemy[Screen.currentMap][i].name+"!");
					screen.ui.addMessage("Exp +"+screen.enemy[Screen.currentMap][i].exp+"!");
					exp += screen.enemy[Screen.currentMap][i].exp;
					checkLevelUp();
				}
			}
		}
	}
	
	private void checkLevelUp() {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp *= 3;
			
			if(level%5 == 0) {
				maxHP++;
				maxMana++;
				strenght++;
				dexterity++;
			}
			else if(level%2 == 0 && level%3 == 0) {
				maxHP++;
				maxMana++;
				strenght++;
				dexterity++;
			}
			else if(level%2 == 0) {
				maxHP++;
				strenght++;
			}
			else if(level%3 == 0) {
				maxMana++;
				dexterity++;
			}
			if(currentMagatama != null) {
				attack = getAttack();
				defense = getDefense();
			}
			else {
				attack = strenght;
				defense = dexterity;
			}
		
			screen.playSFX(1);
			screen.gameState = screen.dialogueState;
			screen.ui.currentSpeechLine = "You are now at level "+level+"!\n"+"You feel stronger!";
		}
	}
	
	public void selectItem() {
		int itemIndex = screen.ui.getItemIndexOnSlot();
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == typeMaga) {
				screen.playSFX(1);
				currentMagatama = selectedItem;
				attack = getAttack();
				defense = getDefense();
				
				switch(screen.player.inventory.get(itemIndex).name) {
				case "Force Magatama":
					projectile = new Magic_WindBlast(screen);
					break;
					
				case "Fire Magatama":
					projectile = new Magic_Fireball(screen);
					break;
					
				case "Ice Magatama":
					projectile = new Magic_Icicle(screen);
					break;
				}
			}
			if(selectedItem.type == typeConsumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
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
		
		
		if(isInvincible == true) {
			 changeSpriteOpacity(g2, 0.4f);
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		changeSpriteOpacity(g2, 1f);
		
		if(screen.key.isDebugging == true) {
		    g2.setColor(new Color(255, 0, 0, 150));

		    int collisionX = screenX + collisionArea.x;
		    int collisionY = screenY + collisionArea.y;
		    g2.fillRect(collisionX, collisionY, collisionArea.width, collisionArea.height);
		    
		    tempScreenX = screenX + collisionArea.x;
			tempScreenY = screenY + collisionArea.y;	
			
			switch(facing) {
			case "up": tempScreenY = screenY - attackArea.height; break;
			case "down": tempScreenY = screenY + Screen.tileSize; break; 
			case "left": tempScreenX = screenX - attackArea.width; break;
			case "right": tempScreenX = screenX + Screen.tileSize; break;
			}	
			
			g2.fillRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
		}
	}
	
	public int getAttack() {
		return attack = strenght+currentMagatama.attackValue;
	}
	
	public int getDefense(){
		return defense = dexterity+currentMagatama.defenseValue;
	}
}
