package tn.esprit.controllers;

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
import tn.esprit.entities.Produittroc;
import tn.esprit.controllers.Produit_TrocService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class Editproduittr {

    @FXML
    private TextField nomidt;

    @FXML
    private TextField prodrtr;

    @FXML
    private TextArea dectro;

    @FXML
    private ComboBox<String> catrgtro; // Assuming the ComboBox holds String items

    @FXML
    private TextField statuspt;

    @FXML
    private ImageView imageid;

    @FXML
    private Button imagebuttp;

    private File selectedImageFile;

    private Produittroc productToEdit; // Store the product to be edited
    @FXML
    void affichierProduit(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/showproduitr.fxml"));
            Parent root = loader.load();
            Showproduitr controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initData(Produittroc product) {
        productToEdit = product;
        // Populate the fields with the details of the product to be edited
        nomidt.setText(product.getNom());
        prodrtr.setText(product.getNom_produit_recherche());
        dectro.setText(product.getDescription());
        catrgtro.setValue(product.getCategory());
        statuspt.setText(String.valueOf(product.getStatut()));
        // Load and display the product image, if available
        if (product.getImage() != null) {
            imageid.setImage(new Image(new File(product.getImage()).toURI().toString()));
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

    @FXML
    void editproduct(ActionEvent event) {
        // Update the details of the product with the edited values
        productToEdit.setNom(nomidt.getText());
        productToEdit.setNom_produit_recherche(prodrtr.getText());
        productToEdit.setDescription(dectro.getText());
        productToEdit.setCategory(catrgtro.getValue());
        productToEdit.setStatut(Integer.parseInt(statuspt.getText()));
        // Perform database update operation to save the changes
        Produit_TrocService produitTrocService = new Produit_TrocService();
        try {
            produitTrocService.modifierPT(productToEdit);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit troc modifié avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la modification du produit troc: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
