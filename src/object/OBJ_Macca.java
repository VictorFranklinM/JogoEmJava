package object;
import entity.Entity;
import main.Screen;

public class OBJ_Macca extends Entity {
	 
	public OBJ_Macca(Screen screen) {
		super(screen);
		
		type = typePickupOnly;
		name = "Macca";
		value = 1;
		down1 = setup("/objects/macca",Screen.tileSize, Screen.tileSize);
	}
 public void use(Entity entity) {
	 
	 screen.playSFX(1);
	 screen.ui.addMessage("Macca +"+ value);
		screen.player.macca += value;
	 
	 
 }
}
