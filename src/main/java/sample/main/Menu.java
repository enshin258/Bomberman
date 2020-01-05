package sample.main;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.interfaces.Observable;
import sample.interfaces.Observer;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * the class responsible from the menu visible at the start of the game
 */
public class Menu implements Initializable, Observable {

    @FXML
    Button newGameButton;
    @FXML
    Button exitButton;
    @FXML
    RadioButton players_2;
    @FXML
    RadioButton players_3;
    @FXML
    RadioButton players_4;


    private final Set<Observer> observers = new HashSet<>();
    private static TypeOfButton clickedButton;
    private static Stage actualStage;
    private static int numberOfPlayers=2;


    /**
     * sets the button behavior in the game menu
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuObserver menuObserver = new MenuObserver();
        addObserver(menuObserver);

        ToggleGroup toggleGroup = new ToggleGroup();
        players_2.setToggleGroup(toggleGroup);
        players_3.setToggleGroup(toggleGroup);
        players_4.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((ob, o, n) -> {
            RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
            if (rb != null) {
                numberOfPlayers = Integer.parseInt(rb.getText().charAt(0) + "");
                System.out.println("Ilosc graczy: " + numberOfPlayers);
            }
        });


        newGameButton.setOnAction(actionEvent -> {
            actualStage = (Stage)newGameButton.getScene().getWindow();
            clickedButton = TypeOfButton.NEW_GAME;
            userClickedButton();
        });

        exitButton.setOnAction(actionEvent -> {
            actualStage = (Stage)exitButton.getScene().getWindow();
            clickedButton = TypeOfButton.EXIT;
            userClickedButton();
        });

    }
    

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }


    public void notifyObservers() {
        this.observers.forEach(Observer::update);
    }
    private void userClickedButton()
    {
        notifyObservers();
    }
    
    static TypeOfButton getClickedButton() {
        return clickedButton;
    }
    
    public static Stage getActualStage() {
        return actualStage;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
