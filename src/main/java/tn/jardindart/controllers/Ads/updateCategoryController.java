package tn.jardindart.controllers.Ads;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.jardindart.controllers.Menu;
import tn.jardindart.models.Annonces;
import tn.jardindart.models.Categories;
import tn.jardindart.services.Sannonces;
import tn.jardindart.services.Scategories;
import tn.jardindart.test.HelloApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updateCategoryController {

    @FXML
    private ImageView SearchButtonUserClick;

    @FXML
    private Label adminName;

    @FXML
    private Button affAds;

    @FXML
    private Button affCategory;

    @FXML
    private Button affOrder;

    @FXML
    private Button allCategory;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button buttonadd;

    @FXML
    private Pane cat;

    @FXML
    private ImageView imageuser;

    @FXML
    private Rectangle key;

    @FXML
    private TextField keyCat;

    @FXML
    private Label keycategory;

    @FXML
    private TextField nameCat;

    @FXML
    private Label namecategory;

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


    Scategories catt =new Scategories();

    Menu menuc = new Menu();

    private Categories categ ;
    private Connection con;

    /*@FXML
    void Edit(ActionEvent event) {
        // Récupérer les valeurs modifiées depuis les champs de texte
        String newTitle = nameCat.getText();
        int keycat = Integer.parseInt(keyCat.getText());

        // Créer un nouvel objet Categories avec les nouvelles valeurs
        categ.setName(newTitle);
        categ.setKey_cat(keycat);

        // Mettre à jour la catégorie dans la base de données
        catt.update(categ);
        // Si la mise à jour réussit, afficher une alerte de succès
        showAlert(Alert.AlertType.INFORMATION, "Success", "Category updated successfully.");
    }*/

    // Méthode pour afficher une alerte
    void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    void Category(ActionEvent event) {

    }

    @FXML
    void affAds(ActionEvent event) {

    }

    @FXML
    void affOrder(ActionEvent event) {

    }
   /* public void initData(Categories cat) {
        nameCat.setText(cat.getName());

        keyCat.setText(String.valueOf(cat.getKey_cat()));


    }*/
   public void initData(Categories cat) {
       if (cat == null) {
           System.err.println("Error: Category object is null.");
           return;
       }

       this.categ = cat;
       nameCat.setText(cat.getName());
       keyCat.setText(String.valueOf(cat.getKey_cat()));
   }

    @FXML
    void Edit(ActionEvent event) {

        String newTitle = nameCat.getText().trim();
        String keyCatText = keyCat.getText().trim();


        if (newTitle.isEmpty() || keyCatText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }


        int keycat;
        try {
            keycat = Integer.parseInt(keyCatText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de catégorie doit être un nombre entier.");
            return;
        }
        categ.setName(newTitle);
        categ.setKey_cat(keycat);
        catt.update(categ);
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Catégorie mise à jour avec succès.");
        try {
            menuc.navigateToFXML("/tn.jardindart/Ads/affCategory.fxml",buttonadd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
