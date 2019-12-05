package sample.main;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.interfaces.Observable;
import sample.interfaces.Observer;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class Menu implements Initializable, Observable {

    @FXML
    Button newGameButton;
    @FXML
    Button optionsButton;
    @FXML
    Button exitButton;
    private Set<Observer> observerInterfaces = new HashSet<>();
    private static TypeOfButton clickedButton;
    private static Stage actualStage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuObserver menuObserver = new MenuObserver();
        attach(menuObserver);

        newGameButton.setOnAction(actionEvent -> {
            actualStage = (Stage)newGameButton.getScene().getWindow();
            clickedButton = TypeOfButton.NEW_GAME;
            userClickedButton();
        });
        optionsButton.setOnAction(actionEvent -> {
            clickedButton = TypeOfButton.OPTIONS;
            userClickedButton();

        });
        exitButton.setOnAction(actionEvent -> {
            clickedButton = TypeOfButton.EXIT;
            userClickedButton();
        });
        System.out.println("INICJALIZACJA W MENU");

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

}
