package tn.jardindart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
<<<<<<< HEAD
import tn.jardindart.entites.User;
=======
import tn.jardindart.models.User;
>>>>>>> Recycle

public class UserManagment extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("/tn.jardindart/chatbot.fxml"));
=======
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("/tn.jardindart/Login.fxml"));
>>>>>>> Recycle
        Scene scene = new Scene(fxmlLoader.load(), 1101, 576);
        stage.setTitle("JARDIN D'ART");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }


}
