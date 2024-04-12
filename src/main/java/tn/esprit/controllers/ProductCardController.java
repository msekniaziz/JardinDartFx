//package tn.esprit.controllers;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.text.Text;
//import tn.esprit.entities.Produittroc;
//import tn.esprit.controllers.Produit_TrocService;
//import javafx.scene.Node;
//
//
//
//
//
//public class ProductCardController {
//
//    @FXML
//    private HBox deleteCourse;
//
//    @FXML
//    private Label descriptionCourse;
//
//    @FXML
//    private HBox editCourse;
//
//    @FXML
//    private ImageView img;
//
//    @FXML
//    private Label levelCourse;
//
//    @FXML
//    private Text titleCourse;
//
//    @FXML
//    private HBox qrCodeBtn;
//
//    @FXML
//    private HBox shareFbBtn;
//
//
//    public void setCourseData(Produittroc cours) {
//        // Instancier le service de cours
//        Produit_TrocService coursService = new Produit_TrocService();
//
//        //  Image image = new Image(
//        //          getClass().getResource("/uploads/" + cours.getImagePath()).toExternalForm());
//        //  img.setImage(image);
//
//        titleCourse.setText(cours.getNom());
//        descriptionCourse.setText(cours.getDescription());
//        levelCourse.setText(cours.getNom_produit_recherche());
//
//        // deleteCategory btn click
//        deleteCourse.setId(String.valueOf(cours.getId()));
//
//        deleteCourse.setOnMouseClicked(event -> {
//            System.out.println("ID du Product à supprimer : " + cours.getId());
//
//            // int courseIdToDelete = Integer.parseInt(IdTF.getText());
//            Produittroc coursToDelete = new Produittroc();
//            coursToDelete.setId(cours.getId());
//
//            // Appeler la méthode de suppression dans le service
//            try {
//                coursService.deletePT(coursToDelete);
//
//                // Afficher un message de succès ou effectuer d'autres actions nécessaires après la suppression réussie
//                System.out.println("Le cours a été supprimé avec succès.");
//
//                // Rafraîchir le contenu du GridPane après la suppression réussie
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Admin/CoursUI.fxml"));
//                try {
//                    Parent root = loader.load();
//                    // Accéder à la pane content_area depuis le controller de
//
//                    AnchorPane contentArea = (AnchorPane ) ((Node) event.getSource()).getScene().lookup("#content_area");
//
//                    // Vider la pane et afficher le contenu de ProductsList.fxml
//                    contentArea.getChildren().clear();
//                    contentArea.getChildren().add(root);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //refreshCourseList();
//            } catch (SQLException e) {
//                // Afficher un message d'erreur en cas d'échec de la suppression
//                System.out.println("Erreur lors de la suppression du cours : " + e.getMessage());
//                e.printStackTrace();
//            }
//        });
//        // END deleteCourse btn click
//
//        //**********edit course btn click
//        editCourse.setOnMouseClicked(event -> {
//            System.out.println("ID du cours à modifier : " + cours.getId());
//
//            try {
//                coursService.getProduitById(cours.getId());
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            // Accéder aux inputs du formulaire du cours depuis ce controller
//            TextField titleTF = (TextField) ((Node) event.getSource()).getScene().lookup("#titleTF");
//            titleTF.setText(cours.getNom());
//
//            TextField DescriptionTF = (TextField) ((Node) event.getSource()).getScene().lookup("#DescriptionTF");
//            DescriptionTF.setText(cours.getDescription());
//
//            ComboBox<String> niveauComboBox = (ComboBox) ((Node) event.getSource()).getScene().lookup("#niveauComboBox");
//            niveauComboBox.setValue(cours.getCategory());
//
//            ImageView  imgb = (ImageView ) ((Node) event.getSource()).getScene().lookup("#imgb");
//
//            Image image = new Image(
//                    getClass().getResource("/images/" + cours.getImage()).toExternalForm());
//            imgb.setImage(image);
//
//            cours.setId(cours.getId());
//
//        });
//        // END editCourse btn click
//
//
//
//    }
//}
