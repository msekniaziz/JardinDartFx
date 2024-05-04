package tn.esprit.jardindart.controllers.DBM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tn.esprit.jardindart.controllers.Menu;
import tn.esprit.jardindart.models.DonBienMateriel;
import tn.esprit.jardindart.services.DonBienMaterielService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import tn.esprit.jardindart.test.HelloApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType; // Import ButtonType here


public class CardView implements Initializable {

    @FXML
    private GridPane gridPane;

    private final DonBienMaterielService service = new DonBienMaterielService();
    Menu menuc =new Menu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayDonsBienMateriel();
    }

    private void displayDonsBienMateriel() {
        List<DonBienMateriel> donsBienMateriel = service.afficher();

        int col = 0;
        int row = 0;
        for (DonBienMateriel don : donsBienMateriel) {
            AnchorPane card = createCard(don);
            gridPane.add(card, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
    }

    private AnchorPane createCard(DonBienMateriel don) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("card");


        /*Label organizationLabel = new Label("Organisation: ");
        organizationLabel.getStyleClass().add("organization-label");
        organizationLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Mettre en gras
        AnchorPane.setTopAnchor(organizationLabel, 10.0);
        AnchorPane.setLeftAnchor(organizationLabel, 10.0);
        card.getChildren().add(organizationLabel);*/

        ImageView imageView = new ImageView();
        double imageWidth = 200; // Largeur fixe de l'image
        double imageHeight = 200; // Hauteur fixe de l'image
        imageView.setFitWidth(imageWidth); // Définissez la largeur de l'image
        imageView.setFitHeight(imageHeight); // Définissez la hauteur de l'image
        imageView.setPreserveRatio(true); // Préservez le ratio de l'image
        imageView.setSmooth(true); // Lissage de l'image
        imageView.setCache(true); // Mise en cache de l'image pour une meilleure performance
        card.getChildren().add(imageView);
        AnchorPane.setTopAnchor(imageView, 10.0);
        AnchorPane.setRightAnchor(imageView, 10.0);
        if (don.getPhoto_don() != null && !don.getPhoto_don().isEmpty()) {
            File imageFile = new File(don.getPhoto_don());
            if (imageFile.exists()) { // Vérifiez si le fichier image existe
                // Créer une image à partir du fichier
                Image image = new Image(imageFile.toURI().toString());
                // Affecter l'image à l'ImageView
                imageView.setImage(image);
            } else {
                System.out.println("Le fichier image n'existe pas : " + don.getPhoto_don());
            }
        }

        Label associationNameLabel = new Label(don.getNom_assoc()); // Utiliser le nom de l'association
        associationNameLabel.getStyleClass().add("association-name-label");
        AnchorPane.setTopAnchor(associationNameLabel, 160.0);
        AnchorPane.setLeftAnchor(associationNameLabel, 10.0); // Décalage pour aligner avec "Organisation: "
        card.getChildren().add(associationNameLabel);

        Label descriptionLabel = new Label("Description: " + don.getDesc_don());
        descriptionLabel.getStyleClass().add("description-label");
        descriptionLabel.setFont(Font.font("System", FontWeight.BOLD, 30)); // Mettre en gras
        AnchorPane.setTopAnchor(descriptionLabel, 180.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);
        card.getChildren().add(descriptionLabel);





        Label statusLabel = new Label("Statut: " + (don.isStatut_don() ? "Actif" : "Inactif"));
        statusLabel.getStyleClass().add("status-label");
        if (don.isStatut_don()) {
            statusLabel.setTextFill(Color.GREEN); // Mettre en vert si actif
        } else {
            statusLabel.setTextFill(Color.RED); // Mettre en rouge si inactif
        }
        AnchorPane.setTopAnchor(statusLabel, 200.0);
        AnchorPane.setLeftAnchor(statusLabel, 10.0);
        card.getChildren().add(statusLabel);

        HBox actionsBox = new HBox();
        actionsBox.setSpacing(10);

        JFXButton editButton = createIconButton("/tn/esprit/jardindart/icons/edit.png", 20);
        editButton.setOnAction(event -> handleEditDon(don));
        actionsBox.getChildren().add(editButton);

        JFXButton deleteButton = createIconButton("/tn/esprit/jardindart/icons/delete.png", 20);
        deleteButton.setOnAction(event -> handleDeleteDon(don));
        actionsBox.getChildren().add(deleteButton);

        AnchorPane.setTopAnchor(actionsBox, 220.0);
        AnchorPane.setLeftAnchor(actionsBox, 10.0);
        card.getChildren().add(actionsBox);

        return card;
    }



    private JFXButton createIconButton(String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton();
        button.getStyleClass().add("icon-button");
        Image iconImage = new Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);  // Set the width of the icon
        iconView.setFitHeight(iconSize); // Set the height of the icon
        button.setGraphic(iconView);
        button.setPrefSize(iconSize, iconSize);
        return button;
    }


    private void handleEditDon(DonBienMateriel don) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DBM/UpdateDBM.fxml"));
        try {
            gridPane.getScene().setRoot(fxmlLoader.load());
            UpdateDBM controller = fxmlLoader.getController();
            controller.initialize(don.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleDeleteDon(DonBienMateriel don) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de la suppression");
        alert.setContentText("Voulez-vous vraiment supprimer cet élément ?");

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    DonBienMaterielService donService = new DonBienMaterielService();
                    donService.supprimer(don.getId());
                    HelloApplication.updateCurrentView("/tn/esprit/jardindart/DBM/card_view.fxml");
                } catch (Exception e) {
                    // Show an error message if deletion fails
                    showAlert("Erreur", "Une erreur s'est produite lors de la suppression du don : " + e.getMessage());
                }
            }
        });
    }





    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
