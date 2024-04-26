package tn.esprit.ads.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Entity.Categories;
import tn.esprit.ads.Services.Sannonces;
import tn.esprit.ads.Services.Scategories;
import tn.esprit.ads.test.MainFx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static tn.esprit.ads.Services.Sannonces.cnx;

public class EditAdFormController {
    @FXML
    private Label Producrs_troc;

    @FXML
    private Label Products;

    @FXML
    private ImageView affichageImage;

    @FXML
    private Label blogs;

    @FXML
    private Button blogs_buut;

    @FXML
    private Button btn;

    @FXML
    private ComboBox<String> category;

    @FXML
    private TextArea description;

    @FXML
    private Label don;

    @FXML
    private Button dons_butt;

    @FXML
    private Button editid;

    @FXML
    private Label home;

    @FXML
    private Button home_butt;

    @FXML
    private ImageView imageView;

    @FXML
    private Label logout;

    @FXML
    private Button logout_butt;

    @FXML
    private CheckBox negotiable;

    @FXML
    private TextField price;

    @FXML
    private Button prods_butt;

    @FXML
    private Label recy;

    @FXML
    private Button recycle_butt;

    @FXML
    private TextField title;

    @FXML
    private Button troc_buut;

    @FXML
    private Button uploadImage;
    @FXML
    private Label titleErrorLabel;

    @FXML
    private Label descErrorLabel;
    @FXML
    private Label priceErrorLabel;



    @FXML
    private AnchorPane selectedCard;
    private Sannonces sannonces = new Sannonces();
    private Annonces annonce = new Annonces();
    private int idtoUp;
    @FXML
    public void initialize() {
        // Récupérer la liste des catégories depuis la base de données
        ArrayList<String> categoriesList = sannonces.getCategories();

        // Créer une liste observable à partir de la liste des catégories
        ObservableList<String> categoriesObservableList = FXCollections.observableArrayList(categoriesList);

        // Assigner la liste observable à la ComboBox
        category.setItems(categoriesObservableList);
    }




    public void initData(Annonces selectedAds) {
        this.idtoUp =selectedAds.getId();
        title.setText(selectedAds.getTitle());

        price.setText(String.valueOf(selectedAds.getPrix()));
        String negotiableStatus = selectedAds.getNegiciable() != 0 ? "Negotiable" : "Not Negotiable";
        negotiable.setText( negotiableStatus);
        description.setText(selectedAds.getDescription());

        // Mise à jour de l'image
        Image image = new Image("file:" + selectedAds.getImage());
        imageView.setImage(image);

        // Mise à jour de la catégorie
        // Category.setText(selectedAds.getId_Cat());
        //  Category.setText("anas");
        // Récupérer le nom de la catégorie à partir de l'id_cat_id
        //int categoryId = selectedAds.getId_Cat();
      //  String categoryName = new Sannonces().getCategoryName(categoryId);

        // Afficher le nom de la catégorie dans l'interface utilisateur
      //  categoryId.setText("Category : " + categoryName);
        int categoryId = selectedAds.getId_Cat();
        String categoryName = new Sannonces().getCategoryName(categoryId);

        // Set the selected category in the ComboBox
        category.setValue(categoryName);
    }


   /* @FXML
    public void Edit(ActionEvent actionEvent) {
        // Récupérer les valeurs modifiées depuis les champs de texte
        String newTitle = title.getText();
        String newDescription = description.getText();
        String selectedCategory = category.getValue();
        double newPrice = Double.parseDouble(price.getText());
        int newNegotiable = negotiable.getText().equalsIgnoreCase("Negotiable") ? 1 : 0; // Convertir en entier (1 ou 0)
        int categoryId = getIdFromCategoryName(selectedCategory);
        // Créer un nouvel objet Annonces avec les nouvelles valeurs
        Annonces updatedAd = new Annonces();
        updatedAd.setTitle(newTitle);
        updatedAd.setDescription(newDescription);
        updatedAd.setPrix(newPrice);
        updatedAd.setNegiciable(newNegotiable); // Utiliser setNegotiable au lieu de setNegiciable

        // Créer une instance de Sannonces
        Sannonces sannonces = new Sannonces();

        // Mettre à jour l'annonce dans la base de données et récupérer le résultat
        boolean success = sannonces.updateAndReturnSuccess(updatedAd);

        if (success) {
            // Afficher un message de réussite
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Ad updated successfully!");
            alert.showAndWait();

            // Fermer la fenêtre d'édition
            Stage stage = (Stage) editid.getScene().getWindow();
            stage.close();
        } else {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update ad!");
            alert.showAndWait();
        }
    }*/


