import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
public class BlackSheep{
		double x;
		double y;
		Image img;
		double width;
		double height;
		
		public BlackSheep(double width, double height){
			this.x = width/2;
			this.y = height/2;
			this.width = width;
			this.height = height;
			img = new Image("sheep.png",60,40,false,false);
		}
		
		public void update(KeyHandler handler,double width, double height){
			if (handler.up){
				if(y>=0) y -= 1.3;		
			}
			if (handler.down){
				if(y<=height-40) y += 1.3;
			}
			if (handler.left){
				if(x>=0)x -= 1.3;
			}
			if (handler.right){
				if(x<=width-60) x += 1.3;
			}
		}
		
		public void paint (GraphicsContext gc){
			gc.drawImage(img, x, y);
		}
	}