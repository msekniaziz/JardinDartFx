package tn.jardindart.controllers.Prdtroc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Addproduittr {

    @FXML
    private TextField nomidt;

    @FXML
    private TextField prodrtr;

    @FXML
    private TextArea dectro;

    @FXML
    private ComboBox<String> catrgtro;

    @FXML
    private TextField statuspt;

    @FXML
    private ImageView imageid;

    @FXML
    private Button imagebuttp;

    private File selectedImageFile;

    @FXML
    void ajouter_prodtroc(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/addproduittr.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(root);

            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }}
    @FXML
    public void market(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Market.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(root);

            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    @FXML
    void affichierProduit(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Market.fxml"));
            Parent root = loader.load();
            Marketroc controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    @FXML
//    void ajouterProduit(javafx.event.ActionEvent event) {
//        Produit_TrocService produitTrocService = new Produit_TrocService();
//        Produittroc p = new Produittroc();
//        int id = SessionManager.getInstance().getUserFront();
//
//        SessionManager.getInstance().setUserFront(id);
//
//        // Validate 'nomidt' field
//        if (nomidt.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Le champ 'Nom' ne peut pas être vide.");
//            alert.showAndWait();
//            return; // Exit the method if input is invalid
//        }
//
//// Validate 'dectro' field
//        if (dectro.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Le champ 'Description' ne peut pas être vide.");
//            alert.showAndWait();
//            return; // Exit the method if input is invalid
//        }
//
//// Validate 'prodrtr' field
//        if (prodrtr.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Le champ 'Nom du produit recherché' ne peut pas être vide.");
//            alert.showAndWait();
//            return; // Exit the method if input is invalid
//        }
//
//// Validate 'catrgtro' field
//        if (catrgtro.getValue() == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Veuillez sélectionner une catégorie.");
//            alert.showAndWait();
//            return; // Exit the method if input is invalid
//        }
//
//// Validate 'statuspt' field
//        if (statuspt.getText().isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Le champ 'Statut' ne peut pas être vide.");
//            alert.showAndWait();
//            return; // Exit the method if input is invalid
//        }
//
//// Validate 'statuspt' field for integer value
//        try {
//            Integer.parseInt(statuspt.getText());
//        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Le statut doit être un nombre entier.");
//            alert.showAndWait();
//            return; // Exit the method if input is invalid
//        }
//
//        try {
//            p.setId_user_id(id);
//            p.setNom(nomidt.getText());
//            p.setDescription(dectro.getText());
//            p.setNom_produit_recherche(prodrtr.getText());
//            p.setCategory((String) catrgtro.getValue());
//            if (selectedImageFile != null) {
//                p.setImage(selectedImageFile.getPath());
//            }
//            p.setStatut(Integer.parseInt(statuspt.getText()));
//
//            if (selectedImageFile != null) {
//                produitTrocService.uploadImage(selectedImageFile);
//            }
//            produitTrocService.addMeth2(p);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setContentText("Produit troc ajouté avec succès");
//            alert.showAndWait();
//        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Le statut doit être un nombre entier.");
//            alert.showAndWait();
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Erreur lors de l'ajout du produit troc: " + e.getMessage());
//            alert.showAndWait();
//        }
//    }

    @FXML
    void ajouterProduit(javafx.event.ActionEvent event) {
        Produit_TrocService produitTrocService = new Produit_TrocService();
        Produittroc p = new Produittroc();
        int id = SessionManager.getInstance().getUserFront();

        SessionManager.getInstance().setUserFront(id);

        // Validate 'nomidt' field
        if (nomidt.getText().isEmpty()) {
            showAlert("Erreur", "Le champ 'Nom' ne peut pas être vide.");
            return; // Exit the method if input is invalid
        }

        // Validate 'dectro' field
        if (dectro.getText().isEmpty()) {
            showAlert("Erreur", "Le champ 'Description' ne peut pas être vide.");
            return; // Exit the method if input is invalid
        }

        // Validate 'prodrtr' field
        if (prodrtr.getText().isEmpty()) {
            showAlert("Erreur", "Le champ 'Nom du produit recherché' ne peut pas être vide.");
            return; // Exit the method if input is invalid
        }

        // Validate 'catrgtro' field
        if (catrgtro.getValue() == null) {
            showAlert("Erreur", "Veuillez sélectionner une catégorie.");
            return; // Exit the method if input is invalid
        }

        // Validate 'statuspt' field
        if (statuspt.getText().isEmpty()) {
            showAlert("Erreur", "Le champ 'Statut' ne peut pas être vide.");
            return; // Exit the method if input is invalid
        }

        // Validate 'statuspt' field for integer value
        try {
            Integer.parseInt(statuspt.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le statut doit être un nombre entier.");
            return; // Exit the method if input is invalid
        }

        try {
            p.setId_user_id(id);
            p.setNom(nomidt.getText());
            p.setDescription(dectro.getText());
            p.setNom_produit_recherche(prodrtr.getText());
            p.setCategory((String) catrgtro.getValue());

            // Set the image path
            if (selectedImageFile != null) {
                p.setImage(selectedImageFile.getAbsolutePath()); // Use getAbsolutePath() to get the full path
                produitTrocService.uploadImage(selectedImageFile);
            }

            p.setStatut(Integer.parseInt(statuspt.getText()));

            produitTrocService.addMeth2(p);
            showcorrect("Success", "Produit troc ajouté avec succès");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le statut doit être un nombre entier.");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout du produit troc: " + e.getMessage());
        }
    }

    private void showcorrect(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            Image image = new Image(selectedFile.toURI().toString());
            imageid.setImage(image);
        }
    }



}
