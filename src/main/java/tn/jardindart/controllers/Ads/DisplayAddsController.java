package tn.jardindart.controllers.Ads;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.jardindart.models.Annonces;
import tn.jardindart.services.Sannonces;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayAddsController implements Initializable {
    @FXML
    private Button back;





    @FXML
    private TableColumn<Annonces, String> image;

    @FXML
    private TableColumn<Annonces, String> title;

    @FXML
    private TableColumn<Annonces, Double> price;




    @FXML
    private TableView<Annonces> paniertable;

    @FXML
    void backtodisplay(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*-ArrayList<Annonces>test=new ArrayList<Annonces>();
        Sannonces sannonces = new Sannonces();
        test=sannonces.getAllAdsByidcat();*/
    }



    public TableView<Annonces> getPaniertable() {
        return paniertable;
    }

    public void configureTableView() {
        // Configurez les cellules de la TableView avec les données des annonces
        image.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));

        // Configurez une cellule personnalisée pour l'affichage des images
        image.setCellFactory(column -> {
            return new TableCell<Annonces, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (empty || imagePath == null) {
                        setGraphic(null);
                    } else {
                        try {
                            // Chargez et affichez l'image à partir du chemin
                            Image image = new Image("file:" + imagePath);
                            imageView.setImage(image);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
                            setGraphic(null);
                        }
                    }
                }
            };
        });
    }
}
