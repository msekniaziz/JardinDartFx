
package tn.esprit.applicationgui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddRepBlog extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/applicationgui/AddRepBlog.fxml"));

            // Set up the scene
            Scene scene = new Scene(root);

            // Set the stage title
            primaryStage.setTitle("Add Blog");

            // Set the scene on the primary stage
            primaryStage.setScene(scene);

            // Show the primary stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
