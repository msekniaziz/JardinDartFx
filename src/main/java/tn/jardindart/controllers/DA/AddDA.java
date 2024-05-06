package tn.jardindart.controllers.DA;

import com.example.user1.HelloApplication;
import com.stripe.exception.StripeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.Association;
import tn.jardindart.models.DonArgent;
import tn.jardindart.services.AssociationService;
import tn.jardindart.services.DonArgentService;

import java.util.ArrayList;
import java.util.List;

public class AddDA {

/*    @FXML
    private ComboBox<String> montantComboBox;*/

    @FXML
    private ComboBox<Association> associationComboBox;

    @FXML
    private Label montantErrorLabel;
    @FXML
    private TextField montantField;

     private final String paymentIntentId;
    public AddDA() {
        this.paymentIntentId = null; // Initialize paymentIntentId
    }

    public AddDA(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    @FXML
    public void initialize(Association association) {
        fillAssociationComboBox();

        associationComboBox.setValue(association);
//        fillMontantComboBox();
        associationComboBox.setDisable(true);
        associationComboBox.setStyle("-fx-opacity: 1;");
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
    // Méthode pour remplir le ComboBox avec les montants disponibles
  /*  private void fillMontantComboBox() {
        montantComboBox.getItems().addAll("20", "50", "100","150","200","500","750","1000","1500","2000");
    }*/


    @FXML
    public void handleAddButton() {
        int id = SessionManager.getInstance().getUserFront();
        Association selectedAssociation = associationComboBox.getValue();
        String selectedMontant = montantField.getText();

        boolean hasError = false;

        // Vérifier si une association est sélectionnée
        if (selectedAssociation == null) {
            showAlert("Erreur", "Veuillez sélectionner une association.");
            return;
        }

        // Vérifier si un montant est sélectionné
        if (selectedMontant == null || selectedMontant.isEmpty()) {
            montantErrorLabel.setText("Il faut sélectionner un montant");
            hasError = true;
        } else {
            montantErrorLabel.setText("");
        }

        // Si aucune erreur, procéder à l'ajout
        if (!hasError) {
            try {
                double montant = Double.parseDouble(selectedMontant);

                StripePay stripePay = new StripePay();
                String paymentUrl = stripePay.createCheckoutSession(id, montant, selectedAssociation.getId()); // Envoyer le montant à StripePay
                stripePay.openBrowser(paymentUrl);

                // Wait for user interaction and then retrieve the paymentIntentId
                String paymentIntentId = stripePay.getPaymentIntentId();

                // Handle the payment event
                if (paymentIntentId != null) {
                    Boolean test = stripePay.handlePaymentEvent(paymentIntentId, (int) montant, id, selectedAssociation.getId());
                    if (test) {
                        DonArgent newDon = new DonArgent(id, montant, selectedAssociation.getId());
                        DonArgentService donArgentService = new DonArgentService();
                        donArgentService.ajouter(newDon);

                        showAlert("Succès", "Le don d'argent a été ajouté avec succès.");
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn.jardindart/DA/card_view.fxml"));
                        montantField.getScene().setRoot(fxmlLoader.load());
                    } else {
                        showAlert("Echec", "Erreur lors de la gestion de l'événement de paiement.");
                    }
                } else {
                    showAlert("Erreur", "Une erreur s'est produite lors de la création de la session de paiement.");
                }
            } catch (StripeException e) {
                showAlert("Erreur", "Une erreur s'est produite lors de la création de la session de paiement.");
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Une erreur s'est produite lors de la conversion du montant.");
            } catch (Exception e) {
                showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du don d'argent : " + e.getMessage());
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

    public String pid(String paymentIntentId) {
return  paymentIntentId;   }
}
