package main;

import java.awt.Color; // Biblioteca para gerenciamento de cores.
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; // Biblioteca para renderizacoes graficas.
import java.awt.Graphics2D; // Biblioteca para renderizacoes de formas geometricas.
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Interface da janela).

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.Map;
import tile.TileOrganizer;


// Sub-Classe da Classe JPanel.
@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable{
	
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Pega as proporcoes da tela.
	public static final int screenWidth = (int) screenSize.getWidth(); // Pega apenas a largura.
	public static final int screenHeight = (int) screenSize.getHeight(); // Pega apenas a altura.
	
	public static final int originalTileSize = 16; // Tamanho dos Tiles do jogo. (16x16).
	public static final int scale = 4; // Escala dos pixels.
	public static final int tileSize = originalTileSize * scale; // Tile redimensionado.
	
	public static final int horizontalTiles = 20; // Quantos tiles horizontais cabem na tela.
	public static final int verticalTiles = 12; // Quantos tiles verticais cabem na tela.
	
	public static final int maxWorldCol = 250;
	public static final int maxWorldRow = 250;
	public static final int maxMap = 10;
	public static int currentMap = 0;
	
	public static final int objPerScreen = 10;
	public static final int ovrPerScreen = 40;
	public static final int npcPerScreen = 10;
	public static final int enemyPerScreen = 20;
	
	//SOM
	Sound music = new Sound();
	Sound sfx = new Sound();
	TileSoundManager tsm = new TileSoundManager(this);

	public TileOrganizer tileM = new TileOrganizer(this);
	public KeyInput key = new KeyInput(this);
	Thread gameThread; // Cria uma linha de execucao secundaria para executar um codigo em segundo plano por cima do clock base.
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	public UI ui = new UI(this);
	
	public CollisionChecker colCheck = new CollisionChecker(this);
	public EventManager eventManager = new EventManager(this);
	
	public ObjPlacer objPlacer = new ObjPlacer(this);
	public NpcPlacer npcPlacer = new NpcPlacer(this);
	
	public Player player = new Player(this,key);
	public Entity obj[][] = new Entity[maxMap][objPerScreen]; // new Object[x]. x e a quantidade de objetos que podem ser renderizados na tela ao mesmo tempo.
	public Entity[][] overlay = new Entity[maxMap][ovrPerScreen]; // estou brincando
	public Entity npc[][] = new Entity[maxMap][npcPerScreen];
	public Entity enemy[][] = new Entity[maxMap][enemyPerScreen];
	public Entity projectile [][] = new Entity[maxMap][20];
	public ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> spellList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	Map map = new Map(this);
	SaveLoad saveLoad = new SaveLoad(this);
	public EntityGenerator eGenerator = new EntityGenerator(this);
	
	public int gameState;
	public static final int titleState = 0;
	public static final int playState = 1;
	public static final int dialogueState = 2;
	public static final int statusState = 3;
	public static final int optionsState = 4;
	public static final int gameOverState = 5;
	public static final int transitionState = 6;
	public static final int tradeState = 7;
	public final static int mapState = 8;
	
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
		npcPlacer.placeEnemy();
		playMusic(4);
		gameState = titleState;
	}
	
	public void resetGame(boolean restart) {
		player.setDefaultPositions();
		player.restoreStatus();
		npcPlacer.placeNPC();
		npcPlacer.placeEnemy();
		stopMusic();	
		if(restart == true) {
			player.setDefaultValues();
			player.currentMagatama = null;
			objPlacer.placeObject();
			playMusic(4);
		} else {
			playMusic(2);
		}
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
    		for (int i = 0; i < npcPerScreen; i++) {
    			if(npc[currentMap][i] != null) {
    				npc[currentMap][i].update();
    			}
    		}
    		//ENEMY
    		for (int i = 0; i < enemyPerScreen; i++) {
    			if(enemy[currentMap][i] != null) {
    				if(enemy[currentMap][i].alive && !enemy[currentMap][i].dying) {
    					enemy[currentMap][i].update();
    				}
    				if(!enemy[currentMap][i].alive) {
    					enemy[currentMap][i].checkDrop();
    					enemy[currentMap][i] = null;
    				}
    			}
    		}
    		
    		// OVERLAY
    		for (int i = 0; i < ovrPerScreen; i++) {
    		    if (overlay[currentMap][i] != null) {
    		        overlay[currentMap][i].update();
    		    }
    		}
    	
    		for (int i = 0; i < projectile[1].length; i++) {
    			if(projectile[currentMap][i] != null) {
    				if(projectile[currentMap][i].alive == true) {
    					projectile[currentMap][i].update();
    				} else {
    					projectile[currentMap][i] = null;
    				
    					i--;
    				}
    			}
    		}
    		
    		for (int i = 0; i < particleList.size(); i++) {
    			if(particleList.get(i) != null) {
    				if(particleList.get(i).alive) {
    					particleList.get(i).update();
    				} else {
    					particleList.remove(i);
    					i--;
    				}
    			}
    		}

    	}
    
    }
    
    // Funcao que renderiza os graficos do jogo.
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Limpa o desenho anterior antes de renderizar novamente.
    	Graphics2D g2 = (Graphics2D)g; // Faz um casting para Graphics2D para poder manipular x, y e outros atributos mais avancados.
    	
    	
    	//DEBUG (TESTADOR DE VELOCIDADE DE RENDERIZACAO
    	long drawStart = 0;
    	if(key.isDebugging == true) {
    		drawStart = System.nanoTime();
    	}
    	
    	// TITULO
    	if(gameState == titleState) {
    		ui.draw(g2);
    	}
    	// MAP SCREEN
    	else if(gameState == mapState) {
    		map.drawFullMapScreen(g2);
    	}
    	//OTHERS
    	else {
    		tileM.draw(g2); // mapa
    		//ADD ENTITY TO THE ENTITY LIST
    		entityList.add(player);
    		for(int i = 0;i < npcPerScreen; i++) {
    			if (npc[currentMap][i] != null) {
    				entityList.add(npc[currentMap][i]);
    			}
    		}
    		for(int i = 0; i < enemyPerScreen; i++) {
    			if(enemy[currentMap][i] != null) {
    				entityList.add(enemy[currentMap][i]);
    			}
    		}
    		for(int i = 0; i < objPerScreen; i++) {
    			if(obj[currentMap][i] != null) {
    				entityList.add(obj[currentMap][i]);
    			}
    		}
    		
    		for (int i = 0; i < ovrPerScreen; i++) {  //eu estou brincando com isso
    			if (overlay[currentMap][i] != null) {
    				entityList.add(overlay[currentMap][i]);
    			}
    		}
    		
    		for(int i = 0; i < projectile[1].length; i++) {
    			if(projectile[currentMap][i] != null) {
    				entityList.add(projectile[currentMap][i]);
    			}
    		}
    		
    		for(int i = 0; i < particleList.size(); i++) {
    			if(particleList.get(i) != null) {
    				entityList.add(particleList.get(i));
    			}
    		}
    		
    		//SORT
    		Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity o1, Entity o2) {
					int result = Integer.compare(o1.worldY, o2.worldY);
					return result;
				}
			});
    		//DRAW ENTITIES
    		for(int i = 0;i< entityList.size(); i++) {
    			entityList.get(i).draw(g2);
    		}
    		
    		entityList.clear();
    		
    		map.drawMiniMap(g2);
    		
        	ui.draw(g2);
        	
        	//DEBUG
        	if(key.isDebugging == true) {
        		long drawEnd = System.nanoTime();
        		long passed = drawEnd - drawStart;
        		
        		g2.setFont(new Font("Arial", Font.PLAIN,20));
        		
        		int x = 10;
        		int y = 400;
        		int lineHeight = 20;
        		
        		g2.drawRect(x-5, y-20, 200, 130);
        		g2.setColor(Color.black);
        		g2.fillRect(x-5, y-20, 200, 130);
        		g2.setColor(Color.white);
        		
        		g2.drawString("World X: "+player.worldX, x, y); y += lineHeight;
        		g2.drawString("World Y: "+player.worldY, x, y); y += lineHeight;
        		g2.drawString("Col: "+(player.worldX+player.collisionArea.x)/tileSize, x, y); y += lineHeight;
        		g2.drawString("Row: "+(player.worldY+player.collisionArea.y)/tileSize, x, y); y += lineHeight;
        		g2.drawString("Draw Time: "+passed, x, y); y += lineHeight;
        	}
    	}
        //liberar memoria
        g2.dispose();

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