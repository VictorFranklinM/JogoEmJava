package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Screen;

public class Map extends TileOrganizer {

    Screen screen;
    public boolean miniMapOn = false;
    private BufferedImage[] worldMap;

    public Map(Screen screen) {
        super(screen);
        this.screen = screen;
        createWorldMap();
    }

    private void createWorldMap() {
        worldMap = new BufferedImage[Screen.maxMap];
        
        for (int mapNum = 0; mapNum < Screen.maxMap; mapNum++) {
            worldMap[mapNum] = new BufferedImage(Screen.maxWorldCol, Screen.maxWorldRow, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[mapNum].createGraphics();

            for (int row = 0; row < Screen.maxWorldRow; row++) {
                for (int col = 0; col < Screen.maxWorldCol; col++) {
                    int tileNum = mapTileNum[mapNum][col][row];
                    g2.drawImage(tile[tileNum].image, col, row, 1, 1, null);
                }
            }
            g2.dispose();
        }
    }

    public void drawFullMapScreen(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);

        int mapSize = 3;
        int mapWidth = Screen.maxWorldCol * mapSize;
        int mapHeight = Screen.maxWorldRow * mapSize;
        
        int x = Screen.screenWidth / 2 - mapWidth / 2;
        int y = Screen.screenHeight / 2 - mapHeight / 2;

        for (int row = 0; row < Screen.maxWorldRow; row++) {
            for (int col = 0; col < Screen.maxWorldCol; col++) {
                int tileNum = mapTileNum[Screen.currentMap][col][row];
                g2.drawImage(tile[tileNum].image, x + col * mapSize, y + row * mapSize, mapSize, mapSize, null);
            }
        }

        int playerX = x + (int)(screen.player.worldX / Screen.tileSize) * mapSize;
        int playerY = y + (int)(screen.player.worldY / Screen.tileSize) * mapSize;
        int playerSize = mapSize * 5;
        g2.drawImage(screen.player.face, playerX, playerY, playerSize, playerSize, null);
        g2.setFont(screen.ui.megaten.deriveFont(32F));
        g2.setColor(new Color(185, 219, 149));
        g2.drawString("PRESS M to Close", Screen.screenWidth - 300, Screen.screenHeight - 100);
    }

    public void drawMiniMap(Graphics2D g2) {
        if (!miniMapOn) return;

        int miniMapSize = Screen.screenWidth / 6;
        int mapZoom = 8;
        int tilesOnScreen = miniMapSize / mapZoom;
        int x = Screen.screenWidth - miniMapSize - 25;
        int y = 25;

        int playerCol = screen.player.worldX / Screen.tileSize;
        int playerRow = screen.player.worldY / Screen.tileSize;

        int startCol = playerCol - tilesOnScreen / 2;
        int startRow = playerRow - tilesOnScreen / 2;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.setColor(Color.black);
        int borders = 3;
        g2.fillRect(x - borders, y - borders, miniMapSize + borders * 2, miniMapSize + borders * 2);

        for (int row = 0; row < tilesOnScreen; row++) {
            for (int col = 0; col < tilesOnScreen; col++) {
                int worldCol = startCol + col;
                int worldRow = startRow + row;

                if (worldCol >= 0 && worldRow >= 0 && worldCol < Screen.maxWorldCol && worldRow < Screen.maxWorldRow) {
                    int tileNum = mapTileNum[Screen.currentMap][worldCol][worldRow];
                    BufferedImage tileImage = tile[tileNum].image;
                    g2.drawImage(tileImage, x+col*mapZoom, y+row*mapZoom, mapZoom, mapZoom, null);
                }
            }
        }

        int playerDrawX = x + (tilesOnScreen / 2) * mapZoom;
        int playerDrawY = y + (tilesOnScreen / 2) * mapZoom;
        int playerDrawSize = mapZoom * 2;

        g2.drawImage(screen.player.face, playerDrawX, playerDrawY, playerDrawSize, playerDrawSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}