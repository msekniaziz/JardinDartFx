package tn.jardindart.controllers.Ads;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import tn.jardindart.controllers.user.EmailSender;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.Annonces;
import tn.jardindart.models.Commandes;
import tn.jardindart.models.Panier;
import tn.jardindart.services.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.util.Callback;
import tn.jardindart.utils.MyDataBase;

import static helper.AlertHelper.showAlert;


public class PanierController {
    public static Connection cnx;

    private ProdRService prodRService;

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
    int id = SessionManager.getInstance().getUserFront();
    SpanierAnnonce pa = new SpanierAnnonce();
    Commandes com = new Commandes();


    public Panier selectPanierParUserId(int id) {
        Panier panier = null;
        String requete = "SELECT * FROM panier WHERE idUser = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                panier = new Panier();
                panier.setIdPanier(rs.getInt("idPanier"));
                // Ajoutez d'autres attributs du panier en fonction de votre modèle de données
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return panier;
    }

    public void initialize(int idPanier) {
        prodRService = new ProdRService(); // Initialisation de l'instance de ProdRService

        System.out.println("Chargement des données de panier...");
        cnx = MyDataBase.getInstance().getCnx();
        afficherPanier();
        deleteAds();
        addRemiseButtonColumn(); // Ajoutez cette ligne pour ajouter la colonne de bouton de remise.


    }

