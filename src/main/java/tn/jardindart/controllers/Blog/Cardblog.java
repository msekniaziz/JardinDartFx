package tn.jardindart.controllers.Blog;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.models.dislike_blog;
import tn.jardindart.models.like_blog;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.ImageView;

// Make sure to import MouseEvent if needed

import javafx.fxml.FXML;
import java.net.URL;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.jardindart.models.Blog;
import tn.jardindart.models.ReponseBlog;

import java.sql.SQLException;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;


public class Cardblog {
    String filepath = null, filename = null, fn = null;
    FileChooser fc = new FileChooser();
   // String uploads = "C:/Users/ROUMAYSA/IdeaProjects/Blog/src/main/resources/Images/";
    @FXML
    private Label catrgtro;

    @FXML
    private Label dectro;

    @FXML
    private Button delete;

    @FXML
    private Button exchange;

    @FXML
    private ImageView itemimg;
    @FXML
    private Button jaimebtn;
    @FXML
    private Label itemname;

    @FXML
    private Label itemprice;

    @FXML
    private Button modif;

    @FXML
    private Button shareFbBtn;

    @FXML
    private Button exchange11;

    @FXML
    private Label nomidt;

    @FXML
    private HBox qr_code;
    private Blog book = new Blog();
    private ReponseBlog reponseBlog = new ReponseBlog();
    //   private like_blog like_blog= new like_blog();


    Blogservice blogservice = new Blogservice();
    ReponseBlogservice reponseBlogservice = new ReponseBlogservice();
    like_blogservice like_blogservice = new like_blogservice();
    dislike_blogservice dislike_blogservice = new dislike_blogservice();

