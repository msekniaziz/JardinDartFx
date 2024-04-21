package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Produittroc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Addproduittr {

    @FXML
    private TextField nomidt;

    @FXML
    private TextField prodrtr;

    @FXML
    private TextArea dectro;

    @FXML
    private ComboBox<?> catrgtro;

    @FXML
    private TextField statuspt;

    @FXML
    private ImageView imageid;

    @FXML
    private Button imagebuttp;

    private File selectedImageFile;

    @FXML
    void affichierProduit(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Market.fxml"));
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



    @FXML
    void ajouterProduit(javafx.event.ActionEvent event) {
        Produit_TrocService produitTrocService = new Produit_TrocService();
        Produittroc p = new Produittroc();

        // Validate 'nomidt' field
        if (nomidt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ 'Nom' ne peut pas être vide.");
            alert.showAndWait();
            return; // Exit the method if input is invalid
        }

// Validate 'dectro' field
        if (dectro.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ 'Description' ne peut pas être vide.");
            alert.showAndWait();
            return; // Exit the method if input is invalid
        }

// Validate 'prodrtr' field
        if (prodrtr.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ 'Nom du produit recherché' ne peut pas être vide.");
            alert.showAndWait();
            return; // Exit the method if input is invalid
        }

// Validate 'catrgtro' field
        if (catrgtro.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une catégorie.");
            alert.showAndWait();
            return; // Exit the method if input is invalid
        }

// Validate 'statuspt' field
        if (statuspt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ 'Statut' ne peut pas être vide.");
            alert.showAndWait();
            return; // Exit the method if input is invalid
        }

// Validate 'statuspt' field for integer value
        try {
            Integer.parseInt(statuspt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le statut doit être un nombre entier.");
            alert.showAndWait();
            return; // Exit the method if input is invalid
        }

        try {
            p.setId_user_id(2);
            p.setNom(nomidt.getText());
            p.setDescription(dectro.getText());
            p.setNom_produit_recherche(prodrtr.getText());
            p.setCategory((String) catrgtro.getValue());
            if (selectedImageFile != null) {
                p.setImage(selectedImageFile.getPath());
            }
            p.setStatut(Integer.parseInt(statuspt.getText()));

            if (selectedImageFile != null) {
                produitTrocService.uploadImage(selectedImageFile);
            }
            produitTrocService.addMeth2(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit troc ajouté avec succès");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le statut doit être un nombre entier.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de l'ajout du produit troc: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void addImage(javafx.event.ActionEvent event) {
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
