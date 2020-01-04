package sample.bomb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.game.Game;
import sample.maps.Map;
import sample.maps.Title;
import sample.maps.TypeOfTitle;

import java.util.Iterator;
import java.util.Vector;

public class Bomb {
    private final Rectangle rectangle;
    private final int x;
    private final int y;
    private final int idOfPlayerWhoPlantedBomb;
    private final Vector<Title> lastFires = new Vector<>();

    public Bomb(Image sprite,int x,int y,int idOfPlayerWhoPlantedBomb) {
        this.rectangle = new Rectangle(40,40);
        this.rectangle.setFill(new ImagePattern(sprite));
        this.x=x;
        this.y=y;
        this.idOfPlayerWhoPlantedBomb = idOfPlayerWhoPlantedBomb;
    }
    synchronized public void detonate(Map map)
    {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(500),
                event ->
                        this.rectangle.setFill(new ImagePattern(new Image("/sprites/bomb/bomb_2.png")))));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1000),
                event ->
                        this.rectangle.setFill(new ImagePattern(new Image("/sprites/bomb/bomb_3.png")))));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1500),
                event ->
                {
                    Game.getMap().getBombs().remove(this);
                    map.getMapGridPane().getChildren().remove(this.rectangle);
                    setActualExplosionLevel(map,1);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1600),
                event ->
                {
                    removeFireTitles(map);
                    setActualExplosionLevel(map,2);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1700),
                event ->
                {
                    removeFireTitles(map);

                    setActualExplosionLevel(map,3);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
            Duration.millis(1800),
            event ->
            {
                removeFireTitles(map);

                setActualExplosionLevel(map,4);
            }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1900),
                event ->
                {
                    removeFireTitles(map);

                    setActualExplosionLevel(map,3);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2000),
                event ->
                {
                    removeFireTitles(map);

                    setActualExplosionLevel(map,2);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2100),
                event ->
                {
                    removeFireTitles(map);

                    setActualExplosionLevel(map,1);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2200),
                event ->
                {
                    removeFireTitles(map);

                    timeline.stop();
                }));


        timeline.playFromStart();


    }
    public void setActualExplosionLevel(Map map,int level)
    {
        switch (level)
        {
            case 1:
            {
                addFireTitles(map,"1");
                break;
            }
            case 2:
            {
                addFireTitles(map,"2");
                break;
            }
            case 3:
            {
                addFireTitles(map,"3");
                break;
            }
            case 4:
            {
                addFireTitles(map,"4");
                break;
            }
        }
    }
    public void addFireTitles(Map map,String level)
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
                else return t.getTypeOfTitle() == TypeOfTitle.FLOOR;
            }

        }
        return true;
    }

    public void removeFireTitles(Map map)
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIdOfPlayerWhoPlantedBomb() {
        return idOfPlayerWhoPlantedBomb;
    }

}
