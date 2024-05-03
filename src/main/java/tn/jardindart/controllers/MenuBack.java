package tn.jardindart.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.jardindart.controllers.user.SessionManager;
import tn.jardindart.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
        navigateToFXML("/tn.jardindart/prodR/ListProdR.fxml",btnMenus);
    }

    public void navigateToAddPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/AddPtCollect.fxml",btnMenus);
    }

    public void navigateToListPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/ListPtCollect.fxml",btnMenus);
    }

    public void navigateToListAssoc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/Association/AfficherAssoc.fxml",btnMenus);
    }

    public void navigateToViewPr(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/prodR/ViewProdR.fxml",btnMenus);

    }

    public void map(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/Map.fxml",btnMenus);

    }

    public void navigateToViewPt(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/ViewPtCollect.fxml",btnMenus);

    }


    @FXML
    private void navigateToListOrg() {

        navigateToFXML("/tn.jardindart/Association/AfficherAssoc.fxml", btnMenus);
    }
    @FXML
    private void navigateToListDBM() {

        navigateToFXML("/tn.jardindart/DBM/AfficherDBM.fxml", btnMenus);
    }
    @FXML
    private void navigateToListDA() {

        navigateToFXML("/tn.jardindart/DA/AfficherDA.fxml", btnMenus);
    }

    public void gotouser(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/AdminPannel.fxml",btnMenus);

    }

    public void LogoutAdmin(ActionEvent actionEvent){
        SessionManager.getInstance().cleanUserSessionAdmin();
        try {
            Node sourceNode = (Node) btnMenus;
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

    public void navigateToListT(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/ListType.fxml",btnMenus);

    }

    public void echange(ActionEvent event) {
        navigateToFXML("/tn.jardindart/BackproT.fxml",btnMenus);

    }
}