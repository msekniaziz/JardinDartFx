package tn.esprit.pifx.controllers.prodR;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.xhtmlrenderer.pdf.ITextRenderer;
import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.services.ProdRService;
import tn.esprit.pifx.test.HelloApplication;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ListProdR {

    @FXML
    private TableView<ProdR> prodRTable;

    @FXML
    private TableColumn<ProdR, Integer> userIdColumn ,ptcIdColumn,typeProdIdColumn,statutColumn,nomCentreTriColumn;

    @FXML
    private TableColumn<ProdR, String> descriptionColumn,nomPColumn,justificatifColumn,nomUtilisateurColumn,nomTypeProdColumn;

    @FXML
    private TableColumn<ProdR, Void> actionsColumn;

    private ProdRService prodRService;

    public void initialize() {
        prodRService = new ProdRService();
        afficherListeProdR();
        configurerCellulesActions();
    }

    private void generatePDF(ProdR prodR) {
        try (StringWriter writer = new StringWriter()) {
            // Générer le contenu HTML pour le PDF
            writer.write("<html><head><style>");
            writer.write("h1 { color: #058C4B; }");
            writer.write("body { font-family: Montserrat; }");
            writer.write("p { margin-bottom: 10px; }");
            writer.write("</style></head><body>");
            writer.write("<h1>RECYCLING PRODUCT -DETAILS : </h1>");
            writer.write("<p>ProdR ID: " + prodR.getId() + "</p>");
            writer.write("<p>Product name : " + prodR.getNomP() + "</p>");
            writer.write("<p>Product type : " + prodR.getNomTypeProd() + "</p>");
            writer.write("<p>Username: " + prodR.getNomUtilisateur() + "</p>");
            writer.write("<p>Collect point : " + prodR.getNomPtcId() + "</p>");
            writer.write("<p>Verified ? " + prodR.getStatut() + "</p>");
            writer.write("</body></html>");

            // Créer le rendu PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(writer.toString());
            renderer.layout();

            try (OutputStream os = new FileOutputStream("prodR_" + prodR.getId() + ".pdf")) {
                renderer.createPDF(os);
            }

            // Ouvrir le PDF avec le lecteur PDF par défaut
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File("prodR_" + prodR.getId() + ".pdf"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficherListeProdR() {
        List<ProdR> prodRList = prodRService.recuperer();
        ObservableList<ProdR> observableProdRList = FXCollections.observableArrayList(prodRList);

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ptcIdColumn.setCellValueFactory(new PropertyValueFactory<>("ptcId"));
        nomUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
        typeProdIdColumn.setCellValueFactory(new PropertyValueFactory<>("typeProdId"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomPColumn.setCellValueFactory(new PropertyValueFactory<>("nomP"));
        justificatifColumn.setCellValueFactory(new PropertyValueFactory<>("justificatif"));
        nomCentreTriColumn.setCellValueFactory(new PropertyValueFactory<>("nomPtcId"));
        nomTypeProdColumn.setCellValueFactory(new PropertyValueFactory<>("nomTypeProd"));

        prodRTable.setItems(observableProdRList);
    }

    private JFXButton createIconButton(String text, String iconFilePath, double iconSize) {
        JFXButton button = new JFXButton(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconFilePath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(iconSize);  // Set the width of the icon
        iconView.setFitHeight(iconSize); // Set the height of the icon
        button.setGraphic(iconView);
        //button.setButtonType(ButtonType.RAISED);
        return button;
    }

    private void configurerCellulesActions() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final JFXButton verifyB = createIconButton(null, "/tn/esprit/pifx/icons/done.png",20);
            private final JFXButton pdfButton = createIconButton(null, "/tn/esprit/pifx/icons/pdf.png",20);
            private final JFXButton editButton = createIconButton(null, "/tn/esprit/pifx/icons/edit.png",20);
            private final JFXButton deleteButton = createIconButton(null, "/tn/esprit/pifx/icons/delete.png",20);

            {
                pdfButton.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());
                    generatePDF(p);
                    System.out.println("PDF generated for ProdR ID: " + p.getId());
                });
                verifyB.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Verification du produit");
                    alert.setContentText("Voulez-vous vraiment verifier cet élément ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        prodRService.updateStatus(p, true); // Mettez le nouveau statut ici (true ou false)
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


                editButton.setOnAction(event -> {
                    ProdR p = getTableView().getItems().get(getIndex());
                    System.out.println("Edit clicked for ProdR ID: " + p.getId());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pifx/prodR/EditProdR.fxml"));
                        prodRTable.getScene().setRoot(fxmlLoader.load());
                        EditProdR controller = fxmlLoader.getController();
                        controller.initialize(p.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    setGraphic(new HBox(verifyB,pdfButton, editButton, deleteButton));
                }
            }
        });
    }
    private void sendEmailConfirmation() {
        String to = "mohamedaziz.msekni@esprit.tn"; // Mettez l'adresse e-mail du destinataire ici
        String subject = "PRODUCT APPROVED!";
        String body = "Dear User, <br>"
                + "Your donation has been approved, please stay up to date we will call you later. <br>"
                + "Thank you for your trust. <br><br>"
                + "The JARDIN D'ART Team";

        String host = "smtp.office365.com";
        String user = "lindafarah.trabelsi@esprit.tn"; // Votre adresse e-mail
        String password = "16242002*af"; // Votre mot de passe

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(body, "text/html");

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


}
