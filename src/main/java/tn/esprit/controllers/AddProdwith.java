package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.controlsfx.control.Notifications;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import  tn.esprit.entities.Producttrocwith;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddProdwith implements Initializable {

    @FXML
    private Label AvaibilityLabel;

    @FXML
    private Label CategorieLabel;

    @FXML
    private ComboBox<String> CategoryCombo;

    @FXML
    private TextField TitleL;

    @FXML
    private AnchorPane addanchor;

    @FXML
    private Button addbook;

    @FXML
    private TextField description;
    private String imagePath1;

    @FXML
    private ImageView imgb;
    @FXML
    private TextField prod_id_id;
    @FXML
    private Button imgbb;

    @FXML
    private TextField imgpathstring;
    private  Produit_troc_with_Service produitTrocWithService = new Produit_troc_with_Service();

   Producttrocwith producttrocwith=new Producttrocwith();
    int z=this.producttrocwith.getId();

    public AnchorPane getAddAnchor() {
        return addanchor;
    }

    private int mainProductId;

    public void setMainProductId(int mainProductId) {
        this.mainProductId = mainProductId;
    }

public Producttrocwith setprod(Producttrocwith book,int a) {
        this.producttrocwith = book;
        // Set existing book information to the labels and text fields
        book.setId(producttrocwith.getId());
prod_id_id.setText(String.valueOf(a));
        TitleL.setText(book.getNom());
        description.setText(book.getDescription());
        CategoryCombo.setValue(book.getCategory());
return book;

    }


    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listData = FXCollections.observableArrayList("house", "garden");
        CategoryCombo.setItems(listData);
    }

    void reloadPage(ActionEvent event) {
        // Get the URL of the FXML file
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Market.fxml"));
            Parent newContent = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Obtenir la scène actuelle
            Scene currentScene = stage.getScene();

            // Remplacer le contenu de la racine de la scène actuelle avec le nouveau contenu chargé depuis le fichier FXML
            currentScene.setRoot(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void importImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // If a file is selected, set the image
        if (selectedFile != null) {
            imagePath1 = selectedFile.toURI().toString();
            imgpathstring.setText(imagePath1); // Update the image path text field

            // Load and set the image
            Image image = new Image(imagePath1);
            imgb.setImage(image);
        }
    }

    Itemstroc i =new Itemstroc();

    @FXML
void AddBookBtn(ActionEvent event) {
        // Validate input fields before adding the product
        String prodtitle = TitleL.getText().trim();
        String proddesc = description.getText().trim();
        String selectedCategoryString = CategoryCombo.getValue();
        String id_prod = prod_id_id.getText();

        if (prodtitle.isEmpty() || !prodtitle.matches(".*[a-zA-Z].*")) {
            showAlert("Error", "The book title cannot be empty and must contain letters.");
            return;
        }

        if (selectedCategoryString == null) {
            showAlert("Error", "Please select a category.");
            return;
        }

        // Convert the selected category string to the enum value
        Producttrocwith.Categorie selectedCategory = Producttrocwith.Categorie.valueOf(selectedCategoryString);

        // Check if an image is selected
        if (imagePath1 == null || imagePath1.isEmpty()) {
            showAlert("Error", "Please select an image.");
            return;
        }

        // Create a new instance of Producttrocwith using the setprod method
        Producttrocwith book = new Producttrocwith(Integer.parseInt(id_prod), prodtitle, selectedCategory, proddesc, imagePath1);

        try {
            produitTrocWithService.addMeth2(book);
            // Pass the product ID to the notification method
            showNotificationWithButtons("Success", "Product added successfully.", "View Product", "Close", book.getId());
            reloadPage(event);
        } catch (SQLException e) {
            showAlert("Error", "Failed to add product: " + e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(book);
        }
    }


    void showNotificationWithButtons(String title, String message, String button1Text, String button2Text, int productId) {
        // Create buttons with appropriate actions
        Button button1 = new Button("No");
        button1.getStyleClass().add("notification-button");
        button1.setOnAction(event -> {
            // Delete the product with the given ID
            produitTrocWithService.deletePT1(productId);
        });

        Button button2 = new Button("Yes");
        button2.getStyleClass().add("notification-button");
        button2.setOnAction(event -> {
            // Handle button 2 action
        });

        // Create HBox to hold buttons
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.getChildren().addAll(button1, button2);

        // Show notification
        Notifications.create()
                .title(title)
                .text(message)
                .position(Pos.TOP_RIGHT)
                .graphic(buttonsContainer)
                .show();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


private void showconf(String title, String message) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}}
