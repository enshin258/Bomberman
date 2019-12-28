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

import java.net.URL;
import java.util.*;

public class Game implements Initializable  {


    @FXML
    GridPane boardGridPane;
    @FXML
    Text lives_counter_1;
    @FXML
    Text lives_counter_2;
    @FXML
    Text lives_counter_3;
    @FXML
    Text lives_counter_4;

    static private Map map;


    static public Vector<Player> players = new Vector<>();
    static public Vector<Text> counters = new Vector<>();
    MapObserver mapObserver = new MapObserver();



    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        counters.add(lives_counter_1);
        counters.add(lives_counter_2);
        counters.add(lives_counter_3);
        counters.add(lives_counter_4);

        generateMap();
        addPlayers();

        boardGridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    players.get(0).setDirection(Direction.UP);
                    break;
                case UP:
                    players.get(1).setDirection(Direction.UP);
                    break;
                case NUMPAD8:
                    players.get(2).setDirection(Direction.UP);
                    break;
                case I:
                    players.get(3).setDirection(Direction.UP);
                    break;


                case S:
                    players.get(0).setDirection(Direction.DOWN);
                    break;
                case DOWN:
                    players.get(1).setDirection(Direction.DOWN);
                    break;
                case NUMPAD2:
                    players.get(2).setDirection(Direction.DOWN);
                    break;
                case K:
                    players.get(3).setDirection(Direction.DOWN);
                    break;



                case A:
                    players.get(0).setDirection(Direction.LEFT);
                    break;
                case LEFT:
                    players.get(1).setDirection(Direction.LEFT);
                    break;
                case NUMPAD4:
                    players.get(2).setDirection(Direction.LEFT);
                    break;
                case J:
                    players.get(3).setDirection(Direction.LEFT);
                    break;





                case D:
                    players.get(0).setDirection(Direction.RIGHT);
                    break;
                case RIGHT:
                    players.get(1).setDirection(Direction.RIGHT);
                    break;
                case NUMPAD6:
                    players.get(2).setDirection(Direction.RIGHT);
                    break;
                case L:
                    players.get(3).setDirection(Direction.RIGHT);
                    break;



                case SPACE:
                    if(players.get(0).getLives()>0)
                    {
                        players.get(0).plantBomb(map);
                    }
                    break;
                case SHIFT:
                    if(players.get(1).getLives()>0)
                    {
                        players.get(1).plantBomb(map);
                    }
                    break;
                case NUMPAD5:
                    if(players.get(2).getLives()>0)
                    {
                        players.get(2).plantBomb(map);
                    }
                    break;
                case SEMICOLON:
                    if(players.get(3).getLives()>0)
                    {
                        players.get(3).plantBomb(map);
                    }
                    break;
            }
        });
        boardGridPane.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                case A:
                case S:
                case D:
                    players.get(0).setDirection(Direction.STANDING);
                    break;
                case UP:
                case LEFT:
                case DOWN:
                case RIGHT:
                    players.get(1).setDirection(Direction.STANDING);
                    break;
                case NUMPAD8:
                case NUMPAD4:
                case NUMPAD2:
                case NUMPAD6:
                    players.get(2).setDirection(Direction.STANDING);
                    break;
                case I:
                case J:
                case K:
                case L:
                    players.get(3).setDirection(Direction.STANDING);
                    break;
            }
        });

        System.out.println("INICJALIZACJA GRY");
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
        for(int k = 0; k<Menu.getNumberOfPlayers(); k++) {
            List<Image> imagesForPlayer = new LinkedList<>();
            for (int i = 1; i <= 16; i++) {
                String nameOfFile = "/sprites/character" + (k+1) + "/row-1-col-" + i + ".png";
                imagesForPlayer.add(new Image(nameOfFile));
            }
            switch (k) {
                case 0: {
                    Player player = new Player(k, imagesForPlayer);
                    player.setDirection(Direction.STANDING);
                    map.addPlayerToMap(player, 1, 1);
                    players.add(player);
                    counters.get(0).setText(player.getLives()+"");
                    break;
                }
                case 1: {
                    Player player = new Player(k, imagesForPlayer);
                    player.setDirection(Direction.STANDING);
                    map.addPlayerToMap(player, 14, 14);
                    players.add(player);
                    counters.get(1).setText(player.getLives()+"");
                    break;

                }
                case 2: {
                    Player player = new Player(k, imagesForPlayer);
                    player.setDirection(Direction.STANDING);
                    map.addPlayerToMap(player, 1, 14);
                    players.add(player);
                    counters.get(2).setText(player.getLives()+"");
                    break;

                }
                case 3: {
                    Player player = new Player(k, imagesForPlayer);
                    player.setDirection(Direction.STANDING);
                    map.addPlayerToMap(player, 14, 1);
                    players.add(player);
                    counters.get(3).setText(player.getLives()+"");
                    break;
                }
            }
        }
    }

    public static Map getMap() {
        return map;
    }


    public void changeLivesCounterPlayer(int idOfPlayer)
    {
        Player player = players.get(idOfPlayer);
        player.setLives(player.getLives()-1);
        counters.get(idOfPlayer).setText(Integer.toString(player.getLives()));
        if(player.getLives()<=0)
        {

            map.getMapGridPane().getChildren().remove(player.getImageView());
            map.getMapGridPane().getChildren().remove(player.getHitbox());
            System.out.println("GRACZ NUMER: " + player.getCharacterID() + "PRZEGRAł");
            endGame();
        }

    }



    public static void endGame()
    {

        //System.out.println("Koniec GRY!");
        //Menu.getActualStage().close();

    }


    private void startGame(){
        System.out.println("START W GAME");
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Player player:players) {
                    if(player.loseHealth())
                    {
                        changeLivesCounterPlayer(player.getCharacterID());
                    }
                    if(player.getDirection()!=Direction.STANDING && player.getLives()>0)
                    {
                        map.notifyObservers(player.getCharacterID());
                    }
                }
            }
        };
        gameLoop.start();
    }
}
