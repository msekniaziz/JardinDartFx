package tn.jardindart.controllers.prodR;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.StringConverter;
import tn.jardindart.controllers.MenuBack;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.ProdR;
import tn.jardindart.services.ProdRService;
import tn.jardindart.test.HelloApplication;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ViewProdR implements Initializable {
    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<ProdR> searchComboBox;

    @FXML
    private TilePane tilePane;

    private ProdRService prodRService;

    MenuBack menuc;
    private List<ProdR> prodRs = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodRService = new ProdRService();

        afficherProdR();
        initializeSearchField();
        initializeSearchComboBox();
    }
    private void initializeSearchField() {
        searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);
    }

    private void handleSearchFieldKeyReleased(javafx.scene.input.KeyEvent event) {
        String searchTerm = searchField.getText().toLowerCase();
        List<ProdR> filteredProdRs = prodRs.stream()
                .filter(prodR -> prodR.getNomP().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        searchComboBox.setItems(FXCollections.observableArrayList(filteredProdRs));

        if (event.getCode().isArrowKey()) {
            searchComboBox.hide();
        } else {
            searchComboBox.show();
        }
    }
    public void initializeSearchComboBox() {
        searchComboBox.setConverter(new StringConverter<ProdR>() {
            @Override
            public String toString(ProdR object) {
                // Retourner la représentation sous forme de chaîne de l'objet ProdR
                return object.toString();
            }

            @Override
            public ProdR fromString(String string) {
                // Convertir la chaîne en un objet ProdR (si nécessaire)
                // Retourner null si la conversion n'est pas possible
                return null;
            }
        });
    }    private void handleSearchComboBoxAction() {
        ProdR selectedProdR = searchComboBox.getValue();
        if (selectedProdR != null) {
            // Traitez l'action lorsque l'utilisateur sélectionne un produit dans la recherche
            // Par exemple, affichez les détails du produit ou effectuez une autre opération
            System.out.println("Produit sélectionné : " + selectedProdR.getNomP());
        }
    }
    private void afficherProdR() {
        int userId = SessionManager.getInstance().getUserFront(); // Récupérer l'ID de l'utilisateur connecté

        List<ProdR> prodRs = prodRService.recupererParUtilisateur(userId); // Obtenez les produits de l'utilisateur connecté

        for (ProdR prodR : prodRs) {
            AnchorPane card = createCard(prodR);
            tilePane.getChildren().add(card);
        }
        prodRs = prodRService.recupererParUtilisateur(userId);

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
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn.jardindart/prodR/EditProdR.fxml"));
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
