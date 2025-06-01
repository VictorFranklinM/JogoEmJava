package item;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.Screen;

public class Item_MagaGreen extends Entity{

	
	public Item_MagaGreen(Screen screen) {
		super(screen);
		
		name = "Wind Magatama";
		attackValue = 1;
		defenseValue = 1;
		
		down1 = setup("/objects/greenmaga",screen.tileSize, screen.tileSize);
	}
}