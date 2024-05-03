package tn.jardindart.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class front_top implements Initializable {

    @FXML
    public Button btn;
    @FXML
    private Label usernameLabel;

    private SessionManager sessionManager= SessionManager.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int userId = sessionManager.getUserFront();

        // Assuming you have methods in SessionManager to get first name, last name, and points by user ID
        String firstName = sessionManager.getFirstNameById(userId);
        String lastName = sessionManager.getUsernameById(userId);
        int points = sessionManager.getPointsById(userId);
       // firstNameLabel.setText(firstName);
        //lastNameLabel.setText(lastName);
        //pointsLabel.setText(String.valueOf(points));

        String fullName = firstName + " " + lastName+","+points;
        usernameLabel.setText(fullName);


        // Concatenate first name, last name, and points
        //String username = firstName + " " + lastName + " - Points: " + points;


    }


    public void navigateToFXML(String fxmlPath, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
        try {
            node.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






    public void navigateToViewPr(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/prodR/ViewProdR.fxml",btn);

    }

    public void map(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/Map.fxml",btn);

    }

    public void handleAccountAction1(ActionEvent actionEvent) {

        navigateToFXML("/tn.jardindart/ProfileUser.fxml",btn);

       /* try {
            Node sourceNode = (Node) btn;
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn.jardindart/ProfileUser.fxml")));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("User Profile");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public void handleAccountAction2(ActionEvent actionEvent) {
    }

    public void ourCP(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/ViewPtCollect.fxml",btn);
    }

    public void myprd(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/prodR/ViewProdR.fxml",btn);

    }

    public void ourNGO(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Association/card_view.fxml",btn);

    }
    public void mydbm(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/DBM/card_view.fxml",btn);

    }
    public void myda(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/DA/card_view.fxml",btn);

    }

    public void navigateToLogin(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Login.fxml",btn);

    }

    public void addexchange(ActionEvent event) {
        navigateToFXML("/tn.jardindart/addproduittr.fxml",btn);

    }

    public void chatbot(ActionEvent event) {
        navigateToFXML("/tn.jardindart/openai.fxml",btn);

    }

    public void yourprodex(ActionEvent event) {
        navigateToFXML("/tn.jardindart/Market.fxml",btn);

    }

    public void yourAds(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Ads/affMyAds.fxml",btn);
    }

    public void allAds(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Ads/affADS.fxml",btn);
    }

    public void addAds(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Ads/addAds.fxml",btn);
    }

    public void Blogs(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Blog/blogmain.fxml",btn);

    }

    public void Addblogs(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Blog/AddBlog.fxml",btn);

    }
}
