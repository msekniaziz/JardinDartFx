package tn.jardindart.controllers.Ads;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.Row;
//import com.mysql.cj.result.Row;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.jardindart.models.Annonces;
import tn.jardindart.models.Categories;
import tn.jardindart.models.Commandes;
import tn.jardindart.services.Scategories;
import tn.jardindart.services.Scommandes;
import tn.jardindart.utils.MyDataBase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//execl fasakhhom baed ken matessthakhomch  ye noussa
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class AffOrderController implements Initializable {

    @FXML
    private TextField SearchBarOrder;
    @FXML
    private Button excelProduit;
    @FXML
    private Button pdfProduit;
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
    private AnchorPane getSelectedCard;

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
         Sc = new Scommandes();
        loadCartData();


    }
    @FXML
    private void loadCartData() {
        List<Commandes> order = Sc.getAll();

        flowPaneLOrder.getChildren().clear();
        for (Commandes com : order) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Ads/cardOrder.fxml"));
                AnchorPane card = loader.load();
                CardOrderController controller = loader.getController();
                controller.initialize(com); // Initialise la carte avec les données de catégorie
                card.setUserData(com); // Définit les données utilisateur de la carte comme la catégorie
                flowPaneLOrder.getChildren().add(card);
                card.setOnMouseClicked(this::onOrderSelected);

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des données de commandes.");
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private List<Commandes> getOrder() {
        Scommandes serviceCat = new Scommandes();
        return serviceCat.getAll();
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
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/tn.jardindart/Ads/affCategory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.jardindart.controllers.Ads.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void affOrder(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/tn.jardindart/Ads/affOrder.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.jardindart.controllers.Ads.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void sortByDate(ActionEvent event) {

    }
    private void onOrderSelected(MouseEvent event) {
        getSelectedCard = (AnchorPane) event.getSource();
        Object userData = getSelectedCard.getUserData();
        System.out.println(userData);
        if (userData instanceof Commandes) {
            selectedOrder = (Commandes) userData;
            System.out.println("commande sélectionnée : " + selectedOrder.getId());

            initData(selectedOrder);
        } else {
            System.err.println("Erreur : Données utilisateur invalides.");
        }
    }

   /* void onOrderSelected(MouseEvent event) {
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
    }*/

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
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/tn.jardindart/Ads/affAdsBack.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(tn.jardindart.controllers.Ads.AffCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }


    @FXML
    public void generateExcelProduit(ActionEvent actionEvent) {
        String req = "SELECT * FROM commandes";

        try (Connection cnx = MyDataBase.getInstance().getCnx();
             Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(req)) {

            Workbook wb = new HSSFWorkbook(); // Use HSSFWorkbook to create an Excel file in .xls format
            Sheet sheet = wb.createSheet("Détails commande");
            Row header = sheet.createRow(0);

            // Add headers for each column
            String[] headers = {"id_user_c_id", "etat", "date"};
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_user_c_id"));
                row.createCell(1).setCellValue(rs.getString("etat"));
                row.createCell(2).setCellValue(rs.getString("date"));
            }

            // Write content to a file
            String filePath = "C:/Users/user/Desktop/commande.xls";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                wb.write(fileOut);
                JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succès");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'écriture du fichier Excel : " + e.getMessage());
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'exécution de la requête SQL : " + e.getMessage());
        }
    }

   /* @FXML
    void generatePdfProduit(ActionEvent event) {
        List<Commandes> data = getOrder();

        try {
            // Créez un nouveau document PDF
            PDDocument document = new PDDocument();

            // Créez une page dans le document
            PDPage page = new PDPage();
            document.addPage(page);

            // Obtenez le contenu de la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Écrivez du texte dans le document
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);


            for (Commandes com : data) {


                String ligne = "ID : " + com.getId_user_c_id() + "   prix : " + com.getEtat() + "     date : " + com.getDate() ;
                contentStream.showText(ligne);

                contentStream.newLine();
                contentStream.newLineAtOffset(0, -15);


            }

            contentStream.endText();

            // Fermez le contenu de la page
            contentStream.close();

            String outputPath ="C:/Users/user/Desktop/produits.pdf";
            File file = new File(outputPath);
            document.save(file);

            // Fermez le document
            document.close();

            System.out.println("Le PDF a été généré avec succès.");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
   @FXML
   void generatePdfProduit(ActionEvent event) {
       List<Commandes> data = getOrder();

       // Chemin de sortie pour le PDF
       String outputPath = "C:/Users/user/Desktop/produits.pdf";

       try (PDDocument document = new PDDocument()) {
           PDPage page = new PDPage(PDRectangle.A4);
           document.addPage(page);

           try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
               contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20); // Taille de police agrandie
               contentStream.setNonStrokingColor(new Color(34, 139, 34)); // Couleur vert

               // Centrer le texte "Jardin D'art" en haut de la page
               float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("Jardin D'art") / 1000f * 20; // Largeur du texte en points
               float centerX = (PDRectangle.A4.getWidth() - textWidth) / 2; // Centre X de la page
               float startY = PDRectangle.A4.getHeight() - 50; // Position Y en haut de la page
               contentStream.beginText();
               contentStream.newLineAtOffset(centerX, startY);
               contentStream.showText("Jardin D'art");
               contentStream.endText();

               // Logo à gauche
               PDImageXObject logo = PDImageXObject.createFromFile("src/main/resources/images/logo_site.png", document);
               float logoX = 50;
               float logoY = PDRectangle.A4.getHeight() - 100; // Position Y du logo
               contentStream.drawImage(logo, logoX, logoY, 100, 100); // Position et taille du logo

               // Dessiner le tableau
               drawTable(contentStream, 50, 600, data);

               // Fermez le contenu de la page
               contentStream.close();
           }

           // Enregistrez et ouvrez le fichier PDF
           File file = new File(outputPath);
           document.save(file);
           System.out.println("Le PDF a été généré avec succès : " + outputPath);

           // Ouvrir le PDF avec l'application par défaut
           Desktop.getDesktop().open(file);
       } catch (IOException e) {
           System.err.println("Erreur lors de la génération du PDF : " + e.getMessage());
       }
   }

    // Méthode pour dessiner un tableau avec les données des commandes
    private void drawTable(PDPageContentStream contentStream, float x, float y, List<Commandes> data) throws IOException {
        final int rows = data.size() + 1; // Nombre de lignes incluant l'en-tête
        final int cols = 3; // Nombre de colonnes : ID, Prix, Date
        final float rowHeight = 20f;
        final float tableWidth = 500f;
        final float colWidth = tableWidth / (float) cols;

        // Dessiner les lignes et les cellules du tableau
        float nextY = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(x, nextY);
            contentStream.lineTo(x + tableWidth, nextY);
            contentStream.stroke();
            nextY -= rowHeight;
        }

        // Écrire les en-têtes de colonnes
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        float textX = x + 5;
        float textY = y - 15;
        contentStream.beginText();
        contentStream.newLineAtOffset(textX, textY);
        contentStream.setNonStrokingColor(new Color(34, 139, 34)); // Couleur vert pour les en-têtes
        contentStream.showText("ID");
        contentStream.newLineAtOffset(colWidth, 0);
        contentStream.showText("Prix");
        contentStream.newLineAtOffset(colWidth, 0);
        contentStream.showText("Date");
        contentStream.endText();

        // Écrire les données des commandes dans le tableau
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        textY -= rowHeight;
        for (Commandes com : data) {
            textX = x + 5;
            contentStream.beginText();
            contentStream.newLineAtOffset(textX, textY);

            // ID (convertir en String)
            contentStream.setNonStrokingColor(Color.BLACK); // Couleur noire pour les données
            contentStream.showText(String.valueOf(com.getId_user_c_id()));
            contentStream.newLineAtOffset(colWidth, 0);

            // Prix (convertir en String)
            contentStream.showText(String.valueOf(com.getEtat()));
            contentStream.newLineAtOffset(colWidth, 0);

            // Date (convertir en String avec formatage)
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(com.getDate());
            contentStream.showText(formattedDate);

            contentStream.endText();
            textY -= rowHeight;
        }
    }




}