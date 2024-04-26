package tn.esprit.pifx.controllers.prodR;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import tn.esprit.pifx.controllers.MenuBack;
import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.services.ProdRService;
import tn.esprit.pifx.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
public class ViewProdR implements Initializable {

    @FXML
    private TilePane tilePane;

    private ProdRService prodRService;

    MenuBack menuc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodRService = new ProdRService();

        afficherProdR();
    }

    private void afficherProdR() {
        List<ProdR> prodRs = prodRService.recuperer(); // Obtenez vos données depuis le service

        for (ProdR prodR : prodRs) {
            AnchorPane card = createCard(prodR);
            tilePane.getChildren().add(card);
        }
    }

    private AnchorPane createCard(ProdR prodR) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("card");

        Label nomLabel = new Label("Nom: " + prodR.getNomP());
        nomLabel.getStyleClass().add("nom-label");
        card.getChildren().add(nomLabel);
        AnchorPane.setTopAnchor(nomLabel, 10.0);
        AnchorPane.setLeftAnchor(nomLabel, 10.0);

        Label descriptionLabel = new Label("Description: " + prodR.getDescription());
        descriptionLabel.getStyleClass().add("description-label");
        card.getChildren().add(descriptionLabel);
        AnchorPane.setTopAnchor(descriptionLabel, 30.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);

        Label typeProdLabel = new Label("Type: " + prodR.getNomTypeProd());
        typeProdLabel.getStyleClass().add("type-prod-label");
        card.getChildren().add(typeProdLabel);
        AnchorPane.setTopAnchor(typeProdLabel, 50.0);
        AnchorPane.setLeftAnchor(typeProdLabel, 10.0);

        Label ptcLabel = new Label("Point de collecte: " + prodR.getNomPtcId());
        ptcLabel.getStyleClass().add("ptc-label");
        card.getChildren().add(ptcLabel);
        AnchorPane.setTopAnchor(ptcLabel, 70.0);
        AnchorPane.setLeftAnchor(ptcLabel, 10.0);

        // Ajoutez une ImageView pour afficher l'image du produit
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200); // Définissez la largeur de l'image
        imageView.setFitHeight(200); // Définissez la hauteur de l'image
        imageView.setPreserveRatio(true); // Préservez le ratio de l'image
        imageView.setSmooth(true); // Lissage de l'image
        imageView.setCache(true); // Mise en cache de l'image pour une meilleure performance
        card.getChildren().add(imageView);
        AnchorPane.setTopAnchor(imageView, 90.0);
        AnchorPane.setRightAnchor(imageView, 10.0);
        if (prodR.getJustificatif() != null && !prodR.getJustificatif().isEmpty()) {
            File imageFile = new File(prodR.getJustificatif());
            if (imageFile.exists()) { // Vérifiez si le fichier image existe
                // Créer une image à partir du fichier
                Image image = new Image(imageFile.toURI().toString());
                // Affecter l'image à l'ImageView
                imageView.setImage(image);
            } else {
                // Gérez le cas où le fichier image n'existe pas
                System.out.println("Le fichier image n'existe pas : " + prodR.getJustificatif());
            }
        }
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEdit(prodR));
        card.getChildren().add(editButton);
        AnchorPane.setBottomAnchor(editButton, -30.0);
        AnchorPane.setRightAnchor(editButton, -00.0);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDelete(prodR));
        card.getChildren().add(deleteButton);
        AnchorPane.setBottomAnchor(deleteButton, -30.0);
        AnchorPane.setRightAnchor(deleteButton, 60.0);

        return card;
    }

    private void handleEdit(ProdR prodR) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pifx/prodR/EditProdR.fxml"));
            tilePane.getScene().setRoot(fxmlLoader.load());
//                        Parent root = loader.load();
            EditProdR controller = fxmlLoader.getController();
            controller.initialize(prodR.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(ProdR prodR) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de la suppression");
        alert.setContentText("Voulez-vous vraiment supprimer cet élément ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur clique sur OK, supprimez le produit
                prodRService.supprimer(prodR);
                refreshView();
                // Mettez à jour l'affichage en retirant la carte supprimée
                System.out.println("Suppression effectuée pour l'ID de Produit: " + prodR.getId());
            } else {
                // Si l'utilisateur clique sur Annuler, ne faites rien
                System.out.println("Suppression annulée pour l'ID de Produit: " + prodR.getId());
            }
        });
    }

    private void refreshView() {
        // Effacez toutes les cartes actuellement affichées dans le TilePane
        tilePane.getChildren().clear();
        // Rechargez les données et affichez-les à nouveau
        afficherProdR();
    }
}
