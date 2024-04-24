module tn.esprit.jardindart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.google.zxing;
    requires javafx.swing;
    exports tn.esprit.gui;
    exports tn.esprit.controllers;
    opens tn.esprit.controllers to javafx.fxml;
    opens tn.esprit.entities to javafx.base;


    opens tn.esprit.jardindart to javafx.fxml;
    exports tn.esprit.jardindart;
    opens tn.esprit.gui to javafx.fxml;
}