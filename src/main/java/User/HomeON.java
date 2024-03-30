package User ;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeON implements Initializable {
    @FXML
    private Label AccountInformations;
    @FXML
    private Label Logout;
    @FXML
    private ContextMenu accountMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userId = SessionManager.getInstance().getUserId();
        AccountInformations.setText("Account");
        AccountInformations.setContextMenu(accountMenu);
        AccountInformations.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                accountMenu.show(AccountInformations, event.getScreenX(), event.getScreenY());
            }
        });

    }

    public void GoToLogout() {
        SessionManager.getInstance().cleanUserSessionAdmin();
        try {
            Node sourceNode = (Node) Logout;
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAccountMenu(ActionEvent event) {
    }

    public void handleAccountAction1(ActionEvent actionEvent) {
        try {
            Node sourceNode = (Node) AccountInformations;
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ProfileUser.fxml")));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("User Profile");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAccountAction2(ActionEvent actionEvent) {

    }


}
