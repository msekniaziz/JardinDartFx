package tn.esprit.pifx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.esprit.pifx.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class front_left implements Initializable {

    @FXML
    public Button btn;

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
        navigateToFXML("/tn/esprit/pifx/ptCollect/ViewPtCollect.fxml",btn);
    }

    public void navigateToViewPr(ActionEvent actionEvent) {
          navigateToFXML("/tn/esprit/pifx/prodR/ViewProdR.fxml",btn);

    }

    public void map(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/Map.fxml",btn);

    }
}
