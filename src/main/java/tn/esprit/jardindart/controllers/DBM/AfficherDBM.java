package tn.esprit.jardindart.controllers.DBM;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.xhtmlrenderer.pdf.ITextRenderer;
import tn.esprit.jardindart.models.DonBienMateriel;
import tn.esprit.jardindart.services.DonBienMaterielService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class AfficherDBM {

    @FXML
    private TableView<DonBienMateriel> tableac;

    @FXML
    private TableColumn<DonBienMateriel, Integer> idColumn;

    @FXML
    private TableColumn<DonBienMateriel, String> pictureColumn;

    @FXML
    private TableColumn<DonBienMateriel, String> descriptionColumn;

    @FXML
    private TableColumn<DonBienMateriel, String> organizationColumn;
    @FXML
    private TableColumn<DonBienMateriel, Integer> userColumn;

    @FXML
    private TableColumn<DonBienMateriel, String> statusColumn;

    @FXML
    private TableColumn<DonBienMateriel, Void> actionsColumn;

    @FXML
    private TableColumn<DonBienMateriel, Void> deleteColumn;

    private final DonBienMaterielService service = new DonBienMaterielService();

    // Initialise la méthode, appelée après que le fichier FXML a été chargé
    @FXML

    private void initialize() {
        // Bind DonBienMateriel properties to table columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        pictureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoto_don()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc_don()));
        // organizationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getAssociationNameById(cellData.getValue())));

        organizationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_assoc()));
        userColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUser_id()).asObject());
        statusColumn.setCellValueFactory(cellData -> {
            BooleanProperty statusProperty = new SimpleBooleanProperty(cellData.getValue().isStatut_don());
            // Convert boolean property to a string property
            StringProperty stringStatusProperty = new SimpleStringProperty(statusProperty.getValue() ? "Active" : "Inactive");
            // Bind the string property to the cell value
            statusProperty.addListener((observable, oldValue, newValue) -> {
                stringStatusProperty.set(newValue ? "Active" : "Inactive");
            });
            return stringStatusProperty;
        });        // Configure custom cells for "Actions" and "Delete" columns
        configurerCellulesActions();
        //configurerCellulesDelete();
        // Load data from database and display in table
        chargerDonnees();
    }

    // Méthode pour charger les données depuis la base de données et les afficher dans la table
    private void chargerDonnees() {
        // Récupérez les données de la base de données en utilisant votre méthode afficher()
        ArrayList<DonBienMateriel> donsBienMateriel = service.afficher();

        // Créez une liste observable à partir des données récupérées
        ObservableList<DonBienMateriel> observableDonsBienMateriel = FXCollections.observableArrayList(donsBienMateriel);

        // Ajoutez la liste observable à la table
        tableac.setItems(observableDonsBienMateriel);
    }

    // Méthode pour configurer les cellules personnalisées pour la colonne "Actions"




    private void configurerCellulesActions() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final JFXButton pdfButton = createIconButton(null, "/tn/esprit/jardindart/icons/pdf.png",20);
            private final JFXButton editButton = createIconButton(null, "/tn/esprit/jardindart/icons/edit.png",20);
            private final JFXButton deleteButton = createIconButton(null, "/tn/esprit/jardindart/icons/delete.png",20);
            private final JFXButton verifyB = createIconButton(null, "/tn/esprit/jardindart/icons/activate.png",20);

            {
                pdfButton.setOnAction(event -> {
                    DonBienMateriel don = getTableView().getItems().get(getIndex());
                     generatePDF(don);
                    System.out.println("PDF generated for Don ID: " + don.getId());
                });

                editButton.setOnAction(event -> {
                    DonBienMateriel don = getTableView().getItems().get(getIndex());
                    // Action à effectuer lors du clic sur l'icône "Edit"
                    System.out.println("Edit clicked for Don ID: " + don.getId());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/jardindart/DBM/UpdateDBMback.fxml"));
                        Parent root = loader.load();
                        UpdateDBM controller = loader.getController();
                        controller.initialize(don.getId());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                deleteButton.setOnAction(event -> {
                    DonBienMateriel don = getTableView().getItems().get(getIndex());
                    service.supprimer(don.getId());
                    getTableView().getItems().remove(don);
                    System.out.println("Delete clicked for Don ID: " + don.getId());
                });

                verifyB.setOnAction(event -> {
                    DonBienMateriel d = getTableView().getItems().get(getIndex());
                    // Appelez la méthode du service pour mettre à jour le statut
                    // Par exemple, si vous avez une instance de ProdRService nommée prodRService
                    service.updateStatus(d, true); // Mettez le nouveau statut ici (true ou false)
                    // Rafraîchissez la vue si nécessaire
                    getTableView().refresh();
                    // Affichez un message ou effectuez d'autres actions si nécessaire
                    System.out.println("Statut mis à jour pour Don ID: " + d.getId());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(pdfButton, editButton,deleteButton,verifyB));
                }
            }
        });
    }



    private JFXButton createIconButton(String text, String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);  // Set the width of the icon
        iconView.setFitHeight(iconSize); // Set the height of the icon
        button.setGraphic(iconView);
        button.setButtonType(ButtonType.RAISED);
        return button;
    }

    private void generatePDF(DonBienMateriel dbm) {
        try (StringWriter writer = new StringWriter()) {
            // Générer le contenu HTML pour le PDF
            writer.write("<html><head><style>");
            writer.write("h1 { color: #058C4B; }");
            writer.write("body { font-family: Montserrat; }");
            writer.write("p { margin-bottom: 10px; }");
            writer.write("</style></head><body>");
            writer.write("<h1>Material goods donation details : </h1>");
            writer.write("<p>Donation ID: " + dbm.getId() + "</p>");
            writer.write("<p>Pic : " + dbm.getPhoto_don() + "</p>");
            writer.write("<p>Description : " + dbm.getDesc_don() + "</p>");
            writer.write("<p>Organization: " + dbm.getNom_assoc() + "</p>");
            writer.write("<p>User : " + dbm.getNom_ut() + "</p>");
            writer.write("<p>Verified ? " + dbm.isStatut_don() + "</p>");
            writer.write("</body></html>");

            // Créer le rendu PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(writer.toString());
            renderer.layout();


            // Enregistrer le PDF dans un fichier
            try (OutputStream os = new FileOutputStream("don_" + dbm.getId() + ".pdf")) {
                renderer.createPDF(os);
            }

            // Ouvrir le PDF avec le lecteur PDF par défaut
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File("don_" + dbm.getId() + ".pdf"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
