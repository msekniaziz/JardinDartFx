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
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Entity.Panier;
import tn.esprit.ads.Entity.User;
import tn.esprit.ads.Services.*;

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
    private TextField valeurAchercher;
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
    @FXML
    private ImageView PanierImage;




    private Annonces selectedAds;
    private Spanier pnss;
    Spanier pns = new Spanier();

    private Sannonces sannonces;
    Panier p ;
    SpanierAnnonce pa = new SpanierAnnonce();
    private String newValue = "";

    public void initialize(URL url, ResourceBundle resourceBundle ) {
        System.out.println("Chargement des données des Annonces...");
        sannonces = new Sannonces();
        loadAdsData();
        filterAds("");
        valeurAchercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAds(newValue);
        });
    }

    private void filterAds(String searchTerm) {
        List<Annonces> filteredAds = sannonces.getAll().stream()
                .filter(ad -> ad.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        ad.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());

        flowPaneLads.getChildren().clear();
        for (Annonces annonce : filteredAds) {
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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardADS.fxml"));
            AnchorPane card = loader.load();
            CardAdsController controller = loader.getController();
            controller.setSelectedAds(selectedAds);

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


        Image image = new Image("file:" + selectedAds.getImage());
        imgSelected.setImage(image);


       // Category.setText(selectedAds.getId_Cat());
      //  Category.setText("anas");

        int categoryId = selectedAds.getId_Cat();
        String categoryName = new Sannonces().getCategoryName(categoryId);


        Category.setText("Category : " + categoryName);
    }

    /*public void DeleteCatSelected(javafx.event.ActionEvent actionEvent) {
        sannonces.delete(selectedAds);
        loadAdsData(); // Recharger les données des annonces
    }*/

    public void RefrecheListe(ActionEvent actionEvent) {
        loadAdsData();
    }


    private List<Annonces> getAds() {
        Sannonces serviceAds = new Sannonces();
        return serviceAds.getAll();
    }

    @FXML
    void SortbyPrice(ActionEvent event) {
        List<Annonces> annonces = getAds();

        if (annonces != null) {

            String selectedOption = Sort.getValue().toString();


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



    /*
      private Annonces selectedAds;
    private Spanier pnss;
    Spanier pns = new Spanier();

    private Sannonces sannonces;
    Panier p ;
    SpanierAnnonce pa = new SpanierAnnonce();
     */

    public void addToCart(ActionEvent actionEvent) {
        int idUtilisateur = 5;

        Suser utilisateurService = new Suser();
        User user = utilisateurService.getUserById(idUtilisateur);

        if (user != null) {

            Panier panier = pns.selectPanierParUserId(user.getId());

            if (panier != null) {


                pa.ajouterProduitAuPanier(panier.getIdPanier(), selectedAds.getId());
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté au panier.");
            } else {

                pns.ajouterPanier(user.getId());
                showAlert(Alert.AlertType.WARNING, "Avertissement", "Vous n'avez pas de panier. Un nouveau panier a été créé.");
            }
        } else {

            showAlert(Alert.AlertType.ERROR, "Erreur", "Utilisateur introuvable.");
        }
    }



    public void navigateToViewPr(ActionEvent actionEvent) {
    }

    @FXML
   /* public void showpanier(MouseEvent event) throws IOException {
        // Charger le fichier FXML de la vue du panier
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
        Parent panierView = loader.load();
        PanierController controller = loader.getController();
        controller.initialize(p.getIdPanier());

        // Créer une nouvelle scène
        Scene panierScene = new Scene(panierView);

        // Obtenir la fenêtre actuelle à partir de l'événement de la souris
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Afficher la vue du panier dans la fenêtre actuelle
        currentStage.setScene(panierScene);
        currentStage.show();
    }*/
    public void showpanier(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
        Parent panierView = loader.load();
        PanierController controller = loader.getController();


        Panier panier = pns.selectPanierParUserId(5);
        if (panier != null) {
            int idPanier = panier.getIdPanier();
            controller.initialize(idPanier);
        } else {

            System.err.println("Aucun panier trouvé pour cet utilisateur.");

            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun panier trouvé pour cet utilisateur.");
            return;
        }


        Scene panierScene = new Scene(panierView);


        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        currentStage.setScene(panierScene);
        currentStage.show();
    }



}
