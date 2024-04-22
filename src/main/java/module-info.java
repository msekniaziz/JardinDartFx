module tn.esprit.ads {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.swing;


    opens tn.esprit.ads to javafx.fxml;
    exports tn.esprit.ads;
    exports tn.esprit.ads.test;
    exports tn.esprit.ads.Controllers;
    opens tn.esprit.ads.Controllers to javafx.fxml;
}