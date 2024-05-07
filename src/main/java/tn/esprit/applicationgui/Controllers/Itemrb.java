package tn.esprit.applicationgui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.entites.ReponseBlog;

import java.io.File;
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
    private Button modif;

    @FXML
    private HBox qr_code;

    @FXML
    private Label status;

    @FXML
    private Label statusid;
    private Blog blog=new Blog();
    @FXML
    void status_click(ActionEvent event) {
        // Update the status
        blog.setStatus(1); // Assuming 1 represents the new status

        // Save the updated product in the database
        try {
            produitTrocService.updatestatus(blog);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(blog.getStatus()==1)
        {
            statusid.setText(""+"Active"); // Assuming status is a label showing the status

        }
        else
            statusid.setText(""+ "inactive"); // Assuming status is a label showing the status



        // Update the UI or show a confirmation message if needed
    }
    Blogservice produitTrocService = new Blogservice();
    ReponseBlog producttrocwith=new ReponseBlog();

    public void setData(Blog book) {
        this.blog = book;

        book.setId(this.blog.getId());

        System.out.println(book);
        itemname.setText(book.getTitre());
        itemprice.setText(book.getCategory());
        descript.setText(book.getContenu_blog());

        // Load the image
        try {
            Image image = new Image(new File(book.getImage_blog()).toURI().toString());
            itemimg.setImage(image);
        } catch (Exception e) {
            // Handle the case where the image resource is not found
            System.out.println("Error loading image: " + e.getMessage());
            // Set a default image or display an error message
        }
    }

}
