package tn.jardindart.controllers.DA;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.jardindart.models.DonArgent;
import tn.jardindart.services.DonArgentService;
import java.util.ArrayList;

public class AfficherDA {

    @FXML
    private TableView<DonArgent> tableac;

    @FXML
    private TableColumn<DonArgent, Integer> idColumn;

    @FXML
    private TableColumn<DonArgent, Double> montantColumn;

    @FXML
    private TableColumn<DonArgent, String> associationColumn;

    @FXML
    private TableColumn<DonArgent, Integer> userIdColumn;

    private final DonArgentService service = new DonArgentService();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        montantColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMontant()).asObject());
        associationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_assoc()));
        //userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());

        //configurerCellulesActions();
        chargerDonnees();
    }

    private void chargerDonnees() {
        ArrayList<DonArgent> donsArgent = service.afficher();
        ObservableList<DonArgent> observableDonsArgent = FXCollections.observableArrayList(donsArgent);
        tableac.setItems(observableDonsArgent);
    }


}
