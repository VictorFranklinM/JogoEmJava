package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import tile.Map;
import tile.TileOrganizer;

@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable{
	
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = (int) screenSize.getWidth();
	public static final int screenHeight = (int) screenSize.getHeight();
	
	public static final int originalTileSize = 16;
	public static final int scale = 4;
	public static final int tileSize = originalTileSize * scale;
	
	public static final int horizontalTiles = 20;
	public static final int verticalTiles = 12;
	
	public static final int maxWorldCol = 250;
	public static final int maxWorldRow = 250;
	public static final int maxMap = 10;
	public static int currentMap = 0;
	
	public static final int objPerScreen = 10;
	public static final int ovrPerScreen = 40;
	public static final int npcPerScreen = 10;
	public static final int enemyPerScreen = 20;
	
	// Sound
	Sound music = new Sound();
	Sound sfx = new Sound();
	TileSoundManager tsm = new TileSoundManager(this);

	public TileOrganizer tileM = new TileOrganizer(this);
	public KeyInput key = new KeyInput(this);
	Thread gameThread;
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	public UI ui = new UI(this);
	
	public CollisionChecker colCheck = new CollisionChecker(this);
	public EventManager eventManager = new EventManager(this);
	
	public ObjPlacer objPlacer = new ObjPlacer(this);
	public NpcPlacer npcPlacer = new NpcPlacer(this);
	
	public Player player = new Player(this,key);
	public Entity obj[][] = new Entity[maxMap][objPerScreen];
	public Entity[][] overlay = new Entity[maxMap][ovrPerScreen];
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
	
	int fps = 60;
	
	public Screen() {
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBuffered(true);
		this.addKeyListener(key);
		this.setFocusable(true);
		
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
		player.resetCounter();
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
    		
    		//PROJECTILES
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
    		
    		//PARTICLES
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
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D)g;
    	
    	
    	//DEBUG
    	long drawStart = 0;
    	if(key.isDebugging == true) {
    		drawStart = System.nanoTime();
    	}
    	
    	// TITLE
    	if(gameState == titleState) {
    		ui.draw(g2);
    	}
    	// MAP SCREEN
    	else if(gameState == mapState) {
    		map.drawFullMapScreen(g2);
    	}
    	//OTHERS
    	else {
    		tileM.draw(g2);
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
    		
    		for (int i = 0; i < ovrPerScreen; i++) {
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
        g2.dispose();

    }

	public void run() {
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) { 
				update();
				repaint();
				delta --;
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
	
	public void stopSFX(int i) {
		sfx.stop();
	}
	
}