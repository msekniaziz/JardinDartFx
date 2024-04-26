package tn.esprit.pifx.controllers.ptCollect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.models.TypeDispo;
import tn.esprit.pifx.services.PtCollectService;
import tn.esprit.pifx.services.TypeDispoService;
import tn.esprit.pifx.test.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditPtCollect {

    @FXML
    private TextField nomPcField, adressePcField, latitudePcField, longitudePcField;

    @FXML
    private CheckComboBox<TypeDispo> typeComboBox;

    private PtCollectService ptCollectService;
    private int id;

    public EditPtCollect() {
    }

    public void initialize(int id) {
        this.id = id;
        ptCollectService = new PtCollectService();
        fillTypeComboBox();
        populateFields();
    }

    private void populateFields() {
        PtCollect ptCollect = ptCollectService.getById(id);

        if (ptCollect != null) {
            nomPcField.setText(ptCollect.getNomPc());
            adressePcField.setText(ptCollect.getAdressePc());
            latitudePcField.setText(String.valueOf(ptCollect.getLatitudePc()));
            longitudePcField.setText(String.valueOf(ptCollect.getLongitudePc()));

            List<TypeDispo> associatedTypes = ptCollect.getType();
            if (associatedTypes != null) {
                for (TypeDispo type : associatedTypes) {
                    typeComboBox.getCheckModel().check(type);
                }
            }
        }
    }

    private void fillTypeComboBox() {
        List<TypeDispo> typeDispoList = retrieveTypeFromDatabase();

        typeComboBox.setConverter(new StringConverter<TypeDispo>() {
            @Override
            public String toString(TypeDispo typeDispo) {
                return typeDispo != null ? typeDispo.getNom() : null;
            }

            @Override
            public TypeDispo fromString(String string) {
                return null;
            }
        });

        typeComboBox.getItems().addAll(typeDispoList);
    }

    private List<TypeDispo> retrieveTypeFromDatabase() {
        TypeDispoService ts = new TypeDispoService();
        return ts.recuperer();
    }

    @FXML
    public void modifierPtCollect(ActionEvent actionEvent) {
        if (!validerSaisie()) {
            return;
        }

        String nomPc = nomPcField.getText();
        String adressePc = adressePcField.getText();
        Float latitudePc = Float.parseFloat(latitudePcField.getText());
        Float longitudePc = Float.parseFloat(longitudePcField.getText());
        List<TypeDispo> selectedType = typeComboBox.getCheckModel().getCheckedItems();

        PtCollect ptCollect = new PtCollect(id, nomPc, adressePc, latitudePc, longitudePc, (List<TypeDispo>) selectedType);
        ptCollectService.modifier(ptCollect);
        ptCollectService.ajouterAvecType(ptCollect);
        afficherAlerte(Alert.AlertType.INFORMATION, "Succes", "Point de collecte modifié avec succès.");
    }

    private boolean validerSaisie() {
        String nomPc = nomPcField.getText();
        if (nomPc.isEmpty() || nomPc.length() < 3 || nomPc.length() > 12) {
            afficherAlerte(Alert.AlertType.ERROR, "Error", "Le nom du point de collecte doit contenir entre 3 et 12 caractères.");
            return false;
        }

        String latitudeStr = latitudePcField.getText();
        if (!estNombreFloat(latitudeStr)) {
            afficherAlerte(Alert.AlertType.ERROR, "Error", "La latitude doit être un nombre décimal.");
            return false;
        }

        String longitudeStr = longitudePcField.getText();
        if (!estNombreFloat(longitudeStr)) {
            afficherAlerte(Alert.AlertType.ERROR, "Error", "La longitude doit être un nombre décimal.");
            return false;
        }

        return true;
    }

    private void afficherAlerte(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean estNombreFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
