package tn.jardindart.controllers.Blog;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.jardindart.models.ReponseBlog;
import tn.jardindart.models.Blog;

import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Mainrep implements Initializable {


    @FXML
    public GridPane BookListView;
    @FXML
    private GridPane collectionListView;
    @FXML
    private ImageView bookicon;
    @FXML
    private Button calendrierid;
    @FXML
    private Button generatePDFButton;
    private List<ReponseBlog> BookObservableList;

    private List<ReponseBlog> ProdwithObservelist;


    @FXML
    private AnchorPane mainanchor;

    @FXML
    private AnchorPane collectionanchor;

    // Your existing code...

    @FXML
    private void handleCollectionButtonClick(ActionEvent event) {
        // Hide the mainanchor and show the collectionanchor
        mainanchor.setVisible(false);
        collectionanchor.setVisible(true);
        bookicon.setVisible(true);

    }
    @FXML
    void booksnavclicked(ActionEvent event) {
        mainanchor.setVisible(true);
        collectionanchor.setVisible(false);
        bookicon.setVisible(false);
    }
    public Mainrep() throws SQLException {
        Blogservice serviceBook=new Blogservice();
        ReponseBlogservice serviceTRoc =new ReponseBlogservice();

        try {
            BookObservableList = serviceTRoc.showrep();
          //  ProdwithObservelist =serviceTRoc.showrep();

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
            for (int i = 0; i < BookObservableList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/cardRepblog.fxml"));
                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                Cardrep ItemController = fxmlLoader.getController();
                ItemController.setData(BookObservableList.get(i));
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
            URL location = getClass().getResource("/tn.jardindart/Blog/blogmain.fxml");

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
    }

    @FXML
    private void generatePDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            float yPosition = 700;

            for (ReponseBlog reponse : BookObservableList) {
                contentStream.beginText();
                contentStream.newLineAtOffset(100, yPosition);
                contentStream.showText("Date: " + reponse.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                contentStream.endText();

                yPosition -= 20;

                contentStream.beginText();
                contentStream.newLineAtOffset(100, yPosition);
                contentStream.showText("Content: " + reponse.getContenu());
                contentStream.endText();

                yPosition -= 20;

                // Ajoutez d'autres informations si nécessaire

                yPosition -= 20;
            }

            contentStream.close();
            document.save("reponses_blogs.pdf");
            document.close();

            showAlert(Alert.AlertType.INFORMATION, "Success", "PDF generated successfully.");

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate PDF: " + e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showCalendar(ActionEvent event) {    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/calendrier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    }


//    private void loadCollectionItems() {
//        int col = 0;
//        int rows = 0;
////        System.out.println(bookicon);
//        try {
//            for (int i = 0; i < ProdwithObservelist.size(); i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/itemstroc.fxml"));
//                System.out.println("Loading collectionItem.fxml");
//                AnchorPane anchorPane = fxmlLoader.load();
//                // Assuming setData is a method in collectionItemController
//                Cardblog ItemController = fxmlLoader.getController();
//                ItemController.setData(ProdwithObservelist.get(i));
//                collectionListView.add(anchorPane, col, rows++);
//            }
//        } catch (IOException e) {
//            System.err.println("Error loading collectionItem.fxml: " + e.getMessage());
//            e.printStackTrace();
//        }





//    @FXML
//    void filterByCategory(ActionEvent event) {
//        String selectedCategory = categoryComboBox.getValue();
//        if (selectedCategory != null) {
//            // Filter the books based on the selected category
//            List<Add> filteredBooks = filterProdparcateg(selectedCategory);
//
//            // Clear the existing BookListView
//            BookListView.getChildren().clear();
//
//            // Reload the filtered books
//            int col = 1;
//            int rows = 0;
//            try {
//                for (int i = 0; i < filteredBooks.size(); i++) {
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/items.fxml"));
//                    System.out.println("Loading aaaaaaaaaaaa.fxml");
//                    AnchorPane anchorPane = fxmlLoader.load();
//                    itemsPd ItemController = fxmlLoader.getController();
//                    ItemController.setData(filteredBooks.get(i));
//                    BookListView.add(anchorPane, col, rows);
//
//                    // Increment col for the next iteration
//                    col++;
//
//                    // Check if col reaches 2 (number of desired columns)
//                    if (col == 3) {
//                        col = 1;  // Reset col to 0 for the next row
//                        rows++;   // Move to the next row
//                    }
//                }
//            } catch (IOException e) {
//                System.err.println("Error loading itembook.fxml: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//   }

//    private List<Produittroc> filterProdparcateg(String category) {
//        return BookObservableList.stream()
//                .filter(book -> category.equals(book.getCategory()))
//                .collect(Collectors.toList()); // Add this line to collect the filtered books into a list
//    }




//    void refreshGridPane() throws SQLException {
//        // Assuming your grid pane is named BookListView
//        collectionListView.getChildren().clear(); // Clear existing items
////        ServicePanier servicePanier=new ServicePanier();
//        // Reload the books and populate the grid pane
////        List<Panier> allBooks = servicePanier.recuperer(); // Replace this with your actual method to get all books
//        int col = 0;
//        int rows = 0;
//
//        try {
//            for (int i = 0; i < allBooks.size(); i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/User/collectionItem.fxml"));
//                System.out.println("Loading itembook.fxml");
//                AnchorPane anchorPane = fxmlLoader.load();
//                collectionitemController ItemController = fxmlLoader.getController();
//                ItemController.setData(allBooks.get(i));
//                collectionListView.add(anchorPane, col, rows++);
//
//
//            }
//        } catch (IOException e) {
//            System.err.println("Error loading itembook.fxml: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }





