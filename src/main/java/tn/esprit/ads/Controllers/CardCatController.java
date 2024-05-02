package tn.esprit.ads.Controllers;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;

import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Categories;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.Services.Scategories;
import tn.esprit.ads.Services.SpanierAnnonce;
import tn.esprit.ads.test.MainFx;

public class CardCatController {

    @FXML
    private FlowPane flowPaneLCat;


    @FXML
    private Label Key;

    @FXML
    private Label Name;
    private Annonces selectedAd;



    @FXML
    private Label Number;

    @FXML
    private VBox V1;

    @FXML
    private AnchorPane V2;
    @FXML
    private Button deleteC;

    @FXML
    private Button editC;
    @FXML
    private Button nbr;
    @FXML
    private TableColumn<Annonces, String> image;

    @FXML
    private TableColumn<Annonces, String> title;

    @FXML
    private TableColumn<Annonces, Double> price;
    @FXML
    private TableView<Annonces> paniertable;
    private Categories selectedCategory;
    Scategories catt =new Scategories();
    updateCategoryController p = new updateCategoryController();


    private Categories cat ;
    Sannonces ads = new Sannonces();


    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }



    @FXML
    public void onCatSelected(MouseEvent event) {
        AnchorPane catCard = (AnchorPane) event.getSource();

        setSelected(!isSelected());

        catCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");


        for (int i = 0; i < flowPaneLCat.getChildren().size(); i++) {
            AnchorPane node = (AnchorPane) flowPaneLCat.getChildren().get(i);
            if (node != catCard) {
                CardCatController otherController = (CardCatController) node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }


        selectedCategory = cat;


        AffCategoryController controller = (AffCategoryController) flowPaneLCat.getParent().getProperties().get("controller");
        controller.initData(cat);
    }

    @FXML
    void deleteC(ActionEvent event) {
        if ( catt== null) {
            System.err.println("Erreur : Sannonces non initialisée.");
            return;
        }

        if (cat == null) {
            System.err.println("Erreur : Aucune annonce sélectionnée.");
            return;
        }
        catt.delete(cat);
        MainFx.updateCurrentView("/affCategory.fxml");

    }
    public void initialize(URL url, ResourceBundle resourceBundle) throws IOException {
        System.out.println("Initialisation de FactureCardController...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/displayAdds.fxml"));
        Parent root = loader.load();


    }

    public void initialize(Categories cat) throws IOException {

        if (cat == null) {
            System.err.println("Erreur: instance de Facture est nulle");
            return;
        }


        this.cat = cat;
        Name.setText("Name:" + cat.getName());
        Key.setText("key : " + cat.getKey_cat());
        nbr.setText("Number Ads :"+cat.getNbr_annonce());


    }

    public Categories getCat() {
        return cat;
    }


   @FXML
   void editC(ActionEvent event) {
       if (cat != null) {
           try {

               FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateCat.fxml"));
               Parent root = loader.load();


               updateCategoryController editController = loader.getController();


               editController.initData(cat);

               Scene scene = new Scene(root);

               Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();


               Stage editStage = new Stage();
               editStage.setScene(scene);
               editStage.initOwner(currentStage);
               editStage.initModality(Modality.WINDOW_MODAL);


               editStage.show();
           } catch (IOException e) {
               e.printStackTrace();

               showAlert(Alert.AlertType.ERROR, "Error", "Failed to load edit form.");
           }
       }
   }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    @FXML
    void nbrAds(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/displayAdds.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de displayAdds.fxml
            DisplayAddsController displayAddsController = loader.getController();

            // Récupérer le TableView du contrôleur de displayAdds.fxml
            TableView<Annonces> paniertable = displayAddsController.getPaniertable();

            if (paniertable != null) {
                List<Annonces> panier = ads.getAllAdsByidcat(cat.getId()); // Remplacez 187 par l'ID de la catégorie souhaitée
                System.out.println("Chargement du category...");

                // Effacez les éléments existants avant d'ajouter de nouveaux éléments
                paniertable.getItems().clear();

                // Ajoutez les nouveaux éléments à la TableView
                paniertable.getItems().addAll(panier);

                // Configurez les cellules de la TableView avec les données des annonces
                displayAddsController.configureTableView();

                // Affichez le contenu FXML chargé (facultatif)
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Erreur : TableView paniertable n'est pas initialisée.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


