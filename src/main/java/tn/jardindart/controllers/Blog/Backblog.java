package tn.jardindart.controllers.Blog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.jardindart.controllers.Blog.Itemrb;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;

import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import tn.jardindart.controllers.Blog.Blogservice;
import tn.jardindart.models.Blog;
import tn.jardindart.test.HelloApplication;

public class Backblog implements Initializable {
    @FXML
    public Button pdf;

    @FXML
    private GridPane BookListView;


    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private AnchorPane mainanchor;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;


    private Backblog backblog;

    private List<Blog> BookObservableList;

Itemrb itemrb =new Itemrb();



    public void initialize(URL location, ResourceBundle resources) {
        loadBooks();
        loadCategories();
        }





    public Backblog() throws SQLException {
        Blogservice serviceBook=new Blogservice();

        try {
            BookObservableList = serviceBook.showblogback();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BookListView = new GridPane();


    }

    @FXML
    void back(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/blogmain.fxml"));
            Parent newContent = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(newContent);

            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void loadBooks() {
        int col = 1;
        int rows = 0;
        try {
            for (int i = 0; i < BookObservableList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/ItemBack.fxml"));

                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                 itemrb = fxmlLoader.getController();
                itemrb.setData(BookObservableList.get(i));

                BookListView.add(anchorPane, col, rows);

                // Increment col for the next iteration
                col++;

                // Check if col reaches 2 (number of desired columns)
                if (col == 3) {
                    col = 1;  // Reset col to 0 for the next row
                    rows++;   // Move to the next row
                }
                System.out.printf("aaaaa");

            }
        } catch (IOException e) {
            System.err.println("Error loading itembook.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void loadCategories() {
        ObservableList<String> categories = FXCollections.observableArrayList("garden", "house");
        categoryComboBox.setItems(categories);
    }



//void generatePDF(ActionEvent event) {
//    String selectedCategory = categoryComboBox.getValue();
//    if (selectedCategory != null) {
//        try {
//            // Create a new PDF document
//            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("C:/Users/Zakraoui/Desktop/JardinDart/ouee.pdf"));
//            System.out.println("PDF works");
//
//            // Set the document size and margins
//            Document document = new Document(pdfDoc, PageSize.A4);
//            document.setMargins(50, 50, 50, 50);
//
//            // Add a title to the document
//            Paragraph title = new Paragraph("Products Exchange in Category: " + selectedCategory);
//            title.setFontSize(18f);
//            title.setBold();
//            title.setTextAlignment(TextAlignment.CENTER);
//            document.add(title);
//
//            // Add a line separator
//            LineSeparator lineSeparator = new LineSeparator(new DottedLine());
//            document.add(lineSeparator);
//
//            // Add the books based on the selected category
//            for (Produittroc book : BookObservableList) {
//                if (book.getCategory().equals(selectedCategory)) {
//                    // Add book title
//                    Paragraph bookTitle = new Paragraph(book.getNom());
//                    bookTitle.setBold();
//                    bookTitle.setFontSize(14f);
//                    document.add(bookTitle);
//
//                    // Add book image
//                    Image image = new Image(ImageDataFactory.create(book.getImage()));
//                    image.setWidth(200);
//                    image.setHeight(200);
//                    document.add(image);
//
//                    // Add book description
//                    Paragraph description = new Paragraph(book.getDescription());
//                    description.setFontSize(12f);
//                    document.add(description);
//
//                    // Add a line separator
//                    document.add(lineSeparator);
//                }
//            }
//
//            // Close the document
//            document.close();
//
//            System.out.println("PDF generated successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
}