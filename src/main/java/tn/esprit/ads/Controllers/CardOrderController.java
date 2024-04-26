package tn.esprit.ads.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Entity.Commandes;
import javafx.scene.layout.FlowPane;
import tn.esprit.ads.Services.Scommandes;
import tn.esprit.ads.test.MainFx;

import java.awt.event.ActionEvent;
import java.util.jar.Attributes;

public class CardOrderController {
    @FXML
    private FlowPane flowPaneLOrder;
    @FXML
    private Label name;
    @FXML
    private Label state;

    @FXML
    private VBox View1;
    @FXML
    private Button delete;

    @FXML
    private AnchorPane View2;

    @FXML
    private Label dateLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label stateLabel;
    private Commandes com;
    Scommandes comm = new Scommandes();
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @FXML
    void onOrderSelected(MouseEvent event) {

        AnchorPane orderCard = (AnchorPane) event.getSource();


        setSelected(!isSelected());


        orderCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");


        for (int i = 0; i < flowPaneLOrder.getChildren().size(); i++) {
            AnchorPane node = (AnchorPane) flowPaneLOrder.getChildren().get(i);
            if (node != orderCard ){
                CardCatController otherController = (CardCatController)node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }

    }


    public void initialize(Commandes com) {
        if (com == null) {
            System.err.println("Erreur: instance de Facture est nulle");
            return;
        }

        this.com = com;
        name.setText("Name:" + com.getId());
        state.setText("key : " + com.getEtat());



    }
    public Commandes getCom() {
        return com;
    }



    public void delete(javafx.event.ActionEvent actionEvent) {
        if ( comm== null) {
            System.err.println("Erreur : Sannonces non initialisée.");
            return;
        }

        if (com == null) {
            System.err.println("Erreur : Aucune annonce sélectionnée.");
            return;
        }
        comm.delete(com);
        MainFx.updateCurrentView("/affOrder.fxml");

    }
}
