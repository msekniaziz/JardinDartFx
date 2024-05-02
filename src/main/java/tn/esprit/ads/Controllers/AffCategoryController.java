package tn.esprit.ads.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tn.esprit.ads.Controllers.CardCatController;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.Services.Scategories;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class AffCategoryController implements Initializable {

    @FXML
    private TextField TFSearch;
    @FXML
    private Button affCategory;
    @FXML
    private Button back;
    @FXML
    private ImageView SearchButtonUserClick;
    @FXML
    private ImageView imageuser;

    @FXML
    private Button brtn_DeleteAll;

    @FXML
    private Button btn_DeleteSelected;

    @FXML
    private Button btn_Edit;

    @FXML
    private Button btn_Refresh;

    @FXML
    private Button btn_imprimer;
    @FXML
    private Button affOrder;
    @FXML
    private Button affAds;

    @FXML
    private FlowPane flowPaneLCat;

    @FXML
    private TextField keyCat;

    @FXML
    private AnchorPane selectedCard;

    @FXML
    private Rectangle montant;

    @FXML
    private TextField nameCat;

    @FXML
    private HBox recherche_avance;

    @FXML
    private Button sort;

    private Categories selectedCategory;
    Scategories catt = new Scategories();
    Sannonces ads = new Sannonces();

    @FXML
    Categories cat;
    private FXMLLoader CardLoader;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chargement des données de facture...");
        Scategories serviceCat = new Scategories();
        loadCartData();

        System.out.println("Chargement des données de category...");
        Image image1 = new Image("file:src/main/java/tn/esprit/ads/img/jimmy-fallon.png");
        imageuser.setImage(image1);


    }

    private List<Categories> getCat() {
        Scategories serviceCat = new Scategories();
        return serviceCat.getAll();
    }

    private void updateCatCards() {
        for (Node node : flowPaneLCat.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane card = (AnchorPane) node;
                CardCatController controller = (CardCatController) card.getProperties().get("controller");
                Categories categories = controller.getCat();
                if (categories.isSelected()) {
                    card.setStyle("-fx-background-color: lightblue;");
                } else {
                    card.setStyle("-fx-background-color: white;");
                }
            }
        }
    }

    void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    void loadCartData() {
        List<Categories> categories = catt.getAll();

        flowPaneLCat.getChildren().clear();
        for (Categories cat : categories) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardCat.fxml"));
                AnchorPane card = loader.load();
                CardCatController controller = loader.getController();
                controller.initialize(cat); // Initialise la carte avec les données de catégorie
                card.setUserData(cat); // Définit les données utilisateur de la carte comme la catégorie
                flowPaneLCat.getChildren().add(card);
                card.setOnMouseClicked(this::onCatSelected);

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des données de catégorie.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onCatSelected(MouseEvent event) {
        selectedCard = (AnchorPane) event.getSource();

        Object userData = selectedCard.getUserData();
        System.out.println(userData);
        if (userData instanceof Categories) {
            selectedCategory = (Categories) userData;
            System.out.println("Annonce sélectionnée : " + selectedCategory.getName());
            initData(selectedCategory);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
        }

    }


    void initData(Categories selectedCat) {
      /*  System.out.println("init data");
            nameCat.setText(selectedCat.getName());
            System.out.println(selectedCat.getName());
            keyCat.setText(String.valueOf(selectedCat.getKey_cat()));
            System.out.println(selectedCat.getId());*/
    }


    @FXML
    void DeleteAllCat(ActionEvent event) {
        Scategories cat = new Scategories();
        boolean success = cat.deleteAll();
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "tous les Factures sont supprimés!");
            loadCartData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "erreur.");
        }


    }

    @FXML
    void DeleteCatSelected(ActionEvent event) {
        catt.delete(selectedCategory);
        loadCartData(); // Recharger les données de categorie
    }



    @FXML
    void EditCat(ActionEvent event) {
        if (selectedCategory != null) {
            try {
                // Récupération des valeurs saisies
                String newName = nameCat.getText();
                String newKey = keyCat.getText();

                // Expression régulière pour vérifier si le nom ne contient que des lettres et des espaces
                String nameRegex = "^[a-zA-Z\\s]+$";

                // Expression régulière pour vérifier si la clé ne contient que des chiffres
                String keyRegex = "^[0-9]+$";

                // Vérifier si le nom de la catégorie est vide
                if (newName.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie ne peut pas être vide.");
                    return;
                }

                // Vérifier si le nom de la catégorie correspond au format requis
                if (!newName.matches(nameRegex)) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la catégorie doit contenir uniquement des lettres et des espaces.");
                    return;
                }

                // Vérifier si la clé de la catégorie est vide
                if (newKey.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de la catégorie ne peut pas être vide.");
                    return;
                }

                // Vérifier si la clé de la catégorie correspond au format requis
                if (!newKey.matches(keyRegex)) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La clé de la catégorie doit être un nombre entier.");
                    return;
                }

                // Mise à jour des attributs de la catégorie avec les nouvelles valeurs
                selectedCategory.setName(newName);
                selectedCategory.setKey_cat(Integer.parseInt(newKey));

                // Appel de la méthode de mise à jour du service de catégorie
                catt.update(selectedCategory);

                // Rafraîchir la liste des catégories pour afficher les modifications
                loadCartData();

                // Afficher un message de succès
                showAlert(Alert.AlertType.INFORMATION, "Succès", "La catégorie a été mise à jour avec succès.");

            } catch (NumberFormatException e) {
                // En cas d'erreur de conversion de type, afficher un message d'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs numériques valides pour la clé de catégorie.");
            } catch (Exception e) {
                // En cas d'erreur, afficher un message d'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour de la catégorie : " + e.getMessage());
            }
        }
    }


    @FXML
    void Imprimez(ActionEvent event) {

    }


    @FXML
    void RefrecheListe(ActionEvent event) {
        loadCartData();
    }

    @FXML
    void back(ActionEvent event) {
        try {
            System.out.println("1");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("addCategory.fxml")));
            System.out.println("2");
            Scene scene = new Scene(root);
            System.out.println("3");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            System.out.println("4");
            stage.setScene(scene);
            System.out.println("5");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void recherche_avance(ActionEvent event) {

    }


    @FXML
    void sortByName(ActionEvent event) {
        List<Categories> categories = getCat();

        // Tri des livraisons par Prix en utilisant Java Streams

        categories = categories.stream()
                .sorted(Comparator.comparing(Categories::getName))
                .collect(Collectors.toList());

        // Recharger les données de livraison triées
        flowPaneLCat.getChildren().clear();
        for (Categories categories1 : categories) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardCat.fxml"));
                AnchorPane card = loader.load();
                CardCatController controller = loader.getController();
                controller.initialize(categories1);
                flowPaneLCat.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void affCategory(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Add(ActionEvent actionEvent) {
    }

    @FXML
    void affOrder(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affOrder.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void affAds(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("affAdsBack.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.esprit.ads.Controllers.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
