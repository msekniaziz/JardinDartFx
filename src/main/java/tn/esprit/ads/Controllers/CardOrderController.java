package tn.esprit.ads.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Entity.Commandes;
import javafx.scene.layout.FlowPane;

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
    private AnchorPane View2;

    @FXML
    private Label dateLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label stateLabel;
    private Commandes com;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @FXML
    void onOrderSelected(MouseEvent event) {
        // Récupérer la carte de livraison cliquée
        AnchorPane orderCard = (AnchorPane) event.getSource();

        // Mettre à jour l'état de sélection de la carte
        setSelected(!isSelected());

        // Mettre à jour l'apparence de la carte en fonction de l'état de sélection
        orderCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        // Désélectionner les autres cartes
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
}
