package object;

import entity.OverlayEntity;
import main.Screen;

public class OVR_TreeTop extends OverlayEntity {

    public static final String objName = "TreeTop";

    public OVR_TreeTop(Screen screen) {
        super(screen, 1, false);

        name = objName;
        type = typeOverlay;
        collision = false;

        down1 = setup("/objects/treeTop", Screen.tileSize, Screen.tileSize);

        collisionArea.x = 0;
        collisionArea.y = 0;
        collisionArea.width = 0;
        collisionArea.height = 0;
    }
}