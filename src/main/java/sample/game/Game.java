package sample.game;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import sample.main.Menu;
import sample.maps.MapObserver;
import sample.player.Direction;
import sample.player.Player;
import sample.maps.Map;
import sample.player.TypeOfPlayer;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Game implements Initializable  {


    @FXML
    GridPane boardGridPane;

    @FXML
    Text lives_counter_1;

    @FXML
    Text lives_counter_2;

    static private Map map;
    static private Player player1;
    static private Player player2;
    MapObserver mapObserver = new MapObserver();



    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        generateMap();
        addPlayers();
        boardGridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player1.setDirection(Direction.UP);
                    break;
                case UP:
                    player2.setDirection(Direction.UP);
                    break;
                case S:
                    player1.setDirection(Direction.DOWN);
                    break;
                case DOWN:
                    player2.setDirection(Direction.DOWN);
                    break;
                case A:
                    player1.setDirection(Direction.LEFT);
                    break;
                case LEFT:
                    player2.setDirection(Direction.LEFT);
                    break;
                case D:
                    player1.setDirection(Direction.RIGHT);
                    break;
                case RIGHT:
                    player2.setDirection(Direction.RIGHT);
                    break;
                case SPACE:
                    player1.plantBomb(map);
                    break;
                case SHIFT:
                    player2.plantBomb(map);
                    break;
            }
        });
        boardGridPane.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                case A:
                case S:
                case D:
                    player1.setDirection(Direction.STANDING);
                    break;
                case UP:
                case LEFT:
                case DOWN:
                case RIGHT:
                    player2.setDirection(Direction.STANDING);
                    break;
            }
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

        List<Image> imagesForPlayer2 = new LinkedList<>();
        for(int i=1;i<=16;i++)
        {
            String nameOfFile="/sprites/character2/image_part_0";
            if(i<10)
            {
                nameOfFile=nameOfFile+"0"+i+".png";
            }
            else
            {
                nameOfFile=nameOfFile+i+".png";
            }
            imagesForPlayer2.add(new Image(nameOfFile));
        }

        player1 = new Player(1, TypeOfPlayer.HUMAN,imagesForPlayer1);
        player1.setDirection(Direction.STANDING);
        map.addPlayerToMap(player1, 1, 1);

        player2 = new Player(2, TypeOfPlayer.HUMAN,imagesForPlayer2);
        player2.setDirection(Direction.STANDING);
        map.addPlayerToMap(player2, 14, 14);

    }

    public static Map getMap() {
        return map;
    }

    public static Player getPlayer1() {
        return player1;
    }
    public static Player getPlayer2() {
        return player2;
    }

    public void changeLivesCounterPlayer1()
    {
        player1.setLives(player1.getLives()-1);
        lives_counter_1.setText(Integer.toString(player1.getLives()));
        if(player1.getLives()<=0)
        {
            endGame();
        }

    }
    public void changeLivesCounterPlayer2()
    {
        player2.setLives(player2.getLives()-1);
        lives_counter_2.setText(Integer.toString(player2.getLives()));
        if(player2.getLives()<=0)
        {
            endGame();
        }

    }


    public static void endGame()
    {
        System.out.println("Koniec GRY!");
        Menu.getActualStage().close();
    }


    private void startGame(){
        System.out.println("START W GAME");
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(player1.loseHealth())
                {
                    changeLivesCounterPlayer1();
                }
                if(player1.getDirection()!=Direction.STANDING)
                {
                    map.notifyObservers();
                }
                if(player2.loseHealth())
                {
                    changeLivesCounterPlayer2();
                }
                if(player2.getDirection()!=Direction.STANDING)
                {
                    map.notifyObservers();
                }
            }
        };
        gameLoop.start();
    }
}
