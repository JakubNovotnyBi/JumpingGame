package com.example.pong;

import entity.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class HelloApplication extends Application {
    private static final int width = 1000;
    private static final int height = 1000;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_R = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = height / 2;
    private double playerTwoYPos = height / 2;



    private boolean gameStarted;
    private int playerOneXPos = 0;
    private int playerTwoXPos = width - PLAYER_WIDTH;

    public Rectangle rectangle;
    Image mario = new Image("mario.png");
    Image marioR = new Image("marioR.png");
    Image tableLand = new Image("platform.png");

    Entity player = new Entity(mario, 0, height - mario.getHeight());
    Entity platform = new Entity(tableLand, 150, 200);
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Jumping Game");
        stage.setResizable(false);

        Canvas canvas = new Canvas(width, height);
        canvas.requestFocus();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(100), e -> run1(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        //canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);

        Scene scene = new Scene(new StackPane(canvas));

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {

            if (keyEvent.getCode()== KeyCode.A){
                if (player.getPositionX() - 10 >= 5){
                    player.setImage(marioR);
                player.setPositionX(player.getPositionX()-10);
                }
            }

            else if (keyEvent.getCode()== KeyCode.D){
                if (player.getPositionX()+player.getImage().getWidth()+10 <= width){
                    player.setImage(mario);
                    player.setPositionX(player.getPositionX()+10);
                }

            }

            else if (keyEvent.getCode()== KeyCode.W){

                System.out.println("Y = " + player.getPositionY());
                System.out.println("Platforma Y = " + (platform.getPositionY()+30) );
                System.out.println("X = " + player.getPositionX());
                System.out.println("Platforma X = " + (platform.getPositionX()));

                if(player.getPositionY() - 10 <= 0  && player.getPositionY() == platform.getPositionY()+platform.getImage().getHeight() && player.getPositionX()+player.getImage().getWidth() >= platform.getPositionX()){
                    player.setPositionY(player.getPositionY());
                }
                else{
                    player.setPositionY(player.getPositionY()-10);
                }

                //else if ()
            }

            else if (keyEvent.getCode()== KeyCode.S){
                if (player.getPositionY()+player.getImage().getHeight()+10 <= height){
                    player.setPositionY(player.getPositionY()+10);
                }

            }


        });

        stage.setScene(scene);
        stage.show();
        tl.play();

    }


    private void run1(GraphicsContext gc) {

        rectangle = new Rectangle(100, 100, 100, 100);


        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.drawImage(player.getImage(), player.getPositionX(), player.getPositionY(), player.getImage().getWidth(),player.getImage().getHeight());

        gc.drawImage(platform.getImage(), platform.getPositionX(), platform.getPositionY());


    }

    public static void main(String[] args) {
        launch();
    }
}