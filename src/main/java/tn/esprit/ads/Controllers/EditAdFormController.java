package tn.esprit.ads.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.Services.Scategories;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EditAdFormController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField categoryField;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button edit;

    @FXML
    private ImageView imgSelected;

    @FXML
    private TextField negotiableField;

    @FXML
    private TextField priceField;





    @FXML
    private AnchorPane selectedCard;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation du formulaire
    }

    public void initData(Annonces selectedAds) {
        titleField.setText(selectedAds.getTitle());

        priceField.setText(String.valueOf(selectedAds.getPrix()));
        String negotiableStatus = selectedAds.getNegiciable() != 0 ? "Negotiable" : "Not Negotiable";
        negotiableField.setText( negotiableStatus);
        descriptionField.setText(selectedAds.getDescription());

        // Mise à jour de l'image
        Image image = new Image("file:" + selectedAds.getImage());
        imgSelected.setImage(image);

        // Mise à jour de la catégorie
        // Category.setText(selectedAds.getId_Cat());
        //  Category.setText("anas");
        // Récupérer le nom de la catégorie à partir de l'id_cat_id
        int categoryId = selectedAds.getId_Cat();
        String categoryName = new Sannonces().getCategoryName(categoryId);

        // Afficher le nom de la catégorie dans l'interface utilisateur
        categoryField.setText("Category : " + categoryName);
    }


    @FXML
    public void Edit(ActionEvent actionEvent) {
        // Récupérer les valeurs modifiées depuis les champs de texte
        String newTitle = titleField.getText();
        String newDescription = descriptionField.getText();
        double newPrice = Double.parseDouble(priceField.getText());
        int newNegotiable = negotiableField.getText().equalsIgnoreCase("Negotiable") ? 1 : 0; // Convertir en entier (1 ou 0)

        // Créer un nouvel objet Annonces avec les nouvelles valeurs
        Annonces updatedAd = new Annonces();
        updatedAd.setTitle(newTitle);
        updatedAd.setDescription(newDescription);
        updatedAd.setPrix(newPrice);
        updatedAd.setNegiciable(newNegotiable); // Utiliser setNegotiable au lieu de setNegiciable

        // Créer une instance de Sannonces
        Sannonces sannonces = new Sannonces();

        // Mettre à jour l'annonce dans la base de données et récupérer le résultat
        boolean success = sannonces.updateAndReturnSuccess(updatedAd);

        if (success) {
            // Afficher un message de réussite
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Ad updated successfully!");
            alert.showAndWait();

            // Fermer la fenêtre d'édition
            Stage stage = (Stage) edit.getScene().getWindow();
            stage.close();
        } else {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update ad!");
            alert.showAndWait();
        }
    }


}
