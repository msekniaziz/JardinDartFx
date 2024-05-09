package tn.jardindart.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Mainfx extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn.jardindart/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1540, 800);
        stage.setTitle("JARDIN D'ART");
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
