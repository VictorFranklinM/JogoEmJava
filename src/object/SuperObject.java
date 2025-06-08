package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.PerformanceTool;
import main.Screen;

public class SuperObject {
	public BufferedImage image,image2,image3,image4,image5,image6;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle collisionArea = new Rectangle();
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	PerformanceTool performanceObj = new PerformanceTool();
	
	public void draw(Graphics2D g2, Screen screen) {
		int screenX = worldX - screen.player.worldX + screen.player.screenX;
		int screenY = worldY - screen.player.worldY + screen.player.screenY;
		
		if(((worldX + Screen.tileSize) > (screen.player.worldX - screen.player.screenX))
			&& ((worldX - Screen.tileSize) < (screen.player.worldX + screen.player.screenX))
			&& ((worldY + Screen.tileSize) > (screen.player.worldY - screen.player.screenY))
			&& ((worldY - Screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
			
			g2.drawImage(image, screenX, screenY, Screen.tileSize, Screen.tileSize, null); // Desenha o tile
		}
	}

}
