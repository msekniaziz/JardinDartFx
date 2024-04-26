package tn.esprit.jardindart.controllers.DBM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.models.DonBienMateriel;
import tn.esprit.jardindart.services.AssociationService;

import tn.esprit.jardindart.services.DonBienMaterielService;
import tn.esprit.jardindart.test.HelloApplication;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class AddDBM {

    @FXML
    private TextField photoDonField;

    @FXML
    private TextArea descriptionDonArea;


    //private DatePicker dateDonPicker;

    @FXML
    private ComboBox<Association> associationComboBox;
    private File selectedImageFile;
    @FXML
    private ImageView imageid;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label associationErrorLabel;

    @FXML
    private Label imageErrorLabel;

   // private ComboBox<String> userIdComboBox;

private  DonBienMaterielService dbms;


    @FXML
    public void initialize(Association association) {
        // Remplir le ComboBox avec les associations disponibles
        fillAssociationComboBox();
dbms=new DonBienMaterielService();
        associationComboBox.setValue(association);
        associationComboBox.setDisable(true);
        associationComboBox.setStyle("-fx-opacity: 1;");


        // userIdComboBox.setValue("Default User ID");
    }


    private void fillAssociationComboBox() {

        List<Association> associations = retrieveAssociationsFromDatabase();

        // Configurez le ComboBox pour afficher le nom de l'association à l'aide d'une expression lambda
        associationComboBox.setConverter(new StringConverter<Association>() {
            @Override
            public String toString(Association association) {
                return association != null ? association.getNom_association() : null;
            }

            @Override
            public Association fromString(String string) {
                return null; // Vous n'avez pas besoin de cette méthode pour un ComboBox de sélection
            }
        });

        // Ajoutez les associations au ComboBox
        associationComboBox.getItems().addAll(associations);
    }



    // Méthode pour récupérer la liste des associations à partir de la base de données (ou de toute autre source de données)
    private List<Association> retrieveAssociationsFromDatabase() {

        AssociationService associationService = new AssociationService();
        ArrayList<Association> associations = associationService.afficher();
        return associations;
    }


    @FXML
    void handleAddButton() {
        // Récupérer les données du formulaire
        String photoDon = selectedImageFile != null ? selectedImageFile.getPath() : "";
        String descriptionDon = descriptionDonArea.getText();
        Association selectedAssociation = associationComboBox.getValue();

        // Vérifier si les champs sont vides
        boolean hasError = false;
        if (photoDon.isEmpty()) {
            imageErrorLabel.setText("Il faut une image");
            hasError = true;
        } else {
            imageErrorLabel.setText("");
        }
        if (descriptionDon.isEmpty()) {
            descriptionErrorLabel.setText("Il faut remplir ce champ");
            hasError = true;
        } else {
            descriptionErrorLabel.setText("");
        }


        if (hasError) {
            showAlert("Erreur", "Veuillez remplir tous les champs du formulaire.");
        } else {
            try {
                DonBienMateriel newDon = new DonBienMateriel(descriptionDon, photoDon, false, selectedAssociation.getId(), 27); // Remplacez 1 par l'ID de l'utilisateur actuellement connecté
                DonBienMaterielService donServ = new DonBienMaterielService();
                donServ.ajouter(newDon);
                //sendEmailConfirmation();

                // Afficher un message de succès
                showAlert("Succès", "Le don a été ajouté avec succès.");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DBM/card_view.fxml"));
                imageid.getScene().setRoot(fxmlLoader.load());
                sendEmailConfirmation();

            } catch (Exception e) {
                // Capturer et gérer l'exception
                showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du don : " + e.getMessage());
            }
        }
    }

    private void sendEmailConfirmation() {
        String to = "mohamedaziz.msekni@esprit.tn"; // Mettez l'adresse e-mail du destinataire ici
        String subject = "DONATION APPROVED!";
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


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void addImage(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "."));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            javafx.scene.image.Image image = new Image(selectedFile.toURI().toString());
            imageid.setImage(image);
        }
    }
}



