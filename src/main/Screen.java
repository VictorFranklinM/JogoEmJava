package main;

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Define as interações quando a janela está selecionada)

// Sub-Classe da Classe JPanel
public class Screen extends JPanel{
	
	final int originalTileSize = 16; // Tamanho dos Tiles do jogo. (16x16)
	final int scale = 3; // Escala dos pixels.
	final int tileSize = originalTileSize * scale; // Tile redimensionado.
	
	final int horizontalTiles = 16; // Quantos tiles horizontais (ainda não tá implementado);
	final int verticalTiles = 9; // Quantos tiles verticais (ainda não tá implementado);
	
	// Tamanho da tela (comentei pq não sei se precisa, já que a tela tá maximizada, então o calculo de tamanho é outro)
	//final int screenWidth = tileSize * horizontalTiles; 
	//final int screenHeight = tileSize * verticalTiles;
	
}
