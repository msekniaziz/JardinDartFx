package tn.esprit.jardindart.controllers.Association;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.services.AssociationService;
import tn.esprit.jardindart.test.HelloApplication;

import java.io.File;

public class UpdateAssoc {

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

    private int associationIdToUpdate;
    private File selectedImageFile;
    @FXML
    private ImageView imageid;

    @FXML
    public void initialize(int associationIdToUpdate) {
        this.associationIdToUpdate = associationIdToUpdate;
        populateFields();
    }

    private void populateFields() {
        AssociationService associationService = new AssociationService();
        Association associationToUpdate = associationService.getById(associationIdToUpdate);

        if (associationToUpdate != null) {
            nomAssocField.setText(associationToUpdate.getNom_association());
            adresseField.setText(associationToUpdate.getAdresse_association());
            File imageFile = new File(associationToUpdate.getLogo_association());
            Image image = new Image(imageFile.toURI().toString());
            imageid.setImage(image);
            descriptionArea.setText(associationToUpdate.getDescription_asso());
            ribField.setText(String.valueOf(associationToUpdate.getRib()));
        }
    }

    @FXML
    void handleUpdateButton() {
        String name = nomAssocField.getText();
        String address = adresseField.getText();
        String logo = selectedImageFile != null ? selectedImageFile.getPath() : ""; // Utiliser le nouveau chemin si un nouveau fichier est sélectionné, sinon utiliser l'ancien chemin
        String description = descriptionArea.getText();
        int rib = Integer.parseInt(ribField.getText()); // Récupération du contenu de ribField en tant qu'entier

        if (name.isEmpty() || address.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs du formulaire.");
        } else {
            try {
                AssociationService associationService = new AssociationService();
                Association associationToUpdate = associationService.getById(associationIdToUpdate);
                if (associationToUpdate != null) {
                    associationToUpdate.setNom_association(name);
                    associationToUpdate.setAdresse_association(address);
                    associationToUpdate.setDescription_asso(description);
                    associationToUpdate.setRib(rib); // Mise à jour du rib
                    if(selectedImageFile != null) {
                        associationToUpdate.setLogo_association(logo); // Mise à jour de l'image seulement si un nouveau fichier est sélectionné
                    }
                    associationService.modifier(associationToUpdate);
                    showAlert("Succès", "L'association a été modifiée avec succès.");

                    // Rediriger vers un autre fichier FXML
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/Association/AfficherAssoc.fxml"));
                    nomAssocField.getScene().setRoot(fxmlLoader.load());
                } else {
                    showAlert("Erreur", "L'association à modifier n'a pas été trouvée dans la base de données.");
                }
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur s'est produite lors de la modification de l'association : " + e.getMessage());
            }
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
