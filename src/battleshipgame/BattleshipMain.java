package src.battleshipgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by yanair on 06.05.17.
 */

public class BattleshipMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        primaryStage.setTitle("BATTLESHIP");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
