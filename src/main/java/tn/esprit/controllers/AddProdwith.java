package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import org.controlsfx.control.Notifications;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import  tn.esprit.entities.Producttrocwith;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.util.Duration;
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

   Producttrocwith producttrocwith;
   private itemsPd p ;

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
        // Load the image
//        Image image = new Image(book.getImage()); // Assuming book.getImage() returns the path to the image file

        // Set the image to the ImageView
//        imgb.setImage(image);
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
            showNotificationWithButtons("Success", "Product added successfully.", "View Product", "Close");
            System.out.println(book);
            reloadPage(event);
        } catch (SQLException e) {
            showAlert("Error", "Failed to add product: " + e.getMessage());
            System.out.println(e.getMessage());
            System.out.println(book);
        }
    }

    private void showNotificationWithButtons(String title, String message, String button1Text, String button2Text) {
        Button button1 = new Button(button1Text);
        button1.getStyleClass().add("notification-button");
        button1.setOnAction(event -> {
            // Handle button 1 action
            // For example, open the product details page
        });

        Button button2 = new Button(button2Text);
        button2.getStyleClass().add("notification-button");
        button2.setOnAction(event -> {
            // Handle button 2 action
            // For example, close the notification
        });

        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonsContainer.getChildren().addAll(button1, button2);

        Notifications.create()
                .title(title)
                .text(message)
                .position(Pos.TOP_RIGHT)
                .graphic(buttonsContainer)
                //.hideAfter(Duration.seconds(5))
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

//    @FXML
//    void AddBookBtn(ActionEvent event) {
//
//
//        String path;
//        imgpathstring.setText(imagePath1.toString());
//
//        path=imgpathstring .getText();
//        Produit_troc_with_Service serviceBook = new Produit_troc_with_Service();
//
//        Producttrocwith book = new Producttrocwith();
//        String prodtitle = TitleL.getText().trim();
//        String proddesc = description.getText().trim();
//
//
//        if (!prodtitle.isEmpty() && prodtitle.matches(".*[a-zA-Z].*")) {
//            book.setNom(prodtitle);
//                  book.setDescription(proddesc);
//
//
//            String selectedCategoryString = (String) CategoryCombo.getValue();
//
//            // Check if a category is selected
//            if (selectedCategoryString != null) {
//                // Convert the String to Categorie enum
//                try {
//                    Producttrocwith.Categorie selectedCategory = Producttrocwith.Categorie.valueOf(selectedCategoryString);
//                    book.setCategory(String.valueOf(selectedCategory));
//
//                    // Check if an availability is selected
//                        // Convert the String to Disponibilite enum
//
//
//
//                            try {
//                                Producttrocwith book2 =new Producttrocwith(TitleL.getText(),selectedCategory,,);
//                                    public Producttrocwith(int id, int prod_id_troc_id, String nom, String category, String description, String image) {
//
//                                    serviceBook.addMeth2(book2);
//
//                                // Refresh the list of books after successful addition
//
//                            } catch (SQLException e) {
//                                showAlert("Error", e.getMessage());
//                            }
//
//                    } else {
//                        // Show an alert if no availability is selected
//                        showAlert("Error", "Please select an availability");
//                    }
//                } catch (IllegalArgumentException e) {
//                    // Show an alert if the category is not a valid enum constant
//                    showAlert("Error", "Invalid category: " + selectedCategoryString);
//                }
//            } else {
//                // Show an alert if no category is selected
//                showAlert("Error", "Please select a category");
//            }
//        } else {
//            // Show an alert if the book title is empty or doesn't contain letters
//            showAlert("Error", "The book title cannot be empty and must contain letters.");
//        }
//    }
//
//    @FXML
//    void Select_Category(ActionEvent event) {
//        String s1 = CategoryCombo.getSelectionModel().getSelectedItem().toString();
//        CategorieLabel.setText(s1);
//
//    }
//
//
//    @FXML
//    void importImage(ActionEvent event) {
//
//    }


