
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Ovce extends Application implements Runnable {
	Stage stag;
	double width = 900;
	double height = 500;
	Thread t;
	int biele = 8;
	static Playground pg;
	boolean gameOver;
	BlackSheep blacksheep;
	Pes dog;
	Pes2 dog2;
	ArrayList<Whitesheep> stado;
	Image background;
	Farmer farmer;
	KeyHandler handler;
	Label lbScore, lbTime, lbOnMove;
	Button btnLoad, btnSave, btnQuit;
	Timeline tl;
	boolean win = false;
	GameState gs = new GameState();
	int level = 1;
	boolean fix = true;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void run() {
		System.out.println("som sa spustil");
		while (!gameOver) {
			if(biele == 0){
				gameOver = true;
				win = true;
				return;
			}
			blacksheep.update(handler,pg.getWidth(),pg.getHeight());
			farmer.update(pg.getWidth(),pg.getHeight(),blacksheep.x,blacksheep.y);
			if(level >= 2){
				dog.update(pg.getWidth(), pg.getHeight());
				if (dog.x - blacksheep.x >= -20 && dog.x - blacksheep.x <= 20
						&& dog.y - blacksheep.y >= -20 && dog.y - blacksheep.y <= 20) {
					gameOver = true;
			
				}
			}
			if(level >= 3){
				dog2.update(pg.getWidth(), pg.getHeight());
				if (dog2.x - blacksheep.x >= -20 && dog2.x - blacksheep.x <= 20
						&& dog2.y - blacksheep.y >= -20 && dog2.y - blacksheep.y <= 20) {
					gameOver = true;
			
				}
			}
			if (farmer.x - blacksheep.x >= -30 && farmer.x - blacksheep.x <= 30
					&& farmer.y - blacksheep.y >= -20 && farmer.y - blacksheep.y <= 20) {
				gameOver = true;
		
			}
			for (Whitesheep whiteSheep : stado) {
				// kolizie bielej a ciernej ovce
				if (whiteSheep.x - blacksheep.x >= -20 && whiteSheep.x - blacksheep.x <= 20
						&& whiteSheep.y - blacksheep.y >= -20 && whiteSheep.y - blacksheep.y <= 20) {
					if(!whiteSheep.isBlack){
						biele--;
					}
					whiteSheep.isBlack = true;
				}
				
				// Kolozia bielej ovce a farmara
				if (whiteSheep.x - farmer.x >= -30 && whiteSheep.x - farmer.x <= 30
						&& whiteSheep.y - farmer.y >= -40 && whiteSheep.y - farmer.y <= 40) {
					if(whiteSheep.isBlack){
						biele++;
					}
					whiteSheep.isBlack = false;
				}
				
				//kolizie psa a ovce
				if(level>=2){
					if (whiteSheep.x - dog.x >= -20 && whiteSheep.x - dog.x <= 20
							&& whiteSheep.y - dog.y >= -20 && whiteSheep.y - dog.y <= 20) {
						if(whiteSheep.isBlack){
							biele++;
						}
						whiteSheep.isBlack = false;
					}					
				}
				if(level>=3){
					if (whiteSheep.x - dog2.x >= -20 && whiteSheep.x - dog2.x <= 20
							&& whiteSheep.y - dog2.y >= -20 && whiteSheep.y - dog2.y <= 20) {
						if(whiteSheep.isBlack){
							biele++;
						}
						whiteSheep.isBlack = false;
					}					
				}
				whiteSheep.update(pg.getWidth(),pg.getHeight());
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(gameOver){
				if(!win){
					for (Whitesheep whitesheep : stado) {
						whitesheep.isBlack = false;
						whitesheep.update(pg.getWidth(), pg.getHeight());
					}
				}
			}
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					pg.paint();
					lbScore.setText(Integer.toString(biele));
					lbOnMove.setText(Integer.toString(level));
					if(gameOver){
						tl.stop();
						Alert alert = new Alert(AlertType.INFORMATION);
						if(!win){
							alert.setTitle("Game over");
							alert.setHeaderText("Nedotiahol si vsetky ovecky na stranu zla");
							alert.setContentText("Ty looser !");
							alert.showAndWait();
							System.exit(0);
							
							
						}
						else{
							alert.setTitle("You win");
							alert.setHeaderText("Previedol si všetky ovce na temnú stranu, si skutoèný pán(ovca) zla");
							if(level==3)alert.setContentText("Vyhral si všeky kolá si majster, gratlujem");
							else alert.setContentText("Trúfaš si na ïalšie kolo ?");
							alert.show();
							try {
								t.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(level!=3){
								level++;
								restart(stag);
							}
							else System.exit(0);
							
						}
						
					}
					
				}
			});
		}
	}
	
	public void startGame(Stage primaryStage){
		pg = new Playground();
		stag = primaryStage;
		gameOver = false;
		blacksheep = new BlackSheep(pg.getWidth(), pg.getHeight());
		if(level>=2){
			dog = new Pes(pg.getWidth(), pg.getHeight());
		}
		if(level>=3){
			dog2 = new Pes2(pg.getWidth(), pg.getHeight());
		}
		handler = new KeyHandler(blacksheep);
		farmer = new Farmer(pg.getWidth(), pg.getHeight());
		background = new Image("grass.png", pg.getWidth(), pg.getHeight(), false, false);
		stado = new ArrayList<>();
		for (int i = 0; i < biele; i++) {
			stado.add(new Whitesheep(pg.getWidth(), pg.getHeight()));
		}
		
		Pane wrapperPane = new Pane();
		// CENTER
		BorderPane bp = new BorderPane();
		bp.setCenter(wrapperPane);
		wrapperPane.getChildren().add(pg);
		bp.setOnKeyPressed(handler);
		bp.setOnKeyReleased(handler);

		// TOP
		HBox labelPane = new HBox(new Label("Ostáva bielych:"), lbScore = new Label(Integer.toString(biele)),
				new Label("Elapsed time:"), lbTime = new Label("0"), new Label("Level:"), lbOnMove = new Label(Integer.toString(level)));
		labelPane.setSpacing(20);
		lbScore.setFont(Font.font(18));
		lbTime.setFont(Font.font(18));
		lbOnMove.setFont(Font.font(18));
		bp.setTop(labelPane);

		// Bottom
		HBox buttonPane = new HBox(btnLoad = new Button("Restart"),
				btnQuit = new Button("Quit"));
		buttonPane.setSpacing(50);
		bp.setBottom(buttonPane);
		
	    tl = new Timeline(1000);
		tl.setCycleCount(Timeline.INDEFINITE);
		
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
			gs.elapsedTime++;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					lbTime.setText("" + gs.elapsedTime);
				}
			});
		}));
		tl.play();
		Group root = new Group(bp);
		Scene scene = new Scene(root);
		btnQuit.setOnAction(event -> System.exit(0));
		btnLoad.setOnAction(event ->{
			restart(primaryStage);
		});
		
		
		scene.widthProperty().addListener(ev -> pg.setW(scene.getWidth()));
		scene.heightProperty().addListener(ev -> pg.setH(scene.getHeight()-buttonPane.getHeight()-labelPane.getHeight()));

		primaryStage.setScene(scene);
		primaryStage.setTitle("V službách zla");

		t = new Thread(this);
		t.start();
		primaryStage.show();
		pg.setFocusTraversable(true);
		
	}
	
	public void restart(Stage stage){
		biele = 10;
		win = false;
		tl.stop();
		t.stop();
		gs.elapsedTime = 0;
		startGame(stage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		startGame(primaryStage);
	}

	class Playground extends Canvas {

		public Playground() {
			setWidth(width);
			setHeight(height);
		}

		public void paint() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.LAWNGREEN);
			gc.fillRect(0, 0, getWidth(), getHeight());
			gc.drawImage(background, 0, 0);
			blacksheep.paint(gc);
			farmer.paint(pg.getGraphicsContext2D());
			if(level >= 2)
				dog.paint(gc);
			if(level >= 3)
				dog2.paint(pg.getGraphicsContext2D());
			for (Whitesheep whiteSheep : stado) {
				whiteSheep.paint(gc);
			}

		}
		
		public void newImages(){
			background = new Image("grass.png",getWidth(), getHeight(), false, false);
		}
		
		public void setW(double newW){
			setWidth(newW);
			newImages();
			paint();
		}
		
		public void setH(double newH){
			setHeight(newH);
			newImages();
			paint();
		}
	}

}
