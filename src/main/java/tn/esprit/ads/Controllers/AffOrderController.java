package tn.esprit.ads.Controllers;

import com.mysql.cj.result.Row;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Entity.Commandes;
import tn.esprit.ads.Services.Scategories;
import tn.esprit.ads.Services.Scommandes;
import tn.esprit.ads.tools.MyDataBase;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//execl fasakhhom baed ken matessthakhomch  ye noussa
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class AffOrderController implements Initializable {

    @FXML
    private TextField SearchBarOrder;
    @FXML
    private Button excelProduit;
    @FXML
    private Button affOrder;
    @FXML
    private Button affAds;

    @FXML
    private ImageView SearchButtonUserClick;

    @FXML
    private ImageView imageuser;


    @FXML
    private Label adminName;

    @FXML
    private Button affCategory;


    @FXML
    private Button brtn_DeleteAll1;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btn_DeleteSelected1;

    @FXML
    private Button btn_Refresh1;

    @FXML
    private FlowPane flowPaneLOrder;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Button sort1;
    private Commandes selectedOrder;
    Scommandes Sc =new Scommandes();
    @FXML
    Commandes Order;
    private FXMLLoader CardLoader;
    private static Connection cnx;

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Chargement des données des Commandes...");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("affOrder.fxml"));



        CardLoader = new FXMLLoader(getClass().getResource("/cardOrder.fxml"));
        loadCartData();

        System.out.println("Chargement des données de category...");
        Image image1 = new Image("file:src/main/java/tn/esprit/ads/img/jimmy-fallon.png");
        imageuser.setImage(image1);


    }
    @FXML


    private void loadCartData() {
        List<Commandes> order = Sc.getAll();

        flowPaneLOrder.getChildren().clear();
        for (Commandes com : order) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardOrder.fxml"));
                AnchorPane card = loader.load();
                CardOrderController controller = loader.getController();
                controller.initialize(com); // Initialise la carte avec les données de catégorie
                card.setUserData(com); // Définit les données utilisateur de la carte comme la catégorie
                flowPaneLOrder.getChildren().add(card);

                // Mettre en place l'événement de sélection de la carte de catégorie
                card.setOnMouseClicked(event -> {
                    onOrderSelected(event);
                    updateOrderCards(); // Mettre à jour l'apparence des cartes de catégorie
                });
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des données de catégorie.");
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private List<Commandes> getOrder() {
        Scategories serviceCat = new Scategories();
        return Sc.getAll();
    }
    private AnchorPane getSelectedCard() {
        for (Node node : flowPaneLOrder.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane card = (AnchorPane) node;
                if (card.getStyle().contains("-fx-background-color: lightblue;")) {
                    return card;
                }
            }
        }
        return null;
    }
    @FXML
    void DeleteAllOrder(ActionEvent event) {
        Scommandes order = new Scommandes();
        boolean success = order.deleteAll();
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "tous les commandes sont supprimés!");
            loadCartData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "erreur.");
        }

    }

    @FXML
    void DeleteOrderSelected(ActionEvent event) {
        Sc.delete(selectedOrder);
        loadCartData(); // Recharger les données de commande

    }

    @FXML
    void RefrecheOrder(ActionEvent event) {
        loadCartData();

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
    void sortByDate(ActionEvent event) {

    }
    void onOrderSelected(MouseEvent event) {
        AnchorPane selectedCard = (AnchorPane) event.getSource();
        Object userData = selectedCard.getUserData();

        if (userData != null && userData instanceof Commandes) {
            Commandes selectedOrder = (Commandes) userData;

            System.out.println("Commande sélectionnée : " + selectedOrder.getId());

            this.selectedOrder = selectedOrder;
            initData(selectedOrder);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
        }
    }

    private void initData(Commandes selectedOrder) {
    }
    private void updateOrderCards() {
        for (Node node : flowPaneLOrder.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane card = (AnchorPane) node;
                CardOrderController controller = (CardOrderController) card.getProperties().get("controller");
                Commandes commandes = controller.getCom();
                if (commandes.isSelected()) {
                    card.setStyle("-fx-background-color: lightblue;");
                } else {
                    card.setStyle("-fx-background-color: white;");
                }
            }
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

    @FXML
    public void generateExcelProduit(ActionEvent actionEvent) {/*
        String req = "SELECT c.nomCategorie, p.idProduit, p.nomProduit, p.quantite, p.prix, SUM(p.quantite) OVER(PARTITION BY c.nomCategorie) AS stock " +
                "FROM produit p JOIN categorie c ON p.categorie_id = c.idCategorie ORDER BY c.nomCategorie, p.idProduit";

        try (Connection cnx = MyDataBase.getInstance().getCnx();
             Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(req)) {

            Workbook wb = new HSSFWorkbook(); // Utilisation de HSSFWorkbook pour créer un fichier Excel en format .xls
            Sheet sheet = wb.createSheet("Détails produit");
            Row header = sheet.createRow(0);

            // Ajouter des en-têtes pour chaque colonne
            String[] headers = {"Catégorie", "idProduit", "nomProduit", "quantite", "prix", "Stock total"};
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            int index = 1;
            String currentCategory = "";
            while (rs.next()) {
                String category = rs.getString("nomCategorie");
                if (!category.equals(currentCategory)) {
                    currentCategory = category;
                }

                Row row = sheet.createRow(index);
                row.createCell(0).setCellValue(category);
                row.createCell(1).setCellValue(rs.getInt("idProduit"));
                row.createCell(2).setCellValue(rs.getString("nomProduit"));
                row.createCell(3).setCellValue(rs.getInt("quantite"));
                row.createCell(4).setCellValue(rs.getDouble("prix"));
                row.createCell(5).setCellValue(rs.getInt("stock"));

                index++;
            }

            // Écrire le contenu dans un fichier
            String filePath = "C:/Users/Hp/Desktop/produitCategorie/src/main/java/EXCEL/produit.xls";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                wb.write(fileOut);
                JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succès");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'écriture du fichier Excel.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'exécution de la requête SQL.");
        }*/
    }

}
