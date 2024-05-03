package tn.jardindart.controllers.prodR;

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
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.jardindart.controllers.user.EmailSender;
import tn.jardindart.controllers.user.UserController;
import tn.jardindart.models.ProdR;
import tn.jardindart.services.ProdRService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ListProdR {

    @FXML
    private TableView<ProdR> prodRTable;

    @FXML
    private TableColumn<ProdR, Integer>   nomCentreTriColumn;
    @FXML
    private TableColumn<ProdR, String>  statutColumn;

    @FXML
    private TableColumn<ProdR, String> descriptionColumn, nomPColumn, justificatifColumn, nomUtilisateurColumn, nomTypeProdColumn;

    @FXML
    private TableColumn<ProdR, Void> actionsColumn;

    private ProdRService prodRService;

    public void initialize() {
        prodRService = new ProdRService();
        afficherListeProdR();
        configurerCellulesActions();
    }

    private static void generatePDF(ProdR prodR) throws IOException {
        // Nom du fichier PDF
        String fileName = "prodR_"+prodR.getNomP()+"_"+prodR.getId()+ ".pdf";

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

        // Ajouter le titre du document
        Color titleColor = new DeviceRgb(34, 139, 34);
        PdfFont titleFont = PdfFontFactory.createFont();
        Paragraph title = new Paragraph("Recycling Products Details");
        title.setFont(titleFont);
        title.setFontSize(18f);
        title.setFontColor(titleColor);
        title.setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Ajouter la section des détails du produit
        document.add(new Paragraph("ProdR ID: " + prodR.getId()));
        document.add(new Paragraph("Product name: " + prodR.getNomP()));
        document.add(new Paragraph("Product type: " + prodR.getNomTypeProd()));
        document.add(new Paragraph("Username: " + prodR.getNomUtilisateur()));
        document.add(new Paragraph("Collect point: " + prodR.getNomPtcId()));
      if( prodR.getStatut()==true){
          document.add(new Paragraph("Status: " + "Verified").setFontColor(titleColor));
      }else {
          document.add(new Paragraph("Status: " + "Not Verified yet"));
      }
        // Ajouter une séparation visuelle
        document.add(line.setStrokeWidth(130));
//        document.add(line);
//        document.add(line);
//        document.add(line);
//        document.add(line);

        document.add(new Paragraph("Justificatif: " ).setBold().setUnderline().setTextAlignment(TextAlignment.CENTER));


        // Ajouter l'image du justificatif
        com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(ImageDataFactory.create(prodR.getJustificatif()));
        image.setMarginTop(10);
        image.setMarginBottom(40);
        image.setHeight(300);
        image.setWidth(500);
        document.add(image);

        // Ajouter une séparation visuelle
        document.add(line);
        document.add(new Paragraph("| JARDIN D'ART  | JARDIN D'ART  | JARDIN D'ART  |  JARDIN D'ART | JARDIN D'ART |" ));

        // Fermer le document
        document.close();

        // Ouvrir le PDF avec l'application par défaut
        try {
            File file = new File(fileName);
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PDF generated successfully.");
    }

    private void afficherListeProdR() {
        List<ProdR> prodRList = prodRService.recuperer();
        ObservableList<ProdR> observableProdRList = FXCollections.observableArrayList(prodRList);

        nomUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
       // statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        statutColumn.setCellValueFactory(cellData -> {
            BooleanProperty statusProperty = new SimpleBooleanProperty(cellData.getValue().getStatut());
            // Convert boolean property to a string property
            StringProperty stringStatusProperty = new SimpleStringProperty(statusProperty.getValue() ? "Active" : "Inactive");
            // Bind the string property to the cell value
            statusProperty.addListener((observable, oldValue, newValue) -> {
                stringStatusProperty.set(newValue ? "Active" : "Inactive");
            });
            return stringStatusProperty;
        });
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomPColumn.setCellValueFactory(new PropertyValueFactory<>("nomP"));
        justificatifColumn.setCellValueFactory(new PropertyValueFactory<>("justificatif"));
        nomCentreTriColumn.setCellValueFactory(new PropertyValueFactory<>("nomPtcId"));
        nomTypeProdColumn.setCellValueFactory(new PropertyValueFactory<>("nomTypeProd"));

        prodRTable.setItems(observableProdRList);
    }

    private JFXButton createIconButton(String text, String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton(text);
        javafx.scene.image.Image iconImage = new javafx.scene.image.Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);  // Set the width of the icon
        iconView.setFitHeight(iconSize); // Set the height of the icon
        button.setGraphic(iconView);
        //button.setButtonType(ButtonType.RAISED);
        return button;
    }

    private void configurerCellulesActions() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final JFXButton verifyB = createIconButton(null, "/tn.jardindart/icons/done.png", 20);
            private final JFXButton unverifyB = createIconButton(null, "/tn.jardindart/icons/cancel.png", 20);
            private final JFXButton pdfButton = createIconButton(null, "/tn.jardindart/icons/pdf.png", 20);
            private final JFXButton deleteButton = createIconButton(null, "/tn.jardindart/icons/delete.png", 20);

            {
                pdfButton.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());
                    try {
                        generatePDF(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("PDF generated for ProdR ID: " + p.getId());
                });
                verifyB.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());
                    UserController u = new UserController();
                    String mail = u.getEmailById(p.getUserId());
                    System.out.println(mail);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Verification du produit");
                    alert.setContentText("Voulez-vous vraiment verifier cet élément ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        prodRService.updateStatus(p, true); // Mettez le nouveau statut ici (true ou false)
                        try {
                            EmailSender.sendEmail( mail, "PRODUCT REJECTED!", "Dear User, <br>"
                                    + "Your donation has been rejected, please stay up to date we will call you later. <br>"
                                    + "Thank you for your trust. <br><br>"
                                    + "The JARDIN D'ART Team");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        // Rafraîchissez la vue si nécessaire
                        getTableView().refresh();
                        // Affichez un message ou effectuez d'autres actions si nécessaire
                        System.out.println("Statut mis à jour pour ProdR ID: " + p.getId());
                    } else {
                        System.out.println("Suppression annulée pour l'ID de Don: " + p.getId());
                    }
                    // Appelez la méthode du service pour mettre à jour le statut
                    // Par exemple, si vous avez une instance de ProdRService nommée prodRService

                });
                unverifyB.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());
                    UserController u = new UserController();
                    String mail = u.getEmailById(p.getUserId());
                    System.out.println(mail);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Verification du produit");
                    alert.setContentText("Voulez-vous vraiment verifier cet élément ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        prodRService.updateStatus(p, false); // Mettez le nouveau statut ici (true ou false)
                        try {
                            EmailSender.sendEmail( mail, "PRODUCT APPROVED!", "Dear User, <br>"
                                    + "Your donation has been approved, please stay up to date we will call you later. <br>"
                                    + "Thank you for your trust. <br><br>"
                                    + "The JARDIN D'ART Team");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        // Rafraîchissez la vue si nécessaire
                        getTableView().refresh();
                        // Affichez un message ou effectuez d'autres actions si nécessaire
                        System.out.println("Statut mis à jour pour ProdR ID: " + p.getId());
                    } else {
                        System.out.println("Suppression annulée pour l'ID de Don: " + p.getId());
                    }
                    // Appelez la méthode du service pour mettre à jour le statut
                    // Par exemple, si vous avez une instance de ProdRService nommée prodRService

                });


                deleteButton.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Confirmation de la suppression");
                    alert.setContentText("Voulez-vous vraiment supprimer cet élément ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        prodRService.supprimer(p);
                        getTableView().getItems().remove(p);
                        System.out.println("Suppression effectuée pour l'ID de Don: " + p.getId());
                    } else {
                        System.out.println("Suppression annulée pour l'ID de Don: " + p.getId());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ProdR p = getTableView().getItems().get(getIndex());

                    if(p.getStatut()==true){
                    setGraphic(new HBox(unverifyB,pdfButton, deleteButton));}
else {
    setGraphic(new HBox(verifyB, pdfButton, deleteButton));}

                }
                }

        });
    }
}
