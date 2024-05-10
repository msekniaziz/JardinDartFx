package tn.jardindart.controllers.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.jardindart.models.Blog;
import tn.jardindart.models.ReponseBlog;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class EditRep {

    @FXML
    private DatePicker daterepField;

    @FXML
    private TextArea dectro;

    @FXML
    private TextField statuspt;



    private ReponseBlog book;

    private void showAlert(String title, String header, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    public void setBook(ReponseBlog book) {
        this.book = book;
        // Set existing book information to the labels and text fields
        dectro.setText(book.getContenu());
     //   daterepField.se(book.getDate());
        }

    @FXML
    void modifie_cli(ActionEvent event) {
        if (book != null) {
            ReponseBlogservice serviceBook1 = new ReponseBlogservice();
            try {
                // Contrôle de saisie pour le champ de contenu
                String contenu = dectro.getText();
                if (contenu.isEmpty()) {
                    showAlert("Erreur", "Champ de contenu vide", "Veuillez saisir du contenu.");
                    return; // Arrêtez la méthode si le champ de contenu est vide
                }

                if (contenu.length() < 10) {
                    showAlert("Erreur", "Contenu trop court", "Le contenu doit avoir au moins 10 caractères.");
                    return; // Arrêtez la méthode si le contenu est trop court
                }

                // Mettre à jour les propriétés de l'objet book avec les valeurs actuelles des champs de texte et des ComboBox
                book.setContenu(contenu);

                // Utiliser l'objet book existant pour effectuer la modification
                serviceBook1.editrep(book);
                System.out.println("Le blog a été modifié avec succès.");

                // Close the window
                closeWindow(event);

            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification du blog : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Objet ReponseBlog null", "L'objet ReponseBlog est null. Assurez-vous qu'il est correctement initialisé.");
        }
    }



    void closeWindow(ActionEvent event) {
            // Close the window
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }



    }





