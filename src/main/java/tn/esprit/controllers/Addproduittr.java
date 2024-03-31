package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;
import tn.esprit.entities.Produittroc;
import tn.esprit.controllers.Produit_TrocService;
import java.io.File;
import java.sql.SQLException;

public class Addproduittr {

    @FXML
    private TextField nomidt;

    @FXML
    private TextField prodrtr;

    @FXML
    private TextArea dectro;

    @FXML
    private ComboBox<?> catrgtro;

    @FXML
    private TextField statuspt;

    @FXML
    private ImageView imageid;

    @FXML
    private Button imagebuttp;
    private File selectedImageFile;


    @FXML
    void affichierProduit(ActionEvent event) {

    }

    @FXML
    void ajouterProduit(ActionEvent event) throws SQLException {
Produit_TrocService produitTrocService = new Produit_TrocService();
        Produittroc p =new Produittroc();
        p.setNom(nomidt.getText());
        p.setDescription(dectro.getText());
        p.setCategory((String) catrgtro.getValue());
        if (selectedImageFile != null) {
            // If an image file is selected, set its path to the product
            p.setImage(selectedImageFile.getPath());
        }
        p.setStatut(Integer.parseInt(statuspt.getText()));
        try {
            produitTrocService.addMeth2(p);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("sucess");
            alert.setContentText("produit troc ajouter");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            System.out.println(e.getMessage());
            alert.showAndWait();
        }


    }

}
