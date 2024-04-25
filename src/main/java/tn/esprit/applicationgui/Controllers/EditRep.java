package tn.esprit.applicationgui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.entites.ReponseBlog;
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


    public void setBook(ReponseBlog book) {
        this.book = book;
        // Set existing book information to the labels and text fields
        dectro.setText(book.getContenu());
      //  daterepField.setValue(book.getDate());
        }

        @FXML
        void EditRepBlog(ActionEvent event) {
            ReponseBlogservice serviceBook1 = new ReponseBlogservice();
            try {
                // Mettre à jour les propriétés de l'objet book avec les valeurs actuelles des champs de texte et des ComboBox
                book.setContenu(dectro.getText());


                // Utiliser l'objet book existant pour effectuer la modification
                serviceBook1.editrep(book);
                System.out.println("Le produit troc a été modifié avec succès.");

//            reloadMarketFXML();

                // Close the window
                closeWindow(event);

            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification du produit : " + e.getMessage());
                e.printStackTrace();
            }

        }


        void closeWindow(ActionEvent event) {
            // Close the window
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }



    }





