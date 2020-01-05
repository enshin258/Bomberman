package sample.maps;

import javafx.scene.layout.GridPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import sample.bomb.Bomb;
import sample.interfaces.Observable;
import sample.interfaces.Observer;
import sample.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * the class responsible for the board on which the game is played
 */
public class Map implements Observable {

    private GridPane mapGridPane;
    private final Vector<Title> titles = new Vector<>();
    private final Vector<Bomb> bombs = new Vector<>();
    private final Set<MapObserver> observerInterfaces = new HashSet<>();


    public void loadMap(){
        try {
            //problems with normal relative path
            String pathToMap = System.getProperty("user.dir")+ File.separator+ "\\src\\main\\java\\sample\\maps\\map1.xlsx";
            MapReader.readMapFromXLSXFile(pathToMap,this.mapGridPane,this.titles);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
    public void addPlayerToMap(Player player,int x,int y)
    {
        this.mapGridPane.add(player.getImageView(),x,y);
        this.mapGridPane.add(player.getHitbox(),x,y);
    }
    public void addBombToMap(Bomb bomb, int x, int y)
    {
        this.mapGridPane.add(bomb.getRectangle(),x,y);
        this.bombs.add(bomb);

    }
    public void addFireToMap(Title fire,int x,int y)
    {
        this.mapGridPane.add(fire.getRectangle(),x,y);
        titles.add(fire);
    }
    
    public GridPane getMapGridPane() {
        return mapGridPane;
    }

    public Vector<Title> getTitles() {
        return titles;
    }

    public void setMapGridPane(GridPane mapGridPane) {
        this.mapGridPane = mapGridPane;
    }

    public Vector<Bomb> getBombs() {
        return bombs;
    }

    public void addMapObserver(MapObserver observerInterface) {
        this.observerInterfaces.add(observerInterface);
    }

    public void notifyMapObservers(int playerID) {
        for (MapObserver mapObserver:this.observerInterfaces) {
            mapObserver.update(playerID);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        
    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
