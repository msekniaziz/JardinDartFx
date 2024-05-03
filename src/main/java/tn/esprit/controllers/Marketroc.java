package tn.esprit.controllers;

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
import javafx.stage.Stage;
import tn.esprit.entities.Producttrocwith;
import tn.esprit.entities.Produittroc;

import java.awt.print.Book;
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
    private TextField searchpanel;
    private List<Producttrocwith> ProdwithObservelist;

    @FXML
    private ComboBox<String> categoryComboBox;

    public AnchorPane getMainanchor() {
        return mainanchor;
    }

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
    public void market(ActionEvent event) {
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


        }
    }
    @FXML
    void back(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/BackproT.fxml"));
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






    public void searchbook_clicked(KeyEvent keyEvent) {
        // 1. Retrieve the text from the searchpanel TextField
        String searchText = searchpanel.getText().toLowerCase().trim();

        // 2. Clear the existing content in the gridPane
        BookListView.getChildren().clear();

        // 3. Iterate through the list of books to find matches
        int col = 0;
        int rows = 0;
        for (Produittroc book : BookObservableList) {
            if (book.getNom().toLowerCase().contains(searchText))  {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/tn/esprit/jardindart/items.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    itemsPd itemAdminController = fxmlLoader.getController();
                    itemAdminController.setData(book);
                    BookListView.add(anchorPane, col, rows++);
                } catch (IOException e) {
                    System.out.println("Error loading FXML: " + e.getMessage());
                }
            }
        }
    }
    @FXML
    void ajouter_prodtroc(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/addproduittr.fxml"));
            Parent root = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Créer une nouvelle scène avec le contenu chargé depuis le fichier FXML
            Scene scene = new Scene(root);
            ComboBox<String> catrgtroComboBox = (ComboBox<String>) root.lookup("#catrgtro");
            catrgtroComboBox.getItems().addAll("house", "garden");
            // Définir la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }}

    @FXML
    void booksnavclicked(ActionEvent event) {
        mainanchor.setVisible(true);
        collectionanchor.setVisible(false);
        bookicon.setVisible(false);
    }
    public Marketroc() throws SQLException {
        Produit_TrocService serviceBook=new Produit_TrocService();
        Produit_troc_with_Service serviceTRoc =new Produit_troc_with_Service();

        try {
            BookObservableList = serviceBook.afficherListPTfront();
            ProdwithObservelist =serviceTRoc.afficherListPT();

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
        loadCollectionItems();
        ObservableList<String> listData = FXCollections.observableArrayList("garden", "house");
        categoryComboBox.setItems(listData);
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
    private void loadCollectionItems() {
        int col = 0;
        int rows = 0;
//        System.out.println(bookicon);
        try {
            for (int i = 0; i < ProdwithObservelist.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/itemstroc.fxml"));
                System.out.println("Loading collectionItem.fxml");
                AnchorPane anchorPane = fxmlLoader.load();
                // Assuming setData is a method in collectionItemController
                Itemstroc ItemController = fxmlLoader.getController();
                ItemController.setData(ProdwithObservelist.get(i));
                collectionListView.add(anchorPane, col, rows++);
            }
        } catch (IOException e) {
            System.err.println("Error loading collectionItem.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @FXML
    void filterByCategory(ActionEvent event) {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            // Filter the books based on the selected category
            List<Produittroc> filteredBooks = filterProdparcateg(selectedCategory);

            // Clear the existing BookListView
            BookListView.getChildren().clear();

            // Reload the filtered books
            int col = 1;
            int rows = 0;
            try {
                for (int i = 0; i < filteredBooks.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/items.fxml"));
                    System.out.println("Loading aaaaaaaaaaaa.fxml");
                    AnchorPane anchorPane = fxmlLoader.load();
                    itemsPd ItemController = fxmlLoader.getController();
                    ItemController.setData(filteredBooks.get(i));
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
    }

    private List<Produittroc> filterProdparcateg(String category) {
        return BookObservableList.stream()
                .filter(book -> category.equals(book.getCategory()))
                .collect(Collectors.toList()); // Add this line to collect the filtered books into a list
    }

}