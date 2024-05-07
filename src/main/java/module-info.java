module applicationgui {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    requires facebook4j.core;
    requires org.apache.pdfbox;
    requires org.apache.httpcomponents.httpcore; // Ajoutez cette ligne

    exports tn.esprit.applicationgui;
    exports tn.esprit.applicationgui.Controllers;
    opens tn.esprit.applicationgui.Controllers to javafx.fxml;
    exports tn.esprit.applicationgui.gui;
    opens tn.esprit.applicationgui to javafx.fxml;
    requires java.net.http; // Ajoutez cette ligne
    requires org.apache.httpcomponents.httpclient; // Ajoutez cette ligne
}
