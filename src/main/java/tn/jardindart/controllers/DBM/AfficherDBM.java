package tn.jardindart.controllers.DBM;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
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
//import org.xhtmlrenderer.pdf.ITextRenderer;
import tn.jardindart.models.DonBienMateriel;
import tn.jardindart.models.ProdR;
import tn.jardindart.services.DonBienMaterielService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SplitPane;
import tn.jardindart.models.DonBienMateriel;
import tn.jardindart.services.DonBienMaterielService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

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

    @FXML
    private SplitPane splitPane;
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
        stat();

    }

    // Méthode pour charger les données depuis la base de données et les afficher dans la table
    private void stat() {
        Map<String, Integer> statisticsData = collectDonationStatistics();

        // Création du LineChart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistiques des dons de biens matériels par association");
        xAxis.setLabel("Associations");
        yAxis.setLabel("Nombre de dons");

        // Ajout des données au LineChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : statisticsData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        lineChart.getData().add(series);

        // Ajout du LineChart au SplitPane
        splitPane.getItems().add(lineChart);
    }
    private Map<String, Integer> collectDonationStatistics() {
        DonBienMaterielService service = new DonBienMaterielService();
        List<DonBienMateriel> donations = service.afficher();

        Map<String, Integer> statisticsData = new HashMap<>();
        for (DonBienMateriel donation : donations) {
            String association = donation.getNom_assoc();
            statisticsData.put(association, statisticsData.getOrDefault(association, 0) + 1);
        }

        return statisticsData;
    }


//    private Map<String, Integer> collectDonationStatistics() {
//        DonBienMaterielService service = new DonBienMaterielService();
//        List<DonBienMateriel> donations = service.afficher();
//
//        Map<String, Integer> statisticsData = new HashMap<>();
//        for (DonBienMateriel donation : donations) {
//            String association = donation.getNom_assoc();
//            statisticsData.put(association, statisticsData.getOrDefault(association, 0) + 1);
//        }
//
//        return statisticsData;
//    }

    private void chargerDonnees() {
        // Récupérez les données de la base de données en utilisant votre méthode afficher()
        ArrayList<DonBienMateriel> donsBienMateriel = service.afficher();

        // Créez une liste observable à partir des données récupérées
        ObservableList<DonBienMateriel> observableDonsBienMateriel = FXCollections.observableArrayList(donsBienMateriel);

        // Ajoutez la liste observable à la table
        tableac.setItems(observableDonsBienMateriel);
    }

    // Méthode pour configurer les cellules personnalisées pour la colonne "Actions"


    private static void generatePDF(DonBienMateriel prodR) throws IOException {
        // Nom du fichier PDF
        String fileName = "prodR_"+prodR.getUser_id()+"_"+prodR.getId()+ ".pdf";

        // Créer un objet ImageData pour l'image de fond
        ImageData logoData = ImageDataFactory.create("src/main/resources/tn.jardindart/Resources/logo_site.png");

        // Créer un nouveau document PDF
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));

        // Créer un objet Document pour le document PDF
        Document document = new Document(pdfDoc);

        // Définir la taille du document et les marges
        document.setMargins(50, 50, 50, 50);

        // Ajouter l'image de fond
        com.itextpdf.layout.element.Image logoImage = new com.itextpdf.layout.element.Image(logoData);
        logoImage.setHeight(100);
        logoImage.setWidth(140);
        logoImage.setMarginBottom(10);
        // Définir l'opacité (valeur entre 0 et 1)
        document.add(logoImage);

        // Ajouter une séparation visuelle
        LineSeparator line = new LineSeparator(new SolidLine());
        document.add(line);
        Color titleColor = new DeviceRgb(34, 139, 34);
        PdfFont titleFont = PdfFontFactory.createFont();
        Paragraph title = new Paragraph("DETAILS DU DON");
        title.setFont(titleFont);
        title.setFontSize(18f);
        title.setFontColor(titleColor);
        title.setTextAlignment(TextAlignment.CENTER);
        document.add(title);



        // Ajouter l'image du justificatif
        com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(ImageDataFactory.create(prodR.getPhoto_don()));
        image.setMarginTop(10);
        image.setMarginBottom(40);
        image.setHeight(300);
        image.setWidth(500);
        document.add(image);



        // Ajouter la section des détails du produit
        document.add(new Paragraph("DON ID: " + prodR.getId()));
        document.add(new Paragraph("DESCRIPTION: " + prodR.getDesc_don()));
        document.add(new Paragraph("ASSOCIATION: " + prodR.getNom_assoc()));
        document.add(new Paragraph("USERNAME: " + prodR.getNom_ut()));
        if( prodR.isStatut_don()==true){
            document.add(new Paragraph("STATUS: " + "Verified").setFontColor(titleColor));
        }else {
            document.add(new Paragraph("STATUS: " + "Not Verified yet"));
        }

        document.add(line);
        document.add(new Paragraph("| JARDIN D'ART  | JARDIN D'ART  | JARDIN D'ART  |  JARDIN D'ART | JARDIN D'ART |" ));
        document.close();

        try {
            File file = new File(fileName);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PDF generated successfully.");
    }


    private void configurerCellulesActions() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final JFXButton pdfButton = createIconButton(null, "/tn.jardindart/icons/pdf.png",20);
            private final JFXButton deleteButton = createIconButton(null, "/tn.jardindart/icons/delete.png",20);
            private final JFXButton verifyB = createIconButton(null, "/tn.jardindart/icons/done.png",20);
            private final JFXButton unverifyB = createIconButton(null, "/tn.jardindart/icons/cancel.png",20);

            {
                pdfButton.setOnAction(event -> {
                    DonBienMateriel don = getTableView().getItems().get(getIndex());
                    try {
                        generatePDF(don);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("PDF generated for Don ID: " + don.getId());
                });

                deleteButton.setOnAction(event -> {
                    DonBienMateriel don = getTableView().getItems().get(getIndex());
                    service.supprimer(don.getId());
                    getTableView().getItems().remove(don);
                    System.out.println("Delete clicked for Don ID: " + don.getId());
                });

                verifyB.setOnAction(event -> {
                    DonBienMateriel d = getTableView().getItems().get(getIndex());
                    service.updateStatus(d, true); // Mettez le nouveau statut ici (true ou false)
                    getTableView().refresh();
                    System.out.println("Statut mis à jour pour Don ID: " + d.getId());
                });
                unverifyB.setOnAction(event -> {
                    DonBienMateriel d = getTableView().getItems().get(getIndex());

                    service.updateStatus(d, false); // Mettez le nouveau statut ici (true ou false)
                    getTableView().refresh();
                    System.out.println("Statut mis à jour pour Don ID: " + d.getId());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DonBienMateriel p = getTableView().getItems().get(getIndex());

                    if(p.isStatut_don()==true){
                        setGraphic(new HBox(unverifyB,pdfButton, deleteButton));}
                    else {
                        setGraphic(new HBox(verifyB, pdfButton, deleteButton));}

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



}
