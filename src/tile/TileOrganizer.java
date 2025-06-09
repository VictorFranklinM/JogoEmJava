
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
	
	Screen screen; // Referencia a tela principal
	public Tile[] tile; // Array de tiles
	public int mapTileNum[][][]; // Matriz que armazena os numeros dos tiles do mapa
	public boolean drawPath = false;
	public TileOrganizer(Screen screen) {
		
		this.screen = screen;
		
		tile = new Tile[20]; //Quantidade maxima de tiles que podem ser usados, alterar conforme necessidade;
		
		mapTileNum = new int [Screen.maxMap][Screen.maxWorldCol][Screen.maxWorldRow]; // Inicializa a matriz do mapa
		
		getTileImage();
		// SE MUDAR O ID DOS MAPAS TEM QUE MUDAR O DEBUG MODE NO KEYINPUT E O MAPNUM NOS PLACERS DOS NPCS, OBJETOS E INIMIGOS
		loadMap("/maps/world01.txt", 0);
		loadMap("/maps/dungeon.txt", 1);
	}
	
	// Metodo para carregar as imagens dos tiles
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
			
	
	}
	public void setup(int index, String imagePath, boolean collision, int soundIndex) {
	    PerformanceTool performance = new PerformanceTool();
	    try {
	        tile[index] = new Tile();
	        tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
	        tile[index].image = performance.scaleImage(tile[index].image, Screen.tileSize, Screen.tileSize);
	        tile[index].collision = collision;
	        tile[index].soundIndex = soundIndex; // Aqui associa o som.
	    } catch(IOException e) {
	        e.printStackTrace();
	        System.out.println("Invalid Path/Invalid Image Format (Must be PNG)");
	    }
	}
	
	// Metodo para carregar o mapa a partir de um arquivo de texto
	public void loadMap(String mapPath, int map) {
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {	
				
				String line = br.readLine(); // Le uma linha do arquivo
				
				while (col < Screen.maxWorldCol) {
					
					String numbers[] = line.split(" "); // Divide a linha em numeros
					
					int num = Integer.parseInt(numbers[col]); // Converte a string em numero inteiro
					
					mapTileNum[map][col][row] = num; // Armazena o numero na matriz
					col++;
				}
				if(col == Screen.maxWorldCol) {	// Se a linha foi completamente lida, passa para a proxima
					
					col = 0;
					row++;					
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace(); // Exibe erro caso ocorra problema ao carregar o mapa
			System.out.println("Invalid map path.");
		}
	}
	
	// Metodo para desenhar os tiles na tela
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < Screen.maxWorldCol && worldRow < Screen.maxWorldRow) {
			
			int tileNum = mapTileNum[Screen.currentMap][worldCol][worldRow]; // Obtem o numero do tile
			
			int worldX = worldCol * Screen.tileSize;
			int worldY = worldRow * Screen.tileSize;
			int screenX = worldX - screen.player.worldX + screen.player.screenX;
			int screenY = worldY - screen.player.worldY + screen.player.screenY;
			
			// Checa se o tile e visivel na camera antes de renderizar.
			if(((worldX + Screen.tileSize) > (screen.player.worldX - screen.player.screenX))
				&& ((worldX - Screen.tileSize) < (screen.player.worldX + screen.player.screenX))
				&& ((worldY + Screen.tileSize) > (screen.player.worldY - screen.player.screenY))
				&& ((worldY - Screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null); // Desenha o tile
			}
			
			worldCol++;

			if (worldCol == Screen.maxWorldCol) { // Move para a proxima linha ao atingir o limite de colunas
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