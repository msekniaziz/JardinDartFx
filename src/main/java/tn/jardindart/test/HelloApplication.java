package tn.jardindart.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.jardindart.services.ProdRService;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/ptCollect/ListPtCollect.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/ptCollect/ViewPtCollect.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.jardindart/ptCollect/AddPtCollect.fxml"));
        Parent root = loader.load();

       /*ViewPtCollect controller = loader.getController();
        List<PtCollect> collectPoints = new ArrayList<>(); // replace with your list of collect points
        controller.setCollectPoints(collectPoints);
*/
        Scene scene = new Scene(root, 1500, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        ProdRService prodRService = new ProdRService();

        launch();
    }
}