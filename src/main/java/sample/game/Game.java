package sample.game;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import sample.maps.MapObserver;
import sample.player.Direction;
import sample.player.Player;
import sample.maps.Map;
import sample.player.PlayerObserver;
import sample.player.TypeOfPlayer;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Game implements Initializable  {


    @FXML
    GridPane boardGridPane;

    static private Map map;
    static private Player player1;
    MapObserver mapObserver = new MapObserver();
    PlayerObserver playerObserver = new PlayerObserver();



    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        generateMap();
        addPlayers();
        boardGridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                case UP:
                    player1.setDirection(Direction.UP);
                    break;
                case S:
                case DOWN:
                    player1.setDirection(Direction.DOWN);
                    break;
                case A:
                case LEFT:
                    player1.setDirection(Direction.LEFT);
                    break;
                case D:
                case RIGHT:
                    player1.setDirection(Direction.RIGHT);
                    break;
            }
        });
        boardGridPane.setOnKeyReleased(event -> {
            player1.setDirection(Direction.STANDING);
        });
        System.out.println("INICJALIZACJA W GAME");
        startGame();

    }
    private void generateMap()
    {
        map = new Map();
        map.setMapGridPane(boardGridPane);
        map.attach(mapObserver);
        map.loadMap();
    }
    private void addPlayers()
    {
        List<Image> imagesForPlayer1 = new LinkedList<>();
        for(int i=1;i<=16;i++)
        {
            String nameOfFile="/sprites/character1/image_part_0";
            if(i<10)
            {
                nameOfFile=nameOfFile+"0"+i+".png";
            }
            else
            {
                nameOfFile=nameOfFile+i+".png";
            }
            imagesForPlayer1.add(new Image(nameOfFile));
        }

        player1 = new Player(1, TypeOfPlayer.HUMAN,imagesForPlayer1);
        player1.setDirection(Direction.STANDING);
        player1.attach(playerObserver);
        map.addPlayerToMap(player1, 1, 1);
    }

    public static Map getMap() {
        return map;
    }

    public static Player getPlayer1() {
        return player1;
    }

    private void startGame(){
        System.out.println("START W GAME");
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(player1.getDirection()!=Direction.STANDING)
                {
                    map.notifyObservers();
                }
            }
        };
        gameLoop.start();
    }
}
