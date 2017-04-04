import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Whitesheep{
		double x;
		double y;
		Image img;
		int imgsize = 40;
		double dx;
		double dy;
		double width;
		double height;
		int smer = 1;
		Image img_right = new Image("white_sheep2.png",imgsize,imgsize,false,false);
		Image img_left = new Image("white_sheep.png",imgsize,imgsize,false,false);
		Image new_black_left = new Image("new_black.png",imgsize,imgsize,false,false);
		Image new_black_right = new Image("new_black2.png",imgsize,imgsize,false,false);
		boolean isBlack = false;
		
		public Whitesheep(double width,double height){
			Random rnd = new Random();
			this.width = width;
			this.height = height;
			dx = rnd.nextDouble();
			dy = rnd.nextDouble();
			x = rnd.nextInt((int) (width-imgsize*2))+imgsize;
			y = rnd.nextInt((int) (height-imgsize*2))+imgsize;
			img = img_right;
		}
		
		public void update(double width, double height){
			Random rnd = new Random();

			if(isBlack){
				if(smer == 1) img = new_black_right;
				if (smer == 2) img = new_black_left;
			}
			else{
				if(smer == 1) img = img_right;
				if (smer == 2) img = img_left;
			}
			if(x<=0){
				if(isBlack)
					img = new_black_right;
				else
					img = img_right;
				smer = 1;
				dx = -this.dx;
			}
			if(x>=width-imgsize){
				if(isBlack)
					img = new_black_left;
				else
					img = img_left;
				smer = 2;
				dx = -dx;			
			}
			if(y>=height-imgsize) dy = -dy;
			if(y<=imgsize) dy = -dy;
			
			x += dx;
			y += dy;
		}
		
		public void paint (GraphicsContext gc){
			gc.drawImage(img, x, y);
		}
	}