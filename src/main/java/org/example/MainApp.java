package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ensure the path is correct
            URL fxmlLocation = getClass().getResource("/fxml/main.fxml");
            if (fxmlLocation == null) {
                throw new NullPointerException("FXML file not found at specified location.");
            }

            VBox root = FXMLLoader.load(fxmlLocation);
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Restaurant Management");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
