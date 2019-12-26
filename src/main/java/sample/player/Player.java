package sample.player;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.bomb.Bomb;
import sample.game.Game;
import sample.interfaces.Observable;
import sample.interfaces.Observer;
import sample.main.Main;
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
    private Rectangle hitbox;
    private List<Image> images;


    private double speed;
    private double power;
    private double maxBombs;
    private int lives;


    private Direction direction;
    private double frame_counter;

    public Player(int characterID, TypeOfPlayer typeOfPlayer, List<Image> images){
        this.characterID = characterID;
        this.typeOfPlayer = typeOfPlayer;

        this.images = images;
        this.imageView = new ImageView(images.get(1));
        this.imageView.setFitHeight(50);
        this.imageView.setFitWidth(50);
        this.hitbox = new Rectangle(25,25);
        this.hitbox.setTranslateX(7.5);
        this.hitbox.setTranslateY(7.5);
        this.hitbox.setOpacity(0.0);
        imageView.setFocusTraversable(true);


        this.frame_counter=1;

        this.speed=2;
        this.power=2;
        this.maxBombs=1;
        this.lives=3;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void move(double dx, double dy)
    {
        frame_counter+=0.08;
        if(frame_counter>12)
        {
            frame_counter=1;
        }

        double newX = this.imageView.getTranslateX()+ dx;
        double newY = this.imageView.getTranslateY()+ dy;

//        System.out.println("X2: " + this.imageView.getBoundsInParent().getMaxX());
//        System.out.println("Y2: " + this.imageView.getBoundsInParent().getMaxY());




        this.imageView.setTranslateX(newX);
        this.imageView.setTranslateY(newY);

        this.hitbox.setTranslateX(this.hitbox.getTranslateX()+ dx);
        this.hitbox.setTranslateY(this.hitbox.getTranslateY()+ dy);

        if(checkCollisions())
        {
            switch (this.direction)
            {

                case UP:
                    this.imageView.setTranslateY(this.imageView.getTranslateY()+5);
                    this.hitbox.setTranslateY(this.hitbox.getTranslateY()+5);
                    break;
                case DOWN:
                    this.imageView.setTranslateY(this.imageView.getTranslateY()-5);
                    this.hitbox.setTranslateY(this.hitbox.getTranslateY()-5);
                    break;
                case LEFT:
                    this.imageView.setTranslateX(this.imageView.getTranslateX()+5);
                    this.hitbox.setTranslateX(this.hitbox.getTranslateX()+5);
                    break;
                case RIGHT:
                    this.imageView.setTranslateX(this.imageView.getTranslateX()-5);
                    this.hitbox.setTranslateX(this.hitbox.getTranslateX()-5);
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
            if(this.hitbox.getBoundsInParent().intersects(t.getRectangle().getBoundsInParent()))
            {
                if(t.getTypeOfTitle()!=TypeOfTitle.FLOOR)
                {
                    if(t.getTypeOfTitle()==TypeOfTitle.FIRE)
                    {
                        loseHealth();
                    }
                    return true;
                }

            }
        }
        return false;
    }
    public void loseHealth()
    {
        this.lives--;
        if(this.lives<=0)
        {
            Game.endGame();
        }
    }


    public void plantBomb(Map map)
    {
        Bomb bomb = new Bomb(new Image("/sprites/bomb/bomb_1.png"),(int)this.hitbox.getBoundsInParent().getMaxX()/50,(int)this.hitbox.getBoundsInParent().getMaxY()/50);

        map.addBombToMap(bomb,(bomb.getX()),(bomb.getY()));
        bomb.detonate(map);

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

