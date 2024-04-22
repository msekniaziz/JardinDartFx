package tn.esprit.ads.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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

public class affAdsController implements Initializable {

    @FXML
    private Label Category;
    @FXML
    private Label titleAds;

    @FXML
    private FlowPane flowPaneLads;

    @FXML
    private Label Description;

    @FXML
    private Label Negotiable;

    @FXML
    private Label priceAds;
    @FXML
    private ImageView imgSelected;

    @FXML
    private AnchorPane selectedCard;

    @FXML
    private Button brtn_DeleteAll;

    @FXML
    private Button btn_DeleteSelected;

    @FXML
    private Button btn_Edit;

    @FXML
    private Button btn_Refresh;

    @FXML
    private ComboBox<?> Sort;




    private Annonces selectedAds;

    private Sannonces sannonces;

    public void initialize(URL url, ResourceBundle resourceBundle ) {
        System.out.println("Chargement des données des Annonces...");
        sannonces = new Sannonces();
        loadAdsData();


    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadAdsData() {
        List<Annonces> annonces = sannonces.getAll();
        flowPaneLads.getChildren().clear();
        for (Annonces annonce : annonces) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardADS.fxml"));
                AnchorPane card = loader.load();
                CardAdsController controller = loader.getController();
                controller.initialize(annonce);
                card.setUserData(annonce);
                flowPaneLads.getChildren().add(card);
                card.setOnMouseClicked(this::onAdsSelected);
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des données des annonces.");
                e.printStackTrace();
            }
        }
    }

   /* private void onAdsSelected(MouseEvent event) {
        selectedCard = (AnchorPane) event.getSource();
        Object userData = selectedCard.getUserData();
        if (userData instanceof Annonces) {
            selectedAds = (Annonces) userData;
            System.out.println("Annonce sélectionnée : " + selectedAds.getTitle());
            initData(selectedAds);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
        }
    }*/
   private void onAdsSelected(MouseEvent event) {
       selectedCard = (AnchorPane) event.getSource();
       Object userData = selectedCard.getUserData();
       if (userData instanceof Annonces) {
           selectedAds = (Annonces) userData;
           System.out.println("Annonce sélectionnée : " + selectedAds.getTitle());
           updateSelectedCardFields(selectedAds); // Mettre à jour les champs avec les informations de la carte sélectionnée
       } else {
           System.err.println("Erreur : Données utilisateur invalides.");
       }}

    private void initData(Annonces selectedAds) {
        // Implement initialization logic here
        // Sélectionner la carte et afficher les détails de l'annonce sélectionnée
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardADS.fxml"));
            AnchorPane card = loader.load();
            CardAdsController controller = loader.getController();
            controller.setSelectedAds(selectedAds);
            // Ajoutez la carte sélectionnée à selectedCard
            selectedCard.getChildren().clear();
            selectedCard.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSelectedCardFields(Annonces selectedAds) {
        titleAds.setText(selectedAds.getTitle());
        priceAds.setText(String.valueOf(selectedAds.getPrix()));
        String negotiableStatus = selectedAds.getNegiciable() != 0 ? "Negotiable" : "Not Negotiable";
        Negotiable.setText( negotiableStatus);
        Description.setText(selectedAds.getDescription());

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
        Category.setText("Category : " + categoryName);
    }

    public void DeleteCatSelected(javafx.event.ActionEvent actionEvent) {
        sannonces.delete(selectedAds);
        loadAdsData(); // Recharger les données des annonces
    }

    public void RefrecheListe(ActionEvent actionEvent) {
        loadAdsData();
    }

    public void DeleteAllCat(ActionEvent actionEvent) {
        Sannonces cat = new Sannonces();
        boolean success = cat.deleteAll();
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "tous les Factures sont supprimés!");
            loadAdsData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "erreur.");
        }
    }

    /*public void EditCat(ActionEvent actionEvent) {
    }*/
    public void EditCat(ActionEvent actionEvent) {
        // Vérifiez d'abord si une annonce est sélectionnée
        if (selectedAds != null) {
            try {
                // Chargez la vue du formulaire d'édition
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditAds.fxml"));
                Parent root = loader.load();

                // Obtenez le contrôleur du formulaire d'édition
                EditAdFormController editAdFormController = loader.getController();

                // Passez l'annonce sélectionnée au contrôleur du formulaire d'édition
                editAdFormController.initData(selectedAds);

                // Créez une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenez la scène actuelle à partir de l'événement d'action
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                // Affichez le formulaire d'édition dans une nouvelle fenêtre
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérez l'exception si le chargement du formulaire d'édition échoue
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load edit form.");
            }
        } else {
            // Si aucune annonce n'est sélectionnée, affichez un message à l'utilisateur
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an ad to edit.");
        }
    }

    private List<Annonces> getAds() {
        Sannonces serviceAds = new Sannonces();
        return serviceAds.getAll();
    }

    @FXML
    void SortbyPrice(ActionEvent event) {
        List<Annonces> annonces = getAds();

        if (annonces != null) {
            // Récupérer la valeur sélectionnée dans le ComboBox
            String selectedOption = Sort.getValue().toString();

            // Trier les annonces en fonction de l'option sélectionnée
            switch (selectedOption) {
                case "sort by lowest price":
                    annonces.sort(Comparator.comparingDouble(Annonces::getPrix));
                    break;
                case "sort by highest price":
                    annonces.sort(Comparator.comparingDouble(Annonces::getPrix).reversed());
                    break;
                default:
                    break;
            }

            // Effacer les enfants existants de flowPaneLads
            flowPaneLads.getChildren().clear();

            // Recharger les données des annonces triées
            for (Annonces annonce : annonces) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardAds.fxml"));
                    AnchorPane card = loader.load();
                    CardAdsController controller = loader.getController();
                    controller.initialize(annonce);
                    flowPaneLads.getChildren().add(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void addToCart(ActionEvent actionEvent) {
    }
}
