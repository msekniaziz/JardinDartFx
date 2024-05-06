package tn.jardindart.controllers.Ads;

import com.mysql.cj.Session;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.Annonces;
import tn.jardindart.services.Sannonces;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class affMyAdsController implements Initializable {

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
    private ScrollPane scroll;



    private Annonces selectedAds;

    private Sannonces sannonces;
    private int userid = SessionManager.getInstance().getUserFront();
    public affMyAdsController() {
        this.flowPaneLads = flowPaneLads;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chargement des données des mes Annonces...");
        sannonces = new Sannonces();
        loadAdsData();
    }

    void loadAdsData() {
        Sannonces sannonces = new Sannonces();
        List<Annonces> annonces = sannonces.getAllMyAds(userid);
        for (Annonces annonce : annonces) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Ads/cardMyAds.fxml"));
                AnchorPane card = loader.load();
                cardMyAdsController controller = loader.getController();
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

    void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void onAdsSelected(MouseEvent event) {
        selectedCard = (AnchorPane) event.getSource();
        Object userData = selectedCard.getUserData();
        if (userData instanceof Annonces) {
            selectedAds = (Annonces) userData;
            System.out.println("Annonce sélectionnée : " + selectedAds.getTitle());
            updateSelectedCardFields(selectedAds);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
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

    @FXML
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
    @FXML
    public void DeleteCatSelected(ActionEvent actionEvent) {

        sannonces.delete(selectedAds);
        loadAdsData();
    }

    @FXML
    void EditCat(ActionEvent actionEvent) {
        // Vérifiez d'abord si une annonce est sélectionnée
        if (selectedAds != null) {
            try {
                // Chargez la vue du formulaire d'édition
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Ads/EditAds.fxml"));
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

    @FXML
    public void RefrecheListe(ActionEvent actionEvent) {
        loadAdsData();
    }

    public void navigateToViewPr(ActionEvent actionEvent) {
    }

    public void refreshFlowPane() throws SQLException {
        flowPaneLads.getChildren().clear(); // Clear existing items

        List<Annonces> annonces = sannonces.getAllMyAds(5);

        int col = 0;
        int rows = 0;

        try {
            for (int i = 0; i < annonces.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn.jardindart/Ads/cardMyAds.fxml"));
                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                cardMyAdsController mycard = fxmlLoader.getController();
                mycard.setSelectedAds(annonces.get(i));
              //  flowPaneLads.add(anchorPane, col, rows++);
                flowPaneLads.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            System.err.println("Error loading itembook.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
