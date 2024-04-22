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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Entity.Commandes;
import tn.esprit.ads.Services.Scategories;
import tn.esprit.ads.Services.Scommandes;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AffOrderController implements Initializable {

    @FXML
    private TextField SearchBarOrder;
    @FXML
    private Button affOrder;
    @FXML
    private Button affAds;

    @FXML
    private ImageView SearchButtonUserClick;

    @FXML
    private ImageView imageuser;


    @FXML
    private Label adminName;

    @FXML
    private Button affCategory;


    @FXML
    private Button brtn_DeleteAll1;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btn_DeleteSelected1;

    @FXML
    private Button btn_Refresh1;

    @FXML
    private FlowPane flowPaneLOrder;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Button sort1;
    private Commandes selectedOrder;
    Scommandes Sc =new Scommandes();
    @FXML
    Commandes Order;
    private FXMLLoader CardLoader;
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chargement des données des Commandes...");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("affOrder.fxml"));



        CardLoader = new FXMLLoader(getClass().getResource("/cardOrder.fxml"));
        loadCartData();

        System.out.println("Chargement des données de category...");
        Image image1 = new Image("file:src/main/java/tn/esprit/ads/img/jimmy-fallon.png");
        imageuser.setImage(image1);


    }
    @FXML


    private void loadCartData() {
        List<Commandes> order = Sc.getAll();

        flowPaneLOrder.getChildren().clear();
        for (Commandes com : order) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardOrder.fxml"));
                AnchorPane card = loader.load();
                CardOrderController controller = loader.getController();
                controller.initialize(com); // Initialise la carte avec les données de catégorie
                card.setUserData(com); // Définit les données utilisateur de la carte comme la catégorie
                flowPaneLOrder.getChildren().add(card);

                // Mettre en place l'événement de sélection de la carte de catégorie
                card.setOnMouseClicked(event -> {
                    onOrderSelected(event);
                    updateOrderCards(); // Mettre à jour l'apparence des cartes de catégorie
                });
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des données de catégorie.");
                e.printStackTrace();
            }
        }
    }
    private List<Commandes> getOrder() {
        Scategories serviceCat = new Scategories();
        return Sc.getAll();
    }
    private AnchorPane getSelectedCard() {
        for (Node node : flowPaneLOrder.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane card = (AnchorPane) node;
                if (card.getStyle().contains("-fx-background-color: lightblue;")) {
                    return card;
                }
            }
        }
        return null;
    }
    @FXML
    void DeleteAllOrder(ActionEvent event) {
        Scommandes order = new Scommandes();
        boolean success = order.deleteAll();
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "tous les commandes sont supprimés!");
            loadCartData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "erreur.");
        }



    }

    @FXML
    void DeleteOrderSelected(ActionEvent event) {
        Sc.delete(selectedOrder);
        loadCartData(); // Recharger les données de commande

    }

    @FXML
    void RefrecheOrder(ActionEvent event) {
        loadCartData();

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
    void sortByDate(ActionEvent event) {

    }
    void onOrderSelected(MouseEvent event) {
        AnchorPane selectedCard = (AnchorPane) event.getSource();
        Object userData = selectedCard.getUserData();

        if (userData != null && userData instanceof Commandes) {
            Commandes selectedOrder = (Commandes) userData;

            System.out.println("Commande sélectionnée : " + selectedOrder.getId());

            this.selectedOrder = selectedOrder;
            initData(selectedOrder);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
        }
    }

    private void initData(Commandes selectedOrder) {
    }
    private void updateOrderCards() {
        for (Node node : flowPaneLOrder.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane card = (AnchorPane) node;
                CardOrderController controller = (CardOrderController) card.getProperties().get("controller");
                Commandes commandes = controller.getCom();
                if (commandes.isSelected()) {
                    card.setStyle("-fx-background-color: lightblue;");
                } else {
                    card.setStyle("-fx-background-color: white;");
                }
            }
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
