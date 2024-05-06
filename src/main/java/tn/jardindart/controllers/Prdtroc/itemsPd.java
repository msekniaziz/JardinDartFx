package tn.jardindart.controllers.Prdtroc;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import  tn.jardindart.controllers.Prdtroc.*;
import javafx.event.ActionEvent;
import com.google.zxing.qrcode.QRCodeWriter;
import tn.jardindart.models.*;

import com.google.zxing.WriterException;
import tn.jardindart.controllers.Prdtroc.*;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;


import java.awt.image.BufferedImage;
import java.io.IOException;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
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

    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private HBox qr_code;
    private Produittroc book=new Produittroc();
    Produit_TrocService produitTrocService = new Produit_TrocService();
    Producttrocwith producttrocwith=new Producttrocwith();



    public void setData(Produittroc book) {
        this.book = book;

        book.setId(this.book.getId());

        System.out.println(book);
        itemname.setText(book.getNom());
        itemprice.setText(book.getCategory());
        descript.setText(book.getDescription());

        // Check if the image path is not null or empty
        if (book.getImage() != null && !book.getImage().isEmpty()) {
            // Use the file:/// protocol for local file paths
            String imageUrl = "file:///" + book.getImage();
            Image image = new Image(imageUrl);
            itemimg.setImage(image);
        } else {
            // Handle case where image path is empty or null
            // You can set a default image or do nothing
            // For example, setting a default image:
            // Image defaultImage = new Image("default_image.png");
            // itemimg.setImage(defaultImage);
        }
    }




    public void modif_click(ActionEvent event) throws SQLException, IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn.jardindart/editproduittr.fxml"));

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
        URL location = getClass().getResource("/tn.jardindart/Market.fxml");

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

        fxmlLoader.setLocation(getClass().getResource("/tn.jardindart/addProdwith.fxml"));

        // Load the addProdwith.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Access the controller after loading the fxml
        AddProdwith addProdController = fxmlLoader.getController();

        // Set the book in the AddProdwith controller

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

    public void qr_click(ActionEvent event) {
        // Construct the text to encode in the QR code
        String text =
                 "\nProduct Name: " + book.getNom()
                + "\nProduct Description: " + book.getDescription()
                + "\nProduct Category: " + book.getCategory()
                + "\nProduct looking for: " + book.getNom_produit_recherche();

        // Create a QRCodeWriter instance
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // Create a BitMatrix from the QR code text
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

            // Convert the BitMatrix to a BufferedImage
            BufferedImage bufferedImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                for (int y = 0; y < bitMatrix.getHeight(); y++) {
                    int grayValue = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                    bufferedImage.setRGB(x, y, grayValue);
                }
            }

            // Convert the BufferedImage to a JavaFX Image
            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

            // Display the QR code in the ImageView
            qrCodeImageView.setImage(fxImage);

            // Set the ImageView to visible
            qrCodeImageView.setVisible(true);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}