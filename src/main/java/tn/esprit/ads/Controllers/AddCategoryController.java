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
import tn.esprit.ads.tools.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCategoryController implements Initializable {


    @FXML
    private Label namecategory;
    @FXML
    private Label keycategory;

    private Connection con;

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

    public AddCategoryController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new FXMLLoader(getClass().getResource("addCategory.fxml"));
        Image image1 = new Image("file:src/main/java/tn/esprit/ads/img/jimmy-fallon.png");
        imageuser.setImage(image1);
        con=MyDataBase.getInstance().getCnx();
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
    boolean isAlreadyRegisteredWithMail() {
        PreparedStatement ps;
        ResultSet rs;
        boolean MailExist = false;
        String query = "select * from category WHERE name = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nameCat.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                MailExist = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return MailExist;
    }
    @FXML
    void Add(ActionEvent event) throws SQLException {
        if(checkIsValidated()) {
            try {
                int intValue = Integer.parseInt(keyCat.getText());
                Categories category = new Categories(nameCat.getText(), intValue);
                scategories.add(category);
                showAlert(Alert.AlertType.INFORMATION, "Catégorie ajoutée", "La catégorie a été ajoutée avec succès.");
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("affCategory.fxml")));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean CheckPhoneNumberConstraint(String test) {
        if (test.length() != 4) {
            return false;
        }
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }
    private boolean CheckLastNameConstraint(String test) {
        if (test.length() < 3) {
            return false;
        }
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            if (!(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }
    private boolean checkIsValidated() {

        boolean verif = true ;
        if (nameCat.getText().isEmpty()) {
            namecategory.setVisible(true);
            namecategory.setText("Category name field cannot be blank.");
            namecategory.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckLastNameConstraint(nameCat.getText())) {
            namecategory.setVisible(true);
            namecategory.setText("Category name must contain only characters [a-z], minimum length is 3");
            namecategory.setStyle("-fx-text-fill: red;");
            verif = false;
        }  else if (isAlreadyRegisteredWithMail()) {
            namecategory.setVisible(true);
            namecategory.setText("Category name already exists");
            namecategory.setStyle("-fx-text-fill: red;");
            verif = false;
        }else {
            namecategory.setVisible(false);
        }
        if (keyCat.getText().isEmpty()) {
            keycategory.setVisible(true);
            keycategory.setText("Key category field cannot be blank.");
            keycategory.setStyle("-fx-text-fill: red;");
            verif = false;
        } else if (!CheckPhoneNumberConstraint(keyCat.getText())) {
            keycategory.setVisible(true);
            keycategory.setText("The category key must consist of exactly 4 digits.");
            keycategory.setStyle("-fx-text-fill: red;");
            verif = false;
        } else {
            keycategory.setVisible(false);
        }
        if (!verif) {
            return false;
        }
        return true;

    }
}
