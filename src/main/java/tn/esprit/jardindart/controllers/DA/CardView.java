package tn.esprit.jardindart.controllers.DA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import tn.esprit.jardindart.models.DonArgent;
import tn.esprit.jardindart.services.DonArgentService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import tn.esprit.jardindart.controllers.Menu;
import tn.esprit.jardindart.test.HelloApplication;

public class CardView implements Initializable {

    @FXML
    private GridPane gridPane;

    private final DonArgentService service = new DonArgentService();
    Menu menuc =new Menu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayDonsArgent();
    }

    private void displayDonsArgent() {
        List<DonArgent> donsArgent = service.afficher();

        int col = 0;
        int row = 0;
        for (DonArgent don : donsArgent) {
            AnchorPane card = createCard(don);
            gridPane.add(card, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
    }

    private AnchorPane createCard(DonArgent don) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("card");

        Label montantLabel = new Label("Montant: " + don.getMontant() + " TND");
        montantLabel.getStyleClass().add("montant-label");
        montantLabel.setStyle("-fx-font-weight: bold;");
        AnchorPane.setTopAnchor(montantLabel, 36.0);
        AnchorPane.setLeftAnchor(montantLabel, 10.0);
        card.getChildren().add(montantLabel);

        Label associationNameLabel = new Label(don.getNom_assoc()); // Utiliser le nom de l'association
        associationNameLabel.getStyleClass().add("association-name-label");
        AnchorPane.setTopAnchor(associationNameLabel, 10.0);
        AnchorPane.setLeftAnchor(associationNameLabel, 10.0); // Décalage pour aligner avec "Organisation: "
        card.getChildren().add(associationNameLabel);








        HBox actionsBox = new HBox();
        actionsBox.setSpacing(10);

        /*JFXButton editButton = createIconButton("/tn/esprit/jardindart/icons/edit.png", 20);
        editButton.setOnAction(event -> handleEditDon(don));
        actionsBox.getChildren().add(editButton);*/

        JFXButton deleteButton = createIconButton("/tn/esprit/jardindart/icons/delete.png", 20);
        deleteButton.setOnAction(event -> handleDeleteDon(don));
        actionsBox.getChildren().add(deleteButton);

        AnchorPane.setTopAnchor(actionsBox, 76.0);
        AnchorPane.setLeftAnchor(actionsBox, 10.0);
        card.getChildren().add(actionsBox);

        return card;
    }

    private JFXButton createIconButton(String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton();
        button.getStyleClass().add("icon-button");
        Image iconImage = new Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);
        iconView.setFitHeight(iconSize);
        button.setGraphic(iconView);
        button.setPrefSize(iconSize, iconSize);
        return button;
    }



    private void handleDeleteDon(DonArgent don) {
        try {
            DonArgentService donService = new DonArgentService();
            donService.supprimer(don.getId());
            showAlert("Succès", "Le don d'argent a été supprimé avec succès.");
            // Refresh view after deletion
            gridPane.getChildren().clear();
            displayDonsArgent();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la suppression du don d'argent : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
