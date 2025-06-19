
package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.PerformanceTool;
import main.Screen;

public class TileOrganizer {
	
	Screen screen;
	public Tile[] tile;
	public int mapTileNum[][][];
	public boolean drawPath = false;
	private int maxTileNum = 20;
	public TileOrganizer(Screen screen) {
		
		this.screen = screen;
		
		tile = new Tile[maxTileNum];
		
		mapTileNum = new int [Screen.maxMap][Screen.maxWorldCol][Screen.maxWorldRow];
		
		getTileImage();
		// IF THE MAP ID IS CHANGED, YOU NEED TO CHANGE THE DEBUG MODE ON KEYINPUT AND MAPNUM ON THE PLACERS (NPCS, OBJS AND ENEMIES)
		loadMap("/maps/world01.txt", 0);
		loadMap("/maps/dungeon.txt", 1);
		loadMap("/maps/dungeonB1.txt", 2);
		loadMap("/maps/dungeonB2.txt", 3);
	}
	
	public void getTileImage() {
		int i = 0;
		
		setup(i,"dungeonBG", false, Tile.noSoundIndex);
		i++;
		setup(i,"dungeonEntrance", true, Tile.noSoundIndex);
		i++;
		setup(i,"dungeonRoof", true, Tile.noSoundIndex);
		i++;
		setup(i,"grass", false, 3);
		i++;
		setup(i,"grassTosnow", false, Tile.noSoundIndex);
		i++;
		setup(i,"magma", false, Tile.noSoundIndex);
		i++;
		setup(i,"sand", false, Tile.noSoundIndex);
		i++;
		setup(i,"snow", false, Tile.noSoundIndex);
		i++;
		setup(i,"stone", false, Tile.noSoundIndex);
		i++;
		setup(i,"tree", true, Tile.noSoundIndex);
		i++;
		setup(i,"treeTrunk", false, Tile.noSoundIndex);
		i++;
		setup(i,"wall", true, Tile.noSoundIndex);
		i++;
		setup(i,"water", true, Tile.noSoundIndex);
		i++;
		setup(i,"stairsDown", false, Tile.noSoundIndex);
		i++;
		setup(i,"stairsUp", false, Tile.noSoundIndex);
		i++;
		
	}
	public void setup(int index, String imagePath, boolean collision, int soundIndex) {
	    PerformanceTool performance = new PerformanceTool();
	    try {
	        tile[index] = new Tile();
	        tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
	        tile[index].image = performance.scaleImage(tile[index].image, Screen.tileSize, Screen.tileSize);
	        tile[index].collision = collision;
	        tile[index].soundIndex = soundIndex;
	    } catch(IOException e) {
	        e.printStackTrace();
	        System.out.println("Invalid Path/Invalid Image Format (Must be PNG)");
	    }
	}
	
	public void loadMap(String mapPath, int map) {
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {	
				
				String line = br.readLine();
				
				while (col < Screen.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == Screen.maxWorldCol) {
					
					col = 0;
					row++;					
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Invalid map path.");
		}
	}

	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < Screen.maxWorldCol && worldRow < Screen.maxWorldRow) {
			
			int tileNum = mapTileNum[Screen.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * Screen.tileSize;
			int worldY = worldRow * Screen.tileSize;
			int screenX = worldX - screen.player.worldX + screen.player.screenX;
			int screenY = worldY - screen.player.worldY + screen.player.screenY;
			
			if(((worldX + Screen.tileSize) > (screen.player.worldX - screen.player.screenX))
				&& ((worldX - Screen.tileSize) < (screen.player.worldX + screen.player.screenX))
				&& ((worldY + Screen.tileSize) > (screen.player.worldY - screen.player.screenY))
				&& ((worldY - Screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;

			if (worldCol == Screen.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}	
	
		}
		if(drawPath == true) {
			g2.setColor(new Color(255,0,0,70));
			
			for(int i = 0;i < screen.pFinder.pathList.size(); i++) {
				
				int worldX = screen.pFinder.pathList.get(i).col *Screen.tileSize;
				int worldY = screen.pFinder.pathList.get(i).row * Screen.tileSize;
				int screenX = worldX - screen.player.worldX + screen.player.screenX;
				int screenY = worldY - screen.player.worldY + screen.player.screenY;
				
				g2.fillRect(screenX, screenY, Screen.tileSize, Screen.tileSize);
			}
		}
	}
}