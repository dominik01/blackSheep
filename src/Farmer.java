import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Farmer {
		double x;
		double y;
		double dx;
		double dy;
		Image image;
		int imgWidth = 60;
		int imgHeight = 80;
		double width;
		double height;
		Image img_right = new Image("farmer2.png",imgWidth,imgHeight,false,false);
		Image img_left = new Image("farmer.png",imgWidth,imgHeight,false,false);

		public Farmer(double width,double height) {
			x = 100;
			y = 500;
			this.width = width;
			this.height = height;
			Random rnd = new Random();
			dx = 0.5;
			dy = 0.5;
			image = img_right;
		}
		
		public void update(double width,double height, double targetX, double targetY){
			if(targetX>x){
				image = img_right;
				if(targetY>y){
					x += dx;
					y += dy;
				}
				else{
					x += dx;
					y -= dy;
				}
			}
			else{
				image = img_left;
				if (targetY>y){
					x -= dx;
					y += dy;
				}
				else{
					x -= dx;
					y -= dy;
				}
			}
			/*if(x<=0){
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
			}*/
			
			//x += dx;
			//y += dy;
		}
		
		public void paint (GraphicsContext gc){
			gc.drawImage(image, x, y-imgHeight/2);
		}
	}