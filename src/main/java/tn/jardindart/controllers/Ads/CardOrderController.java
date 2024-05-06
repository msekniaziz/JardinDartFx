package tn.jardindart.controllers.Ads;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.jardindart.controllers.Menu;
import tn.jardindart.models.Categories;
import tn.jardindart.models.Commandes;
import javafx.scene.layout.FlowPane;
import tn.jardindart.services.Sannonces;
import tn.jardindart.services.Scommandes;
import tn.jardindart.utils.MyDataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.jar.Attributes;
import tn.jardindart.test.HelloApplication;

import static tn.jardindart.services.Sannonces.cnx;

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
    Menu menuc = new Menu();
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


    public void initialize(Commandes com) throws SQLException {
        if (com == null) {
            System.err.println("Erreur: instance de Facture est nulle");
            return;
        }
        int iduser = com.getId_user_c_id() ;
        this.com = com;
      //  Scommandes scommandes = new Scommandes() ;
        String nom = comm.recupereNom(iduser);
        name.setText("Name: " + nom);
        state.setText(String.valueOf("Status : " + com.getEtat()));
        dateLabel.setText(" "+com.getDate());

    }


    public Commandes getCom() {
        return com;
    }

    public void delete(ActionEvent actionEvent) {
        if ( comm== null) {
            System.err.println("Erreur : Sannonces non initialisée.");
            return;
        }

        if (com == null) {
            System.err.println("Erreur : Aucune annonce sélectionnée.");
            return;
        }
        comm.delete(com);
        try {
            menuc.navigateToFXML("/tn.jardindart/Ads/affOrder.fxml",delete);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /* @FXML
   public void delete(ActionEvent event) {
       System.out.println("wosslot hne");
       if (comm == null) {
           System.err.println("Erreur : Sannonces non initialisée.");
           return;
       }

       if (com == null) {
           System.err.println("Erreur : Aucune commande sélectionnée.");
           return;
       }

       System.out.println("kbaldelete");
       comm.delete(com);

       System.out.println("baeddelete");
       MainFx.updateCurrentView("/affOrder.fxml");
   }*/

}