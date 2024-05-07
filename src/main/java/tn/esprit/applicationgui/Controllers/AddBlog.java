package tn.esprit.applicationgui.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.applicationgui.entites.Blog;


import java.awt.event.ActionEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AddBlog {


    String filepath = null, filename = null, fn = null;
    FileChooser fc = new FileChooser();
    String uploads = "C:/Users/ROUMAYSA/IdeaProjects/Blog/src/main/resources/Images/";

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



    private void clearFields() {
        nomidt.clear();
        catrgtro.getSelectionModel().clearSelection();
        dectro.clear();
    }

    public void AddBlog(javafx.event.ActionEvent actionEvent) {
        Blogservice blogService = new Blogservice();
        Blog blog = new Blog();

        // Récupérer les valeurs des champs de saisie
        String titre = nomidt.getText().trim();
        String category = catrgtro.getValue() != null ? catrgtro.getValue().toString().trim() : "";
        String Contenu_blog = dectro.getText().trim();

//        if (containsBadWords(Contenu_blog)) {
//            showAlert("Erreur", "Le contenu contient des mots irrespectueux.");
//            return; // Arrêter l'ajout si des mots irrespectueux sont détectés
//        }

        // Vérifier si le titre contient moins de 8 caractères
        if (titre.length() < 8) {
            showAlert("Erreur", "Le titre doit contenir au moins 8 caractères.");
            return;
        }


        // Vérifier si la catégorie est sélectionnée
        if (category.isEmpty()) {
            showAlert("Erreur", "Veuillez choisir une catégorie.");
            return;
        }

        // Vérifier si tous les champs requis sont remplis
        if (titre.isEmpty() || category.isEmpty() || Contenu_blog.isEmpty()) {
            // Afficher une alerte si un champ est vide
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Assigner les valeurs au blog
        blog.setTitre(titre);
        blog.setCategory(category);
        blog.setContenu_blog(Contenu_blog);
        blog.setImage_blog(filepath);

        try {
            // Appeler la méthode addBlog du service
            blogService.addblog(blog);
            reloadPage(actionEvent);
            // Afficher une confirmation
            showsuccess("Success", "Blog ajouté avec succès");

            // Effacer les champs de saisie après l'ajout
            clearFields();
        } catch (SQLException e) {
            // En cas d'erreur, afficher un message d'erreur
            showAlert("Erreur", "Erreur lors de l'ajout du blog: " + e.getMessage());
        }

    }

    private void showsuccess(String title, String Contenu_blog) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Contenu_blog);
        alert.showAndWait();
    }

    private void showAlert(String title, String Contenu_blog) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(Contenu_blog);
        alert.showAndWait();
    }



    void reloadPage(javafx.event.ActionEvent event) {
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


    @FXML
    void addImage_blog(javafx.event.ActionEvent actionEvent) {
        File file = fc.showOpenDialog(null);
        // Shows a new file open dialog.
        if (file != null) {
            try {
                // Copier l'image sélectionnée vers le dossier de destination
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(new File(uploads + file.getName()));

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                // Mettre à jour le chemin de l'image
                filepath = uploads + file.getName();
                // Afficher l'image dans l'ImageView
                imageid.setImage(new Image(new FileInputStream(file)));

                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Fichier invalide!");
        }
//public void addImage_blog(javafx.event.ActionEvent actionEvent) {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Select Image File");
//    fileChooser.getExtensionFilters().addAll(
//            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
//            new FileChooser.ExtensionFilter("All Files", "*.*"));
//    File selectedFile = fileChooser.showOpenDialog(null);
//    if (selectedFile != null) {
//        selectedImageFile = selectedFile;
//        Image image_blog = new Image(selectedFile.toURI().toString());
//        imageid.setImage(image_blog);
//    }

//
//        private boolean containsBadWords (String text){
//            try {
//                // Données à envoyer
//                String data = "text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
//
//                // URL de l'API
//                String apiUrl = "https://profanity-toxicity-detection-for-user-generated-content.p.rapidapi.com/";
//
//                // Ouvrir la connexion
//                URL url = new URL(apiUrl);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                connection.setRequestProperty("X-RapidAPI-Key", "7f8f4854fcmsh18a104a62e08b06p15fbcfjsn6a9cf9faa202");
//                connection.setRequestProperty("X-RapidAPI-Host", "profanity-toxicity-detection-for-user-generated-content.p.rapidapi.com");
//                connection.setDoOutput(true);
//
//                // Envoyer les données
//                OutputStream outputStream = connection.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                writer.write(data);
//                writer.flush();
//                writer.close();
//                outputStream.close();
//
//                // Lire la réponse
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // Afficher la réponse (pour le débogage)
//                System.out.println(response.toString());
//
//                // Vérifier si la réponse contient des mots inappropriés
//                if (response.toString().contains("bad_words")) {
//                    showAlert("Erreur", "Le contenu contient des mots irrespectueux.");
//                    return true;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return false;
//        }


//    private void showAlert(String String title;
//        title, String message, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private void showsuccess(String title, String message) {
//        showAlert(title, message, Alert.AlertType.CONFIRMATION);
//    }

    }
}




//    private void showAlert(String title, String message, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }







