package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.Screen;

public class OverlayEntity extends Entity {
    public boolean isAnimated = true;
    public BufferedImage[] frames;
    public int totalFrames = 16;

    public OverlayEntity(Screen screen) {
        super(screen);
        alive = true;
        frames = new BufferedImage[totalFrames];
    }

    @Override
    public void update() {
        if (!isAnimated) return;

        spriteCounter++;
        if (spriteCounter > 16) {
            spriteNum++;
            if (spriteNum >= totalFrames) spriteNum = 0;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (frames[spriteNum] == null) return;

        int screenX = worldX - screen.player.worldX + screen.player.screenX;
        int screenY = worldY - screen.player.worldY + screen.player.screenY;

        g2.drawImage(frames[spriteNum], screenX, screenY, null);

        // Retângulo de teste
        // g2.setColor(Color.RED);
        // g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, 
                    // collisionArea.width, collisionArea.height);
    }
}