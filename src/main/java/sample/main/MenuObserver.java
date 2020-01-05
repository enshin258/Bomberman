package sample.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import sample.interfaces.Observer;

import java.io.IOException;

/**
 * reacts on clicked button in main menu
 */
public class MenuObserver implements Observer {
    /**
     * execute action based on clicked button in main menu
     */
    @Override
    public void update() {
        switch (Menu.getClickedButton())
        {
            case NEW_GAME:
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/xmlFiles/map.fxml"));
                    Menu.getActualStage().setScene(new Scene(root, 1000,800));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case EXIT:
                Menu.getActualStage().close();
                break;
        }
    }
}
