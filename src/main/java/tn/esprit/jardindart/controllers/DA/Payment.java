package tn.esprit.jardindart.controllers.DA;

import com.stripe.exception.StripeException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.jardindart.models.CardDetails;
import tn.esprit.jardindart.services.PaymentService;


public class Payment {
    @FXML
    private TextField numberField,expmField,expyField,cvcField;
    private PaymentService service = new PaymentService();
    @FXML
    private void handlePayButton() {





        try {
            CardDetails details = new CardDetails();

            details.setNumber(numberField.getText());
            details.setNumber(expmField.getText());
            details.setNumber(expyField.getText());
            details.setNumber(cvcField.getText());
            String chargeId = service.chargeCard(details);
            System.out.println(chargeId);
            

            showSuccessDialog(chargeId);

        } catch (StripeException e) {

            showErrorDialog(e);

        }
    }

    private void showSuccessDialog(String chargeId) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Paiement effectué");
        alert.setHeaderText("Votre paiement a été accepté");
        alert.setContentText("ID de transaction: " + chargeId);

        alert.showAndWait();

    }
    private void showErrorDialog(StripeException e) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de paiement");
        alert.setHeaderText("Le paiement a échoué");
        alert.setContentText(e.getMessage());

        alert.showAndWait();

    }

}
