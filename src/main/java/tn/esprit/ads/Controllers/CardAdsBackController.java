package tn.esprit.ads.Controllers;

import com.mysql.cj.jdbc.MysqlSQLXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.tools.MyDataBase;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CardAdsBackController implements Initializable {

    @FXML
    private Label category;

    @FXML
    private Label description;

    @FXML
    private ImageView imageView;

    @FXML
    private Label negotiable;

    @FXML
    private Label price;

    @FXML
    private Label title;
    private Connection con;

    private Annonces ads;
    @FXML
    private Button activate;
    @FXML
    private AnchorPane V2;

    private boolean selected = false;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de CardAdsController...");

    }

    public void initialize(Annonces ads) {

        if (ads == null) {
            System.err.println("Erreur: instance d'Annonces est nulle");
            return;
        }

        this.ads = ads;
        title.setText("Titre : " + ads.getTitle());
        description.setText("Description : " + ads.getDescription());
        price.setText("Prix : " + ads.getPrix());
        // Vérifier si l'annonce est négociable
        String negotiableStatus = ads.getNegiciable() != 0 ? "Negotiable" : "Not Negotiable";
        negotiable.setText("Négociable : " + negotiableStatus);

        //  negotiable.setText("Négociable : " + ads.getNegiciable());
        //  category.setText("Catégorie : " + ads.getCategory());

        // Charger l'image à partir d'un fichier
        Image image = new Image("file:" + ads.getImage());

        // Définir l'image chargée sur l'ImageView
        imageView.setImage(image);
        // Récupérer le nom de la catégorie à partir de l'id_cat_id
        int categoryId = ads.getId_Cat();
        String categoryName = new Sannonces().getCategoryName(categoryId);

        // Afficher le nom de la catégorie dans l'interface utilisateur
        category.setText("Catégorie : " + categoryName);
    }
    @FXML
    void onAdsSelected(MouseEvent event) {
        // Récupérer la carte de l'annonce cliquée
        AnchorPane adsCard = (AnchorPane) event.getSource();

        // Mettre à jour l'état de sélection de la carte
        setSelected(!isSelected());

        // Mettre à jour l'apparence de la carte en fonction de l'état de sélection
        adsCard.setStyle(isSelected() ? "-fx-background-color: lightblue;" : "");

        // Désélectionner les autres cartes
        for (int i = 0; i < adsCard.getParent().getChildrenUnmodifiable().size(); i++) {
            AnchorPane node = (AnchorPane) adsCard.getParent().getChildrenUnmodifiable().get(i);
            if (node != adsCard) {
                CardAdsController otherController = (CardAdsController) node.getProperties().get("controller");
                otherController.setSelected(false);
                node.setStyle("");
            }
        }
    }
    public Annonces getAds() {
        return ads;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {

        this.selected = selected;
    }
    public void setSelectedAds(Annonces selectedAds) {
        this.ads = selectedAds;
        title.setText(selectedAds.getTitle());
        description.setText(selectedAds.getDescription());
        price.setText(String.valueOf(selectedAds.getPrix()));
        negotiable.setText(selectedAds.isNegotiable() ? "Negotiable" : "Not Negotiable");
        category.setText(selectedAds.getCategory());
        Image image = new Image("file:" + selectedAds.getImage());
        imageView.setImage(image);
    }

    private void updateUserStatus(int annonceID, String newStatus) {
        try (
                Connection con = MyDataBase.getInstance().getCnx();
                PreparedStatement ps = con.prepareStatement("UPDATE annonces SET category = ? WHERE id = ?")) {
            ps.setString(1, newStatus);
            ps.setInt(2, annonceID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void gotoactivate(ActionEvent actionEvent) {

        Button button = (Button) actionEvent.getSource();
        AnchorPane cardView = (AnchorPane) button.getParent();
        Object userData = cardView.getUserData();
        if (userData instanceof Annonces) {
            Annonces annonce = (Annonces) userData;
            int id = annonce.getId();
            String category = null;
            try {
                    Sannonces sannonces = new Sannonces();
                ResultSet resultSet = Sannonces.checkdispo(id);
                System.out.println(resultSet);
                System.out.println("step 5");
                if (resultSet.next()) {
                    category = resultSet.getString("category");

                    if (category.equals("inactive")) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Confirm Account Activation");
                        alert.setContentText("Are you sure you want to activate this account?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            updateUserStatus(id, "active");
                        }
                    } else if (category.equals("active")) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Confirm Account Deactivation");
                        alert.setContentText("Are you sure you want to deactivate this account?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            updateUserStatus(id, "inactive");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }

}
