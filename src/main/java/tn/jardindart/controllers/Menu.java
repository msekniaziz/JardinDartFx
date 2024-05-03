package tn.jardindart.controllers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tn.jardindart.test.HelloApplication;

import java.io.IOException;

public class Menu {

    @FXML
    public Button btn;


    public void navigateToFXML(String fxmlPath, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
        try {
            node.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void navigateToListOrg() {
        // Accessing the parent directly
       // MenuButton menuButton = (MenuButton) btn.getParentPopup().getOwnerNode();
        navigateToFXML("/tn/esprit/jardindart/Association/card_view.fxml", btn);
    }


    @FXML
    private void navigateToListDBM() {

        navigateToFXML("/tn/esprit/jardindart/DBM/card_view.fxml", btn);
    }

    @FXML
    private void navigateToListDA() {
        // Accessing the parent directly
        // MenuButton menuButton = (MenuButton) btn.getParentPopup().getOwnerNode();
        navigateToFXML("/tn/esprit/jardindart/DA/card_view.fxml", btn);
    }






    public void navigateToAddPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/AddPtCollect.fxml",btn);
    }

    public void navigateToListPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/ListPtCollect.fxml",btn);
    }

    public void navigateToViewPr(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/prodR/ViewProdR.fxml",btn);

    }


}

