import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
	BlackSheep sheep;
	Image sheep_left = new Image("sheep2.png",60,40,false,false);
	Image sheep_right = new Image("sheep.png",60,40,false,false);
	
	public KeyHandler(BlackSheep sheep){
		this.sheep = sheep;		
	}
	
	boolean up, down, left, right, pause;

	@Override
	public void handle(KeyEvent key) {
		if(key.getEventType() == KeyEvent.KEY_PRESSED){
			if (key.getCode() == KeyCode.UP){
				up = true;
			}
			if (key.getCode() == KeyCode.DOWN){
				down = true;
			}
			if (key.getCode() == KeyCode.LEFT){
				sheep.img = sheep_left;
				left = true;
			}
			if (key.getCode() == KeyCode.RIGHT){
				sheep.img = sheep_right;
				right = true;
			}
			if (key.getCode() == KeyCode.P){
				pause = !pause;
			}
			
		}
		
		if(key.getEventType() == KeyEvent.KEY_RELEASED){
			if (key.getCode() == KeyCode.UP){
				up = false;
			}
			if (key.getCode() == KeyCode.DOWN){
				down = false;
			}
			if (key.getCode() == KeyCode.LEFT){
				left = false;
			}
			if (key.getCode() == KeyCode.RIGHT){
				right = false;
			}
		}
		
	}

}
