package sample.maps;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Title{

    Rectangle rectangle;
    Image sprite;
    TypeOfTitle typeOfTitle;
    int x;
    int y;

    public Title(Image sprite, TypeOfTitle typeOfTitle,int x,int y) {
        this.rectangle = new Rectangle(50,50);
        this.sprite = sprite;
        this.typeOfTitle = typeOfTitle;
        this.rectangle.setFill(new ImagePattern(sprite));
        this.x=x;
        this.y=y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TypeOfTitle getTypeOfTitle() {
        return typeOfTitle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
