package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.KeyInput;
import main.PerformanceTool;
import main.Screen;

public class Player extends Entity{
	Screen screen;
	
	KeyInput key;
	
	public final int screenX;
	public final int screenY;
	private final int defaultSpeed = 5;
	
	public int hasMaga = 0;
	
	public boolean isMoving = false;
	
	private int standingCounter = 0;
	
	public Player(Screen screen, KeyInput keyInput) {
		super(screen);
		this.screen = screen;
		
		this.key = keyInput;
		
		/* Como o personagem e renderizado a partir do pixel superior esquerdo, subtraimos meio tile de X e Y para que ele
		 * seja renderizado corretamente no meio da tela. */
		screenX = (screen.screenWidth / 2) - (screen.tileSize/2);
		screenY = (screen.screenHeight / 2) - (screen.tileSize/2);
		
		collisionArea = new Rectangle();
		collisionArea.x = (4 * screen.scale); // X do retângulo (comeca no canto esquerdo).
		collisionArea.y = (8 * screen.scale); // Y (comeca no canto superior).
		collisionArea.width = (8 * screen.scale); // Largura do retângulo.
		collisionArea.height = (7 * screen.scale); // Altura.
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		setDefaultValues();
		getImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultValues() {
		// World X e Y sao onde o personagem do player aparecera no mapa inicialmente.
		worldX = screen.tileSize * 46;
		worldY = screen.tileSize * 29;
		speed = defaultSpeed;
		facing = "down";
		
		// Status
		maxHP = 3;
		hp = maxHP;
		maxMana = 3;
		mana = maxMana;
	}
	
	public void getImage() {
		up1 = setup("/player/Up-1", screen.tileSize, screen.tileSize);
		up2 = setup("/player/Up-2", screen.tileSize, screen.tileSize);
		up3 = setup("/player/Up-3", screen.tileSize, screen.tileSize);
		down1 = setup("/player/Down-1", screen.tileSize, screen.tileSize);
		down2 = setup("/player/Down-2", screen.tileSize, screen.tileSize);
		down3 = setup("/player/Down-3", screen.tileSize, screen.tileSize);
		left1 = setup("/player/Left-1", screen.tileSize, screen.tileSize);
		left2 = setup("/player/Left-2", screen.tileSize, screen.tileSize);
		left3 = setup("/player/Left-3", screen.tileSize, screen.tileSize);
		right1 = setup("/player/Right-1", screen.tileSize, screen.tileSize);
		right2 = setup("/player/Right-2", screen.tileSize, screen.tileSize);
		right3 = setup("/player/Right-3", screen.tileSize, screen.tileSize);

	}	
	public void getPlayerAttackImage() {
		
		attackUp1 = setup("/player/AttackUp-1", screen.tileSize, screen.tileSize*2);
		attackUp2 = setup("/player/AttackUp-2", screen.tileSize, screen.tileSize*2);
		attackDown1 = setup("/player/AttackDown-1", screen.tileSize, screen.tileSize*2);
		attackDown2 = setup("/player/AttackDown-2", screen.tileSize, screen.tileSize*2);
		attackLeft1 = setup("/player/AttackLeft-1", screen.tileSize*2, screen.tileSize);
		attackLeft2 = setup("/player/AttackLeft-2", screen.tileSize*2, screen.tileSize);
		attackRight1 = setup("/player/AttackRight-1", screen.tileSize*2, screen.tileSize);
		attackRight2 = setup("/player/AttackRight-2", screen.tileSize*2, screen.tileSize);
	}
	// NOTA: tentar fazer com switch case pra ver se fica mais fluido.
	public void update() {
		
		if (attacking == true) {
			
			attacking();
			
		}
		else if ( key.upHold || key.downHold || key.leftHold || key.rightHold || key.ePressed) {
			isMoving = true;
			
		
	    if(isMoving) {
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
	    	interact(objIndex);
	    	
	    	int npcIndex = screen.colCheck.checkEntity(this, screen.npc);
	    	interactNPC(npcIndex);
	    	int monsterIndex = screen.colCheck.checkEntity(this,screen.enemy);
	    	contactMonster(monsterIndex);
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
	    	
	    	screen.key.ePressed = false;
	    	
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
		else {
			standingCounter++;
			if(standingCounter == 15) {
				spriteNum = 2;
				standingCounter = 0;
			}
		}
	    
	    if(isInvincible == true) {
	    	invincibilityTimer++;
	    	if(invincibilityTimer > 60) {
	    		isInvincible = false;
	    		invincibilityTimer = 0;
	    	}
	    	
	    }
		}
	    
	   
	}
	public void attacking () {
		
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
		damageEnemy(enemyIndex);
		
		
		
		
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
	

	public void interact(int index) {
		// OBS: Pra aumentar a speed do player, tirar o "final" de defaultSpeed e colocar defaultSpeed += (speed a ser incrementada).
		if(index != (screen.objPerScreen)) {
			String objName = screen.obj[index].name;
			
			if(key.ePressed == true) {
				screen.gameState = screen.dialogueState;
				switch(objName) {
				case "Magatama":
					hasMaga++;
					screen.obj[index] = null;
					screen.ui.currentSpeechLine = "You got a magatama!";
					playSFX(1);
					break;
					
				case "Portal":
					if(hasMaga > 0) {
						screen.obj[index] = null;
						screen.ui.currentSpeechLine = "Opened the portal with the Magatama!";
					}
					else {
						screen.ui.currentSpeechLine = "You need a Magatama to open the portal!";
					}
					break;
				
				case "Cache Cube":
					screen.obj[index] = null;
					screen.ui.currentSpeechLine = "Got an item! Work In Progress";
					playSFX(0);
					break;
				}
			}
		}
	}
	
	public void interactNPC (int i) {
		
		if(screen.key.ePressed == true) {
			
			if (i != screen.npcPerScreen) {
				
				screen.gameState = screen.dialogueState;
				screen.npc[i].speak();
				
			}
			else {
					attacking = true;
		     }
		}
			
	}
	
	private void contactMonster(int i) {
		
		if(i != screen.npcPerScreen) {
			if(isInvincible == false) {
			hp -= 1;
			isInvincible = true;
			}
		}
	}
	public void damageEnemy(int i) {
		if(i != 999 && screen.enemy[i] !=null) {
			
			if(!screen.enemy[i].isInvincible) {
				
				screen.enemy[i].hp -=1;
				screen.enemy[i].isInvincible = true;
				
				if (screen.enemy[i].hp <= 0) {
					screen.enemy[i] = null;
				}
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
				tempScreenY = screenY - screen.tileSize;
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
				tempScreenX = screenX - screen.tileSize;
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
			 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	
		g2.setFont(new Font("Arial",Font.PLAIN,10));
		g2.setColor(Color.WHITE);
		g2.drawString("Invincibility Frames: "+invincibilityTimer,10,30);
	}
}
