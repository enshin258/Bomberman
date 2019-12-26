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

public class Bomb {
    Rectangle rectangle;
    Image sprite;
    int x;
    int y;

    public Bomb(Image sprite,int x,int y) {
        this.rectangle = new Rectangle(40,40);
        this.sprite = sprite;
        this.rectangle.setFill(new ImagePattern(sprite));
        this.x=x;
        this.y=y;
    }
    public void detonate(Map map)
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
                    explosionLevel(map,2);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1700),
                event ->
                {
                    explosionLevel(map,3);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1800),
                event ->
                {
                    explosionLevel(map,4);
                }));
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(2000),
                event ->
                {

                }));


        timeline.playFromStart();
    }
    public void explosionLevel(Map map,int level)
    {
        switch (level)
        {
            case 1:
            {
                //middle
                Title fire = new Title(new Image("/sprites/explosion/level1/image_part_001.png"),TypeOfTitle.FIRE,this.x,this.y);
                map.addFireToMap(fire,this.x,this.y);
                //up
                if(this.y-1>=1)
                {
                    if(checkNextTitle(x,y-1,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_002.png"),TypeOfTitle.FIRE,this.x,this.y-1);
                        map.addFireToMap(fire,this.x,this.y-1);
                    }
                }
                //up up
                if(this.y-2>=1)
                {
                    if(checkNextTitle(x,y-2,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_004.png"),TypeOfTitle.FIRE,this.x,this.y-2);
                        map.addFireToMap(fire,this.x,this.y-2);
                    }
                }
                //left
                if(this.x-1>=1)
                {
                    if(checkNextTitle(x-1,y,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_003.png"),TypeOfTitle.FIRE,this.x-1,this.y);
                        map.addFireToMap(fire,this.x-1,this.y);
                    }
                }
                //left left
                if(this.x-2>=1)
                {
                    if(checkNextTitle(x-2,y,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_007.png"),TypeOfTitle.FIRE,this.x-2,this.y);
                        map.addFireToMap(fire,this.x-2,this.y);
                    }
                }
                //right
                if(this.x+1<=16)
                {
                    if(checkNextTitle(x+1,y,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_003.png"),TypeOfTitle.FIRE,this.x+1,this.y);
                        map.addFireToMap(fire,this.x+1,this.y);
                    }

                }
                //right right
                if(this.x+2<=16)
                {
                    if(checkNextTitle(x+2,y,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_006.png"),TypeOfTitle.FIRE,this.x+2,this.y);
                        map.addFireToMap(fire,this.x+2,this.y);
                    }

                }
                //down
                if(this.y+1<=16)
                {
                    if(checkNextTitle(x,y+1,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_002.png"),TypeOfTitle.FIRE,this.x,this.y+1);
                        map.addFireToMap(fire,this.x,this.y+1);
                    }

                }
                //down down
                if(this.y+2<=16)
                {
                    if(checkNextTitle(x,y+2,map))
                    {
                        fire = new Title(new Image("/sprites/explosion/level1/image_part_005.png"),TypeOfTitle.FIRE,this.x,this.y+2);
                        map.addFireToMap(fire,this.x,this.y+2);
                    }

                }
                break;
            }
//            case 2:
//            {
//                //middle
//                Title fire = new Title(new Image("/sprites/explosion/level2/image_part_008.png"),TypeOfTitle.FIRE);
//                map.addFireToMap(fire,this.x,this.y);
//                //up
//                if(this.y-1>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_009.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y-1);
//                }
//                //up up
//                if(this.y-2>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_011.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y-2);
//                }
//                //left
//                if(this.x-1>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_010.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x-1,this.y);
//                }
//                //left left
//                if(this.x-2>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_014.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x-2,this.y);
//                }
//                //right
//                if(this.x+1<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_010.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x+1,this.y);
//                }
//                //right right
//                if(this.x+2<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_013.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x+2,this.y);
//                }
//                //down
//                if(this.y+1<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_009.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y+1);
//                }
//                //down down
//                if(this.y+2<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level2/image_part_012.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y+2);
//                }
//                break;
//            }
//            case 3:
//            {
//                //middle
//                Title fire = new Title(new Image("/sprites/explosion/level3/image_part_015.png"),TypeOfTitle.FIRE);
//                map.addFireToMap(fire,this.x,this.y);
//                //up
//                if(this.y-1>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_016.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y-1);
//                }
//                //up up
//                if(this.y-2>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_018.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y-2);
//                }
//                //left
//                if(this.x-1>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_017.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x-1,this.y);
//                }
//                //left left
//                if(this.x-2>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_021.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x-2,this.y);
//                }
//                //right
//                if(this.x+1<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_017.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x+1,this.y);
//                }
//                //right right
//                if(this.x+2<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_020.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x+2,this.y);
//                }
//                //down
//                if(this.y+1<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_016.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y+1);
//                }
//                //down down
//                if(this.y+2<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level3/image_part_019.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y+2);
//                }
//                break;
//            }
//            case 4:
//            {
//                //middle
//                Title fire = new Title(new Image("/sprites/explosion/level4/image_part_022.png"),TypeOfTitle.FIRE);
//                map.addFireToMap(fire,this.x,this.y);
//                //up
//                if(this.y-1>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_023.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y-1);
//                }
//                //up up
//                if(this.y-2>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_025.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y-2);
//                }
//                //left
//                if(this.x-1>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_024.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x-1,this.y);
//                }
//                //left left
//                if(this.x-2>=1)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_028.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x-2,this.y);
//                }
//                //right
//                if(this.x+1<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_024.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x+1,this.y);
//                }
//                //right right
//                if(this.x+2<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_027.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x+2,this.y);
//                }
//                //down
//                if(this.y+1<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_023.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y+1);
//                }
//                //down down
//                if(this.y+2<=16)
//                {
//                    fire = new Title(new Image("/sprites/explosion/level4/image_part_026.png"),TypeOfTitle.FIRE);
//                    map.addFireToMap(fire,this.x,this.y+2);
//                }
//                break;
//            }
        }



    }

    public boolean checkNextTitle(int nextX,int nextY,Map map)
    {
        for (Title t:map.getTitles()) {
            if(t.getX()==nextX && t.getY()==nextY)
            {
                if(t.getTypeOfTitle()==TypeOfTitle.BRICK)
                {
                    t.getRectangle().setFill(new ImagePattern(new Image("sprites/environment/brick_destroyed.png")));
                    return true;
                }
                else if(t.getTypeOfTitle()!=TypeOfTitle.WALL && t.getTypeOfTitle()!=TypeOfTitle.FIRE)
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
