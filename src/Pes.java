import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pes {
		double x;
		double y;
		double dx;
		double dy;
		Image image;
		int imgWidth = 50;
		int imgHeight = 50;
		double width;
		double height;
		Image img_right = new Image("dog2.png",imgWidth,imgHeight,false,false);
		Image img_left = new Image("dog.png",imgWidth,imgHeight,false,false);

		public Pes(double width,double height) {
			x = 800;
			y = 100;
			this.width = width;
			this.height = height;
			Random rnd = new Random();
			dx = rnd.nextDouble();
			dy = rnd.nextDouble();
			image = img_right;
		}
		
		public void update(double width,double height){

			if(x<=0){
				dx = -this.dx;
				image = img_right;
			}
			if(x>=width-imgWidth){
				dx = -dx;
				image = img_left;
			}
			if(y>=height-imgHeight){
				dy = -dy;
			}
			if(y<=0){ 
				dy = -dy;			
			}
			
			x += dx;
			y += dy;
		}
		
		public void paint (GraphicsContext gc){
			gc.drawImage(image, x, y);
		}
	}