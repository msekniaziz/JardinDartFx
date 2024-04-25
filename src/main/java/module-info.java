module applicationgui {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    exports tn.esprit.applicationgui;
    exports tn.esprit.applicationgui.Controllers;
    opens tn.esprit.applicationgui.Controllers to javafx.fxml;
    exports tn.esprit.applicationgui.gui;


}