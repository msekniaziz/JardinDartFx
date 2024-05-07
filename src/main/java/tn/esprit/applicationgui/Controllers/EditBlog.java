package tn.esprit.applicationgui.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;



import javafx.scene.Node;

import javafx.scene.image.ImageView;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.applicationgui.entites.Blog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EditBlog implements Initializable {

    @FXML
    private ComboBox<String> catrgtro;

    @FXML
    private TextArea dectro;

    @FXML
    private ImageView imageid;

    @FXML
    private TextField nomidt;

    private Blog book;
    @FXML
    private TableView<Blog> tableView;

    private Blogservice blogService;

    public void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blogService = new Blogservice();
        blogService.setTableView(tableView);
        // Other initialization code...
    }

    private Image event;

    public void setBook(Blog book) {
        this.book = book;
        // Set existing book information to the labels and text fields
        nomidt.setText(book.getTitre());
        dectro.setText(book.getContenu_blog());
        catrgtro.setValue(book.getCategory());



    }
    @FXML
    void okbtn_clicked(ActionEvent event) {
        Blogservice serviceBook1 = new Blogservice();
        try {
            // Mettre à jour les propriétés de l'objet book avec les valeurs actuelles des champs de texte et des ComboBox
            book.setTitre(nomidt.getText());
            book.setCategory(catrgtro.getValue());
            book.setContenu_blog(dectro.getText());

            // Utiliser l'objet book existant pour effectuer la modification
            serviceBook1.EditBlog(book);
            System.out.println("Le produit troc a été modifié avec succès.");

            // Refresh the data displayed in the TableView in blogmain.fxml
            reloadPage(event);
            // Close the window
            closeWindow(event);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du produit : " + e.getMessage());
            e.printStackTrace();
        }
    }

    void reloadPage(ActionEvent event) {
        // Get the URL of the FXML file
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicationgui/blogmain.fxml"));
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

    void closeWindow(ActionEvent event) {
        // Close the window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editImage_blog(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // If the user selected a file, load and display the image
        if (selectedFile != null) {
            String imagePath = ((File) selectedFile).toURI().toString();
            Image image = new Image(imagePath);
            imageid.setImage(image);

            // Update the Produittroc object with the path to the selected image file
            book.setImage_blog(imagePath);
        }}

}
















