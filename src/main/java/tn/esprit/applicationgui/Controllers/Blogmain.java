package tn.esprit.applicationgui.Controllers;


import javafx.geometry.Insets; // Assurez-vous d'importer la classe Insets de JavaFX


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tn.esprit.applicationgui.entites.ReponseBlog;
import tn.esprit.applicationgui.entites.Blog;

import java.awt.*;
import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Blogmain implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    public GridPane BookListView;
    @FXML
    private GridPane collectionListView;
    @FXML
    private ImageView bookicon;
    private List<Blog> BookObservableList;

    private List<ReponseBlog> ProdwithObservelist;
    private Blogservice blogService; // Ajoutez cet attribut
private like_blogservice like_blogservice;

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
    public Blogmain() throws SQLException {
        blogService = new Blogservice();

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

    private void updateGrid(List<Blog> filteredResults) {
        BookListView.getChildren().clear();
        int columnCount = 3;
        int columnIndex = 0;
        int rowIndex = 0;

        // Espacement horizontal et vertical supplémentaire entre les cartes
        double horizontalGap = 20.0;
        double verticalGap = 20.0;

        for (Blog blog : filteredResults) {
            Node card = createCard(blog);
            if (card != null) {
                Insets cardMargin = new Insets(verticalGap, horizontalGap, verticalGap, horizontalGap);

                GridPane.setMargin(card, cardMargin);

                BookListView.add(card, columnIndex, rowIndex);
                columnIndex++;
                if (columnIndex >= columnCount) {
                    columnIndex = 0;
                    rowIndex++;
                }
            }
        }
    }

    private Pane createCard(Blog blog) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/applicationgui/cardblog.fxml"));
        try {
            AnchorPane cardPane = loader.load();
            Cardblog cardController = loader.getController();
            cardController.setData(blog);
            return cardPane;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    void loadBooks() {
        int col = 1;
        int rows = 0;
        double horizontalGap = 20.0; // Espacement horizontal
        double verticalGap = 20.0;
        try {
            for (int i = 0; i < BookObservableList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/applicationgui/cardblog.fxml"));
                System.out.println("Loading itembook.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                Cardblog ItemController = fxmlLoader.getController();



                ItemController.setData(BookObservableList.get(i));
                Insets cardMargin = new Insets(verticalGap, horizontalGap, verticalGap, horizontalGap);
                GridPane.setMargin(anchorPane, cardMargin);

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
            URL location = getClass().getResource("/tn/esprit/applicationgui/blogmain.fxml");

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

    public void handleSearch(KeyEvent keyEvent) {
        String searchTerm = searchField.getText().toLowerCase();
        try {
            List<Blog> emplacements = blogService.searchEmplacement(searchTerm); // Utilisez l'instance de Blogservice
            updateGrid(emplacements); // Mettez à jour la grille avec les résultats filtrés
        } catch (SQLException e) {
            e.printStackTrace();
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
    }




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





