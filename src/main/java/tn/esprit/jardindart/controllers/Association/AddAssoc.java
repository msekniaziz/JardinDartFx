package tn.esprit.jardindart.controllers.Association;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.services.AssociationService;
import tn.esprit.jardindart.test.HelloApplication;
import tn.esprit.jardindart.controllers.Menu;

import java.io.File;

public class AddAssoc {

    @FXML
    private TextField nomAssocField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField logoField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField ribField;

    @FXML
    private Label nomAssocErrorLabel;

    @FXML
    private Label adresseErrorLabel;

    @FXML
    private Label logoErrorLabel;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label ribErrorLabel;
    private File selectedImageFile;
    @FXML
    private ImageView imageid;

    private final AssociationService associationService = new AssociationService();
    Menu menuc = new Menu();

    @FXML
    public void handleSubmit() {
        clearErrorLabels();

        String nomAssoc = nomAssocField.getText();
        String adresse = adresseField.getText();
        String logo = selectedImageFile != null ? selectedImageFile.getPath() : "";
        //String logo = logoField.getText();
        String description = descriptionArea.getText();
        String ribText = ribField.getText();

        boolean isValid = true;

        if (nomAssoc.isEmpty()) {
            nomAssocErrorLabel.setText("Il faut remplir ce champ");
            isValid = false;
        }

        if (adresse.isEmpty()) {
            adresseErrorLabel.setText("Il faut remplir ce champ");
            isValid = false;
        }



        if (description.isEmpty()) {
            descriptionErrorLabel.setText("Il faut remplir ce champ");
            isValid = false;
        }

        if (ribText.isEmpty()) {
            ribErrorLabel.setText("Il faut remplir ce champ");
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        boolean isRibValid = validateRib(ribText);

        if (!isRibValid) {
            ribErrorLabel.setText("Le RIB doit être un nombre entier de 10 chiffres");
            return;
        }

        boolean isNameUnique = isAssociationNameUnique(nomAssoc);

        if (!isNameUnique) {
            nomAssocErrorLabel.setText("Le nom de l'association existe déjà");
            return;
        }

        try {
            int rib = Integer.parseInt(ribText);
            Association nouvelleAssociation = new Association(nomAssoc, adresse, logo, description, rib);
            associationService.ajouter(nouvelleAssociation);
            showAlert("Succès", "L'association est ajoutée avec succès.");
            menuc.navigateToFXML("/tn/esprit/jardindart/Association/AfficherAssoc.fxml", nomAssocField);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'ajout de l'association : " + e.getMessage());
        }
    }

    private boolean validateRib(String rib) {
        return rib.matches("\\d{10}");
    }

    private boolean isAssociationNameUnique(String name) {
        return associationService.isAssociationNameUnique(name);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        nomAssocField.clear();
        adresseField.clear();
        logoField.clear();
        descriptionArea.clear();
        ribField.clear();
    }

    private void clearErrorLabels() {
        nomAssocErrorLabel.setText("");
        adresseErrorLabel.setText("");
        logoErrorLabel.setText("");
        descriptionErrorLabel.setText("");
        ribErrorLabel.setText("");
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
