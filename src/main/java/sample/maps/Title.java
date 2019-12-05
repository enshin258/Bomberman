package sample.maps;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Title{

    Rectangle rectangle;
    Image sprite;
    TypeOfTitle typeOfTitle;

    public Title(Image sprite, TypeOfTitle typeOfTitle) {
        this.rectangle = new Rectangle(50,50);
        this.sprite = sprite;
        this.typeOfTitle = typeOfTitle;
        this.rectangle.setFill(new ImagePattern(sprite));
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TypeOfTitle getTypeOfTitle() {
        return typeOfTitle;
    }
}
