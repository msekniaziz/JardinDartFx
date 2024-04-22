package tn.esprit.ads.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Services.Scategories;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCategoryController implements Initializable {

    @FXML
    private ImageView SearchButtonUserClick;

    @FXML
    private Label adminName;

    @FXML
    private Button affCategory;
    @FXML
    private Button affOrder;
    @FXML
    private Button affAds;

    @FXML
    private Button btnCustomers;

    @FXML
    private Pane cat;

    @FXML
    private Rectangle key;

    @FXML
    private TextField keyCat;

    @FXML
    private TextField nameCat;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ImageView imageuser;
    @FXML
    private Button buttonadd;

    private Scategories scategories = new Scategories();
    private FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new FXMLLoader(getClass().getResource("addCategory.fxml"));
        Image image1 = new Image("file:src/main/java/tn/esprit/ads/img/jimmy-fallon.png");
        imageuser.setImage(image1);

    }

    /*@FXML
    void Add(ActionEvent event) throws SQLException {
        System.out.println("name: " + nameCat.getText());
        System.out.println("keycat: " + keyCat.getText());

        // Récupération des valeurs des champs de texte
        String name = nameCat.getText();
        String key = keyCat.getText();

        // Vérifier si le champ de texte pour le nom de la catégorie est vide
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie ne peut pas être vide.");
            return; // Arrêter l'exécution de la méthode
        }

        try {
            // Vérification de la validité de la clé (doit être un entier)
            int keyInt = Integer.parseInt(key);

            // Création de l'objet Categories
            Categories category = new Categories(name, keyInt);

            // Appel de la méthode add pour ajouter la catégorie
            scategories.add(category);

            // Affichage d'une alerte de succès
            showAlert(Alert.AlertType.INFORMATION, "Catégorie ajoutée", "La catégorie a été ajoutée avec succès.");

        } catch (NumberFormatException e) {
            // En cas d'erreur de conversion de la clé en entier
            showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de catégorie doit être un nombre entier.");
        }
    }*/
    @FXML
    void Add(ActionEvent event) throws SQLException {
        System.out.println("name: " + nameCat.getText());
        System.out.println("keycat: " + keyCat.getText());


        // Récupération des valeurs des champs de texte
        String name = nameCat.getText();
        String key = keyCat.getText();

        // Expression régulière pour vérifier si le nom ne contient que des lettres et des espaces
        String nameRegex = "^[a-zA-Z\\s]+$";

        // Expression régulière pour vérifier si la clé ne contient que des chiffres
        String keyRegex = "^[0-9]+$";

        // Vérifier si le champ de texte pour le nom de la catégorie est vide
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie ne peut pas être vide.");
            return; // Arrêter l'exécution de la méthode
        }

        // Vérifier si le nom de la catégorie correspond au format requis
        if (!name.matches(nameRegex)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie doit contenir uniquement des lettres et des espaces.");
            return; // Arrêter l'exécution de la méthode
        }

        // Vérifier si la clé de catégorie est vide
        if (key.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de catégorie ne peut pas être vide.");
            return; // Arrêter l'exécution de la méthode
        }

        // Vérifier si la clé de catégorie correspond au format requis
        if (!key.matches(keyRegex)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de catégorie doit être un nombre entier.");
            return; // Arrêter l'exécution de la méthode
        }
      //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("affCategory.fxml"));
        try {
            // Conversion de la clé en entier
            int keyInt = Integer.parseInt(key);

            // Création de l'objet Categories
            Categories category = new Categories(name, keyInt);

            // Appel de la méthode add pour ajouter la catégorie
            scategories.add(category);
            showAlert(Alert.AlertType.INFORMATION, "Catégorie ajoutée", "La catégorie a été ajoutée avec succès.");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            // En cas d'erreur de conversion de la clé en entier
            showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de catégorie doit être un nombre entier.");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    void Category(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
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
