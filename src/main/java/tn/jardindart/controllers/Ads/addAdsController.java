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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.Annonces;
import tn.jardindart.services.Sannonces;
import tn.jardindart.controllers.front_top ;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import static tn.jardindart.services.Sannonces.cnx;

public class addAdsController implements Initializable {

    @FXML
    private Button addid;

    @FXML
    private ComboBox<String> category ;

    @FXML
    private TextArea description;

    @FXML
    private CheckBox negotiable;

    @FXML
    private TextField price;

    @FXML
    private TextField title;
    @FXML
    private Button uploadImage;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView affichageImage;
    @FXML
    private Label PriceErrorLabel;
    @FXML
    private Label titleErrorLabel;
    @FXML
    private Label decriptionErrorLabel;


    private Sannonces sannonces = new Sannonces();
    private Annonces annonce = new Annonces();
    private FXMLLoader loader;

    private int idUser = SessionManager.getInstance().getUserFront();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new FXMLLoader(getClass().getResource("addAds.fxml"));

        // Récupérer la liste des catégories depuis la base de données
        ArrayList<String> categoriesList = sannonces.getCategories();

        // Créer une liste observable à partir de la liste des catégories
        ObservableList<String> categoriesObservableList = FXCollections.observableArrayList(categoriesList);

        // Assigner la liste observable à la ComboBox
        category.setItems(categoriesObservableList);


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

    public void addAnnonce(ActionEvent actionEvent) {
        String titleText = title.getText();
        String descriptionText = description.getText();
       double priceValue = Double.parseDouble(price.getText()); // Assurez-vous que le champ prix contient un nombre valide
        // String priceValue = price.getText();
       // double priceValue = 0.0; // Initialisation à 0.0 par défaut
        String priceText = price.getText();



        String selectedCategory = category.getValue();
        int negotiableValue = negotiable.isSelected() ? 1 : 0;
        int categoryId = getIdFromCategoryName(selectedCategory);
        String statut ="inctive";
        String imagePath = null;
        if (imageView.getImage() != null) {

            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String destinationPath = "C:/Users/user/IdeaProjects/Ads/src/main/java/tn/esprit/ads/img" + timeStamp + ".png";
            saveImageToFile(destinationPath);
            imagePath = destinationPath;


            Image image = new Image("file:" + destinationPath);
            affichageImage.setImage(image);}
        int etat =0;
        Annonces newAnnonce = new Annonces();
        newAnnonce.setTitle(titleText);
        newAnnonce.setDescription(descriptionText);
        newAnnonce.setPrix(Double.parseDouble(String.valueOf(priceValue)));
        newAnnonce.setId_Cat(categoryId); // Définir l'ID de la catégorie
        newAnnonce.setNegiciable(negotiableValue);
        newAnnonce.setCategory(statut);
        newAnnonce.setStatus(etat);
        newAnnonce.setImage(imagePath);

        boolean isvalid = true;
        if (descriptionText.isEmpty())
        { decriptionErrorLabel.setText("this field is empty");
            isvalid = false;
        }
        if (titleText.isEmpty())
        { titleErrorLabel.setText("this field is empty");
            isvalid = false;
        }
        if (!priceText.isEmpty()) {
            try {
                priceValue = Double.parseDouble(String.valueOf(Double.parseDouble(String.valueOf(Double.parseDouble(priceText)))));
            } catch (NumberFormatException e) {
                PriceErrorLabel.setText("Veuillez entrer un prix valide");
                return; // Quitter la méthode si le prix n'est pas un nombre valide
            }
        } else {
            PriceErrorLabel.setText("Veuillez entrer un prix");
            return; // Quitter la méthode si le champ de prix est vide
        }


        if (!isvalid)
        {return;}
        try {

            sannonces.add(newAnnonce,idUser);
            System.out.println("Annonce ajoutée avec succès.");
            front_top frontTop = new front_top();
            frontTop.navigateToFXML("/tn.jardindart/Ads/affMyAds.fxml",addid);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'annonce : " + e.getMessage());
        }


    }



    @FXML
    void category(ActionEvent event) {

    }

    @FXML
    void negotiable(ActionEvent event) {

    }

    // Méthode pour enregistrer l'image sur le disque
    private void saveImageToFile(String destinationPath) {
        try {
            Annonces imageview;
            Image image = imageView.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            File outputFile = new File(destinationPath);
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        // Configurez les filtres de fichiers pour ne montrer que les images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        // Affichez la boîte de dialogue pour permettre à l'utilisateur de sélectionner un fichier image
       // File selectedFile = fileChooser.showOpenDialog(new Stage());
        File file = fileChooser.showOpenDialog(uploadImage.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            // Mettez à jour le label ou le champ de texte pour afficher le chemin du fichier sélectionné
            // imagePathLabel.setText(selectedFile.getAbsolutePath());
            // Associez le chemin du fichier à votre objet Annonces
            // Assurez-vous d'avoir une instance de Annonces accessible dans ce contrôleur
            //annonce.setImage(file.getAbsolutePath());
        }

    }


    public void navigateToViewPr(ActionEvent actionEvent) {
    }
}
