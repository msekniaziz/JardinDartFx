package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Produittroc;

public class BackproT implements Initializable {
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
    private ComboBox<?> categoryComboBox;

    @FXML
    private AnchorPane mainanchor;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;



    private List<Produittroc> BookObservableList;





    public void initialize(URL location, ResourceBundle resources) {

        loadBooks();

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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Itemrb.fxml"));
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



}
