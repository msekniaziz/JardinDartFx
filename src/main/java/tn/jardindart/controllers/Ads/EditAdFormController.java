package tn.jardindart.controllers.Ads;

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
import tn.jardindart.controllers.front_top;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.Annonces;
import tn.jardindart.models.Categories;
import tn.jardindart.services.Sannonces;
import tn.jardindart.services.Scategories;
import tn.jardindart.test.HelloApplication;

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

import static tn.jardindart.services.Sannonces.cnx;

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

    private int idUser = SessionManager.getInstance().getUserFront();
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
        int categoryId = selectedAds.getId_Cat();
        String categoryName = new Sannonces().getCategoryName(categoryId);
        category.setValue(categoryName);
    }

    public void category(ActionEvent actionEvent) {
    }

    public void negotiable(ActionEvent actionEvent) {
    }
   @FXML
   public void editAnnonce(ActionEvent event) throws IOException {

       String newTitle = title.getText().trim();
       String newDescription = description.getText().trim();
       String selectedCategory = category.getValue();
       String priceText = price.getText().trim();
       System.out.println("test");


       if (newTitle.isEmpty() || newDescription.isEmpty() || priceText.isEmpty() || selectedCategory == null) {

           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText(null);
           alert.setContentText("Please fill in all required fields (Title, Description, Price, Category)");
           alert.showAndWait();
           System.out.println("test ");

           return;
       }
       double newPrice = Double.parseDouble(priceText);
       int newNegotiable = negotiable.isSelected() ? 1 : 0;

       Annonces existingAd = sannonces.getById(idtoUp);
       System.out.println(existingAd);

       System.out.println(existingAd);
       if (existingAd != null) {
           System.out.println("test ");

           existingAd.setTitle(newTitle);
           existingAd.setDescription(newDescription);
           existingAd.setPrix(newPrice);
           existingAd.setNegiciable(newNegotiable);
           int categoryId = getIdFromCategoryName(selectedCategory);
           existingAd.setId_Cat(categoryId);
           String newImagePath = saveImageToFile();
           if (newImagePath != null) {
               existingAd.setImage(newImagePath);
           }
           System.out.println("test 1");
           sannonces.update(existingAd,idUser);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setHeaderText(null);
           alert.setContentText("Ad updated successfully!");
           alert.showAndWait();
           front_top frontTop = new front_top();
           frontTop.navigateToFXML("/tn.jardindart/Ads/affMyAds.fxml",editid);

       } else {

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
