package tn.esprit.jardindart.controllers.Association;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.services.AssociationService;
import com.jfoenix.controls.JFXButton;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.jardindart.test.HelloApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import tn.esprit.jardindart.controllers.Menu;

public class AfficherAssoc{

    @FXML
    private TableView<Association> table;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btn;

    @FXML
    private TableColumn<Association, Integer> idColumn;

    @FXML
    private TableColumn<Association, String> nameColumn;

    @FXML
    private TableColumn<Association, String> addressColumn;

    @FXML
    private TableColumn<Association, String> logoColumn;

    @FXML
    private TableColumn<Association, String> descriptionColumn;

    @FXML
    private TableColumn<Association, Integer> ribColumn;

    @FXML
    private TableColumn<Association, Void> actionsColumn;

    @FXML
    private TableColumn<Association, Void> deleteColumn;

    private final AssociationService service = new AssociationService();
    Menu menuc =new Menu();

    // Initialize method, called after FXML file has been loaded
    @FXML
    private void initialize() {
        // Bind Association properties to table columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_association()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse_association()));
        logoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogo_association()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription_asso()));
        ribColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(Integer.parseInt(String.valueOf(cellData.getValue().getRib()))));

        configurerCellulesActions();
        chargerDonnees();
    }

    @FXML
    private void handleAddAssociation() {
        try {
            menuc.navigateToFXML("/tn/esprit/jardindart/Association/AddAssoc.fxml", btn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chargerDonnees() {
        ArrayList<Association> associations = service.afficher();
        ObservableList<Association> observableAssociations = FXCollections.observableArrayList(associations);
        table.setItems(observableAssociations);
    }

    private void configurerCellulesActions() {
        actionsColumn.setCellFactory(param -> {
            return new TableCell<>() {
                private final JFXButton viewButton = createIconButton(null, "/tn/esprit/jardindart/icons/view.png", 20);
                private final JFXButton editButton = createIconButton(null, "/tn/esprit/jardindart/icons/edit.png", 20);
                private final JFXButton deleteButton = createIconButton(null, "/tn/esprit/jardindart/icons/delete.png", 20);

                {
                    viewButton.setOnAction(event -> {
                        Association association = getTableView().getItems().get(getIndex());
                        // Action à effectuer lors du clic sur l'icône "View"
                        System.out.println("View clicked for Don ID: " + association.getId());
                    });

                    editButton.setOnAction(event -> {
                        Association association = getTableView().getItems().get(getIndex());
                        System.out.println("Edit clicked for Association ID: " + association.getId());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Association/UpdateAssociation.fxml"));

                        try {
                          //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/Association/UpdateAssociation.fxml"));
                            //Parent editView = loader.load();
                            borderPane.getScene().setRoot(loader.load());
                            UpdateAssoc controller = loader.getController();
                            controller.initialize(association.getId());
                            //HelloApplication.updateCurrentView("/tn/esprit/jardindart/Association/UpdateAssociation.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    deleteButton.setOnAction(event -> {
                        Association association = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Confirmation de la suppression");
                        alert.setContentText("Voulez-vous vraiment supprimer cet élément ?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            service.supprimer(association.getId());
                            getTableView().getItems().remove(association);
                            System.out.println("Delete clicked for Don ID: " + association.getId());
                        } else {
                            System.out.println("Suppression annulée pour l'ID de Don: " + association.getId());
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(viewButton, editButton, deleteButton));
                    }
                }
            };
        });
    }

    private JFXButton createIconButton(String text, String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);  // Set the width of the icon
        iconView.setFitHeight(iconSize); // Set the height of the icon
        button.setGraphic(iconView);
        return button;
    }

}
