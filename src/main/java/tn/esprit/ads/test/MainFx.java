package tn.esprit.ads.test;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    private static   Stage primaryStage;
    public MainFx() throws IOException {
    }

    @Override
    public void start(Stage stage) {
        try {
            primaryStage=stage;
            FXMLLoader fxmlLoader=new FXMLLoader(MainFx.class.getResource("/affMyAds.fxml"));
            Parent root =fxmlLoader.load();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();//nzid hedhi
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
            //Scene scene = new Scene(root,1500,600);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();


            //Parent root = FXMLLoader.load(getClass().getResource("/affMyAds.fxml"));
            //Scene scene = new Scene(root);
           // stage.setScene(scene);
           // stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateCurrentView(String fxmlFilePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFx.class.getResource(fxmlFilePath));
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
        launch(args);
    }
}
