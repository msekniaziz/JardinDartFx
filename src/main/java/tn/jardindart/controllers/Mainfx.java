package tn.jardindart.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mainfx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn.jardindart/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1101, 576);
        stage.setTitle("JARDIN D'ART");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
