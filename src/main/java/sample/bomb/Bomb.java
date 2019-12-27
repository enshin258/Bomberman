package sample.bomb;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.maps.Map;
import sample.maps.Title;
import sample.maps.TypeOfTitle;

import java.util.Iterator;
import java.util.Vector;

public class Bomb {
    Rectangle rectangle;
    Image sprite;
    int x;
    int y;
    public Vector<Title> lastFires = new Vector<>();

    public Bomb(Image sprite,int x,int y) {
        this.rectangle = new Rectangle(40,40);
        this.sprite = sprite;
        this.rectangle.setFill(new ImagePattern(sprite));
        this.x=x;
        this.y=y;
    }
    synchronized public void detonate(Map map)
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(500),
                event ->
                {
                    this.rectangle.setFill(new ImagePattern(new Image("/sprites/bomb/bomb_2.png")));
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1000),
                event ->
                {
                    this.rectangle.setFill(new ImagePattern(new Image("/sprites/bomb/bomb_3.png")));
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1500),
                event ->
                {
                    map.getMapGridPane().getChildren().remove(this.rectangle);
                    explosionLevel(map,1);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1600),
                event ->
                {
                    resetFire(map);
                    explosionLevel(map,2);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1700),
                event ->
                {
                    resetFire(map);

                    explosionLevel(map,3);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
            Duration.millis(1800),
            event ->
            {
                resetFire(map);

                explosionLevel(map,4);
            }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1900),
                event ->
                {
                    resetFire(map);

                    explosionLevel(map,3);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2000),
                event ->
                {
                    resetFire(map);

                    explosionLevel(map,2);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2100),
                event ->
                {
                    resetFire(map);

                    explosionLevel(map,1);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2200),
                event ->
                {
                    resetFire(map);

                    timeline.stop();
                }));


        timeline.playFromStart();


    }
    public void explosionLevel(Map map,int level)
    {
        switch (level)
        {
            case 1:
            {
                addFire(map,"1");
                break;
            }
            case 2:
            {
                addFire(map,"2");
                break;
            }
            case 3:
            {
                addFire(map,"3");
                break;
            }
            case 4:
            {
                addFire(map,"4");

                break;
            }
        }
    }
    public void addFire(Map map,String level)
    {

        String path = "/sprites/explosion/level" + level + "/center_fire.png";
        Title fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x,this.y);
        map.addFireToMap(fire,this.x,this.y);
        this.lastFires.add(fire);
        //check up
        //up
        if(this.y-1>=1)
        {
            if(checkIfNextTitleIsFlammable(x,y-1,map))
            {
                path = "/sprites/explosion/level" + level + "/vertical_fire.png";
                fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x,this.y-1);
                map.addFireToMap(fire,this.x,this.y-1);
                this.lastFires.add(fire);

                //up up
                if(this.y-2>=1)
                {
                    if(checkIfNextTitleIsFlammable(x,y-2,map))
                    {
                        path = "/sprites/explosion/level" + level + "/up_fire.png";
                        fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x,this.y-2);
                        map.addFireToMap(fire,this.x,this.y-2);
                        this.lastFires.add(fire);

                    }
                }
            }
        }

        //left
        if(this.x-1>=1)
        {
            if(checkIfNextTitleIsFlammable(x-1,y,map))
            {
                path = "/sprites/explosion/level" + level + "/horizontal_fire.png";
                fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x-1,this.y);
                map.addFireToMap(fire,this.x-1,this.y);

                this.lastFires.add(fire);

                //left left
                if(this.x-2>=1)
                {
                    if(checkIfNextTitleIsFlammable(x-2,y,map))
                    {
                        path = "/sprites/explosion/level" + level + "/left_fire.png";
                        fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x-2,this.y);
                        map.addFireToMap(fire,this.x-2,this.y);
                        this.lastFires.add(fire);

                    }
                }
            }
        }

        //right
        if(this.x+1<=16)
        {
            if(checkIfNextTitleIsFlammable(x+1,y,map))
            {
                path = "/sprites/explosion/level" + level + "/horizontal_fire.png";
                fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x+1,this.y);
                map.addFireToMap(fire,this.x+1,this.y);
                this.lastFires.add(fire);

                //right right
                if(this.x+2<=16)
                {
                    if(checkIfNextTitleIsFlammable(x+2,y,map))
                    {
                        path = "/sprites/explosion/level" + level + "/right_fire.png";
                        fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x+2,this.y);
                        map.addFireToMap(fire,this.x+2,this.y);
                        this.lastFires.add(fire);

                    }

                }
            }

        }

        //down
        if(this.y+1<=16)
        {
            if(checkIfNextTitleIsFlammable(x,y+1,map))
            {
                path = "/sprites/explosion/level" + level + "/vertical_fire.png";
                fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x,this.y+1);
                map.addFireToMap(fire,this.x,this.y+1);
                this.lastFires.add(fire);

                //down down
                if(this.y+2<=16)
                {
                    if(checkIfNextTitleIsFlammable(x,y+2,map))
                    {
                        path = "/sprites/explosion/level" + level + "/down_fire.png";
                        fire = new Title(new Image(path),TypeOfTitle.FIRE,this.x,this.y+2);
                        map.addFireToMap(fire,this.x,this.y+2);
                        this.lastFires.add(fire);

                    }

                }
            }
        }
    }
    
    public boolean checkIfNextTitleIsFlammable(int nextX,int nextY,Map map)
    {
        for (Title t:map.getTitles()) {
            if(t.getX()==nextX && t.getY()==nextY)
            {
                if(t.getTypeOfTitle()==TypeOfTitle.BRICK)
                {
                    t.getRectangle().setFill(new ImagePattern(new Image("sprites/environment/brick_destroyed.png")));
                    t.setTypeOfTitle(TypeOfTitle.FLOOR);
                    return true;
                }
                else if(t.getTypeOfTitle()==TypeOfTitle.FLOOR)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

        }
        return true;
    }

    public void resetFire(Map map)
    {
        for (Iterator<Title> iterator = map.getTitles().iterator(); iterator.hasNext();) {
            Title t = iterator.next();
            if (t.getTypeOfTitle()==TypeOfTitle.FIRE && lastFires.contains(t)  ) {
                // Remove the current element from the iterator and the list.
                iterator.remove();
                map.getMapGridPane().getChildren().remove(t.getRectangle());
            }
        }
        lastFires.clear();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
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
