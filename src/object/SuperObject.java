package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Screen;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	
	public void draw(Graphics2D g2, Screen screen) {
		int screenX = worldX - screen.player.worldX + screen.player.screenX;
		int screenY = worldY - screen.player.worldY + screen.player.screenY;
		
		if(((worldX + screen.tileSize) > (screen.player.worldX - screen.player.screenX))
			&& ((worldX - screen.tileSize) < (screen.player.worldX + screen.player.screenX))
			&& ((worldY + screen.tileSize) > (screen.player.worldY - screen.player.screenY))
			&& ((worldY - screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
			
			g2.drawImage(image, screenX, screenY, screen.tileSize, screen.tileSize, null); // Desenha o tile
		}
	}
}
