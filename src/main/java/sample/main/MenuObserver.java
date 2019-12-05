package sample.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import sample.interfaces.Observer;

import java.io.IOException;

public class MenuObserver implements Observer {
    @Override
    public void update() {
        switch (Menu.getClickedButton())
        {
            case NEW_GAME:
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/xmlFiles/map.fxml"));
                    Menu.getActualStage().setScene(new Scene(root, 800, 800));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case OPTIONS:
                break;
            case EXIT:
                Menu.getActualStage().close();
                break;
        }
    }
}
