
package tile;

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
	public int mapTileNum[][]; // Matriz que armazena os numeros dos tiles do mapa
	
	public TileOrganizer(Screen sc) {
		
		this.screen = sc;
		
		tile = new Tile[10]; //Quantidade maxima de tiles que podem ser usados, alterar conforme necessidade;
		
		mapTileNum = new int [sc.maxWorldCol][sc.maxWorldRow]; // Inicializa a matriz do mapa
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	// Metodo para carregar as imagens dos tiles
	public void getTileImage() {
			// Carregando diferentes tipos de tiles, quantidade maxima definida acima em tile
			setup(0,"barrel", true, Tile.noSoundIndex);
			setup(1,"cactus", true, Tile.noSoundIndex);
			setup(2,"grass", false, 3);
			setup(3,"sand", false, Tile.noSoundIndex);
			setup(4,"stone", false, Tile.noSoundIndex);
			setup(5,"wall", true, Tile.noSoundIndex);
			setup(6,"water", true, Tile.noSoundIndex);
		
			
	
	}
	public void setup(int index, String imagePath, boolean collision, int soundIndex) {
	    PerformanceTool performance = new PerformanceTool();
	    try {
	        tile[index] = new Tile();
	        tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
	        tile[index].image = performance.scaleImage(tile[index].image, screen.tileSize, screen.tileSize);
	        tile[index].collision = collision;
	        tile[index].soundIndex = soundIndex; // Aqui associa o som.
	    } catch(IOException e) {
	        e.printStackTrace();
	        System.out.println("Invalid Path/Invalid Image Format (Must be PNG)");
	    }
	}
	
	// Metodo para carregar o mapa a partir de um arquivo de texto
	public void loadMap(String mapPath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < screen.maxWorldCol && row < screen.maxWorldRow) {	
				
				String line = br.readLine(); // Le uma linha do arquivo
				
				while (col < screen.maxWorldCol) {
					
					String numbers[] = line.split(" "); // Divide a linha em numeros
					
					int num = Integer.parseInt(numbers[col]); // Converte a string em numero inteiro
					
					mapTileNum[col][row] = num; // Armazena o numero na matriz
					col++;
				}
				if(col == screen.maxWorldCol) {	// Se a linha foi completamente lida, passa para a proxima
					
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
		
		while(worldCol < screen.maxWorldCol && worldRow < screen.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow]; // Obtem o numero do tile
			
			int worldX = worldCol * screen.tileSize;
			int worldY = worldRow * screen.tileSize;
			int screenX = worldX - screen.player.worldX + screen.player.screenX;
			int screenY = worldY - screen.player.worldY + screen.player.screenY;
			
			// Checa se o tile e visivel na camera antes de renderizar.
			if(((worldX + screen.tileSize) > (screen.player.worldX - screen.player.screenX))
				&& ((worldX - screen.tileSize) < (screen.player.worldX + screen.player.screenX))
				&& ((worldY + screen.tileSize) > (screen.player.worldY - screen.player.screenY))
				&& ((worldY - screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null); // Desenha o tile
			}
			
			worldCol++;

			if (worldCol == screen.maxWorldCol) { // Move para a proxima linha ao atingir o limite de colunas
				worldCol = 0;
				worldRow++;
			}	
		}
	}
}