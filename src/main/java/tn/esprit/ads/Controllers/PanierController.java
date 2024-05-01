package tn.esprit.ads.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Commandes;
import tn.esprit.ads.Entity.Panier;
import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.Services.Scommandes;
import tn.esprit.ads.Services.Spanier;
import tn.esprit.ads.Services.SpanierAnnonce;
import com.jfoenix.controls.JFXButton;
import tn.esprit.ads.test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.util.Callback;
import tn.esprit.ads.tools.MyDataBase;



public class PanierController {
    public static Connection cnx;


    @FXML
    private TableView<Annonces> paniertable;

    @FXML
    private TableColumn<Annonces, String> image;

    @FXML
    private TableColumn<Annonces, String> title;

    @FXML
    private TableColumn<Annonces, Double> price;

    @FXML
    private TableColumn<Annonces, Void> actions;
    @FXML
    private Button payer;

    @FXML
    private VBox panierItems;


    @FXML
    private Label totalLabel;

    private Annonces selectedAds;
    private Spanier pnss;
    Spanier pns = new Spanier();

    private Sannonces sannonces;
    Panier p;
    int id = 1;
    SpanierAnnonce pa = new SpanierAnnonce();
    Commandes com = new Commandes();


    public void initialize(int id) {
        System.out.println("Chargement des données de panier...");
        cnx = MyDataBase.getInstance().getCnx();
        this.id = id;
        afficherPanier();
        deleteAds();



    }
   /* private void afficherPanier() {

        List<Annonces> panier = pa.getCard(id);
        System.out.println("chfama ");
        paniertable.getItems().addAll(panier);
        image.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));



    }*/
   private void afficherPanier() {
       List<Annonces> panier = pa.getCard(id);
       System.out.println("Chargement du panier...");

       paniertable.getItems().clear();

       paniertable.getItems().addAll(panier);

       image.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
       title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
       price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));


       image.setCellFactory(column -> {
           return new TableCell<Annonces, String>() {
               private final ImageView imageView = new ImageView();
               @Override
               protected void updateItem(String imagePath, boolean empty) {
                   super.updateItem(imagePath, empty);
                   if (empty || imagePath == null) {
                       setGraphic(null);
                   } else {
                       System.out.println("src/main/java/tn/esprit/ads/ads.java : " + imagePath);
                       try {

                           Image image = new Image("file:" + imagePath);
                           imageView.setImage(image);
                           imageView.setFitWidth(50);
                           imageView.setFitHeight(50);
                           setGraphic(imageView);
                       } catch (Exception e) {
                           System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
                           setGraphic(null);
                       }
                   }
               }
           };
       });
   }

    private void showConfirmationDialog(String title, String content, Runnable action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(title);
        alert.setContentText(content);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                action.run();
            }
        });
    }

    private void deleteAds() {
        TableColumn<Annonces, Void> actionButtonColumn = new TableColumn<>("ACTIONS");


        Callback<TableColumn<Annonces, Void>, TableCell<Annonces, Void>> cellFactory = (TableColumn<Annonces, Void> param) -> {
            final TableCell<Annonces, Void> cell = new TableCell<Annonces, Void>() {
                private final Button actionButton = new Button("delete");//ki nheb nrodha ktiba new Button("delete");


                {
                    /*Image image1 = new Image(getClass().getResourceAsStream("/images/icons8_Box_32px.png"));
                    ImageView view = new ImageView(image1);
                    view.setFitWidth(30);
                    view.setFitHeight(30);
                    actionButton.setGraphic(view);*/

                    actionButton.setOnAction(event -> {

                        Annonces ads = getTableView().getItems().get(getIndex());
                        int id = ads.getId();
                        SpanierAnnonce spa = new SpanierAnnonce();


                        showConfirmationDialog("Confirm Ad Deletion",
                                "Are you sure you want to delete this ad?", () -> {
                                    spa.DeleteProduitAuPanier(id);

                                    paniertable.getItems().remove(ads);
                                    paniertable.refresh();
                                });
                    });
                }


                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(actionButton);
                    }
                }
            };
            return cell;
        };


        actionButtonColumn.setCellFactory(cellFactory);


        paniertable.getColumns().add(actionButtonColumn);

    }

    @FXML
   void payer(ActionEvent event) {
       int idUser = 5;

       try {
           Scommandes scommandes = new Scommandes();
           int idPanier = getIdPanierUtilisateur(idUser);
           if (idPanier == -1) {
               System.out.println("Aucun panier trouvé pour cet utilisateur.");
               return;
           }
           List<Integer> annonceIds = getIdAnnoncesDansPanier(idPanier);
          double price= updateStatutAnnonces(annonceIds);
           scommandes.add(idUser,price);
           deleteAllAnnoncesDansPanier(idPanier);
           System.out.println("Commande traitée avec succès!");
           String subject = "Order";
           String messageBody = "votre commande est effectue avec succées! Prix total \n\n"+price;
           String email_to = "anas.joo@esprit.tn";
           EmailSender.sendEmail(email_to, subject, messageBody);

       } catch (SQLException e) {
           System.err.println("Erreur lors du traitement de la commande : " + e.getMessage());
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }




    private int getIdPanierUtilisateur(int idUser) throws SQLException {
        String selectPanierIdQuery = "SELECT idPanier FROM panier WHERE idUser = ?";
        try (PreparedStatement ps = cnx.prepareStatement(selectPanierIdQuery)) {
            ps.setInt(1, idUser);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("idPanier");
            } else {
                return -1;
            }
        }
    }

    private List<Integer> getIdAnnoncesDansPanier(int idPanier) throws SQLException {
        List<Integer> annonceIds = new ArrayList<>();
        String selectAnnoncesQuery = "SELECT idAnnonce FROM panierannonce WHERE idPanier = ?";
        try (PreparedStatement ps = cnx.prepareStatement(selectAnnoncesQuery)) {
            ps.setInt(1, idPanier);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                annonceIds.add(resultSet.getInt("idAnnonce"));
            }
        }
        return annonceIds;
    }

    private double updateStatutAnnonces(List<Integer> annonceIds) throws SQLException {
        String updateAnnoncesQuery = "UPDATE annonces SET status = 1 WHERE id = ?";
        double totalPrice = 0.0;
        try (PreparedStatement ps = cnx.prepareStatement(updateAnnoncesQuery)) {
            for (int annonceId : annonceIds) {
                ps.setInt(1, annonceId);
                ps.executeUpdate();
                double annoncePrice = getPrixAnnonceById(annonceId);
                totalPrice += annoncePrice;
                System.out.println(totalPrice);
            }
        }
        return totalPrice;
    }

    private void deleteAllAnnoncesDansPanier(int idPanier) throws SQLException {
        String deleteAnnoncesQuery = "DELETE FROM panierannonce WHERE idPanier = ?";
        try (PreparedStatement ps = cnx.prepareStatement(deleteAnnoncesQuery)) {
            ps.setInt(1, idPanier);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Toutes les annonces du panier ont été supprimées!");

                refreshPage();
            } else {
                System.out.println("Aucune annonce trouvée dans le panier pour cet ID.");
            }
        }
    }


    private void refreshPage() {

        paniertable.getItems().clear();


        afficherPanier();


    }

    private double getPrixAnnonceById(int annonceId) throws SQLException {
        String selectPrixQuery = "SELECT prix FROM annonces WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(selectPrixQuery)) {
            ps.setInt(1, annonceId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("prix");
            } else {
                throw new SQLException("Annonce introuvable pour l'ID : " + annonceId);
            }
        }
    }



}
