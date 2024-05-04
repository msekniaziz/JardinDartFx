package tn.esprit.jardindart.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DBM/card_view.fxml"));
     // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/Association/card_view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/Association/AfficherAssoc.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DA/AddDA.fxml"));
  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DA/Payment.fxml"));

        Parent root =fxmlLoader.load();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();//nzid hedhi
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        //Scene scene = new Scene(root,1500,600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void updateCurrentView(String fxmlFilePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();//nzid hedhi
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
            //Scene scene = new Scene(root, 1500, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}