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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class front_left implements Initializable {

    @FXML
    public Button btn;
    @FXML
    private Button prods_butt;


    @FXML
    private Label blogs;

    @FXML
    private Button blogs_buut;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void navigateToFXML(String fxmlPath, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
        try {
            node.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void navigateToViewPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/ViewPtCollect.fxml",btn);
    }

    public void navigateToViewPr(ActionEvent actionEvent) {
          navigateToFXML("/tn.jardindart/prodR/ViewProdR.fxml",btn);

    }

    public void map(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/Map.fxml",btn);

    }

    public void troc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/MarketV.fxml",btn);

    }

    public void GoToLogout(MouseEvent mouseEvent) {

        SessionManager.getInstance().cleanUserSessionAdmin();
        try {
            Node sourceNode = (Node) btn;
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn.jardindart/Login.fxml")));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToProducts(ActionEvent actionEvent) {
        front_top frontTop = new front_top();
        frontTop.navigateToFXML("/tn.jardindart/ptCollect/ViewPtCollect.fxml",prods_butt);
    }

    public void blog(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Blog/blogmain.fxml",blogs_buut);

    }
}
