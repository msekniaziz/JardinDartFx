package tn.esprit.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Produittroc;
import tn.esprit.controllers.Produit_TrocService;
import tn.esprit.controllers.Marketroc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Editproduittr implements Initializable {

    @FXML
    private Label AvaibilityLabel;

    @FXML
    private Label CategorieLabel;

    @FXML
    private ComboBox<String> CategoryCombo;

    @FXML
    private TextField PriceL;

    @FXML
    private TextField TitleL;

    @FXML
    private TextField TitleL1;

    @FXML
    private TextField imgpathstring1;
    @FXML
    private ImageView imgb1;

    @FXML
    private Button imgbbu;
    @FXML
    private Button okButton;

    private Produittroc book;

    public void setBook(Produittroc book) {
        this.book = book;
        // Set existing book information to the labels and text fields
        TitleL1.setText(book.getNom());
        TitleL.setText(book.getNom_produit_recherche());
        PriceL.setText(book.getDescription());
        CategoryCombo.setValue(book.getCategory());


        // Load the image
        Image image = new Image(book.getImage()); // Assuming book.getImage() returns the path to the image file

        // Set the image to the ImageView
        imgb1.setImage(image);    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listData = FXCollections.observableArrayList("House", "Garden");
        CategoryCombo.setItems(listData);
    }


    @FXML
    public void okbtn_clicked(ActionEvent event) {
        Produit_TrocService serviceBook1 = new Produit_TrocService();
        try {
            // Mettre à jour les propriétés de l'objet book avec les valeurs actuelles des champs de texte et des ComboBox
            book.setNom(TitleL1.getText());
            book.setNom_produit_recherche(TitleL.getText());
            book.setCategory(CategoryCombo.getValue());
            book.setDescription(PriceL.getText());

            // Utiliser l'objet book existant pour effectuer la modification
            serviceBook1.modifierPT(book);
            System.out.println("Le produit troc a été modifié avec succès.");

//            reloadMarketFXML();

            // Close the window
            closeWindow(event);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du produit : " + e.getMessage());
            e.printStackTrace();
        }
    }

    void closeWindow(ActionEvent event) {
        // Close the window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


//    void reloadMarketFXML() {
//        try {
//            // Load the FXML file and initialize the controller
////            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Market.fxml"));
////            Parent root = loader.load();
//
//            // Get the current scene from any node within the scene hierarchy
//            Scene scene = okButton.getScene();
//
//            // Replace the root of the current scene with the newly loaded root
////            scene.setRoot(root);
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle or log the exception
//        }
//    }


    void closeWindow() {
        // Close the window
        okButton.getScene().getWindow().hide();
    }


    @FXML
    public void Select_Category() {
        String s1 = CategoryCombo.getSelectionModel().getSelectedItem();
        CategorieLabel.setText(s1);
    }

    @FXML
    public void importImageu() {
        // Open a file chooser dialog to allow the user to select an image file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // If the user selected a file, load and display the image
        if (selectedFile != null) {
            String imagePath = ((File) selectedFile).toURI().toString();
            Image image = new Image(imagePath);
            imgb1.setImage(image);

            // Update the Produittroc object with the path to the selected image file
            book.setImage(imagePath);
        }}
}
