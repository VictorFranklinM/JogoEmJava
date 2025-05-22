package entity;

import java.awt.Color;
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
	
	int hasMaga = 0;
	
	public Player(Screen screen, KeyInput keyInput) {
		this.screen = screen;
		this.key = keyInput;
		
		/* Como o personagem é renderizado a partir do pixel superior esquerdo, subtraimos meio tile de X e Y para que ele
		 * seja renderizado corretamente no meio da tela. */
		screenX = (screen.screenWidth / 2) - (screen.tileSize/2);
		screenY = (screen.screenHeight / 2) - (screen.tileSize/2);
		
		collisionArea = new Rectangle();
		collisionArea.x = (4 * screen.scale); // X do retângulo (começa no canto esquerdo).
		collisionArea.y = (8 * screen.scale); // Y (começa no canto superior).
		collisionArea.width = (8 * screen.scale); // Largura do retângulo.
		collisionArea.height = (7 * screen.scale); // Altura.
		
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		setDefaultValues();
		renderPlayer();
	}
	
	public void setDefaultValues() {
		// World X e Y são onde o personagem do player aparecerá no mapa inicialmente.
		worldX = screen.tileSize * 47;
		worldY = screen.tileSize * 29;
		speed = defaultSpeed;
		facing = "down";
	}
	
	public void renderPlayer() {
		up1 = setup("Up-1");
		up2 = setup("Up-2");
		up3 = setup("Up-3");
		down1 = setup("Down-1");
		down2 = setup("Down-2");
		down3 = setup("Down-3");
		left1 = setup("Left-1");
		left2 = setup("Left-2");
		left3 = setup("Left-3");
		right1 = setup("Right-1");
		right2 = setup("Right-2");
		right3 = setup("Right-3");

	}
	public BufferedImage setup(String imageName) {
		PerformanceTool performancePlayer = new PerformanceTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = performancePlayer.scaleImage(image, screen.tileSize, screen.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("sprite Path invalid for Player sprite");
		}
		return image;
	}

	
	
	// NOTA: tentar fazer com switch case pra ver se fica mais fluido.
	public void update() {
		if(key.upHold == true || key.downHold == true || key.leftHold == true || key.rightHold == true) {
			if(key.upHold == true) {
	    		facing = "up";
	    	}
	    	if(key.downHold == true) {
	    		facing = "down";
	    	}
	    	if(key.leftHold == true) {
	    		facing = "left";
	    	}
	    	if(key.rightHold == true) {
	    		facing = "right";
	    	}
	    	
	    	collision = false;
	    	screen.colCheck.checkTile(this);
	    	int objIndex = screen.colCheck.checkObject(this, true);
	    	interact(objIndex);
	    	
	    	if(collision == false) {
	    		switch(facing) {
	    		case "up":
		    		// Checa se o personagem está movendo na diagonal e recalcula o vetor de velocidade.
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
		    		// Checa se o personagem está movendo na diagonal e recalcula o vetor de velocidade.
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
		    		// Checa se o personagem está movendo na diagonal e recalcula o vetor de velocidade.
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
		    		// Checa se o personagem está movendo na diagonal e recalcula o vetor de velocidade.
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
			spriteNum = 2;
		}
	}
	
	public void interact(int index) {
		// OBS: Pra aumentar a speed do player, tirar o "final" de defaultSpeed e colocar defaultSpeed += (speed a ser incrementada).
		if(index != (screen.objPerScreen)) {
			String objName = screen.obj[index].name;
			
			if(key.ePressed == true) {
				switch(objName) {
				case "Magatama":
					hasMaga++;
					screen.obj[index] = null;
					System.out.println(+hasMaga+" Magatamas.");
				 playSFX(1);
					break;
					
				case "Portal":
					if(hasMaga > 0) {
					screen.obj[index] = null;
					}
					break;
				
				case "Cache Cube":
					screen.obj[index] = null;
					System.out.println("Ganhou item (ainda a implementar).");
					playSFX(0);
					break;
				}
			}
		}
		key.ePressed = false;
	}
	
	public void drawn(Graphics2D g2) {
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
		g2.drawImage(image, screenX, screenY, null);
	}
}
