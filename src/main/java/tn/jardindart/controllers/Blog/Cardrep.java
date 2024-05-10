package tn.jardindart.controllers.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.jardindart.models.Blog;
import tn.jardindart.models.ReponseBlog;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

import java.io.File;
import java.io.IOException;

public class Cardrep {
    @FXML
    private Label catrgtro;

    @FXML
    private Label contetid;

    @FXML
    private Label dectro;

    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;


    @FXML
    private Button modifie_cli;

    @FXML
    private Button modifier1;

    @FXML
    private Label nomidt;
    private Blog blog = new Blog();
    private ReponseBlog book = new ReponseBlog();
    ReponseBlogservice reponseBlogservice = new ReponseBlogservice();

    @FXML
    void delete_clicked(ActionEvent event) {

    }

    @FXML
    void modifie_cli(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn.jardindart/Blog/EditRep.fxml"));

        // Load the payment.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
            reloadPage(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Access the controller after loading the fxml
        EditRep updateController = fxmlLoader.getController();

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

    void reloadPage(javafx.event.ActionEvent event) {
        // Get the URL of the FXML file
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/EditRep.fxml"));
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
    public void setData(ReponseBlog book) {
        this.book = book;

        book.setId(this.book.getId());
        book.setBlog_id(this.blog.getId());
        System.out.println("le id"+blog.getId());
        contetid.setText(book.getContenu());
        itemname.setText(String.valueOf(book.getDate()));

        if (book.getBlog() != null) {
            itemprice.setText(book.getBlog().getTitre());
        } else {
            itemprice.setText("Titre inconnu");
        }
    }
        // Load the image
}