    private void addRemiseButtonColumn() {
        TableColumn<Annonces, Void> remiseButtonColumn = new TableColumn<>("Remise");
        Callback<TableColumn<Annonces, Void>, TableCell<Annonces, Void>> cellFactory = (TableColumn<Annonces, Void> param) -> {
            final TableCell<Annonces, Void> cell = new TableCell<Annonces, Void>() {
                private final Button remiseButton = new Button("Remise(20% SI NBPTS>=20");

                {
                    remiseButton.setOnAction(event -> {
                        Annonces ads = getTableView().getItems().get(getIndex());
                        handleRemiseButtonClicked(ads); // Appel de la méthode pour gérer le clic sur le bouton de remise.
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(remiseButton);
                    }
                }
            };
            return cell;
        };
        remiseButtonColumn.setCellFactory(cellFactory);
        paniertable.getColumns().add(remiseButtonColumn);
    }
    private double calculateRemise(int nbPoints) {
        // Par exemple, une remise de 20% si l'utilisateur a 20 points.
        return nbPoints >= 20 ? 20.0 : 0.0; // Si l'utilisateur a 20 points ou plus, la remise est de 20%, sinon pas de remise.
    }

    private double applyRemise(double prixInitial, double pourcentageRemise) {
        double montantRemise = prixInitial * (pourcentageRemise / 100);
        return prixInitial - montantRemise;
    }



    private void handleRemiseButtonClicked(Annonces ads) {
        int nbPoints = prodRService.getNbById(id); // Obtenez le nombre de points de l'utilisateur.
        if (nbPoints >= 20) {
            double pourcentageRemise = calculateRemise(nbPoints);
            double nouveauPrix = applyRemise(ads.getPrix(), pourcentageRemise);
            // Vous pouvez faire quelque chose avec le nouveau prix, par exemple, l'afficher ou le mettre à jour dans la base de données.
            // Par exemple :
          ads.setPrix(nouveauPrix);
          prodRService.EditNbPtsUser(id,nbPoints-20);
           updateAnnoncePrix(ads.getId(), nouveauPrix);
            refreshTable(); // Rafraîchir la table pour voir les changements.
        }
    }
    private void updateAnnoncePrix(int annonceId, double nouveauPrix) {
        String updateAnnonceQuery = "UPDATE annonces SET prix = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(updateAnnonceQuery)) {
            ps.setDouble(1, nouveauPrix);
            ps.setInt(2, annonceId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Prix de l'annonce " + annonceId + " mis à jour avec succès !");
            } else {
                System.out.println("Aucune annonce trouvée pour l'ID : " + annonceId);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du prix de l'annonce : " + e.getMessage());
        }
    }

    private void refreshTable() {
        paniertable.refresh();
    }


    private void afficherPanier() {
       Panier pp = selectPanierParUserId(id);
       System.out.println(pp.getIdPanier());
       List<Annonces> panier = pa.getCard(pp.getIdPanier());
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

       try {
           Scommandes scommandes = new Scommandes();
           int idPanier = getIdPanierUtilisateur(id);
           if (idPanier == -1) {
               System.out.println("Aucun panier trouvé pour cet utilisateur.");
               return;
           }
           List<Integer> annonceIds = getIdAnnoncesDansPanier(idPanier);
          double price= updateStatutAnnonces(annonceIds);
           scommandes.add(id,price);
           System.out.println(id);
           deleteAllAnnoncesDansPanier(idPanier);
           String mail = search_mail(id);
           System.out.println("Commande traitée avec succès!");
           String subject = "Order";
           String messageBody = "votre commande est effectue avec succées! Prix total \n\n"+price;
           String email_to =mail;
           String body = "<!DOCTYPE html>" +
                   "<html>" +
                   "<head>" +
                   "<style>" +
                   "body {" +
                   "    font-family: Arial, sans-serif;" +
                   "    background-color: #f4f4f4;" +
                   "    margin: 0;" +
                   "    padding: 0;" +
                   "}" +
                   ".container {" +
                   "    max-width: 600px;" +
                   "    margin: 0 auto;" +
                   "    padding: 20px;" +
                   "    background-color: #fff;" +
                   "    border-radius: 8px;" +
                   "    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);" +
                   "}" +
                   ".header {" +
                   "    background-color: #4CAF50;" +
                   "    color: #fff;" +
                   "    text-align: center;" +
                   "    padding: 20px;" +
                   "    border-radius: 8px 8px 0 0;" +
                   "}" +
                   ".content {" +
                   "    padding: 20px;" +
                   "}" +
                   ".footer {" +
                   "    background-color: #f4f4f4;" +
                   "    padding: 20px;" +
                   "    text-align: center;" +
                   "    border-radius: 0 0 8px 8px;" +
                   "}" +
                   "</style>" +
                   "</head>" +
                   "<body>" +
                   "<div class=\"container\">" +
                   "    <div class=\"header\">" +
                   "        <h1>Thank you!</h1>" +
                   "    </div>" +
                   "    <div class=\"content\">" +
                   "        <p>Dear User "+  " ,</p>" +
                   "        <p>votre commande est effectue avec succées! Prix total "+price+" </p>" +
                   "        <p>Thank you for your trust.</p>" +
                   "    </div>" +
                   "    <div class=\"footer\">" +
                   "        <p>The JARDIN D'ART Team</p>" +
                   "    </div>" +
                   "</div>" +
                   "</body>" +
                   "</html>";
           EmailSender.sendEmail(email_to, subject, body);

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
       String updateCategoryQuery = "UPDATE category SET nbr_annonce = nbr_annonce - 1 WHERE id = ?";
       double totalPrice = 0.0;
       try (PreparedStatement psAnnonces = cnx.prepareStatement(updateAnnoncesQuery);
            PreparedStatement psCategory = cnx.prepareStatement(updateCategoryQuery)) {

           for (int annonceId : annonceIds) {

               psAnnonces.setInt(1, annonceId);
               psAnnonces.executeUpdate();


               int categoryId = getCategorieIdByAnnonceId(annonceId);


               psCategory.setInt(1, categoryId);
               psCategory.executeUpdate();

               double annoncePrice = getPrixAnnonceById(annonceId);
               totalPrice += annoncePrice;
               System.out.println(totalPrice);
           }
       }
       return totalPrice;
   }


    private int getCategorieIdByAnnonceId(int annonceId) throws SQLException {
        String query = "SELECT id_cat_id FROM annonces WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, annonceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cat_id");
                }
            }
        }
        return -1;
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

   private String search_mail(int id)
   {
       String selectPrixQuery = "SELECT mail FROM user WHERE id = ?";
       try (PreparedStatement ps = cnx.prepareStatement(selectPrixQuery)) {
           ps.setInt(1, id);
           ResultSet resultSet = ps.executeQuery();
           if (resultSet.next()) {
               return resultSet.getString("mail");
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return null ;
   }



}
