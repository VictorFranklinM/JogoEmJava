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
		description = "[" +name+ "]\n"
					+ "A  Magatama  of  the  wind  attribute!\n"
					+ attackValue + "  attack!\n"
					+ defenseValue + "  defense!\n"
					+ "Let's  you  enter  the  fire  realm!\n"
					+ "Let's  you  use  wind  magic!";
		down1 = setup("/objects/greenmaga",screen.tileSize, screen.tileSize);
	}
}