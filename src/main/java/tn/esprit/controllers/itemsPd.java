package tn.esprit.controllers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entities.Producttrocwith;
import tn.esprit.entities.Produittroc;
import tn.esprit.controllers.Marketroc;
import tn.esprit.controllers.*;

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
    Producttrocwith producttrocwith=new Producttrocwith();



    public int seti()
    {int id;
        return id=this.book.getId();
    }
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

    public void modif_click(ActionEvent event) throws SQLException, IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn/esprit/jardindart/editproduittr.fxml"));

        // Load the payment.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Access the controller after loading the fxml
        Editproduittr updateController = fxmlLoader.getController();

        // Set the panier in the PaymentController
        updateController.setBook(book);

        // Create a new scene
        Scene updateScene = new Scene(anchorPane);

        // Get the stage from the ActionEvent
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new stage for the payment.fxml scene
        Stage paymentStage = new Stage();
        paymentStage.setTitle("Update");
        paymentStage.setScene(updateScene);

        // Set the owner stage to the current stage (collectionitem.fxml)
        paymentStage.initOwner(currentStage);

        // Show the payment.fxml scene without closing the collectionitem.fxml scene
        paymentStage.show();
    }



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

    void reloadPage() {
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

    // Method to get any node in the scene hierarchy (replace getNode() with an appropriate method)
    private Node getNode() {
        // Provide implementation to get any node in the scene hierarchy

        return null;
    }


    private void showPopup(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Collection Update");
        alert.setHeaderText(null);
        alert.setContentText(message);



        alert.showAndWait();
    }

    @FXML
    public void exchange_click(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn/esprit/jardindart/addProdwith.fxml"));

        // Load the addProdwith.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int z;
        z=seti();

        // Access the controller after loading the fxml
        AddProdwith addProdController = fxmlLoader.getController();

        // Set the book in the AddProdwith controller
        int a;
        a=this.book.getId();
       addProdController.setprod(producttrocwith,this.book.getId());

        // Create a new scene
        Scene addProdScene = new Scene(anchorPane);

        // Get the stage from the ActionEvent
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new stage for the addProdwith.fxml scene
        Stage addProdStage = new Stage();
        addProdStage.setTitle("produit with");
        addProdStage.setScene(addProdScene);

        // Set the owner stage to the current stage
        addProdStage.initOwner(currentStage);

        // Show the addProdwith.fxml scene without closing the current scene
        addProdStage.show();
    }

}