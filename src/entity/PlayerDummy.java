package entity;

import main.Screen;

public class PlayerDummy extends Entity{

	public static final String npcName = "Dummy";
	
	public PlayerDummy(Screen screen) {
		super(screen);
		
		name = npcName;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/player/Up-1", Screen.tileSize, Screen.tileSize);
		up2 = setup("/player/Up-2", Screen.tileSize, Screen.tileSize);
		up3 = setup("/player/Up-3", Screen.tileSize, Screen.tileSize);
		down1 = setup("/player/Down-1", Screen.tileSize, Screen.tileSize);
		down2 = setup("/player/Down-2", Screen.tileSize, Screen.tileSize);
		down3 = setup("/player/Down-3", Screen.tileSize, Screen.tileSize);
		left1 = setup("/player/Left-1", Screen.tileSize, Screen.tileSize);
		left2 = setup("/player/Left-2", Screen.tileSize, Screen.tileSize);
		left3 = setup("/player/Left-3", Screen.tileSize, Screen.tileSize);
		right1 = setup("/player/Right-1", Screen.tileSize, Screen.tileSize);
		right2 = setup("/player/Right-2", Screen.tileSize, Screen.tileSize);
		right3 = setup("/player/Right-3", Screen.tileSize, Screen.tileSize);
		face = setup("/player/face", Screen.tileSize, Screen.tileSize);
	}	
}
