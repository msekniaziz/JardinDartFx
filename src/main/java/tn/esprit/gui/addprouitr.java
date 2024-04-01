package tn.esprit.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class addprouitr extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/jardindart/addproduittr.fxml"));

        // Set up the scene
        Scene scene = new Scene(root);

        // Set the scene on the stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle("Your JavaFX Application");

        // Show the stage
        primaryStage.show();

        // Modify the existing ComboBox
        ComboBox<String> catrgtroComboBox = (ComboBox<String>) root.lookup("#catrgtro");
        catrgtroComboBox.getItems().addAll("house", " garden");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
