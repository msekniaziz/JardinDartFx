package tn.jardindart.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.jardindart.test.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class Back {
    @FXML
    private Button gotouser;

    @FXML
    private Button Logout;
    public void navigateToFXML(String fxmlPath, Node node) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
        try {
            node.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LogoutAdmin(ActionEvent actionEvent){
        SessionManager.getInstance().cleanUserSessionAdmin();
        try {
            Node sourceNode = (Node) Logout;
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

    public void gotouser(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) gotouser.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tn.jardindart/AdminPannel.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User SignIn");
        stage.show();
    }

    public void navigateToListProdR(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/prodR/ListProdR.fxml",Logout);
    }

    public void navigateToAddPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/AddPtCollect.fxml",Logout);
    }

    public void navigateToListPtc(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/ListPtCollect.fxml",Logout);
    }


    public void map(ActionEvent actionEvent) {
        navigateToFXML("/tn.jardindart/ptCollect/Map.fxml",Logout);

    }

}
