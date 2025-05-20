
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Screen;

public class TileOrganizer {
	
	Screen screen; // Referência à tela principal
	public Tile[] tile; // Array de tiles
	public int mapTileNum[][]; // Matriz que armazena os números dos tiles do mapa
	
	public TileOrganizer(Screen sc) {
		
		this.screen = sc;
		
		tile = new Tile[10]; //Quantidade máxima de tiles que podem ser usados, alterar conforme necessidade;
		
		mapTileNum = new int [sc.maxWorldCol][sc.maxWorldRow]; // Inicializa a matriz do mapa
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	// Método para carregar as imagens dos tiles
	public void getTileImage() {
		
		try {
			
			// Carregando diferentes tipos de tiles, quantidade máxima definida acima em tile
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/barrel.png"));
			tile[0].collision = true;
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cactus.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[6].collision = true;
			
		}catch(IOException e) {
			e.printStackTrace(); // Exibe erro caso ocorra problema ao carregar as imagens
			System.out.println("Invalid image path.");
		}
	}
	
	// Método para carregar o mapa a partir de um arquivo de texto
	public void loadMap(String mapPath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < screen.maxWorldCol && row < screen.maxWorldRow) {	
				
				String line = br.readLine(); // Lê uma linha do arquivo
				
				while (col < screen.maxWorldCol) {
					
					String numbers[] = line.split(" "); // Divide a linha em números
					
					int num = Integer.parseInt(numbers[col]); // Converte a string em número inteiro
					
					mapTileNum[col][row] = num; // Armazena o número na matriz
					col++;
				}
				if(col == screen.maxWorldCol) {	// Se a linha foi completamente lida, passa para a próxima
					
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
	
	// Método para desenhar os tiles na tela
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < screen.maxWorldCol && worldRow < screen.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow]; // Obtém o número do tile
			
			int worldX = worldCol * screen.tileSize;
			int worldY = worldRow * screen.tileSize;
			int screenX = worldX - screen.player.worldX + screen.player.screenX;
			int screenY = worldY - screen.player.worldY + screen.player.screenY;
			
			// Checa se o tile é visível na câmera antes de renderizar.
			if(((worldX + screen.tileSize) > (screen.player.worldX - screen.player.screenX))
				&& ((worldX - screen.tileSize) < (screen.player.worldX + screen.player.screenX))
				&& ((worldY + screen.tileSize) > (screen.player.worldY - screen.player.screenY))
				&& ((worldY - screen.tileSize) < (screen.player.worldY + screen.player.screenY))) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, screen.tileSize, screen.tileSize, null); // Desenha o tile
			}
			
			worldCol++;

			if (worldCol == screen.maxWorldCol) { // Move para a próxima linha ao atingir o limite de colunas
				worldCol = 0;
				worldRow++;
			}	
		}
	}
}