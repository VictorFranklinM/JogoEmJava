package main;

import java.awt.Color; // Biblioteca para gerenciamento de cores.
import java.awt.Graphics; // Biblioteca para renderizações gráficas.
import java.awt.Graphics2D; // Biblioteca para renderizações de formas geométricas.

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Interface da janela).


// Sub-Classe da Classe JPanel.
public class Screen extends JPanel implements Runnable{
	
	final int originalTileSize = 16; // Tamanho dos Tiles do jogo. (16x16).
	final int scale = 3; // Escala dos pixels.
	final int tileSize = originalTileSize * scale; // Tile redimensionado.
	
	final int horizontalTiles = 16; // Quantos tiles horizontais (ainda não tá implementado);
	final int verticalTiles = 9; // Quantos tiles verticais (ainda não tá implementado);	
	
	KeyInput key = new KeyInput();
	
	Thread gameThread; // Cria uma linha de execução secundária para executar um código em segundo plano por cima do clock base.
	
	int playerX = 100; // Variável da posição X do jogador.
	int playerY = 100; // Variável da posição Y do jogador.
	int movSpeed = 4; // Variável da velocidade de movimento do jogador.
	int fps = 60; // Quantas vezes a tela vai ser atualizada por segundo.
	
	public Screen() {
		this.setBackground(Color.BLACK); // Define o plano de fundo da janela como a cor preta.
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
    	if(key.upHold == true) {
    		playerY -= movSpeed;
    	}
    	else if(key.downHold == true) {
    		playerY += movSpeed;
    	}
    	else if(key.leftHold == true) {
    		playerX -= movSpeed;
    	}
    	else if(key.rightHold == true) {
    		playerX += movSpeed;
    	}
    }
    
    // Função que renderiza os gráficos do jogo.
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Limpa o desenho anterior antes de renderizar novamente.
    	Graphics2D g2 = (Graphics2D)g; // Faz um casting para Graphics2D para poder manipular x, y e outros atributos mais avançados.
    	g2.setColor(Color.WHITE); // Escolhendo a cor do nosso objeto g2, que seria o quadrado.
    	g2.fillRect(playerX, playerY, tileSize, tileSize); // Criando nosso quadrado g2 com as coordenadas x, y e o tamanho 16/16.
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
