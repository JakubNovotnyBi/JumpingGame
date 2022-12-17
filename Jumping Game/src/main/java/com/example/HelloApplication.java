package com.example;

import entity.Entity;
import entity.Platform;
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
import java.util.ArrayList;

// TODO: 13.12.2022 ideas: moving platform, moving into another screen (new background ?)
// TODO: 13.12.2022 need to add: gravity, fall speed
// TODO: 13.12.2022 need to fix: continuity(FPS),


public class HelloApplication extends Application {
    private static final int width = 800;
    private static final int height = 800;

    private boolean gameStarted;

    private boolean inAir = false;

    private double speed = 20;
    private double vSpeed = 0;

    private int background = 0;
    private boolean moveStage = false;

    public Rectangle rectangle;
    Image mario = new Image("mario.png");
    Image marioL = new Image("marioL.png");
    Image marioM = new Image("marioM.png");
    Image marioM2 = new Image("marioM2.png");
    Image tableLand = new Image("platform.png");
    Image backgroundImage = new Image("background.png");
    Image backgroundImage2 = new Image("background2.png");
    Image bgImage = backgroundImage;

    Entity player = new Entity(mario, 0, height - mario.getHeight() - 190);

    ArrayList<Platform> platforms = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Jumping Game");
        stage.setResizable(false);

        Canvas canvas = new Canvas(width, height);
        canvas.requestFocus();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run1(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        //canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);

        platforms.add(new Platform(tableLand, 150, 450));
        platforms.add(new Platform(tableLand, 300, 300));
        platforms.add(new Platform(new Image("transparentPlatform.png", 800, 50, false, false), 0, height - 190));


        Scene scene = new Scene(new StackPane(canvas));

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            //System.out.println(keyEvent.getCode().getCode());

            if (keyEvent.getCode() == KeyCode.A) {
                player.setImage(marioL);
                if (player.getPositionX() - speed >= 5) {
                    if (!moveStage){
                        player.setImage(marioM2);
                        moveStage= true;
                    }
                    else{
                        player.setImage(marioL);
                        moveStage = false;
                    }

                    player.setPositionX(player.getPositionX() - speed);
                    if (collisionTest(player, platforms)) {
                        player.setPositionX(player.getPositionX() + speed);
                    }

                } else {
                    if (background == 1) {
                        player.setPositionX(width - player.getImage().getWidth());
                        bgImage = backgroundImage;
                        background = 0;
                    }
                }
            } else if (keyEvent.getCode() == KeyCode.D) {
                player.setImage(mario);
                if (player.getPositionX() + player.getImage().getWidth() + speed <= width) {
                    if (!moveStage){
                        player.setImage(marioM);
                        moveStage= true;
                    }
                    else{
                        player.setImage(mario);
                        moveStage = false;
                    }
                    player.setPositionX(player.getPositionX() + speed);
                    if (collisionTest(player, platforms)) {
                        player.setPositionX(player.getPositionX() - speed);
                    }
                } else{
                    if (background==0){
                    player.setPositionX(0);
                    bgImage = backgroundImage2;
                    background = 1;
                    }
                }
            } else if (keyEvent.getCode() == KeyCode.W) {
                inAir = true;
                vSpeed = -30;

                if (player.getPositionY() + vSpeed >= 0) {
                    player.setPositionY(player.getPositionY() + vSpeed);
                    if (collisionTest(player, platforms)) {
                        player.setPositionY(player.getPositionY() - vSpeed);
                    }
                }
            }
/* just let him fall, no moving through cursor keys
            else if (keyEvent.getCode() == KeyCode.S){

                if (player.getPositionY() + player.getImage().getHeight() + speed <= height - 190 ){
                    player.setPositionY(player.getPositionY() + speed);
                    if (collisionTest(player,platforms)){
                        player.setPositionY(player.getPositionY() - speed);
                    }
                }
                else {
                    inAir = false;
                }
            }
        */
        });

        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("icon.png"));
        tl.play();

    }

    private boolean collisionTest(Entity target, ArrayList<Platform> platforms) {
        for (Platform platform : platforms)
            if (target.rect().getBoundsInParent().intersects(platform.rect().getBoundsInParent()))
                return true;
        return false;
    }


    private void run1(GraphicsContext gc) {

        //Canvas rendering
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        //Background rendering
        gc.drawImage(bgImage, 0, 0, width, height);
        gc.setFill(Color.WHITE);

        vSpeed += 2;
        player.setPositionY(player.getPositionY() + vSpeed);
        if (collisionTest(player, platforms)) {
            player.setPositionY(player.getPositionY() - vSpeed);
            inAir = false;
            vSpeed = 0;
        } else {
            inAir = true;
        }


        //Player rendering
        gc.drawImage(player.getImage(), player.getPositionX(), player.getPositionY(), player.getImage().getWidth(), player.getImage().getHeight());

        //Platform rendering
        for (Platform platform : platforms) {
            gc.drawImage(platform.getImage(), platform.getPositionX(), platform.getPositionY());
        }

    }

    public static void main(String[] args) {
        launch();
    }
}
