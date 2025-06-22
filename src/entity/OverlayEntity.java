package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Screen;

public class OverlayEntity extends Entity {
    public boolean isAnimated;
    public BufferedImage[] frames;
    public int totalFrames;

    public OverlayEntity(Screen screen, int totalFrames, boolean isAnimated) {
        super(screen);
        this.totalFrames = totalFrames;
        this.isAnimated = isAnimated;
        alive = true;

        if (isAnimated) {
            frames = new BufferedImage[totalFrames];
        }
    }
    
    @Override
    public void update() {
        if (!isAnimated || frames == null) return;

        spriteCounter++;
        if (spriteCounter > 16) {
            spriteNum++;
            if (spriteNum >= totalFrames) spriteNum = 0;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - screen.player.worldX + screen.player.screenX;
        int screenY = worldY - screen.player.worldY + screen.player.screenY;

        BufferedImage imageToDraw;

        if (isAnimated && frames != null && frames[spriteNum] != null) {
            imageToDraw = frames[spriteNum];
        } else {
            imageToDraw = down1;
        }

        if (imageToDraw != null) {
            g2.drawImage(imageToDraw, screenX, screenY, null);
        }
    }
}