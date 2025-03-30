package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Screen;

public class TileOrganizer {
	
	Screen sc; // Referência à tela principal
	Tile[] tile; // Array de tiles
	int mapTileNum[][]; // Matriz que armazena os números dos tiles do mapa
	
	public TileOrganizer(Screen sc) {
		
		this.sc = sc;
		
		tile = new Tile[10]; //Quantidade máxima de tiles que podem ser usados, alterar conforme necessidade;
		
		mapTileNum = new int [sc.horizontalTiles][sc.verticalTiles]; // Inicializa a matriz do mapa
		
		getTileImage();
		loadMap("/maps/mapa01.txt");
	}
	
	// Método para carregar as imagens dos tiles
	public void getTileImage() {
		
		try {
			
			// Carregando diferentes tipos de tiles, quantidade máxima definida acima em tile
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/blank.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch(IOException e) {
			e.printStackTrace(); // Exibe erro caso ocorra problema ao carregar as imagens
		}
	}
	
	// Método para carregar o mapa a partir de um arquivo de texto
	public void loadMap(String mapPath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < sc.horizontalTiles && row < sc.verticalTiles) {	
				
				String line = br.readLine(); // Lê uma linha do arquivo
				
				while (col < sc.horizontalTiles) {
					
					String numbers[] = line.split(" "); // Divide a linha em números
					
					int num = Integer.parseInt(numbers[col]); // Converte a string em número inteiro
					
					mapTileNum[col][row] = num; // Armazena o número na matriz
					col++;
				}
				if(col == sc.horizontalTiles) {	// Se a linha foi completamente lida, passa para a próxima
					
					col = 0;
					row++;					
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace(); // Exibe erro caso ocorra problema ao carregar o mapa
		}
	}
	
	// Método para desenhar os tiles na tela
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < sc.horizontalTiles && row < sc.verticalTiles) {
			
			int tileNum = mapTileNum[col][row]; // Obtém o número do tile
			
			g2.drawImage(tile[tileNum].image, x, y, sc.tileSize, sc.tileSize, null); // Desenha a imagem do tile
			
			col++;
			x += sc.tileSize;
			if (col == sc.horizontalTiles) { // Move para a próxima linha ao atingir o limite de colunas
				col = 0;
				x = 0;
				row++;
				y += sc.tileSize;
			}	
		}
	}
}