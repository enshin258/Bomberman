package sample.maps;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Title{

    private final Rectangle rectangle;
    private TypeOfTitle typeOfTitle;
    private final int x;
    private final int y;

    public Title(Image sprite, TypeOfTitle typeOfTitle,int x,int y) {
        this.rectangle = new Rectangle(50,50);
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

    public void setTypeOfTitle(TypeOfTitle typeOfTitle) {
        this.typeOfTitle = typeOfTitle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
