package tn.esprit.jardindart.controllers.DBM;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateDBM {

    @FXML
    private TextField photoDonField;

    @FXML
    private TextArea descriptionDonArea;

    @FXML
    private ComboBox<Association> associationComboBox;
    private int donIdToUpdate;

    private File selectedImageFile;
    @FXML
    private ImageView imageid;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label associationErrorLabel;


    @FXML
    public void initialize(int donIdToUpdate) {
        // Fill the ComboBox with available associations
        this.donIdToUpdate = donIdToUpdate;
        fillAssociationComboBox();
        populateFields();
    }

    private void populateFields() {
        DonBienMaterielService donService = new DonBienMaterielService();
        DonBienMateriel donToUpdate = donService.getById(donIdToUpdate); // Remplacez getById par la méthode que vous utilisez pour récupérer un don par son ID


        // Populate the fields with the fetched data
        if (donToUpdate != null) {
            //photoDonField.setText(donToUpdate.getPhoto_don());
            descriptionDonArea.setText(donToUpdate.getDesc_don());
            File imageFile = new File(donToUpdate.getPhoto_don());
            Image image = new Image(imageFile.toURI().toString());
            imageid.setImage(image);
              int associationId = donToUpdate.getId_association();
            for (Association association : associationComboBox.getItems()) {
                if (association.getId() == associationId) {
                    associationComboBox.setValue(association);
                    break;
                }
            }
        }
    }

    private DonBienMateriel fetchDonDetailsFromDatabase(int donId) {
        // Retrieve all material goods donations from the database
        DonBienMaterielService donServ = new DonBienMaterielService();
        ArrayList<DonBienMateriel> allDonations = donServ.afficher();


        // Iterate through the list of donations to find the one with the specified ID
        for (DonBienMateriel donation : allDonations) {
            if (donation.getId() == donId) {
                // If a donation with the specified ID is found, return it
                return donation;
            }
        }

        // If no donation with the specified ID is found, return null
        return null;
    }

    private void fillAssociationComboBox() {
        List<Association> associations = retrieveAssociationsFromDatabase();

        // Configure the ComboBox to display the association name using a StringConverter
        associationComboBox.setConverter(new StringConverter<Association>() {
            @Override
            public String toString(Association association) {
                return association != null ? association.getNom_association() : null;
            }

            @Override
            public Association fromString(String string) {
                return null; // You don't need this method for a selection ComboBox
            }
        });

        // Add the associations to the ComboBox
        associationComboBox.getItems().addAll(associations);
    }

    private List<Association> retrieveAssociationsFromDatabase() {
        AssociationService associationService = new AssociationService();
        ArrayList<Association> associations = associationService.afficher();
        return associations;
    }


    @FXML
    void handleUpdateButton() {
        Association selectedAssociation = associationComboBox.getValue();
        String photoDon = selectedImageFile != null ? selectedImageFile.getPath() : ""; // Par défaut, utiliser l'ancienne URL de l'image
        String descriptionDon = descriptionDonArea.getText();

        boolean hasError = false;
        if (descriptionDon.isEmpty()) {
            descriptionErrorLabel.setText("Il faut remplir ce champ");
            hasError = true;
        } else {
            descriptionErrorLabel.setText("");
        }

        if (selectedAssociation == null) {
            associationErrorLabel.setText("Il faut remplir ce champ");
            hasError = true;
        } else {
            associationErrorLabel.setText("");
        }

        if (hasError) {
            showAlert("Erreur", "Veuillez remplir tous les champs du formulaire.");
        } else {
            try {
                DonBienMaterielService donService = new DonBienMaterielService();
                DonBienMateriel donToUpdate = donService.getById(donIdToUpdate);

                if (donToUpdate != null) {
                    // Vérifier si une nouvelle image a été sélectionnée
                    if (selectedImageFile != null) {
                        // Si oui, mettre à jour l'URL de l'image
                        donToUpdate.setPhoto_don(photoDon);
                    }

                    // Mettre à jour les autres champs du don
                    donToUpdate.setDesc_don(descriptionDon);
                    donToUpdate.setId_association(selectedAssociation.getId());
                    donService.modifier(donToUpdate);
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/jardindart/DBM/card_view.fxml"));
                    descriptionDonArea.getScene().setRoot(fxmlLoader.load());

                    // Afficher le message après que le don ait été modifié
                    showAlert("Succès", "Le don a été modifié avec succès.");
                } else {
                    showAlert("Erreur", "Le don à modifier n'a pas été trouvé dans la base de données.");
                }
            } catch (Exception e) {
                // Afficher un message d'erreur en cas d'exception
                showAlert("Erreur", "Une erreur s'est produite lors de la modification du don : " + e.getMessage());
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
