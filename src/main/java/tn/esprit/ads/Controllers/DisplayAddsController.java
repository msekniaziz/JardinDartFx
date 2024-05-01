package tn.esprit.ads.Controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import tn.esprit.ads.Entity.Annonces;
import tn.esprit.ads.Services.Sannonces;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayAddsController implements Initializable {
    @FXML
    private Button back;

    @FXML
    private TableColumn<?, ?> category;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    void backtodisplay(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*-ArrayList<Annonces>test=new ArrayList<Annonces>();
        Sannonces sannonces = new Sannonces();
        test=sannonces.getAllAdsByidcat();*/
    }
}
