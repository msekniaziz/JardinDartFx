package tn.esprit.jardindart.controllers.DA;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Success {

    @FXML
    private Hyperlink emailLink;

    @FXML
    private void initialize() {
        emailLink.setOnAction(event -> {
            // Define the action to perform when the hyperlink is clicked
            String email = "orders@example.com";
            // Perform any action you want, such as opening the default email client
            openEmailClient(email);
        });
    }

    private void openEmailClient(String emailAddress) {
        // Implement the logic to open the default email client with the provided email address
        // For example:
        // Desktop.getDesktop().mail(new URI("mailto:" + emailAddress));
    }

    // Après un paiement réussi
    @FXML
    private void handlePaymentSuccess(ActionEvent event) {
        // Charger la page FXML de succès et afficher
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tn/esprit/jardindart/DA/success.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
