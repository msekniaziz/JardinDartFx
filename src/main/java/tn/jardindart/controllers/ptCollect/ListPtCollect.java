package tn.jardindart.controllers.ptCollect;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.jardindart.models.PtCollect;
import tn.jardindart.services.PtCollectService;
import tn.jardindart.test.HelloApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ListPtCollect {

    @FXML
    private TableView<PtCollect> ptCollectTable;

    @FXML
    private TableColumn<PtCollect, String> nomPcColumn;

    @FXML
    private TableColumn<PtCollect, String> adressePcColumn;

    @FXML
    private TableColumn<PtCollect, Float> latitudePcColumn;

    @FXML
    private TableColumn<PtCollect, Float> longitudePcColumn;
    @FXML
    private TableColumn<PtCollect, Void> actionsColumn;

    @FXML
    private PieChart statistiquesTypesChart;

    private PtCollectService ptCollectService;
    @FXML
    private VBox statistiquesTypesContainer; // VBox pour afficher les statistiques sur les types disponibles


    @FXML
    private void initialize() {
        ptCollectService = new PtCollectService();
        afficherPtCollect();
        configurerCellulesActions();
      afficherStatistiquesTypes();

    }

    private void afficherPtCollect() {
        // Récupérer la liste des points de collecte depuis le service
        List<PtCollect> ptCollectList = ptCollectService.recuperer();

        // Ajouter les données à la table
        ptCollectTable.getItems().addAll(ptCollectList);

        nomPcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomPc()));
        adressePcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdressePc()));
        latitudePcColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLatitudePc()));
        longitudePcColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLongitudePc()));
    }
    private JFXButton createIconButton(String text, String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);  // Set the width of the icon
        iconView.setFitHeight(iconSize); // Set the height of the icon
        button.setGraphic(iconView);
        button.setButtonType(JFXButton.ButtonType.RAISED);
        return button;
    }

    private void configurerCellulesActions() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final JFXButton viewButton = createIconButton(null, "/tn.jardindart/icons/carte.png",20);
            private final JFXButton editButton = createIconButton(null, "/tn.jardindart/icons/edit.png",20);

            private final JFXButton deleteButton = createIconButton(null, "/tn.jardindart/icons/delete.png",20);

            {
                viewButton.setOnAction(event -> {
                    PtCollect p = getTableView().getItems().get(getIndex());
afficherMap();
                    // Action à effectuer lors du clic sur l'icône "View"
                    System.out.println("View clicked for ProdR ID: " + p.getId());
                });

                editButton.setOnAction(event -> {
                    PtCollect p = getTableView().getItems().get(getIndex());
                    // Action à effectuer lors du clic sur l'icône "Edit"
                    System.out.println("Edit clicked for ProdR ID: " + p.getId());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn.jardindart/ptCollect/EditPtCollect.fxml"));
                        ptCollectTable.getScene().setRoot(fxmlLoader.load());
//                        Parent root = loader.load();
                        EditPtCollect controller = fxmlLoader.getController();
                        controller.initialize(p.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                deleteButton.setOnAction(event -> {
                    PtCollect p = getTableView().getItems().get(getIndex());

                    // Créer une boîte de dialogue de confirmation
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Confirmation de la suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer cet élément ?");

                    // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Si l'utilisateur confirme, supprimer l'élément
                        ptCollectService.supprimer(p.getId());
                        getTableView().getItems().remove(p);
                        System.out.println("Suppression effectuée pour collect pt ID: " + p.getId());
                    } else {
                        // Sinon, ne rien faire
                        System.out.println("Suppression annulée pour collect pt ID: " + p.getId());
                    }
                });



            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(viewButton, editButton,deleteButton));
                }
            }
        });



    }
    /*private void afficherStatistiquesTypes() {
        Map<String, Integer> statistiquesTypes = ptCollectService.recupererStatistiquesTypes();
        statistiquesTypesContainer.getChildren().clear(); // Nettoyer le conteneur avant d'ajouter de nouveaux éléments

        // Parcourir la carte des statistiques et afficher chaque type avec son nombre
        statistiquesTypes.forEach((type, nombre) -> {
            Label label = new Label(type + ": " + nombre);
            statistiquesTypesContainer.getChildren().add(label);
        });
    }
    private void afficherStatistiquesTypesS() {
        // Récupérer les statistiques sur les types disponibles depuis le service
        List<PtCollect> ptCollectList = ptCollectService.recuperer();

        // Compter le nombre de chaque type de disponibilité
        // (Cette partie dépend de la structure de vos données et de la façon dont vous souhaitez les afficher dans le PieChart)
        // Exemple: Supposons que chaque PtCollect a une liste de types de disponibilité, et vous voulez compter le nombre de chaque type
        // Vous devrez parcourir chaque PtCollect, puis chaque type de disponibilité dans sa liste, et incrémenter le compteur approprié

        // Créer une liste de sections de PieChart avec les données obtenues
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Type 1", 25), // Remplacer 25 par le nombre réel de ce type
                new PieChart.Data("Type 2", 35) // Remplacer 35 par le nombre réel de ce type
                // Ajouter d'autres sections pour chaque type de disponibilité
        );

        // Afficher les données dans le PieChart
        statistiquesTypesChart.setData(pieChartData);
    }*/
    private void afficherStatistiquesTypes() {
        Map<String, Integer> statistiquesTypes = ptCollectService.recupererStatistiquesTypes();
        statistiquesTypesContainer.getChildren().clear(); // Nettoyer le conteneur avant d'ajouter de nouveaux éléments

        // Créer une liste de sections de PieChart avec les données obtenues
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Parcourir la carte des statistiques et afficher chaque type avec son nombre
        statistiquesTypes.forEach((type, nombre) -> {
            Label label = new Label(type + ": " + nombre);
            statistiquesTypesContainer.getChildren().add(label);

            // Ajouter les données à la liste pour le PieChart
            pieChartData.add(new PieChart.Data(type, nombre));
        });

        // Afficher les données dans le PieChart
        statistiquesTypesChart.setData(pieChartData);
    }


    @FXML
    public void afficherMap() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn.jardindart/ptCollect/Map.fxml"));
        try {
            statistiquesTypesContainer.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
