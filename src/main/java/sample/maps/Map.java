package sample.maps;

import javafx.scene.layout.GridPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import sample.interfaces.Observable;
import sample.interfaces.Observer;
import sample.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Map implements Observable {

    private GridPane mapGridPane;
    private Vector<Title> titles = new Vector<>();
    private Set<Observer> observerInterfaces = new HashSet<>();


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
