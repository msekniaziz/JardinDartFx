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


//import javax.security.auth.callback.Callback;
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


    public void initialize(int id) {
        System.out.println("Chargement des données de panier...");
        cnx = MyDataBase.getInstance().getCnx();
        this.id = id;
        afficherPanier();
        deleteAds();



    }


    private void afficherPanier() {

        List<Annonces> panier = pa.getCard(id);
        System.out.println("chfama ");
        paniertable.getItems().addAll(panier);
        image.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));

    }
  /* private void afficherPanier() {
       List<Annonces> panier = pa.getCard(id);
       System.out.println("Chargement du panier...");

       ObservableList<Annonces> observableList = FXCollections.observableArrayList(panier);

       image.setCellValueFactory(cellData -> {
           Annonces annonce = cellData.getValue();
           ImageView imageView = new ImageView(new Image(annonce.getImage()));
           imageView.setFitWidth(100); // Ajustez la largeur souhaitée
           imageView.setFitHeight(100); // Ajustez la hauteur souhaitée
           return new SimpleObjectProperty<>(imageView);
       });

       title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
       price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));

       paniertable.setItems(observableList);
   }*/

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

        // Création de la fabrique de cellules pour la colonne d'action
        Callback<TableColumn<Annonces, Void>, TableCell<Annonces, Void>> cellFactory = (TableColumn<Annonces, Void> param) -> {
            final TableCell<Annonces, Void> cell = new TableCell<Annonces, Void>() {
                private final Button actionButton = new Button("delete");//ki nheb nrodha ktiba new Button("delete");


                {
                    /*Image image1 = new Image(getClass().getResourceAsStream("/images/icons8_Box_32px.png"));
                    ImageView view = new ImageView(image1);
                    view.setFitWidth(30);
                    view.setFitHeight(30);
                    actionButton.setGraphic(view);*/
                    // Logique d'événement lorsque le bouton est cliqué
                    actionButton.setOnAction(event -> {

                        Annonces ads = getTableView().getItems().get(getIndex());
                        int id = ads.getId();
                        SpanierAnnonce spa = new SpanierAnnonce();

                        // Affichage de la boîte de dialogue de confirmation
                        showConfirmationDialog("Confirm Ad Deletion",
                                "Are you sure you want to delete this ad?", () -> {
                                    spa.DeleteProduitAuPanier(id);
                                    // Rafraîchir la TableView après la suppression
                                    paniertable.getItems().remove(ads); // Retirer l'élément de la liste observable
                                    paniertable.refresh(); // Rafraîchir la TableView
                                });
                    });
                }

                // Appelé lors de la mise à jour de l'élément de la cellule
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null); // Cellule vide
                    } else {
                        setGraphic(actionButton); // Affiche le bouton d'action dans la cellule
                    }
                }
            };
            return cell;
        };

        // Attribution de la fabrique de cellules à la colonne d'action
        actionButtonColumn.setCellFactory(cellFactory);

        // Ajout de la colonne d'action à votre TableView
        paniertable.getColumns().add(actionButtonColumn);

    }

   /* @FXML
    void payer(ActionEvent event) throws SQLException {
       int idUser = 5 ;
       Scommandes scommandes = new Scommandes() ;
       scommandes.add(idUser);



    }*/
  /* @FXML
   void payer(ActionEvent event) throws SQLException {
       int idUser = 5;
       Scommandes scommandes = new Scommandes() ;
       scommandes.add(idUser);


       try {

           String selectPanierIdQuery = "SELECT idPanier FROM panier WHERE idUser = ?";
           int idPanier = -1;

           try (PreparedStatement ps = cnx.prepareStatement(selectPanierIdQuery)) {
               ps.setInt(1, idUser);
               ResultSet resultSet = ps.executeQuery();

               if (resultSet.next()) {
                   idPanier = resultSet.getInt("idPanier");
               } else {
                   System.out.println("Aucun panier trouvé pour cet utilisateur.");
                   return;
               }
           }

           if (idPanier == -1) {
               System.out.println("Aucun panier trouvé pour cet utilisateur.");
               return;
           }


           String selectAnnoncesQuery = "SELECT idAnnonce FROM panierannonce WHERE idPanier = ?";
           List<Integer> annonceIds = new ArrayList<>();

           try (PreparedStatement ps = cnx.prepareStatement(selectAnnoncesQuery)) {
               ps.setInt(1, idPanier);
               ResultSet resultSet = ps.executeQuery();

               while (resultSet.next()) {
                   annonceIds.add(resultSet.getInt("idAnnonce"));
               }
           }


           String updateAnnoncesQuery = "UPDATE annonces SET status = 1 WHERE id = ?";

           try (PreparedStatement ps = cnx.prepareStatement(updateAnnoncesQuery)) {
               for (int annonceId : annonceIds) {
                   ps.setInt(1, annonceId);
                   ps.executeUpdate();
               }

               System.out.println("Statut des annonces mis à jour avec succès.");
           }
       } catch (SQLException e) {
           System.err.println("Erreur lors de la mise à jour du statut des annonces : " + e.getMessage());
       }

   }*/
   @FXML
   void payer(ActionEvent event) {
       int idUser = 5; // ID de l'utilisateur (à remplacer par la récupération de l'ID de l'utilisateur qui paie)

       try {
           // Ajouter la commande pour l'utilisateur
           Scommandes scommandes = new Scommandes();
           scommandes.add(idUser);

           // Récupérer l'ID du panier de l'utilisateur
           int idPanier = getIdPanierUtilisateur(idUser);
           if (idPanier == -1) {
               System.out.println("Aucun panier trouvé pour cet utilisateur.");
               return;
           }

           // Récupérer les ID des annonces dans le panier
           List<Integer> annonceIds = getIdAnnoncesDansPanier(idPanier);

           // Mettre à jour le statut des annonces (ex: passer le statut à 1 pour les annonces commandées)
           updateStatutAnnonces(annonceIds);

           // Supprimer toutes les annonces du panier
           deleteAllAnnoncesDansPanier(idPanier);

           System.out.println("Commande traitée avec succès!");

       } catch (SQLException e) {
           System.err.println("Erreur lors du traitement de la commande : " + e.getMessage());
       }
   }

    // Méthode pour récupérer l'ID du panier de l'utilisateur
    private int getIdPanierUtilisateur(int idUser) throws SQLException {
        String selectPanierIdQuery = "SELECT idPanier FROM panier WHERE idUser = ?";
        try (PreparedStatement ps = cnx.prepareStatement(selectPanierIdQuery)) {
            ps.setInt(1, idUser);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("idPanier");
            } else {
                return -1; // Aucun panier trouvé pour cet utilisateur
            }
        }
    }

    // Méthode pour récupérer les ID des annonces dans un panier
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

    // Méthode pour mettre à jour le statut des annonces
    private void updateStatutAnnonces(List<Integer> annonceIds) throws SQLException {
        String updateAnnoncesQuery = "UPDATE annonces SET status = 1 WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(updateAnnoncesQuery)) {
            for (int annonceId : annonceIds) {
                ps.setInt(1, annonceId);
                ps.executeUpdate();
            }
        }
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









}
