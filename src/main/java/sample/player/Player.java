package sample.player;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import sample.game.Game;
import sample.interfaces.Observable;
import sample.interfaces.Observer;
import sample.maps.Map;
import sample.maps.Title;
import sample.maps.TypeOfTitle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player implements Observable {

    private Set<Observer> observerInterfaces = new HashSet<>();


    private int characterID;
    private TypeOfPlayer typeOfPlayer;

    private ImageView imageView;
    private List<Image> images;


    private double speed;
    private double power;
    private double maxBombs;


    private Direction direction;
    private double frame_counter;

    public Player(int characterID, TypeOfPlayer typeOfPlayer, List<Image> images){
        this.characterID = characterID;
        this.typeOfPlayer = typeOfPlayer;

        this.images = images;
        this.imageView = new ImageView(images.get(1));
        this.imageView.setFitHeight(40);
        this.imageView.setFitWidth(40);
        imageView.setFocusTraversable(true);

        this.frame_counter=1;

        this.speed=2.5;
        this.power=2;
        this.maxBombs=1;
    }

    public void move(double dx,double dy)
    {
        frame_counter+=0.08;
        if(frame_counter>12)
        {
            frame_counter=1;
        }

        double newX = this.imageView.getTranslateX()+ dx;
        double newY = this.imageView.getTranslateY()+ dy;



        this.imageView.setTranslateX(newX);
        this.imageView.setTranslateY(newY);
        if(checkCollisions())
        {
            switch (this.direction)
            {

                case UP:
                    this.imageView.setTranslateY(this.imageView.getTranslateY()+5);
                    break;
                case DOWN:
                    this.imageView.setTranslateY(this.imageView.getTranslateY()-5);
                    break;
                case LEFT:
                    this.imageView.setTranslateX(this.imageView.getTranslateX()+5);
                    break;
                case RIGHT:
                    this.imageView.setTranslateX(this.imageView.getTranslateX()-5);
                    break;
                case STANDING:
                    break;
            }
        }


         switch (this.getDirection())
         {
             case DOWN:
                 this.imageView.setImage(this.getImages().get((int)this.frame_counter%3));
                 break;
             case LEFT:
                 this.imageView.setImage(this.getImages().get((((int)this.frame_counter%3)+3)));
                 break;
             case RIGHT:
                 this.imageView.setImage(this.getImages().get((((int)this.frame_counter%3)+6)));
                 break;
             case UP:
                 this.imageView.setImage(this.getImages().get((((int)this.frame_counter%3)+9)));
                 break;
             case STANDING:
                 break;
         }
    }

    private boolean checkCollisions() {
        for(Title t:Game.getMap().getTitles())
        {
            if(this.imageView.getBoundsInParent().intersects(t.getRectangle().getBoundsInParent()))
            {
                if(t.getTypeOfTitle()!=TypeOfTitle.FLOOR)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public TypeOfPlayer getTypeOfPlayer() {
        return typeOfPlayer;
    }

    public void setTypeOfPlayer(TypeOfPlayer typeOfPlayer) {
        this.typeOfPlayer = typeOfPlayer;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getMaxBombs() {
        return maxBombs;
    }

    public void setMaxBombs(double maxBombs) {
        this.maxBombs = maxBombs;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Set<Observer> getObserverInterfaces() {
        return observerInterfaces;
    }

    public void setObserverInterfaces(Set<Observer> observerInterfaces) {
        this.observerInterfaces = observerInterfaces;
    }
    @Override
    public void attach(Observer observerInterface) {
        this.observerInterfaces.add(observerInterface);
    }

    @Override
    public void detach(Observer observerInterface) {
        this.observerInterfaces.remove(observerInterface);
    }

    @Override
    public void notifyObservers() {
        this.observerInterfaces.forEach(Observer::update);
    }
}

