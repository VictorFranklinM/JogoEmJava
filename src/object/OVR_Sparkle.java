package object;

import entity.OverlayEntity;
import main.Screen;

public class OVR_Sparkle extends OverlayEntity {

    public static final String objName = "Sparkle";

    public OVR_Sparkle(Screen screen) {
        super(screen, 10, true);

        name = objName;
        type = typeOverlay;
        collision = false;

        for (int i = 0; i < totalFrames; i++) {
            frames[i] = setup("/objects/sparkle/sparkle_" + i, Screen.tileSize, Screen.tileSize);
        }

        spriteCounter = 0;
        spriteNum = 0;
        down1 = frames[0];

        collisionArea.x = 0;
        collisionArea.y = 0;
        collisionArea.width = Screen.tileSize;
        collisionArea.height = Screen.tileSize;
    }
}