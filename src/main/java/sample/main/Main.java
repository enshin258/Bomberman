package sample.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that load menu fxml file
 */
public class Main extends Application {
    /**
     * load fxml and create scene
     * @param primaryStage set scene on that stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/xmlFiles/menu.fxml"));
        primaryStage.setTitle("BomberMan");
        primaryStage.setScene(new Scene(root, 900, 675));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
