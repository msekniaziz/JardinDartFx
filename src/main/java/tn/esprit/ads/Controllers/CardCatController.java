package tn.esprit.ads.Controllers;
import tn.esprit.ads.Entity.Categories;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import java.net.URL;
import java.util.ResourceBundle;
import tn.esprit.ads.Controllers.AffCategoryController;

public class CardCatController {

    @FXML
    private FlowPane flowPaneLCat;


    @FXML
    private Label Key;

    @FXML
    private Label Name;

    @FXML
    private Label Number;

    @FXML
    private VBox V1;

    @FXML
    private AnchorPane V2;
    private Categories cat ;


    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /*@FXML
    public void onCatSelected(MouseEvent event) {
        // Récupérer la carte de livraison cliquée
        AnchorPane CatCard = (AnchorPane) event.getSource();

        // Mettre à jour l'état de sélection de la carte
        setSelected(!isSelected());

        // Mettre à jour l'apparence de la carte en fonction de l'état de sélection
        CatCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        // Désélectionner les autres cartes
        for (int i = 0; i < flowPaneLCat.getChildren().size(); i++) {
            AnchorPane node = (AnchorPane) flowPaneLCat.getChildren().get(i);
            if (node != CatCard ){
                CardCatController otherController = (CardCatController)node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }
    }*/
    @FXML
    public void onCatSelected(MouseEvent event) {
        // Récupérer la carte de catégorie cliquée
        AnchorPane catCard = (AnchorPane) event.getSource();

        // Mettre à jour l'état de sélection de la carte
        setSelected(!isSelected());

        // Mettre à jour l'apparence de la carte en fonction de l'état de sélection
        catCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        // Désélectionner les autres cartes
        for (int i = 0; i < flowPaneLCat.getChildren().size(); i++) {
            AnchorPane node = (AnchorPane) flowPaneLCat.getChildren().get(i);
            if (node != catCard) {
                CardCatController otherController = (CardCatController) node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }

        // Appeler la méthode initData du contrôleur principal pour mettre à jour les champs nameCat et keyCat
        AffCategoryController controller = (AffCategoryController) flowPaneLCat.getParent().getProperties().get("controller");
        controller.initData(cat);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de FactureCardController...");
    }

    public void initialize(Categories cat) {
        if (cat == null) {
            System.err.println("Erreur: instance de Facture est nulle");
            return;
        }

        this.cat = cat;
        Name.setText("Name:" + cat.getName());
        Key.setText("key : " + cat.getKey_cat());
        Number.setText("Number: " + cat.getNbr_annonce());

    }

    public Categories getCat() {
        return cat;
    }
}


