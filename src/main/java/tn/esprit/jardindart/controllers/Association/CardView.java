package tn.esprit.jardindart.controllers.Association;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.jardindart.controllers.DA.AddDA;
import tn.esprit.jardindart.controllers.DBM.AddDBM;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.services.AssociationService;
import tn.esprit.jardindart.test.HelloApplication;

import javafx.scene.input.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CardView implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField searchField;

    private AssociationService associationService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        associationService = new AssociationService();
        displayAssociations();
        searchField.setOnKeyReleased(event -> rechercherAssociations(event));

        //searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercherAssociations(newValue));
    }

    private void displayAssociations() {
        List<Association> associations = associationService.afficher();

        int col = 0;
        int row = 0;
        for (Association association : associations) {
            AnchorPane card = createCard(association);
            gridPane.add(card, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    @FXML
    private void rechercherAssociations(KeyEvent event) {
        String searchTerm = searchField.getText();
        List<Association> associations = associationService.afficher();

        if (!searchTerm.isEmpty()) {
            associations = associations.stream()
                    .filter(a -> a.getNom_association().toLowerCase().contains(searchTerm.toLowerCase())
                            || a.getAdresse_association().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }

        gridPane.getChildren().clear();

        int col = 0;
        int row = 0;
        for (Association association : associations) {
            AnchorPane card = createCard(association);
            gridPane.add(card, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    private AnchorPane createCard(Association association) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("card");


        ImageView imageView = new ImageView();
        imageView.setFitWidth(200); // Définissez la largeur de l'image
        imageView.setFitHeight(200); // Définissez la hauteur de l'image
        imageView.setPreserveRatio(true); // Préservez le ratio de l'image
        imageView.setSmooth(true); // Lissage de l'image
        imageView.setCache(true); // Mise en cache de l'image pour une meilleure performance

        card.getChildren().add(imageView);

        AnchorPane.setTopAnchor(imageView, 10.0);
        AnchorPane.setRightAnchor(imageView, 5.0);


        if (association.getLogo_association() != null && !association.getLogo_association().isEmpty()) {
            File imageFile = new File(association.getLogo_association());
            if (imageFile.exists()) { // Vérifiez si le fichier image existe
                // Créer une image à partir du fichier
                Image image = new Image(imageFile.toURI().toString());
                // Affecter l'image à l'ImageView
                imageView.setImage(image);
            } else {
                System.out.println("Le fichier image n'existe pas : " + association.getLogo_association());
            }
        }

        Label nameLabel = new Label( association.getNom_association());
        nameLabel.getStyleClass().add("name-label");
        AnchorPane.setTopAnchor(nameLabel, 180.0);
        AnchorPane.setLeftAnchor(nameLabel, 10.0);
        card.getChildren().add(nameLabel);

        Label addressLabel = new Label("Adresse: " + association.getAdresse_association());
        addressLabel.getStyleClass().add("address-label");
        AnchorPane.setTopAnchor(addressLabel, 200.0);
        AnchorPane.setLeftAnchor(addressLabel, 10.0);
        card.getChildren().add(addressLabel);

        Label descriptionLabel = new Label("Description: " + association.getDescription_asso());
        descriptionLabel.getStyleClass().add("description-label");
        AnchorPane.setTopAnchor(descriptionLabel, 220.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);
        card.getChildren().add(descriptionLabel);

        Label ribLabel = new Label("RIB: " + association.getRib());
        ribLabel.getStyleClass().add("rib-label");
        AnchorPane.setTopAnchor(ribLabel, 240.0);
        AnchorPane.setLeftAnchor(ribLabel, 10.0);
        card.getChildren().add(ribLabel);

        Button donArgentButton = new Button("Ajouter don argent");
        donArgentButton.setOnAction(event -> handleDonArgent(association));
        AnchorPane.setTopAnchor(donArgentButton, 260.0);
        AnchorPane.setLeftAnchor(donArgentButton, 10.0);
        card.getChildren().add(donArgentButton);

        Label separatorLabel = new Label();
        separatorLabel.getStyleClass().add("separator-label");
        AnchorPane.setTopAnchor(separatorLabel, 260.0);
        AnchorPane.setLeftAnchor(separatorLabel, 135.5);
        card.getChildren().add(separatorLabel);

        Button donMaterielButton = new Button("Ajouter don bien matériel");
        donMaterielButton.setOnAction(event -> handleDonMateriel(association));
        AnchorPane.setTopAnchor(donMaterielButton, 260.0);
        AnchorPane.setLeftAnchor(donMaterielButton, 160.0);
        card.getChildren().add(donMaterielButton);

        return card;
    }

    private void handleDonArgent(Association association) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DA/AddDA.fxml"));
        try {
            gridPane.getScene().setRoot(fxmlLoader.load());
            AddDA controller = fxmlLoader.getController();
            controller.initialize(association);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleDonMateriel(Association association) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DBM/AddDBM.fxml"));
        try {
            gridPane.getScene().setRoot(fxmlLoader.load());
            AddDBM controller = fxmlLoader.getController();
            controller.initialize(association);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
