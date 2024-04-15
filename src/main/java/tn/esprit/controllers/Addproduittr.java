package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Produittroc;

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
        p.setId_user_id(2);
        p.setNom(nomidt.getText());
        p.setDescription(dectro.getText());
        p.setNom_produit_recherche(prodrtr.getText());
        p.setCategory((String) catrgtro.getValue());
        if (selectedImageFile != null) {
            p.setImage(selectedImageFile.getPath());
        }
        p.setStatut(Integer.parseInt(statuspt.getText()));
        try {
            if (selectedImageFile != null) {
                produitTrocService.uploadImage(selectedImageFile);
            }
            produitTrocService.addMeth2(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit troc ajouté avec succès");
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
