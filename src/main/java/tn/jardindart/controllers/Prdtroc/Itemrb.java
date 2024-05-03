package tn.jardindart.controllers.Prdtroc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.jardindart.models.*;

import java.sql.SQLException;

public class Itemrb  {

    @FXML
    private Label descript;

    @FXML
    private ImageView itemimg;

    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;



    @FXML
    private Label statusid;
    private Produittroc Produit=new Produittroc();
    @FXML
    void status_click(ActionEvent event) {
        // Update the status
        Produit.setStatut(1); // Assuming 1 represents the new status

        // Save the updated product in the database
        try {
            produitTrocService.updateProduittroc(Produit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(Produit.getStatut()==1)
        {
            statusid.setText(""+ "Active"); // Assuming status is a label showing the status

        }
        else
            statusid.setText(""+ "inactive"); // Assuming status is a label showing the status



        // Update the UI or show a confirmation message if needed
    }
    Produit_TrocService produitTrocService = new Produit_TrocService();
    Producttrocwith producttrocwith=new Producttrocwith();


    public void setData(Produittroc Produit){
        this.Produit=Produit;

        Produit.setId(this.Produit.getId());

        System.out.println(Produit);
        itemname.setText(Produit.getNom());
        itemprice.setText(Produit.getCategory());
        descript.setText(Produit.getDescription());
statusid.setText(String.valueOf(Produit.getStatut()));
        if (Produit.getImage() != null && !Produit.getImage().isEmpty()) {
            // Use the file:/// protocol for local file paths
            String imageUrl = "file:///" + Produit.getImage();
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


}
