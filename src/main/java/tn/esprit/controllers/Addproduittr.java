package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Produittroc;
import tn.esprit.controllers.Produit_TrocService;
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
    void affichierProduit(ActionEvent event) {
        try {
            // Load the FXML file for the showproduitr.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/showproduitr.fxml"));
            Parent root = loader.load();

            // Get the controller of the loaded FXML file
            Showproduitr controller = loader.getController();

            // Optionally, you can pass data to the controller if needed
            // For example:
            // controller.setData(data);

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    @FXML
    void ajouterProduit(ActionEvent event) throws SQLException {
        Produit_TrocService produitTrocService = new Produit_TrocService();
        Produittroc p = new Produittroc();
        p.setId_user_id(2);
        p.setNom(nomidt.getText());
        p.setDescription(dectro.getText());
        p.setNom_produit_recherche(prodrtr.getText());
        p.setCategory((String) catrgtro.getValue());
        if (selectedImageFile != null) {
            // If an image file is selected, set its path to the product
            p.setImage(selectedImageFile.getPath());
        }
        p.setStatut(Integer.parseInt(statuspt.getText()));
        try {
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
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            // Load and display the selected image
            Image image = new Image(selectedFile.toURI().toString());
            imageid.setImage(image);
        }
    }


}
