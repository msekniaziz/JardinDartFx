package tn.esprit.applicationgui.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import tn.esprit.applicationgui.entites.Blog;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AddBlog {
    @FXML
    private DatePicker dateblogField;

    @FXML
    private ComboBox<?> catrgtro;

    @FXML
    private TextArea dectro;

    @FXML
    private Button imagebuttp;

    @FXML
    private ImageView imageid;

    @FXML
    private TextField nomidt;

    @FXML
    private TextField statuspt;

    private File selectedImageFile;
    @FXML
    void AddBlog(ActionEvent event) {

    }

    @FXML
    void ShowBlog(ActionEvent event) {

    }







    public void AddBlog(javafx.event.ActionEvent actionEvent) {
            Blogservice blogService = new Blogservice();
            Blog blog = new Blog();

            // Récupérer les valeurs des champs de saisie
            String titre = nomidt.getText();
            String category = catrgtro.getValue().toString();
            String contenu = dectro.getText();

            // Assigner les valeurs au blog
            blog.setTitre(titre);
            blog.setCategory(category);
            blog.setContenu_blog(contenu);

            try {
                // Appeler la méthode addBlog du service
                blogService.addblog(blog);

                // Afficher une confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Blog ajouté avec succès");
                alert.showAndWait();

                // Effacer les champs de saisie après l'ajout
                nomidt.clear();
                catrgtro.getSelectionModel().clearSelection();
                dectro.clear();
            } catch (SQLException e) {
                // En cas d'erreur, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de l'ajout du blog: " + e.getMessage());
                alert.showAndWait();
            }
        }

    public void addImage_blog(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            Image image_blog = new Image(selectedFile.toURI().toString());
            imageid.setImage(image_blog);
        }
    }

}



