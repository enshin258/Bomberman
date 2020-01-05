package sample.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import sample.main.Menu;
import sample.maps.MapObserver;
import sample.player.Direction;
import sample.player.Player;
import sample.maps.Map;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * the main class that handles the entire game and is responsible for its logic
 */

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


    /**
     * vector of players in game
     */
    static public final Vector<Player> players = new Vector<>();
    /**
     * additional vector used for deleting players
     */
    static public final Vector<Player> playersToRemove = new Vector<>();
    /**
     * vector of graphical counters in game
     */
    static public final Vector<Text> counters = new Vector<>();
    final MapObserver mapObserver = new MapObserver();
    private static int numberOfLivingPlayers;

    /**
     * used in music
     */
    private MediaPlayer mediaPlayer;


    /**
     * initialize game, setting control of players
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        numberOfLivingPlayers = Menu.getNumberOfPlayers();
        counters.add(lives_counter_1);
        counters.add(lives_counter_2);
        counters.add(lives_counter_3);
        counters.add(lives_counter_4);

        generateMap();
        addPlayers();

        boardGridPane.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case W:
                    if(players.get(0)!=null)
                    {
                        players.get(0).setDirection(Direction.UP);
                    }
                    break;
                case UP:
                    if(players.get(1)!=null)
                    {
                        players.get(1).setDirection(Direction.UP);
                    }
                    break;
                case NUMPAD8:
                    if(players.get(2)!=null)
                    {
                        players.get(2).setDirection(Direction.UP);
                    }
                    break;
                case I:
                    if(players.get(3)!=null)
                    {
                        players.get(3).setDirection(Direction.UP);
                    }
                    break;


                case S:
                    if(players.get(0)!=null)
                    {
                        players.get(0).setDirection(Direction.DOWN);
                    }
                    break;
                case DOWN:
                    if(players.get(1)!=null)
                    {
                        players.get(1).setDirection(Direction.DOWN);
                    }
                    break;
                case NUMPAD2:
                    if(players.get(2)!=null)
                    {
                        players.get(2).setDirection(Direction.DOWN);
                    }
                    break;
                case K:
                    if(players.get(3)!=null)
                    {
                        players.get(3).setDirection(Direction.DOWN);
                    }
                    break;



                case A:
                    if(players.get(0)!=null)
                    {
                        players.get(0).setDirection(Direction.LEFT);
                    }
                    break;
                case LEFT:
                    if(players.get(1)!=null)
                    {
                        players.get(1).setDirection(Direction.LEFT);
                    }
                    break;
                case NUMPAD4:
                    if(players.get(2)!=null)
                    {
                        players.get(2).setDirection(Direction.LEFT);
                    }
                    break;
                case J:
                    if(players.get(3)!=null)
                    {
                        players.get(3).setDirection(Direction.LEFT);
                    }
                    break;





                case D:
                    if(players.get(0)!=null)
                    {
                        players.get(0).setDirection(Direction.RIGHT);
                    }
                    break;
                case RIGHT:
                    if(players.get(1)!=null)
                    {
                        players.get(1).setDirection(Direction.RIGHT);
                    }
                    break;
                case NUMPAD6:
                    if(players.get(2)!=null)
                    {
                        players.get(2).setDirection(Direction.RIGHT);
                    }
                    break;
                case L:
                    if(players.get(3)!=null)
                    {
                        players.get(3).setDirection(Direction.RIGHT);
                    }
                    break;



                case SPACE:
                    if(players.get(0)!=null)
                    {
                        players.get(0).plantBomb(map);
                    }
                    break;
                case SHIFT:
                    if(players.get(1)!=null)
                    {
                        players.get(1).plantBomb(map);
                    }
                    break;
                case NUMPAD5:
                    if(players.get(2)!=null)
                    {
                        players.get(2).plantBomb(map);
                    }
                    break;
                case SEMICOLON:
                    if(players.get(3)!=null)
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
                    if(players.get(0)!=null)
                    {
                        players.get(0).setDirection(Direction.STANDING);
                    }
                    break;
                case UP:
                case LEFT:
                case DOWN:
                case RIGHT:
                    if(players.get(1)!=null)
                    {
                        players.get(1).setDirection(Direction.STANDING);
                    }
                    break;
                case NUMPAD8:
                case NUMPAD4:
                case NUMPAD2:
                case NUMPAD6:
                    if(players.get(2)!=null)
                    {
                        players.get(2).setDirection(Direction.STANDING);
                    }
                    break;
                case I:
                case J:
                case K:
                case L:
                    if(players.get(3)!=null)
                    {
                        players.get(3).setDirection(Direction.STANDING);
                    }
                    break;
            }
        });

        System.out.println("Inicjalizacja gry");
        startGame();

    }

    /**
     * generate adn add map to game
     */
    private void generateMap()
    {
        map = new Map();
        map.setMapGridPane(boardGridPane);
        map.addMapObserver(mapObserver);
        map.loadMap();
    }

    /**
     * add players based on option in main menu, and load their sprites
     */
    private void addPlayers()
    {
        for(int k = 0; k < Menu.getNumberOfPlayers(); k++) {
            List<Image> imagesForPlayer = new LinkedList<>();
            for (int i = 1; i <= 16; i++) {
                String nameOfFile = "/sprites/character" + k + "/row-1-col-" + i + ".png";
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


    /**
     * chanhe number of lives on player counter
     * @param idOfPlayer determines the player who lost his life
     */
    public void changeLivesCounterPlayer(int idOfPlayer)
    {

        Player player = players.get(idOfPlayer);
        player.setLives(player.getLives()-1);
        counters.get(idOfPlayer).setText(Integer.toString(player.getLives()));
        System.out.println("Gracz: "+player.getCharacterID() + " stracil zycie, pozostalo: " + player.getLives());
        if(player.getLives()<=0)
        {

            map.getMapGridPane().getChildren().remove(player.getImageView());
            map.getMapGridPane().getChildren().remove(player.getHitbox());
            System.out.println("Gracz numer: " + player.getCharacterID() + " przegrał gre");
            playersToRemove.add(player);
            numberOfLivingPlayers--;

        }
        checkIfEndGame();
    }


    /**
     * check if there is only one living player
     */
    public static void checkIfEndGame()
    {

        if(numberOfLivingPlayers==1)
        {
            System.out.println("Koniec gry!");
            Menu.getActualStage().close();
            System.exit(0);
        }


    }


    /**
     * starts game timer
     */
    private void startGame(){
        System.out.println("Start gry");


        Media media = new Media(new File("src/main/resources/sounds/MainTheme.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();


        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try
                {
                    Platform.runLater(() -> {
                        for (Iterator<Player> iterator = players.iterator();iterator.hasNext();)
                        {
                            Player actualPlayer  = iterator.next();
                            if(actualPlayer!=null)
                            {
                                if (actualPlayer.checkIfPlayerIsInFire()) {
                                    changeLivesCounterPlayer(actualPlayer.getCharacterID());
                                }
                                if (actualPlayer.getDirection() != Direction.STANDING && actualPlayer.getLives() > 0) {
                                    map.notifyMapObservers(actualPlayer.getCharacterID());
                                }
                                if(playersToRemove.contains(actualPlayer))
                                {
                                    players.set(players.indexOf(actualPlayer),null);
                                }
                                playersToRemove.clear();
                            }
                        }
                    });
                }
                catch (Exception e)
                {

                }


            }
        };
        gameLoop.start();
    }
}
