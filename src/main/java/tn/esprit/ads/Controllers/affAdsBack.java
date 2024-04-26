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

public class affAdsBack implements Initializable {

    @FXML
    private Label Category;

    @FXML
    private FlowPane flowPaneLads;

    @FXML
    private Label Description;

    @FXML
    private Label Negotiable;

    @FXML
    private Label priceAds;

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
    @FXML
    private ImageView imageuserads;



    private Annonces selectedAds;

    private Sannonces sannonces;

    public void initialize(URL url, ResourceBundle resourceBundle ) {
        System.out.println("Chargement des données des Annonces...");
        sannonces = new Sannonces();
        loadAdsData();
        Image image = new Image("file:src/main/java/tn/esprit/ads/img/jimmy-fallon.png");
        imageuserads.setImage(image);


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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardAdsBack.fxml"));
                AnchorPane card = loader.load();
                CardAdsBackController controller = loader.getController();
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

    private void onAdsSelected(MouseEvent event) {
        selectedCard = (AnchorPane) event.getSource();
        Object userData = selectedCard.getUserData();
        if (userData instanceof Annonces) {
            selectedAds = (Annonces) userData;
            System.out.println("Annonce sélectionnée : " + selectedAds.getTitle());
            initData(selectedAds);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
        }
    }

    private void initData(Annonces selectedAds) {
        // Implement initialization logic here
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

    public void EditCat(ActionEvent actionEvent) {
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
    @FXML
    void affCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void affOrder(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affOrder.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    void affAds(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affAdsBack.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
