module tn.esprit.jardindart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports tn.esprit.gui;
    exports tn.esprit.controllers;
    opens tn.esprit.controllers to javafx.fxml;


    opens tn.esprit.jardindart to javafx.fxml;
    exports tn.esprit.jardindart;
}