package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entities.Produittroc;
import tn.esprit.controllers.Marketroc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class itemsPd {
    @FXML
    private Parent root;

    @FXML
    private Button delete;

    @FXML
    private Label descript;

    @FXML
    private ImageView itemimg;

    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;

    @FXML
    private Button modif;

    private Produittroc book=new Produittroc();
    Produit_TrocService produitTrocService = new Produit_TrocService();

    public void setData(Produittroc book){
        this.book=book;
      book.setId(this.book.getId());
        System.out.println(book);
        itemname.setText(book.getNom());
        itemprice.setText(book.getCategory());
        descript.setText(book.getDescription());

        Image image = new Image(book.getImage());
        itemimg.setImage(image);
    }

//    public void delete_clicked(ActionEvent event) throws SQLException {
//        System.out.println("cliiick");
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("Are you sure you want to delete this product?");
//
//        // Show the confirmation dialog and wait for the user's response
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                // Call the service method to delete the product from the database
//                produitTrocService.deletePT1(book.getId());
//
//
//
//                // Notify the user that the deletion was successful
//                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                successAlert.setTitle("Deletion Successful");
//                successAlert.setHeaderText(null);
//                successAlert.setContentText("Product deleted successfully.");
//                successAlert.showAndWait();
//
//            }
//        });
//    }


    public void delete_clicked(ActionEvent event) throws SQLException {
        System.out.println("cliiick");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the service method to delete the product from the database
                produitTrocService.deletePT1(book.getId());

                // Notify the user that the deletion was successful
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deletion Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Product deleted successfully.");
                successAlert.showAndWait();

                // Reload the page
                reloadPage();
            }
        });
    }

    private void reloadPage() {
        // Get the URL of the FXML file
        URL location = getClass().getResource("/tn/esprit/jardindart/Market.fxml");

        try {
            // Load the FXML file and initialize the controller
            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();

            // Get the current scene from any node within the scene hierarchy
            Scene scene = delete.getScene();

            // Replace the root of the current scene with the newly loaded root
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }


    private void showPopup(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Collection Update");
        alert.setHeaderText(null);
        alert.setContentText(message);



        alert.showAndWait();
    }
}
