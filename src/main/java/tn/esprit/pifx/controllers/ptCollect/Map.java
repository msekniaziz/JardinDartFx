package tn.esprit.pifx.controllers.ptCollect;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tn.esprit.pifx.models.PtCollect;
import tn.esprit.pifx.services.PtCollectService;
import tn.esprit.pifx.models.TypeDispo; // Importez la classe TypeDispo si elle n'est pas déjà importée

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Map implements Initializable {

    @FXML
    private WebView webView;

    private List<PtCollect> collectPoints;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PtCollectService ptCollectService = new PtCollectService();
        collectPoints = ptCollectService.recuperer();

        StringBuilder jsCode = new StringBuilder();

        jsCode.append("var map = L.map('map').setView([33.9184, 8.1339], 13);");
        jsCode.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {");
        jsCode.append("    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'");
        jsCode.append("}).addTo(map);");
        jsCode.append("var markers = [];");

        for (PtCollect collectPoint : collectPoints) {
            String popupContent = generatePopupContent(collectPoint);
            jsCode.append("var marker = L.marker([" + collectPoint.getLatitudePc() + ", " + collectPoint.getLongitudePc() + "])");
            jsCode.append(".addTo(map)");
            jsCode.append(".bindPopup('" + popupContent + "');");
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
                        "            height: 500px;" +
                        "            width: 90%;" +
                        "            left: 5%;" +
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

//        popupContent.append("<br><a href=\"#\" onclick=\"menuc.navigateToFXML('/tn/esprit/pifx/prodR/AddProdR.fxml', webView)\">Déposer un produit à recycler ici</a>");

        return popupContent.toString();
    }

    public void setCollectPoints(List<PtCollect> collectPoints) {
        this.collectPoints = collectPoints;
    }
}
