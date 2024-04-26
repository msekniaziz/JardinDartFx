package tn.esprit.pifx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.services.PtCollectService;

public class AddPtCollectController {

    @FXML
    private TextField nomPcField;

    @FXML
    private TextField adressePcField;

    @FXML
    private TextField latitudePcField;

    @FXML
    private TextField longitudePcField;

    private PtCollectService ptCollectService;

    public AddPtCollectController() {
        ptCollectService = new PtCollectService();
    }

    @FXML
    public void ajouterPtCollect() {
        String nomPc = nomPcField.getText();
        String adressePc = adressePcField.getText();
        float latitudePc = Float.parseFloat(latitudePcField.getText());
        float longitudePc = Float.parseFloat(longitudePcField.getText());

        PtCollect ptCollect = new PtCollect(nomPc, adressePc, latitudePc, longitudePc);
        ptCollectService.ajouter(ptCollect);

        // Ajoutez ici une logique pour afficher un message de confirmation ou effectuer d'autres actions après l'ajout du point de collecte
    }
}
