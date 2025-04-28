package main;

import java.awt.Color; // Biblioteca para gerenciamento de cores.
import java.awt.Dimension;
import java.awt.Graphics; // Biblioteca para renderizações gráficas.
import java.awt.Graphics2D; // Biblioteca para renderizações de formas geométricas.
import java.awt.Toolkit;

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Interface da janela).

import entity.Player;
import tile.TileOrganizer;


// Sub-Classe da Classe JPanel.
public class Screen extends JPanel implements Runnable{
	
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Pega as proporções da tela.
	public final int screenWidth = (int) screenSize.getWidth(); // Pega apenas a largura.
	public final int screenHeight = (int) screenSize.getHeight(); // Pega apenas a altura.
	
	public final int originalTileSize = 16; // Tamanho dos Tiles do jogo. (16x16).
	public final int scale = 4; // Escala dos pixels.
	public final int tileSize = originalTileSize * scale; // Tile redimensionado.
	
	public final int horizontalTiles = 22; // Quantos tiles horizontais cabem na tela.
	public final int verticalTiles = 12; // Quantos tiles verticais cabem na tela.
	
	public final int maxWorldCol = 100;
	public final int maxWorldRow = 100;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	TileOrganizer tileM = new TileOrganizer(this);
	KeyInput key = new KeyInput();
	Thread gameThread; // Cria uma linha de execução secundária para executar um código em segundo plano por cima do clock base.
	public CollisionChecker colCheck = new CollisionChecker(this);
	public Player player = new Player(this,key);
	
	int fps = 60; // Quantas vezes a tela vai ser atualizada por segundo.
	
	public Screen() {
		this.setBackground(Color.DARK_GRAY); // Define o plano de fundo da janela como a cor preta.
		this.setDoubleBuffered(true); // Vai renderizar os componentes gráficos em segundo plano em uma memória temporária.
		this.addKeyListener(key); // Implementação da classe KeyInput.
		this.setFocusable(true); // Indica se é possivel focar na janela do jogo.
		
	}
	
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();

    }
    
    // Função que atualiza a posição do jogador quando a tecla de movimento é pressionada.
    public void update() {
    	player.update();
    }
    
    // Função que renderiza os gráficos do jogo.
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Limpa o desenho anterior antes de renderizar novamente.
    	Graphics2D g2 = (Graphics2D)g; // Faz um casting para Graphics2D para poder manipular x, y e outros atributos mais avançados.
    	
    	tileM.draw(g2); // mapa
    	
    	player.drawn(g2); // Função que renderiza o player.
    	g2.dispose(); // Liberando memória após função gráfica.	
    	
    }

    // Linha de execução secundária do jogo, onde ocorrem as coisas na tela.
	public void run() {
		double drawInterval = 1000000000/fps; // Nosso intervalo de atualizações necessárias(1 nano segundo convertido para segundo/fps.
		double delta = 0; // Nossa variável de controle, ela servirá como um contador de segundos.
		long lastTime = System.nanoTime(); // Armazenamos nosso tempo atual (que será trocado, então ele é nosso ultimo tempo).
		long currentTime; // Tempo atual, este será contado na hora de pintar e desenhar.
		
		// Enquanto o thread do jogo (tempo) está rodando, ele irá contar o tempo.
		while (gameThread != null) {
			currentTime = System.nanoTime(); // Nosso tempo atual na thread recebe o tempo em nano segundos.
			delta += (currentTime - lastTime) / drawInterval; // Nosso delta vai receber o resto da diferença entre o tempo passado e o atual, dividido pelo fps. E quando der 1 segundo, irá aplicar.
			lastTime = currentTime; // Resetamos nosso tempo passado, para atualizar o loop, já que o cálculo ja foi feito.
			
			//Quando delta der o tempo de 1/60 frames por segundo, ele irá atualizar um frame.
			if(delta >= 1) { 
				update(); // Chama a função de atualizar a posição do jogador.
				repaint(); // Redesenha o objeto.
				delta --; // Reduz o delta para recalcular no nosso loop.
			}
			
		}
		
	}

}
