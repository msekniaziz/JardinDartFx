package tn.esprit.applicationgui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.entites.ReponseBlog;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class RepBlogmain  {

    @FXML
    private GridPane BookListView;

    @FXML
    private ImageView bookicon;

    @FXML
    private GridPane collectionListView;
    @FXML
    private Button generatePDFButton;
    @FXML
    private AnchorPane collectionanchor;

    @FXML
    private AnchorPane mainanchor;
    private List<Blog> BookObservableList;

    private List<ReponseBlog> ProdwithObservelist;

    @FXML
    private Label totalReponsesLabel;
    @FXML
    void booksnavclicked(ActionEvent event) {
        mainanchor.setVisible(true);
        collectionanchor.setVisible(false);
        bookicon.setVisible(false);
    }

    public RepBlogmain() throws SQLException {
        Blogservice serviceBook=new Blogservice();
        ReponseBlogservice serviceTRoc =new ReponseBlogservice();

        try {
            BookObservableList = serviceBook.showblog();
            ProdwithObservelist =serviceTRoc.showrep();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BookListView = new GridPane();

        collectionListView= new GridPane();

    }

    public void initialize(URL location, ResourceBundle resources) {
        collectionanchor.setVisible(false);

        mainanchor.setVisible(true);
        bookicon.setVisible(false);
        loadBooks();
        //  loadCollectionItems();

    }


    void loadBooks() {
        int col = 1;
        int rows = 0;
        try {
            for (int i = 0; i < ProdwithObservelist.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/applicationgui/RepBlogmain.fxml"));
                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                Cardrep ItemController = fxmlLoader.getController();
                ItemController.setData(ProdwithObservelist.get(i));
                BookListView.add(anchorPane, col, rows);

                // Increment col for the next iteration
                col++;

                // Check if col reaches 2 (number of desired columns)
                if (col == 3) {
                    col = 1;  // Reset col to 0 for the next row
                    rows++;   // Move to the next row
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading itembook.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void reloadMarket() {
        try {
            // Get the URL of the FXML file
            URL location = getClass().getResource("/tn/esprit/applicationgui/RpBlogmain.fxml");

            // Load the FXML file and initialize the controller
            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();

            // Get the current scene from any node within the scene hierarchy
            Scene scene = mainanchor.getScene();

            // Replace the root of the current scene with the newly loaded root
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }

        // Méthode updateTotalReponsesLabel à l'extérieur de reloadMarket

//        public void updateTotalReponsesLabel(int totalReponses) {
//            totalReponsesLabel.setText("Total Réponses: " + totalReponses);
//        }
    }

}
