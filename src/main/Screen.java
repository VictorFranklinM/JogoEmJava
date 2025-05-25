package main;

import java.awt.Color; // Biblioteca para gerenciamento de cores.
import java.awt.Dimension;
import java.awt.Graphics; // Biblioteca para renderizações gráficas.
import java.awt.Graphics2D; // Biblioteca para renderizações de formas geométricas.
import java.awt.Toolkit;

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Interface da janela).

import entity.Player;
import object.SuperObject;
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
	
	public final int objPerScreen = 10;
	
	//SOM
	Sound music = new Sound();
	Sound sfx = new Sound();
	Sound tileSound = new Sound();
	
	TileOrganizer tileM = new TileOrganizer(this);
	KeyInput key = new KeyInput(this);
	Thread gameThread; // Cria uma linha de execução secundária para executar um código em segundo plano por cima do clock base.
	
	public UI ui = new UI(this);
	public CollisionChecker colCheck = new CollisionChecker(this);
	public ObjPlacer objPlacer = new ObjPlacer(this);
	
	public Player player = new Player(this,key);
	public SuperObject obj[] = new SuperObject[objPerScreen]; // new Object[x]. x é a quantidade de objetos que podem ser renderizados na tela ao mesmo tempo.
	
	public int gameState;
	public final int playState = 0;
	public final int pauseState = 1;
	
	int fps = 60; // Quantas vezes a tela vai ser atualizada por segundo.
	
	public Screen() {
		this.setBackground(Color.DARK_GRAY); // Define o plano de fundo da janela como a cor preta.
		this.setDoubleBuffered(true); // Vai renderizar os componentes gráficos em segundo plano em uma memória temporária.
		this.addKeyListener(key); // Implementação da classe KeyInput.
		this.setFocusable(true); // Indica se é possivel focar na janela do jogo.
		
	}
	
	public void setupGame() {
		objPlacer.placeObject();
		playMusic(2);
		gameState = playState;
	}
	
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();

    }
    
    // Função que atualiza a posição do jogador quando a tecla de movimento é pressionada.
    public void update() {
    	if(gameState==playState) {
    		player.update();
    		playTileSound();
    	}
    	if(gameState==pauseState) {
    		
    	}
    }
    
    // Função que renderiza os gráficos do jogo.
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Limpa o desenho anterior antes de renderizar novamente.
    	Graphics2D g2 = (Graphics2D)g; // Faz um casting para Graphics2D para poder manipular x, y e outros atributos mais avançados.
    	
    	//DEBUG (TESTADOR DE VELOCIDADE DE RENDERIZAÇÃO
    	long drawBegin = 0;
    	if(key.isDebugging == true) {
    		drawBegin = System.nanoTime();
    	}
    	
    	tileM.draw(g2); // mapa
    	
    	for(int i = 0; i < obj.length; i++) {
    		if(obj[i] != null) {
    			obj[i].draw(g2, this);
    		}
    	}
    	// DESENHAR PLAYER
    	player.draw(g2); // Função que renderiza o player.
    	
    	//UI
    	ui.draw(g2);
    	
    	long drawStop = System.nanoTime();
    	if(key.isDebugging == true) {
    	long elapsedTime = drawStop - drawBegin;
    	g2.setColor(Color.white);
    	g2.drawString("Render Time: "+ elapsedTime, 10, 400);
    	System.out.println("Render Time = "+ elapsedTime);
    	
    	}
    	//liberar memória
    	g2.dispose();
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
	
	public void playMusic(int i) {
		music.setFile(i);
		music.loop();
		music.play();

	}
	
	public void stopMusic() {
		music.stop();
	}

	public void playSFX(int i) {
		sfx.setFile(i);
		sfx.play();
	}

	private int lastTileCol = -1;
	private int lastTileRow = -1;
	private int lastTileSoundIndex = -1;

	private boolean wasMoving = false;

	public void playTileSound() {
	    int footX = player.worldX + player.collisionArea.x + player.collisionArea.width / 2;
	    int footY = player.worldY + player.collisionArea.y + player.collisionArea.height;

	    int col = footX / tileSize;
	    int row = footY / tileSize;

	    boolean movedTile = col != lastTileCol || row != lastTileRow;

	    if (player.isMoving == false) {
	        if (tileSound.isPlaying()) {
	            tileSound.stop();
	        }
	        wasMoving = false;
	        return;
	    }

	    if (wasMoving == false) {
	        wasMoving = true;

	        if (col >= 0 && col < maxWorldCol && row >= 0 && row < maxWorldRow) {
	            int tileNum = tileM.mapTileNum[col][row];
	            int soundIndex = tileM.tile[tileNum].soundIndex;

	            if (soundIndex != -1) {
	                if (!tileSound.isPlaying()) {
	                    tileSound.setFile(soundIndex);
	                    tileSound.loop();
	                }
	                lastTileSoundIndex = soundIndex;
	            }
	            lastTileCol = col;
	            lastTileRow = row;
	        }
	        return;
	    }

	    if (movedTile) {
	        if (col >= 0 && col < maxWorldCol && row >= 0 && row < maxWorldRow) {
	            int tileNum = tileM.mapTileNum[col][row];
	            int soundIndex = tileM.tile[tileNum].soundIndex;

	            if (soundIndex != lastTileSoundIndex) {
	                if (tileSound.isPlaying()) {
	                    tileSound.stop();
	                }
	                if (soundIndex != -1) {
	                    tileSound.setFile(soundIndex);
	                    tileSound.loop();
	                }
	                lastTileSoundIndex = soundIndex;
	            }
	            lastTileCol = col;
	            lastTileRow = row;
	        }
	    }
	}
}