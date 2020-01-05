package sample.bomb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.game.Game;
import sample.maps.Map;
import sample.maps.Title;
import sample.maps.TypeOfTitle;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;
/**
 * class responsible for the appearance and behaviour of bombs planted by players (@see {@link sample.player.Player})
 * **/
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

    /**
     * method triggers explosion sequences by queuing animations
     * @param map is a link to the map
     */
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
                    String path = "src/main/resources/sounds/Explosion.wav";
                    Media media = new Media(new File(path).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
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

    /**
     * sets the current bomb blast level
     * @param map is a link to the map
     * @param level is a level of explosion
     */
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

    /**
     * adds fields of fire, calculating the appropriate locations,
     * and adds appropriate graphics depending on the current explosion level
     * @param map is a link to the map
     * @param level is a level of explosion
     */
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

    /**
     * check if next title on range of explosion is flammable
     * if the next field is a brick field, then it destroys it and makes you walk on it
     * @param nextX next fire x coordinate
     * @param nextY next fire y coordinate
     * @param map is a link to map
     * @return true if next title is flammable
     */
    
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

    /**
     * removes fields of fire that no longer burn
     * @param map is a link to map
     */

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
