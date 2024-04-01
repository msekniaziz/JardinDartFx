package tn.esprit.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Produittroc;

public class Showproduitr {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Produittroc> tableviewTroc;

    @FXML
    private TableColumn<Produittroc, String> nom;

    @FXML
    private TableColumn<Produittroc, String> categ;

    @FXML
    private TableColumn<Produittroc, String> decription;

    @FXML
    private TableColumn<Produittroc, String> image;

    @FXML
    private TableColumn<Produittroc,String> print;
    @FXML
    private TextField searchField;

    @FXML
    void initialize() {

        Produit_TrocService pt = new Produit_TrocService();
        try {
            List<Produittroc> prds = pt.afficherListPT();
            ObservableList<Produittroc> observableList = FXCollections.observableList(prds);
            tableviewTroc.setItems(observableList);

            // Set cell value factories for each TableColumn
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            categ.setCellValueFactory(new PropertyValueFactory<>("category"));
            decription.setCellValueFactory(new PropertyValueFactory<>("description"));
            print.setCellValueFactory(new PropertyValueFactory<>("nom_produit_recherche")); // Assuming "print" is a property of Produittroc

            // Set custom cell factory for the image column
            image.setCellFactory(tc -> new TableCell<Produittroc, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (empty || imagePath == null) {
                        setGraphic(null);
                    } else {
                        try {
                            // Load the image from the file path
                            Image image = new Image(new FileInputStream(imagePath));
                            imageView.setImage(image);
                            imageView.setFitWidth(50); // Set the width of the image
                            imageView.setFitHeight(50); // Set the height of the image
                            setGraphic(imageView);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            // Handle image loading error
                        }
                    }
                }
            });

            // Assuming "imagePath" is the property representing the image file path in Produittroc
            image.setCellValueFactory(new PropertyValueFactory<>("image"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

}
