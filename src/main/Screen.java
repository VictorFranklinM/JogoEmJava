package main;

import java.awt.Color; // Biblioteca para gerenciamento de cores.
import java.awt.Dimension;
import java.awt.Graphics; // Biblioteca para renderizacoes graficas.
import java.awt.Graphics2D; // Biblioteca para renderizacoes de formas geometricas.
import java.awt.Toolkit;

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Interface da janela).

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileOrganizer;


// Sub-Classe da Classe JPanel.
public class Screen extends JPanel implements Runnable{
	
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Pega as proporcoes da tela.
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
	public final int npcPerScreen = 5;
	
	//SOM
	Sound music = new Sound();
	Sound sfx = new Sound();
	TileSoundManager tsm = new TileSoundManager(this);

	TileOrganizer tileM = new TileOrganizer(this);
	public KeyInput key = new KeyInput(this);
	Thread gameThread; // Cria uma linha de execucao secundaria para executar um codigo em segundo plano por cima do clock base.
	
	public UI ui = new UI(this);
	public CollisionChecker colCheck = new CollisionChecker(this);
	public ObjPlacer objPlacer = new ObjPlacer(this);
	public NpcPlacer npcPlacer = new NpcPlacer(this);
	
	public Player player = new Player(this,key);
	public SuperObject obj[] = new SuperObject[objPerScreen]; // new Object[x]. x e a quantidade de objetos que podem ser renderizados na tela ao mesmo tempo.
	public Entity npc[] = new Entity[npcPerScreen];
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	int fps = 60; // Quantas vezes a tela vai ser atualizada por segundo.
	
	public Screen() {
		this.setBackground(Color.DARK_GRAY); // Define o plano de fundo da janela como a cor preta.
		this.setDoubleBuffered(true); // Vai renderizar os componentes graficos em segundo plano em uma memoria temporaria.
		this.addKeyListener(key); // Implementacao da classe KeyInput.
		this.setFocusable(true); // Indica se e possivel focar na janela do jogo.
		
	}
	
	public void setupGame() {
		objPlacer.placeObject();
		npcPlacer.placeNPC();
		playMusic(4);
		gameState = titleState;
	}
	
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();

    }
    
    // Funcao que atualiza a posicao do jogador quando a tecla de movimento e pressionada.
    public void update() {
    	//PLAY STATE
    	if(gameState==playState) {
    		player.update();
    		tsm.playTileSound();
    		//NPC
    		for (int i = 0; i < npc.length; i++) {
    			if(npc[i] != null) {
    				npc[i].update();
    			}
    		}
    	}
    	// PAUSE STATE
    	if(gameState==pauseState) {
    		tsm.stopTileSound();
    	}
    	
    
    }
    
    // Funcao que renderiza os graficos do jogo.
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Limpa o desenho anterior antes de renderizar novamente.
    	Graphics2D g2 = (Graphics2D)g; // Faz um casting para Graphics2D para poder manipular x, y e outros atributos mais avancados.
    	
    	//DEBUG (TESTADOR DE VELOCIDADE DE RENDERIZACAO
    	long drawBegin = 0;
    	if(key.isDebugging == true) {
    		drawBegin = System.nanoTime();
    	}
    	
    	// TITULO
    	if(gameState == titleState) {
    		ui.draw(g2);
    	}
    	//OTHERS
    	else {
    		tileM.draw(g2); // mapa
        
    		//OBJECT
        	for(int i = 0; i < obj.length; i++) {
        		if(obj[i] != null) {
        			obj[i].draw(g2, this);
        		}
        	}
    		// DESENHAR NPC
        	for (int i = 0; i < npc.length; i++) {
        		if(npc[i] != null) {
        			npc[i].draw(g2, this);
        		}
        	}
        	
        	// DESENHAR PLAYER
        	player.draw(g2); // Funcao que renderiza o player.
        	
        	//UI
        	ui.draw(g2);
        	
        	long drawStop = System.nanoTime();
        	if(key.isDebugging == true) {
        	long elapsedTime = drawStop - drawBegin;
        	g2.setColor(Color.white);
        	g2.drawString("Render Time: "+ elapsedTime, 10, 400);
        	System.out.println("Render Time = "+ elapsedTime);
        	
        	}
        	//liberar memoria
        	g2.dispose();
        }
    }


    // Linha de execucao secundaria do jogo, onde ocorrem as coisas na tela.
	public void run() {
		double drawInterval = 1000000000/fps; // Nosso intervalo de atualizacoes necessarias(1 nano segundo convertido para segundo/fps.
		double delta = 0; // Nossa variavel de controle, ela servira como um contador de segundos.
		long lastTime = System.nanoTime(); // Armazenamos nosso tempo atual (que sera trocado, entao ele e nosso ultimo tempo).
		long currentTime; // Tempo atual, este sera contado na hora de pintar e desenhar.
		
		// Enquanto o thread do jogo (tempo) esta rodando, ele ira contar o tempo.
		while (gameThread != null) {
			currentTime = System.nanoTime(); // Nosso tempo atual na thread recebe o tempo em nano segundos.
			delta += (currentTime - lastTime) / drawInterval; // Nosso delta vai receber o resto da diferenca entre o tempo passado e o atual, dividido pelo fps. E quando der 1 segundo, ira aplicar.
			lastTime = currentTime; // Resetamos nosso tempo passado, para atualizar o loop, ja que o calculo ja foi feito.
			
			//Quando delta der o tempo de 1/60 frames por segundo, ele ira atualizar um frame.
			if(delta >= 1) { 
				update(); // Chama a funcao de atualizar a posicao do jogador.
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
	
}