package tn.esprit.ads.Controllers;

import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.test.MainFx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class cardMyAdsController implements Initializable {

    @FXML
    private VBox V1;

    @FXML
    private AnchorPane V2;

    @FXML
    private Label category;

    @FXML
    private Button deleteButton;
    @FXML
    private ImageView affichageImage;
    @FXML
    private Button uploadImage;

    @FXML
    private Label description;

    @FXML
    private Button editButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Label negotiable;

    @FXML
    private Label price;

    @FXML
    private Label title;

    private boolean selected = false;
    private Annonces ads;

    private Sannonces sannonces;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de CardMyAdsController...");
        sannonces = new Sannonces();
    }

    public void initialize(Annonces ads) {
        System.out.println("Initialisation de CardMyAdsController...");
        sannonces = new Sannonces(); // Déplacez l'initialisation ici

        if (ads == null) {
            System.err.println("Erreur: instance d'Annonces est nulle");
            return;
        }

        this.ads = ads;
        title.setText("Titre : " + ads.getTitle());
        description.setText("Description : " + ads.getDescription());
        price.setText("Prix : " + ads.getPrix());
        String negotiableStatus = ads.isNegotiable() ? "Negotiable" : "Not Negotiable";
        negotiable.setText("Négociable : " + negotiableStatus);

        Image image = new Image("file:" + ads.getImage());
        imageView.setImage(image);

        int categoryId = ads.getId_Cat();
        String categoryName = new Sannonces().getCategoryName(categoryId);
        category.setText("Catégorie : " + categoryName);
    }

    private affMyAdsController parentController;

    public void setParentController(affMyAdsController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void handleDelete(ActionEvent actionEvent) throws SQLException {
        if (sannonces == null) {
            System.err.println("Erreur : Sannonces non initialisée.");
            return;
        }

        if (ads == null) {
            System.err.println("Erreur : Aucune annonce sélectionnée.");
            return;
        }
        sannonces.delete(ads);
        MainFx.updateCurrentView("/affMyAds.fxml");
        //affMyAdsController card = new affMyAdsController();
      //  card.loadAdsData();
    }


   /* @FXML
    void handleEdit(ActionEvent event) {
        try {
            // Chargez la vue du formulaire d'édition
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditAds.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur du formulaire d'édition
            EditAdFormController editAdFormController = loader.getController();

            // Créez une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenez la scène actuelle à partir de l'événement d'action
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créez une nouvelle fenêtre modale pour le formulaire d'édition
            Stage editStage = new Stage();
            editStage.setScene(scene);
            editStage.initOwner(currentStage); // Définir la fenêtre parente
            editStage.initModality(Modality.WINDOW_MODAL); // Définir la modalité à WINDOW_MODAL

            // Affichez le formulaire d'édition dans une nouvelle fenêtre modale
            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez l'exception si le chargement du formulaire d'édition échoue
            parentController.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load edit form.");
        }
    }*/
  /* @FXML
   void handleEdit(ActionEvent event) {
       if (ads != null) {
           try {
               // Load the edit form view
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditAds.fxml"));
               Parent root = loader.load();

               // Get the controller of the edit form
               EditAdFormController editController = loader.getController();

               // Pass the selected category to the edit controller
               editController.initData(ads);

               // Create a new scene
               Scene scene = new Scene(root);

               // Get the current stage from the action event
               Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

               // Create a new modal window for the edit form
               Stage editStage = new Stage();
               editStage.setScene(scene);
               editStage.initOwner(currentStage); // Set the parent window
               editStage.initModality(Modality.WINDOW_MODAL); // Set modality to WINDOW_MODAL

               // Show the edit form in a new modal window
               editStage.show();
           } catch (IOException e) {
               e.printStackTrace();
               // Handle exception if loading the edit form fails
               showAlert(Alert.AlertType.ERROR, "Error", "Failed to load edit form.");
           }
       }
   }*/
   @FXML
   void handleEdit(ActionEvent event) {
       if (ads != null) {
           try {
               // Fermez la fenêtre actuelle
               Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               currentStage.close();

               // Chargez la vue du formulaire d'édition
               FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("/EditAds.fxml"));
               Parent root = loader.load();

               // Get the controller of the edit form
               EditAdFormController editController = loader.getController();

               // Pass the selected category to the edit controller
               editController.initData(ads);

               // Create a new scene
               Scene scene = new Scene(root);

               // Create a new stage for the edit form
               Stage editStage = new Stage();
               editStage.setScene(scene);
               editStage.show();
           } catch (IOException e) {
               e.printStackTrace();
               // Handle exception if loading the edit form fails
               showAlert(Alert.AlertType.ERROR, "Error", "Failed to load edit form.");
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

    @FXML
    void onAdsSelected(MouseEvent event) {
        AnchorPane adsCard = (AnchorPane) event.getSource();

        setSelected(!isSelected());

        adsCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        for (int i = 0; i < adsCard.getParent().getChildrenUnmodifiable().size(); i++) {
            AnchorPane node = (AnchorPane) adsCard.getParent().getChildrenUnmodifiable().get(i);
            if (node != adsCard) {
                cardMyAdsController otherController = (cardMyAdsController) node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Annonces getAds() {
        return ads;
    }

    public void setSelectedAds(Annonces selectedAds) {
        this.ads = selectedAds;
        title.setText(selectedAds.getTitle());
        description.setText(selectedAds.getDescription());
        price.setText(String.valueOf(selectedAds.getPrix()));
        negotiable.setText(selectedAds.isNegotiable() ? "Negotiable" : "Not Negotiable");
        category.setText(selectedAds.getCategory());
        Image image = new Image("file:" + selectedAds.getImage());
        imageView.setImage(image);
    }

}
