package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Produittroc;

import java.sql.SQLException;

public class itemsPd {
    @FXML
    private Parent root;
    @FXML
    private ImageView itemimg;
    @FXML
    private Label itemname;
@FXML
private Label descript;
    @FXML
    private Label itemprice;

    private Produittroc book;
    public void setData(Produittroc book){
        this.book=book;
        System.out.println(book);
        itemname.setText(book.getNom());
        itemprice.setText(book.getCategory());
        descript.setText(book.getDescription());

        Image image = new Image(book.getImage());
        itemimg.setImage(image);
    }

    public void  delete_clicked(ActionEvent event) throws SQLException {
        System.out.println("cliiick");
        Produit_TrocService produitTrocService = new Produit_TrocService();

        produitTrocService.deletePT(this.book);



    }

    private void showPopup(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Collection Update");
        alert.setHeaderText(null);
        alert.setContentText(message);



        alert.showAndWait();
    }
}
