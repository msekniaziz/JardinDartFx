package User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeOFF implements Initializable {
    @FXML
    private Label Login;
    private Stage stage; // Declare Stage variable

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void GoToLogin() {
        try {
            Stage currentStage = (Stage) Login.getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene newScene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Admin Panel");

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
