package sample.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import sample.bomb.Bomb;
import sample.game.Game;
import sample.maps.Map;
import sample.maps.Title;
import sample.maps.TypeOfTitle;

import java.util.*;

public class Player {


    private final int characterID;

    private final ImageView imageView;
    private final Rectangle hitbox;
    private final List<Image> images;
    private boolean invincible=false;


    private final double speed;
    private int lives;


    private Direction direction;
    private double frame_counter;

    public Player(int characterID, List<Image> images){
        this.characterID = characterID;


        this.images = images;
        this.imageView = new ImageView(images.get(1));
        this.imageView.setFitHeight(50);
        this.imageView.setFitWidth(50);
        this.hitbox = new Rectangle(25,25);
        this.hitbox.setTranslateX(10);
        this.hitbox.setTranslateY(2.5);
        this.hitbox.setOpacity(0f);
        this.imageView.setFocusTraversable(true);


        this.frame_counter=1;

        this.speed=2;
        this.lives=3;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void move(double dx, double dy)
    {
        this.frame_counter+=0.08;
        if(this.frame_counter>12)
        {
            this.frame_counter=1;
        }

        double newX = this.imageView.getTranslateX()+ dx;
        double newY = this.imageView.getTranslateY()+ dy;

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
                if(t.getTypeOfTitle()!=TypeOfTitle.FLOOR && t.getTypeOfTitle()!=TypeOfTitle.FIRE)
                {
                    return true;
                }
            }
        }
        for (Player otherPlayer:Game.players) {
            if(otherPlayer.getCharacterID()!=this.characterID)
            {
                if(this.hitbox.getBoundsInParent().intersects(otherPlayer.getHitbox().getBoundsInParent()))
                {
                    return true;
                }
            }
        }
        for (Bomb bomb:Game.getMap().getBombs()) {
            if(this.hitbox.getBoundsInParent().intersects(bomb.getRectangle().getBoundsInParent()))
            {
                if(bomb.getIdOfPlayerWhoPlantedBomb()!=this.characterID)
                {
                    return true;
                }
            }
        }

        return false;
    }
    public boolean checkIfPlayerIsInFire()
    {
        for(Title t:Game.getMap().getTitles())
        {
            if(this.hitbox.getBoundsInParent().intersects(t.getRectangle().getBoundsInParent()))
            {
                if(t.getTypeOfTitle()==TypeOfTitle.FIRE && !this.invincible && this.getLives()>0)
                {

                    this.invincible =true;
                    this.imageView.setOpacity(0.5f);

                    Timer myTimer = new Timer();
                    myTimer.schedule(new TimerTask(){
                        @Override
                        public void run() {
                            invincible =false;
                            imageView.setOpacity(1f);
                        }
                    }, 1000);
                    return true;
                }
            }
        }
        return false;
    }


    public void plantBomb(Map map)
    {
        Bomb bomb = new Bomb(new Image("/sprites/bomb/bomb_1.png"),(int)this.hitbox.getBoundsInParent().getMinX()/50,(int)this.hitbox.getBoundsInParent().getMinY()/50,this.characterID);

        map.addBombToMap(bomb,(bomb.getX()),(bomb.getY()));
        bomb.detonate(map);

    }


    public int getCharacterID() {
        return characterID;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public List<Image> getImages() {
        return images;
    }

    public double getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

}