    @FXML
    void modifie_cli(ActionEvent event) {
        try {
            // Load the EditBlog FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/EditBlog.fxml"));
            Parent root = loader.load();

            // Get the EditBlog controller
            EditBlog editBlogController = loader.getController();


            // Call the setBook method of the EditBlog controller with the selected Blog object
            editBlogController.setBook(book);

            // Show the EditBlog scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int setid() {
        int id;
        return id = this.book.getId();
    }


    public void setData(Blog book) {
        this.book = book;

        book.setId(this.book.getId());

        System.out.println(book);
        itemname.setText(book.getTitre());
        itemprice.setText(book.getCategory());
        dectro.setText(book.getContenu_blog());
        // Load the image
        // Check if the image path is not null or empty
        // Check if the image path is not null or empty
        // Check if the image path is not null or empty
        try {
            Image image = new Image(new File(book.getImage_blog()).toURI().toString());
            itemimg.setImage(image);
        } catch (Exception e) {
            // Handle the case where the image resource is not found
            System.out.println("Error loading image: " + e.getMessage());
            // Set a default image or display an error message
        }

        // Retrieve the number of likes for the current blog
        int likeCount = 0;
        try {
            likeCount = like_blogservice.countLikesByBlogId(book.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the text of the likeCountLabel
        likeCountLabel.setText("Likes: " + likeCount);
    }







    @FXML
    public void delete_clicked(ActionEvent event) throws SQLException {
        System.out.println("cliiick");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call the service method to delete the product from the database
                try {
                    blogservice.deleteblog(book.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Notify the user that the deletion was successful
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deletion Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Product deleted successfully.");
                successAlert.showAndWait();

                // Reload the page
                reloadPage(event);
            }
        });
    }

    @FXML
    void reloadPage(ActionEvent event) {
        // Get the URL of the FXML file
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/Blog/Blogmain.fxml"));
            Parent newContent = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Obtenir la scène actuelle
            Scene currentScene = stage.getScene();

            // Remplacer le contenu de la racine de la scène actuelle avec le nouveau contenu chargé depuis le fichier FXML
            currentScene.setRoot(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get any node in the scene hierarchy (replace getNode() with an appropriate method)
    private Node getNode() {
        // Provide implementation to get any node in the scene hierarchy

        return null;
    }


//    private void showPopup(String message, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle("Collection Update");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//
//
//
//        alert.showAndWait();
//    }


    @FXML
    public void AddRepBlog(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn.jardindart/Blog/AddRepBlog.fxml"));

        // Load the addProdwith.fxml file and get the root node
        Parent anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int z;

        // Access the controller after loading the fxml
        AddRepBlog addRepBlog = fxmlLoader.getController();

        // Set the book in the AddProdwith controller
        int a;
        a = this.book.getId();
        addRepBlog.setprod(reponseBlog, this.book.getId());

        // Create a new scene
        Scene addProdScene = new Scene(anchorPane);

        // Get the stage from the ActionEvent
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new stage for the addProdwith.fxml scene
        Stage addProdStage = new Stage();
        addProdStage.setTitle("produit with");
        addProdStage.setScene(addProdScene);

        // Set the owner stage to the current stage
        addProdStage.initOwner(currentStage);

        // Show the addProdwith.fxml scene without closing the current scene
        addProdStage.show();
    }


//    @FXML
//    void likeblog(ActionEvent event) {
//        Blog blog = new Blog();
//
//        int blogId = blog.getId();
//
//        // Ajoutez ici la logique pour gérer le "like" du blog
//        try {
//            // Créez un nouvel objet like_blog avec les informations nécessaires (id du blog, id de l'utilisateur, etc.)
//            like_blog like = new like_blog(1, Blog.getId(), user_id); // Par exemple, userId est l'ID de l'utilisateur actuellement connecté
//
//            // Appelez la méthode addlike du service like_blogservice pour ajouter le "like" dans la base de données
//            like_blogservice.addlike(like);
//
//            // Affichez un message pour confirmer que le blog a été liké
//            System.out.println("Blog liked successfully!");
//        } catch (SQLException e) {
//            // Gérez les exceptions SQL
//            e.printStackTrace();
//        }
//    }


    @FXML
    void ListRepBlog(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/tn.jardindart/Blog/Mainrep.fxml"));

        // Load the payment.fxml file and get the root node
        Parent anchorPane = null;
        try {
            // Charger la racine de l'élément Parent depuis le fichier FXML
            anchorPane = fxmlLoader.load();

            // Accéder au contrôleur après le chargement du fichier FXML
            Mainrep updateController = fxmlLoader.getController();

            // Définir le contenu dans le contrôleur EditBlog (s'il y a lieu)

            // Récupérer la scène actuelle
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer la racine de la scène actuelle par le nouveau contenu
            currentScene.setRoot(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


//     shareFbBtn.setOnMouseClicked(event -> {
//        String appId = "1093298458485409";
//        String appSecret = "561fb810c001c9113e0a1d8459f3d0e1";
//        String accessTokenString = "EAAPiWWJAYqEBO6oZAZBR5JoLV39RZAnyZAZAhoW0p61RdQAY9kJYcBIFOG1GDkjje4WYYG37kwMsogLNNTZCZCNKzfbZAyFhfOZBZBqnNRtzZB5uGZBZC5FLp5nSVHVHBDOZCwEB4BrM2akpo90ZARSETLVPultRpE2ZASGjjvwv3TIapgNR1yItl3NjSPuWpdkN3TZATYMGhcektFsKHl7RDUOWHZA2WlKrEZD";
//
//        Facebook facebook = new FacebookFactory().getInstance();
//        facebook.setOAuthAppId(appId, appSecret);
//        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

//        String msg = "New Course is available now "
//                + "\n*** Title: "
//                + blogservice.getTitre()
//                + "\n*** Description: "
////                + blogservice.getDescription()
////                + "\n***Date: "
////                + blogservice.getNiveau() ;
//
//        try {
//            facebook.postStatusMessage(msg);
//            System.out.println("Post shared successfully.");
//        } catch (FacebookException e) {
//            throw new RuntimeException(e);
//        }
//
//    });

    @FXML
    void partage(ActionEvent event) {
        String appId = "300308449736891";
        String appSecret = "4cf17d1c8ce3d0b4e57840c5504e611f";
        String accessTokenString = "EAAERIQJ4OLsBOZBjocsnORqmWrXp4iCZA4GpB3BC1wHVpvwpYk3saP1mM1cXCNZAOBYbcdzeRdxj2qcdIUA3dvI0sj9gJMCAZCTlm6m4sQ7jGEyTQ0Inqv8g9J2IfbvaNDwZAj1BVCcM76LysbBJ9ZBOjq93t8F37u5VSoHKeWQSHLtyC1l7HQYYVrcr86gHSV3AhYZBgBn7LUhZAV2g97HnV0MZD";
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        // Construire le message à partager sur Facebook
        String msg = "Nouveau blog disponible maintenant ";
//                + "\n*** Titre: "
//                + blogservice.getTitre()
//                + "\n*** Description: "
//        //+ blogservice.getDescription()
//        //+ "\n***Date: "
//        //+ blogservice.getNiveau() ;

        try {
            facebook.postStatusMessage(msg);
            System.out.println("Post shared successfully.");
        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void addlike(ActionEvent event) {
        like_blog like = new like_blog();

        // Set the properties of the like object.
        // You'll need to get the blog_id and user_id from somewhere.
        like.setLike_blog(1);
        like.setBlog_id(book.getId()); // Get the current blog id
        like.setUser_id(SessionManager.getInstance().getUserFront()); // Get the current user id

        try {
            like_blogservice.addlike(like);

            // Update the like count label text
            int likeCount = like_blogservice.countLikesByBlogId(book.getId());
            likeCountLabel.setText("Likes: " + likeCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label likeCountLabel;



    @FXML
    void adddislike(ActionEvent event) {
        dislike_blog dislike = new dislike_blog();

        // Set the properties of the like object.
        // You'll need to get the blog_id and user_id from somewhere.
        dislike.setDislike_blogs_id(1);
        dislike.setBlog_id(book.getId()); // Get the current blog id
        dislike.setUser_id(SessionManager.getInstance().getUserFront()); // Get the current user id

        try {
            dislike_blogservice.adddislike(dislike);

            // Update the like count label text
            int dislikesount = dislike_blogservice.countdisLikesByBlogId(book.getId());
            dislikeCountLabel.setText("disLikes: " + dislikesount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label dislikeCountLabel;


}