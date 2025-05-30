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
	
	KeyInput key;
	
	public final int screenX;
	public final int screenY;
	private final int defaultSpeed = 5;
	
	public int hasMaga = 0;
	
	public boolean isMoving = false;
	
	private int standingCounter = 0;
	
	public Player(Screen screen, KeyInput keyInput) {
		super(screen);
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
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		setDefaultValues();
		getImage();
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
		up1 = setup("/player/Up-1");
		up2 = setup("/player/Up-2");
		up3 = setup("/player/Up-3");
		down1 = setup("/player/Down-1");
		down2 = setup("/player/Down-2");
		down3 = setup("/player/Down-3");
		left1 = setup("/player/Left-1");
		left2 = setup("/player/Left-2");
		left3 = setup("/player/Left-3");
		right1 = setup("/player/Right-1");
		right2 = setup("/player/Right-2");
		right3 = setup("/player/Right-3");

	}	
	
	// NOTA: tentar fazer com switch case pra ver se fica mais fluido.
	public void update() {
		isMoving = key.upHold || key.downHold || key.leftHold || key.rightHold;

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
	    	int monsterIndex = screen.colCheck.checkEntity(this,screen.monster);
	    	contactMonster(monsterIndex);
	    	screen.eventManager.checkEvent();
	    	
	    	screen.key.ePressed = false;
	    	
	    	if(collision == false) {
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
		if (i != screen.npcPerScreen) {
			if(screen.key.ePressed == true) {
				screen.gameState = screen.dialogueState;
			screen.npc[i].speak();
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
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
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
		if(isInvincible == true) {
			 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		}
		g2.drawImage(image, screenX, screenY, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
		g2.setFont(new Font("Arial",Font.PLAIN,10));
		g2.setColor(Color.WHITE);
		g2.drawString("Invincibility Frames: "+invincibilityTimer,10,30);
	}
}
