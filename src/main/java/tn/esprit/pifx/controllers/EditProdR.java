package tn.esprit.pifx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.services.ProdRService;

import java.awt.event.ActionEvent;

public class EditProdR {

    @FXML
    private TextField idField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField ptcIdField;

    @FXML
    private TextField typeProdIdField;

    @FXML
    private CheckBox statutCheckBox;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField nomPField;

    @FXML
    private TextField justificatifField;

    private ProdRService prodRService;

    public EditProdR() {
        prodRService = new ProdRService();
    }

    public void initData(ProdR prodR) {
        idField.setText(String.valueOf(prodR.getId()));
        userIdField.setText(String.valueOf(prodR.getUserId()));
        ptcIdField.setText(String.valueOf(prodR.getPtcId()));
        typeProdIdField.setText(String.valueOf(prodR.getTypeProdId()));
        descriptionArea.setText(prodR.getDescription());
        nomPField.setText(prodR.getNomP());
        justificatifField.setText(prodR.getJustificatif());
    }

    @FXML
    public void modifierProdR(ActionEvent actionEvent) {
        int id = Integer.parseInt(idField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        int ptcId = Integer.parseInt(ptcIdField.getText());
        int typeProdId = Integer.parseInt(typeProdIdField.getText());
        boolean statut = statutCheckBox.isSelected();
        String description = descriptionArea.getText();
        String nomP = nomPField.getText();
        String justificatif = justificatifField.getText();

        ProdR updatedProdR = new ProdR(userId, ptcId, typeProdId, 0, description, nomP, justificatif);
        prodRService.modifier(updatedProdR);

        // Vous pouvez ajouter ici une logique pour afficher un message de confirmation ou effectuer d'autres actions après la modification du ProdR
    }
}
