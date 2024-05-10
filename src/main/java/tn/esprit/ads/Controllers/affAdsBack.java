package tn.jardindart.controllers.Ads;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import tn.jardindart.models.Annonces;
import tn.jardindart.models.Categories;
import tn.jardindart.services.Sannonces;
import tn.jardindart.services.Scategories;

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
    @FXML
    private Label labelAdsSold;

    @FXML
    private Label labelAdsUnsold;

    @FXML
    private PieChart pieChart;

    private Sannonces sannonces;



    private Annonces selectedAds;

   // private Sannonces sannonces;

    public void initialize(URL url, ResourceBundle resourceBundle ) {
        System.out.println("Chargement des données des Annonces...");
        sannonces = new Sannonces();
        loadAdsData();
        updateStatistics();


    }
    private void updateStatistics() {
        List<Annonces> allAds = sannonces.getAllBack();
        long adsSold = allAds.stream().filter(a -> a.getStatus() == 1).count();
        long adsUnsold = allAds.size() - adsSold;

        labelAdsSold.setText("Ads sold: " + adsSold);
        labelAdsUnsold.setText("Ads unsold: " + adsUnsold);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Ads sold", adsSold),
                new PieChart.Data("Ads unsold", adsUnsold)
        );

        pieChart.setData(pieChartData);
    }

    // Méthode appelée pour rafraîchir les statistiques après une opération (par exemple, ajout ou suppression d'une annonce)
    public void refreshStatistics() {
        updateStatistics();
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadAdsData() {
        List<Annonces> annonces = sannonces.getAllBack();
        flowPaneLads.getChildren().clear();
        for (Annonces annonce : annonces) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Ads/cardAdsBack.fxml"));
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
    }


    public void DeleteCatSelected(javafx.event.ActionEvent actionEvent) {
        sannonces.delete(selectedAds);
        loadAdsData();
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
        return serviceAds.getAllBack();
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


            flowPaneLads.getChildren().clear();


            for (Annonces annonce : annonces) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Ads/cardAds.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/tn.jardindart/Ads/affCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.jardindart.controllers.Ads.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void affOrder(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/tn.jardindart/Ads/affOrder.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.jardindart.controllers.Ads.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    void affAds(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/tn.jardindart/Ads/affAdsBack.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.jardindart.controllers.Ads.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
