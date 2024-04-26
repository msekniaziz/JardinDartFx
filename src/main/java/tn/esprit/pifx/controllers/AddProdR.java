package tn.esprit.pifx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.pifx.models.ProdR;
import tn.esprit.pifx.services.ProdRService;

import java.sql.SQLException;

public class AddProdR {

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

    public void initialize() {
        prodRService = new ProdRService(); // Initialisation de l'instance de ProdRService
    }

    @FXML
    public void ajouterProdR() {
        int userId = Integer.parseInt(userIdField.getText());
        int ptcId = Integer.parseInt(ptcIdField.getText());
        int typeProdId = Integer.parseInt(typeProdIdField.getText());
        String description = descriptionArea.getText();
        String nomP = nomPField.getText();
        String justificatif = justificatifField.getText();

        ProdR newProdR = new ProdR(userId, ptcId, typeProdId, 0, description, nomP, justificatif);

        try {
            prodRService.ajouter(newProdR);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Product added successfully");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    public void setProdRService(ProdRService prodRService) {
        this.prodRService = prodRService;
    }
}
