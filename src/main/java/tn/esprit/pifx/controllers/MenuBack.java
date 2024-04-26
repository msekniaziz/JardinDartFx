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

public class MenuBack implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }

    public void navigateToFXML(String fxmlPath, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
        try {
            node.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void navigateToListProdR(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/prodR/ListProdR.fxml",btnMenus);
    }

    public void navigateToAddPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/AddPtCollect.fxml",btnMenus);
    }

    public void navigateToListPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/ListPtCollect.fxml",btnMenus);
    }

    public void navigateToViewPr(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/prodR/ViewProdR.fxml",btnMenus);

    }

    public void map(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/Map.fxml",btnMenus);

    }

    public void navigateToViewPt(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/ViewPtCollect.fxml",btnMenus);

    }

    public void navigateToListT(ActionEvent actionEvent) {
        navigateToFXML("/tn/esprit/pifx/ptCollect/ListType.fxml",btnMenus);

    }
}