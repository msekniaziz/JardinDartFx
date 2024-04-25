package tn.esprit.applicationgui.Controllers;

import javafx.event.ActionEvent;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.event.ActionEvent;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.entites.ReponseBlog;
import tn.esprit.applicationgui.Controllers.Blogmain;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Cardblog {
    @FXML
    private Label catrgtro;

    @FXML
    private Label dectro;

    @FXML
    private Button delete;

    @FXML
    private Button exchange;

    @FXML
    private ImageView itemimg;

    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;

    @FXML
    private Button modif;

    @FXML
    private Label nomidt;

    @FXML
    private HBox qr_code;
    private Blog book=new Blog();
    private ReponseBlog reponseBlog=new ReponseBlog();
    Blogservice blogservice = new Blogservice();
    ReponseBlogservice reponseBlogservice=new ReponseBlogservice();

    @FXML
    void modifie_cli(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn/esprit/applicationgui/EditBlog.fxml"));

        // Load the payment.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Access the controller after loading the fxml
        EditBlog updateController = fxmlLoader.getController();

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



    public int setid()
    {int id;
        return id=this.book.getId();
    }
    public void setData(Blog book) {
        this.book = book;

        book.setId(this.book.getId());

        System.out.println(book);
        itemname.setText(book.getTitre());
        itemprice.setText(book.getCategory());
        dectro.setText(book.getContenu_blog());

        // Load the image
        try {
         //   Image image = new Image(book.getImage_blog());
           // itemimg.setImage(image);
        } catch (Exception e) {
            // Handle the case where the image resource is not found
            System.out.println("Error loading image: " + e.getMessage());
            // Set a default image or display an error message
        }
    }




@FXML
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
                try {
                    blogservice.deleteblog(book.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

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
        URL location = getClass().getResource("/tn/esprit/applicationgui/blogmain.fxml");

        try {
            // Load the FXML file and initialize the controller
            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();

            // Get the current scene from any node within the scene hierarchy
            Scene scene = delete.getScene();

            // Replace the root of the current scene with the newly loaded root
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();  // Handle or log the exception
        }

    }

    // Method to get any node in the scene hierarchy (replace getNode() with an appropriate method)
    private Node getNode() {
        // Provide implementation to get any node in the scene hierarchy

        return null;
    }


//    private void showPopup(String message, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle("Collection Update");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//
//
//
//        alert.showAndWait();
//    }

    @FXML
    public void AddRepBlog(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn/esprit/applicationgui/AddRepBlog.fxml"));

        // Load the addrep.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int z;
        z=setid();

        // Access the controller after loading the fxml
        AddRepBlog addRepBlog = fxmlLoader.getController();

        // Set the book in the AddProdwith controller
        int a;
        a=this.book.getId();
        addRepBlog.setBlog(reponseBlog,this.book.getId());

        // Create a new scene
        Scene addProdScene = new Scene(anchorPane);

        // Get the stage from the ActionEvent
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new stage for the addProdwith.fxml scene
        Stage addProdStage = new Stage();
        addProdStage.setTitle("reponse blog");
        addProdStage.setScene(addProdScene);

        // Set the owner stage to the current stage
        addProdStage.initOwner(currentStage);

        // Show the addProdwith.fxml scene without closing the current scene
        addProdStage.show();
    }

}