    public void category(ActionEvent actionEvent) {
    }

    public void negotiable(ActionEvent actionEvent) {
    }



   /* @FXML
    public void editAnnonce(ActionEvent event) {

        String newTitle = title.getText();
        String newDescription = description.getText();
        String selectedCategory = category.getValue();
        double newPrice = Double.parseDouble(price.getText());
        int newNegotiable = negotiable.isSelected() ? 1 : 0; // Convertir en entier (1 ou 0)
        int categoryId = getIdFromCategoryName(selectedCategory);
        annonce.setId_Cat(categoryId);
        annonce.setTitle(newTitle);
        annonce.setDescription(newDescription);
        annonce.setPrix(newPrice);
        annonce.setNegiciable(newNegotiable);
        String newImagePath = saveImageToFile();
        annonce.setImage(newImagePath);
        System.out.println("test test");

        Sannonces sannonces = new Sannonces();

        System.out.println("test test");
        sannonces.update(annonce);
        System.out.println("test test");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Ad updated successfully!");
        alert.showAndWait();


        Stage stage = (Stage) editid.getScene().getWindow();
        stage.close();
    }*/
   /*@FXML
   public void editAnnonce(ActionEvent event) throws IOException {
       // Récupérer les nouvelles valeurs depuis les champs de texte
       String newTitle = title.getText();
       String newDescription = description.getText();
       String selectedCategory = category.getValue();
       double newPrice = Double.parseDouble(price.getText());
       int newNegotiable = negotiable.isSelected() ? 1 : 0; // Convertir en entier (1 ou 0)

       // Récupérer l'ID de l'annonce à partir de la méthode getById
       Annonces existingAd = sannonces.getById(idtoUp);
       System.out.println(idtoUp);

       // Vérifier si l'annonce existe
       if (existingAd != null) {
           // Mettre à jour les propriétés de l'annonce avec les nouvelles valeurs
           existingAd.setTitle(newTitle);
           existingAd.setDescription(newDescription);
           existingAd.setPrix(newPrice);
           existingAd.setNegiciable(newNegotiable);
           int categoryId = getIdFromCategoryName(selectedCategory);
           existingAd.setId_Cat(categoryId);
             String newImagePath = saveImageToFile();
             existingAd.setImage(newImagePath);

           // Mettre à jour l'annonce dans la base de données
           sannonces.update(existingAd);
           FXMLLoader fxmlLoader = new FXMLLoader(MainFx.class.getResource("/affMyAds.fxml"));
           description.getScene().setRoot(fxmlLoader.load());


           // Afficher un message de réussite
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setHeaderText(null);
           alert.setContentText("Ad updated successfully!");
           alert.showAndWait();

           // Fermer la fenêtre d'édition

       } else {
           // Afficher un message d'erreur si l'annonce n'est pas trouvée
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Failed to update ad! Ad not found.");
           alert.showAndWait();
       }
   }*/
  /* @FXML
   public void editAnnonce(ActionEvent event) throws IOException {
       // Récupérer les nouvelles valeurs depuis les champs de texte
       String newTitle = title.getText().trim();
       String newDescription = description.getText().trim();
       String selectedCategory = category.getValue();
       double newPrice = Double.parseDouble(price.getText());
       int newNegotiable = negotiable.isSelected() ? 1 : 0; // Convertir en entier (1 ou 0)

       // Récupérer l'annonce existante à partir de l'ID
       Annonces existingAd = sannonces.getById(idtoUp);

       // Vérifier si l'annonce existe
       if (existingAd != null) {
           // Mettre à jour les propriétés de l'annonce avec les nouvelles valeurs
           existingAd.setTitle(newTitle);
           existingAd.setDescription(newDescription);
           existingAd.setPrix(newPrice);
           existingAd.setNegiciable(newNegotiable);

           // Mettre à jour la catégorie de l'annonce
           int categoryId = getIdFromCategoryName(selectedCategory);
           existingAd.setId_Cat(categoryId);

           // Sauvegarder la nouvelle image si une image est chargée
           String newImagePath = saveImageToFile();
           if (newImagePath != null) {
               existingAd.setImage(newImagePath);
           }

           // Mettre à jour l'annonce dans la base de données
           sannonces.update(existingAd);
           FXMLLoader fxmlLoader = new FXMLLoader(MainFx.class.getResource("/affMyAds.fxml"));
           description.getScene().setRoot(fxmlLoader.load());

           // Afficher un message de réussite
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setHeaderText(null);
           alert.setContentText("Ad updated successfully!");
           alert.showAndWait();

           // Fermer la fenêtre d'édition (si nécessaire)
           Stage stage = (Stage) editid.getScene().getWindow();
           stage.close();
       } else {
           // Afficher un message d'erreur si l'annonce n'est pas trouvée
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Failed to update ad! Ad not found.");
           alert.showAndWait();
       }
   }
*/
   @FXML
   public void editAnnonce(ActionEvent event) throws IOException {
       // Récupérer les nouvelles valeurs depuis les champs de texte
       String newTitle = title.getText().trim();
       String newDescription = description.getText().trim();
       String selectedCategory = category.getValue();
       String priceText = price.getText().trim();

       // Vérifier si les champs obligatoires sont vides
       if (newTitle.isEmpty() || newDescription.isEmpty() || priceText.isEmpty() || selectedCategory == null) {
           // Afficher un message d'erreur indiquant les champs manquants
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Please fill in all required fields (Title, Description, Price, Category)");
           alert.showAndWait();
           return; // Arrêter l'exécution de la méthode si les champs sont vides
       }

       // Convertir le prix en double
       double newPrice = Double.parseDouble(priceText);
       int newNegotiable = negotiable.isSelected() ? 1 : 0; // Convertir en entier (1 ou 0)

       // Récupérer l'annonce existante à partir de l'ID
       Annonces existingAd = sannonces.getById(idtoUp);

       // Vérifier si l'annonce existe
       if (existingAd != null) {
           // Mettre à jour les propriétés de l'annonce avec les nouvelles valeurs
           existingAd.setTitle(newTitle);
           existingAd.setDescription(newDescription);
           existingAd.setPrix(newPrice);
           existingAd.setNegiciable(newNegotiable);

           // Mettre à jour la catégorie de l'annonce
           int categoryId = getIdFromCategoryName(selectedCategory);
           existingAd.setId_Cat(categoryId);

           // Sauvegarder la nouvelle image si une image est chargée
           String newImagePath = saveImageToFile();
           if (newImagePath != null) {
               existingAd.setImage(newImagePath);
           }

           // Mettre à jour l'annonce dans la base de données
           sannonces.update(existingAd);
           FXMLLoader fxmlLoader = new FXMLLoader(MainFx.class.getResource("/affMyAds.fxml"));
           description.getScene().setRoot(fxmlLoader.load());

           // Afficher un message de réussite
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setHeaderText(null);
           alert.setContentText("Ad updated successfully!");
           alert.showAndWait();

           // Fermer la fenêtre d'édition (si nécessaire)
           Stage stage = (Stage) editid.getScene().getWindow();
           stage.close();
       } else {
           // Afficher un message d'erreur si l'annonce n'est pas trouvée
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Failed to update ad! Ad not found.");
           alert.showAndWait();
       }
   }






    public void navigateToViewPr(ActionEvent actionEvent) {
    }
    private int getIdFromCategoryName(String categoryName) {

        int categoryId = 0;
        String query = "SELECT `id` FROM `category` WHERE name = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                categoryId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
        return categoryId;
    }

    private String saveImageToFile() {
        try {
            Image image = imageView.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            // Create a temporary file to store the image
            File tempFile = File.createTempFile("temp-", ".png");
            String destinationPath = tempFile.getAbsolutePath();

            ImageIO.write(bufferedImage, "png", tempFile);
            return destinationPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // or handle the exception differently
        }
    }
   /* private void saveImageToFile(String destinationPath) {
        try {
            Annonces imageview;
            Image image = imageView.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            File outputFile = new File(destinationPath);
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(uploadImage.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);

        }}
}
