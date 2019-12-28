package sample.main;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
    Button exitButton;
    @FXML
    RadioButton players_2;
    @FXML
    RadioButton players_3;
    @FXML
    RadioButton players_4;


    private Set<Observer> observerInterfaces = new HashSet<>();
    private static TypeOfButton clickedButton;
    private static Stage actualStage;
    private static int numberOfPlayers=2;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuObserver menuObserver = new MenuObserver();
        attach(menuObserver);

        ToggleGroup toggleGroup = new ToggleGroup();
        players_2.setToggleGroup(toggleGroup);
        players_3.setToggleGroup(toggleGroup);
        players_4.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {
                RadioButton rb = (RadioButton)toggleGroup.getSelectedToggle();
                if (rb != null) {
                    numberOfPlayers = Integer.parseInt(rb.getText().charAt(0)+"");
                    System.out.println("ILOSC GRACZY: " + numberOfPlayers);
                }
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

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
