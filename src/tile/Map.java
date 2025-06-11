package tile;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Screen;

public class Map extends TileOrganizer {

    Screen screen;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;
 

    public Map(Screen screen) {
        super(screen);
        this.screen = screen;
        createWorldMap();

    }
    public void createWorldMap() {
     	 worldMap = new BufferedImage[Screen.maxMap];
     	int scaledTileSize = 12; //ADD NEW SCALED
     	int worldMapWidth = scaledTileSize * Screen.maxWorldCol;
     	int worldMapHeight = scaledTileSize * Screen.maxWorldRow;
     	 
     	  for(int i = 0; i < Screen.maxMap; i++) {
     		  
     		  worldMap[i] = new BufferedImage (worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
     		  Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
     		  
     		  int col = 0;
     		  int row = 0;
     		  
     		  while(col < Screen.maxWorldCol && row < Screen.maxWorldRow) {
     			  
     			  int tileNum = mapTileNum[i][col][row];
     			  int x =  Screen.tileSize * col;
     			  int y = Screen.tileSize  * row;
     			  g2.drawImage(tile[tileNum].image, x, y, null);
     			  
     			  col++;
     			  if(col == Screen.maxWorldCol) {
     				  col = 0;
     				  row++;
     			  }
     		  }
     	  }
       
     }
    public void drawFullMapScreen(Graphics2D g2) {
        // BACKGROUND COLOR
        g2.setColor(Color.black);
        g2.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);

        //DRAW MAP
        int width = 500;
        int height = 500;
        int x = Screen.screenWidth / 2 - width / 2;
        int y = Screen.screenHeight / 2 - height / 2;
        g2.drawImage(worldMap[Screen.currentMap], x, y, width, height, null);

        // DRAW PLAYER
        double scale = (double) worldMap[Screen.currentMap].getWidth() / width; //ADD NEW
        int playerX = (int) (x + screen.player.worldX / scale);
        int playerY = (int) (y + screen.player.worldY / scale);
        int playerSize = (int) (Screen.tileSize / scale);
        if (playerSize < 2) playerSize = 2; //ADD NEW
        g2.drawImage(screen.player.down1, playerX, playerY, playerSize, playerSize, null);

        // HINT
        g2.setFont(screen.ui.megaten.deriveFont(32F));
        g2.setColor(Color.white);
        g2.drawString("PRESS M to Close", 1000, 900);
    }

    public void drawMiniMap(Graphics2D g2) {
        if (miniMapOn) {
           
        	//DRAW MAP
            int width = 300;
            int height = 300;
            int x = Screen.screenWidth - width - 50;
            int y = 50;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8F));
            g2.drawImage(worldMap[Screen.currentMap], x, y, width, height, null);

            
            //DRAW PLAYER
            double scale = (double) worldMap[Screen.currentMap].getWidth() / width; //ADD NEW
            int playerX = (int) (x + screen.player.worldX / scale);
            int playerY = (int) (y + screen.player.worldY / scale);
            int playerSize = (int) ((Screen.tileSize / scale) *2);
            if (playerSize < 2) playerSize = 2; //ADD NEW
            
            if (screen.player.down1 != null) { //ADD NEW
                g2.drawImage(screen.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);
            

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    }
}
 
   
	
    
    
