package tn.esprit.pifx.controllers.ptCollect;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.models.TypeDispo;
import tn.esprit.pifx.services.PtCollectService;
import tn.esprit.pifx.services.TypeDispoService;
import tn.esprit.pifx.test.HelloApplication;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddPtCollect {

    @FXML
    private TextField nomPcField,adressePcField,latitudePcField,longitudePcField;
    @FXML
    private Label typeComboBoxCommentLabel,longitudePcCommentLabel,latitudePcCommentLabel,nomPcCommentLabel;


    @FXML
    private CheckComboBox <TypeDispo> typeComboBox;

    private PtCollectService ptCollectService;
    private TypeDispoService typeDispoService;


    public void initialize() {
        ptCollectService = new PtCollectService();
        typeDispoService = new TypeDispoService();

        fillTypeComboBox();


    }

    private List<TypeDispo> retrieveTypeFromDatabase() {

        TypeDispoService ts = new TypeDispoService();
        ArrayList<TypeDispo> tl = (ArrayList<TypeDispo>) ts.recuperer();
        return tl;
    }

    private void fillTypeComboBox() {

        List<TypeDispo> tl = retrieveTypeFromDatabase();

        typeComboBox.setConverter(new StringConverter<TypeDispo>() {
            @Override
            public String toString(TypeDispo p) {
                return p != null ? p.getNom() : null;
            }

            @Override
            public TypeDispo fromString(String string) {
                return null;
            }
        });

        typeComboBox.getItems().addAll(tl);
    }

    @FXML
    private void ajouterPtCollect() {
        if (!validerSaisie()) {
            return; // Sortir de la méthode si la saisie n'est pas valide
        }

        String nomPc = nomPcField.getText();
        String adressePc = adressePcField.getText();
        Float latitudePc = Float.parseFloat(latitudePcField.getText());
        Float longitudePc = Float.parseFloat(longitudePcField.getText());
        List<TypeDispo> selectedTypes = typeComboBox.getCheckModel().getCheckedItems();

        // Ajouter le point de collecte pour obtenir l'ID auto-incrémenté
        PtCollect ptCollect = new PtCollect(nomPc, adressePc, latitudePc, longitudePc);
        ptCollectService.ajouter(ptCollect);

        int IdP = ptCollectService.recupererDernierIdPtCollect();
        // Créer un nouvel objet PtCollect avec l'ID et les types de disponibilité sélectionnés
        PtCollect ptCollectWithType = new PtCollect(IdP, nomPc, adressePc, latitudePc, longitudePc, selectedTypes);

        // Ajouter les types de disponibilité associés au point de collecte
        ptCollectService.ajouterAvecType(ptCollectWithType);

        afficherAlerte(Alert.AlertType.INFORMATION, "Succes", "Point de collecte ajouté avec succès.");

        viderChamps();
    }




    // Ajoutez la méthode validerSaisie pour vérifier la saisie des champs
    private boolean validerSaisie() {
        boolean isValid = true; // Variable pour vérifier la validité de la saisie

        // Validation du nom du point de collecte
        String nomPc = nomPcField.getText();
        if (nomPc.isEmpty() || nomPc.length() < 3 || nomPc.length() > 12) {
            nomPcCommentLabel.setText("Le nom doit contenir entre 3 et 12 caractères.");
            isValid = false;
        } else {
            nomPcCommentLabel.setText(""); // Effacer le message d'erreur si la saisie est valide
        }

        // Validation de l'adresse du point de collecte (peut être vide)

        // Validation de la latitude
        String latitudeStr = latitudePcField.getText();
        if (!estNombreFloat(latitudeStr)) {
            latitudePcCommentLabel.setText("La latitude doit être un nombre décimal.");
            isValid = false;
        } else {
            latitudePcCommentLabel.setText(""); // Effacer le message d'erreur si la saisie est valide
        }

        // Validation de la longitude
        String longitudeStr = longitudePcField.getText();
        if (!estNombreFloat(longitudeStr)) {
            longitudePcCommentLabel.setText("La longitude doit être un nombre décimal.");
            isValid = false;
        } else {
            longitudePcCommentLabel.setText(""); // Effacer le message d'erreur si la saisie est valide
        }

        // Validation du type de point de collecte
        if (typeComboBox.getCheckModel().isEmpty()) {
            typeComboBoxCommentLabel.setText("Veuillez sélectionner au moins un type.");
            isValid = false;
        } else {
            typeComboBoxCommentLabel.setText(""); // Effacer le message d'erreur si la saisie est valide
        }

        return isValid;
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



    private void viderChamps() {
        nomPcField.clear();
        adressePcField.clear();
        latitudePcField.clear();
        longitudePcField.clear();
    }
    @FXML
    public void afficherListePt(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pifx/ptCollect/ListPtCollect.fxml"));
        try {
            nomPcField.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
