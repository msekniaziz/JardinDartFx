package tn.esprit.pifx.controllers.ptCollect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tn.esprit.pifx.controllers.front_top;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.models.TypeDispo;
import tn.esprit.pifx.services.PtCollectService;
import tn.esprit.pifx.controllers.prodR.AddProdR;
import tn.esprit.pifx.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPtCollect implements Initializable {

    @FXML
    private TilePane tilePane;

    @FXML
    private WebView webView;

    private List<PtCollect> collectPoints;

    front_top menuc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PtCollectService ptCollectService = new PtCollectService();
        collectPoints = ptCollectService.recupererT();

        afficherPtCollect();
        afficherCarte();
        menuc =new front_top();
    }

    private void afficherPtCollect() {
        for (PtCollect ptCollect : collectPoints) {
            AnchorPane card = createCard(ptCollect);
            tilePane.getChildren().add(card);
        }
    }

    private AnchorPane createCard(PtCollect ptCollect) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("card");

        Label nameLabel = new Label(ptCollect.getNomPc());
        nameLabel.getStyleClass().add("name-label");
        card.getChildren().add(nameLabel);
        AnchorPane.setTopAnchor(nameLabel, 10.0);
        AnchorPane.setLeftAnchor(nameLabel, 10.0);

        Label addressLabel = new Label(ptCollect.getAdressePc());
        addressLabel.getStyleClass().add("address-label");
        card.getChildren().add(addressLabel);
        AnchorPane.setTopAnchor(addressLabel, 30.0);
        AnchorPane.setLeftAnchor(addressLabel, 10.0);

        List<TypeDispo> typesDisponibles = ptCollect.getType();
        if (!typesDisponibles.isEmpty()) {
            StringBuilder typesStringBuilder = new StringBuilder("Types disponibles : ");
            for (TypeDispo typeDispo : typesDisponibles) {
                typesStringBuilder.append(typeDispo.getNom()).append(", ");
            }
            typesStringBuilder.delete(typesStringBuilder.length() - 2, typesStringBuilder.length()); // Supprime la virgule et l'espace en trop à la fin
            Label typesLabel = new Label(typesStringBuilder.toString());
            typesLabel.getStyleClass().add("types-label");
            card.getChildren().add(typesLabel);
            AnchorPane.setTopAnchor(typesLabel, 50.0);
            AnchorPane.setLeftAnchor(typesLabel, 10.0);
        }

        Button editButton = new Button("I recycle");
        editButton.setOnAction(event -> navigateToAddProdR(ptCollect));
        card.getChildren().add(editButton);
        AnchorPane.setBottomAnchor(editButton, -40.0);
        AnchorPane.setRightAnchor(editButton, 80.0);


        return card;
    }

    private void afficherCarte() {
        StringBuilder jsCode = new StringBuilder();
        jsCode.append("var map = L.map('map').setView([33.9184, 8.1339], 3);");
        jsCode.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {");
        jsCode.append("    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'");
        jsCode.append("}).addTo(map);");
        jsCode.append("var markers = [];");

        for (PtCollect collectPoint : collectPoints) {
            String popupContent = generatePopupContent(collectPoint);
            jsCode.append("var marker = L.marker([").append(collectPoint.getLatitudePc()).append(", ").append(collectPoint.getLongitudePc()).append("])");
            jsCode.append(".addTo(map)");
            jsCode.append(".bindPopup('").append(popupContent).append("');");
            jsCode.append("markers.push(marker);");
        }

        WebEngine engine = webView.getEngine();
        engine.loadContent(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>Map</title>" +
                        "    <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" integrity=\"sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=\" crossorigin=\"\">" +
                        "    <style>" +
                        "        #map {" +
                        "            height: 800px;" +
                        "            width: 100%;" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "<div id=\"map\"></div>" +
                        "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\" integrity=\"sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=\" crossorigin=\"\"></script>" +
                        "<script>" +
                        jsCode.toString() +
                        "</script>" +
                        "</body>" +
                        "</html>"
        );
    }

    private String generatePopupContent(PtCollect collectPoint) {
        StringBuilder popupContent = new StringBuilder();
        popupContent.append("<div class=\"text-center\" style=\"font-family: Muli, sans-serif; font-weight: bold;\">")
                .append(collectPoint.getNomPc())
                .append("</div>");

        // Ajoutez ici le contenu du popup
        // Parcourez les types disponibles et ajoutez-les au contenu du popup
        List<TypeDispo> typesDisponibles = collectPoint.getType();
        if (!typesDisponibles.isEmpty()) {
            popupContent.append("<div style=\"font-family: Muli, sans-serif; border-radius: 30px; border: 1px solid green; padding: 10px; margin-top: 5px;\">");
            popupContent.append("Types disponibles : ");
            for (TypeDispo typeDispo : typesDisponibles) {
                popupContent.append(typeDispo.getNom()).append(", ");
            }
            popupContent.delete(popupContent.length() - 2, popupContent.length()); // Supprime la virgule et l'espace en trop à la fin
            popupContent.append("</div>");
        }

        popupContent.append("<br><a href=\"#\" onclick=\"navigateToAddProdR()\">Déposer un produit à recycler ici</a>");

        return popupContent.toString();
    }

    public void navigateToAddProdR(PtCollect ptCollect) {


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pifx/prodR/AddProdR.fxml"));
        try {
            tilePane.getScene().setRoot(fxmlLoader.load());
            AddProdR controller = fxmlLoader.getController();
            controller.initialize(ptCollect);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void setCollectPoints(List<PtCollect> collectPoints) {
        this.collectPoints = collectPoints;
    }
}
