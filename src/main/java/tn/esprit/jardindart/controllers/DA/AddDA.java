package tn.esprit.jardindart.controllers.DA;

import com.stripe.exception.StripeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import tn.esprit.jardindart.controllers.Association.UpdateAssoc;
import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.models.DonArgent;
import tn.esprit.jardindart.services.AssociationService;
import tn.esprit.jardindart.services.DonArgentService;
import tn.esprit.jardindart.test.HelloApplication;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddDA {

    @FXML
    private TextField montantField;

    @FXML
    private ComboBox<Association> associationComboBox;
    @FXML
    private Label montantErrorLabel;
    @FXML
    private WebView webView;
    @FXML
    public void initialize(Association association) {

        fillAssociationComboBox();

        associationComboBox.setValue(association);
        associationComboBox.setDisable(true);
        associationComboBox.setStyle("-fx-opacity: 1;");


        // userIdComboBox.setValue("Default User ID");
    }


    // Méthode pour remplir le ComboBox avec les associations disponibles
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

    // Méthode pour récupérer la liste des associations à partir de la base de données
    private List<Association> retrieveAssociationsFromDatabase() {
        AssociationService associationService = new AssociationService();
        ArrayList<Association> associations = associationService.afficher();
        return associations;
    }

    @FXML
    void handleAddButton() {
        // Récupérer les données du formulaire
        String montantText = montantField.getText();
        Association selectedAssociation = associationComboBox.getValue();
        boolean hasError = false;
        if (montantField.getText().isEmpty()) {
            montantErrorLabel.setText("Il faut entrer un montant");
            hasError = true;
        } else {
            montantErrorLabel.setText("");
        }

        // Vérifier si les champs sont vides
        if (hasError) {
            showAlert("Erreur", "Veuillez remplir tous les champs du formulaire.");
        } else {
            try {
                double montant = Double.parseDouble(montantText);

                // Create an instance of StripePay
                StripePay stripePay = new StripePay();
                // Call createCheckoutSession() to get the payment URL
                String paymentUrl = stripePay.createCheckoutSession();
                // Open the browser with the payment URL
                stripePay.openBrowser(paymentUrl);

                DonArgent newDon = new DonArgent(montant, selectedAssociation.getId(), 27); // Remplacer 27 par l'ID de l'utilisateur actuellement connecté
                DonArgentService donArgentService = new DonArgentService();
                donArgentService.ajouter(newDon);

                showAlert("Succès", "Le don d'argent a été ajouté avec succès.");
                HelloApplication.updateCurrentView("/tn/esprit/jardindart/DA/card_view.fxml");

            } catch (NumberFormatException e) {
                showAlert("Erreur", "Veuillez entrer un montant valide pour le don.");
            } catch (StripeException e) {
                showAlert("Erreur", "Une erreur s'est produite lors de la création de la session de paiement.");
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du don d'argent : " + e.getMessage());
            }
        }
    }
    //addda

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
