package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.entities.Produittroc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Marketroc implements Initializable {


    @FXML
    public GridPane BookListView;
    @FXML
    private GridPane collectionListView;
    @FXML
    private ImageView bookicon;
    private List<Produittroc> BookObservableList;


    @FXML
    private ComboBox<String> categoryComboBox;
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
    public Marketroc() throws SQLException {
        Produit_TrocService serviceBook=new Produit_TrocService();

        try {
            BookObservableList = serviceBook.afficherListPT();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BookListView = new GridPane();
//        collectionListView= new GridPane();

    }

    public void initialize(URL location, ResourceBundle resources) {
//        collectionanchor.setVisible(false);

        mainanchor.setVisible(true);
        bookicon.setVisible(false);
        loadBooks();
//        loadCollectionItems();
//        ObservableList<String> listData = FXCollections.observableArrayList("GARDEN", "HOUSE","AUTRE");
//        categoryComboBox.setItems(listData);
    }



    void loadBooks() {
        int col = 1;
        int rows = 0;
        try {
            for (int i = 0; i < BookObservableList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/items.fxml"));
                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                itemsPd ItemController = fxmlLoader.getController();
                ItemController.setData(BookObservableList.get(i));

                BookListView.add(anchorPane, col, rows);

                // Increment col for the next iteration
                col++;

                // Check if col reaches 2 (number of desired columns)
                if (col == 4) {
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

    public void reloadMarket() {
        try {
            // Get the URL of the FXML file
            URL location = getClass().getResource("/tn/esprit/jardindart/Market.fxml");

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
//    private void loadCollectionItems() {
//        int col = 0;
//        int rows = 0;
//      //  System.out.println(panier);
//        try {
//            for (int i = 0; i < panierObservableList.size(); i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/User/collectionItem.fxml"));
//                System.out.println("Loading collectionItem.fxml");
//                AnchorPane anchorPane = fxmlLoader.load();
//                // Assuming setData is a method in collectionItemController
//                collectionitemController ItemController = fxmlLoader.getController();
//                ItemController.setData(panierObservableList.get(i));
//                collectionListView.add(anchorPane, col, rows++);
//            }
//        } catch (IOException e) {
//            System.err.println("Error loading collectionItem.fxml: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//



    // ... Existing code ...

//    @FXML
//    void filterByCategory(ActionEvent event) {
//        Book.Categorie selectedCategory = Book.Categorie.valueOf(categoryComboBox.getValue());
//        if (selectedCategory != null) {
//            // Filter the books based on the selected category
//            List<Book> filteredBooks = filterBooksByCategory(selectedCategory);
//
//            // Clear the existing BookListView
//            BookListView.getChildren().clear();
//
//            // Reload the filtered books
//            int col = 1;
//            int rows = 0;
//            try {
//                for (int i = 0; i < filteredBooks.size(); i++) {
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/Admin/itembook.fxml"));
//                    System.out.println("Loading itembook.fxml");
//                    AnchorPane anchorPane = fxmlLoader.load();
//                    itembookController ItemController = fxmlLoader.getController();
//                    ItemController.setData(filteredBooks.get(i));
//                    BookListView.add(anchorPane, col, rows);
//
//                    // Increment col for the next iteration
//                    col++;
//
//                    // Check if col reaches 2 (number of desired columns)
//                    if (col == 4) {
//                        col = 1;  // Reset col to 0 for the next row
//                        rows++;   // Move to the next row
//                    }
//                }
//            } catch (IOException e) {
//                System.err.println("Error loading itembook.fxml: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//    }

//    private List<Book> filterBooksByCategory(Book.Categorie category) {
//        // Implement your logic to filter books based on the selected category
//        // For example, you can iterate through BookObservableList and return books with matching category
//        return BookObservableList.stream()
//                .filter(book -> category.equals(book.getCategorie()))
//                .collect(Collectors.toList());
//    }
//


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




}
