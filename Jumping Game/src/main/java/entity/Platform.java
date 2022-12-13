package entity;


import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Platform {


    private Image image;

    private double positionX;
    private double positionY;

    public Platform(Image image, double positionX, double positionY) {
        this.image = image;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    ;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public Rectangle rect() {
        return new Rectangle(image.getWidth(), image.getHeight());
    }
}