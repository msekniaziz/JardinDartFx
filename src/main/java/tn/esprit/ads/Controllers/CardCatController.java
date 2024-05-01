package tn.esprit.ads.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Categories;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import tn.esprit.ads.Services.Scategories;
import tn.esprit.ads.test.MainFx;

public class CardCatController {

    @FXML
    private FlowPane flowPaneLCat;


    @FXML
    private Label Key;

    @FXML
    private Label Name;


    @FXML
    private Label Number;

    @FXML
    private VBox V1;

    @FXML
    private AnchorPane V2;
    @FXML
    private Button deleteC;

    @FXML
    private Button editC;
    @FXML
    private Button nbr;
    private Categories selectedCategory;
    Scategories catt =new Scategories();
    updateCategoryController p = new updateCategoryController();


    private Categories cat ;


    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /*@FXML
    public void onCatSelected(MouseEvent event) {
        // Récupérer la carte de catégorie cliquée
        AnchorPane catCard = (AnchorPane) event.getSource();

        // Mettre à jour l'état de sélection de la carte
        setSelected(!isSelected());

        // Mettre à jour l'apparence de la carte en fonction de l'état de sélection
        catCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        // Désélectionner les autres cartes
        for (int i = 0; i < flowPaneLCat.getChildren().size(); i++) {
            AnchorPane node = (AnchorPane) flowPaneLCat.getChildren().get(i);
            if (node != catCard) {
                CardCatController otherController = (CardCatController) node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }

        // Appeler la méthode initData du contrôleur principal pour mettre à jour les champs nameCat et keyCat
        AffCategoryController controller = (AffCategoryController) flowPaneLCat.getParent().getProperties().get("controller");
        controller.initData(cat);
    }*/
    @FXML
    public void onCatSelected(MouseEvent event) {
        // Récupérer la carte de catégorie cliquée
        AnchorPane catCard = (AnchorPane) event.getSource();

        // Mettre à jour l'état de sélection de la carte
        setSelected(!isSelected());

        // Mettre à jour l'apparence de la carte en fonction de l'état de sélection
        catCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        // Désélectionner les autres cartes
        for (int i = 0; i < flowPaneLCat.getChildren().size(); i++) {
            AnchorPane node = (AnchorPane) flowPaneLCat.getChildren().get(i);
            if (node != catCard) {
                CardCatController otherController = (CardCatController) node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }

        // Mettre à jour la catégorie sélectionnée
        selectedCategory = cat;

        // Appeler la méthode initData du contrôleur principal pour mettre à jour les champs nameCat et keyCat
        AffCategoryController controller = (AffCategoryController) flowPaneLCat.getParent().getProperties().get("controller");
        controller.initData(cat);
    }

    @FXML
    void deleteC(ActionEvent event) {
        if ( catt== null) {
            System.err.println("Erreur : Sannonces non initialisée.");
            return;
        }

        if (cat == null) {
            System.err.println("Erreur : Aucune annonce sélectionnée.");
            return;
        }
        catt.delete(cat);
        MainFx.updateCurrentView("/affCategory.fxml");

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de FactureCardController...");
    }

    public void initialize(Categories cat) {
        if (cat == null) {
            System.err.println("Erreur: instance de Facture est nulle");
            return;
        }

        this.cat = cat;
        Name.setText("Name:" + cat.getName());
        Key.setText("key : " + cat.getKey_cat());
        nbr.setText("Number Ads :"+cat.getNbr_annonce());

    }

    public Categories getCat() {
        return cat;
    }


   /* @FXML
    void editC(ActionEvent event) {
        if (cat != null) {
            try {
                // Récupération des valeurs saisies
                String newName = Name.getText();
                String keyText = Key.getText();

                // Extract numeric part from keyText
                String[] parts = keyText.split(":");
                if (parts.length != 2) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Format de clé invalide.");
                    return;
                }
                int newKey = Integer.parseInt(parts[1].trim());

                // Vérifier si le nom de la catégorie est vide
                if (newName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie ne peut pas être vide.");
                    return;
                }

                // Vérifier si la clé de la catégorie est vide
                if (keyText.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de la catégorie ne peut pas être vide.");
                    return;
                }

                // Mise à jour des attributs de la catégorie avec les nouvelles valeurs
                cat.setName(newName);
                cat.setKey_cat(newKey);

                // Appel de la méthode de mise à jour du service de catégorie
                catt.update(selectedCategory);

                // Afficher un message de succès
                showAlert(Alert.AlertType.INFORMATION, "Succès", "La catégorie a été mise à jour avec succès.");
                MainFx.updateCurrentView("/affCategory.fxml");

            } catch (NumberFormatException e) {
                // En cas d'erreur de conversion de type, afficher un message d'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs numériques valides pour la clé de catégorie.");
            } catch (Exception e) {
                // En cas d'erreur, afficher un message d'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour de la catégorie : " + e.getMessage());
            }
        }
    }*/
  /* @FXML
   void editC(ActionEvent event) {
      try {
        // Chargez la vue du formulaire d'édition
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateCat.fxml"));
        Parent root = loader.load();

        // Obtenez le contrôleur du formulaire d'édition
        updateCategoryController edit = loader.getController();

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
    } catch (
    IOException e) {
        e.printStackTrace();
        // Gérez l'exception si le chargement du formulaire d'édition échoue
        p.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load edit form.");
    }}*/
   @FXML
   void editC(ActionEvent event) {
       if (cat != null) {
           try {
               // Load the edit form view
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateCat.fxml"));
               Parent root = loader.load();

               // Get the controller of the edit form
               updateCategoryController editController = loader.getController();

               // Pass the selected category to the edit controller
               editController.initData(cat);

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
   }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

   /* @FXML
    void nbrAds(ActionEvent event) {  try {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affAdsByCat.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
    }


    }*/
   @FXML
   void nbrAds(ActionEvent event) {
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



}


