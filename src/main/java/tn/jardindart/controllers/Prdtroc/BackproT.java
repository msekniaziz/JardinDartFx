package tn.jardindart.controllers.Prdtroc;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.io.font.constants.StandardFonts;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.scene.control.Button;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
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
import tn.jardindart.models.Produittroc;
import tn.jardindart.models.Producttrocwith;

public class BackproT implements Initializable {
    @FXML
    public Button pdf;

    @FXML
    private GridPane BookListView;

    @FXML
    private PieChart pieChart;

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



    private List<Produittroc> BookObservableList;

private  Produit_TrocService p=new Produit_TrocService();



    public void initialize(URL location, ResourceBundle resources) {

        loadBooks();
        loadCategories();


    }

    public BackproT() throws SQLException {
        Produit_TrocService serviceBook=new Produit_TrocService();
        Produit_troc_with_Service serviceTRoc =new Produit_troc_with_Service();

        try {
            BookObservableList = serviceBook.afficherListPTdiffuser(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BookListView = new GridPane();


    }

    @FXML
    void back(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Market.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(root);

            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }}

    void loadBooks() {
        int col = 1;
        int rows = 0;
        try {
            for (int i = 0; i < BookObservableList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn.jardindart/Itemrb.fxml"));
                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                Itemrb ItemController = fxmlLoader.getController();
                ItemController.setData(BookObservableList.get(i));

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



    @FXML
    void generatePieChart(ActionEvent event) throws SQLException {
        // Prepare your data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("garden", p.countGardenProducts()),
                new PieChart.Data("house", p.counthouse())
        );
        // Add data to the PieChart
        pieChart.setData(pieChartData);
    }



    @FXML
    void generatePDF(ActionEvent event) {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            try {
                // Create a new PDF document
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter("C:\\Users\\Zakraoui\\Desktop\\JardinDartFx-integ\\troc.pdf"));
                System.out.println("PDF works");

                // Set the document size and margins
                Document document = new Document(pdfDoc, PageSize.A4);
                document.setMargins(50, 50, 50, 50);

                // Define colors
                Color titleColor = new DeviceRgb(34, 139, 34); // Green color for the title
                Color categoryTitleColor = new DeviceRgb(139, 69, 19); // Brown color for category titles
                Color bookTitleColor = new DeviceRgb(0, 0, 255); // Blue color for book titles

                // Set fonts
                PdfFont titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                PdfFont categoryTitleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                PdfFont bookTitleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

                // Add a title to the document
                Paragraph title = new Paragraph("Jardin'Dart Magazine \n Products Exchange Catalogue");
                title.setFont(titleFont);
                title.setFontSize(18f);
                title.setFontColor(titleColor);
                title.setTextAlignment(TextAlignment.CENTER);
                document.add(title);

                // Add a line separator
                LineSeparator lineSeparator = new LineSeparator(new DottedLine());

                // Iterate through each selected category
                for (String category : selectedCategory.split(", ")) {
                    // Add category title
                    Paragraph categoryTitle = new Paragraph("Category: " + category);
                    categoryTitle.setFont(categoryTitleFont);
                    categoryTitle.setFontSize(16f);
                    categoryTitle.setFontColor(categoryTitleColor);
                    document.add(categoryTitle);

                    // Add a line separator
                    document.add(lineSeparator);

                    // Add the books based on the selected category
                    for (Produittroc book : BookObservableList) {
                        if (book.getCategory().equals(category)) {
                            // Add book title
                            Paragraph bookTitle = new Paragraph(book.getNom());
                            bookTitle.setFont(bookTitleFont);
                            bookTitle.setFontSize(14f);
                            bookTitle.setFontColor(bookTitleColor);
                            document.add(bookTitle);

                            // Add book image
                            Image image = new Image(ImageDataFactory.create(book.getImage()));
                            image.setWidth(100);
                            image.setHeight(100);
                            document.add(image);

                            // Add book description
                            Paragraph description = new Paragraph(book.getDescription());
                            description.setFontSize(12f);
                            document.add(description);

                            // Add a line separator
                            document.add(lineSeparator);
                        }
                    }

                    // Add spacing between categories
                    document.add(new Paragraph("\n"));
                }

                // Close the document
                document.close();

                System.out.println("PDF generated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}