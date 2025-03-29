package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Screen;

public class TileOrganizer {
	
	Screen sc;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileOrganizer(Screen sc) {
		
		this.sc = sc;
		
		tile = new Tile[10]; //Quantidade máxima de tiles que podem ser usados, alterar conforme necessidade;
		
		mapTileNum = new int [sc.horizontalTiles][sc.verticalTiles];
		
		getTileImage();
		loadMap("/maps/mapa01.txt");
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/blank.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String mapPath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < sc.horizontalTiles && row < sc.verticalTiles) {	
				
				String line = br.readLine();
				
				while (col < sc.horizontalTiles) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == sc.horizontalTiles) {		
					
					col = 0;
					row++;					
				}
			}
			br.close();
		}catch(Exception e) {	
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < sc.horizontalTiles && row < sc.verticalTiles) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, sc.tileSize, sc.tileSize, null);
			
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