package edu.lawrence.cmsc250.lightbike.client;

import edu.lawrence.cmsc250.lightbike.client.graphics.panes.Grid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("graphics/arena.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed((evt)-> Grid.handleKeyEvent(evt));
    }
}
