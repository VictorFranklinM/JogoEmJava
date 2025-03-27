package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Screen;

public class TileOrganizer {
	
	Screen sc;
	Tile[] tile;
	
	public TileOrganizer(Screen sc) {
		
		this.sc = sc;
		
		tile = new Tile[10]; //Quantidade de tiles a criar, alterar depois;
		
		getTileImage();
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < sc.horizontalTiles && row < sc.verticalTiles) {
			g2.drawImage(tile[0].image, x, y, sc.tileSize, sc.tileSize, null);
			
			col++;
			x += sc.tileSize;
			if (col == sc.horizontalTiles) {
				col = 0;
				x = 0;
				row++;
				y += sc.tileSize;
			}
			
		}
	}
}